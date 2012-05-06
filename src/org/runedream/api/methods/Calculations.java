package org.runedream.api.methods;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

/**
 * Convenience methods for common calculations.
 */
public final class Calculations {
	
	private Calculations() {
	}

	/**
	 * Gets the distance between two points.
	 * @param p1 The first point.
	 * @param p2 The second point.
	 * @return The distance.
	 */
	public static int getDistanceBetween(final Point p1, final Point p2) {
		if (p1 == null || p2 == null) {
			return -1;
		}
		final int xDiff = p2.x - p1.x;
		final int yDiff = p2.y - p1.y;
		return (int) Math.sqrt(xDiff * xDiff + yDiff * yDiff);
	}

	/**
	 * Gets the largest distance between all points.<br>Useful to check if a bunch of points is close to each other.
	 * @param points The points to include.
	 * @return The largest distance in pixels between all points.
	 */
	public static int getLargestDistanceBetween(final Point...points) {
		if (points == null) {
			return -1;
		}
		int distance = 0;
		int largestDistance = -1;
		top: for (final Point main : points) {
			for (final Point sub : points) {
				if (main == null || sub == null || main.equals(sub)) {
					continue top;
				} else if ((distance = getDistanceBetween(main, sub)) > largestDistance) {
					largestDistance = distance;
				}
			}
		}
		return largestDistance;
	}

	/**
	 * Gets the percentage of points out of all points with a certain color in a certain rectangle.
	 * @param rectangle A rectangle in which the points must be found.
	 * @param color The color to scan for.
	 * @param tolerance The tolerance to scan by.
	 * @return The percentage of points out of all points with a certain color in a certain rectangle.
	 * @author Aidden
	 */
	public static double getColorConcentration(final Rectangle rectangle, final Color color, final int tolerance) {
		final List<Point> points = ImageUtil.getPointsWithColor(Game.getImage(), rectangle, color, tolerance);
		if (points == null || points.isEmpty()) {
			return 0;
		}
		final double pixels = (double) (rectangle.getWidth() * rectangle.getHeight() * 100);
		return Math.min((double) (points.size() / pixels), 100.00);
	}

	/**
	 * Gets the nearest point to a given point of a given set.
	 * @param point The point to check others' distances to.
	 * @param checks The points to check for distance to the primary point.
	 * @return The nearest point of the set to the given point.
	 * @author iSmokePurple
	 */
	public static Point getNearestPoint(final Point point, final Point...checks) {
		Point nearest = null;
		double dist = -1;
		for (final Point check : checks) {
			final double distTmp = getDistanceBetween(check, point);
			if (nearest == null || nearest != null && distTmp < dist) {
				dist = distTmp;
				nearest = check;
			}
		}
		return nearest;
	}

	/**
	 * Gets the nearest point to a given point of points with a given color.
	 * @param point The point to check others' distances to.
	 * @param color The color to identify other points with.
	 * @return The nearest point of given color to the given point.
	 * @author iSmokePurple
	 */
	public static Point getNearestPoint(final Point point, final Color color) {
		return getNearestPoint(point, color, 0);
	}
	
	/**
	 * Gets the nearest point to a given point of points with a given color within a given tolerance.
	 * @param point The point to check others' distances to.
	 * @param color The color to identify other points with.
	 * @param tolerance The tolerance to match point colors within.
	 * @return The nearest point of given color to the given point.
	 * @author iSmokePurple
	 */
	public static Point getNearestPoint(final Point point, final Color color, final int tolerance) {
		final List<Point> pts = Game.getPointsWithColor(color, tolerance);
		final Point[] points = pts.toArray(new Point[pts.size()]);
		if (points.length > 0) {
			return getNearestPoint(point, points);
		}
		return null;
	}

}
