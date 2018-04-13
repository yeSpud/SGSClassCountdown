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
            currentTime = simpleDateFormat.parse(String.format("%s:%s:%s", String.valueOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)),
                    String.valueOf(Calendar.getInstance().get(Calendar.MINUTE)), String.valueOf(Calendar.getInstance().get(Calendar.SECOND))));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return currentTime;

    }

    public String getBlock() {
        String block = "None";

        weekday = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());

        try {

            if (weekday.equalsIgnoreCase("monday") || weekday.equalsIgnoreCase("tuesday") || weekday.equalsIgnoreCase("friday")) {
                // Its an 8 - period day

                if (getFormatTime().after(simpleDateFormat.parse("8:20:00")) && getFormatTime().before(simpleDateFormat.parse("9:00:00"))) {
                    // A block
                    block = "A - normal";
                } else if (getFormatTime().after(simpleDateFormat.parse("9:05:00")) && getFormatTime().before(simpleDateFormat.parse("9:45:00"))) {
                    // B block
                    block = "B - normal";
                } else if (getFormatTime().after(simpleDateFormat.parse("10:00:00")) && getFormatTime().before(simpleDateFormat.parse("10:45:00"))) {
                    // C block
                    block = "C - normal";
                } else if (getFormatTime().after(simpleDateFormat.parse("10:50:00")) && getFormatTime().before(simpleDateFormat.parse("11:30:00"))) {
                    // D block
                    block = "D - normal";
                } else if (getFormatTime().after(simpleDateFormat.parse("11:35:00")) && getFormatTime().before(simpleDateFormat.parse("12:15:00"))) {
                    // E block
                    block = "E - normal";
                } else if (getFormatTime().after(simpleDateFormat.parse("13:00:00")) && getFormatTime().before(simpleDateFormat.parse("13:40:00"))) {
                    // F block
                    block = "F - normal";
                } else if (getFormatTime().after(simpleDateFormat.parse("13:45:00")) && getFormatTime().before(simpleDateFormat.parse("14:25:00"))) {
                    // G block
                    block = "G - normal";
                } else if (getFormatTime().after(simpleDateFormat.parse("14:30:00")) && getFormatTime().before(simpleDateFormat.parse("15:10:00"))) {
                    // H block
                    block = "H - normal";
                } else {
                    // None
                    block = "None";
                }

            } else if (weekday.equalsIgnoreCase("wednesday")) {
                // Its an A day

                if (getFormatTime().after(simpleDateFormat.parse("8:20:00")) && getFormatTime().before(simpleDateFormat.parse("9:45:00"))) {
                    // A block
                    block = "A - long";
                } else if (getFormatTime().after(simpleDateFormat.parse("10:00:00")) && getFormatTime().before(simpleDateFormat.parse("11:25:00"))) {
                    // B block
                    block = "B - long";
                } else if (getFormatTime().after(simpleDateFormat.parse("12:05:00")) && getFormatTime().before(simpleDateFormat.parse("13:30:00"))) {
                    // C block
                    block = "C - long";
                } else if (getFormatTime().after(simpleDateFormat.parse("13:45:00")) && getFormatTime().before(simpleDateFormat.parse("15:10:00"))) {
                    // D block
                    block = "D - long";
                } else {
                    // None
                    block = "None";
                }

            } else if (weekday.equalsIgnoreCase("thursday")) {
                // Its an E day

                if (getFormatTime().after(simpleDateFormat.parse("8:20:00")) && getFormatTime().before(simpleDateFormat.parse("9:45:00"))) {
                    // E block
                    block = "E - long";
                } else if (getFormatTime().after(simpleDateFormat.parse("10:00:00")) && getFormatTime().before(simpleDateFormat.parse("11:25:00"))) {
                    // F block
                    block = "F - long";
                } else if (getFormatTime().after(simpleDateFormat.parse("12:05:00")) && getFormatTime().before(simpleDateFormat.parse("13:30:00"))) {
                    // G block
                    block = "G - long";
                } else if (getFormatTime().after(simpleDateFormat.parse("13:45:00")) && getFormatTime().before(simpleDateFormat.parse("15:10:00"))) {
                    // H block
                    block = "H - long";
                } else {
                    // None
                    block = "None";
                }

            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return block;

    }

    public String getTimeRemaining() {

        try {


            if (getBlock().equalsIgnoreCase("a - normal")) {
                checkTime = simpleDateFormat.parse("9:00:00");
            } else if (getBlock().equalsIgnoreCase("b - normal")) {
                checkTime = (simpleDateFormat.parse("9:45:00"));
            } // TODO: Finish this

        } catch (ParseException e) {
            e.printStackTrace();
        }

        long diff = checkTime.getTime() - currentTime.getTime();
        long seconds = diff / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;

        return String.format("%s:%s:%s", hours, minutes, seconds);
    }

}
