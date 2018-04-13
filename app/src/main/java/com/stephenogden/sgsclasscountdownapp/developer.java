package com.stephenogden.sgsclasscountdownapp;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Stephen Ogden on 4/13/18.
 * FTC 6128 | 7935
 * FRC 1595
 */

public class developer extends AppCompatActivity {

    Button back;

    TextView getFormatTime, getBlock, weekday, getTimeRemaining;

    boolean running;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.developer);

        running = true;

        getFormatTime = findViewById(R.id.getFormatTime);
        getBlock = findViewById(R.id.getBlock);
        weekday = findViewById(R.id.weekday);
        getTimeRemaining = findViewById(R.id.getTimeRemaining);

        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                running = false;
                finish();
            }
        });

    }

    protected void onResume() {
        super.onResume();
        final time time = new time();

        new CountDownTimer(Long.MAX_VALUE-1, 1000) {
            public void onTick(long millisUntilFinished) {

                getFormatTime.setText(time.getFormatTime().toString());
                getBlock.setText(time.getBlock());
                weekday.setText(time.weekday);
                getTimeRemaining.setText(time.getTimeRemaining());

                back.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cancel();
                        finish();
                    }
                });
            }

            public void onFinish() {
                finish();
            }
        }.start();



    }

}
