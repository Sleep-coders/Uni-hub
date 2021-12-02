package com.example.uni_hub.ui.carpool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uni_hub.services.HttpRequester;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.squareup.picasso.Picasso;
import com.example.uni_hub.R;

import java.io.IOException;
import java.util.List;

public class BookRideActivity extends AppCompatActivity implements OnMapReadyCallback {


    ImageView carImage;
    TextView driverName;
    TextView rideDepartureDateTime;
    TextView rideAvailableSeats;
    TextView rideCost;
    TextView rideCarInfo;
    TextView rideExpAt;
    TextView rideDescription;

    List<LatLng> pathPoints;

    private String startLatitude;
    private String startLongitude;
    private String endLatitude;
    private String endLongitude;

    private double startLat;
    private double startLon;

    Handler pointsH ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_ride);

        Intent intent = getIntent();

        carImage = findViewById(R.id.ride_car_img);
        driverName = findViewById(R.id.ride_driver_name);
        rideDepartureDateTime = findViewById(R.id.ride_departure_date_time);
        rideAvailableSeats = findViewById(R.id.ride_avalable_seats);
        rideCost = findViewById(R.id.ride_cost);
        rideCarInfo = findViewById(R.id.ride_car_info);
        rideExpAt = findViewById(R.id.ride_exp_at);
        rideDescription = findViewById(R.id.ride_description);

        String path = intent.getStringExtra("rideRoute");
        String[] paths = path.split(":");
        String[] start = paths[0].split(",");
        String[] end = paths[1].split(",");

        startLatitude = start[0];
        startLongitude = start[1];
        endLatitude = end[0];
        endLongitude = end[1];

        startLat = Double.parseDouble(start[0]);
        startLon = Double.parseDouble(start[1]);



        pointsH = new Handler(Looper.getMainLooper(),message -> {
            Log.i("All _ Points", "======> |||||||||||||||||////////////////"+ pathPoints);
            return false;
        });

        getPoints();

        loadActivityData(intent);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_book);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        findViewById(R.id.ride_contact_driver).setOnClickListener(view -> {

        });
    }

    private void getPoints() {
        String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + startLatitude + "," + startLongitude + "&destination=" + endLatitude + "," + endLongitude + "&key=AIzaSyAh_BlQF3Zdf3_O4vJUuNwmkVKQEhmIq90";
        HttpRequester requester = new HttpRequester();
        try {
            pathPoints =  requester.run(url);
            pointsH.sendEmptyMessage(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void loadActivityData(Intent intent) {
        String imageUrl = intent.getStringExtra("carImage");
        String _driverName = intent.getStringExtra("ownerName");
        String departureDateTime = intent.getStringExtra("rideDepartureTime")
                + " : " + intent.getStringExtra("rideDate");
        String availableSeats = Integer.toString(intent.getIntExtra("availableSeats", 4));
        String _rideCost = Float.toString(intent.getFloatExtra("cost", 0.0f));
        String carInfo = intent.getStringExtra("carInfo");
        String _rideExpAt = intent.getStringExtra("rideExpAt");
        String _rideDescription = intent.getStringExtra("rideDescription");

        Picasso.get().load(imageUrl).into(carImage);
        driverName.setText(_driverName);
        rideDepartureDateTime.setText(departureDateTime);
        rideAvailableSeats.setText(availableSeats);
        rideCost.setText(_rideCost);
        rideCarInfo.setText(carInfo);
        rideExpAt.setText(_rideExpAt);
        rideDescription.setText(_rideDescription);

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        LatLng latLng = new LatLng(startLat, startLon);

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
        googleMap.animateCamera(cameraUpdate);
        googleMap.addMarker(
                new MarkerOptions()
                        .position(latLng)
                        .title("Start Point")
        );
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setTrafficEnabled(true);
        googleMap.setBuildingsEnabled(true); // dosent seem to work in jordan but should in other countries
        LatLng[] points = (LatLng[]) pathPoints.toArray();
        googleMap.addPolygon(new PolygonOptions().add(points));
    }
}