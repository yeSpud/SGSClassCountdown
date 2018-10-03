package com.stephenogden.sgsclasscountdownapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileWriter;

public class Timer extends AppCompatActivity {

    public Context context;

    private TextView block, countdown, noClass;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);

        context = getApplicationContext();

        block = findViewById(R.id.blockInfo);
        countdown = findViewById(R.id.countdown);
        noClass = findViewById(R.id.noClass);

        // Setup the settings button
        findViewById(R.id.gotoSettings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Timer.this, settings.class));
            }
        });

        /*
        try {
            developer.localStorage = new File(this.getFilesDir(), "local.txt");
            if (developer.localStorage.createNewFile()) {
                FileWriter fOS = new FileWriter(developer.localStorage);
                fOS.write("Auto");
                fOS.flush();
                fOS.close();
                recreate();
            } else {
                Log.i("File location", developer.localStorage.getAbsolutePath());
                if (developer.localStorage.canRead() && developer.localStorage.canWrite()) {

                    if (developer.localStorage.length() == 0) {
                        Log.e("Error", "File empty");
                    } else {
                        Log.i("File size", Long.toString(developer.localStorage.length()));
                    }
                } else {
                    Log.e("Error", "cannot read or write!");
                }
            }
        } catch (Exception e) {
            Log.e("Error getting file", e.toString());
        }
        */

    }


    protected void onResume() {
        super.onResume();

        final Core Core = new Core();

        // Create a timer for that one second loop
        new CountDownTimer(Long.MAX_VALUE - 1, 1000) {
            @Override
            public void onTick(long l) {

                // If there is no block, change the message to there is no block
                if (Core.getBlock().equals(Block.NoBlock)) {
                    block.setVisibility(View.GONE);
                    countdown.setVisibility(View.GONE);
                    noClass.setVisibility(View.VISIBLE);
                } else {

                    // If the countdown is not showing, set it to visible
                    if (noClass.getVisibility() == View.VISIBLE) {
                        block.setVisibility(View.VISIBLE);
                        countdown.setVisibility(View.VISIBLE);
                        noClass.setVisibility(View.GONE);
                    }
                    block.setText(Core.changeBlock(Core.getBlock()));
                    countdown.setText(Core.getTimeRemaining());
                }
            }

            @Override
            public void onFinish() {

                // If the timer has stopped as it has run out, relaunch the actvity
                recreate();
            }
        }.start(); // Start the timer
    }
}
