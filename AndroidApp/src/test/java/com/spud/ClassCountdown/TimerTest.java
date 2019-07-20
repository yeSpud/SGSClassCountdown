package com.spud.ClassCountdown;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Spud on 2019-07-20 for the project: SGSClassCountdown.
 * <p>
 * For the license, view the file titled LICENSE at the root of the project
 */
public class TimerTest {

	@Test
	public void formatTimeRemaining() {

		// TODO Test 2 hours remaining

		// TODO Test 1.5 hours remaining

		// TODO Test 1 hour remaining

		// TODO Test 30 minutes remaining

		// TODO Test 2 minutes remaining

		// TODO Test 1.5 minutes remaining


		// Test 1 minute remaining
		Assert.assertEquals("1:00", Timer.formatTimeRemaining(60));

		// Test 59 seconds remaining
		Assert.assertEquals("59", Timer.formatTimeRemaining(59));

		// Test 30 seconds remaining
		Assert.assertEquals("30", Timer.formatTimeRemaining(30));

		// Test 10 seconds remaining
		Assert.assertEquals("10", Timer.formatTimeRemaining(10));

		// Test 9 seconds remaining

		// Test 1 second remaining
	}

}
