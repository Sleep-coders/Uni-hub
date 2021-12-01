package com.example.uni_hub.ui.roommates;

import static android.content.Intent.ACTION_PICK;
import static android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
import static android.provider.MediaStore.Video.Media.INTERNAL_CONTENT_URI;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.amplifyframework.core.Amplify;
import com.example.uni_hub.MainActivity;
import com.example.uni_hub.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.apache.commons.io.FilenameUtils;

import java.io.File;

import br.com.onimur.handlepathoz.HandlePathOz;
import br.com.onimur.handlepathoz.HandlePathOzListener;
import br.com.onimur.handlepathoz.model.PathOz;


public class Create_post_Roommates extends AppCompatActivity implements HandlePathOzListener.SingleUri{
    private static final int REQUEST_PERMISSION = 123;
    private HandlePathOz handlePathOz;

    private Handler handleRoomPost;
    private String roomImgUrl;

    BottomNavigationView bottomNavigationView;

    private TextView textView;
    private ProgressBar progressBar;
    private SeekBar seekBar;

    ActivityResultLauncher<Intent> loadRoomImageLauncher = registerForActivityResult(
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post_roommates);

        String userEmail= Amplify.Auth.getCurrentUser().getUsername();

        getSupportActionBar().hide();

        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
//                        finish();
                        return true;
                }
                return false;
            }
        });



        textView = (TextView) findViewById(R.id.progressBarTextId);
        progressBar= (ProgressBar) findViewById(R.id.progressBarId);
        seekBar=(SeekBar) findViewById(R.id.seekBarId);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressBar.setProgress(progress);
                textView.setText("" + progress + " JD");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Intent intent = new Intent(this, Available_Rooms.class);
        Button addPost= findViewById(R.id.addPostBtnId);
        addPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

        handleRoomPost = new Handler(Looper.getMainLooper(), msg ->{
            roomImgUrl = msg.getData().getString("roomImgUrl");
            saveRoomToAPI(roomImgUrl,userEmail);
            return false;
        });
    }

    private void saveRoomToAPI(String roomImgUrl, String userEmail) {
//        Amplify.API.query(ModelQuery.get(User.class, userEmail), success -> {
//
//            User oldUser = success.getData();
//
//            User user = User.builder()
//                    .userRealName(oldUser.getUserRealName())
//                    .userNickname(usernameTextView.getText().toString())
//                    .userPhoneNumber(phoneTextView.getText().toString())
//                    .userEmail(oldUser.getUserEmail())
//                    .userUniversity(schoolTextView.getText().toString())
//                    .userLocation(locationTextView.getText().toString())
//                    .userImg(oldUser.getUserImg())
//                    .id(oldUser.getId())
//                    .build();
//
//            Amplify.API.mutate(ModelMutation.update(user), response ->
//                            Log.i(TAG, "successfully update user data"),
//                    error -> Log.e(TAG, "Unsuccessfully update user data!?"));
//
//        }, error -> {
//        });
    }

    private void loadRoomImage() {

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

            loadRoomImageLauncher.launch(intent);
//            startActivityForResult(intent, REQUEST_OPEN_GALLERY);
        }
    }
    private boolean checkSelfPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestHandlePathOz(@NonNull PathOz pathOz, @Nullable Throwable throwable) {
        Bundle bundle = new Bundle();
        Message message = new Message();
        if (throwable != null) {
            Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
        }
        String filePath = FilenameUtils.getName(pathOz.getPath());
        File file = new File(pathOz.getPath());
        Amplify.Storage.uploadFile(
                filePath,
                file,
                result -> Amplify.Storage.getUrl(
                        result.getKey(),
                        resultUrl -> {
                            bundle.putString("roomImgUrl", resultUrl.getUrl().toString());
                            message.setData(bundle);
                            handleRoomPost.sendMessage(message);
                        },
                        error -> Log.e("MyAmplifyApp", "URL generation failure", error)
                ),
                storageFailure -> Log.e("MyAmplifyApp", "Upload failed", storageFailure)
        );
    }
}