package com.stephenogden.sgsclasscountdownapp;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Stephen Ogden on 4/10/18.
 * FTC 6128 | 7935
 * FRC 1595
 */
public class time {

    Calendar calendar = Calendar.getInstance();

    @SuppressLint("SimpleDateFormat")
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");

    Date checkTime, currentTime;

    String weekday;

    public Date getFormatTime() {

        try {
            currentTime = simpleDateFormat.parse(String.format("%s:%s:%s",String.valueOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)),
                    String.valueOf(Calendar.getInstance().get(Calendar.MINUTE)),String.valueOf(Calendar.getInstance().get(Calendar.SECOND))));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return currentTime;

    }

    public String getBlock() {
        String block = "H";

        weekday = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        try {
        if (weekday.equalsIgnoreCase("monday") || weekday.equalsIgnoreCase("tuesday") || weekday.equalsIgnoreCase("friday")) {
            // Its an 8 - period day

                if (getFormatTime().after(simpleDateFormat.parse("8:20:00")) && getFormatTime().before(simpleDateFormat.parse("9:00:00"))) {
                    // A block
                    block = "A";
                } else if (getFormatTime().after(simpleDateFormat.parse("9:05:00")) && getFormatTime().before(simpleDateFormat.parse("9:45:00"))) {
                    // B block
                    block = "B";
                } else if (getFormatTime().after(simpleDateFormat.parse("10:00:00")) && getFormatTime().before(simpleDateFormat.parse("10:45:00"))) {
                    // C block
                    block = "C";
                } else if (getFormatTime().after(simpleDateFormat.parse("10:50:00")) && getFormatTime().before(simpleDateFormat.parse("11:30:00"))) {
                    
                }


        }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return block;

    }

    public String getTimeRemaining(String block) {

        return "00:00";
    }

}
