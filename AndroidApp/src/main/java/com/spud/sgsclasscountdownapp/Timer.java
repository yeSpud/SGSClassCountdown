package com.spud.sgsclasscountdownapp;

import com.spud.sgsclasscountdownapp.Activities.Main;
import com.spud.sgsclasscountdownapp.Regime.Class;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Stephen Ogden on 2/5/19.
 */
public class Timer extends Thread {

    public volatile boolean enable = false;

    public static String formatTimeRemaining(long seconds) {
        int minutes = 0;
        while (seconds > 60) {
            minutes++;
            seconds -= 60;
        }

        if (minutes > 0) {
            return String.format(Locale.ENGLISH, "%02d:%02d", minutes, seconds);
        } else {
            return String.format("%02d", seconds);
        }

    }

    public static int getHour(long time) {
        int hours = 0;
        while (time >= 3600) {
            time -= 3600;
            hours++;
        }
        return hours;
    }

    public static int getMinute(long time) {
        int minutes = 0;
        while (time >= 60) {
            time -= 60;
            minutes++;
        }
        return minutes;
    }

    public static long getCurrentTime() {
        // https://stackoverflow.com/questions/4389500/how-can-i-find-the-amount-of-seconds-passed-from-the-midnight-with-java
        Calendar c = Calendar.getInstance();
        long now = c.getTimeInMillis();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        long passed = now - c.getTimeInMillis();
        return passed / 1000;
    }

    public static Date longToTime(long seconds) {
        // TODO
        return null;
    }

    public void run() {
        Main app = new Main();
        while (!Thread.interrupted()) {
            // If enabled, update the HUD
            if (enable) {

                // Get the classes from the regime
                Class currentClass = Class.getClass(Main.currentRegeme, Timer.getCurrentTime());
                app.updateTime(currentClass, Timer.formatTimeRemaining(this.getTimeRemaining(currentClass)));
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private long getTimeRemaining(Class currentClass) {
        // Get the current time in seconds (since the start of the day)
        long currentTime = Timer.getCurrentTime();

        // If the class is still null, then return 0, as there is no class at the moment
        if (currentClass == null) {
            return 0;
        } else {
            // Return the remaining time.
            return currentClass.getEndTime() - currentTime;
        }
    }

    private boolean isAprilFirst() {
        // TODO
        return false;
    }
}
