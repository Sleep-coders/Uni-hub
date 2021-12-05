package com.example.uni_hub.ui.carpool.rider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.AppUser;
import com.amplifyframework.datastore.generated.model.Ride;
import com.example.uni_hub.MainActivity;
import com.example.uni_hub.R;
import com.example.uni_hub.ui.carpool.driver.DriverAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Rider_home extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView;

    Handler allRidesHandler;
    Handler userIdHandler;
    List<Ride> allRides = new ArrayList<>();
    String userId;
    List<Ride> filteredRides;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_home);

        getUserID();






        recyclerView = findViewById(R.id.availaberides_recyclerview);
//        Rider_home_adapter rider_home_adapter = new Rider_home_adapter(allRides,userId,this);
//        filteredRides =  allRides.stream().filter(ride ->
//                !ride.getOwnerId().equals(userId)
//        ).collect(Collectors.toList());
        userIdHandler=new Handler(Looper.getMainLooper(), message -> {
            userId = message.getData().getString("ID");
            Log.i("USERID_InHandler++++>>>>", "++++++>>>>>>>" + userId);
            getAllRides();
            return false;
        });

        allRidesHandler = new Handler(Looper.getMainLooper(), message -> {
            Log.i("ALL_RIDES>>>>>||||||||++++>>>>", "++++++>>>>>>>" + allRides);

            Log.i("filteredRidesr++++>>>>", "++++++>>>>>>>" + filteredRides);
            recyclerView.setAdapter(new Rider_home_adapter(allRides,userId,this));
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
            return false;
        });


        recyclerView.setAdapter(new Rider_home_adapter(allRides,userId,this));
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();


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
    }

    public void getAllRides() {
        allRides = new ArrayList<>();
        Amplify.API.query(ModelQuery.list(Ride.class),
                success -> {
                    for (Ride ride : success.getData()) {
                        if(!ride.getOwnerId().equals(userId))
                            allRides.add(ride);
                    }
                    allRidesHandler.sendEmptyMessage(0);
                },
                error -> {
                    runOnUiThread(() -> {
                        @SuppressLint("ShowToast") Toast toast = Toast.makeText(this, "No available rides for now, Try again later", Toast.LENGTH_LONG);
                        toast.show();
                    });
                    Log.i("getAllRides", "======> Error in getting all rides" + error.getMessage());
                });
    }


    public void getUserID() {
        Bundle bundle = new Bundle();
        Message message = new Message();
        String email = Amplify.Auth.getCurrentUser().getUsername();
        Amplify.API.query(ModelQuery.list(AppUser.class, AppUser.USER_EMAIL.eq(email)),
                success -> {
                    String  id = "";
                    for (AppUser user : success.getData().getItems()) {
                        id = user.getId();
                        Log.i("USERID++++>>>>", "++++++>>>>>>>" + user.getId());
                        break;
                    }
                    bundle.putString("ID",id);
                    message.setData(bundle);
                    userIdHandler.sendMessage(message);
                },
                error -> {
                    Log.i("getUserID", "Error in getting user id");
                });
    }
}