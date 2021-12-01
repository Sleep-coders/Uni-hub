package com.example.uni_hub.ui.utilities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.uni_hub.R;

public class Student_Utilities extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_utilities);


        Intent intent=new Intent(this, Calendar_Activity.class);
        Button btn= findViewById(R.id.btn_calender);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });




        Intent intent2=new Intent(this, Calendar_Activity.class);
        Button btn2= findViewById(R.id.btn_pdf_scanner);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent2);
            }
        });

    }
}