package com.example.uni_hub.ui.roommates;

import android.app.Activity;
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
import com.amplifyframework.datastore.generated.model.AppUser;
import com.amplifyframework.datastore.generated.model.Room;
import com.example.uni_hub.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RoommatesAdapter extends RecyclerView.Adapter<RoommatesAdapter.ViewHolder> {

    private static final String TAG = RoommatesAdapter.class.getName();
    private List<Room> mData;
    private LayoutInflater mInflater;
    private Context context;
    private String currentUserId;

    // data is passed into the constructor
    public RoommatesAdapter(Context context, List<Room> data) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.currentUserId = Amplify.Auth.getCurrentUser().getUsername();
    }


    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.roommate_recycler_view_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Room room = mData.get(position);
        String userName = room.getRoomOwnerUserName();
        Double roomCost = room.getRoomCost();
        String roomLocation = room.getRoomLocation();
        String roomImage = room.getRoomImage();
        String userEmail = room.getRoomOwnerEmail();
        holder.userNameRoom.setText(userName);
        holder.costRoom.setText(String.valueOf(roomCost));
        holder.locationRoom.setText(roomLocation);
        Picasso.get().load(roomImage).into(holder.imageRoom);


        if (userEmail.equals(currentUserId)) {
            holder.deleteRoomButton.setVisibility(View.VISIBLE);
            holder.deleteRoomButton.setOnClickListener((v) -> {
                deleteRoomFromDB(room);
            });
        } else {
            holder.requestRoomButton.setOnClickListener((v) -> {
                Intent intent = new Intent(new Intent(context, RoomDetailsActivity.class));
                Log.i(TAG, "onBindViewHolder: ============> " + room.getId());
                intent.putExtra("roomId", room.getId());
                context.startActivity(intent);
            });
        }


    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView userNameRoom;
        TextView costRoom;
        TextView locationRoom;
        Button requestRoomButton;
        Button deleteRoomButton;
        ImageView imageRoom;

        ViewHolder(View itemView) {
            super(itemView);
            userNameRoom = itemView.findViewById(R.id.user_name_id);
            costRoom = itemView.findViewById(R.id.room_cost_id);
            locationRoom = itemView.findViewById(R.id.room_location_id);
            imageRoom = itemView.findViewById(R.id.room_image);
            requestRoomButton = itemView.findViewById(R.id.req_room_btn);
            deleteRoomButton = itemView.findViewById(R.id.del_room_btn);
        }
    }

    public void setmData(List<Room> mData) {
        this.mData = mData;
    }

    public void deleteRoomFromDB(Room room) {
        Amplify.API.mutate(ModelMutation.delete(room), success -> {
            ((AvailableRooms) context).updateView();
            Log.i(TAG, "deleteRoomFromDB: -----> " + success.getData());
        }, err -> {

        });
    }
}