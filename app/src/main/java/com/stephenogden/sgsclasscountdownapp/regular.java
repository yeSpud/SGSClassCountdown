package com.stephenogden.sgsclasscountdownapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

        final time time = new time();

        new CountDownTimer(Long.MAX_VALUE-1, 1000) {
            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {

                if (time.getBlock().contains("normal")) {
                    if (time.getBlock().contains("A -")) {
                        blockText.setText(R.string.aBlock);
                        staticTime.setText(R.string.A_STDTime);
                        countdownTime.setText(time.getTimeRemaining());
                    } else if (time.getBlock().contains("B -")) {
                        blockText.setText(R.string.bBlock);
                        staticTime.setText(R.string.B_STDTime);
                        countdownTime.setText(time.getTimeRemaining());
                    } else if (time.getBlock().contains("C -")) {
                        blockText.setText(R.string.cBlock);
                        staticTime.setText(R.string.C_STDTime);
                        countdownTime.setText(time.getTimeRemaining());
                    } else if (time.getBlock().contains("D -")) {
                        blockText.setText(R.string.dBlock);
                        staticTime.setText(R.string.D_STDTime);
                        countdownTime.setText(time.getTimeRemaining());
                    } else if (time.getBlock().contains("E -")) {
                        blockText.setText(R.string.eBlock);
                        staticTime.setText(R.string.E_STDTime);
                        countdownTime.setText(time.getTimeRemaining());
                    } else if (time.getBlock().contains("F -")) {
                        blockText.setText(R.string.fBlock);
                        staticTime.setText(R.string.F_STDTime);
                        countdownTime.setText(time.getTimeRemaining());
                    } else if (time.getBlock().contains("G -")) {
                        blockText.setText(R.string.gBlock);
                        staticTime.setText(R.string.G_STDTime);
                        countdownTime.setText(time.getTimeRemaining());
                    } else if (time.getBlock().contains("H -")) {
                        blockText.setText(R.string.hBlock);
                        staticTime.setText(R.string.H_STDTime);
                        countdownTime.setText(time.getTimeRemaining());
                    }
                } else if (time.getBlock().contains("long")) {
                    if (time.getBlock().contains("A -")) {
                        blockText.setText(R.string.aBlock);
                        staticTime.setText(R.string.First_LongTime);
                        countdownTime.setText(time.getTimeRemaining());
                    } else if (time.getBlock().contains("B -")) {
                        blockText.setText(R.string.bBlock);
                        staticTime.setText(R.string.Second_LongTime);
                        countdownTime.setText(time.getTimeRemaining());
                    } else if (time.getBlock().contains("C -")) {
                        blockText.setText(R.string.cBlock);
                        staticTime.setText(R.string.Third_LongTime);
                        countdownTime.setText(time.getTimeRemaining());
                    } else if (time.getBlock().contains("D -")) {
                        blockText.setText(R.string.dBlock);
                        staticTime.setText(R.string.Fourth_LongTime);
                        countdownTime.setText(time.getTimeRemaining());
                    } else if (time.getBlock().contains("E -")) {
                        blockText.setText(R.string.eBlock);
                        staticTime.setText(R.string.First_LongTime);
                        countdownTime.setText(time.getTimeRemaining());
                    } else if (time.getBlock().contains("F -")) {
                        blockText.setText(R.string.fBlock);
                        staticTime.setText(R.string.Second_LongTime);
                        countdownTime.setText(time.getTimeRemaining());
                    } else if (time.getBlock().contains("G -")) {
                        blockText.setText(R.string.gBlock);
                        staticTime.setText(R.string.Third_LongTime);
                        countdownTime.setText(time.getTimeRemaining());
                    } else if (time.getBlock().contains("H -")) {
                        blockText.setText(R.string.hBlock);
                        staticTime.setText(R.string.Fourth_LongTime);
                        countdownTime.setText(time.getTimeRemaining());
                    }
                } else {
                    blockText.setText("- block");
                    staticTime.setText("--:--");
                    countdownTime.setText("--:--");
                }

                settings.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(regular.this, developer.class));
                    }
                });

            }

            public void onFinish() {
                finish();
            }
        }.start();

    }
}
