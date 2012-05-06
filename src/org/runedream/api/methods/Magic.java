package org.runedream.api.methods;

import java.awt.Point;
import java.awt.Rectangle;

import org.runedream.api.methods.Mouse;
import org.runedream.api.util.Random;
import org.runedream.api.wrappers.Tab;

/**
 * Methods for interaction with the magic tab interface.
 * 
 * @author Viper
 */
public final class Magic {
	
	public static final Rectangle BOUNDS = new Rectangle(545, 206, 192, 260);
	
	private Magic() {
	}
	
	/**
	 * SortButton convenience interface.
	 * 
	 * @author Vulcan
	 */
	public static interface SortButton {
		
		/**
		 * Gets the button's name.
		 * @return The button's name.
		 */
		public String getName();

		/**
		 * Gets the button's bounding rectangle.
		 * @return The button's bounding rectangle.
		 */
		public Rectangle getBounds();

		/**
		 * Gets the button's center point.
		 * @return The button's center point.
		 */
		public Point getCenter();
		
		/**
		 * Gets a random interaction point.
		 * @return A random interaction point.
		 */
		public Point getRandomPoint();

		/**
		 * Clicks the button.
		 */
		public void click();
		
		/**
		 * Clicks the button.
		 * @param left <tt>true</tt> for left click; <tt>false</tt> for right click.
		 */
		public void click(final boolean left);
	}
	
	/**
	 * An enumeration of modern spellbook sort buttons.
	 */
	public enum ModernButton implements SortButton {
		BUTTON_TOGGLE_CASTING("Toggle Defensive Casting", new Rectangle(557, 443, 13, 13)),
		BUTTON_COMBAT_SPELLS("Toggle Combat Spells", new Rectangle(586, 443, 13, 13)),
		BUTTON_TELEPORT_SPELLS("Toggle Teleport Spells", new Rectangle(604, 443, 13, 13)),
		BUTTON_MISCELLANEOUS_SPELLS("Toggle Miscellaneous Spells", new Rectangle(626, 443, 13, 13)),
		BUTTON_SKILL_SPELLS("Toggle Skills Spells", new Rectangle(647, 443, 13, 13)),
		BUTTON_SORT_LEVEL("Toggle Sort by Level", new Rectangle(674, 443, 13, 13)),
		BUTTON_SORT_COMBAT("Toggle Sort by Combat", new Rectangle(694, 443, 13, 13)),
		BUTTON_SORT_TELEPORT("Toggle Sort by Teleport", new Rectangle(714, 443, 13, 13)),
		BUTTON_SCROLL_UP("Scroll Up", new Rectangle(722, 207, 11, 11)),
		BUTTON_SCROLL_DOWN("Scroll Down", new Rectangle(722, 419, 11, 11));
		
		private final String name;
		private final Rectangle bounds;
		
		private ModernButton(final String name, final Rectangle bounds) {
			this.name = name;
			this.bounds = bounds;
		}
		
		public String getName() {
			return name;
		}
		
		public Rectangle getBounds() {
			return bounds;
		}
		
		public Point getCenter() {
			return new Point(bounds.x + (int) (bounds.width / 2), bounds.y + (int) (bounds.height / 2));
		}
		
		public Point getRandomPoint() {
			return Random.getRandomPoint(bounds);
		}
		
		public void click() {
			Mouse.click(getCenter());
		}
		
		public void click(final boolean left) {
			Mouse.click(getCenter(), left);
		}
	}

	/**
	 * An enumeration of ancient spellbook sort buttons.
	 */
	public enum AncientButton implements SortButton {
		BUTTON_TOGGLE_CASTING("Toggle Defensive Casting", new Rectangle(565, 443, 13, 13)),
		BUTTON_COMBAT_SPELLS("Toggle Combat Spells", new Rectangle(598, 443, 13, 13)),
		BUTTON_TELEPORT_SPELLS("Toggle Teleport Spells", new Rectangle(633, 443, 13, 13)),
		BUTTON_SORT_LEVEL("Toggle Sort by Level", new Rectangle(674, 443, 13, 13)),
		BUTTON_SORT_COMBAT("Toggle Sort by Combat", new Rectangle(694, 443, 13, 13)),
		BUTTON_SORT_TELEPORT("Toggle Sort by Teleport", new Rectangle(714, 443, 13, 13));
		
		private final String name;
		private final Rectangle bounds;
		
		private AncientButton(final String name, final Rectangle bounds) {
			this.name = name;
			this.bounds = bounds;
		}
		
		public String getName() {
			return name;
		}
		
		public Rectangle getBounds() {
			return bounds;
		}
		
		public Point getCenter() {
			return new Point(bounds.x + (int) (bounds.width / 2), bounds.y + (int) (bounds.height / 2));
		}
		
		public Point getRandomPoint() {
			return Random.getRandomPoint(bounds);
		}
		
		public void click() {
			Mouse.click(getCenter());
		}
		
		public void click(final boolean left) {
			Mouse.click(getCenter(), left);
		}
	}

	/**
	 * An enumeration of lunar spellbook sort buttons.
	 */
	public enum LunarButton implements SortButton {
		BUTTON_TOGGLE_CASTING("Toggle Defensive Casting", new Rectangle(557, 443, 13, 13)),
		BUTTON_COMBAT_SPELLS("Toggle Combat Spells", new Rectangle(586, 443, 13, 13)),
		BUTTON_TELEPORT_SPELLS("Toggle Teleport Spells", new Rectangle(604, 443, 13, 13)),
		BUTTON_MISCELLANEOUS_SPELLS("Toggle Miscellaneous Spells", new Rectangle(626, 443, 13, 13)),
		BUTTON_SKILL_SPELLS("Toggle Skills Spells", new Rectangle(647, 443, 13, 13)),
		BUTTON_SORT_LEVEL("Toggle Sort by Level", new Rectangle(674, 443, 13, 13)),
		BUTTON_SORT_COMBAT("Toggle Sort by Combat", new Rectangle(694, 443, 13, 13)),
		BUTTON_SORT_TELEPORT("Toggle Sort by Teleport", new Rectangle(714, 443, 13, 13)),
		BUTTON_SCROLL_UP("Scroll Up", new Rectangle(722, 207, 11, 11)),
		BUTTON_SCROLL_DOWN("Scroll Down", new Rectangle(722, 419, 11, 11));
		
		private final String name;
		private final Rectangle bounds;
		
		private LunarButton(final String name, final Rectangle bounds) {
			this.name = name;
			this.bounds = bounds;
		}
		
		public String getName() {
			return name;
		}
		
		public Rectangle getBounds() {
			return bounds;
		}
		
		public Point getCenter() {
			return new Point(bounds.x + (int) (bounds.width / 2), bounds.y + (int) (bounds.height / 2));
		}
		
		public Point getRandomPoint() {
			return Random.getRandomPoint(bounds);
		}
		
		public void click() {
			Mouse.click(getCenter());
		}
		
		public void click(final boolean left) {
			Mouse.click(getCenter(), left);
		}
	}

	/**
	 * Checks if the inventory tab is open.
	 * @return <tt>true</tt> if open; otherwise <tt>false</tt>.
	 */
	public static boolean isOpen() {
		return Tabs.getOpenTab() != null && Tabs.getOpenTab().equals(Tab.MAGIC);
	}

	/**
	 * Opens the magic tab.
	 */
	public static void open() {
		Tabs.openTab(Tab.MAGIC);
	}

}