package org.runedream.api.wrappers;

import java.awt.Color;
import java.awt.Point;

import org.runedream.api.methods.ColorUtil;
import org.runedream.api.methods.Game;

/**
 * A branch point for use with a DTM.
 * 
 * @author Dang
 */
public class DTMBranch {

	private final int x;
	private final int y;
	private final int tolerance;
	private final Color color;
	
	/**
	 * Constructs a DTMBranch.
	 * @param color The color of the branch point.
	 * @param x The relative x coordinate.
	 * @param y The relative y coordinate.
	 */
	public DTMBranch(final Color color, final int x, final int y) {
		this(color, x, y, 0);
	}

	/**
	 * Constructs a DTMBranch.
	 * @param color The color of the branch point.
	 * @param x The relative x coordinate.
	 * @param y The relative y coordinate.
	 * @param tolerance The tolerance of the color.
	 */
	public DTMBranch(final Color color, final int x, final int y, final int tolerance) {
		this.color = color;
		this.x = x;
		this.y = y;
		this.tolerance = tolerance;
	}

	/**
	 * Gets the branch's color.
	 * @return The branch's color.
	 */
	public Color getColor() {
		return this.color;
	}

	/**
	 * Gets this branch's relative x coordinate.
	 * @return This branch's relative x coordinate.
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * Gets this branch's relative y coordinate.
	 * @return This branch's relative y coordinate.
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * Get's this branch's color tolerance.
	 * @return This branch's color tolerance.
	 */
	public int getTolerance() {
		return this.tolerance;
	}

	/**
	 * Checks if this branch point is valid at a given point.
	 * @param p The point to check for validity at.
	 * @return <tt>true</tt> if the branch is valid at Point p; otherwise <tt>false</tt>.
	 */
	public boolean isValidAt(final Point p) {
		return ColorUtil.isTolerable(Game.getColorAt(p.x + getX(), p.y + getY()), getColor(), getTolerance());
	}
	
	/**
	 * Returns a string representation of the DTMBranch in format "r_g_b_x_y_t".
	 * @return A string representation of the DTMBranch.
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
		sb.append(getX());
		sb.append(u);
		sb.append(getY());
		sb.append(u);
		sb.append(getTolerance());
		return sb.toString();
	}

	/**
	 * Constructs a DTMBranch from a String representation, in format "r_g_b_x_y_t".
	 * @param The String representation.
	 * @return The parsed DTMBranch.
	 */
	public static DTMBranch fromString(final String string) {
		final String[] split = string.split("_");
		final int r = Integer.parseInt(split[0]);
		final int g = Integer.parseInt(split[1]);
		final int b = Integer.parseInt(split[2]);
		final int x = Integer.parseInt(split[3]);
		final int y = Integer.parseInt(split[4]);
		final int t = Integer.parseInt(split[5]);
		return new DTMBranch(new Color(r, g, b), x, y, t);
	}
}