package com.stephenogden.sgsclasscountdownapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by Stephen Ogden on 4/13/18.
 * FTC 6128 | 7935
 * FRC 1595
 */
public class developer extends AppCompatActivity implements Runnable {

    Button back;

    TextView getFomratTime, getBlock, weekday;

    boolean running;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.developer);

        running = true;

        getFomratTime = findViewById(R.id.getFormatTime);
        getBlock = findViewById(R.id.getBlock);
        weekday = findViewById(R.id.weekday);

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
        time time = new time();

        getBlock.setText(time.getBlock());
        weekday.setText(time.weekday);

        Thread thread = new Thread(this);
        thread.start();


    }

    @Override
    public void run() {
        while(running) {
            final time time = new time();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                        getFomratTime.setText(time.getFormatTime().toString());

                    Thread.yield();
                }
            });
        }
    }

}
