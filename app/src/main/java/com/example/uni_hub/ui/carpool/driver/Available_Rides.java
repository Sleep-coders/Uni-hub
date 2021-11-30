package com.example.uni_hub.ui.carpool.driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.AppUser;
import com.amplifyframework.datastore.generated.model.Ride;
import com.example.uni_hub.MainActivity;
import com.example.uni_hub.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class Available_Rides extends AppCompatActivity {
    RecyclerView recycler_view_driver_post;

    private Button create_ride;

    List<Ride> allRides;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BottomNavigationView bottomNavigationView;
        getSupportActionBar().hide();
        setContentView(R.layout.activity_available_rides);

        getUserID();
        getAllRides();

        recycler_view_driver_post = findViewById(R.id.recycler_view_driver_post);


        DriverAdapter driverAdapter = new DriverAdapter(allRides , userId);
        recycler_view_driver_post.setAdapter(driverAdapter);
        recycler_view_driver_post.setLayoutManager(new LinearLayoutManager(this));

        // Navbar Bottom
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        // Create a Ride Button
        create_ride = findViewById(R.id.create_ride);
        create_ride.setOnClickListener(view -> {
            getCreateRide();
        });
    }

    public void getCreateRide() {
        Intent driver_create_ride = new Intent(this, Driver_Create_Ride.class);
        startActivity(driver_create_ride);
    }

    public void getAllRides(){
        allRides = new ArrayList<>();
        Amplify.API.query(ModelQuery.list(Ride.class),
                success->{
            for (Ride ride : success.getData()){
                allRides.add(ride);
            }
                },
                error->{runOnUiThread(()->{
                    @SuppressLint("ShowToast") Toast toast = Toast.makeText(this, "No available rides for now, Try again later", Toast.LENGTH_LONG);
                    toast.show();
                });
                    Log.i("getAllRides", "======> Error in getting all rides"+ error.getMessage());
        });
    }

    public void getUserID() {
        String email = Amplify.Auth.getCurrentUser().getUsername();
        Amplify.API.query(ModelQuery.list(AppUser.class, AppUser.USER_EMAIL.eq(email)),
                success -> {

                    for(AppUser user : success.getData().getItems()){
                        userId = user.getId();
                        break;
                    }
                },
                error -> {
                    Log.i("getUserID", "Error in getting user id");
                });
    }
}