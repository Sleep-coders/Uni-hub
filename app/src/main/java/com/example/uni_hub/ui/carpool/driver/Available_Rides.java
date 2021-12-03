package com.example.uni_hub.ui.carpool.driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.AppUser;
import com.amplifyframework.datastore.generated.model.Ride;
import com.example.uni_hub.MainActivity;
import com.example.uni_hub.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Available_Rides extends AppCompatActivity {
    RecyclerView recycler_view_driver_post;


    private Button create_ride;
    private ImageView car_img;
    private TextView owner_name;
    private TextView ride_departure_time;
    private TextView cost;
    private TextView available_seats;
    private TextView car_Info;
    private TextView ride_expires_at;
    private TextView ride_description;
    private TextView ride_date;

    Handler allRidesHandler;
    Handler userIdHandler;

    private Ride owner_ride;


    List<Ride> allRides = new ArrayList<>();
    String userId;

    @SuppressLint({"NotifyDataSetChanged", "SetTextI18n"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BottomNavigationView bottomNavigationView;
        setContentView(R.layout.activity_available_rides);
        getUserID();

        recycler_view_driver_post = findViewById(R.id.recycler_view_driver_post);
        userIdHandler=new Handler(Looper.getMainLooper(),message -> {
            userId = message.getData().getString("ID");
            Log.i("USERID_InHandler++++>>>>", "++++++>>>>>>>" + userId);
            getAllRides();
            return false;
        });
        allRidesHandler = new Handler(Looper.getMainLooper(), message -> {
            Log.i("ALL_RIDES>>>>>||||||||++++>>>>", "++++++>>>>>>>" + allRides);
            getOwnerRide();
            recycler_view_driver_post.setLayoutManager(new LinearLayoutManager(this));
            recycler_view_driver_post.setAdapter(new DriverAdapter(allRides, userId, getApplicationContext()));
            Objects.requireNonNull(recycler_view_driver_post.getAdapter()).notifyDataSetChanged();
            return false;
        });

        recycler_view_driver_post.setLayoutManager(new LinearLayoutManager(this));
        recycler_view_driver_post.setAdapter(new DriverAdapter(allRides, userId, getApplicationContext()));
        Objects.requireNonNull(recycler_view_driver_post.getAdapter()).notifyDataSetChanged();




//        DriverAdapter driverAdapter = new DriverAdapter(allRides , userId);



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

    private void getOwnerRide() {
        for (Ride ride : allRides) {
            if (ride.getOwnerId().equals(userId)) {
                owner_ride = ride;
                Log.i("owner_ride>>>>>||||||||++++>>>>", "++++++>>>>>>>" + ride.getOwnerId() + " aanndd===>>" + userId);
            }

        }
    }

//    private void setDriverData() {
//        car_img = findViewById(R.id.car_image);
//        owner_name = findViewById(R.id.driver_name);
//        ride_departure_time = findViewById(R.id.departure_time_post);
//        cost = findViewById(R.id.cost_details);
//        available_seats = findViewById(R.id.available_seats);
//        car_Info = findViewById(R.id.car_Info);
//        ride_expires_at = findViewById(R.id.ride_expires_at_owner_ride);
//        ride_description = findViewById(R.id.ride_description);
//        ride_date = findViewById(R.id.ride_date);
//
//        Picasso.get().load(owner_ride.getCarInfo()).into(car_img);
//        owner_name.setText(owner_ride.getOwnerName());
//        ride_departure_time.setText(owner_ride.getRideDepartureTime());
//        cost.setText(owner_ride.getCost().toString());
//        available_seats.setText(owner_ride.getAvailableSeats().toString());
//        car_Info.setText(owner_ride.getCarImage());
//        ride_expires_at.setText(owner_ride.getRideExpiresAt());
//        ride_description.setText(owner_ride.getRideDescription());
//        ride_date.setText(owner_ride.getRideDate());
//    }

    public void getCreateRide() {
        Intent driver_create_ride = new Intent(this, Driver_Create_Ride.class);
        startActivity(driver_create_ride);
    }

    public void getAllRides() {
        allRides = new ArrayList<>();
        Amplify.API.query(ModelQuery.list(Ride.class),
                success -> {
                    for (Ride ride : success.getData()) {
                        allRides.add(ride);
                    }
                    allRidesHandler.sendEmptyMessage(0);

//                    Log.i("ALLRIDES++++>>>>", "++++++>>>>>>>"+success.getData());
//                    Log.i("ALLRIDES_STATEC++++>>>>", "++++++>>>>>>>"+allRides);
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
//                    Log.i("USERID++++>>>>", "++++++>>>>>>>" + userId);
                },
                error -> {
                    Log.i("getUserID", "Error in getting user id");
                });
    }
}