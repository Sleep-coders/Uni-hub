package com.example.uni_hub.game.ui.host;

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

public class HostGameRoom extends AppCompatActivity {

    private static final String TAG = HostGameRoom.class.getName();
    private ArrayAdapter<String> arrayAdapter;
    public static Thread startNewGameThread;
    public static Thread playerJoinedThread;
    public static Thread endGameForAllThread;
    private List<String> playersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_game_room);

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

    }

    public void startInRoomActivity(View view) {
        startNewGameThread = new Thread(() -> {
            startActivity(new Intent(this, HostInRoomGame.class));
            finish();
        });
        ConnectionManger.startGameForAll();
    }

    public void shareGameInfo(View view) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        String gameID = ConnectionManger.room.getGameID();
        intent.putExtra(Intent.EXTRA_TEXT, "Hey There Friend! Come Join Me In This Game , " + gameID);
        Intent shareIntent = Intent.createChooser(intent, null);
        intent.setType("text/plain");
        startActivity(shareIntent);
    }

    public void endGameForAll(View view) {
        endGameForAllThread = new Thread(() -> {
            Toast toast = Toast.makeText(getApplicationContext(), "You Have Ended The Game", Toast.LENGTH_LONG);
            toast.show();
            startActivity(new Intent(getApplicationContext(), JoinORCreate.class));
            finish();
        });
        ConnectionManger.endGameForAll();
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

    public void sendMessage(View view) {

    }

    @Override
    public void onBackPressed() {
        Log.i(TAG, "onBackPressed: back pressed");
    }
}