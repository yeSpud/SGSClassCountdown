package com.stephenogden.sgsclasscountdownapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
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

    String weekday;
    private Calendar calendar = Calendar.getInstance();
    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");
    private Date checkTime, currentTime;

    public Date getFormatTime() {

        try {
            currentTime = simpleDateFormat.parse(String.format("%s:%s:%s", String.valueOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)),
                    String.valueOf(Calendar.getInstance().get(Calendar.MINUTE)), String.valueOf(Calendar.getInstance().get(Calendar.SECOND))));

        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("getFormatTime",e.getMessage());
        }

        return currentTime;

    }

    public String getBlock() {
        String block = "None";

        try {
            if (read_file(regular.context, developer.localStorage.getName()).startsWith("Auto")) {
                weekday = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
            } else if (read_file(regular.context, developer.localStorage.getName()).startsWith("A")) {
                weekday = "Wednesday";
            } else if (read_file(regular.context, developer.localStorage.getName()).startsWith("E")) {
                weekday = "Thursday";
            } else if (read_file(regular.context, developer.localStorage.getName()).startsWith("8")) {
                weekday = "Monday";
            } else {
                Log.e("None of the above", read_file(regular.context, developer.localStorage.getName()));
                weekday = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
            }
        } catch (Exception e) {
            weekday = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
            Log.e("Error", "Cannot override");
            Log.e("Reason", e.toString());
        }
        //weekday = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());

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
                } else if (getFormatTime().after(simpleDateFormat.parse("11:35:00")) && getFormatTime().before(simpleDateFormat.parse("00:15:00"))) {
                    // Also E block, time is retarded
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
                } else if (getFormatTime().after(simpleDateFormat.parse("00:05:00")) && getFormatTime().before(simpleDateFormat.parse("13:30:00"))) {
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
                } else if (getFormatTime().after(simpleDateFormat.parse("00:05:00")) && getFormatTime().before(simpleDateFormat.parse("13:30:00"))) {
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

    @SuppressLint("DefaultLocale")
    public String getTimeRemaining() {

        try {
            checkTime = simpleDateFormat.parse("15:10:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {

            if (getBlock().equalsIgnoreCase("a - normal")) {
                checkTime = simpleDateFormat.parse("9:00:00");
            } else if (getBlock().equalsIgnoreCase("b - normal")) {
                checkTime = (simpleDateFormat.parse("9:45:00"));
            } else if (getBlock().equalsIgnoreCase("c - normal")) {
                checkTime = (simpleDateFormat.parse("10:45:00"));
            } else if (getBlock().equalsIgnoreCase("d - normal")) {
                checkTime = (simpleDateFormat.parse("11:30:00"));
            } else if (getBlock().equalsIgnoreCase("e - normal")) {
                checkTime = (simpleDateFormat.parse("12:15:00"));
            } else if (getBlock().equalsIgnoreCase("f - normal")) {
                checkTime = simpleDateFormat.parse("13:40:00");
            } else if (getBlock().equalsIgnoreCase("g - normal")) {
                checkTime = simpleDateFormat.parse("14:25:00");
            } else if (getBlock().equalsIgnoreCase("h - block")) {
                checkTime = simpleDateFormat.parse("15:10:00");
            } else if (getBlock().equalsIgnoreCase("a - long") || getBlock().equalsIgnoreCase("e - long")) {
                checkTime = simpleDateFormat.parse("9:45:00");
            } else if (getBlock().equalsIgnoreCase("b - long") || getBlock().equalsIgnoreCase("f - long")) {
                checkTime = simpleDateFormat.parse("11:25:00");
            } else if (getBlock().equalsIgnoreCase("c - long") || getBlock().equalsIgnoreCase("g - long")) {
                checkTime = simpleDateFormat.parse("13:30:00");
            } else if (getBlock().equalsIgnoreCase("d - long") || getBlock().equalsIgnoreCase("h - long")) {
                checkTime = simpleDateFormat.parse("15:10:00");
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        long diff = checkTime.getTime() - getFormatTime().getTime();
        long seconds = diff / 1000;
        long minutes = diff / 60000;

        return String.format("%s:%02d", minutes, seconds - (minutes*60));
    }

    public String read_file(Context context, String filename) {
        try {
            FileInputStream fis = context.openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            return "";
        } catch (UnsupportedEncodingException e) {
            return "";
        } catch (IOException e) {
            return "";
        }
    }

}
