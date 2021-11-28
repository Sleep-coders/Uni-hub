package com.example.uni_hub.ui.carpool.rider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.uni_hub.R;

public class Rider_home extends AppCompatActivity {

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
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
    }
}