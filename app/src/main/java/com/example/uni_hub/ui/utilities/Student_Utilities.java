package com.example.uni_hub.ui.utilities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.AlarmClock;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.uni_hub.MainActivity;
import com.example.uni_hub.R;
import com.example.uni_hub.databinding.ActivityDriverHomeBinding;
import com.example.uni_hub.databinding.ActivityMainBinding;
import com.example.uni_hub.ui.carpool.rider.Rider_home;
import com.example.uni_hub.ui.setting.SettingFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;

public class Student_Utilities extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityDriverHomeBinding binding;

    private Button alarmclock_btn;
    private Button calender_btn;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_utilities);

        BottomNavigationView bottomNavigationView;

        // Alarm Clock
        alarmclock_btn = findViewById(R.id.btn_alarmclock);
        alarmclock_btn.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), AlarmClock_Utility.class));
        });

        // Calender
        calender_btn = findViewById(R.id.btn_calender);
        calender_btn.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), Calendar_Activity.class));
        });

        Intent intent3=new Intent(this, services.class);
        Button btn3= findViewById(R.id.btn_music_player);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent3);
            }
        });

        // Navbar Bottom
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:

                    case R.id.about:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }
}