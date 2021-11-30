package com.example.uni_hub.ui.carpool.driver;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
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
import android.widget.Toast;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.AppUser;
import com.amplifyframework.datastore.generated.model.Car;
import com.amplifyframework.datastore.generated.model.Ride;
import com.example.uni_hub.MainActivity;
import com.example.uni_hub.R;
import com.example.uni_hub.services.HttpRequester;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.google.android.gms.location.FusedLocationProviderClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Driver_Create_Ride extends AppCompatActivity implements OnMapReadyCallback {
    EditText chooseDepartureTime;
    EditText riderExpiresAt;
    EditText chooseDate;
    EditText routDescriptionText;
    TextView carNotFound;

    private static final int PERMISSION_ID = 44;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    DatePickerDialog.OnDateSetListener datePickerDialog;
    TimePickerDialog timePickerDialog1;
    TimePickerDialog timePickerDialog2;
    Calendar calender;

    boolean hasCar = false;
    List<Car> cars = new ArrayList<>();
    List<LatLng> points;

    GoogleMap googleMapL;

    int currentHour;
    int currentMinute;
    String amPm;
    double cost;
    int seatNum;

    String userId;
    String userName;

    Handler rideErrHandler;

    TextView cost_per_passenger_updating;
    ProgressBar progressBar_cost;
    SeekBar seekBar_cost;

    private FusedLocationProviderClient mFusedLocationClient;

    private double latitude;
    private double longitude;
    private double destinationLatitude;
    private double destinationLongitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button submit_ride;
        BottomNavigationView bottomNavigationView;
        getSupportActionBar().hide();
        setContentView(R.layout.activity_driver_create_ride);

        getUserID();

        Toast toast = Toast.makeText(this, "Something Went Wrong, Check Inputs And Try Again", Toast.LENGTH_LONG);
        rideErrHandler = new Handler(Looper.getMainLooper(), message -> {
            toast.show();
            return false;
        });

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        findViewById(R.id.location_showMap_Btn).setOnClickListener(view -> {
            showMapDialog();
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);


        carNotFound = findViewById(R.id.user_car_notFound);
        carNotFound.setVisibility(View.INVISIBLE);


        // Select Date
        chooseDate = (EditText) findViewById(R.id.add_date_time);
        chooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendarDate = Calendar.getInstance();
                int year = calendarDate.get(Calendar.YEAR);
                int month = calendarDate.get(Calendar.MONTH);
                int day = calendarDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(Driver_Create_Ride.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, datePickerDialog, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        // Date
        datePickerDialog = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: date:" + day + "/" + month + "/" + year);
                String date = day + "/" + month + "/" + year;
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
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        chooseDepartureTime.setText(hourOfDay + ":" + minutes);

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
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        riderExpiresAt.setText(hourOfDay + ":" + minutes + " " + amPm);

                    }
                }, currentHour, currentMinute, false);
                timePickerDialog2.show();
            }
        });

        cost_per_passenger_updating = (TextView) findViewById(R.id.cost_per_passenger_updating);
        progressBar_cost = (ProgressBar) findViewById(R.id.progress_bar_for_cost);
        seekBar_cost = (SeekBar) findViewById(R.id.cost_per_passenger);
        seekBar_cost.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                progressBar_cost.setProgress(progress);
                cost_per_passenger_updating.setText("" + progress + " JD");
                cost = progress;
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
                switch (item.getItemId()) {
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

        TextView passengerNumberText = findViewById(R.id.Passenger_Num);
        SeekBar seekBar_PassengerNum = findViewById(R.id.seekBar_Num_Of_Passenger);
        seekBar_PassengerNum.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                passengerNumberText.setText(i + " Seat");
                seatNum = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        submit_ride = findViewById(R.id.submit_ride);
        submit_ride.setOnClickListener(view -> {
            if (getUserCarInfo()) {
//                List<String> coordinates = new ArrayList<>();
//                String currentLatLon = Double.toString(latitude)+
//                coordinates.add()
                String departureTime = chooseDepartureTime.getText().toString();
                String expiresAt = riderExpiresAt.getText().toString();
                String chooseRideDate = chooseDate.getText().toString();
                String routeDescription = routDescriptionText.getText().toString();
                Ride ride = Ride.builder()
                        .ownerName(userName)
                        .ownerId(userId)
                        .rideDepartureTime(departureTime)
                        .availableSeats(seatNum)
                        .cost(cost)
                        .carImage(cars.get(0).getCarImg())
                        .carInfo(cars.get(0).getCarModel())
                        .rideExpiresAt(expiresAt)
                        .rideDate(chooseRideDate).rideDescription(routeDescription)
//                        .rideRoute()
//                        .appUserRidesId(userId)
                        .build();

                Amplify.API.mutate(ModelMutation.create(ride),
                        success -> {
                            startActivity(new Intent(getApplicationContext(), Available_Rides.class));
                        },
                        error -> {
                            rideErrHandler.sendEmptyMessage(0);
                        });
            } else carNotFound.setVisibility(View.VISIBLE);

        });
    }


    public void getUserID() {
        String email = Amplify.Auth.getCurrentUser().getUsername();
        Amplify.API.query(ModelQuery.get(AppUser.class, email),
                success -> {
                    userId = success.getData().getId();
                    userName = success.getData().getUserNickname();
                },
                error -> {
                    Log.i("getUserID", "Error in getting user id");
                });
    }

    public boolean getUserCarInfo() {


        Amplify.API.query(ModelQuery.list(Car.class, Car.OWNER_ID.contains(userId)),
                success -> {
                    if (success.getData() != null) {
                        for (Car car : success.getData()) {
                            if (car.getOwnerId().equals(userId))
                                cars.add(car);
                        }
                    }
                    if (cars.size() >= 1) {
                        hasCar = true;
                    }
                },
                error -> {
                    runOnUiThread(() -> {
                        @SuppressLint("ShowToast") Toast toast = Toast.makeText(this, "Something Went Wrong With Fetching Data, Try Again", Toast.LENGTH_LONG);
                        toast.show();
                    });
                    Log.i("getUserID", "======> Error in getting user id" + error.getMessage());
                });
        return hasCar;
    }
////////////////============== Location Functionality ================//////////////////////

    private final LocationCallback mLocationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();
            Log.i(TAG, "The location is => " + mLastLocation);
        }
    };

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        googleMapL = googleMap;


        LatLng[] latLngs = (LatLng[]) points.toArray();
        googleMapL.addPolygon(new PolygonOptions().add(latLngs));
    }


    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        if (checkPermissions()) {

            if (isLocationEnabled()) {

                mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull com.google.android.gms.tasks.Task<Location> task) {

                        Location location = task.getResult();

                        if (location == null) {
                            requestNewLocationData();
                        } else {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            LatLng latLng = new LatLng(latitude, longitude);
                            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
                            googleMapL.animateCamera(cameraUpdate);
                            googleMapL.addMarker(new MarkerOptions()
                                    .position(latLng)
                                    .title("Current Location"));
                            googleMapL.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                            googleMapL.setTrafficEnabled(true);

                            googleMapL.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

                                @Override
                                public void onMapClick(@NonNull LatLng position) {

                                    googleMapL.addMarker(new MarkerOptions().position(position)
                                            .title("destination Location"));
                                    destinationLatitude = position.latitude;
                                    destinationLongitude = position.longitude;


                                }
                            });

                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {
        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5);
        locationRequest.setFastestInterval(0);
        locationRequest.setNumUpdates(10);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this); // this may or may not be needed
        mFusedLocationClient.requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper());
    }

    public void showMapDialog() {


        getLastLocation();
        String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + latitude + "," + longitude + "&destination=" + destinationLatitude + "," + destinationLongitude + "&key=AIzaSyAh_BlQF3Zdf3_O4vJUuNwmkVKQEhmIq90";
        HttpRequester requester = new HttpRequester();
        try {
            points =  requester.run(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        dialogBuilder = new AlertDialog.Builder(this);
        View confirmUser = getLayoutInflater().inflate(R.layout.geograph_path_create_ride, null);

        Button saveLocationBtn = findViewById(R.id.save_route_path);
        Button cancelLocationBtn = findViewById(R.id.cancel_route_path);

        dialogBuilder.setView(confirmUser);
        dialog = dialogBuilder.create();
        dialog.show();

        cancelLocationBtn.setOnClickListener(view -> {
            dialog.dismiss();
        });


        saveLocationBtn.setOnClickListener(view -> {


        });
    }

}