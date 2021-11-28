package com.example.uni_hub.ui.carpool.rider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uni_hub.R;

public class Rider_home_adapter extends RecyclerView.Adapter<Rider_home_adapter.MyViewHolder> {

    String data1[],  data2[], data3[];
    int images[];
    Context context;

    public Rider_home_adapter(Context ct,String availableRides[], String ridesDescription[],String ridesDescription2[], int img[]) {
        context = ct;
        data1 = availableRides;
        data2= ridesDescription;
        data3= ridesDescription2;
        images= img;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rider_home_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
       holder.rideTitle.setText(data1[position]);
       holder.rideDescription.setText(data2[position]);
       holder.rideDescription2.setText(data3[position]);
       holder.carImage.setImageResource(images[position]);
    }

    @Override
    public int getItemCount() {
        return data1.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView rideTitle;
        TextView rideDescription;
        TextView rideDescription2;
        ImageView carImage;
        Button joinRideBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            rideTitle = itemView.findViewById(R.id.ridesTitle_text);
            rideDescription = itemView.findViewById(R.id.rides_description_text);
            rideDescription2 = itemView.findViewById(R.id.rides_description_text2);
            carImage = itemView.findViewById(R.id.rides_imges);
            joinRideBtn= itemView.findViewById(R.id.joinride_btn);
        }
    }
}
