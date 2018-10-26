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
class Core {

    private Calendar calendar = Calendar.getInstance();

    int[] getTime() {
        int s = calendar.get(Calendar.SECOND), m = calendar.get(Calendar.MINUTE), h = calendar.get(Calendar.HOUR_OF_DAY);
        int[] time = new int[3];
        time[0] = h;
        time[1] = m;
        time[2] = s;
        Log.i("Formatted time", Arrays.toString(time));
        return time;
    }

    long getTimeAsLong() {
        int s = calendar.get(Calendar.SECOND), m = calendar.get(Calendar.MINUTE), h = calendar.get(Calendar.HOUR_OF_DAY);
        long longTime, hoursToSeconds = h * 3600, minutesToSeconds = m * 60;
        longTime = s + minutesToSeconds + hoursToSeconds;
        Log.i("TimeAsLong", Long.toString(longTime));
        return longTime;
    }

    long timeToLong(int hour, int minute, int second) {
        long longTime, hoursToSeconds = hour * 3600, minutesToSeconds = minute * 60;
        longTime = second + minutesToSeconds + hoursToSeconds;
        Log.i("Time to long", String.format("%s:%s:%s -> %s", hour, minute, second, longTime));
        return longTime;
    }

    String getTimeRemaining() {
        String[] checkTime = new String[3];
        RegimeFiles regime = new RegimeFiles();
        String returnString = null;
        if (isAprilFirst()) {
            // TODO: On april 1st, count up from the start of class ;)
        } else {
            try {
                checkTime = regime.getTimesFromRegime(WeekType.getWeekType(), regime.getBlockFromRegime(getTimeAsLong()))[1].split(":");
            } catch (NullPointerException NPE) {
                // Bad regime, just go to 3:10 PM
                checkTime[0] = "15";
                checkTime[1] = "10";
                checkTime[2] = "0";
            }

            int hours = Integer.parseInt(checkTime[0]);
            int minutes = Integer.parseInt(checkTime[1]);
            int seconds = Integer.parseInt(checkTime[2]);

            Log.d("Check time", String.format(Locale.US, "%s:%s:%s", hours, minutes, seconds));

            long longTime = timeToLong(hours, minutes, seconds);
            long timeRemaining = longTime - getTimeAsLong();
            long remainingMinutes = timeRemaining / 60;
            if ((timeRemaining - (remainingMinutes * 60) <= 60) && remainingMinutes == 0) {
                returnString = String.format(Locale.US, "%02d", timeRemaining - (remainingMinutes * 60));
            } else {
                returnString = String.format(Locale.US, "%s:%02d", remainingMinutes, timeRemaining - (remainingMinutes * 60));
            }
        }
        Log.i("Time remaining", returnString);
        return returnString;
    }

    String changeBlockHeader(BlockNames blockName) {
        Log.d("EnteredBlockName", blockName.toString());

        // Setup a return string with an initial value
        String returnString = BlockNames.NoBlock.getName();

        if (!blockName.equals(BlockNames.NoBlock)) {
            DatabaseFile database = new DatabaseFile();

            // https://stackoverflow.com/questions/798545/what-is-the-java-operator-called-and-what-does-it-do
            returnString = String.format("%s will be over in:", database.getBlockName(blockName) != null ? database.getBlockName(blockName) : blockName.getName());
        }

        Log.i("ReturningBlockName", returnString);
        return returnString;
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
        long currentTime = getTimeAsLong();
        boolean isOver = ((currentTime < 30000) || (currentTime > 54600));
        Log.i("isDayOver", Boolean.toString(isOver));
        return isOver;
    }

    private boolean isAprilFirst() {
        // Check if its april
        if (getDate()[0] == Calendar.APRIL) {
            // Return of the date is the first
            return getDate()[1] == 1;
        } else {
            return false;
        }
    }

}
