package com.example.uni_hub.game.ui.player;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uni_hub.R;
import com.example.uni_hub.game.logic.Player;
import com.example.uni_hub.game.services.ConnectionManger;
import com.example.uni_hub.game.ui.JoinORCreate;

import java.util.ArrayList;
import java.util.List;

public class PlayerGameRoom extends AppCompatActivity {

    private static final String TAG = PlayerGameRoom.class.getName();
    private ArrayAdapter<String> arrayAdapter;
    List<String> playersList;
    public static Thread startNewGameThread;
    public static Thread playerJoinedThread;
    public static Thread endGameForAllThread;
    public static Thread endGameForOneThread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_game_room);

        Log.e(TAG, "onCreate: ====>" + ConnectionManger.room.getHostName());
        TextView hostNameTextView = findViewById(R.id.host_name_text_view);
        hostNameTextView.setText(ConnectionManger.room.getHostName());

        playersList = new ArrayList<>();
        for (Player player : ConnectionManger.room.getPlayers()) {
            playersList.add(player.getClientName() + " with " + player.getPlayerPoints() + " points");
        }
        Log.i(TAG, "onCreate: =====> PlayerList ===>: " + playersList.toString());

        ListView listView = findViewById(R.id.list_view_players);
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, playersList);
        listView.setAdapter(arrayAdapter);

        startNewGameThread = new Thread(() -> {
            startActivity(new Intent(getApplicationContext(), PlayerInRoomGame.class));
        });

        playerJoinedThread = new Thread(() -> {
            playersList = new ArrayList<>();
            for (Player player : ConnectionManger.room.getPlayers()) {
                playersList.add(player.getClientName() + " with " + player.getPlayerPoints() + " points");
            }
            runOnUiThread(() -> {
                arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, playersList);
                listView.setAdapter(arrayAdapter);
            });

        });

        endGameForAllThread = new Thread(() -> {
            Toast toast = Toast.makeText(getApplicationContext(), "The Host Has Ended The Game", Toast.LENGTH_LONG);
            toast.show();
            startActivity(new Intent(getApplicationContext(), JoinORCreate.class));
            finish();
        });

        endGameForOneThread = new Thread(() -> {
            Toast toast = Toast.makeText(getApplicationContext(), "You Closed The Game", Toast.LENGTH_LONG);
            toast.show();
            startActivity(new Intent(getApplicationContext(), JoinORCreate.class));
            finish();
        });


    }

    public void endGameForOne(View view) {
        ConnectionManger.endGameForOne();
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