package com.example.uni_hub.ui.carpool.driver;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.api.graphql.model.ModelMutation;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Ride;
import com.example.uni_hub.R;
import com.example.uni_hub.ui.carpool.BookRideActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.DriverViewHolder> {
    List<Ride> allRides;
    String userId;
    Context context;
//    private final RecyclerViewInterface recyclerViewInterface;


    public DriverAdapter(List<Ride> allRides, String userId, Context context) {
        this.allRides = allRides;
        this.userId = userId;
        this.context = context;
//        this.recyclerViewInterface = recyclerViewInterface;

    }

    @NonNull
    @Override
    public DriverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater inflater = LayoutInflater.from(context);
//        View view = inflater.inflate(R.layout.driver_raw, parent, false);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.driver_raw,parent,false);
        return new DriverViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DriverViewHolder holder, int position) {
        Ride ride = allRides.get(position);

        if (ride.getOwnerId().equals(userId)) {
            holder.requestRideBtn.setVisibility(View.INVISIBLE);
            holder.deleteRideBtn.setVisibility(View.VISIBLE);
        }
//        Log.i("ALLRIDES++++>>>>", "++++++>>>>>>>" + allRides);
//        Log.i("USERID++++>>>>", "++++++>>>>>>>" + userId);
//        Log.i("URL IMG++++>>>>", "++++++>>>>>>>" + ride.getCarInfo());
//        holder.passNum.setText(Integer.toString(ride.getAvailableSeats()));
        holder.riderName.setText(ride.getOwnerName());
        holder.routPath.setText(ride.getRideDescription());
        Picasso.get().load(ride.getCarImage()).into(holder.carImg);
        holder.departureTimeText.setText(ride.getRideDepartureTime());
        holder.costText.setText(Double.toString(ride.getCost()));
        holder.rideDate.setText(ride.getRideDate());

        holder.deleteRideBtn.setOnClickListener(view -> {
            Amplify.API.mutate(ModelMutation.delete(allRides.get(position)),
                    response -> Log.i("MyAmplifyApp", "Todo with id: " + response.getData().getId()),
                    error -> Log.e("MyAmplifyApp", "Create failed", error)
            );
//            recyclerViewInterface.onItemClick(position);
        });
//
        holder.requestRideBtn.setOnClickListener(view -> {
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

    public static class DriverViewHolder extends RecyclerView.ViewHolder {
        TextView riderName, routPath, departureTimeText, costText, passNum, rideDate;
        ImageView carImg;
        Button requestRideBtn, deleteRideBtn;

        public DriverViewHolder(@NonNull View itemView) {
            super(itemView);
            riderName = itemView.findViewById(R.id.driver_name_cards);
            routPath = itemView.findViewById(R.id.rout_path);
            carImg = itemView.findViewById(R.id.car_image_card);
            departureTimeText = itemView.findViewById(R.id.departure_time_card);
            costText = itemView.findViewById(R.id.cost_rout_card);
//            passNum = itemView.findViewById(R.id.number_of_passengers_card);
            rideDate = itemView.findViewById(R.id.ride_date_card);
            requestRideBtn = itemView.findViewById(R.id.request_ride);
            deleteRideBtn = itemView.findViewById(R.id.ride_delete_btn);

        }
    }
}
