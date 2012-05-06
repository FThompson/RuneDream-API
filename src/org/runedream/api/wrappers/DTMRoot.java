package org.runedream.api.wrappers;

import java.awt.Color;
import java.awt.Point;

import org.runedream.api.methods.ColorUtil;
import org.runedream.api.methods.Game;

/**
 * A root point for use with a DTM.
 * 
 * @author Dang
 * @see DTM
 */
public class DTMRoot {

	private final int tolerance;
	private final Color color;

	/**
	 * Constructs a DTMRoot.
	 * @param color The color of the root point.
	 */
	public DTMRoot(final Color color) {
		this(color, 0);
	}

	/**
	 * Constructs a DTMRoot.
	 * @param rgb The RGB of the color of the root point.
	 */
	public DTMRoot(final int rgb) {
		this(new Color(rgb));
	}

	/**
	 * Constructs a DTMRoot.
	 * @param rgb The RGB of the color of the root point.
	 * @param tolerance The tolerance.
	 */
	public DTMRoot(final int rgb, final int tolerance) {
		this(new Color(rgb), tolerance);
	}

	/**
	 * Constructs a DTMRoot.
	 * @param color The color of the root point.
	 * @param tolerance The tolerance.
	 */
	public DTMRoot(final Color color, final int tolerance) {
		this.color = color;
		this.tolerance = tolerance;
	}

	/**
	 * Gets the DTMRoot's color.
	 * @return The DTMRoot's color.
	 */
	public Color getColor() {
		return this.color;
	}

	/**
	 * Gets the DTMRoot's tolerance.
	 * @return The DTMRoot's tolerance.
	 */
	public int getTolerance() {
		return this.tolerance;
	}

	/**
	 * Checks if this root point is valid at a given point.
	 * @param p The point to check for validity at.
	 * @return <tt>true</tt> if the root is valid at Point p; otherwise <tt>false</tt>.
	 */
	public boolean isValidAt(final Point p) {
		return ColorUtil.isTolerable(Game.getColorAt(p.x, p.y), getColor(), getTolerance());
	}
	
	/**
	 * Returns a string representation of the DTMRoot in format "r_g_b_t".
	 * @return A string representation of the DTMRoot.
	 */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		final char u = '_';
		sb.append(getColor().getRed());
		sb.append(u);
		sb.append(getColor().getGreen());
		sb.append(u);
		sb.append(getColor().getBlue());
		sb.append(u);
		sb.append(getTolerance());
		return sb.toString();
	}

	/**
	 * Creates a DTMRoot from a String representation, in format "r_g_b_t".
	 * @param A String representation of a DTM, in format "r_g_b_t".
	 * @return The DTMRoot converted from the given String.
	 */
	public static DTMRoot fromString(final String string) {
		final String[] split = string.split("_");
		final int r = Integer.parseInt(split[0]);
		final int g = Integer.parseInt(split[1]);
		final int b = Integer.parseInt(split[2]);
		final int t = Integer.parseInt(split[3]);
		return new DTMRoot(new Color(r, g, b), t);
	}
}