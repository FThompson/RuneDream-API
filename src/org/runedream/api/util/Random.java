package org.runedream.api.util;

/**
 * Random number generation methods.
 */
public class Random {
	
	private static java.util.Random random = new java.util.Random();
	
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

}
