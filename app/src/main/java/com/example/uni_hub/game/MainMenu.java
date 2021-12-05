package com.example.uni_hub.game;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.uni_hub.MainActivity;
import com.example.uni_hub.R;
import com.example.uni_hub.game.ui.JoinORCreate;

public class MainMenu extends AppCompatActivity {

    private static final String TAG = MainMenu.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ImageView imgCancel = findViewById(R.id.imageViewCancel);
        imgCancel.setOnClickListener(this::returnToMainApplication);
    }

    public void startGameActivity(View view) {
        Intent intent = new Intent(MainMenu.this, JoinORCreate.class);
        startActivity(intent);
    }

    public void returnToMainApplication(View view){
        // TODO: 28-Nov-21 this will be implemented on merge
        startActivity(new Intent(this, MainActivity.class));
    }


    ///////////////////////////////////////////////////////////////////////////////////////////
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