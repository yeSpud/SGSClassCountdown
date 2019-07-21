package com.spud.ClassCountdown;

import java.util.Calendar;

/**
 * Created by Stephen Ogden on 2/5/19.
 */
public class Timer extends Thread {

	/**
	 * Format the number of seconds remaining to a countdown like string (ex. 20:37 for 20 minutes and 37 seconds).
	 *
	 * @param seconds The current time remaining.
	 * @return The formatted string.
	 */
	public static String formatTimeRemaining(long seconds) {
		int minutes = 0;
		while (seconds >= 60) {
			minutes++;
			seconds -= 60;
		}

		if (minutes > 0) {
			return String.format(java.util.Locale.ENGLISH, "%d:%02d", minutes, seconds);
		} else {
			return String.format("%02d", seconds);
		}

	}

	/**
	 * Returns the hour from a long time object.
	 *
	 * @param seconds The time in seconds since midnight.
	 * @return The current hour (as an int)
	 */
	public static int getHour(long seconds) {
		return (int) Math.floor(seconds / 3600.0d);
	}

	/**
	 * Returns the minute from a long time object. It should be noted that this function also deducts hours, so it just returns minutes.
	 * <p>
	 * IE. If the current minute minute value is 61 minutes it will return 1, and if it's 59 it will return 59.
	 *
	 * @param seconds The time in seconds since midnight.
	 * @return The current minute (as an int)
	 */
	public static int getMinute(long seconds) {
		return (int) Math.floor((seconds / 60.0d) % 60);
	}

	/**
	 * Gets the current time since midnight in seconds.
	 *
	 * @return The time elapsed in seconds since midnight.
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
	 * Gets the time remaining in the current class object.
	 *
	 * @param currentClass The current class as a class object.
	 * @return The time remaining in seconds.
	 */
	public static long getTimeRemaining(com.spud.ClassCountdown.Regime.Class currentClass) {
		// Get the current time in seconds (since the start of the day)
		long currentTime = Timer.getCurrentTime();

		// If the class is still null, then return 0, as there is no class at the moment
		if (currentClass == null) {
			return 0;
		} else {

			// Return the time remaining unless its april first then return a joke ;)
			return Timer.isAprilFirst() ? currentTime - currentClass.getStartTime() : currentClass.getEndTime() - currentTime;
		}
	}

	/**
	 * Returns if its april first or not.
	 * <p>
	 * Happy april fools!
	 * <p>
	 * :D
	 *
	 * @return Returns whether or not it is april first.
	 */
	public static boolean isAprilFirst() {
		return Calendar.getInstance().get(Calendar.MONTH) == Calendar.APRIL && Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == 1;
	}
}
