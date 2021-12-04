package com.example.uni_hub.ui.carpool;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.api.graphql.model.ModelQuery;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.AppUser;
import com.directions.route.AbstractRouting;
import com.directions.route.Route;
import com.directions.route.RouteException;
import com.directions.route.Routing;
import com.directions.route.RoutingListener;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//import com.airbnb.lottie.L;
import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

//import com.example.uni_hub.services.DirectionsJSONParser;
//import com.example.uni_hub.services.HttpRequester;
//import com.example.uni_hub.services.MapsActivity;
//import com.example.uni_hub.services.Root;
import com.example.uni_hub.services.Step;
import com.example.uni_hub.ui.carpool.driver.AvailableRides;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;
import com.example.uni_hub.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import java.util.List;


import okhttp3.Headers;


public class BookRideActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener, RoutingListener {


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
    private double endLat;
    private double endLon;
    private List<Polyline> polylines=null;
    private GoogleMap mMap;

    private String phoneNumber;
    private Handler phoneHandler;
    String ownerId;

    Handler pointsH ;

    Gson gson ;
    GsonBuilder gsonBuilder;
    Thread mapThread;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_ride);

        Intent intent = getIntent();
        ownerId = intent.getStringExtra("ownerId");


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
        endLat = Double.parseDouble(end[0]);
        endLon = Double.parseDouble(end[1]);



        phoneHandler = new Handler(Looper.getMainLooper(),msg->{
            phoneNumber = msg.getData().getString("phoneNumber");
            Log.i("PHONENUMBER", "======> |||||||||||||||||==============>"+ phoneNumber);
            return false;
        });


        pointsH = new Handler(Looper.getMainLooper(),message -> {
            Log.i("All _ Points", "======> |||||||||||||||||////////////////"+ pathPoints);
            return false;
        });

        getPoints();

        loadActivityData(intent);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map_book);
        assert mapFragment != null;
        mapThread = new Thread(()->{
            mapFragment.getMapAsync(this);
        });

        getOwnerPhoneNumber(ownerId);



        findViewById(R.id.ride_contact_driver).setOnClickListener(view -> {
            if(phoneNumber.equals("") || phoneNumber == null){
                Toast.makeText(this,"Loading Dial Info .. ",Toast.LENGTH_LONG).show();
            }else {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                phoneNumber = phoneNumber.replaceFirst("\\+962", "0");
                Log.i("TAG _ phoneNumber", "startPhoneIntent: =====> " + phoneNumber );
                dialIntent.setData(Uri.parse("tel:" + phoneNumber));
                startActivity(dialIntent);
            }
        });


        findViewById(R.id.ride_back_btn).setOnClickListener(v -> {
            startActivity(new Intent(this, AvailableRides.class));
        });
    }

    private void getOwnerPhoneNumber(String ownerId) {
        Log.i("OWNER_ID", "======> |||||||||||||||||==============>"+ ownerId);
        Bundle bundle = new Bundle();
        Message message = new Message();
        Amplify.API.query(ModelQuery.list(AppUser.class, AppUser.ID.eq(ownerId)),
                success -> {
                    String  phoneNumber = "";
                    for (AppUser user : success.getData().getItems()) {
                        phoneNumber = user.getUserPhoneNumber();
                        break;
                    }
                    Log.i("IN success", "======> |||||||||||||||||==============>"+ success.getData());
                    Log.i("IN success phoneNumber++++>>>>", "++++++>>>>>>>" + phoneNumber);
                    bundle.putString("phoneNumber",phoneNumber);
                    message.setData(bundle);
                    phoneHandler.sendMessage(message);
                },
                error -> {
                    Log.i("phoneNumber", "Error in getting user phoneNumber");
                });
    }

    private void getPoints() {
        String url = "https://maps.googleapis.com/maps/api/directions/json?origin=" + startLatitude + "," + startLongitude + "&destination=" + endLatitude + "," + endLongitude + "&key=AIzaSyAh_BlQF3Zdf3_O4vJUuNwmkVKQEhmIq90";


        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("q", "android");
        params.put("rsz", "8");
        client.get(url, params, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                gsonBuilder = new GsonBuilder();
                gson = gsonBuilder.create();
                List<Step> stepsArr = new ArrayList<>();
                final Type STEP_TYPE = new TypeToken<List<Step>>() {
                }.getType();
                List<LatLng> latLngs = new ArrayList<>();
                Step step;
                JSONObject jsonObject = json.jsonObject;


                try {
                    JSONArray routes = jsonObject.getJSONArray("routes");
                    Log.i("TAG", "onSuccess: =========> " + routes.toString());
                    JSONArray legs = routes.getJSONObject(0).getJSONArray("legs");
                    JSONArray steps = legs.getJSONObject(0).getJSONArray("steps");

                    stepsArr= gson.fromJson(steps.toString(),STEP_TYPE);



                    Log.i("stepsArr", "stepsArr: =========> " + stepsArr.toString());
                    Log.i("stepsArr", "stepsArr: =========> " + steps);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                List<Double> lats = new ArrayList<>();
                List<Double> lons = new ArrayList<>();
                for (Step step1 : stepsArr) {

                    lats.add(step1.start_location.lat);
                    lats.add(step1.end_location.lat);
                    lons.add(step1.start_location.lng);
                    lons.add(step1.end_location.lng);

                }

                for (int i=0;i<lats.size();i=i+2){
                    latLngs.add(new LatLng(lats.get(i),lons.get(i)));
                    latLngs.add(new LatLng(lats.get(i+1),lons.get(i+1)));
                }

                Log.i("All _ Points", "======> |||||||||||||||||////////////////" + json.toString());
                Log.i("All _ Points", "======> " + startLatitude + "  " + startLongitude + "  "  + endLatitude + "  " + endLongitude);
                pathPoints = latLngs;
                mapThread.run();
            }

            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {

            }

        });
    }
    private void loadActivityData (Intent intent){
        String imageUrl = intent.getStringExtra("carImage");
        String _driverName = intent.getStringExtra("ownerName");
        String departureDateTime = intent.getStringExtra("rideDepartureTime")
                + " : " + intent.getStringExtra("rideDate");
        String availableSeats = Integer.toString(intent.getIntExtra("availableSeats", 4));
        String _rideCost = Double.toString(intent.getDoubleExtra("cost", 0.0d));
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
        this.mMap = googleMap;
        LatLng startLatLng = new LatLng(startLat, startLon);
        LatLng endLatLng = new LatLng(endLat,endLon);


        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(startLatLng, 15);
        googleMap.animateCamera(cameraUpdate);
        googleMap.addMarker(
                new MarkerOptions()
                        .position(startLatLng)
                        .title("Start Point")
        );
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setTrafficEnabled(true);

        FindRoutes(startLatLng, endLatLng);

    }

    public void FindRoutes(LatLng Start, LatLng End)
    {
        if(Start==null || End==null) {
            Toast.makeText(this,"Unable to get location", Toast.LENGTH_LONG).show();
        }
        else
        {

            Routing routing = new Routing.Builder()
                    .travelMode(AbstractRouting.TravelMode.DRIVING)
                    .withListener(this)
                    .alternativeRoutes(true)
                    .waypoints(Start, End)
                    .key("AIzaSyAh_BlQF3Zdf3_O4vJUuNwmkVKQEhmIq90")  //also define your api key here.
                    .build();
            routing.execute();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        FindRoutes(new LatLng(startLat,startLon),new LatLng(endLat,endLon));
    }

    @Override
    public void onRoutingFailure(RouteException e) {
        Toast.makeText(this,"Error in Finding Route",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onRoutingStart() {
        Toast.makeText(BookRideActivity.this,"Finding Route...",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onRoutingSuccess(ArrayList<Route> route, int shortestRouteIndex) {
        LatLng startLatLng = new LatLng(startLat,startLon);
        CameraUpdate center = CameraUpdateFactory.newLatLng(startLatLng);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
        PolylineOptions polyOptions = new PolylineOptions();
        LatLng polylineStartLatLng=null;
        LatLng polylineEndLatLng=null;


        polylines = new ArrayList<>();
        //add route(s) to the map using polyline
        for (int i = 0; i <route.size(); i++) {

            if(i==shortestRouteIndex)
            {
                polyOptions.color(android.graphics.Color.RED);
                polyOptions.width(7);
                polyOptions.addAll(route.get(shortestRouteIndex).getPoints());
                Polyline polyline = mMap.addPolyline(polyOptions);
                polylineStartLatLng=polyline.getPoints().get(0);
                int k=polyline.getPoints().size();
                polylineEndLatLng=polyline.getPoints().get(k-1);
                polylines.add(polyline);

            }
            else {

            }

        }

        //Add Marker on route starting position
        MarkerOptions startMarker = new MarkerOptions();
        startMarker.position(polylineStartLatLng);
        startMarker.title("My Location");
        mMap.addMarker(startMarker);

        //Add Marker on route ending position
        MarkerOptions endMarker = new MarkerOptions();
        endMarker.position(polylineEndLatLng);
        endMarker.title("Destination");
        mMap.addMarker(endMarker);

    }

    @Override
    public void onRoutingCancelled() {
        FindRoutes(new LatLng(startLat,startLon),new LatLng(endLat,endLon));

    }
}