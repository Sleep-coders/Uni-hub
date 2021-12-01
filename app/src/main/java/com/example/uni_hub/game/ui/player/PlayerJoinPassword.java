package com.example.uni_hub.game.ui.player;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.uni_hub.R;
import com.example.uni_hub.game.services.ConnectionManger;

public class PlayerJoinPassword extends AppCompatActivity {

    private static final String TAG = PlayerJoinPassword.class.getName();
    public static Thread changeVisibilityThread;
    public static Thread changeVisibilityThread2;
    public static Thread startGameRoomThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_join_password);
        TextView wrongPasswordTextView = findViewById(R.id.wrong_game_password);
        wrongPasswordTextView.setVisibility(TextView.INVISIBLE);

        changeVisibilityThread = new Thread(() -> {
            wrongPasswordTextView.setText("Wrong Password");
            wrongPasswordTextView.setVisibility(TextView.VISIBLE);
        });
        changeVisibilityThread2 = new Thread(() -> {
            wrongPasswordTextView.setText("Game Already Started Or Room Is Full");
            wrongPasswordTextView.setVisibility(TextView.VISIBLE);
        });
    }

    public void backToJoinOrCreate(View view) {
        finish();
    }

    public void checkPasswordAndJoin(View view) {
        startGameRoomThread = new Thread(() -> {
            startActivity(new Intent(getApplicationContext(), PlayerGameRoom.class));
        });

        String gameID = getIntent().getStringExtra("gameID");
        EditText passwordEditText = findViewById(R.id.room_password_join);
        String password = passwordEditText.getText().toString();
        ConnectionManger.joinRoom(gameID, password);
    }
    @Override
    public void onBackPressed() {
        Log.i(TAG, "onBackPressed: back pressed");
    }
}