package com.spud.sgsclasscountdownapp;

import com.spud.sgsclasscountdownapp.Regime.Class;

import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Stephen Ogden on 2/5/19.
 */
public class Timer extends Thread {

	/**
	 * TODO
	 *
	 * @param seconds
	 * @return
	 */
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

	/**
	 * TODO
	 *
	 * @param time
	 * @return
	 */
	public static int getHour(long time) {
		int hours = 0;
		while (time >= 3600) {
			time -= 3600;
			hours++;
		}
		return hours;
	}

	/**
	 * TODO
	 *
	 * @param time
	 * @return
	 */
	public static int getMinute(long time) {
		int minutes = 0;
		while (time >= 60) {
			time -= 60;
			minutes++;
		}
		return minutes;
	}

	/**
	 * TODO
	 *
	 * @return
	 */
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

	/**
	 * TODO
	 *
	 * @param currentClass
	 * @return
	 */
	public static long getTimeRemaining(Class currentClass) {
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
