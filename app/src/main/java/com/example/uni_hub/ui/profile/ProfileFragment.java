package com.example.uni_hub.ui.profile;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.amplifyframework.datastore.generated.model.AppUser;
import com.amplifyframework.datastore.generated.model.Car;
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
    private static final String MyPREFERENCES = "passwordRef";
    private static final String EXTERNAL_USER_IMG = "https://upload.wikimedia.org/wikipedia/commons/9/99/Sample_User_Icon.png";
    private static final String EXTERNAL_CAR_IMG = "https://static1.hotcarsimages.com/wordpress/wp-content/uploads/2021/09/BMW-M3-GTR-Not-Available-For-Sale.jpg?q=50&fit=crop&w=1280&dpr=1.5";
    private static final int REQUEST_PERMISSION = 123;
    private static final String TOAST_ADD_CAR_MSG = "Please fill all fields";

    private androidx.appcompat.app.AlertDialog.Builder dialogBuilder;
    private androidx.appcompat.app.AlertDialog dialog;
    private TextView usernameTextView;
    private TextView emailTextView;
    private TextView passwordTextView;
    private TextView schoolTextView;
    private TextView phoneTextView;
    private TextView locationTextView;
    private ImageView userImgView;
    private ProfileViewModel profileViewModel;
    private FragmentProfileBinding binding;
    private HandlePathOz handlePathOz;
    private Handler userImageHandler;
    private Handler carImageHandler;
    private Handler loadProfileDataHandler;

    private boolean loadCarImgToggle = false;
    private boolean loadUserImgToggle = false;
    private boolean carImgIsLoaded = false;

    private String imgUrl;

    ActivityResultLauncher<Intent> loadUserImageLauncher = registerForActivityResult(
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

    ActivityResultLauncher<Intent> loadCarImageLauncher = registerForActivityResult(
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

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
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

//        Button button = (Button) root.findViewById(R.id.profile_save_btn);
//        root.findViewById(R.id.profile_save_btn).setOnClickListener(v -> {
//            saveChangeToApi(userEmail);
//            saveChangesToCognito();
//        });

        Button button = (Button) root.findViewById(R.id.profile__addcar_button);
        Log.i(TAG, "+button is: " + button);


        root.findViewById(R.id.profile_user_img_upload_btn).setOnClickListener(view -> loadUserImage());

//        root.findViewById(R.id.profile_addcar_btn).setOnClickListener(view -> loadAddCarDialog(userEmail));

        userImageHandler = new Handler(Looper.getMainLooper(), msg -> {
            imgUrl = msg.getData().getString("userImgUrl", EXTERNAL_USER_IMG);
            Picasso.get().load(imgUrl).into(userImgView);
            return false;
        });

        carImageHandler = new Handler(Looper.getMainLooper(), msg -> {
            imgUrl = msg.getData().getString("carImgUrl", EXTERNAL_CAR_IMG);
            carImgIsLoaded = true;
            return false;
        });

        loadProfileDataHandler = new Handler(Looper.getMainLooper(), msg -> {

            String userName = msg.getData().getString("userName", "N/A");
            String userPhoneNumber = msg.getData().getString("userPhoneNumber", "N/A");
            String userAddress = msg.getData().getString("userAddress", "N/A");
            String userSchool = msg.getData().getString("userSchool", "N/A");
            String userPassword = msg.getData().getString("userPassword", "N/A");

            loadProfileData(userName, userEmail, userPhoneNumber, userAddress, userSchool, userPassword);
            return false;
        });
        return root;
    }

    private void addCarToApi(String userEmail, String carModel, String carImgUrl, int carSeatsNumber) {
        Amplify.API.query(ModelQuery.get(AppUser.class, userEmail), success -> {

            AppUser owner = success.getData();

            Car car = Car.builder()
                    .ownerId(owner.getId())
                    .carModel(carModel)
                    .carImg(carImgUrl)
                    .carSeatsNum(carSeatsNumber)
                    .build();

            Amplify.API.mutate(ModelMutation.create(car),
                    res -> {
                        Log.i(TAG, "Car is successfully saved");
                        carImgIsLoaded = false;
                    },
                    error -> Log.e(TAG, "Car is not saved"));

        }, error -> {
        });
    }

    private void loadAddCarDialog(String userEmail) {
        View addUserCarView = getLayoutInflater().inflate(R.layout.addusercardialog, null);

        TextInputEditText carModelText = (TextInputEditText) addUserCarView.findViewById(R.id.addcar_carmodel_text);
        TextInputEditText carSeatsText = (TextInputEditText) addUserCarView.findViewById(R.id.addcar_carseatnumber_text);


        addUserCarView.findViewById(R.id.addcar_addimg_btn).setOnClickListener(view -> loadCarImage());
        addUserCarView.findViewById(R.id.addcar_add_btn).setOnClickListener(view -> {

            String carModel = carModelText.getText().toString();
            String carSeatsNumber = carSeatsText.getText().toString();

            if (carImgIsLoaded && !carModel.equals("") && carSeatsNumber.equals(""))
                addCarToApi(userEmail, imgUrl, carModel, Integer.parseInt(carSeatsNumber));
            else {
                Toast toast = Toast.makeText(requireContext(), TOAST_ADD_CAR_MSG, Toast.LENGTH_LONG);
                toast.show();
            }
        });


    }

    private void loadProfileData(String userEmail) {
        SharedPreferences sharedPref = requireContext()
                .getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        Amplify.API.query(ModelQuery.get(AppUser.class, userEmail),
                success -> {
                    AppUser user = success.getData();

                    Log.i(TAG, "user is: " + user);
//                    String userName = user.getUserNickname();
//                    String userPhoneNumber = user.getUserPhoneNumber();
//                    String userAddress = !user.getUserLocation().equals("") ? user.getUserLocation() : "N/A";
//                    String userSchool = !user.getUserUniversity().equals("") ? user.getUserUniversity() : "N/A";
//                    String userPassword = sharedPref.getString("userPassword", "N/A");
//
//                    Bundle bundle = new Bundle();
//                    bundle.putString("userName", userName);
//                    bundle.putString("userPhoneNumber", userPhoneNumber);
//                    bundle.putString("userAddress", userAddress);
//                    bundle.putString("userSchool", userSchool);
//                    bundle.putString("userPassword", userPassword);
//                    Message message = new Message();
//                    message.setData(bundle);
//                    loadProfileDataHandler.sendMessage(message);

                },
                error -> Log.e(TAG, "User not found!"));
    }

    private void loadProfileData(String userName,
                                 String userEmail,
                                 String userPhoneNumber,
                                 String userAddress,
                                 String userSchool,
                                 String userPassword) {

        usernameTextView.setText(userName);
        emailTextView.setText(userEmail);
        phoneTextView.setText(userPhoneNumber);
        locationTextView.setText(userAddress);
        schoolTextView.setText(userSchool);
        passwordTextView.setText(userPassword);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initHandlePathOz() {
        handlePathOz = new HandlePathOz(requireContext(), this);
    }

    private void loadUserImage() {
        loadUserImgToggle = true;
        loadCarImgToggle = false;

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

            loadUserImageLauncher.launch(intent);
//            startActivityForResult(intent, REQUEST_OPEN_GALLERY);
        }
    }

    private void loadCarImage() {
        loadUserImgToggle = false;
        loadCarImgToggle = true;

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

            loadCarImageLauncher.launch(intent);
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

        View editUserInfo = getLayoutInflater().inflate(R.layout.profile_edit_dialog, null);

        TextInputLayout textContainer = (TextInputLayout) editUserInfo.findViewById(R.id.edit_text_container);
        TextInputEditText editInfoText = (TextInputEditText) editUserInfo.findViewById(R.id.edit_text);
        Button saveBtn = (Button) editUserInfo.findViewById(R.id.edit_btn);
        Button cancelBtn = (Button) editUserInfo.findViewById(R.id.edit_cancel_btn);

        dialogBuilder = new AlertDialog.Builder(requireContext());
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
        Amplify.API.query(ModelQuery.get(AppUser.class, userEmail), success -> {

            AppUser oldUser = success.getData();

            AppUser user = AppUser.builder()
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
        Bundle bundle = new Bundle();
        Message message = new Message();
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
                            if (loadUserImgToggle) {
                                bundle.putString("userImgUrl", resultUrl.getUrl().toString());
                                message.setData(bundle);
                                userImageHandler.sendMessage(message);
                            } else if (loadCarImgToggle) {
                                bundle.putString("carImgUrl", resultUrl.getUrl().toString());
                                message.setData(bundle);
                                carImageHandler.sendMessage(message);
                            }
                        },
                        error -> Log.e("MyAmplifyApp", "URL generation failure", error)
                ),
                storageFailure -> Log.e("MyAmplifyApp", "Upload failed", storageFailure)
        );
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.profile_userinfoupdat_btn:
//                Log.i(TAG, "save in onClick");
//                break;
//            case R.id.profile__addcar_button:
//                Log.i(TAG, "upload in onClick");
//                break;
//        }
//    }
}