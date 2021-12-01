package com.example.uni_hub.ui.carpool.rider;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.example.uni_hub.R;

public class BookRideActivity extends AppCompatActivity {


    ImageView carImage;
    TextView driverName;
    TextView rideDepartureDateTime;
    TextView rideAvailableSeats;
    TextView rideCost;
    TextView rideCarInfo;
    TextView rideExpAt;
    TextView rideDescription;

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

        loadActivityData(intent);

        findViewById(R.id.ride_contact_driver).setOnClickListener(view -> {

        });
    }
//                intent.putExtra("id", ride.getId());
//            intent.putExtra("ownerName", ride.getOwnerName());
//            intent.putExtra("ownerId", ride.getOwnerId());
//            intent.putExtra("rideDepartureTime", ride.getRideDepartureTime());
//            intent.putExtra("availableSeats", ride.getAvailableSeats());
//            intent.putExtra("cost", ride.getCost());
//            intent.putExtra("carImage", ride.getCarImage());
//            intent.putExtra("carInfo", ride.getCarInfo());
//            intent.putExtra("rideExpAt", ride.getRideExpiresAt());
//            intent.putExtra("rideDate", ride.getRideDate());
//            intent.putExtra("rideDescription", ride.getRideDescription());
//            intent.putExtra("rideRoute", ride.getRideRoute());

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
}