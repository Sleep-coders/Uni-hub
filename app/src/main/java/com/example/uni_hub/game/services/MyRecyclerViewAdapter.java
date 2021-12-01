package com.example.uni_hub.game.services;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.uni_hub.R;
import com.example.uni_hub.game.logic.GameLogic;
import com.example.uni_hub.game.logic.GradingResult;
import com.example.uni_hub.game.logic.Player;
import com.example.uni_hub.game.ui.host.HostGameGrading;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = MyRecyclerViewAdapter.class.getName();
    private List<Player> mData;
    private LayoutInflater mInflater;

    // data is passed into the constructor
    public MyRecyclerViewAdapter(Context context, List<Player> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycler_view_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Player player = mData.get(position);
        String playerName = player.getClientName();
        String playerId = player.getPlayerID();
        GameLogic answers = player.getAnswers();

        String human;
        if (answers.getHuman().equals("null")) {
            human = "Human : ";
        } else {
            human = "Human : " + answers.getHuman();
        }

        String animal;
        if (answers.getAnimal().equals("null")) {
            animal = "Animal : ";
        } else {
            animal = "Animal : " + answers.getAnimal();
        }

        String plant;
        if (answers.getPlant().equals("null")) {
            plant = "Plant : ";
        } else {
            plant = "Plant : " + answers.getPlant();
        }
        String country;
        if (answers.getCountry().equals("null")) {
            country = "Country : ";

        } else {
            country = "Country : " + answers.getCountry();

        }
        String thing;
        if (answers.getCountry().equals("null")) {
            thing = "Thing : ";

        } else {
            thing = "Thing : " + answers.getThing();

        }

        holder.playerNameTextView.setText(playerName);
        holder.playerIDTextView.setText(playerId);

        holder.humanTextView.setText(human);
        holder.animalTextView.setText(animal);
        holder.plantTextView.setText(plant);
        holder.countryTextView.setText(country);
        holder.thingTextView.setText(thing);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView playerNameTextView;
        TextView playerIDTextView;
        TextView humanTextView;
        TextView animalTextView;
        TextView plantTextView;
        TextView countryTextView;
        TextView thingTextView;
        Button givePointBtn;

        ViewHolder(View itemView) {
            super(itemView);
            playerNameTextView = itemView.findViewById(R.id.player_name);
            playerIDTextView = itemView.findViewById(R.id.clientId_hidden);

            humanTextView = itemView.findViewById(R.id.human_list_item);
            animalTextView = itemView.findViewById(R.id.animal_list_item);
            plantTextView = itemView.findViewById(R.id.plant_list_item);
            countryTextView = itemView.findViewById(R.id.country_list_item);
            thingTextView = itemView.findViewById(R.id.thing_list_item);

            givePointBtn = itemView.findViewById(R.id.accept_btn);

            if(ConnectionManger.room.getHostID().equals(ConnectionManger.clientID)){
                givePointBtn.setOnClickListener((v) -> {
                    HostGameGrading.gradingResultsList.add(new GradingResult(playerIDTextView.getText().toString(), 5));
                    givePointBtn.setEnabled(false);
                    Log.i(TAG, "onBindViewHolder: ====> Clicked" + playerIDTextView.getText());
                });
            }else{
                givePointBtn.setVisibility(Button.INVISIBLE);
            }

        }
    }
}