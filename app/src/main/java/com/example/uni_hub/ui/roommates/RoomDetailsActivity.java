package com.example.uni_hub.ui.roommates;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.AppUser;
import com.amplifyframework.datastore.generated.model.Room;
import com.example.uni_hub.R;
import com.squareup.picasso.Picasso;

import java.util.Arrays;

public class RoomDetailsActivity extends AppCompatActivity {

    private static final String TAG = RoomDetailsActivity.class.getName();
    String roomOwnerPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_details);
        Intent intent = getIntent();
        String roomId = intent.getStringExtra("roomId");
        Log.i(TAG, "onCreate: =====> " + roomId);
        getSupportActionBar().hide();
        fetchDataAmplify(roomId);
    }

    public void startPhoneIntent(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        roomOwnerPhone = roomOwnerPhone.replaceFirst("\\+962", "0");
        Log.i(TAG, "startPhoneIntent: =====> " + roomOwnerPhone );
        intent.setData(Uri.parse("tel:" + roomOwnerPhone));
        startActivity(intent);
    }

    public void fetchDataAmplify(String roomId) {
        Amplify.API.query(ModelQuery.get(Room.class, roomId), success -> {
            Log.i(TAG, "fetchDataAmplify: ======>" + success.getData().toString());
            String userId = success.getData().getRoomOwnerId();

            Amplify.API.query(ModelQuery.list(AppUser.class, AppUser.ID.eq(userId)), result -> {
                for (AppUser user : result.getData()) {
                    Log.i(TAG, "fetchDataAmplify: =====> " + user.toString());
                    roomOwnerPhone = user.getUserPhoneNumber();
                    fillThingsUp(success.getData());
                    break;
                }

            }, err -> {
                Log.e(TAG, "fetchDataAmplify: ==>" + Arrays.toString(err.getStackTrace()));
            });
        }, error -> {
            Log.e(TAG, "fetchDataAmplify: " + Arrays.toString(error.getStackTrace()));
        });
    }

    public void fillThingsUp(Room room) {
        String userName = room.getRoomOwnerUserName();
        String userEmail = room.getRoomOwnerEmail();
        String userPhoneNumber = roomOwnerPhone;
        String roomLocation = room.getRoomLocation();
        String roomDesc = room.getRoomDescription();
        String roomImage = room.getRoomImage();

        TextView userNameView = findViewById(R.id.room_mate_name);
        TextView userRoomMateEmailView = findViewById(R.id.room_mate_email);
        TextView userPhoneNumberView = findViewById(R.id.room_mate_phone_number);
        TextView roomLocationView = findViewById(R.id.room_location);
        TextView roomDescView = findViewById(R.id.room_description);
        ImageView roomImageView = findViewById(R.id.user_image_view);

        runOnUiThread(() -> {
            userNameView.setText(userName);
            userRoomMateEmailView.setText(userEmail);
            userPhoneNumberView.setText(userPhoneNumber);
            roomLocationView.setText(roomLocation);
            roomDescView.setText(roomDesc);
            Picasso.get().load(roomImage).into(roomImageView);
        });

    }


}