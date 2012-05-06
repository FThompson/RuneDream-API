package org.runedream.api.wrappers;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import org.runedream.api.methods.Game;
import org.runedream.api.methods.Mouse;
import org.runedream.api.methods.OCR;
import org.runedream.api.util.Time;

/**
 * Wrapper representing a menu and its properties.
 * 
 * @author eXoTiK, Vulcan
 */
public final class Menu {

	private boolean offScreen = false;
	private Rectangle menu = null;
	private ArrayList<Point> corners = new ArrayList<Point>();
	private ArrayList<MenuAction> actions = new ArrayList<MenuAction>();

	public static final Color BORDER_BLACK = new Color(0, 0, 0);
	public static final Color BORDER_GRAY = new Color(166, 166, 166);
	public static final Color BORDER_WHITE = new Color(251, 251, 251);
	
	private Menu() {
		getCorners();
	}
	
	/**
	 * Finds a menu in the game viewport.
	 * @return A valid Menu object, or null if no menu could be identified.
	 */
	public static Menu find() {
		final Menu menu = new Menu();
		if (!menu.isValid()) {
			return null;
		}
		menu.getBounds();
		menu.getActions();
		return menu;
	}

	/**
	 * Gets the corners of the menu, finding and defining them if it hasn't been done already.
	 * @return The Menu's corner points.
	 */
	public Point[] getCorners() {
		if (corners.isEmpty()) {
			for (int x = 0; x < Game.getCanvasSize().width; x++) {
				for (int y = 0; y < Game.getCanvasSize().height; y++) {
					if (Game.getColorAt(new Point(x, y)).equals(BORDER_BLACK) 
							&& (Game.getColorAt(new Point(x, y + 1)).equals(BORDER_GRAY)
							|| (Game.getColorAt(new Point(x, y - 1)).equals(BORDER_GRAY)
							&& Game.getColorAt(new Point(x, y - 2)).equals(BORDER_WHITE)))) {
						corners.add(corners.size(), new Point(x, y));
					}
				}
			}
		}
		return corners.toArray(new Point[corners.size()]);
	}
	
	/**
	 * Gets the bounding rectangle of the menu, finding and defining it if it hasn't been done already.
	 * @return The Menu's bounding rectangle.
	 */
	public Rectangle getBounds() {
		if (menu == null) {
			final Point upperLeft = corners.get(0);
			final Point upperRight = corners.get(1);
			if (corners.size() == 2) {
				offScreen = true;
				final int height = Game.getCanvasSize().height - upperLeft.y - 1;
				corners.add(2, new Point(upperLeft.x, upperLeft.y + height));
				corners.add(3, new Point(upperRight.x, upperRight.y + height));
				menu = new Rectangle(upperLeft.x, upperLeft.y, corners.get(corners.size() - 1).x
						- upperLeft.x, height);
			} else if (corners.size() == 4) {
				menu = new Rectangle(upperLeft.x, upperLeft.y, corners.get(corners.size() - 1).x
						- upperLeft.x, corners.get(corners.size()- 1).y - upperLeft.y);
			}
		}
		return menu;
	}
	
	/**
	 * Gets the actions of the menu, finding and defining them if it hasn't been done already.
	 * @return The Menu's actions.
	 * @see MenuAction
	 */
	public MenuAction[] getActions() {
		if (actions.isEmpty()) {
			final Point upperLeft = corners.get(0);
			int options = (corners.get(corners.size() - 1).y - upperLeft.y - 25) / 16;
			if (offScreen) {
				options++;
			}
			for (int i = 0; i < options; i++) {
				Rectangle rectangle = null;
				if (offScreen && i == options) {
					rectangle = new Rectangle(upperLeft.x, upperLeft.y + 21 + (i * 16),
							corners.get(corners.size() - 1).x - upperLeft.x, 16);
				} else {
					rectangle = new Rectangle(upperLeft.x, upperLeft.y + 21 + (i * 16),
							corners.get(corners.size() - 1).x - upperLeft.x, 17);
				}
				final String option = OCR.findString(rectangle, null, true);
				actions.add(i, new MenuAction(option, rectangle));
			}
		}
		return actions.toArray(new MenuAction[actions.size()]);
	}
	
	/**
	 * Gets the menu's actions as Strings.
	 * @return The menu's actions as Strings.
	 * @see MenuAction#getAction()
	 */
	public String[] getActionStrings() {
		String[] strings = new String[actions.size()];
		for (int i = 0; i < strings.length; i++) {
			strings[i] = actions.get(i).getAction();
		}
		return strings;
	}
	
	/**
	 * Gets the menu's actions' bounding rectangles.
	 * @return The menu's actions' bounding rectangles.
	 * @see MenuAction#getBounds()
	 */
	public Rectangle[] getActionRectangles() {
		Rectangle[] rects = new Rectangle[actions.size()];
		for (int i = 0; i < rects.length; i++) {
			rects[i] = actions.get(i).getBounds();
		}
		return rects;
	}
	
	/**
	 * Checks if the menu is valid (i.e. has at least two corners, as the menu could go "offscreen").
	 * <br>
	 * {@link Menu#isOpen()} should be used to check if the menu is still open after instantiation.
	 * @return <tt>true</tt> if the menu was valid at the time of its instantiation; otherwise <tt>false</tt>.
	 * @see Menu#isOpen()
	 */
	public boolean isValid() {
		return corners.size() >= 2;
	}
	
	/**
	 * Checks if the menu is open.
	 * @return <tt>true</tt> if the menu is open; otherwise <tt>false</tt>.
	 */
	public boolean isOpen() {
		for (final Point p : corners) {
			if (!(Game.getColorAt(new Point(p.x, p.y)).equals(BORDER_BLACK) 
					&& (Game.getColorAt(new Point(p.x, p.y + 1)).equals(BORDER_GRAY)
					|| (Game.getColorAt(new Point(p.x, p.y - 1)).equals(BORDER_GRAY)
					&& Game.getColorAt(new Point(p.x, p.y - 2)).equals(BORDER_WHITE))))) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Checks if the menu's actions contain a given action.
	 * @param action The action to check for containing.
	 * @return <tt>true</tt> if contained; otherwise <tt>false</tt>.
	 * @see String#contains(CharSequence)
	 */
	public boolean contains(final String action) {
		return getIndex(action) >= 0;
	}
	
	/**
	 * Gets the MenuAction index of a given action.
	 * @param action The action to check for.
	 * @return A valid index, or -1 if none contain the given action.
	 */
	public int getIndex(final String action) {
		for (int i = 0; i < actions.size(); i++) {
			if (actions.get(i).contains(action)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Clicks a given index of the menu.
	 * @param index The index to click.
	 * @return <tt>true</tt> if a valid index was given and clicked; otherwise <tt>false</tt>.
	 */
	public boolean click(final int index) {
		if (index < 0 || index >= actions.size()) {
			return false;
		}
		final Rectangle action = actions.get(index).getBounds();
		final int halfWidth = action.width / 2;
		final int halfHeight = action.height / 2;
		Mouse.click(action.x + halfWidth, action.y + halfHeight, halfWidth, halfHeight);
		return true;
	}
	
	/**
	 * Clicks the first MenuAction containing a given action.
	 * @param action The action to interact.
	 * @return <tt>true</tt> if action was successfully interacted with; otherwise <tt>false</tt>.
	 */
	public boolean click(final String action) {
		return click(getIndex(action));
	}
	
	/**
	 * Interacts a given action at a given point.
	 * @param action The action to interact.
	 * @param point The point to interact at.
	 * @return <tt>true</tt> if action was successfully interacted with; otherwise <tt>false</tt>.
	 */
	public static boolean interact(final String action, final Point point) {
		Mouse.click(point, false);
		Time.sleep(30, 50);
		final Menu menu = Menu.find();
		if (menu != null) {
			return menu.click(action);
		}
		return false;
	}
	
	/**
	 * Interacts a given action.
	 * @param action The action to interact.
	 * @return <tt>true</tt> if action was successfully interacted with; otherwise <tt>false</tt>.
	 */
	public static boolean interact(final String action) {
		return interact(action, Mouse.getLocation());
	}
}