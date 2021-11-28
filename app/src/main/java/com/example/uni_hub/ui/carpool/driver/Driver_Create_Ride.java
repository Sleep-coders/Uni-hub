package com.example.uni_hub.ui.carpool.driver;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
    EditText chooseDate;
    DatePickerDialog.OnDateSetListener datePickerDialog;
    TimePickerDialog timePickerDialog1;
    TimePickerDialog timePickerDialog2;
    Calendar calender;
    int currentHour;
    int currentMinute;
    String amPm;

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

        // Select Date
        chooseDate = (EditText) findViewById(R.id.add_date_time);
        chooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendarDate = Calendar.getInstance();
                int year = calendarDate.get(Calendar.YEAR);
                int month = calendarDate.get(Calendar.MONTH);
                int day = calendarDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Driver_Create_Ride.this,android.R.style.Theme_Holo_Light_Dialog_MinWidth,datePickerDialog,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        // Date
        datePickerDialog = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month +1;
                Log.d(TAG,"onDateSet: date:"+ day + "/"+ month + "/" + year );
                String date = day + "/"+ month + "/" + year;
                chooseDate.setText(date);
            }
        };

        // Departure Time
        chooseDepartureTime = findViewById(R.id.departure_time_driver);
        chooseDepartureTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                calender = Calendar.getInstance();
                currentHour = calender.get(Calendar.HOUR_OF_DAY);
                currentMinute = calender.get(Calendar.MINUTE);
                timePickerDialog1 = new TimePickerDialog(Driver_Create_Ride.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if(hourOfDay>= 12){
                            amPm = "PM";}else{
                            amPm = "AM";
                        }
                        chooseDepartureTime.setText(hourOfDay+ ":" + minutes +" "+ amPm);

                    }
                }, currentHour, currentMinute, false);
                timePickerDialog1.show();
            }
        });

        // Ride Expires Time
        riderExpiresAt = findViewById(R.id.ride_expires_at);
        riderExpiresAt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view2) {
                calender = Calendar.getInstance();
                currentHour = calender.get(Calendar.HOUR_OF_DAY);
                currentMinute = calender.get(Calendar.MINUTE);
                timePickerDialog2 = new TimePickerDialog(Driver_Create_Ride.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        riderExpiresAt.setText(hourOfDay+ ":" + minutes +" "+ amPm);

                    }
                }, currentHour, currentMinute, false);
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