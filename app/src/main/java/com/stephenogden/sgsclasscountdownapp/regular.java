package com.stephenogden.sgsclasscountdownapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;


public class regular extends AppCompatActivity {

    TextView blockText, staticTime, countdownTime;

    Button settings;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regular);

        blockText = findViewById(R.id.blockText);
        staticTime = findViewById(R.id.staticTime);
        countdownTime = findViewById(R.id.countdownTime);
        settings = findViewById(R.id.settings);

    }

    protected void onResume() {
        super.onResume();



    }
}
