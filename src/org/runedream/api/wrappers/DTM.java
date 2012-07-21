package org.runedream.api.wrappers;

import org.runedream.api.methods.Calculations;
import org.runedream.api.methods.Game;
import org.runedream.api.methods.ImageUtil;
import org.runedream.api.methods.Mouse;
import org.runedream.api.util.Random;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.List;

/**
 * A Deformable Template Model (DTM), made up of a central "root" point and corresponding relative "branch" points.
 * 
 * @author Dang, Vulcan
 */
public class DTM {

	private final DTMRoot root;
	private final DTMBranch[] branches;

	/**
	 * Constructs a DTM from a root point and an array of branch points.
	 * @param root The DTMRoot of this DTM.
	 * @param branches The DTMBranch array of this DTM.
	 */
	public DTM(final DTMRoot root, final DTMBranch... branches) {
		this.root = root;
		this.branches = branches;
	}

	/**
	 * Gets all valid location points within a bounded area. Note that the bounds only apply to the root point.
	 * @param bound The bounds to search within.
	 * @return An array of points where the DTM is valid.
	 */
	public Point[] getAll(final Rectangle bounds) {
		final LinkedList<Point> points = new LinkedList<Point>();
		final LinkedList<Point> colorPoints = ImageUtil.getPointsWithColor(Game.getImage(), bounds, root.getColor(), root.getTolerance());
		for (final Point point : colorPoints) {
			if (isValidAt(point)) {
				points.add(point);
			}
		}
		return points.toArray(new Point[points.size()]);
	}

	/**
	 * Gets all valid location points within a bounded area. Note that the bounds only apply to the root point's location.
	 * @param x The x coordinate of the bounds to search within.
	 * @param y The y coordinate of the bounds to search within.
	 * @param width The width of the bounds to search within.
	 * @param height The height of the bounds to search within.
	 * @return An array of points where the DTM is valid.
	 */
	public Point[] getAll(final int x, final int y, final int width, final int height) {
		return getAll(new Rectangle(x, y, width, height));
	}

	/**
	 * Gets all valid location points.
	 * @return An array of points where the DTM is valid.
	 */
	public Point[] getAll() {
		return getAll(Game.SCREEN);
	}

	/**
	 * Gets a random valid location point.
	 * @param bounds The bounds to search within.
	 * @return A point where the DTM is valid.
	 */
	public Point getPoint(final Rectangle bounds) {
		final Point[] points = getAll(bounds);
		if (points.length > 0) {
			return points[Random.random(0, points.length)];
		}
		return null;
	}

	/**
	 * Gets a random valid location point.
	 * @return A point where the DTM is valid.
	 */
	public Point getPoint() {
		return getPoint(Game.SCREEN);
	}
	
	/**
	 * Gets the nearest valid location point to the given point.
	 * @param point The point to get the nearest to.
	 * @param bounds The bounds to search within.
	 * @return A point where the DTM is valid.
	 */
	public Point getNearest(final Point point, final Rectangle bounds) {
		return Calculations.getNearestPoint(Mouse.getLocation(), getAll(bounds));
	}
	
	/**
	 * Gets the nearest valid location point to the given point.
	 * @param point The point to get the nearest to.
	 * @return A point where the DTM is valid.
	 */
	public Point getNearest(final Point point) {
		return getNearest(point, Game.SCREEN);
	}

	/**
	 * Gets the nearest valid location point to the mouse.
	 * @param bounds The bounds to search within.
	 * @return A point where the DTM is valid.
	 */
	public Point getNearest(final Rectangle bounds) {
		return getNearest(Mouse.getLocation(), bounds);
	}

	/**
	 * Gets the nearest valid location point to the mouse.
	 * @return A point where the DTM is valid.
	 */
	public Point getNearest() {
		return getNearest(Game.SCREEN);
	}

	/**
	 * Gets the first valid location point.
	 * @param bounds The bounds to search within.
	 * @return The first point where the DTM is valid.
	 */
	public Point getFirst(final Rectangle bounds) {
		final LinkedList<Point> points = ImageUtil.getPointsWithColor(Game.getImage(), bounds, root.getColor(), root.getTolerance());
		for (final Point point : points) {
			if (isValidAt(point)) {
				return point;
			}
		}
		return null;
	}

	/**
	 * Gets the first valid location point.
	 * @return The first point where the DTM is valid.
	 */
	public Point getFirst() {
		return getFirst(Game.SCREEN);
	}

	/**
	 * Constructs a polygon from the branches of the DTM relative to the given point.
	 * @param point The point to treat as the root.
	 * @return A polygon of the DTM's inner area.
	 */
	public Polygon getPolygon(final Point point) {
		final LinkedList<Point> points = new LinkedList<Point>();
		final DTMBranch[] branches = getBranches();
		for(int i = 0; i < branches.length; i++) {
			points.add(new Point(point.x + branches[i].getX(), point.y + branches[i].getY()));
		}
		return toPolygon(points);
	}

	private static Polygon toPolygon(final List<Point> points) {
		final int[] x = new int[points.size()];
		final int[] y = new int[points.size()];
		for(int i = 0; i < points.size(); i++) {
			x[i] = points.get(i).x;
		}
		for(int i = 0; i < points.size(); i++) {
			y[i] = points.get(i).y;
		}
		return new Polygon(x, y, points.size());
	}

	/**
	 * Checks if a valid location point exists within the supplied bounds.
	 * @param bound The bounds to search within.
	 * @return <tt>true</tt> if a point was found; otherwise <tt>false</tt>.
	 */
	public boolean isValid(final Rectangle bound) {
		return getFirst(bound) != null;
	}

	/**
	 * Checks if a valid location point exists.
	 * @return <tt>true</tt> if a point was found; otherwise <tt>false</tt>.
	 */
	public boolean isValid() {
		return isValid(Game.SCREEN);
	}

	/**
	 * Checks if the DTM is valid at a given point, assuming that the supplied point is a valid root point.
	 * @param point The point to check.
	 * @return <tt>true</tt> if found, otherwise <tt>false</tt>.
	 */
	private boolean isValidAt(final Point point) {
		for (final DTMBranch branch : branches) {
			if (!branch.isValidAt(point)) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Gets an interaction point of the DTM, determined by a gaussian-distributed random point within the branch polygon, within the given bounds.
	 * @param bounds The bounds to search within.
	 * @return A valid interaction point; or null if DTM not found.
	 */
	public Point getInteractionPoint(final Rectangle bounds) {
		final Point point = getPoint(bounds);
		if (point != null) {
			final Polygon poly = getPolygon(point);
			return Random.getRandomPoint(poly);
		}
		return null;
	}

	/**
	 * Gets an interaction point of the DTM, determined by a gaussian-distributed random point within the branch polygon.
	 * @return A valid interaction point; or null if DTM not found.
	 */
	public Point getInteractionPoint() {
		return getInteractionPoint(Game.SCREEN);
	}
	
	/**
	 * Clicks the DTM at an interaction point.
	 * @param left <tt>true</tt> for left click; <tt>false</tt> for right click.
	 * @return <tt>true</tt> if DTM found and clicked; otherwise <tt>false</tt>.
	 */
	public boolean click(boolean left) {
		final Point point = getInteractionPoint();
		if (point != null) {
			Mouse.click(point, left);
			return true;
		}
		return false;
	}

	/**
	 * Clicks the DTM at an interaction point.
	 * @return <tt>true</tt> if DTM was found and clicked; otherwise <tt>false</tt>.
	 */
	public boolean click() {
		return click(true);
	}

	/**
	 * Clicks the DTM at its root point.
	 * @param left <tt>true</tt> for left click; <tt>false</tt> for right click.
	 * @return <tt>true</tt> if DTM found and clicked; otherwise <tt>false</tt>.
	 */
	public boolean clickRoot(boolean left) {
		final Point point = getPoint();
		if (point != null) {
			Mouse.click(point, left);
			return true;
		}
		return false;
	}

	/**
	 * Clicks the DTM at its root point.
	 * @return <tt>true</tt> if DTM found and clicked; otherwise <tt>false</tt>.
	 */
	public boolean clickRoot() {
		return clickRoot(true);
	}

	/**
	 * Interacts a given action on the DTM at an interaction point.
	 * @param action The action to interact.
	 * @return <tt>true</tt> if DTM was found and action was successfully interacted with; otherwise <tt>false</tt>.
	 * @see Menu#interact(String)
	 */
	public boolean interact(final String action) {
		final Point point = getInteractionPoint();
		return point != null ? Menu.interact(action, point) : false;
	}

	/**
	 * Gets this DTM's root.
	 * @return This DTM's root.
	 */
	public DTMRoot getRoot() {
		return root;
	}

	/**
	 * Gets this DTM's branches.
	 * @return This DTM's branches.
	 */
	public DTMBranch[] getBranches() {
		return branches;
	}

	/**
	 * Returns a string representation of the DTM in format "root,branch,branch,...".
	 * @return A string representation of the DTM.
	 */
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		final char q = '"';
		final char c = ',';
		sb.append(q);
		sb.append(root.toString());
		for (final DTMBranch branch : getBranches()) {
			sb.append(c);
			sb.append(branch.toString());
		}
		sb.append(q);
		return sb.toString();
	}

	/**
	 * Constructs a DTM from a DTM string representation, in format "root,branch,branch,..."
	 * @param dtm The DTM string representation.
	 * @return The parsed DTM.
	 * @see DTMRoot#fromString(String)
	 * @see DTMBranch#fromString(String)
	 * @see #toString()
	 */
	public static DTM fromString(final String dtm) {
		final String[] split = dtm.split(",");
		final DTMRoot root = DTMRoot.fromString(split[0]);
		final LinkedList<DTMBranch> branches = new LinkedList<DTMBranch>();
		for (int i = 1; i < split.length; i++) {
			branches.add(DTMBranch.fromString(split[i]));
		}
		return new DTM(root, branches.toArray(new DTMBranch[branches.size()]));
	}

	/**
	 * Constructs a DTM from a root point and an array of branch points,
	 * @param root The root point of the DTM.
	 * @param branches The branch points of the DTM.
	 * @return The parsed DTM.
	 * @see DTMRoot#fromString(String)
	 * @see DTMBranch#fromString(String)
	 */
	public static DTM fromString(final String rootstr, final String...branchstrs) {
		final DTMRoot root = DTMRoot.fromString(rootstr);
		final LinkedList<DTMBranch> branches = new LinkedList<DTMBranch>();
		for (final String branch : branchstrs) {
			branches.add(DTMBranch.fromString(branch));
		}
		return new DTM(root, branches.toArray(new DTMBranch[branches.size()]));
	}
	
	/**
	 * Returns true if a DTM is valid out of an array of DTMs,
	 * @return True if a DTM in the array is valid.
	 * @param dtms An array of DTMs
	 */
	public boolean areDtmsValid(DTM... dtms) {
		for (DTM d : dtms) {
			if (d.isValid()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns A DTM out of an array of DTMs that is valid (Used to check for multiple valid DTMs),
	 * @return A DTM that is valid out of the DTM array
	 * @param dtms An array of DTMs
	 */
	public DTM getValidDtms(DTM... dtms) {
		for (DTM d : dtms) {
			if (d.isValid()) {
				return d;
			}
		}
		return null;
	}
}