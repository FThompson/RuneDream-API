package org.runedream.api.methods;

import org.runedream.api.util.Random;
import org.runedream.api.util.Time;

/**
 * Convenience class for dynamic sleeping.
 * 
 * @author Vulcan
 */
public final class Timing {
	
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
	
	public static boolean waitFor(final int min, final int max, final Condition condition) {
		return waitFor(Random.random(min, max), condition);
	}
	
	/**
	 * An condition interface.
	 */
	public static interface Condition {
		public boolean isMet();
	}

}
