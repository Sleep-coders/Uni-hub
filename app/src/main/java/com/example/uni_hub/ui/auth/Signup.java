package com.example.uni_hub.ui.auth;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
//import android.widget.EditText;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.AppUser;
import com.amplifyframework.datastore.generated.model.Ride;
import com.example.uni_hub.MainActivity;
import com.example.uni_hub.R;
import com.google.android.material.textfield.TextInputEditText;

public class Signup extends AppCompatActivity {
    private static final String TAG = "SignUpProcess";
    private static final String MyPREFERENCES = "passwordRef";
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    Handler dialogHandler;
    Handler apiHandler;

    String errorMsg = "SignUp Failed: Something Went Wrong.";
    String confirmErrorMsg = "Wrong Code , Try Again";
    String reSendMsg = "Code was sent again , check your email";
    String reSendErrMsg = "Failed to resend code.";

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        TextInputEditText nameText = findViewById(R.id.signup_name_text);
        TextInputEditText userNameText = findViewById(R.id.signup_username_text);
        TextInputEditText emailText = findViewById(R.id.signup_email_text);
        TextInputEditText passwordText = findViewById(R.id.signup_password_text);
        TextInputEditText phoneText = findViewById(R.id.signup_phonenumber_text);
        TextView errorMsg = findViewById(R.id.signup_errormsg);

//        confirmUserDialog("email",
//                "password",
//                "password",
//                "password",
//                "password");

        Button signUpBtn = findViewById(R.id.signuppage_signup_btn);
        Button signInBtn = findViewById(R.id.signup_signin_btn);


        apiHandler = new Handler(Looper.getMainLooper(), message -> {
            String email = message.getData().getString("email");
            String name = message.getData().getString("name");
            String userName = message.getData().getString("userName");
            String phoneNumber = message.getData().getString("phoneNumber");

//            saveUserInDynamoDB(student);
            saveUserInDynamoDB(name, email, userName, phoneNumber);
            return false;
        });


        dialogHandler = new Handler(Looper.getMainLooper(), message -> {

            String email = message.getData().getString("email");
            String password = message.getData().getString("password");
            String name = message.getData().getString("name");
            String userName = message.getData().getString("userName");
            String phoneNumber = message.getData().getString("phoneNumber");
            confirmUserDialog(email, password, name, userName, phoneNumber);

            return false;
        });

        signUpBtn.setOnClickListener(view -> {

            String name = nameText.getText().toString();
            String userName = userNameText.getText().toString();
            String email = emailText.getText().toString();
            String password = passwordText.getText().toString();
            String phoneNumber = "+962" + phoneText.getText().toString();

            signUp(name, userName, email, password, phoneNumber, errorMsg);
        });

        signInBtn.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), Login.class));
        });


    }

    ///////////////////// == signUp == ////////////////////////////////

    public void signUp(String name, String userName, String email, String password, String phoneNumber, TextView errMsg) {

        AuthSignUpOptions options = AuthSignUpOptions.builder()
                .userAttribute(AuthUserAttributeKey.name(), name)
                .userAttribute(AuthUserAttributeKey.nickname(), userName)
                .userAttribute(AuthUserAttributeKey.phoneNumber(), phoneNumber)
                .build();

        Amplify.Auth.signUp(email, password, options,
                result -> {
                    Log.i("AuthQuickStart", "Result: " + result.toString());

                    Bundle bundle = new Bundle();
                    bundle.putString("email", email);
                    bundle.putString("password", password);
                    bundle.putString("name", name);
                    bundle.putString("userName", userName);
                    bundle.putString("phoneNumber", phoneNumber);
                    Message message = new Message();
                    message.setData(bundle);
                    dialogHandler.sendMessage(message);
                },
                error -> {
                    Log.i("InSignUp", "ERRROOOOOORRR===>>>>>======" + error.toString());
                    runOnUiThread(() -> errMsg.setText(errorMsg));
                }
        );
    }

    ///////////////// == confirm User Dialog == ////////////////////////////////

    public void confirmUserDialog(String email, String password, String name, String userName, String phoneNumber) {

        dialogBuilder = new AlertDialog.Builder(this);
        View confirmUser = getLayoutInflater().inflate(R.layout.confirm_pop_up, null);

        TextInputEditText confirmCodeText = (TextInputEditText) confirmUser.findViewById(R.id.edit_text);
        Button confirmBtn = (Button) confirmUser.findViewById(R.id.edit_btn);
//        Button resendBtn = (Button) confirmUser.findViewById(R.id.re_send_btn);
        Button cancelBtn = (Button) confirmUser.findViewById(R.id.edit_cancel_btn);
        TextView confirmError = (TextView) confirmUser.findViewById(R.id.cofirm_error);

        dialogBuilder.setView(confirmUser);
        dialog = dialogBuilder.create();
        dialog.show();

        confirmBtn.setOnClickListener(view -> {
            String confirmCode = confirmCodeText.getText().toString();
            Amplify.Auth.confirmSignUp(
                    email,
                    confirmCode,
                    result -> {
                        Log.i("AuthQuickstart", result.isSignUpComplete() ? "Confirm signUp succeeded" : "Confirm sign up not complete");
                        silentSignIn(email, password, name, userName, phoneNumber);
                    },
                    error -> {
                        Log.e("AuthQuickstart", error.toString());
                        runOnUiThread(() -> confirmError.setText(confirmErrorMsg));
                    }
            );
        });


//        resendBtn.setOnClickListener(view -> {
//            Amplify.Auth.resendUserAttributeConfirmationCode(email,
//                    result -> {
//                        Log.i("AuthDemo", "Code was sent again: " + result.toString());
//                        runOnUiThread(()->confirmError.setText(reSendMsg));
//                    },
//                    error -> {
//                        Log.e("AuthDemo", "Failed to resend code.", error);
//                        runOnUiThread(() ->confirmError.setText(reSendErrMsg));
//                    }
//            );
//        });

        cancelBtn.setOnClickListener(view -> {
            dialog.dismiss();
        });


    }

    ///////////////// == save User In DynamoDB == ////////////////////////////////

    private void saveUserInDynamoDB(String name, String email, String userName, String phoneNumber) {
        AppUser user = AppUser.builder().userRealName(name).userNickname(userName).userPhoneNumber(phoneNumber).userEmail(email).build();
        Amplify.API.mutate(ModelMutation.create(user),
                response -> {
                    Log.i("MyAmplifyApp", "User Saved With Data ========> : " + response.getData());
                },
                error -> Log.e("MyAmplifyApp", "Create failed", error)
        );
    }

    ///////////////// == silent SignIn == ////////////////////////////////??

    private void silentSignIn(String email, String password, String name, String userName, String phoneNumber) {
        Bundle bundle = new Bundle();
        bundle.putString("email", email);
        bundle.putString("name", name);
        bundle.putString("userName", userName);
        bundle.putString("phoneNumber", phoneNumber);
        Message message = new Message();
        message.setData(bundle);
        Amplify.Auth.signIn(
                email,
                password,
                result -> {
                    Log.i("AuthQuickstart", result.isSignInComplete() ? "Sign in succeeded" : "Sign in not complete");

                    // save password to referance
                    SharedPreferences sharedPref = getApplicationContext()
                            .getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("userPassword", password);
                    editor.apply();
                    apiHandler.sendMessage(message);
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                },
                error -> {
                    Log.e("AuthQuickstart", error.toString());
                    startActivity(new Intent(getApplicationContext(), Login.class));
                }
        );
    }
}