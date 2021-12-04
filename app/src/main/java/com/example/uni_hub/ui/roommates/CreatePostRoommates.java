package com.example.uni_hub.ui.roommates;

import static android.content.Intent.ACTION_PICK;
import static android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
import static android.provider.MediaStore.Video.Media.INTERNAL_CONTENT_URI;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.AppUser;
import com.amplifyframework.datastore.generated.model.Room;
import com.example.uni_hub.MainActivity;
import com.example.uni_hub.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.Arrays;

import br.com.onimur.handlepathoz.HandlePathOz;
import br.com.onimur.handlepathoz.HandlePathOzListener;
import br.com.onimur.handlepathoz.model.PathOz;


public class CreatePostRoommates extends AppCompatActivity implements HandlePathOzListener.SingleUri {
    private static final int REQUEST_PERMISSION = 123;
    private static final int REQUEST_OPEN_GALLERY = 920;

    private static final String TAG = CreatePostRoommates.class.getName();
    private HandlePathOz handlePathOz;
    private String roomImgUrl;

    BottomNavigationView bottomNavigationView;
    private TextView textView;
    private ProgressBar progressBar;
    private double roomCost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post_roommates);
        String userEmail = Amplify.Auth.getCurrentUser().getUsername();

        handlePathOz = new HandlePathOz(this, this);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
//                        finish();
                        return true;
                }
                return false;
            }
        });

        roomImgUrl = null;
        roomCost = 0;
        textView = findViewById(R.id.progressBarTextId);
        progressBar = findViewById(R.id.progressBarId);
        SeekBar seekBar = findViewById(R.id.seekBarId);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                roomCost = progress;
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

        Button addPost = findViewById(R.id.addPostBtnId);
        addPost.setOnClickListener((v) -> {
            saveRoomToAPI(roomImgUrl, userEmail);
        });

        ImageView uploadImageImageView = findViewById(R.id.ic_imageId);
        uploadImageImageView.setOnClickListener((v) -> {
            openFile();
        });
    }


    public void openFile() {
        if (checkSelfPermission()) {
            Intent intent;
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                intent = new Intent(ACTION_PICK, EXTERNAL_CONTENT_URI);
            } else {
                intent = new Intent(ACTION_PICK, INTERNAL_CONTENT_URI);
            }

            intent.setType("*/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.putExtra("return-data", true);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            startActivityForResult(intent, REQUEST_OPEN_GALLERY);
        }
    }

    private boolean checkSelfPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_OPEN_GALLERY && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            if (uri != null) {
                //set Uri to handle
                handlePathOz.getRealPath(uri);
            }
        }
    }

    @Override
    public void onRequestHandlePathOz(@NonNull PathOz pathOz, @Nullable Throwable throwable) {
        if (throwable != null) {
            Log.e(TAG, "onRequestHandlePathOz: ERRRRROR");
        }

        Log.i(TAG, "onRequestHandlePathOz: ====> URI === " + pathOz.getPath());

        String fileName = FilenameUtils.getName(pathOz.getPath());
        Log.i(TAG, "onRequestHandlePathOz: ===============> " + fileName);
        File file = new File(pathOz.getPath());

        Amplify.Storage.uploadFile(
                fileName,
                file,
                uploadFileResult -> {
                    Log.i("MyAmplifyApp", "Successfully uploaded: " + uploadFileResult.getKey());
                    Amplify.Storage.getUrl(
                            uploadFileResult.getKey(),
                            result -> {
                                Log.i("MyAmplifyApp", "Successfully generated: " + result.getUrl());
                                roomImgUrl = result.getUrl().toString();
                            },
                            error -> Log.e("MyAmplifyApp", "URL generation failure", error)
                    );
                },
                storageFailure -> Log.e("MyAmplifyApp", "Upload failed", storageFailure)
        );

    }

    private void saveRoomToAPI(String roomImgUrl, String userEmail) {
        Amplify.API.query(ModelQuery.list(AppUser.class, AppUser.USER_EMAIL.eq(userEmail)),
                success -> {
                    String dbUserId = null;
                    String dbUserName = null;
                    EditText roomLocationEditText = findViewById(R.id.locationPlainTextId);
                    String roomLocationInfo = roomLocationEditText.getText().toString();

                    EditText roomDescEditText = findViewById(R.id.locationPlainTextId2);
                    String roomDescInfo = roomDescEditText.getText().toString();
                    for (AppUser dbUser : success.getData()) {
                        dbUserId = dbUser.getId();
                        dbUserName = dbUser.getUserNickname();
                        break;
                    }

                    Room newRoom = Room.builder().
                            roomOwnerId(dbUserId).
                            roomOwnerEmail(userEmail).
                            roomOwnerUserName(dbUserName).
                            roomLocation(roomLocationInfo).
                            roomImage(roomImgUrl).
                            roomCost(roomCost).
                            roomDescription(roomDescInfo).build();

                    Amplify.API.mutate(ModelMutation.create(newRoom),
                            result -> {
                                Log.i(TAG, "saveRoomToAPI: ==> " + result.getData().toString());
                                runOnUiThread(() -> {
                                    Toast.makeText(getApplicationContext(), "The Room Has Been Added", Toast.LENGTH_SHORT).show();
                                });
                                finish();
                            },
                            err -> {
                                Log.e(TAG, "saveRoomToAPI: " + Arrays.toString(err.getStackTrace()));
                            });

                }, error -> {
                    Log.e(TAG, "saveRoomToAPI: " + Arrays.toString(error.getStackTrace()));
                });
    }


}