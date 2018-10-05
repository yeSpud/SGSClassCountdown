package com.spud.sgsclasscountdownapp;

import android.util.Log;

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
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss", Locale.US);
    private Date checkTime, currentTime;

    public Date getFormatTime() {

        try {
            String h,m,s;
            h = String.valueOf(Calendar.getInstance().get(Calendar.HOUR));
            m = String.valueOf(Calendar.getInstance().get(Calendar.MINUTE));
            s = String.valueOf(Calendar.getInstance().get(Calendar.SECOND));
            currentTime = simpleDateFormat.parse(String.format("%s:%s:%s", h, m, s));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.i("Formated time", currentTime.toString());
        return currentTime;

    }

    public Block getBlock() {

        Block block = Block.NoBlock;

        Database database = new Database();

        if (database.databaseExists()) {
            if (database.getUpdateType().equals(Database.updateType.Automatic)) {
                weekday = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
            } else if (database.getUpdateType().equals(Database.updateType.ManualADay)) {
                weekday = "Wednesday";
            } else if (database.getUpdateType().equals(Database.updateType.ManualEDay)) {
                weekday = "Thursday";
            } else if (database.getUpdateType().equals(Database.updateType.ManualFullDay)) {
                weekday = "Monday";
            } else {
                Log.e("E", "Cannot identify the updateType from database");
                weekday = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
            }
            Log.i("Weekday", weekday);
        } else {
            weekday = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        }

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

        Log.i("Block", block.name());
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

        Log.i("Time remaining", String.format(Locale.US, "%s:%02d", minutes, seconds - (minutes * 60)));
        return String.format(Locale.US, "%s:%02d", minutes, seconds - (minutes * 60));
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
