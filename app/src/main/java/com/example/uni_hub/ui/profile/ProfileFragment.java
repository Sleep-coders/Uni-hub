package com.example.uni_hub.ui.profile;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.User;
import com.example.uni_hub.R;
import com.example.uni_hub.databinding.FragmentProfileBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import static android.content.Intent.ACTION_PICK;
import static android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
import static android.provider.MediaStore.Video.Media.INTERNAL_CONTENT_URI;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import br.com.onimur.handlepathoz.HandlePathOzListener;
import br.com.onimur.handlepathoz.model.PathOz;
import br.com.onimur.handlepathoz.HandlePathOz;

import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment implements HandlePathOzListener.SingleUri {
    private static final String TAG = "ProfileFragment";
    private static final String EXTERNAL_USER_IMG = "https://upload.wikimedia.org/wikipedia/commons/9/99/Sample_User_Icon.png";
    private static final int REQUEST_PERMISSION = 123;
    private static final int REQUEST_OPEN_GALLERY = 1111;

    private androidx.appcompat.app.AlertDialog.Builder dialogBuilder;
    private androidx.appcompat.app.AlertDialog dialog;
    private TextView usernameTextView;
    private TextView emailTextView;
    private TextView passwordTextView;
    private TextView schoolTextView;
    private TextView phoneTextView;
    private TextView locationTextView;
    private ImageView userImgView;
    private ProfileViewModel homeViewModel;
    private FragmentProfileBinding binding;
    private HandlePathOz handlePathOz;
    private Handler userImageHandle;

    ActivityResultLauncher<Intent> someActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    // There are no request codes
                    Intent data = result.getData();
                    assert data != null;
                    Uri uri = data.getData();
                    handlePathOz.getRealPath(uri);
                }
            });

    /// do codegen and push /////
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        String userEmail = Amplify.Auth.getCurrentUser().getUsername(); // user email

        initHandlePathOz();
        usernameTextView = root.findViewById(R.id.profile_usename_veiw);
        emailTextView = root.findViewById(R.id.profile_email_veiw);
        passwordTextView = root.findViewById(R.id.profile_password_veiw);
        schoolTextView = root.findViewById(R.id.profile_school_view);
        phoneTextView = root.findViewById(R.id.profile_phone_veiw);
        locationTextView = root.findViewById(R.id.profile_location_veiw);
        userImgView = root.findViewById(R.id.profile_user_img);

        loadProfileData(userEmail);

        root.findViewById(R.id.profile_username_edit_btn)
                .setOnClickListener(v -> setProfileTextView("Username", "text"));// cog and api
        root.findViewById(R.id.profile_password_edit_btn)
                .setOnClickListener(v -> setProfileTextView("Password", "textPassword")); // cog
        root.findViewById(R.id.profile_school_edit_btn)
                .setOnClickListener(v -> setProfileTextView("School", "text")); // api
        root.findViewById(R.id.profile_phone_edit_btn)
                .setOnClickListener(v -> setProfileTextView("Phone Number", "phone")); // api and cog
        root.findViewById(R.id.profile_location_edit_btn)
                .setOnClickListener(v -> setProfileTextView("Location", "text")); // neither just profile

        root.findViewById(R.id.profile_save_btn).setOnClickListener(view -> {
            saveChangeToApi(userEmail);
            saveChangesToCognito();
        });

        root.findViewById(R.id.profile_user_img_upload_btn).setOnClickListener(view -> openFile());

        userImageHandle = new Handler(Looper.getMainLooper(), msg -> {
            String imgUrl = msg.getData().getString("userImgUrl", EXTERNAL_USER_IMG);
            Picasso.get().load(imgUrl).into(userImgView);
            return false;
        });

        return root;
    }

    private void loadProfileData(String userEmail) {
        Amplify.API.query(ModelQuery.get(User.class, userEmail),
                success -> {
                    User user = success.getData();

                    String userName = !user.getUserNickname().equals("") ? user.getUserNickname() : "N/A";
//                    String userEmail = !user.getUserEmail().equals("") ? user.getUserEmail() : "N/A";

//                    usernameTextView.setText(user.getUserNickname());
//                    emailTextView.setText(user.getUserEmail());
////                    passwordTextView.setText(Amplify.Auth.getCurrentUser()); // set the internet
//                    schoolTextView.setText(user.getUserUniversity());
//                    phoneTextView.setText(user.getUserPhoneNumber());
//                    locationTextView.setText(user.getUserLocation());
                },
                error -> Log.e(TAG, "User not found!"));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initHandlePathOz() {
        handlePathOz = new HandlePathOz(requireContext(), this);
    }

    private void openFile() {
        if (checkSelfPermission()) {
            Intent intent;
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                intent = new Intent(ACTION_PICK, EXTERNAL_CONTENT_URI);
            } else {
                intent = new Intent(ACTION_PICK, INTERNAL_CONTENT_URI);
            }
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.putExtra("return-data", true);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            someActivityResultLauncher.launch(intent);
//            startActivityForResult(intent, REQUEST_OPEN_GALLERY);
        }
    }

    private boolean checkSelfPermission() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
            return false;
        }
        return true;
    }

    public void setProfileTextView(String hint, String fieldType) {

        dialogBuilder = new AlertDialog.Builder(requireContext());
        View editUserInfo = getLayoutInflater().inflate(R.layout.profile_edit_dialog, null);

        TextInputLayout textContainer = (TextInputLayout) editUserInfo.findViewById(R.id.edit_text_container);
        TextInputEditText editInfoText = (TextInputEditText) editUserInfo.findViewById(R.id.edit_text);
        Button saveBtn = (Button) editUserInfo.findViewById(R.id.edit_btn);
        Button cancelBtn = (Button) editUserInfo.findViewById(R.id.edit_cancel_btn);

        dialogBuilder.setView(editUserInfo);
        dialog = dialogBuilder.create();
        dialog.show();

        textContainer.setHint(hint);
        if (fieldType.equals("text"))
            editInfoText.setInputType(InputType.TYPE_CLASS_TEXT);
        else if (fieldType.equals("password"))
            editInfoText.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        else
            editInfoText.setInputType(InputType.TYPE_CLASS_PHONE);


        saveBtn.setOnClickListener(view -> {
            String changedValue = editInfoText.toString();
            switch (hint) {
                case "Username":
                    usernameTextView.setText(changedValue);
                    break;
                case "Password":
                    passwordTextView.setText(changedValue);
                    break;
                case "School":
                    schoolTextView.setText(changedValue);
                    break;
                case "Phone Number":
                    phoneTextView.setText(changedValue);
                    break;
                default:
                    locationTextView.setText(changedValue);
                    break;
            }
        });


        cancelBtn.setOnClickListener(view -> dialog.dismiss());
    }

    public void saveChangeToApi(String userEmail) {
        Amplify.API.query(ModelQuery.get(User.class, userEmail), success -> {

            User oldUser = success.getData();

            User user = User.builder()
                    .userRealName(oldUser.getUserRealName())
                    .userNickname(usernameTextView.getText().toString())
                    .userPhoneNumber(phoneTextView.getText().toString())
                    .userEmail(oldUser.getUserEmail())
                    .userUniversity(schoolTextView.getText().toString())
                    .userLocation(locationTextView.getText().toString())
                    .userImg(oldUser.getUserImg())
                    .id(oldUser.getId())
                    .build();

            Amplify.API.mutate(ModelMutation.update(user), response ->
                            Log.i(TAG, "successfully update user data"),
                    error -> Log.e(TAG, "Unsuccessfully update user data!?"));

        }, error -> {
        });
    }

    public void saveChangesToCognito() {
        List<AuthUserAttribute> userAttributes = new ArrayList<>();
        userAttributes.add(new AuthUserAttribute(AuthUserAttributeKey.nickname(), usernameTextView.getText().toString()));
        userAttributes.add(new AuthUserAttribute(AuthUserAttributeKey.phoneNumber(), phoneTextView.getText().toString()));
        userAttributes.add(new AuthUserAttribute(AuthUserAttributeKey.address(), locationTextView.getText().toString()));

        Amplify.Auth.updateUserAttributes(
                userAttributes, // attributes is a list of AuthUserAttribute
                result -> Log.i("AuthDemo", "Updated user attributes = " + result.toString()),
                error -> Log.e("AuthDemo", "Failed to update user attributes.", error)
        );
    }

    @Override
    public void onRequestHandlePathOz(@NonNull PathOz pathOz, @Nullable Throwable throwable) {
        if (throwable != null) {
            Toast.makeText(requireContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
        }
        String filePath = FilenameUtils.getName(pathOz.getPath());
        File file = new File(pathOz.getPath());
        Amplify.Storage.uploadFile(
                filePath,
                file,
                result -> Amplify.Storage.getUrl(
                        result.getKey(),
                        resultUrl -> {
                            Bundle bundle = new Bundle();
                            bundle.putString("userImgUrl", resultUrl.getUrl().toString());
                            Message message = new Message();
                            message.setData(bundle);
                            userImageHandle.sendMessage(message);
                        },
                        error -> Log.e("MyAmplifyApp", "URL generation failure", error)
                ),
                storageFailure -> Log.e("MyAmplifyApp", "Upload failed", storageFailure)
        );
    }
}