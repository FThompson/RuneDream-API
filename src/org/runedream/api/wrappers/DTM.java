package org.runedream.api.wrappers;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.runedream.api.methods.Game;

/**
 * A template of color points, useful for identifying screen patterns and shapes.
 * <br>
 * Note: Rotation detection is not yet supported.
 * 
 * @author Cookie, TheBat
 */
public class DTM {

	private DTMPoint center = null;
	private ArrayList<DTMPoint> points = new ArrayList<DTMPoint>();

	/**
	 * Constructs a template of color points.
	 * @param points The array of points to initialize with.
	 */
	public DTM(final DTMPoint... points) {
		for (final DTMPoint cpt : points) {
			if (cpt.isCenter()) {
				center = cpt;
			} else {
				this.points.add(cpt);
			}
		}
	}

	/**
	 * Checks if the template has at least one clickable point.
	 * @return <tt>true</tt> if the template is valid; otherwise <tt>false</tt>.
	 */
	public boolean isValid() {
		return getPoints().length >= 1;
	}

	/**
	 * Gets the first point returned by getPoints().
	 * @return A valid template point; or null if none found.
	 * @see #getPoints()
	 */
	public Point getPoint() {
		Point[] points = getPoints();
		return points.length >= 1 ? points[0] : null;
	}

	/**
	 * Gets an array of all valid template points.
	 * @return An array of valid DTMPoint points.
	 */
	public Point[] getPoints() {
		final ArrayList<Point> ret = new ArrayList<Point>();
		if (points != null && center != null) {
			final ArrayList<Point> bases = new ArrayList<Point>();
			final BufferedImage game = Game.getImage();
			for (int x = 0; x < game.getWidth() - 1; x++) {
				for (int y = 0; y < game.getHeight() - 1; y++) {
					final Color c = Game.getColorAt(x, y);
					if (isTolerable(c.getRed(), center.getColor().getRed(), center.getTolerance())
							&& isTolerable(c.getGreen(), center.getColor().getGreen(), center.getTolerance())
							&& isTolerable(c.getBlue(), center.getColor().getBlue(), center.getTolerance())) {
						bases.add(new Point(x, y));
					}
				}
			}
			bases: for (final Point p : bases) {
				for (final DTMPoint cpt : points) {
					final int x = p.x + cpt.getX();
					final int y = p.y + cpt.getY();
					final Color c = Game.getColorAt(x, y);
					if (!isTolerable(c.getRed(), cpt.getColor().getRed(), cpt.getTolerance())
							|| !isTolerable(c.getGreen(), cpt.getColor().getGreen(), cpt.getTolerance())
							|| !isTolerable(c.getBlue(), cpt.getColor().getBlue(), cpt.getTolerance())) {
						continue bases;
					}
				}
				ret.add(p);
			}
		}
		return ret.toArray(new Point[ret.size()]);
	}

	/**
	 * Gets an array of all valid template points with regards to a bounding rectangle.
	 * 
	 * @param rect The inner rectangle of the game image to search in.
	 * @return An array of valid DTMPoint points.
	 */
	public Point[] getPoints(Rectangle rect) {
		return getPoints(rect, false);
	}
	
	/**
	 * Gets an array of all valid template points with regards to a bounding rectangle.
	 * 
	 * @param x The x coord of the rectangle.
	 * @param y The y coord of the rectangle.
	 * @param width The width of the rectangle.
	 * @param height The height of the rectangle.\
	 * @return An array of valid DTMPoint points.
	 */
	public Point[] getPoints(int x, int y, int width, int height) {
		return getPoints(new Rectangle(x, y, width, height), false);
	}
	
	/**
	 * Gets an array of all valid template points with regards to a bounding rectangle.
	 * 
	 * @param x The x coord of the rectangle.
	 * @param y The y coord of the rectangle.
	 * @param width The width of the rectangle.
	 * @param height The height of the rectangle.
	 * @param inclusive If <tt>true</tt>, all sub points must be inside of the rectangle;
	 *                  otherwise if <tt>false</tt>, only the center point needs to be in the given rectangle. 
	 * @return An array of valid DTMPoint points.
	 */
	public Point[] getPoints(int x, int y, int width, int height, boolean inclusive) {
		return getPoints(new Rectangle(x, y, width, height), inclusive);
	}
	
	/**
	 * Gets an array of all valid template points with regards to a bounding rectangle.
	 * 
	 * @param rect The inner rectangle of the game image to search in.
	 * @param inclusive If <tt>true</tt>, all sub points must be inside of the rectangle;
	 *                  otherwise if <tt>false</tt>, only the center point needs to be in the given rectangle.
	 * @return An array of valid DTMPoint points.
	 */
	public Point[] getPoints(final Rectangle rect, final boolean inclusive) {
		final ArrayList<Point> ret = new ArrayList<Point>();
		if (points != null && center != null) {
			final ArrayList<Point> bases = new ArrayList<Point>();
			final BufferedImage game = Game.getImage();
			if (rect.x < 0){
				rect.width = rect.width + rect.x;
				rect.x = 0;
			}
			if (rect.x > game.getWidth()){
				rect.x = game.getWidth();
			}
			if (rect.x + rect.width > game.getWidth()){
				rect.width = (rect.x + rect.width) - game.getWidth();
			}
			if (rect.y < 0){
				rect.height = rect.height + rect.y;
				rect.y = 0;	
			}
			if (rect.y > game.getHeight()){
				rect.y = game.getHeight();
			}
			if (rect.y + rect.height > game.getHeight()){
				rect.height = (rect.y + rect.height) - game.getHeight();
			}
			for (int x = rect.x; x < rect.x + rect.width - 1; x++) {
				for (int y = rect.y; y < rect.x + rect.height - 1; y++) {
					final Color c = Game.getColorAt(x, y);
					if (isTolerable(c.getRed(), center.getColor().getRed(), center.getTolerance())
							&& isTolerable(c.getGreen(), center.getColor().getGreen(), center.getTolerance())
							&& isTolerable(c.getBlue(), center.getColor().getBlue(), center.getTolerance())) {
						bases.add(new Point(x, y));
					}
				}
			}
			bases: for (final Point p : bases) {
				for (final DTMPoint cpt : points) {
					final int x = p.x + cpt.getX();
					final int y = p.y + cpt.getY();
					if (inclusive && !rect.contains(x,y)) {
						continue bases;
					}
					final Color c = Game.getColorAt(x, y);
					if (!isTolerable(c.getRed(), cpt.getColor().getRed(), cpt.getTolerance())
							|| !isTolerable(c.getGreen(), cpt.getColor().getGreen(), cpt.getTolerance())
							|| !isTolerable(c.getBlue(), cpt.getColor().getBlue(), cpt.getTolerance())) {
						continue bases;
					}
				}
				ret.add(p);
			}
		}
		return ret.toArray(new Point[ret.size()]);
	}

	/**
	 * Adds a color point to the template.
	 * @param point The CPoint to add.
	 */
	public void add(final DTMPoint point) {
		if (point.isCenter()) {
			center = point;
		} else {
			points.add(point);
		}
	}

	/**
	 * Removes a color point from the template.
	 * @param point The CPoint to remove.
	 */
	public void remove(final DTMPoint point) {
		points.remove(point);
	}

	/**
	 * Gets the central CPoint of the template.
	 * @return The CPoint with relative coordinates (0, 0).
	 */
	public DTMPoint getCenter() {
		return center;
	}

	private static boolean isTolerable(final int n1, final int n2, final int tolerance) {
		return (n1 <= n2 + tolerance) && (n1 >= n2 - tolerance);
	}
}

