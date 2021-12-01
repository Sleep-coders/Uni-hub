package com.example.uni_hub.ui.carpool.rider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.uni_hub.MainActivity;
import com.example.uni_hub.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Rider_home extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView;
    String availableRides[], ridesDescription[], ridesDescription2[];
    int images[] = {R.drawable.car1,R.drawable.car2,R.drawable.car3,R.drawable.car4};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider_home);

        availableRides = getResources().getStringArray(R.array.Available_Rides);
        ridesDescription = getResources().getStringArray(R.array.rides_description);
        ridesDescription2 = getResources().getStringArray(R.array.rides_description2);

        recyclerView = findViewById(R.id.availaberides_recyclerview);

        Rider_home_adapter rider_home_adapter = new Rider_home_adapter(this,availableRides,ridesDescription,ridesDescription2,images);

        recyclerView.setAdapter(rider_home_adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

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
    }
}