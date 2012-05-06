package org.runedream.api.methods;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GameCanvas;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.runedream.api.util.Filter;
import org.runedream.api.util.Random;

/**
 * Basic game image methods.
 */
public final class Game {

	public static final Color ENERGY_ENABLED = new Color(252, 219, 1);
	public static final Rectangle ENERGY = new Rectangle(705, 93, 34, 33);
	public static final Rectangle ENERGY_TEXT_BOUNDS = new Rectangle(740, 102, 22, 23);
	public static final Rectangle COMPASS = new Rectangle(521, 3, 44, 42);
	public static final Rectangle MINIMAP = new Rectangle(548, 5, 155, 155);
	public static final Rectangle WORLD_MAP = new Rectangle(521, 120, 44, 42);
	public static final Rectangle VIEWPORT = new Rectangle(4, 4, 511, 334);
	public static final Rectangle SCREEN = new Rectangle(0, 0, 765, 503);
	public static final Point[] CORNER_POINTS = { new Point(0, 0), new Point(764, 0), new Point(0, 502), new Point(764, 502) };
	public static final Color[] CORNER_COLORS = { new Color(33, 27, 12), new Color(49, 43, 33), new Color(40, 36, 25), new Color(39, 35, 24) };
	
	private Game() {
	}
	
	/**
	 * Gets the game image.
	 * @return The game image.
	 */
	public static BufferedImage getImage() {
		return GameCanvas.getImage();
	}
	
	/**
	 * Gets the color at a given x-y coordinate on the game image.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @return The color at the given coordinates.
	 */
	public static Color getColorAt(final int x, final int y) {
		return ImageUtil.getColorAt(Game.getImage(), x, y);
	}
	
	/**
	 * Gets the color at a given point on the game image.
	 * @param p The point
	 * @return The color at the given point.
	 */
	public static Color getColorAt(final Point p) {
		return getColorAt(p.x, p.y);
	}
	
	/**
	 * Gets the array of all colors of the game image.
	 * @return A two-dimensional array of the colors of the game image.
	 */
	public static Color[][] getColors() {
		return ImageUtil.getColors(Game.getImage());
	}
	
	/**
	 * Gets all points within the game image which have a color within threshold distance of a given color.
	 * @param color The color to scan for.
	 * @param threshold The threshold to scan by.
	 * @return A list of points where the color of the game image is within the threshold.
	 */
	public static List<Point> getPointsWithColor(final Color color, final int threshold) {
		return ImageUtil.getPointsWithColor(Game.getImage(), color, threshold);
	}

	/**
	 * Gets all points within the game image which have a color equal to a given color.
	 * @param color The color to scan for.
	 * @return A list of points where the color of the game image is equal.
	 */
	public static List<Point> getPointsWithColor(final Color color) {
		return getPointsWithColor(color, 0);
	}
	
	/**
	 * Gets the first point within a bounded area that is accepted by a filter.
	 * @param bound The bounds to search within.
	 * @param filter The filter to apply.
	 * @return The point found; or null if none accepted.
	 * @author Dang
	 */
    public Point getPoint(final Rectangle bound, final Filter<Point> filter) {
        for (int x = bound.x; x < bound.x + bound.width; x++) {
             for (int y = bound.y; y < bound.y + bound.height; y++) {
                  if (filter.accept(new Point(x, y))) {
                       return new Point(x, y);
                  }
             }
        }
        return null;
   }

	/**
	 * Gets all points within a bounded area that are accepted by a filter.
	 * @param bound The bounds to search within.
	 * @param filter The filter to apply.
	 * @return The points found; or an empty array if none accepted.
	 * @author Dang
	 */
   public Point[] getPoints(final Rectangle bound, final Filter<Point> filter) {
        final List<Point> points = new LinkedList<Point>();
        for (int x = bound.x; x < bound.x + bound.width; x++) {
             for (int y = bound.y; y < bound.y + bound.height; y++) {
                  if (filter.accept(new Point(x, y))) {
                       points.add(new Point(x, y));
                  }
             }
        }
        return points.toArray(new Point[points.size()]);
   }

	/**
	 * Gets the first point that is accepted by a filter.
	 * @param filter The filter to apply.
	 * @return The point found; null if none accepted.
	 * @author Dang
	 */
   public Point getPoint(final Filter<Point> filter) {
        final Dimension bounds = getCanvasSize();
        return bounds != null ? getPoint(new Rectangle(0, 0, bounds.width, bounds.height), filter) : null;
   }

	/**
	 * Gets all points that are accepted by a filter.
	 * @param filter The filter to apply.
	 * @return The points found; or an empty array if none accepted.
	 * @author Dang
	 */
   public Point[] getPoints(final Filter<Point> filter) {
        final Dimension bounds = Game.getCanvasSize();
        return bounds != null ? getPoints(new Rectangle(0, 0, bounds.width, bounds.height), filter) : null;
   }
	
	/**
	 * Gets the size of the game canvas.
	 * @return The game's dimension.
	 */
	public static Dimension getCanvasSize() {
		final BufferedImage image = getImage();
		return new Dimension(image.getWidth(), image.getHeight());
	}
	
	/**
	 * Gets whether a point is on the game canvas or not.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @return <tt>true</tt> if valid; otherwise <tt>false</tt>.
	 */
	public static boolean isPointValid(final int x, final int y) {
		final Dimension size = getCanvasSize();
		return x >= 0 && y >= 0 && x <= size.width && y <= size.height;
	}
	
	/**
	 * Gets whether a point is on the game canvas or not.
	 * @param p The point.
	 * @return <tt>true</tt> if valid; otherwise <tt>false</tt>.
	 */
	public static boolean isPointValid(final Point p) {
		return isPointValid(p.x, p.y);
	}
	
	/**
	 * Checks if the game has a character logged in.  Does not account for Squeal of Fortune and other "covering" interfaces.
	 * @return <tt>true</tt> if logged in; otherwise <tt>false</tt>.
	 */
	public static boolean isLoggedIn() {
		int valids = 0;
		for (int i = 0; i < CORNER_POINTS.length; i++) {
			if (ColorUtil.isTolerable(getColorAt(CORNER_POINTS[i]), CORNER_COLORS[i], 1)) {
				valids++;
			}
		}
		return valids >= 2;
	}
	
	/**
	 * Gets the center of the energy toggle button.
	 * @return The energy button's center.
	 */
	public static Point getEnergyButtonCenter() {
		return new Point((int) (ENERGY.x + (ENERGY.width / 2)), (int) (ENERGY.y + (ENERGY.height / 2)));
	}
	
	/**
	 * Gets the color array of the energy button's display.
	 * @return The energy button's colors.
	 */
	public static Color[][] getEnergyButtonColors() {
		final Color[][] colors = new Color[ENERGY.width][ENERGY.height];
		for (int x = ENERGY.x; x < ENERGY.x + ENERGY.width; x++) {
			for (int y = ENERGY.y; y < ENERGY.y + ENERGY.height; y++) {
				colors[x][y] = Game.getColors()[x][y];
			}
		}
		return colors;
	}
	
	/**
	 * Checks if run is enabled.
	 * @return <tt>true</tt> if enabled; otherwise <tt>false</tt>.
	 */
	public static boolean isRunEnabled() {
		for (final Color[] carr : getEnergyButtonColors()) {
			for (final Color c : carr) {
				if (c.equals(ENERGY_ENABLED)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Sets running mode.
	 * @param run <tt>true</tt> to turn run on; <tt>false</tt> to turn it off.
	 */
	public static void setRun(final boolean run) {
		if (isRunEnabled() != run) {
			Mouse.click(Random.getRandomPoint(ENERGY));
		}
	}
	
	/**
	 * Clicks the game compass.
	 */
	public static void clickCompass() {
		Mouse.click(Random.getRandomPoint(COMPASS));
	}
	
	/**
	 * Opens the world map.
	 */
	public static void openWorldMap() {
		Mouse.click(Random.getRandomPoint(WORLD_MAP));
	}
	
	/**
	 * Experience button.
	 * 
	 * @author Static
	 */
	public static class Experience {
		
		public static final Rectangle BOUNDS = new Rectangle(517, 47, 33, 33);
		public static final Rectangle DISPLAY_BOUNDS = new Rectangle(425, 53, 88, 18);
		public static final Color[] TOGGLE_COLORS = {new Color(255, 255, 255),  new Color(77, 73, 62), new Color(66, 65, 58)};
		
		/**
		 * Gets the center of the experience button.
		 * @return The experience button's center.
		 */
		public static Point getCenter() {
			return new Point((int) (BOUNDS.x + (BOUNDS.width / 2)), (int) (BOUNDS.y + (BOUNDS.height / 2)));
		}
		
		/**
		 * Gets a random interaction point.
		 * @return A random interaction point.
		 */
		public static Point getRandomPoint() {
			return Random.getRandomPoint(BOUNDS);
		}
		
		/**
		 * Gets the color array of the experience button.
		 * @return The experience button's colors.
		 */
		public static Color[][] getColors() {
			final Color[][] colors = new Color[BOUNDS.width][BOUNDS.height];
			for (int x = BOUNDS.x; x < BOUNDS.x + BOUNDS.width; x++) {
				for (int y = BOUNDS.y; y < BOUNDS.y + BOUNDS.height; y++) {
					colors[x][y] = Game.getColors()[x][y];
				}
			}
			return colors;
		}
		
		/**
		 * Gets the color array of the experience button's display.
		 * @return The experience button's display colors.
		 */
		public static Color[] getDisplayColors() {
			final List<Color> colors = new LinkedList<Color>();
			for (int x = DISPLAY_BOUNDS.x; x < DISPLAY_BOUNDS.x + DISPLAY_BOUNDS.width; x++) {
				for (int y = DISPLAY_BOUNDS.y; y < DISPLAY_BOUNDS.y + DISPLAY_BOUNDS.height; y++) {
					final Point p = new Point(x, y);
					if (Game.isPointValid(p)) {
						colors.add(Game.getColorAt(p));
					}
				}
			}
			return colors.toArray(new Color[colors.size()]);
		}
		
		/**
		 * Checks if the experience button is toggled.
		 * @return <tt>true</tt> if toggled; otherwise <tt>false</tt>.
		 */
		public static boolean isToggled() {
			final List<Color> list = Arrays.asList(getDisplayColors());
			for (final Color c : TOGGLE_COLORS) {
				if (!list.contains(c)) {
					return false;
				}
			}
			return true;
		}
		
		/**
		 * Toggles the experience button on or off.
		 * @param toggle <tt>true</tt> to toggle on; <tt>false</tt> to toggle off.
		 */
		public static void setToggled(final boolean toggle) {
			if (isToggled() != toggle) {
				Mouse.click(getRandomPoint());
			}
		}
	}
	
	/**
	 * Money pouch button.
	 * 
	 * @author Static
	 */
	public static class MoneyPouch {
		
		public static final Rectangle BOUNDS = new Rectangle(517, 82, 33, 33);
		public static final Rectangle DISPLAY_BOUNDS = new Rectangle(450, 87, 65, 24);
		public static final Color[] TOGGLE_COLORS_UNHIGHLIGHTED = {new Color(60, 41, 20), new Color(27, 28, 35)};
		public static final Color[] TOGGLE_COLORS_HIGHLIGHTED = {new Color(60, 41, 20), new Color(52, 57, 82)};

		/**
		 * Gets the center of the money pouch button.
		 * @return The money pouch button's center.
		 */
		public static Point getCenter() {
			return new Point((int) (BOUNDS.x + (BOUNDS.width / 2)), (int) (BOUNDS.y + (BOUNDS.height / 2)));
		}
		
		/**
		 * Gets a random interaction point.
		 * @return A random interaction point.
		 */
		public static Point getRandomPoint() {
			return Random.getRandomPoint(BOUNDS);
		}

		/**
		 * Gets the color array of the experience button.
		 * @return The experience button's colors.
		 */
		public static Color[] getColors() {
			final List<Color> colors = new LinkedList<Color>();
			for (int x = BOUNDS.x; x < BOUNDS.x + BOUNDS.width; x++) {
				for (int y = BOUNDS.y; y < BOUNDS.y + BOUNDS.height; y++) {
					final Point p = new Point(x, y);
					if (Game.isPointValid(p)) {
						colors.add(Game.getColorAt(p));
					}
				}
			}
			return colors.toArray(new Color[colors.size()]);
		}

		/**
		 * Gets the color array of the experience button's display.
		 * @return The experience button's display colors.
		 */
		public static Color[] getDisplayColors() {
			final List<Color> colors = new LinkedList<Color>();
			for (int x = DISPLAY_BOUNDS.x; x < DISPLAY_BOUNDS.x + DISPLAY_BOUNDS.width; x++) {
				for (int y = DISPLAY_BOUNDS.y; y < DISPLAY_BOUNDS.y + DISPLAY_BOUNDS.height; y++) {
					final Point p = new Point(x, y);
					if (Game.isPointValid(p)) {
						colors.add(Game.getColorAt(p));
					}
				}
			}
			return colors.toArray(new Color[colors.size()]);
		}

		/**
		 * Checks if the experience button is toggled.
		 * @return <tt>true</tt> if toggled; otherwise <tt>false</tt>.
		 */
		public static boolean isToggled() {
			final Color[] colors = getColors();
			return Arrays.binarySearch(colors, TOGGLE_COLORS_UNHIGHLIGHTED) != -1 || 
					Arrays.binarySearch(colors, TOGGLE_COLORS_HIGHLIGHTED) != -1;
		}

		/**
		 * Toggles the experience button on or off.
		 * @param toggle <tt>true</tt> to toggle on; <tt>false</tt> to toggle off.
		 */
		public static boolean click(final boolean toggle) {
			if (isToggled() != toggle) {
				Mouse.click(getRandomPoint());
			}
			return isToggled();
		}
	}
	
}
