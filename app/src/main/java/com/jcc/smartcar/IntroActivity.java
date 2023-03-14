package com.jcc.smartcar;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;

public class IntroActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        Button btnSignUp = findViewById(R.id.btn_sign_up_intro);
        btnSignUp.setOnClickListener(view -> {
            Intent signupIntent = new Intent(IntroActivity.this, SignUpActivity.class);
            startActivity(signupIntent);
        });

        Button btnLogin = findViewById(R.id.btn_sign_in_intro);
        btnLogin.setOnClickListener(view -> {
            Intent loginIntent = new Intent(IntroActivity.this, LoginPageActivity.class);
            startActivity(loginIntent);
        });

    }
}