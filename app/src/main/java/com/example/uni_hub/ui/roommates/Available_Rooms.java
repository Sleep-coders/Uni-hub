package com.example.uni_hub.ui.roommates;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.uni_hub.MainActivity;
import com.example.uni_hub.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Available_Rooms extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    String s1[];
    int images[] = {R.drawable.car, R.drawable.car, R.drawable.car};
    RecyclerView recyclerView;

    Activity context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_rooms);

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

        s1= getResources().getStringArray(R.array.availabe_rooms);
        recyclerView = findViewById(R.id.recyclerViewId);

        RoommatesAdapter myAdapter= new RoommatesAdapter(this, s1,images);

        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        Intent intent = new Intent(this, Create_post_Roommates.class);
        Button createPost= findViewById(R.id.createPostId);
        createPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });


        View confirmReq = getLayoutInflater().inflate(R.layout.my_row,null);
        Button reqRoom = (Button) confirmReq.findViewById(R.id.req_room_btn);
        reqRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Available_Rooms.this,  Room_Owner_notification.class);
                startActivity(intent2);
            }
        });

        ImageView notification_requestRoommates = findViewById(R.id.ic_notificationId);
        notification_requestRoommates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent notification_roommates_intent = new Intent(Available_Rooms.this, Room_Owner_notification.class);
                startActivity(notification_roommates_intent);
            }
        });

    }
}