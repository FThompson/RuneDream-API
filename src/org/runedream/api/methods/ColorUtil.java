package org.runedream.api.methods;

import java.awt.Color;

/**
 * Color-related utility methods.
 */
public final class ColorUtil {
	
	private ColorUtil() {
	}
	
	/**
	 * Gets the "distance" between two colors, returning between 0 and 255.
	 * @param r1 Red value of the first color.
	 * @param g1 Green value of the first color.
	 * @param b1 Blue value of the first color.
	 * @param r2 Red value of the second color.
	 * @param g2 Green value of the second color.
	 * @param b2 Blue value of the second color.
	 * @return The "distance" between the two colors.
	 */
	public static int getDistance(int r1, int g1, int b1, int r2, int g2, int b2) {
		int red = Math.abs(r2 - r1);
		int green = Math.abs(g2 - g1);
		int blue = Math.abs(b2 - b1);
		return (red + green + blue) / 3;
	}

	/**
	 * Gets the "distance" between two colors, returning between 0 and 255.
	 * @param c1 The first color.
	 * @param c2 The second Color.
	 * @return The "distance" between the two colors.
	 */
	public static int getDistance(final Color c1, final Color c2) {  
		return getDistance(c1.getRed(), c1.getGreen(), c1.getBlue(), c2.getRed(), c2.getGreen(), c2.getBlue());
	}

	/**
	 * Gets the "distance" between two colors, returning between 0 and 255.
	 * @param rgb1 The RGB values of the first color.
	 * @param rgb2 The RGB values of the second color.
	 * @return The "distance" between the two colors.
	 */
	public static int getDistance(final int[] rgb1, final int[] rgb2) {
		return getDistance(rgb1[0], rgb1[1], rgb1[2], rgb2[0], rgb2[1], rgb2[2]);
	}

	/**
	 * Gets the "distance" between two colors, returning between 0 and 255.
	 * @param rgb1 The RGB value of the first color.
	 * @param rgb2 The RGB value of the second color.
	 * @return The "distance" between the two colors.
	 */
	public static int getDistance(final int rgb1, final int rgb2) {
		return getDistance(new Color(rgb1), new Color(rgb2));
	}
	
	/**
	 * Gets whether or not the given rgb values are closer in distance to black than white.
	 * @param r The red value of the color.
	 * @param g The green value of the color.
	 * @param b The blue value of the color.
	 * @return <tt>true</tt> if the given color is closer to black than white.
	 */
	public static boolean isDark(final int r, final int g, final int b) {
		return getDistance(r, g, b, 0, 0, 0) > getDistance(r, g, b, 255, 255, 255);
	}

	/**
	 * Gets whether or not the given color is closer in distance to black than white.
	 * @param c The color to check.
	 * @return <tt>true</tt> if the given color is closer to black than white.
	 */
	public static boolean isDark(final Color c) {
		return isDark(c.getRed(), c.getGreen(), c.getBlue());
	}

	/**
	 * Gets the complementary color to a given rgb set.
	 * @param r The red value of the color.
	 * @param g The green value of the color.
	 * @param b The blue value of the color.
	 * @return The complementary color.
	 */
	public static Color getComplementary(final int r, final int g, final int b) {
		return new Color(255 - r, 255 - g, 255 - b);
	}

	/**
	 * Gets the complementary color to a given color.
	 * @param color The color to complement.
	 * @return The complementary color.
	 */
	public static Color getComplementary(final Color color) {
		return new Color(255 - color.getRed(), 255 - color.getGreen(), 255 - color.getBlue());
	}
	
	/**
	 * Checks if two colors are within a tolerance.
	 * @param c1 The first color.
	 * @param c2 The second color.
	 * @param tolerance The tolerance.
	 * @return <tt>true</tt> if tolerable; otherwise <tt>false</tt>.
	 */
	public static boolean isTolerable(final Color c1, final Color c2, final int tolerance) {
		return getDistance(c1, c2) <= tolerance;
	}

}
