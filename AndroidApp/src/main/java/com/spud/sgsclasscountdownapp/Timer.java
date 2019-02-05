package com.spud.sgsclasscountdownapp;

import com.spud.sgsclasscountdownapp.Regime.Regime;

import java.util.Calendar;

/**
 * Created by Stephen Ogden on 2/5/19.
 */
public class Timer extends Thread {

    public volatile boolean enable = false;

    public void run() {
        while (!Thread.interrupted()) {
            // If enabled, update the HUD
            if (enable) {

            }
        }
    }


    private int getTimeRemaining() {
        // TODO

        // Get the current time in seconds (since the start of the day)
        long currentTime = this.getCurrentTime();


        Regime regime = Regime.loadRegime(/* TODO: Get the current regime from settings*/);


        return 0;
    }

    private long getCurrentTime() {
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
}
