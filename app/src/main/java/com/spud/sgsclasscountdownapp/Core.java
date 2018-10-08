package com.spud.sgsclasscountdownapp;

import android.util.Log;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Stephen Ogden on 4/10/18.
 * FTC 6128 | 7935
 * FRC 1595
 */
public class Core {

    private Calendar calendar = Calendar.getInstance();

    public int[] getTime() {

        int s = calendar.get(Calendar.SECOND);
        int m = calendar.get(Calendar.MINUTE);
        int h = calendar.get(Calendar.HOUR_OF_DAY);
        Log.i("S", Integer.toString(s));
        Log.i("M", Integer.toString(m));
        Log.i("H", Integer.toString(h));
        int[] time = new int[3];
        time[0] = h;
        time[1] = m;
        time[2] = s;
        Log.i("Formatted time", Arrays.toString(time));
        return time;

    }

    private long timeToLong(int[] time) {
        long longTime;

        int h = time[0];
        int m = time[1];
        int s = time[2];

        long hoursToMinutes = h * 60;
        long minutesToSeconds = hoursToMinutes + (m * 60);
        longTime = s + minutesToSeconds;
        Log.i("Time to long", Arrays.toString(time) + "->" + Long.toString(longTime));

        return longTime;
    }

    private long timeToLong(int hour, int minute, int second) {
        long longTime;
        long hoursToMinutes = hour * 60;
        long minutesToSeconds = hoursToMinutes + (minute * 60);
        longTime = second + minutesToSeconds;
        Log.i("Time to long", String.format("%s:%s:%s -> %s", hour, minute, second, longTime));

        return longTime;
    }

    Block getBlock() {

        Block block = Block.NoBlock;

        Database database = new Database();

        String weekday;


        if (database.databaseExists()) {
            if (database.getUpdateType().equals(Database.updateType.Automatic)) {
                // TODO: Get from URL
                weekday = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
            } else if (database.getUpdateType().equals(Database.updateType.ManualADay)) {
                weekday = "Wednesday";
            } else if (database.getUpdateType().equals(Database.updateType.ManualEDay)) {
                weekday = "Thursday";
            } else if (database.getUpdateType().equals(Database.updateType.ManualFullDay)) {
                weekday = "Monday";
            } else if (database.getUpdateType().equals(Database.updateType.BuiltIn)) {
                weekday = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
            } else {
                Log.e("E", "Cannot identify the updateType from database");
                weekday = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
            }
            Log.i("Weekday", weekday);
        } else {
            weekday = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        }

        if (!weekday.equalsIgnoreCase("saturday") && !weekday.equalsIgnoreCase("sunday")) {
            if (weekday.equalsIgnoreCase("monday") || weekday.equalsIgnoreCase("tuesday") || weekday.equalsIgnoreCase("friday")) {
                Log.i("Schedule", "Full day");
                if (timeToLong(getTime()) > timeToLong(8, 20, 0) && timeToLong(getTime()) < timeToLong(9, 0, 0)) {
                    block = Block.ANormal;
                } else if (timeToLong(getTime()) > timeToLong(9, 5, 0) && timeToLong(getTime()) < timeToLong(9, 45, 0)) {
                    block = Block.BNormal;
                } else if (timeToLong(getTime()) > timeToLong(10, 0, 0) && timeToLong(getTime()) < timeToLong(10, 45, 0)) {
                    block = Block.CNormal;
                } else if (timeToLong(getTime()) > timeToLong(10, 50, 0) && timeToLong(getTime()) < timeToLong(11, 30, 0)) {
                    block = Block.DNormal;
                } else if (timeToLong(getTime()) > timeToLong(11, 35, 0) && timeToLong(getTime()) < timeToLong(12, 15, 0)) {
                    block = Block.ENormal;
                    // TODO: Test for 0,15,0
                } else if (timeToLong(getTime()) > timeToLong(13, 0, 0) && timeToLong(getTime()) < timeToLong(13, 40, 0)) {
                    block = Block.FNormal;
                } else if (timeToLong(getTime()) > timeToLong(13, 45, 0) && timeToLong(getTime()) < timeToLong(14, 25, 0)) {
                    block = Block.GNormal;
                } else if (timeToLong(getTime()) > timeToLong(14, 30, 0) && timeToLong(getTime()) < timeToLong(15, 0, 0)) {
                    block = Block.HNormal;
                }
            } else {
                Log.i("Schedule", "Long day");
                if (timeToLong(getTime()) > timeToLong(8, 20, 0) && timeToLong(getTime()) < timeToLong(9, 45, 9)) {
                    block = (weekday.equalsIgnoreCase("wednesday") ? Block.ALong : (weekday.equalsIgnoreCase("thursday") ? Block.ELong : Block.NoBlock));
                } else if (timeToLong(getTime()) > timeToLong(10, 0, 0) && timeToLong(getTime()) < timeToLong(11, 25, 0)) {
                    block = (weekday.equalsIgnoreCase("wednesday") ? Block.BLong : (weekday.equalsIgnoreCase("thursday") ? Block.FLong : Block.NoBlock));
                } else if (timeToLong(getTime()) > timeToLong(12, 5, 0) && timeToLong(getTime()) < timeToLong(13, 30, 0)) {
                    block = (weekday.equalsIgnoreCase("wednesday") ? Block.CLong : (weekday.equalsIgnoreCase("thursday") ? Block.GLong : Block.NoBlock));
                } else if (timeToLong(getTime()) > timeToLong(13, 45, 0) && timeToLong(getTime()) < timeToLong(15, 10, 0)) {
                    block = (weekday.equalsIgnoreCase("wednesday") ? Block.DLong : (weekday.equalsIgnoreCase("thursday") ? Block.HLong : Block.NoBlock));
                } else {
                    block = Block.NoBlock;
                }
            }
        }
        Log.i("Block", block.name());
        return block;
    }

    String getTimeRemaining() {
        long checkTime = timeToLong(15, 10, 0);
        if (getBlock().equals(Block.ANormal)) {
            checkTime = timeToLong(9, 0, 0);
        } else if (getBlock().equals(Block.BNormal)) {
            checkTime = timeToLong(9, 45, 0);
        } else if (getBlock().equals(Block.CNormal)) {
            checkTime = timeToLong(10, 45, 0);
        } else if (getBlock().equals(Block.DNormal)) {
            checkTime = timeToLong(11, 30, 0);
        } else if (getBlock().equals(Block.ENormal)) {
            checkTime = timeToLong(12, 15, 0);
        } else if (getBlock().equals(Block.FNormal)) {
            checkTime = timeToLong(13, 40, 0);
        } else if (getBlock().equals(Block.GNormal)) {
            checkTime = timeToLong(14, 25, 0);
        } else if (getBlock().equals(Block.HNormal)) {
            checkTime = timeToLong(15, 10, 0);
        } else if (getBlock().equals(Block.ALong) || getBlock().equals(Block.ELong)) {
            checkTime = timeToLong(9, 45, 0);
        } else if (getBlock().equals(Block.BLong) || getBlock().equals(Block.FLong)) {
            checkTime = timeToLong(11, 25, 0);
        } else if (getBlock().equals(Block.CLong) || getBlock().equals(Block.GLong)) {
            checkTime = timeToLong(13, 30, 0);
        } else if (getBlock().equals(Block.DLong) || getBlock().equals(Block.HLong)) {
            checkTime = timeToLong(15, 10, 0);
        }

        long seconds = checkTime - timeToLong(getTime());
        long minutes = seconds / 60;

        Log.i("Time remaining", String.format(Locale.US, "%s:%02d", minutes, seconds - (minutes * 60)));
        return String.format(Locale.US, "%s:%02d", minutes, seconds - (minutes * 60));
    }

    String changeBlock(Block block) {

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
