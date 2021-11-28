package com.example.uni_hub.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.storage.s3.AWSS3StoragePlugin;
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
        getSupportActionBar().hide();
        initPlugins();

        TextInputEditText emailText = findViewById(R.id.signin_email_text);
        TextInputEditText passText = findViewById(R.id.signin_password_text);
        TextView failText = findViewById(R.id.signin_fail_text);

        findViewById(R.id.signpage_signin_btn).setOnClickListener(
                v -> signin(emailText.getText().toString(),
                        passText.getText().toString(),
                        failText));

        findViewById(R.id.signin_signup_btn).setOnClickListener(
                v -> startActivity(new Intent(getApplicationContext(), Signup.class)));
    }

    private void signin(String email, String pass, TextView fail) {
        Amplify.Auth.signIn(
                email,
                pass,
                result -> {Log.i("AuthQuickstart", result.isSignInComplete() ? "Sign in succeeded" : "Sign in not complete");
                startActivity(new Intent(getApplicationContext(), MainActivity.class));},
                error -> runOnUiThread(()->fail.setText(FAIL_MSG))
        );
    }

    private void initPlugins() {
        try {
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.addPlugin(new AWSDataStorePlugin());
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSS3StoragePlugin());
            Amplify.configure(getApplicationContext());
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }
    }
}