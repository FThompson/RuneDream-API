package org.runedream.api.wrappers;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.List;

import org.runedream.api.methods.Game;
import org.runedream.api.util.Random;

/**
 * Enumeration of the control panel tabs.
 * 
 * @author Amplex, Static
 */
public enum Tab {
	COMBAT(new Rectangle(522, 170, 30, 33), KeyEvent.VK_F5),
	TASKS(new Rectangle(552, 170, 30, 33), -1),
	STATS(new Rectangle(582, 170, 30, 33), -1),
	QUESTS(new Rectangle(612, 170, 30, 33), -1),
	INVENTORY(new Rectangle(642, 170, 30, 33), KeyEvent.VK_F1),
	EQUIPMENT(new Rectangle(672, 170, 30, 33), KeyEvent.VK_F2), 
	PRAYER(new Rectangle(702, 170, 30, 33), KeyEvent.VK_F3),
	MAGIC(new Rectangle(732, 170, 30, 33), KeyEvent.VK_F4),
	FRIEND_LIST(new Rectangle(552, 468, 30, 33), -1),
	FRIENDS_CHAT(new Rectangle(582, 468, 30, 33), -1),
	CLAN_CHAT(new Rectangle(612, 468, 30, 33), -1),
	OPTIONS(new Rectangle(642, 467, 30, 33), -1),
	EMOTES(new Rectangle(672, 468, 30, 33), -1),
	MUSIC(new Rectangle(702, 467, 30, 33), -1),
	NOTES(new Rectangle(732, 467, 30, 33), -1);

	private final Rectangle bounds;
	private final int functionKey;
	public static final Color TAB_OPEN = new Color(254, 239, 114);

	private Tab(final Rectangle bounds, final int functionKey) {
		this.bounds = bounds;
		this.functionKey = functionKey;
	}

	/**
	 * Gets the tab bounds.
	 * @return The tab's bounds.
	 */
	public Rectangle getBounds() {
		return bounds;
	}
	
	/**
	 * Gets the tab's function key shortcut.
	 * @return The tab's function key.
	 */
	public int getFunctionKey() {
		return functionKey;
	}
	
	/**
	 * Checks if the tab has a function key shortcut.
	 * @return <tt>true</tt> if has a function key; otherwise <tt>false</tt>.
	 */
	public boolean hasFunctionKey() {
		return functionKey != -1;
	}

	/**
	 * Gets the tab's center point.
	 * @return The tab's center.
	 */
	public Point getCenter() {
		return new Point(bounds.x + (int) (bounds.width / 2), bounds.y + (int) (bounds.height / 2));
	}
	
	/**
	 * Gets a random interaction point.
	 * @return A random interaction point.
	 */
	public Point getRandomPoint() {
		return Random.getRandomPoint(bounds);
	}

	/**
	 * Gets the tab's color array.
	 * @return The colors of the tab.
	 */
	public Color[] getColors() {
		final List<Color> colors = new LinkedList<Color>();
		for (int x = bounds.x; x < bounds.x + bounds.width; x++) {
			for (int y = bounds.y; y < bounds.y + bounds.height; y++) {
				final Point p = new Point(x, y);
				if (Game.isPointValid(p)) {
					colors.add(Game.getColorAt(p));
				}
			}
		}
		return colors.toArray(new Color[colors.size()]);
	}

	/**
	 * Checks if the tab is open.
	 * @return <tt>true</tt> if open; otherwise <tt>false</tt>.
	 */
	public boolean isOpen() {
		for (final Color color : getColors()) {
			if (color.equals(TAB_OPEN)) {
				return true;
			}
		}
		return false;
	}

}