package com.example.uni_hub.ui.carpool.driver;

import android.content.Intent;
import android.os.Bundle;

import com.example.uni_hub.MainActivity;
import com.example.uni_hub.databinding.ActivityDriverHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.navigation.ui.AppBarConfiguration;

import com.example.uni_hub.R;

public class Driver_home extends AppCompatActivity {
    private Button create_ride;
    private AppBarConfiguration appBarConfiguration;
    private ActivityDriverHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BottomNavigationView bottomNavigationView;

        binding = ActivityDriverHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        getSupportActionBar().hide();

        // Navbar Bottom
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        // Create a Ride Button
        create_ride = findViewById(R.id.create_ride);
        create_ride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                getCreateRide();
            }
        });
    }

    public void getCreateRide(){
        Intent driver_create_ride = new Intent(this,Driver_Create_Ride.class);
        startActivity(driver_create_ride);
    }
}