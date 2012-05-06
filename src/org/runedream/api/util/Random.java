package org.runedream.api.util;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

/**
 * Random number generation methods, which utilize a single java.util.Random object to maximize randomization.
 * 
 * @author Vulcan
 */
public final class Random {

	private static java.util.Random random = new java.util.Random();

	private Random() {
	}

	/**
	 * Generates a random number within a given range.
	 * @param min The inclusive lower-limit.
	 * @param max The exclusive upper-limit.
	 * @return A random number.
	 */
	public static int random(int min, int max) {
		if (min == max) {
			return min;
		}
		if (min > max) {
			throw new IllegalArgumentException("max <= min");
		}
		return min + random.nextInt(max - min);
	}

	/**
	 * Generates a random integer.
	 * @return A random integer.
	 */
	public static int nextInt() {
		return random.nextInt();
	}

	/**
	 * Generates a random double.
	 * @return A random double.
	 */
	public static double nextDouble() {
		return random.nextDouble();
	}

	/**
	 * Generates a random long.
	 * @return A random long.
	 */
	public static long nextLong() {
		return random.nextLong();
	}

	/**
	 * Generates a random float.
	 * @return A random float.
	 */
	public static float nextFloat() {
		return random.nextFloat();
	}

	/**
	 * Generates a random boolean.
	 * @return A random boolean.
	 */
	public static boolean nextBoolean() {
		return random.nextBoolean();
	}

	/**
	 * Generates a random gaussian.
	 * @return A random gaussian.
	 */
	public static double nextGaussian() {
		return random.nextGaussian();
	}
	
	/**
	 * Generates a random double within the interval [0.0, 1.0) with a standard deviation of 0.1.
	 * @return A random gaussian-distributed double.
	 */
	public static double nextGaussianDouble() {
		return nextGaussian(0, 1);
	}

	/**
	 * Generates a random double within given bounds with a standard deviation of one tenth of the bounds size.
	 * @param min The inclusive lower-limit.
	 * @param max The exclusive upper-limit.
	 * @return A random gaussian-distributed double.
	 */
	public static double nextGaussian(final double min, final double max) {
		return nextGaussian(min, max, (max - min) / 10.0);
	}

	/**
	 * Generates a random double within given bounds with standard deviation.
	 * @param min The inclusive lower-limit.
	 * @param max The exclusive upper-limit.
	 * @param deviation The value of one standard deviation.
	 * @return A random gaussian-distributed double.
	 */
	public static double nextGaussian(final double min, final double max, final double deviation) {
		return nextGaussian(min, max, min + (max - min) / 2.0, deviation);
	}

	/**
	 * Generates a random double within given bounds with standard deviation about a provided mean.
	 * @param min The inclusive lower-limit.
	 * @param max The exclusive upper-limit.
	 * @param mean The mean of the deviation, within the bounds of min and max.
	 * @param deviation The value of one standard deviation.
	 * @return A random gaussian-distributed double.
	 */
	public static double nextGaussian(final double min, final double max, final double mean, final double deviation) {
		if (min == max) {
			return min;
		}
		double rand;
		do {
			rand = random.nextGaussian() * deviation + mean;
		} while (rand < min || rand >= max);
		return rand;
	}
	
	/**
	 * Gets a random point within a Rectangle, generated with gaussian distribution.
	 * @return A random point within a Rectangle.
	 */
	public static Point getRandomPoint(final Rectangle bounds) {
		return new Point((int) (bounds.x + (nextGaussianDouble() * bounds.width)),
				(int) (bounds.y + (nextGaussianDouble() * bounds.height)));
	}

	/**
	 * Gets a random point within a Polygon, generated with gaussian distribution.
	 * @return A random point within a Polygon.
	 */
	public static Point getRandomPoint(final Polygon polygon) {
		final Rectangle bounds = polygon.getBounds();
		Point p;
		do {
		    p = getRandomPoint(bounds);
		} while (!polygon.contains(p));
		return p;
	}

}
