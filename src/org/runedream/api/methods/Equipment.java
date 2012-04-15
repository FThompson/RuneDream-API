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
	
	public enum Slot {
		SLOT_AURA("Aura", new Rectangle(586, 211, 28, 28)),
		SLOT_HELMIT("Helmit", new Rectangle(626, 211, 28, 28)),
		SLOT_CAPE("Cape", new Rectangle(586, 250, 28, 28)),
		SLOT_NECKLACE("Necklace", new Rectangle(626, 250, 28, 28)),
		SLOT_AMMO("Ammo", new Rectangle(667, 250, 28, 28)),
		SLOT_WEAPON("Weapon", new Rectangle(569, 289, 28, 28)),
		SLOT_BODY("Body", new Rectangle(626, 289, 28, 28)),
		SLOT_SHIELD("Shield", new Rectangle(682, 289, 28, 28)),
		SLOT_LEGS("Legs", new Rectangle(626, 329, 28, 28)),
		SLOT_GLOVES("Gloves", new Rectangle(569, 368, 28, 28)),
		SLOT_BOOTS("Boots", new Rectangle(626, 368, 28, 28)),
		SLOT_RING("Ring", new Rectangle(682, 368, 28, 28)),
		BUTTON_EQUIPMENT("Equipement", new Rectangle(580, 417, 28, 28)),
		BUTTON_ITEMS_KEPT_ON_DEATH("Kept on Death", new Rectangle(626, 417, 28, 28)),
		BUTTON_TOOLBELT("Toolbelt", new Rectangle(670, 417, 28, 28));
		
		private final String name;
		private final Rectangle bounds;
		
		private Slot(final String name, final Rectangle bounds) {
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
		
		public void click() {
			Mouse.click(getCenter());
		}
		
		public void click(final boolean left) {
			Mouse.click(getCenter(), left);
		}
		
	}

	public static boolean isOpen() {
		return Tabs.getOpenTab().equals(Tab.EQUIPMENT);
	}
	
	public static void open() {
		Tabs.openTab(Tab.EQUIPMENT);
	}

}