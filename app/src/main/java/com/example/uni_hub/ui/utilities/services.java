package com.example.uni_hub.ui.utilities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.uni_hub.MyService;
import com.example.uni_hub.R;


import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class services extends AppCompatActivity {

    Button play, stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        play = (Button) findViewById(R.id.play1);
        stop = (Button) findViewById(R.id.stop1);


        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyService.class);
                stopService(intent);
            }
        });

    }
}