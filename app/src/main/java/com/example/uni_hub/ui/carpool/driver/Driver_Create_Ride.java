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
import android.view.View;
import android.widget.Button;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import com.google.android.gms.location.FusedLocationProviderClient;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Driver_Create_Ride extends AppCompatActivity implements OnMapReadyCallback {

    EditText chooseDepartureTime;
    EditText riderExpiresAt;
    EditText chooseDate;
    EditText routDescriptionText;
    TextView carNotFound;

    private static final int PERMISSION_ID = 44;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    View showMapView;

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
    Handler carHandler;

    TextView cost_per_passenger_updating;
    ProgressBar progressBar_cost;
    SeekBar seekBar_cost;

    private FusedLocationProviderClient mFusedLocationClient;

    private double latitude;
    private double longitude;
    int destCounter;
    private double destinationStartLatitude;
    private double destinationStartLongitude;
    private double destinationEndLatitude;
    private double destinationEndLongitude;

    Thread thread1;


    @SuppressLint("InflateParams")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button submit_ride;
        BottomNavigationView bottomNavigationView;
        setContentView(R.layout.activity_driver_create_ride);

        destCounter = 0;
        getUserID();

        Toast toast = Toast.makeText(this, "Something Went Wrong, Check Inputs And Try Again", Toast.LENGTH_LONG);
        rideErrHandler = new Handler(Looper.getMainLooper(), message -> {
            toast.show();
            return false;
        });
        carHandler = new Handler(Looper.getMainLooper(), message -> {
            Log.i("Hallo", "======> |||||||||||||////////////////");

            return false;
        });

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        findViewById(R.id.location_showMap_Btn).setOnClickListener(view -> {
            showMapDialog();
        });


        carNotFound = findViewById(R.id.user_car_notFound);
        carNotFound.setVisibility(View.INVISIBLE);


        // Select Date
        chooseDate = findViewById(R.id.add_date_time);
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
        datePickerDialog = (datePicker, year, month, day) -> {
            month = month + 1;
            Log.d(TAG, "onDateSet: date:" + day + "/" + month + "/" + year);
            String date = day + "/" + month + "/" + year;
            chooseDate.setText(date);
        };

        // Departure Time
        chooseDepartureTime = findViewById(R.id.departure_time_driver);
        chooseDepartureTime.setOnClickListener(view1 -> {
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
        });

        // Ride Expires Time
        riderExpiresAt = findViewById(R.id.ride_expires_at);
        riderExpiresAt.setOnClickListener(view2 -> {
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
        });

        cost_per_passenger_updating = findViewById(R.id.cost_per_passenger_updating);
        progressBar_cost = findViewById(R.id.progress_bar_for_cost);
        seekBar_cost = findViewById(R.id.cost_per_passenger);
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

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
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

        routDescriptionText = findViewById(R.id.route_description);
        submit_ride = findViewById(R.id.submit_ride);
        submit_ride.setOnClickListener(view -> {
            getUserCarInfo();
            thread1 = new Thread(() -> {
                if (hasCar) {

                    StringBuilder path = new StringBuilder();
                    path.append(destinationStartLatitude);
                    path.append(",");
                    path.append(destinationStartLongitude);
                    path.append(":");
                    path.append(destinationEndLatitude);
                    path.append(",");
                    path.append(destinationEndLongitude);
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
                            .rideRoute(path.toString())
                            .appUserRidesId(userId)
                            .build();

                    Amplify.API.mutate(ModelMutation.create(ride),
                            success -> {
                                startActivity(new Intent(getApplicationContext(), AvailableRides.class));
                            },
                            error -> {
                                rideErrHandler.sendEmptyMessage(0);
                            });
                } else {
                    runOnUiThread(()->{
                        carNotFound.setVisibility(View.VISIBLE);
                    });
                }
            });

        });
    }


    public void getUserID() {
        String email = Amplify.Auth.getCurrentUser().getUsername();
        Log.i("HHHHHHHHHHHHHHHH", "======> ||||||||||||||||||||||||||||||////////////////" + email);
        Amplify.API.query(ModelQuery.list(AppUser.class, AppUser.USER_EMAIL.eq(email)),
                success -> {
                    for (AppUser user : success.getData().getItems()) {
                        userId = user.getId();
                        userName = user.getUserNickname();
                        break;
                    }
                    Log.i("XXXXXXXXXXXXXXXXX", "XXXXXXXXX||||||X|X||X|X||XX|" + userId + userName);
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
                        carHandler.sendEmptyMessage(0);
                    }
                    if (cars.size() >= 1) {
                        hasCar = true;
                    }
                    thread1.run();
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


        googleMapL.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(@NonNull LatLng latLng) {
                if (destCounter < 1) {
                    googleMapL.addMarker(new MarkerOptions().position(latLng)
                            .title("Start Location").snippet("saba7 fakhry"));
                    destinationStartLatitude = latLng.latitude;
                    destinationStartLongitude = latLng.longitude;
                    destCounter++;
                    Log.i("XXXXyyyyzzzzz", "XXXXXXXXXyyyyyyyyyzzzzzzzz+++++>>>>" + destinationStartLatitude + destinationStartLongitude + destCounter);
                }
                if (destCounter <= 2) {
                    googleMapL.addMarker(new MarkerOptions().position(latLng)
                            .title("destination Location").snippet("saba7 fakhry"));

                    destinationEndLatitude = latLng.latitude;
                    destinationEndLongitude = latLng.longitude;
                    destCounter++;
                    Log.i("aaaapppppcccc", "AAAAAAAAABBBBBBBBCCCCCC+++++>>>" + destinationEndLatitude + destinationEndLongitude + destCounter);

                }


            }
        });
    }


    // DIALOG BOX
    @SuppressLint("InflateParams")
    public void showMapDialog() {

        dialogBuilder = new AlertDialog.Builder(this);
        showMapView = getLayoutInflater().inflate(R.layout.geograph_path_create_ride, null);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        getLastLocation();

        Button saveLocationBtn = showMapView.findViewById(R.id.save_route_path);
        Button cancelLocationBtn = showMapView.findViewById(R.id.close_map);

        dialogBuilder.setView(showMapView);
        dialog = dialogBuilder.create();
        dialog.show();

        cancelLocationBtn.setOnClickListener(view -> {
            dialog.dismiss();
        });


        saveLocationBtn.setOnClickListener(view -> {
            @SuppressLint("ShowToast") Toast toast = Toast.makeText(this, "Coordinates are saved", Toast.LENGTH_LONG);
            toast.show();
        });
    }


    // PERMISSIONS
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

}