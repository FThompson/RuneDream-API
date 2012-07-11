package org.runedream.api.wrappers;

import java.awt.Point;

import org.runedream.api.methods.Camera;
import org.runedream.api.methods.Mouse;
import org.runedream.api.methods.Walking;
import org.runedream.api.util.Time;

/**
 * Wrapper representing a point relative to the minimap in a modified polar coordinate system.
 * 
 * @author Static, Vulcan
 */
public class VectorTile {

	public static final int NORTH = 0;
	public static final int NORTH_EAST = 45;
	public static final int EAST = 90;
	public static final int SOUTH_EAST = 135;
	public static final int SOUTH = 180;
	public static final int SOUTH_WEST = 225;
	public static final int WEST = 270;
	public static final int NORTH_WEST = 315;
	public static final int MINIMAP_RADIUS = 72;
	public static final Point MINIMAP_CENTER = new Point(627, 85);

	private final int radius;
	private final int degree;

	/**
	 * Instantiates a VectorTile with a radius and degree (r and theta) relative to the minimap center in an offset polar coordinate system.
	 * @param radius The radius from the minimap center.
	 * @param degree The degree (west = 0, counter-clockwise) of the direction to click.
	 */
	public VectorTile(final int radius, final int degree) {
		if (radius < 0 || radius > MINIMAP_RADIUS) {
			throw new IllegalArgumentException("Radius out of bounds: [0, 72]");
		}
		this.radius = radius;
		this.degree = degree;
	}

	/**
	 * Gets the vector tile's radius.
	 * @return The vector tile's radius.
	 */
	public int getRadius() {
		return radius;
	}

	/**
	 * Gets the vector tile's angle degree.
	 * @return The vector tile's angle degree.
	 */
	public int getDegree() {
		return degree;
	}

	/**
	 * Gets the current map point of the VectorTile, accounting for compass rotation.
	 * @return The map point defined by radius and degrees in a rotating modified polar coordinate system.
	 */
	public Point getMapPoint() {
		final double rads = Math.toRadians(degree - 90 + Camera.getCompassAngle());
		final double x = MINIMAP_CENTER.x - radius * Math.cos(rads);
		final double y = MINIMAP_CENTER.y + radius * Math.sin(rads);
		return new Point((int) x, (int) y);
	}

	/**
	 * Clicks on the map point.
	 * @return <tt>true</tt> if clicked, walking began, and walking ended; otherwise <tt>false</tt>.
	 */
	public boolean clickOnMap() {
		final Point p = getMapPoint();
		Mouse.click(p);
		return Time.waitFor(650, 800, new Time.Condition() {
			public boolean isMet() {
				return Walking.hasDestination();
			}
		}) && Time.waitFor(7000, 8000, new Time.Condition() {
			public boolean isMet() {
				return !Walking.hasDestination();
			}
		});
	}
}