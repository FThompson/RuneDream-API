package org.runedream.api.methods;

import java.awt.Color;

/**
 * Color-related utility methods.
 */
public class ColorUtil {

	/**
	 * Gets the "distance" between two colors, their components assumed
	 * to be points in 3D space ranging from 0.0 to 1.0.
	 * @param r1 Red value of the first color.
	 * @param g1 Green value of the first color.
	 * @param b1 Blue value of the first color.
	 * @param r2 Red value of the second color.
	 * @param g2 Green value of the second color.
	 * @param b2 Blue value of the second color.
	 * @return The "distance" between the two colors.
	 */
	public static double getDistance(double r1, double g1, double b1, double r2, double g2, double b2) {
		double red = r2 - r1;
		double green = g2 - g1;
		double blue = b2 - b1;
		return Math.sqrt(red * red + green * green + blue * blue);
	}

	/**
	 * Gets the "distance" between two colors, their components assumed
	 * to be points in 3D space ranging from 0.0 to 1.0.
	 * @param c1 The first color.
	 * @param c2 The second Color.
	 * @return The "distance" between the two colors.
	 */
	public static double getDistance(final Color c1, final Color c2) {
		float rgb1[] = new float[3];
		float rgb2[] = new float[3];    
		c1.getColorComponents(rgb1);
		c2.getColorComponents(rgb2);    
		return getDistance(rgb1[0], rgb1[1], rgb1[2], rgb2[0], rgb2[1], rgb2[2]);
	}

	/**
	 * Gets the "distance" between two colors, their components assumed
	 * to be points in 3D space ranging from 0.0 to 1.0.
	 * @param rgb1 The RGB values of the first color.
	 * @param rgb2 The RGB values of the second color.
	 * @return The "distance" between the two colors.
	 */
	public static double getDistance(final double[] rgb1, final double[] rgb2) {
		return getDistance(rgb1[0], rgb1[1], rgb1[2], rgb2[0], rgb2[1], rgb2[2]);
	}

	/**
	 * Gets the "distance" between two colors, their components assumed
	 * to be points in 3D space ranging from 0.0 to 1.0.
	 * @param rgb1 The RGB value of the first color.
	 * @param rgb2 The RGB value of the second color.
	 * @return The "distance" between the two colors.
	 */
	public static double getDistance(final int rgb1, final int rgb2) {
		return getDistance(new Color(rgb1), new Color(rgb2));
	}
	
	/**
	 * Gets whether or not the given rgb values are closer in distance to black than white.
	 * @param r The red value of the color.
	 * @param g The green value of the color.
	 * @param b The blue value of the color.
	 * @return <tt>true</tt> if closer to black than white.
	 */
	public static boolean isDark(final double r, final double g, final double b) {
		return getDistance(r, g, b, 0.0, 0.0, 0.0) > getDistance(r, g, b, 1.0, 1.0, 1.0);
	}

	/**
	 * Gets whether or not the given color is closer in distance to black than white.
	 * @param c The color.
	 * @return <tt>true</tt> if closer to black than white.
	 */
	public static boolean isDark(final Color c) {
		float rgb[] = new float[3];    
		c.getColorComponents(rgb);
		return isDark(rgb[0], rgb[1], rgb[2]);
	}

}
