package com.example.uni_hub.ui.carpool.driver;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.squareup.picasso.Picasso;

import java.util.List;

public class DriverAdapter extends RecyclerView.Adapter<DriverAdapter.DriverViewHolder> {
    List<Ride> allRides ;
    String userId;
    Context context;



    public DriverAdapter( List<Ride> allRides ,String userId , Context context) {
        this.allRides = allRides;
        this.userId = userId;
        this.context=context;
    }

    @NonNull
    @Override
    public DriverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.driver_raw,parent,false);
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
//        View view = inflater.inflate(R.layout.driver_raw,parent,false);
        return new DriverViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DriverViewHolder holder, int position) {
        if(allRides.get(position).getOwnerId().equals(userId)){
            holder.requestRideBtn.setVisibility(View.INVISIBLE);
            holder.deleteRideBtn.setVisibility(View.VISIBLE);
        }
        Log.i("ALLRIDES++++>>>>", "++++++>>>>>>>"+allRides);
        Log.i("USERID++++>>>>", "++++++>>>>>>>"+userId);
        holder.riderName.setText(allRides.get(position).getOwnerName());
        holder.routPath.setText(allRides.get(position).getRideDescription());
        Log.i("URL IMG++++>>>>", "++++++>>>>>>>"+allRides.get(position).getCarInfo());
        Picasso.get().load(allRides.get(position).getCarInfo()).into(holder.carImg);
        holder.departureTimeText.setText(allRides.get(position).getRideDepartureTime());
        holder.costText.setText(Double.toString(allRides.get(position).getCost()));
//        holder.passNum.setText(Integer.toString(allRides.get(position).getAvailableSeats()));
        holder.rideDate.setText(allRides.get(position).getRideDate());

        holder.deleteRideBtn.setOnClickListener(view -> {
            Amplify.API.mutate(ModelMutation.delete(allRides.get(position)),
                    response -> Log.i("MyAmplifyApp", "Todo with id: " + response.getData().getId()),
                    error -> Log.e("MyAmplifyApp", "Create failed", error)
            );
        });
        holder.requestRideBtn.setOnClickListener(view -> {

        });

    }

    @Override
    public int getItemCount() {
        return allRides.size();
    }

    public static class DriverViewHolder extends RecyclerView.ViewHolder{

        TextView riderName, routPath,departureTimeText,costText,passNum,rideDate ;
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
