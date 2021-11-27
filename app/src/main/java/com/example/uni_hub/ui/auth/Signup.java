package com.example.uni_hub.ui.auth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.example.uni_hub.R;

public class Signup extends AppCompatActivity {

    String errorMsg = "SignUp Failed: Something Went Wrong.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        EditText nameText = findViewById(R.id.signup_name_text);
        EditText userNameText = findViewById(R.id.signup_username_text);
        EditText emailText = findViewById(R.id.signup_email_text);
        EditText passwordText = findViewById(R.id.signup_password_text);
        EditText phoneText = findViewById(R.id.signup_phonenumber_text);
        TextView errorMsg = findViewById(R.id.signup_errormsg);


        String name = nameText.getText().toString();
        String userName = userNameText.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String phoneNumber = phoneText.getText().toString();


        Button signUpBtn = findViewById(R.id.signuppage_signup_btn);
        Button signInBtn = findViewById(R.id.signup_signin_btn);

        signUpBtn.setOnClickListener(view -> {
            signUp(name,userName,email,password,phoneNumber,errorMsg);
        });

        signInBtn.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(),Login.class));
        });


    }

    public void signUp(String name, String userName, String email, String password, String phoneNumber, TextView errMsg) {
        AuthSignUpOptions options = AuthSignUpOptions.builder()
                .userAttribute(AuthUserAttributeKey.name(), name)
                .userAttribute(AuthUserAttributeKey.nickname(), userName)
                .userAttribute(AuthUserAttributeKey.phoneNumber(), phoneNumber)
                .build();
        Amplify.Auth.signUp(email, password, options,
                result -> Log.i("AuthQuickStart", "Result: " + result.toString()),
                error -> errMsg.setText(errorMsg)
        );
    }


}