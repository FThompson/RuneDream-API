package org.runedream.api.methods;

import java.awt.Point;
import java.awt.Rectangle;

import org.runedream.api.methods.Mouse;
import org.runedream.api.wrappers.Tab;

/**
 * Equipment interface convenience methods.
 * 
 * @author Viper
 */
public class Equipment {
	
	public static final Rectangle BOUNDS = new Rectangle(545, 206, 192, 260);
	
	/**
	 * An enumeration of the equipment interface slots.
	 */
	public enum Slot {
		AURA("Aura", new Rectangle(586, 211, 28, 28)),
		HELMIT("Helmit", new Rectangle(626, 211, 28, 28)),
		CAPE("Cape", new Rectangle(586, 250, 28, 28)),
		NECKLACE("Necklace", new Rectangle(626, 250, 28, 28)),
		AMMO("Ammo", new Rectangle(667, 250, 28, 28)),
		WEAPON("Weapon", new Rectangle(569, 289, 28, 28)),
		BODY("Body", new Rectangle(626, 289, 28, 28)),
		SHIELD("Shield", new Rectangle(682, 289, 28, 28)),
		LEGS("Legs", new Rectangle(626, 329, 28, 28)),
		GLOVES("Gloves", new Rectangle(569, 368, 28, 28)),
		BOOTS("Boots", new Rectangle(626, 368, 28, 28)),
		RING("Ring", new Rectangle(682, 368, 28, 28));
		
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
		 * Clicks the slot.
		 */
		public void click() {
			Mouse.click(getCenter());
		}
		
		/**
		 * Clicks the slot.
		 * @param left <tt>true</tt> for left click; <tt>false</tt> for right click.
		 */
		public void click(final boolean left) {
			Mouse.click(getCenter(), left);
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