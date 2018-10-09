package com.spud.sgsclasscountdownapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class Timer extends AppCompatActivity {

    private TextView block, countdown, noClass;
    private CountDownTimer timer;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);

        block = findViewById(R.id.blockInfo);
        countdown = findViewById(R.id.countdown);
        noClass = findViewById(R.id.noClass);

        // Setup the Settings button
        findViewById(R.id.gotoSettings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Timer.this, Settings.class));
                finish();
            }
        });
    }

    protected void onPause() {
        super.onPause();
        timer.cancel();
    }

    protected void onResume() {
        super.onResume();

        timer = new CountDownTimer(Long.MAX_VALUE - 1, 500) {
            @Override
            public void onTick(long l) {

                final Core Core = new Core();

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

                    // Update the time remaining and the current block
                    block.setText(Core.changeBlock(Core.getBlock()));
                    countdown.setText(Core.getTimeRemaining());
                }
            }

            @Override
            public void onFinish() {
                // If the timer has stopped as it has run out, relaunch the activity
                recreate();
            }
        }.start(); // Start the timer
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
}
