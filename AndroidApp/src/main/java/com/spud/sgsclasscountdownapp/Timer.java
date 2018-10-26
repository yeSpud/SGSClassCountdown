package com.spud.sgsclasscountdownapp;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Timer extends AppCompatActivity {

    DatabaseFile database;
    RegimeFiles regime;
    private TextView block, countdown, noClass;
    private CountDownTimer timer;
    private ImageView background;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        database = new DatabaseFile();
        regime = new RegimeFiles();

        // Set the view to the timer XML file
        setContentView(R.layout.timer);

        // Find the blockType, countdown, and noClass header text fields from the XML file
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

        // If the background isn't null, recycle it (RAM management)
        if (background != null) {
            ((BitmapDrawable) background.getDrawable()).getBitmap().recycle();
        }
    }

    protected void onPause() {
        super.onPause();
        // Kill the timer if the app isn't being used (RAM management)
        timer.cancel();
    }

    protected void onResume() {
        super.onResume();

        // Create a new timer object, for updating the info about every half second
        timer = new CountDownTimer(Long.MAX_VALUE - 1, 500) {
            @Override
            public void onTick(long l) {

                Core Core = new Core();

                // Check if the day is not over
                if (!Core.isDayOver()) {
                    // Check if its the weekend
                    if (!WeekType.getWeekType().equals(WeekType.Weekend)) {
                        final RegimeFiles regime = new RegimeFiles();

                        // Check if there's a blockType
                        if (!regime.getBlockFromRegime(Core.getTimeAsLong()).equals(BlockNames.NoBlock)) {
                            // Show the blockType and countdown
                            block.setVisibility(View.VISIBLE);
                            countdown.setVisibility(View.VISIBLE);
                            // Hide the no class header
                            noClass.setVisibility(View.GONE);
                            // Update the blockType and countdown
                            block.setText(Core.changeBlockHeader(regime.getBlockFromRegime(Core.getTimeAsLong())));
                            countdown.setText(Core.getTimeRemaining());
                        } else {
                            // Hide the blockType and countdown
                            block.setVisibility(View.GONE);
                            countdown.setVisibility(View.GONE);
                            // Show the no class header
                            noClass.setVisibility(View.VISIBLE);
                        }
                    } else {
                        // Hide the blockType and countdown
                        block.setVisibility(View.GONE);
                        countdown.setVisibility(View.GONE);
                        // Show the no class header
                        noClass.setVisibility(View.VISIBLE);
                    }
                } else {
                    // Hide the blockType and countdown
                    block.setVisibility(View.GONE);
                    countdown.setVisibility(View.GONE);
                    // Show the no class header
                    noClass.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFinish() {
                // If the timer has stopped as it has run out, relaunch the activity
                recreate();
            }
        }.start(); // Start the timer

        // Setup the background (Unless out of RAM)
        // https://stackoverflow.com/questions/10200256/out-of-memory-error-imageview-issue
        try {
            background = findViewById(R.id.backgroundImage);
            background.setImageResource(R.drawable.background1);
            background.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } catch (OutOfMemoryError noRam) {
            Log.e("Background generation", "Out of RAM!");
            background = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // If the page is queued to be destroyed, remove the timer, and background (RAM management)
        timer.cancel();

        if (background != null) {
            background.setImageDrawable(null);
        }
    }
}
