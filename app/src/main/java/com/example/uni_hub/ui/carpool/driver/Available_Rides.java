package com.example.uni_hub.ui.carpool.driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.uni_hub.MainActivity;
import com.example.uni_hub.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Available_Rides extends AppCompatActivity {
    RecyclerView recycler_view_driver_post;

    String s1[],s2[];
    int images[] ={R.drawable.car,R.drawable.car,R.drawable.car,R.drawable.car};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BottomNavigationView bottomNavigationView;
        getSupportActionBar().hide();
        setContentView(R.layout.activity_available_rides);

        recycler_view_driver_post = findViewById(R.id.recycler_view_driver_post);
        s1 = getResources().getStringArray(R.array.driver_name_cards);
        s2 = getResources().getStringArray(R.array.rout_path);

        DriverAdapter driverAdapter = new DriverAdapter(this, s1,s2,images);
        recycler_view_driver_post.setAdapter(driverAdapter);
        recycler_view_driver_post.setLayoutManager(new LinearLayoutManager(this));

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
    }
}