package com.jcc.smartcar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginPageActivity extends BaseActivity {

    EditText mail;
    EditText password;
    Button signIn;
    ImageButton signInGoogle;
    TextView signUp;
    TextView forgotPassword;
    ProgressBar progressBarLogin;

    GoogleSignInClient googleSignInClient;

    FirebaseAuth auth = FirebaseAuth.getInstance();

    ActivityResultLauncher<Intent> activityResultLauncher;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        //register
        registerActivityForGoogleSignIn();

        mail = findViewById(R.id.editTextLoginEmailAddress);
        password = findViewById(R.id.editTextLoginPassword);
        signIn = findViewById(R.id.buttonLoginPage);
        signInGoogle = findViewById(R.id.signInButtonLogin);
        signUp = findViewById(R.id.textViewLoginRegister);
        forgotPassword = findViewById(R.id.textViewLoginForgotPassword);
        progressBarLogin = findViewById(R.id.progressBarLogin);

        signIn.setOnClickListener(view -> {

            String userEmail = mail.getText().toString();
            String userPassword = password.getText().toString();

            signInWithFirebase(userEmail, userPassword);

        });

        signInGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                signInGoogle();

            }
        });

        signUp.setOnClickListener(view -> {

            Intent i = new Intent(LoginPageActivity.this, SignUpActivity.class);
            startActivity(i);

        });

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentForgotPassword = new Intent(LoginPageActivity.this, ForgotPassword.class);
                startActivity(intentForgotPassword);
            }
        });
    }

    public void signInGoogle(){

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("57460183051-ok6m4f77s79dv4g0t789fdt1tsq5a1cp.apps.googleusercontent.com")
                .requestEmail().build();

        googleSignInClient = GoogleSignIn.getClient(this,gso);

        signin();

    }

    public void signin(){

        Intent signInIntent = googleSignInClient.getSignInIntent();
        activityResultLauncher.launch(signInIntent);

    }

    public void  registerActivityForGoogleSignIn(){

        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
                , new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                        int resultCode = result.getResultCode();
                        Intent data = result.getData();

                        if (resultCode == RESULT_OK && data != null) {

                            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                            firebaseSignInWithGoogle(task);

                        }

                    }
                });

    }

    private void firebaseSignInWithGoogle(Task<GoogleSignInAccount> task){

        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            Toast.makeText(this, "Successfully!", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(LoginPageActivity.this, MainActivity.class);
            startActivity(i);
            finish();
            firebaseGoogleAccount(account);

        } catch (ApiException e) {
            e.printStackTrace();
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    private void firebaseGoogleAccount(GoogleSignInAccount account){

        AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(authCredential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){

                            //FirebaseUser user = auth.getCurrentUser();

                    }else {

                        }
                    }
                });

    }

    public void signInWithFirebase(String userEmail, String userPassword){
        progressBarLogin.setVisibility(View.VISIBLE);
        signIn.setClickable(false);

        auth.signInWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(this, task -> {

                    if (task.isSuccessful()){
                        Intent i = new Intent(LoginPageActivity.this, MainActivity.class);
                                startActivity(i);
                                finish();
                                progressBarLogin.setVisibility(View.INVISIBLE);
                                Toast.makeText(LoginPageActivity.this, "Login is successful!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                                Toast.makeText(LoginPageActivity.this, "Login is not successful!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = auth.getCurrentUser();
        if (user != null){
            Intent i = new Intent(LoginPageActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }

    }
}