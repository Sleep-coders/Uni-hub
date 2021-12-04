package com.example.uni_hub.ui.carpool;
import com.example.uni_hub.MainActivity;
import com.example.uni_hub.databinding.ActivityMainBinding;
import com.example.uni_hub.ui.carpool.driver.AvailableRides;
import com.example.uni_hub.ui.carpool.rider.Rider_home;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.uni_hub.R;

public class Carpool_Home extends AppCompatActivity {
    private ActivityMainBinding binding;
    private Button driver_button;
    private Button rider_button;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carpool_home);

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
        // Rider Role
        rider_button = findViewById(R.id.rider_button);
        rider_button.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), Rider_home.class));
        });

        // Driver Role
        driver_button = (Button) findViewById(R.id.driver_button);
        driver_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                getDriverActivity();
            }
        });
    }
    // Driver Role
    public void getDriverActivity(){
        Intent driver_home = new Intent(this, AvailableRides.class);
        startActivity(driver_home);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}