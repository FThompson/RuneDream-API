package org.runedream.api.methods;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

/**
 * Calculations methods.
 */
public class Calculations {
	
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
        top : for (final Point main : points) {
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
     * @param threshold The threshold to scan by.
     * @return The percentage of points out of all points with a certain color in a certain rectangle.
     */
    public static double getColorConcentration(final Rectangle rectangle, final Color color, final double threshold) {
        final List<Point> points = ImageUtil.getPointsWithColor(Game.getImage(), rectangle, color, threshold);
        if (points == null || points.isEmpty()) {
            return 0;
        }
        final double pixels = (double) (rectangle.getWidth() * rectangle.getHeight() * 100);
        return Math.min((double) (points.size() / pixels), 100.00);
    }

}
