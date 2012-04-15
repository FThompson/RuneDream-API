package org.runedream.api.wrappers;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.List;

import org.runedream.api.methods.Game;

/**
 * Enumeration of the control panel tabs.
 * 
 * @author Amplex, Static
 */
public enum Tab {
	COMBAT(0, new Rectangle(522, 170, 30, 33)),
	TASKS(1, new Rectangle(552, 170, 30, 33)),
	STATS(2, new Rectangle(582, 170, 30, 33)),
	QUESTS(3, new Rectangle(612, 170, 30, 33)),
	INVENTORY(4, new Rectangle(642, 170, 30, 33)),
	EQUIPMENT(5, new Rectangle(672, 170, 30, 33)), 
	PRAYER(6, new Rectangle(702, 170, 30, 33)),
	MAGIC(7, new Rectangle(732, 170, 30, 33)),
	FRIEND_LIST(8, new Rectangle(552, 468, 30, 33)),
	FRIENDS_CHAT(9, new Rectangle(582, 468, 30, 33)),
	CLAN_CHAT(10, new Rectangle(612, 468, 30, 33)),
	OPTIONS(11, new Rectangle(642, 467, 30, 33)),
	EMOTES(12, new Rectangle(672, 468, 30, 33)),
	MUSIC(13, new Rectangle(702, 467, 30, 33)),
	NOTES(14, new Rectangle(732, 467, 30, 33));

	private final int index;
	private final Rectangle bounds;
	public static final Color TAB_OPEN = new Color(254, 239, 114);

	private Tab(final int index, final Rectangle bounds) {
		this.index = index;
		this.bounds = bounds;
	}

	/**
	 * Gets the tab index.
	 * @return The tab's index.
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * Gets the tab bounds.
	 * @return The tab's bounds.
	 */
	public Rectangle getBounds() {
		return bounds;
	}

	/**
	 * Gets the tab's center point.
	 * @return The tab's center.
	 */
	public Point getCenter() {
		return new Point(bounds.x + (int) (bounds.width / 2), bounds.y + (int) (bounds.height / 2));
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