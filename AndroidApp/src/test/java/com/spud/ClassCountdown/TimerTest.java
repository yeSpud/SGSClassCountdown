package com.spud.ClassCountdown;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Spud on 2019-07-20 for the project: SGSClassCountdown.
 * <p>
 * For the license, view the file titled LICENSE at the root of the project
 */
public class TimerTest {

	// Test the format time remaining function
	@Test
	public void formatTimeRemaining() {

		// Test 2 hours remaining
		Assert.assertEquals("120:00", Timer.formatTimeRemaining(7200));

		// Test 1.5 hours remaining
		Assert.assertEquals("90:00", Timer.formatTimeRemaining(5400));

		// Test 1 hour remaining
		Assert.assertEquals("60:00", Timer.formatTimeRemaining(3600));

		// Test 30 minutes remaining
		Assert.assertEquals("30:00", Timer.formatTimeRemaining(1800));

		// Test 2 minutes remaining
		Assert.assertEquals("2:00", Timer.formatTimeRemaining(120));

		// Test 1.5 minutes remaining
		Assert.assertEquals("1:30", Timer.formatTimeRemaining(90));

		// Test 1 minute remaining
		Assert.assertEquals("1:00", Timer.formatTimeRemaining(60));

		// Test 59 seconds remaining
		Assert.assertEquals("59", Timer.formatTimeRemaining(59));

		// Test 30 seconds remaining
		Assert.assertEquals("30", Timer.formatTimeRemaining(30));

		// Test 10 seconds remaining
		Assert.assertEquals("10", Timer.formatTimeRemaining(10));

		// Test 9 seconds remaining
		Assert.assertEquals("09", Timer.formatTimeRemaining(9));

		// Test 1 second remaining
		Assert.assertEquals("01", Timer.formatTimeRemaining(1));
	}

	@Test
	public void getHour() {

		// Should be 0 (just passed midnight / 12 AM)
		Assert.assertEquals(0, Timer.getHour(0));
		Assert.assertEquals(0, Timer.getHour(1));
		Assert.assertEquals(0, Timer.getHour(2));
		Assert.assertEquals(0, Timer.getHour(3));
		Assert.assertEquals(0, Timer.getHour(30));
		Assert.assertEquals(0, Timer.getHour(60));
		Assert.assertEquals(0, Timer.getHour(120));
		Assert.assertEquals(0, Timer.getHour(3599));

		// Should be 1 (1 AM)
		Assert.assertEquals(1, Timer.getHour(3600));
		Assert.assertEquals(1, Timer.getHour(7199));

		// Should be 11 (11 AM)
		Assert.assertEquals(11, Timer.getHour(39600));
		Assert.assertEquals(11, Timer.getHour(43199));

		// Should be 12 (12 PM)
		Assert.assertEquals(12, Timer.getHour(43200));
		Assert.assertEquals(12, Timer.getHour(46799));

		// Should be 13 (1 PM)
		Assert.assertEquals(13, Timer.getHour(46800));
		Assert.assertEquals(13, Timer.getHour(50399));

		// Should be 23 (11 PM)
		Assert.assertEquals(23, Timer.getHour(82800));
		Assert.assertEquals(23, Timer.getHour(86399));

	}

	@Test
	public void getMinute() {

		// Should be 0 (before 1 minute has passed)
		Assert.assertEquals(0, Timer.getMinute(0));
		Assert.assertEquals(0, Timer.getMinute(1));
		Assert.assertEquals(0, Timer.getMinute(2));
		Assert.assertEquals(0, Timer.getMinute(3));
		Assert.assertEquals(0, Timer.getMinute(5));
		Assert.assertEquals(0, Timer.getMinute(15));
		Assert.assertEquals(0, Timer.getMinute(30));
		Assert.assertEquals(0, Timer.getMinute(59));

		// Should be 1
		Assert.assertEquals(1, Timer.getMinute(60));
		Assert.assertEquals(1, Timer.getMinute(61));
		Assert.assertEquals(1, Timer.getMinute(65));
		Assert.assertEquals(1, Timer.getMinute(100));
		Assert.assertEquals(1, Timer.getMinute(119));
	}

}
