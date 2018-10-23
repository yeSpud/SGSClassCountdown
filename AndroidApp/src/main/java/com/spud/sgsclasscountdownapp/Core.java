package com.spud.sgsclasscountdownapp;

import android.util.Log;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

// TODO: On april 1st, count up from the start of class

/**
 * Created by Stephen Ogden on 4/10/18.
 * FTC 6128 | 7935
 * FRC 1595
 */
public class Core {

    private Calendar calendar = Calendar.getInstance();

    public int[] getTime() {
        int s = calendar.get(Calendar.SECOND), m = calendar.get(Calendar.MINUTE), h = calendar.get(Calendar.HOUR_OF_DAY);
        int[] time = new int[3];
        time[0] = h;
        time[1] = m;
        time[2] = s;
        Log.i("Formatted time", Arrays.toString(time));
        return time;
    }

    long timeToLong(int[] time) {
        int h = time[0], m = time[1], s = time[2];
        long longTime, hoursToSeconds = h * 3600, minutesToSeconds = m * 60;
        longTime = s + minutesToSeconds + hoursToSeconds;
        Log.i("Time to long", Arrays.toString(time) + "->" + Long.toString(longTime));
        return longTime;
    }

    long timeToLong(int hour, int minute, int second) {
        long longTime, hoursToSeconds = hour * 3600, minutesToSeconds = minute * 60;
        longTime = second + minutesToSeconds + hoursToSeconds;
        Log.i("Time to long", String.format("%s:%s:%s -> %s", hour, minute, second, longTime));
        return longTime;
    }

    // TODO: Create a system for special schedules
    // TODO: Redo
    String getTimeRemaining() {
        String[] checkTime = new String[3];
        RegimeFiles regime = new RegimeFiles();
        try {
            checkTime = regime.getTimes(WeekType.getWeekType(), regime.getBlockFromRegime(timeToLong(getTime())))[1].split(":");
        } catch (NullPointerException NPE) {
            // Wrong regime
            checkTime[0] = "15";
            checkTime[1] = "10";
            checkTime[2] = "0";
        }

        int hours = Integer.parseInt(checkTime[0]);
        int minutes = Integer.parseInt(checkTime[1]);
        int seconds = Integer.parseInt(checkTime[2]);

        Log.d("Check time", String.format(Locale.US, "%s:%s:%s", hours, minutes, seconds));

        long longTime = timeToLong(hours, minutes, seconds);
        long timeRemaining = longTime - timeToLong(getTime());
        long remainingMinutes = timeRemaining / 60;
        String returnString = String.format(Locale.US, "%s:%02d", remainingMinutes, timeRemaining - (remainingMinutes * 60));
        Log.i("Time remaining", returnString);
        return returnString;
    }

    // TODO: Create a system for special schedules
    String changeBlockHeader(Block block) {
        DatabaseFile database = new DatabaseFile();
        // https://stackoverflow.com/questions/6705955/why-switch-is-faster-than-if
        // https://stackoverflow.com/questions/798545/what-is-the-java-operator-called-and-what-does-it-do
        Log.d("Change block", block.name());
        switch (block) {
            case ANormal:
                return (database.getBlockName(Block.ANormal).equals("")) ? "A block will end in:" : String.format("%s will end in:", database.getBlockName(Block.ANormal));
            case BNormal:
                return (database.getBlockName(Block.BNormal).equals("")) ? "B block will end in:" : String.format("%s will end in:", database.getBlockName(Block.BNormal));
            case CNormal:
                return (database.getBlockName(Block.CNormal).equals("")) ? "C block will end in:" : String.format("%s will end in:", database.getBlockName(Block.CNormal));
            case DNormal:
                return (database.getBlockName(Block.DNormal).equals("")) ? "D block will end in:" : String.format("%s will end in:", database.getBlockName(Block.DNormal));
            case ENormal:
                return (database.getBlockName(Block.ENormal).equals("")) ? "E block will end in:" : String.format("%s will end in:", database.getBlockName(Block.ENormal));
            case FNormal:
                return (database.getBlockName(Block.FNormal).equals("")) ? "F block will end in:" : String.format("%s will end in:", database.getBlockName(Block.FNormal));
            case GNormal:
                return (database.getBlockName(Block.GNormal).equals("")) ? "G block will end in:" : String.format("%s will end in:", database.getBlockName(Block.GNormal));
            case HNormal:
                return (database.getBlockName(Block.HNormal).equals("")) ? "H block will end in:" : String.format("%s will end in:", database.getBlockName(Block.HNormal));
            case ALong:
                return (database.getBlockName(Block.ALong).equals("")) ? "A block will end in:" : String.format("%s will end in:", database.getBlockName(Block.ALong));
            case BLong:
                return (database.getBlockName(Block.BLong).equals("")) ? "B block will end in:" : String.format("%s will end in:", database.getBlockName(Block.BLong));
            case CLong:
                return (database.getBlockName(Block.CLong).equals("")) ? "C block will end in:" : String.format("%s will end in:", database.getBlockName(Block.CLong));
            case DLong:
                return (database.getBlockName(Block.DLong).equals("")) ? "D block will end in:" : String.format("%s will end in:", database.getBlockName(Block.DLong));
            case ELong:
                return (database.getBlockName(Block.ELong).equals("")) ? "E block will end in:" : String.format("%s will end in:", database.getBlockName(Block.ELong));
            case FLong:
                return (database.getBlockName(Block.FLong).equals("")) ? "F block will end in:" : String.format("%s will end in:", database.getBlockName(Block.FLong));
            case GLong:
                return (database.getBlockName(Block.GLong).equals("")) ? "G block will end in:" : String.format("%s will end in:", database.getBlockName(Block.GLong));
            case HLong:
                return (database.getBlockName(Block.HLong).equals("")) ? "H block will end in:" : String.format("%s will end in:", database.getBlockName(Block.HLong));
            case LunchNormal:
                return "Lunch will be over in:";
            case LunchLong:
                return "Lunch will be over in:";
            default:
                return null;
        }
    }

    int[] getDate() {
        int date[] = new int[3];
        int day, month, year;
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.WEEK_OF_YEAR);
        year = calendar.get(Calendar.YEAR);
        date[0] = month;
        date[1] = day;
        date[2] = year;

        Log.i("Date", Arrays.toString(date));
        return date;
    }

    boolean isDayOver() {
        // Return if the time is either before 8:20 or after 3:10
        long currentTime = timeToLong(getTime());
        boolean isOver = ((currentTime < 30000) || (currentTime > 54600));
        Log.i("isDayOver", Boolean.toString(isOver));
        return isOver;
    }

}
