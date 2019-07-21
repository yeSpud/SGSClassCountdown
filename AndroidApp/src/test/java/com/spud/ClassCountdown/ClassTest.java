package com.spud.ClassCountdown;

import com.spud.ClassCountdown.Regime.Class;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Spud on 2019-07-20 for the project: SGSClassCountdown.
 * <p>
 * For the license, view the file titled LICENSE at the root of the project
 */
public class ClassTest {

	private static final Class earlyClass = new Class("Early Class", 3600, 7200, "1 / 1 AM"),
			lateClass = new Class("Late Class", 46800, 50400, "13 / 1 PM"),
			midnightClass = new Class("Midnight Class", 0, 60, "0 / 12 AM"),
			noonClass = new Class("Noon Class", 43200, 43260, "12 / 12 PM");

	@Test
	public void testRealNames() {
		Assert.assertEquals("Early Class", earlyClass.getName(false));
		Assert.assertEquals("Late Class", lateClass.getName(false));
		Assert.assertEquals("Midnight Class", midnightClass.getName(false));
		Assert.assertEquals("Noon Class", noonClass.getName(false));
	}

	@Test
	public void testStartTime() {
		Assert.assertEquals(1, Timer.getHour(earlyClass.getStartTime()));
		Assert.assertEquals(0, Timer.getMinute(earlyClass.getStartTime()));

		Assert.assertEquals(13, Timer.getHour(lateClass.getStartTime()));
		Assert.assertEquals(0, Timer.getMinute(lateClass.getStartTime()));

		Assert.assertEquals(0, Timer.getHour(midnightClass.getStartTime()));
		Assert.assertEquals(0, Timer.getMinute(midnightClass.getStartTime()));

		Assert.assertEquals(12, Timer.getHour(noonClass.getStartTime()));
		Assert.assertEquals(0, Timer.getMinute(noonClass.getStartTime()));
	}

	@Test
	public void testEndTime() {
		Assert.assertEquals(2, Timer.getHour(earlyClass.getEndTime()));
		Assert.assertEquals(0, Timer.getMinute(earlyClass.getEndTime()));

		Assert.assertEquals(14, Timer.getHour(lateClass.getEndTime()));
		Assert.assertEquals(0, Timer.getMinute(lateClass.getEndTime()));

		Assert.assertEquals(0, Timer.getHour(midnightClass.getEndTime()));
		Assert.assertEquals(1, Timer.getMinute(midnightClass.getEndTime()));

		Assert.assertEquals(12, Timer.getHour(noonClass.getEndTime()));
		Assert.assertEquals(1, Timer.getMinute(noonClass.getEndTime()));
	}

	@Test
	public void testCustomNames() {
		Assert.assertEquals("1 / 1 AM", earlyClass.getName(true));
		Assert.assertEquals("13 / 1 PM", lateClass.getName(true));
		Assert.assertEquals("0 / 12 AM", midnightClass.getName(true));
		Assert.assertEquals("12 / 12 PM", noonClass.getName(true));
	}

}
