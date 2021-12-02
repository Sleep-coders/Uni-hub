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

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.uni_hub.services.HttpRequester;
import com.example.uni_hub.services.Root;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.example.uni_hub.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.Headers;


public class BookRideActivity extends AppCompatActivity  {

//implements OnMapReadyCallback
    public static final String TAG = "BookRideActivity";
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

    Gson gson ;


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

//        getPoints();

        loadActivityData(intent);

//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map_book);
//        assert mapFragment != null;
//        mapFragment.getMapAsync(this);
//        findViewById(R.id.ride_contact_driver).setOnClickListener(view -> {
//
//        });
    }

    private void getPoints() {
//        String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + startLatitude + "," + startLongitude + "&destination=" + endLatitude + "," + endLongitude + "&key=AIzaSyAh_BlQF3Zdf3_O4vJUuNwmkVKQEhmIq90";
//
////        HttpRequester requester = new HttpRequester();
////        try {
////            pathPoints =  requester.run(url);
////            pointsH.sendEmptyMessage(0);
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
//
//        AsyncHttpClient client = new AsyncHttpClient();
//        RequestParams params = new RequestParams();
//        params.put("q", "android");
//        params.put("rsz", "8");
//        client.get(url, params, new JsonHttpResponseHandler() {
//
//            @Override
//            public void onSuccess(int statusCode, Headers headers, JSON json) {
//                JSONArray steps= new ArrayList<>();
//                List<LatLng> latLngs = new ArrayList<>();
//                Log.i(TAG, "onSuccess: =========>" + json.toString());
//                JSONObject jsonObject = json.jsonObject;
//                try {
//                    JSONArray routes = jsonObject.getJSONArray("routes");
//                    Log.i(TAG, "onSuccess: =========> " + routes.toString());
//                    JSONArray legs = routes.getJSONObject(0).getJSONArray("legs");
//                    steps = legs.getJSONObject(0).getJSONArray("steps");
////                    List<JSONObject> js = new ArrayList<>(steps);
//                    for (JSONObject step : steps.){
//                        latLngs.add(new LatLng(step.start_location.lat,step.start_location.lng));
//                        latLngs.add(new LatLng(step.end_location.lat,step.end_location.lng));
//                    }
//                    Log.i(TAG, "legs: =========> " + legs);
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                Root root = gson.fromJson(json.toString(),Root.class);
//                List<Root.Step> steps = root.routes.get(0).legs.get(0).steps;
//                for (Root.Step step : steps){
//                    latLngs.add(new LatLng(step.start_location.lat,step.start_location.lng));
//                    latLngs.add(new LatLng(step.end_location.lat,step.end_location.lng));
//                }
//                Log.i("All _ Points", "======> |||||||||||||||||////////////////"+ json.toString());
//                pathPoints = latLngs;
//            }
//
//            @Override
//            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
//
//            }
//        });

    }

    private void loadActivityData(Intent intent) {
        String imageUrl = intent.getStringExtra("carImage");
        String _driverName = intent.getStringExtra("ownerName");
        String departureDateTime = intent.getStringExtra("rideDepartureTime")
                + " : " + intent.getStringExtra("rideDate");
        String availableSeats = Integer.toString(intent.getIntExtra("availableSeats", 4));
        String _rideCost = Double.toString(intent.getDoubleExtra("cost", 0.0d));
        String carInfo = intent.getStringExtra("carInfo");
        String _rideExpAt = intent.getStringExtra("rideExpAt");
        String _rideDescription = intent.getStringExtra("rideDescription");

        Picasso.get().load(carInfo).into(carImage);
        driverName.setText(_driverName);
        rideDepartureDateTime.setText(departureDateTime);
        rideAvailableSeats.setText(availableSeats);
        rideCost.setText(_rideCost);
        rideCarInfo.setText(imageUrl);
        rideExpAt.setText(_rideExpAt);
        rideDescription.setText(_rideDescription);

    }

//    @Override
//    public void onMapReady(@NonNull GoogleMap googleMap) {
//        LatLng latLng = new LatLng(startLat, startLon);
//
//        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
//        googleMap.animateCamera(cameraUpdate);
//        googleMap.addMarker(
//                new MarkerOptions()
//                        .position(latLng)
//                        .title("Start Point")
//        );
//        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//        googleMap.setTrafficEnabled(true);
//        googleMap.setBuildingsEnabled(true); // dosent seem to work in jordan but should in other countries
////        LatLng[] points = (LatLng[]) pathPoints.toArray();
////        googleMap.addPolygon(new PolygonOptions().add(points));
//    }
}