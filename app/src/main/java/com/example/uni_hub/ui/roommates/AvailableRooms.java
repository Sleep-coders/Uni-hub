package com.example.uni_hub.ui.roommates;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.AppUser;
import com.amplifyframework.datastore.generated.model.Room;
import com.example.uni_hub.MainActivity;
import com.example.uni_hub.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class AvailableRooms extends AppCompatActivity {

    private static final String TAG = AvailableRooms.class.getName();
    BottomNavigationView bottomNavigationView;
    public static RecyclerView recyclerView;
    RoommatesAdapter myAdapter;
    private String currnetUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_rooms);


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


        recyclerView = findViewById(R.id.recyclerViewId);

        Button createPost = findViewById(R.id.createPostId);
        createPost.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreatePostRoommates.class);
            startActivity(intent);
        });

//        ImageView notification_requestRoommates = findViewById(R.id.ic_notificationId);
//        notification_requestRoommates.setOnClickListener(view -> {
//            Intent notification_roommates_intent = new Intent(AvailableRooms.this, Room_Owner_notification.class);
//            startActivity(notification_roommates_intent);
//        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getSupportActionBar().hide();
        getAllRooms();
    }


    public void getAllRooms() {
        Amplify.API.query(ModelQuery.list(Room.class), success -> {
            List<Room> roomList = new ArrayList<>();
            for (Room room : success.getData()) {
                roomList.add(room);
            }
            runOnUiThread(() -> {
                myAdapter = new RoommatesAdapter(this, roomList);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(myAdapter);
            });
            Log.i(TAG, "getAllRooms: ====> " + success.getData().toString());
        }, err -> {

        });
    }


}