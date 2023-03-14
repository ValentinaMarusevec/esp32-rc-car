package com.jcc.smartcar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.text.TextUtils;
import android.util.Patterns;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUpActivity extends BaseActivity {
    TextView login;


    private boolean validateForm(final String name, final String email, final String password) {
        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            return false;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        Button btnSignUp = (Button) findViewById(R.id.btn_sign_up);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        login = findViewById(R.id.textViewLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this, LoginPageActivity.class);
                startActivity(intent);
            }
        });
    }

    private void registerUser() {
        EditText editTextName = findViewById(R.id.et_name);
        EditText editTextEmail = findViewById(R.id.et_email);
        EditText editTextPassword = findViewById(R.id.et_password);

        String name = editTextName.getText().toString();
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if (validateForm(name, email, password)) {
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser firebaseUser = task.getResult().getUser();
                                String uid = firebaseUser.getUid();
                                User user = new User(uid, name, email);
                                FirebaseFirestore.getInstance().collection("users").document(uid)
                                        .set(user)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d("User", "User added to Firestore");
                                                } else {
                                                    Log.d("User", "Error adding user to Firestore");
                                                }
                                            }
                                        });
                            } else {
                                Toast.makeText(SignUpActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
    }}
