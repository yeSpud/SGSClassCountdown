package com.stephenogden.sgsclasscountdownapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
public class Core {

    String weekday;
    private Calendar calendar = Calendar.getInstance();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");
    private Date checkTime, currentTime;

    public Date getFormatTime() {

        try {
            currentTime = simpleDateFormat.parse(String.format("%s:%s:%s", String.valueOf(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)),
                    String.valueOf(Calendar.getInstance().get(Calendar.MINUTE)), String.valueOf(Calendar.getInstance().get(Calendar.SECOND))));

        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("getFormatTime", e.getMessage());
        }

        return currentTime;

    }

    public Block getBlock() {

        Block block = Block.NoBlock;

        Timer Timer = new Timer();

        try {
            if (read_file(Timer.context, developer.localStorage.getName()).startsWith("Auto")) {
                weekday = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
            } else if (read_file(Timer.context, developer.localStorage.getName()).startsWith("A")) {
                weekday = "Wednesday";
            } else if (read_file(Timer.context, developer.localStorage.getName()).startsWith("E")) {
                weekday = "Thursday";
            } else if (read_file(Timer.context, developer.localStorage.getName()).startsWith("8")) {
                weekday = "Monday";
            } else {
                Log.e("None of the above", read_file(Timer.context, developer.localStorage.getName()));
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
                    block = Block.ANormal;
                } else if (getFormatTime().after(simpleDateFormat.parse("9:05:00")) && getFormatTime().before(simpleDateFormat.parse("9:45:00"))) {
                    // B block
                    block = Block.BNormal;
                } else if (getFormatTime().after(simpleDateFormat.parse("10:00:00")) && getFormatTime().before(simpleDateFormat.parse("10:45:00"))) {
                    // C block
                    block = Block.CNormal;
                } else if (getFormatTime().after(simpleDateFormat.parse("10:50:00")) && getFormatTime().before(simpleDateFormat.parse("11:30:00"))) {
                    // D block
                    block = Block.DNormal;
                } else if (getFormatTime().after(simpleDateFormat.parse("11:35:00")) && getFormatTime().before(simpleDateFormat.parse("12:15:00"))) {
                    // E block
                    block = Block.ENormal;
                } else if (getFormatTime().after(simpleDateFormat.parse("11:35:00")) && getFormatTime().before(simpleDateFormat.parse("00:15:00"))) {
                    // Also E block, Core is retarded
                    block = Block.ENormal;
                } else if (getFormatTime().after(simpleDateFormat.parse("13:00:00")) && getFormatTime().before(simpleDateFormat.parse("13:40:00"))) {
                    // F block
                    block = Block.FNormal;
                } else if (getFormatTime().after(simpleDateFormat.parse("13:45:00")) && getFormatTime().before(simpleDateFormat.parse("14:25:00"))) {
                    // G block
                    block = Block.GNormal;
                } else if (getFormatTime().after(simpleDateFormat.parse("14:30:00")) && getFormatTime().before(simpleDateFormat.parse("15:10:00"))) {
                    // H block
                    block = Block.HNormal;
                } else {
                    // None
                    block = Block.NoBlock;
                }

            } else if (weekday.equalsIgnoreCase("wednesday")) {
                // Its an A day

                if (getFormatTime().after(simpleDateFormat.parse("8:20:00")) && getFormatTime().before(simpleDateFormat.parse("9:45:00"))) {
                    // A block
                    block = Block.ALong;
                } else if (getFormatTime().after(simpleDateFormat.parse("10:00:00")) && getFormatTime().before(simpleDateFormat.parse("11:25:00"))) {
                    // B block
                    block = Block.BLong;
                } else if (getFormatTime().after(simpleDateFormat.parse("12:05:00")) && getFormatTime().before(simpleDateFormat.parse("13:30:00"))) {
                    // C block
                    block = Block.CLong;
                } else if (getFormatTime().after(simpleDateFormat.parse("00:05:00")) && getFormatTime().before(simpleDateFormat.parse("13:30:00"))) {
                    // C block
                    block = Block.CNormal;
                } else if (getFormatTime().after(simpleDateFormat.parse("13:45:00")) && getFormatTime().before(simpleDateFormat.parse("15:10:00"))) {
                    // D block
                    block = Block.DNormal;
                } else {
                    // None
                    block = Block.NoBlock;
                }

            } else if (weekday.equalsIgnoreCase("thursday")) {
                // Its an E day

                if (getFormatTime().after(simpleDateFormat.parse("8:20:00")) && getFormatTime().before(simpleDateFormat.parse("9:45:00"))) {
                    // E block
                    block = Block.ELong;
                } else if (getFormatTime().after(simpleDateFormat.parse("10:00:00")) && getFormatTime().before(simpleDateFormat.parse("11:25:00"))) {
                    // F block
                    block = Block.FLong;
                } else if (getFormatTime().after(simpleDateFormat.parse("12:05:00")) && getFormatTime().before(simpleDateFormat.parse("13:30:00"))) {
                    // G block
                    block = Block.GLong;
                } else if (getFormatTime().after(simpleDateFormat.parse("00:05:00")) && getFormatTime().before(simpleDateFormat.parse("13:30:00"))) {
                    // G block
                    block = Block.GLong;
                } else if (getFormatTime().after(simpleDateFormat.parse("13:45:00")) && getFormatTime().before(simpleDateFormat.parse("15:10:00"))) {
                    // H block
                    block = Block.HLong;
                } else {
                    // None
                    block = Block.NoBlock;
                }

            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return block;
    }

    public String getTimeRemaining() {

        try {
            checkTime = simpleDateFormat.parse("15:10:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        try {

            if (getBlock().equals(Block.ANormal)) {
                checkTime = simpleDateFormat.parse("9:00:00");
            } else if (getBlock().equals(Block.BNormal)) {
                checkTime = (simpleDateFormat.parse("9:45:00"));
            } else if (getBlock().equals(Block.CNormal)) {
                checkTime = (simpleDateFormat.parse("10:45:00"));
            } else if (getBlock().equals(Block.DNormal)) {
                checkTime = (simpleDateFormat.parse("11:30:00"));
            } else if (getBlock().equals(Block.ENormal)) {
                checkTime = (simpleDateFormat.parse("12:15:00"));
            } else if (getBlock().equals(Block.FNormal)) {
                checkTime = simpleDateFormat.parse("13:40:00");
            } else if (getBlock().equals(Block.GNormal)) {
                checkTime = simpleDateFormat.parse("14:25:00");
            } else if (getBlock().equals(Block.HNormal)) {
                checkTime = simpleDateFormat.parse("15:10:00");
            } else if (getBlock().equals(Block.ALong) || getBlock().equals(Block.ELong)) {
                checkTime = simpleDateFormat.parse("9:45:00");
            } else if (getBlock().equals(Block.BLong) || getBlock().equals(Block.FLong)) {
                checkTime = simpleDateFormat.parse("11:25:00");
            } else if (getBlock().equals(Block.CLong) || getBlock().equals(Block.GLong)) {
                checkTime = simpleDateFormat.parse("13:30:00");
            } else if (getBlock().equals(Block.DLong) || getBlock().equals(Block.HLong)) {
                checkTime = simpleDateFormat.parse("15:10:00");
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        long diff = checkTime.getTime() - getFormatTime().getTime();
        long seconds = diff / 1000;
        long minutes = diff / 60000;

        return String.format("%s:%02d", minutes, seconds - (minutes * 60));
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

    public String changeBlock(Block block) {

        if (block.equals(Block.ANormal) || block.equals(Block.ALong)) {
            return "A block will end in:";
        } else if (block.equals(Block.BNormal) || block.equals(Block.BLong)) {
            return "B block will end in:";
        } else if (block.equals(Block.CNormal) || block.equals(Block.CLong)) {
            return "C block will end in:";
        } else if (block.equals(Block.DNormal) || block.equals(Block.DLong)) {
            return "D block will end in:";
        } else if (block.equals(Block.ENormal) || block.equals(Block.ELong)) {
            return "E block will end in:";
        } else if (block.equals(Block.FNormal) || block.equals(Block.FLong)) {
            return "F block will end in:";
        } else if (block.equals(Block.GNormal) || block.equals(Block.GLong)) {
            return "G block will end in:";
        } else if (block.equals(Block.HNormal) || block.equals(Block.HLong)) {
            return "H block will end in:";
        } else {
            return null;
        }
    }

}
