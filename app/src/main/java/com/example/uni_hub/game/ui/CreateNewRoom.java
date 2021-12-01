package com.example.uni_hub.game.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.uni_hub.R;
import com.example.uni_hub.game.services.ConnectionManger;
import com.example.uni_hub.game.ui.host.HostGameRoom;

public class CreateNewRoom extends AppCompatActivity {

    private static final String TAG = CreateNewRoom.class.getName();
    public static Thread createNewGameThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_room);
        onWindowFocusChanged(true);
        findViewById(R.id.create_new_game_btn).setOnClickListener(this::createNewGame);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }



    public void createNewGame(View view) {
        EditText password = findViewById(R.id.room_password);
        String passwordString = password.getText().toString();
        Spinner playerNumberSpinner = findViewById(R.id.players_num_spinner);
        int playerNumberMaxInt = Integer.parseInt(playerNumberSpinner.getSelectedItem().toString());
        createNewGameThread = new Thread(() -> {
            startActivity(new Intent(getApplicationContext(), HostGameRoom.class));
        });
        ConnectionManger.createNewGame(passwordString, "osaid720720@gmail.com", playerNumberMaxInt);
    }

    public void backToJoinOrCreate(View view){
        finish();
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
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    @Override
    public void onBackPressed() {
        Log.i(TAG, "onBackPressed: back pressed");
    }
}