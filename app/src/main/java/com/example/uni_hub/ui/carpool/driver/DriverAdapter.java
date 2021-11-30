package com.example.uni_hub.ui.carpool.driver;

import android.annotation.SuppressLint;
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
import com.squareup.picasso.Picasso;

import java.util.List;

public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.DriverViewHolder> {
    List<Ride> allRides ;
    String userId;




    public DriverAdapter( List<Ride> allRides ,String userId) {
        this.allRides = allRides;
        this.userId = userId;
    }

    @NonNull
    @Override
    public DriverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.driver_raw,parent,false);
        return new DriverViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DriverViewHolder holder, int position) {
        if(allRides.get(position).getOwnerId() == userId){
            holder.requestRideBtn.setVisibility(View.INVISIBLE);
            holder.riderName.setText(allRides.get(position).getOwnerName());
            holder.routPath.setText(allRides.get(position).getRideDescription());
            Picasso.get().load(allRides.get(position).getCarImage()).into(holder.carImg);
            holder.departureTimeText.setText(allRides.get(position).getRideDepartureTime());
            holder.costText.setText(Double.toString(allRides.get(position).getCost()));
            holder.passNum.setText(Integer.toString(allRides.get(position).getAvailableSeats()));
            holder.rideExAt.setText(allRides.get(position).getRideExpiresAt());
        }

    }

    @Override
    public int getItemCount() {
        return allRides.size();
    }

    public static class DriverViewHolder extends RecyclerView.ViewHolder{

        TextView riderName, routPath,departureTimeText,costText,passNum,rideExAt ;
        ImageView carImg;
        Button requestRideBtn;

        public DriverViewHolder(@NonNull View itemView) {
            super(itemView);
            riderName = itemView.findViewById(R.id.driver_name_cards);
            routPath = itemView.findViewById(R.id.rout_path);
            carImg = itemView.findViewById(R.id.car_image_card);
            departureTimeText = itemView.findViewById(R.id.departure_time_card);
            costText = itemView.findViewById(R.id.cost_rout_card);
            passNum = itemView.findViewById(R.id.number_of_passengers_card);
            rideExAt = itemView.findViewById(R.id.ride_expires_at_card);
            requestRideBtn = itemView.findViewById(R.id.request_ride);

        }
    }
}
