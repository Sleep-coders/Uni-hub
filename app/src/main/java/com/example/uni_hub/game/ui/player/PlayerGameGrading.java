package com.example.uni_hub.game.ui.player;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.uni_hub.R;
import com.example.uni_hub.game.logic.Player;
import com.example.uni_hub.game.services.ConnectionManger;
import com.example.uni_hub.game.services.MyRecyclerViewAdapter;

import java.util.List;

public class PlayerGameGrading extends AppCompatActivity {

    private static final String TAG = PlayerGameGrading.class.getName();
    MyRecyclerViewAdapter adapter;
    public static Thread startResultsThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_game_grading);

        List<Player> playersList = ConnectionManger.room.getPlayers();

        TextView hostNameTextView = findViewById(R.id.host_name_text_view);
        hostNameTextView.setText(ConnectionManger.room.getHostName());

        RecyclerView recyclerView = findViewById(R.id.players_grading_R_V);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, playersList);
        recyclerView.setAdapter(adapter);
        startResultsThread = new Thread(() -> {
            startActivity(new Intent(getApplicationContext(), PlayerGameRoom.class));
        });
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        // Enables regular immersive mode.
        // For "lean back" mode, remove SYSTEM_UI_FLAG_IMMERSIVE.
        // Or for "sticky immersive," replace it with SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                // Set the content to appear under the system bars so that the
                // content doesn't resize when the system bars hide and show.
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    @Override
    public void onBackPressed() {
        Log.i(TAG, "onBackPressed: back pressed");
    }
}