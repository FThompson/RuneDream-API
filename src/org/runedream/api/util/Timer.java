package org.runedream.api.util;

/**
 * A timer utility.
 * 
 * @author Vulcan
 */
public class Timer {

	private long start;
	private long end;
	private long period;

	/**
	 * Constructs a new timer.
	 * @param period The period (in milliseconds) to set the timer to. The timer counts down.
	 */
	public Timer(final long period) {
		start = System.currentTimeMillis();
		this.period = period;
		end = start + period;
	}

	/**
	 * Gets the time elapsed since the time was instantiated or last reset.
	 * @return The time elapsed in milliseconds.
	 */
	public long getElapsed() {
		return System.currentTimeMillis() - start;
	}

	/**
	 * Gets the time remaining until the timer hits 0.
	 * @return The time remaining in milliseconds.
	 */
	public long getRemaining() {
		return end - System.currentTimeMillis();
	}

	/**
	 * Gets whether or not the timer is currently running.
	 * @return <tt>true</tt> if running; otherwise <tt>false</tt>.
	 */
	public boolean isRunning() {
		return System.currentTimeMillis() < end;
	}

	/**
	 * Resets the timer to its original time period.
	 */
	public void reset() {
		start = System.currentTimeMillis();
		end = start + period;
	}

	/**
	 * Sets the timer's period and resets it.
	 * @param ms The period to set.
	 */
	public void setEndIn(final long ms) {
		start = System.currentTimeMillis();
		period = ms;
		end = start + period;
	}
}
