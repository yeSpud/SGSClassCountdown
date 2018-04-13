package com.stephenogden.sgsclasscountdownapp;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

public class regular extends AppCompatActivity {

    boolean running;

    TextView count,staticTime,staticHeader,dynamicHeader;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regular);

        count = findViewById(R.id.countdownTime);
        staticTime = findViewById(R.id.staticTime);
        staticHeader = findViewById(R.id.staticTimeHeader);
        dynamicHeader = findViewById(R.id.countdownHeader);

    }

    protected void onResume() {
        super.onResume();



    }
}
