package org.runedream.api.wrappers;

import java.awt.Color;

/**
 * A relative point representing a color.
 * 
 * @author Cookie
 */
public class DTMPoint {

	private int x = 0;
	private int y = 0;
	private int tolerance = 0;
	private Color color;

	/**
	 * Constructs a CPoint of given relative coordinates.
	 * @param x The relative x coordinate.
	 * @param y The relative y coordinate.
	 * @param tolerance The tolerance to accept of the color.
	 * @param color The color at the relative location.
	 */
	public DTMPoint(final int x, final int y, final int tolerance, final Color color) {
		this.x = x;
		this.y = y;
		this.tolerance = tolerance;
		this.color = color;
	}

	/**
	 * Constructs a CPoint at relative coordinates (0, 0), making it a center point.
	 * @param tolerance The tolerance to accept of the color.
	 * @param color The color at the center.
	 */
	public DTMPoint(final int tolerance, final Color color) {
		this.tolerance = tolerance;
		this.color = color;
	}

	/**
	 * Checks if the CPoint is a central point.
	 * @return <tt>true</tt> if the CPoint is located at relative coordinates (0, 0); otherwise <tt>false</tt>.
	 */
	public boolean isCenter() {
		return x == 0 && y == 0;
	}

	/**
	 * Gets the relative x coordinate.
	 * @return The relative x coordinate.
	 */
	public int getX() {
		return x;
	}

	/**
	 * Gets the relative y coordinate.
	 * @return The relative y coordinate.
	 */
	public int getY() {
		return y;
	}

	/**
	 * Gets the tolerance by which to accept the color.
	 * @return The tolerance.
	 */
	public int getTolerance() {
		return tolerance;
	}

	/**
	 * Gets the color of the relative point.
	 * @return The color.
	 */
	public Color getColor() {
		return color;
	}
}

