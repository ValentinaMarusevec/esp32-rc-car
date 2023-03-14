package com.jcc.smartcar;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;



public class MoreFragment extends Fragment {
    private View root;
    private EditText editTextName;
    private EditText editTextEmail;
    private TextView logout;
    private FirebaseAuth auth;
    private FirebaseFirestore firestore;
    private FirebaseUser firebaseUser;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_more, container, false);

        logout = root.findViewById(R.id.textViewLogOut);

        editTextName = root.findViewById(R.id.editTextName);
        editTextEmail = root.findViewById(R.id.editEmailAdressProfileInfo);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        firebaseUser = auth.getCurrentUser();

        if(firebaseUser != null){
            getUserInfo();
            getGoogleUserInfo();
        }
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(auth.getCurrentUser()!=null){
                    auth.signOut();
                    Intent intent = new Intent(getActivity(), LoginPageActivity.class);
                    startActivity(intent);
                }
            }
        });
        return  root;
    }

    private void getUserInfo(){
        firestore.collection("users").document(firebaseUser.getUid()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                User user = document.toObject(User.class);
                                editTextName.setText(user.getName());
                                editTextEmail.setText(user.getEmail());
                            }
                        }
                    }
                });
    }

    private void getGoogleUserInfo() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getContext());
        if (account != null) {
            String name = account.getDisplayName();
            String email = account.getEmail();
            editTextName.setText(name);
            editTextEmail.setText(email);
        }
    }
}

