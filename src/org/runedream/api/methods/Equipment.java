package org.runedream.api.methods;

import java.awt.Point;
import java.awt.Rectangle;

import org.runedream.api.methods.Mouse;
import org.runedream.api.methods.Tabs;
import org.runedream.api.util.Random;
import org.runedream.api.wrappers.Tab;

/**
 * Equipment interface convenience methods.
 * 
 * @author Viper
 */
public final class Equipment {

	public static final Rectangle BOUNDS = new Rectangle(545, 206, 192, 260);
	
	private Equipment() {
	}

	/**
	 * An enumeration of the equipment interface slots.
	 */
	public enum Slot {
		AURA("Aura", new Rectangle(585, 211, 33, 31)),
		HELMET("Helmet", new Rectangle(626, 211, 33, 31)),
		CAPE("Cape", new Rectangle(585, 250, 33, 31)),
		NECKLACE("Necklace", new Rectangle(626, 250, 33, 31)),
		AMMO("Ammo", new Rectangle(667, 250, 33, 31)),
		WEAPON("Weapon", new Rectangle(570, 289, 33, 31)),
		BODY("Body", new Rectangle(626, 289, 33, 31)),
		SHIELD("Shield", new Rectangle(683, 289, 33, 31)),
		LEGS("Legs", new Rectangle(626, 329, 33, 31)),
		GLOVES("Gloves", new Rectangle(570, 369, 33, 31)),
		BOOTS("Boots", new Rectangle(626, 369, 33, 31)),
		RING("Ring", new Rectangle(682, 369, 33, 31));

		private final String name;
		private final Rectangle bounds;

		private Slot(final String name, final Rectangle bounds) {
			this.name = name;
			this.bounds = bounds;
		}

		/**
		 * Gets the slot's name.
		 * @return The slot's name.
		 */
		public String getName() {
			return name;
		}

		/**
		 * Gets the slot's bounding rectangle.
		 * @return The slot's bounding rectangle.
		 */
		public Rectangle getBounds() {
			return bounds;
		}

		/**
		 * Gets the slot's center point.
		 * @return The slot's center point.
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
		 * Clicks the slot.
		 */
		public void click() {
			Mouse.click(getRandomPoint());
		}

		/**
		 * Clicks the slot.
		 * @param left <tt>true</tt> for left click; <tt>false</tt> for right click.
		 */
		public void click(final boolean left) {
			Mouse.click(getRandomPoint(), left);
		}

		/**
		 * Hovers over the slot.
		 * @author Aidden
		 */
		public void hover() {
			Mouse.move(getRandomPoint());
		}
	}

	/**
	 * An enumeration of the equipment interface buttons.
	 */
	public enum Button {
		WORN_EQUIPMENT("Equipment", new Rectangle(580, 417, 28, 28)),
		ITEMS_KEPT_ON_DEATH("Kept on Death", new Rectangle(626, 417, 28, 28)),
		TOOLBELT("Toolbelt", new Rectangle(670, 417, 28, 28));

		private final String name;
		private final Rectangle bounds;

		private Button(final String name, final Rectangle bounds) {
			this.name = name;
			this.bounds = bounds;
		}

		/**
		 * Gets the button's name.
		 * @return The button's name.
		 */
		public String getName() {
			return name;
		}

		/**
		 * Gets the button's bounding rectangle.
		 * @return The button's bounding rectangle.
		 */
		public Rectangle getBounds() {
			return bounds;
		}

		/**
		 * Gets the button's center point.
		 * @return The button's center point.
		 */
		public Point getCenter() {
			return new Point(bounds.x + (int) (bounds.width / 2), bounds.y + (int) (bounds.height / 2));
		}

		/**
		 * Clicks the button.
		 */
		public void click() {
			Mouse.click(getCenter());
		}

		/**
		 * Clicks the button.
		 * @param left <tt>true</tt> for left click; <tt>false</tt> for right click.
		 */
		public void click(final boolean left) {
			Mouse.click(getCenter(), left);
		}
	}

	/**
	 * Checks if the equipment tab is open.
	 * @return <tt>true</tt> if open; otherwise <tt>false</tt>.
	 */
	public static boolean isOpen() {
		return Tabs.getOpenTab() != null && Tabs.getOpenTab().equals(Tab.EQUIPMENT);
	}

	/**
	 * Opens the equipment tab.
	 */
	public static void open() {
		Tabs.openTab(Tab.EQUIPMENT);
	}

}