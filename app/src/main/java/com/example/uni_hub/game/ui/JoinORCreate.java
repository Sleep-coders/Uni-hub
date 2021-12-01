package com.example.uni_hub.game.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.uni_hub.R;
import com.example.uni_hub.game.services.ConnectionManger;
import com.example.uni_hub.game.ui.player.PlayerJoinPassword;

public class JoinORCreate extends AppCompatActivity {
    private static final String TAG = JoinORCreate.class.getName();
    private ConnectionManger connectionManger;
    public static Thread startPassWordActivityThread;
    public static Thread changeVisibilityThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_orcreate);
        connectionManger = new ConnectionManger();
        connectionManger.connectToServer();

        TextView wrongIDTextView = findViewById(R.id.wrong_game_id_text);
        wrongIDTextView.setVisibility(TextView.INVISIBLE);

        changeVisibilityThread = new Thread(() -> {
            Log.i(TAG, "onCreate: BROOOOOOOO");
            checkGameId(wrongIDTextView);
        });

    }

    public void checkGameId(TextView view) {
        runOnUiThread(() -> {
            view.setVisibility(TextView.VISIBLE);
        });
    }

    public void checkForGameID(View view) {

        EditText roomIDEditText = findViewById(R.id.room_password_join);
        String roomIDString = roomIDEditText.getText().toString();
        startPassWordActivityThread = new Thread(() -> {
            Intent intent = new Intent(getApplicationContext(), PlayerJoinPassword.class);
            intent.putExtra("gameID",roomIDString);
            startActivity(intent);
        });
        ConnectionManger.verifyGameId(roomIDString);
    }

    public void startCreateRoom(View view) {
        Intent intent = new Intent(getApplicationContext(), CreateNewRoom.class);
        startActivity(intent);
    }

    public void backToMainMenu(View view) {
        finish();
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
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    @Override
    public void onBackPressed() {
        Log.i(TAG, "onBackPressed: back pressed");
    }

}