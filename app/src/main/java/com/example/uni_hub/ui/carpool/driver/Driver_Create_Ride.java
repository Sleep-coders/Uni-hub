package com.example.uni_hub.ui.carpool.driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.uni_hub.MainActivity;
import com.example.uni_hub.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

import java.util.Calendar;

public class Driver_Create_Ride extends AppCompatActivity {
    EditText chooseDepartureTime;
    EditText riderExpiresAt;
    TimePickerDialog timePickerDialog1;
    TimePickerDialog timePickerDialog2;
    Calendar calender1;
    int currentHour1;
    int currentMinute1;
    String amPm1;

    Calendar calender2;
    int currentHour2;
    int currentMinute2;
    String amPm2;

    TextView cost_per_passenger_updating;
    ProgressBar progressBar_cost;
    SeekBar seekBar_cost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button submit_ride;
        BottomNavigationView bottomNavigationView;
        getSupportActionBar().hide();
        setContentView(R.layout.activity_driver_create_ride);

        // Departure Time
        chooseDepartureTime = findViewById(R.id.departure_time_driver);
        chooseDepartureTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                calender1 = Calendar.getInstance();
                currentHour1 = calender1.get(Calendar.HOUR_OF_DAY);
                currentMinute1 = calender1.get(Calendar.MINUTE);
                timePickerDialog1 = new TimePickerDialog(Driver_Create_Ride.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if(hourOfDay>= 12){
                            amPm1 = "PM";}else{
                            amPm1 = "AM";
                        }
                        chooseDepartureTime.setText(hourOfDay+ ":" + minutes +" "+ amPm1);

                    }
                }, currentHour1, currentMinute1, false);
                timePickerDialog1.show();
            }
        });

        // Arrival Time
        riderExpiresAt = findViewById(R.id.ride_expires_at);
        riderExpiresAt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                calender2 = Calendar.getInstance();
                currentHour2 = calender2.get(Calendar.HOUR_OF_DAY);
                currentMinute2 = calender2.get(Calendar.MINUTE);
                timePickerDialog2 = new TimePickerDialog(Driver_Create_Ride.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if(hourOfDay>= 12){
                            amPm2 = "PM";}else{
                            amPm2 = "AM";
                        }
                        riderExpiresAt.setText(hourOfDay+ ":" + minutes +" "+ amPm2);

                    }
                }, currentHour2, currentMinute2, false);
                timePickerDialog2.show();
            }
        });

        cost_per_passenger_updating = (TextView) findViewById(R.id.cost_per_passenger_updating);
        progressBar_cost = (ProgressBar) findViewById(R.id.progress_bar_for_cost);
        seekBar_cost = (SeekBar) findViewById(R.id.cost_per_passenger);
        seekBar_cost.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                progressBar_cost.setProgress(progress);
                cost_per_passenger_updating.setText(""+progress+" JD");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

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

        submit_ride = findViewById(R.id.submit_ride);
        submit_ride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSumbitRide();
            }
        });
    }

    public void getSumbitRide(){
        Intent intent_sumbit_ride = new Intent(this, Available_Rides.class);
        startActivity(intent_sumbit_ride);
    }
}