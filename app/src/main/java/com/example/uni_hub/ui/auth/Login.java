package com.example.uni_hub.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.example.uni_hub.MainActivity;
import com.example.uni_hub.R;
import com.google.android.material.textfield.TextInputEditText;

public class Login extends AppCompatActivity {

    private static final String TAG = "Login";
    private static final String FAIL_MSG = "Signin Failed Password and/or email incorrect!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initPlugins();

        TextInputEditText emailText = findViewById(R.id.signin_email_text);
        TextInputEditText passText = findViewById(R.id.signin_password_text);
        TextView failText = findViewById(R.id.signin_fail_text);

        findViewById(R.id.signin_btn).setOnClickListener(
                v -> signin(emailText.getText().toString(),
                        passText.getText().toString(),
                        failText));

        findViewById(R.id.signin_signup_btn).setOnClickListener(
                v -> startActivity(new Intent(getApplicationContext(), Signup.class)));
    }

    private void signin(String email, String pass, TextView fail) {
        AuthSignUpOptions options = AuthSignUpOptions.builder()
                .userAttribute(AuthUserAttributeKey.email(), email)
                .build();
        Amplify.Auth.signUp(email, pass, options,
                result -> startActivity(new Intent(getApplicationContext(),
                        MainActivity.class)),
                error -> fail.setText(FAIL_MSG)
        );
    }

    private void initPlugins() {
        try {
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.configure(getApplicationContext());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }
}