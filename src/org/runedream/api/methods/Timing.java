package org.runedream.api.methods;

import org.runedream.api.Script;

/**
 * Convenience class for dynamic sleeping.
 */
public final class Timing {
	
	/**
	 * Waits for a given condition to be met, timing out after a given time.
	 * @param timeout The time (in milliseconds) to timeout after.
	 * @param condition The condition to be met.
	 * @return <tt>true</tt> if the condition was met; otherwise <tt>false</tt>.
	 */
	public static boolean waitFor(final long timeout, final Condition condition) {
		long startTime = System.currentTimeMillis();
		while (startTime + timeout > System.currentTimeMillis()) {
			if (condition.isMet()) {
				return true;
			}
			Script.sleep(20, 30);
		}
		return condition.isMet();
	}
	
	/**
	 * An condition interface.
	 */
	public static interface Condition {
		public boolean isMet();
	}

}
