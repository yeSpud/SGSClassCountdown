package com.stephenogden.sgsclasscountdownapp;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class regular extends AppCompatActivity {

    Calendar calendar = Calendar.getInstance();

    boolean isWeekend, running, lo;

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");

    Date checkTime, curTime;

    TextView count,staticTime,staticHeader,dynamicHeader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regular);

        count = findViewById(R.id.countdownTime);
        staticTime = findViewById(R.id.staticTime);
        staticHeader = findViewById(R.id.staticTimeHeader);
        dynamicHeader = findViewById(R.id.countdownHeader);

        try {
            curTime = simpleDateFormat.parse(String.format("%s:%s:%s",String.valueOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)),
                    String.valueOf(Calendar.getInstance().get(Calendar.MINUTE)),String.valueOf(Calendar.getInstance().get(Calendar.SECOND))));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String weekday = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());

        Log.e("weekday", weekday);
        Log.e("time", String.format("%s:%s:%s",String.valueOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)),
                String.valueOf(Calendar.getInstance().get(Calendar.MINUTE)),String.valueOf(Calendar.getInstance().get(Calendar.SECOND))));


        if (weekday.toLowerCase().equals("monday") || weekday.toLowerCase().equals("tuesday") || weekday.toLowerCase().equals("friday")) {
            staticTime.setText(R.string.G_STDTime);
            try {
                checkTime = simpleDateFormat.parse("14:25:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            running = true;
        } else if (weekday.toLowerCase().equals("thursday")) {
            staticTime.setText(R.string.Third_LongTime);
            try {
                checkTime = simpleDateFormat.parse("13:30:00");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            running = true;
        } else {
            staticTime.setText(R.string.noClass);
            running = false;
            staticHeader.setText(" ");
            staticHeader.setVisibility(View.INVISIBLE);
            dynamicHeader.setVisibility(View.INVISIBLE);
            dynamicHeader.setText(" ");
        }

        if (running) {
            Log.e("Passed", String.valueOf(curTime.after(checkTime)));
            if (curTime.after(checkTime)) {
                running = false;
            }
        }

        long inbetween = checkTime.getTime() - curTime.getTime();

        if (running) {

            new CountDownTimer(inbetween, 1000) {
                String hms = "";

                @SuppressLint("DefaultLocale")
                @Override
                public void onTick(long l) {
                    hms = String.format("%02d:%02d:%02d",
                            TimeUnit.MILLISECONDS.toHours(l),
                            TimeUnit.MILLISECONDS.toMinutes(l) -
                                    TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(l)), // The change is in this line
                            TimeUnit.MILLISECONDS.toSeconds(l) -
                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(l)));
                    Log.e("Time remaining", hms);
                    count.setText(hms);

                }

                @Override
                public void onFinish() {
                    count.setText(R.string.classOut);
                }
            }.start();

        } else {
            staticTime.setText(R.string.noClass);
        }

    }
}
