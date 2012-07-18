package org.runedream.api.util;

/**
 * Time-related methods.
 * 
 * @author Vulcan
 */
public final class Time {
	
	private Time() {
	}
	
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
	 * Waits for a given condition to be met, timing out after a given time.
	 * @param timeout The time (in milliseconds) to timeout after.
	 * @param condition The condition to be met.
	 * @return <tt>true</tt> if the condition was met; otherwise <tt>false</tt>.
	 */
	public static boolean waitFor(final int timeout, final Condition condition) {
		final long startTime = System.currentTimeMillis();
		while (startTime + timeout > System.currentTimeMillis()) {
			if (condition.isMet()) {
				return true;
			}
			Time.sleep(20, 30);
		}
		return condition.isMet();
	}
	
	/**
	 * Waits for a given condition to be met, timing out after a given time.
	 * @param min The minimum time (in milliseconds) to timeout after.
	 * @param max The maximum time (in milliseconds) to timeout after.
	 * @param condition The condition to be met.
	 * @return <tt>true</tt> if the condition was met; otherwise <tt>false</tt>.
	 */
	public static boolean waitFor(final int min, final int max, final Condition condition) {
		return waitFor(Random.random(min, max), condition);
	}
	
	/**
	 * Converts a long to a time display in format 00:00:00.
	 * @param millis The time in milliseconds to convert.
	 * @return The display string of the time.
	 */
	public static String toElapsedString(final long millis) {
		final long time = millis / 1000;
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
	
	/**
	 * A condition interface.
	 */
	public static interface Condition {
		
		/**
		 * Checks if the condition has been met.
		 * @return <tt>true</tt> if met; otherwise <tt>false</tt>.
		 */
		public boolean isMet();
	}

}
