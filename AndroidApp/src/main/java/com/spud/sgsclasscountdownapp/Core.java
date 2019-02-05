package com.spud.sgsclasscountdownapp;

import android.util.Log;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Stephen Ogden on 4/10/18.
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

    // FIXME
    String getTimeRemaining() {
        String[] checkTime = new String[3];

        String returnString = null;
        if (isAprilFirst()) {
            // TODO: On april 1st, count up from the start of class ;)
        } else {

        }
        Log.i("Time remaining", returnString);
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

    private boolean isAprilFirst() {
        // Check if its april
        if (this.getDate()[0] == Calendar.APRIL) {
            // Return of the date is the first
            return getDate()[1] == 1;
        } else {
            return false;
        }
    }

}
