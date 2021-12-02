package com.example.uni_hub.game.ui.host;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.uni_hub.R;
import com.example.uni_hub.game.services.ConnectionManger;

public class HostInRoomGame extends AppCompatActivity {

    private static final String TAG = HostInRoomGame.class.getName();
    private TextView timerText;
    private long timeLeft = 61000;
    public static Thread sendGameDataThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host_in_room_game);
        TextView randomAlphabetTextView;
        randomAlphabetTextView = findViewById(R.id.alphabet_random);
        randomAlphabetTextView.setText(String.valueOf(ConnectionManger.randomChar));

        TextView hostNameTextView = findViewById(R.id.host_name_text_view);
        hostNameTextView.setText(ConnectionManger.room.getHostName());

        TextView humanTV = findViewById(R.id.human_text_view);
        TextView animalTV = findViewById(R.id.animal_text_view);
        TextView plantTV = findViewById(R.id.plant_text_view);
        TextView countryTV = findViewById(R.id.country_text_view);
        TextView thingTV = findViewById(R.id.thing_text_view);


        timerText = findViewById(R.id.timer_text);
        CountDownTimer timer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long l) {
                timeLeft = l;
                updateTimerText();
            }

            @Override
            public void onFinish() {

                sendGameDataThread = new Thread(()->{
                    startActivity(new Intent(getApplicationContext(),HostGameGrading.class));
                });

                String human = humanTV.getText().toString();
                String animal = animalTV.getText().toString();
                String plant = plantTV.getText().toString();
                String country = countryTV.getText().toString();
                String thing = thingTV.getText().toString();


                ConnectionManger.sendGameLogicToServer(human,animal,plant,country,thing);


            }
        };
        timer.start();
    }

    public void updateTimerText() {
        int seconds = (int) timeLeft / 1000;
        timerText.setText(String.valueOf(seconds));
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