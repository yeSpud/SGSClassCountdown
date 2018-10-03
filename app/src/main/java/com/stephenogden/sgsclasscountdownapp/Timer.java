package com.stephenogden.sgsclasscountdownapp;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.io.FileWriter;

public class Timer extends AppCompatActivity {

    public Context context;

    private TextView block, countdown;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timer);

        context = getApplicationContext();

        block = findViewById(R.id.blockInfo);
        countdown = findViewById(R.id.countdown);

        findViewById(R.id.gotoSettings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Timer.this, settings.class));
            }
        });

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

    }


    protected void onResume() {
        super.onResume();

        final Core Core = new Core();

        CountDownTimer timer = new CountDownTimer(Long.MAX_VALUE - 1, 1000) {
            @Override
            public void onTick(long l) {
                if (Core.getBlock().contains("normal")) {
                    if (Core.getBlock().contains("A -")) {
                        block.setText(Core.changeBlock(Block.ANormal));
                        countdown.setText(Core.getTimeRemaining());
                    } else if (Core.getBlock().contains("B -")) {
                        block.setText(Core.changeBlock(Block.BNormal));
                        countdown.setText(Core.getTimeRemaining());
                    } else if (Core.getBlock().contains("C -")) {
                        block.setText(Core.changeBlock(Block.CNormal));
                        countdown.setText(Core.getTimeRemaining());
                    } else if (Core.getBlock().contains("D -")) {
                        block.setText(Core.changeBlock(Block.DNormal));
                        countdown.setText(Core.getTimeRemaining());
                    } else if (Core.getBlock().contains("E -")) {
                        block.setText(Core.changeBlock(Block.ENormal));
                        countdown.setText(Core.getTimeRemaining());
                    } else if (Core.getBlock().contains("F -")) {
                        block.setText(Core.changeBlock(Block.FNormal));
                        countdown.setText(Core.getTimeRemaining());
                    } else if (Core.getBlock().contains("G -")) {
                        block.setText(Core.changeBlock(Block.GNormal));
                        countdown.setText(Core.getTimeRemaining());
                    } else if (Core.getBlock().contains("H -")) {
                        block.setText(Core.changeBlock(Block.HNormal));
                        countdown.setText(Core.getTimeRemaining());
                    }
                } else if (Core.getBlock().contains("long")) {
                    if (Core.getBlock().contains("A -")) {
                        block.setText(Core.changeBlock(Block.ALong));
                        countdown.setText(Core.getTimeRemaining());
                    } else if (Core.getBlock().contains("B -")) {
                        block.setText(Core.changeBlock(Block.BLong));
                        countdown.setText(Core.getTimeRemaining());
                    } else if (Core.getBlock().contains("C -")) {
                        block.setText(Core.changeBlock(Block.CLong));
                        countdown.setText(Core.getTimeRemaining());
                    } else if (Core.getBlock().contains("D -")) {
                        block.setText(Core.changeBlock(Block.DLong));
                        countdown.setText(Core.getTimeRemaining());
                    } else if (Core.getBlock().contains("E -")) {
                        block.setText(Core.changeBlock(Block.ELong));
                        countdown.setText(Core.getTimeRemaining());
                    } else if (Core.getBlock().contains("F -")) {
                        block.setText(Core.changeBlock(Block.FLong));
                        countdown.setText(Core.getTimeRemaining());
                    } else if (Core.getBlock().contains("G -")) {
                        block.setText(Core.changeBlock(Block.GLong));
                        countdown.setText(Core.getTimeRemaining());
                    } else if (Core.getBlock().contains("H -")) {
                        block.setText(Core.changeBlock(Block.HLong));
                        countdown.setText(Core.getTimeRemaining());
                    }
                } else {
                    block.setText("- block");
                    countdown.setText("--:--");
                }
            }

            @Override
            public void onFinish() {

            }
        }.start();
    }
}
