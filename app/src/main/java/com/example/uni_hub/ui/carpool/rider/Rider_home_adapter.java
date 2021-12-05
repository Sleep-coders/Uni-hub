package com.example.uni_hub.ui.carpool.rider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.Ride;
import com.example.uni_hub.R;
import com.example.uni_hub.ui.carpool.BookRideActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class Rider_home_adapter extends RecyclerView.Adapter<Rider_home_adapter.MyViewHolder> {

    List<Ride> allRides;
    String userId;
    Context context;

    public Rider_home_adapter(List<Ride> allRides, String userId, Context context) {
        this.allRides = allRides;
        this.userId = userId;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rider_home_row,parent,false);
        return new MyViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Ride ride = allRides.get(position);
       holder.ride_owner_name.setText(ride.getOwnerName());
       holder.ride_location_path.setText(ride.getRideDescription());
       holder.ride_date.setText(ride.getRideDate());
       holder.ride_cost.setText(ride.getCost().toString());
       holder.ride_seats_number.setText(ride.getAvailableSeats().toString());
       holder.ride_time.setText(ride.getRideDepartureTime());
       holder.ride_expires_at.setText(ride.getRideExpiresAt());
       Picasso.get().load(ride.getCarImage()).into(holder.carImage);

        holder.joinRideBtn.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), BookRideActivity.class);

            intent.putExtra("id", ride.getId());
            intent.putExtra("ownerName", ride.getOwnerName());
            intent.putExtra("ownerId", ride.getOwnerId());
            intent.putExtra("rideDepartureTime", ride.getRideDepartureTime());
            intent.putExtra("availableSeats", ride.getAvailableSeats());
            intent.putExtra("cost", ride.getCost());
            intent.putExtra("carImage", ride.getCarImage());
            intent.putExtra("carInfo", ride.getCarInfo());
            intent.putExtra("rideExpAt", ride.getRideExpiresAt());
            intent.putExtra("rideDate", ride.getRideDate());
            intent.putExtra("rideDescription", ride.getRideDescription());
            intent.putExtra("rideRoute", ride.getRideRoute());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            view.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return allRides.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView ride_owner_name;
        TextView ride_location_path;
        TextView ride_date;
        TextView ride_cost;
        TextView ride_seats_number;
        TextView ride_time;
        TextView ride_expires_at;
        ImageView carImage;
        Button joinRideBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ride_owner_name = itemView.findViewById(R.id.driver_name);
            ride_location_path = itemView.findViewById(R.id.location_path_from_driver);
            ride_date = itemView.findViewById(R.id.date_of_the_trip);
            ride_cost = itemView.findViewById(R.id.cost_of_the_trip);
            ride_seats_number = itemView.findViewById(R.id.seats_number_available);
            ride_time = itemView.findViewById(R.id.time_of_the_trip);
            ride_expires_at = itemView.findViewById(R.id.trip_expires_at);
            carImage = itemView.findViewById(R.id.car_img_from_driver);
            joinRideBtn= itemView.findViewById(R.id.joinride_btn);
        }
    }
}
