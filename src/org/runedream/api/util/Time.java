package org.runedream.api.util;

/**
 * Time-related methods.
 * 
 * @author Vulcan
 */
public final class Time {
	
	/**
	 * Sleeps for a given amount of milliseconds.
	 * @param millis The milliseconds to sleep.
	 */
	public static void sleep(final int millis) {
		try {
			Thread.sleep(millis);
		} catch (final InterruptedException e) {
		}
	}
	
	/**
	 * Sleeps for a random amount of milliseconds between two given values.
	 * @param min The minimum boundary to sleep.
	 * @param max The exclusive maximum boundary to sleep.
	 */
	public static void sleep(final int min, final int max) {
		sleep(Random.random(min, max));
	}
	
	/**
	 * Converts a long to a time display in format 00:00:00.
	 * @param millis The time in milliseconds to convert.
	 * @return The display string of the time.
	 */
	public static String getTimeString(long millis) {
		long time = millis / 1000;
		String seconds = Integer.toString((int) (time % 60));
		String minutes = Integer.toString((int) ((time % 3600) / 60));
		String hours = Integer.toString((int) (time / 3600));
		for (int i = 0; i < 2; i++) {
			if (seconds.length() < 2) {
				seconds = "0" + seconds;
			}
			if (minutes.length() < 2) {
				minutes = "0" + minutes;
			}
			if (hours.length() < 2) {
				hours = "0" + hours;
			}
		}
		return hours + ":" + minutes + ":" + seconds;
	}

}
