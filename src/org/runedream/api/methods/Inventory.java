package org.runedream.api.methods;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.List;

import org.runedream.api.methods.Game;
import org.runedream.api.methods.Mouse;
import org.runedream.api.methods.Timing.Condition;
import org.runedream.api.wrappers.Tab;

/**
 * Inventory interface convenience methods.
 * 
 * @author Static
 */
public class Inventory {
	
	public static final Rectangle BOUNDS = new Rectangle(545, 206, 192, 260);
	public static final Color SLOT_BACKGROUND = new Color(63, 53, 44);
	
	public enum Slot {
		SLOT_0(new Rectangle(561, 212, 36, 32)),
		SLOT_1(new Rectangle(603, 212, 36, 32)),
		SLOT_2(new Rectangle(645, 212, 36, 32)),
		SLOT_3(new Rectangle(687, 212, 36, 32)),
		SLOT_4(new Rectangle(561, 248, 36, 32)),
		SLOT_5(new Rectangle(603, 248, 36, 32)),
		SLOT_6(new Rectangle(645, 248, 36, 32)),
		SLOT_7(new Rectangle(687, 248, 36, 32)),
		SLOT_8(new Rectangle(561, 283, 36, 32)),
		SLOT_9(new Rectangle(603, 283, 36, 32)),
		SLOT_10(new Rectangle(645, 283, 36, 32)),
		SLOT_11(new Rectangle(687, 283, 36, 32)),
		SLOT_12(new Rectangle(561, 319, 36, 32)),
		SLOT_13(new Rectangle(603, 319, 36, 32)),
		SLOT_14(new Rectangle(645, 319, 36, 32)),
		SLOT_15(new Rectangle(687, 319, 36, 32)),
		SLOT_16(new Rectangle(561, 355, 36, 32)),
		SLOT_17(new Rectangle(603, 355, 36, 32)),
		SLOT_18(new Rectangle(645, 355, 36, 32)),
		SLOT_19(new Rectangle(687, 355, 36, 32)),
		SLOT_20(new Rectangle(561, 391, 36, 32)),
		SLOT_21(new Rectangle(603, 391, 36, 32)),
		SLOT_22(new Rectangle(645, 391, 36, 32)),
		SLOT_23(new Rectangle(687, 391, 36, 32)),
		SLOT_24(new Rectangle(561, 427, 36, 32)),
		SLOT_25(new Rectangle(603, 427, 36, 32)),
		SLOT_26(new Rectangle(645, 427, 36, 32)),
		SLOT_27(new Rectangle(687, 427, 36, 32));
		
		private final Rectangle bounds;
		
		private Slot(final Rectangle bounds) {
			this.bounds = bounds;
		}
		
		public Rectangle getBounds() {
			return bounds;
		}
		
		public Point getCenter() {
			return new Point(bounds.x + (int) (bounds.width / 2), bounds.y + (int) (bounds.height / 2));
		}
		
		public Color getCenterColor() {
			final Point center = getCenter();
			return Game.isPointValid(center) ? Game.getColorAt(center) : null;
		}
		
		public Color[] getColors() {
			final List<Color> colors = new LinkedList<Color>();
			for (int x = bounds.x; x < bounds.x + bounds.width; x++) {
				for (int y = bounds.y; y < bounds.y + bounds.height; y++) {
					final Point p = new Point(x, y);
					if (Game.isPointValid(p)) {
						final Color color = Game.getColorAt(p);
						colors.add(color);
					}
				}
			}
			return colors.toArray(new Color[colors.size()]);
		}
		
		public boolean isEmpty() {
			final Color search_color = new Color(63, 53, 44);
			for (final Color slot_color : getColors()) {
				final double distance = ColorUtil.getDistance(slot_color, search_color);
				if (distance > 0.0925) {
					return false;
				}
			}
			return true;
		}
		
		public boolean isSelected() {
			final Rectangle bounds = getBounds();
			Color previous_color = null;
			Point previous_point = null;
			for (int x = bounds.x; x < bounds.x + bounds.width; x += 1) {
				for (int y = bounds.y; y < bounds.y + bounds.height; y += 1) {
					if (previous_color != null && previous_point != null) {
						if (ColorUtil.getDistance(SLOT_BACKGROUND, previous_color) <= 0.0925) {
							if (Game.getColorAt(x, y).equals(Color.WHITE)) {
								if (Calculations.getDistanceBetween(previous_point, new Point(x, y)) <= 1) {
									return true;
								}
							}
						}
					}
					previous_color = Game.getColorAt(x, y);
					previous_point = new Point(x, y);
				}
			}
			return false;
		}
		
		public void click() {
			Mouse.click(getCenter());
		}
		
		public void click(final boolean left) {
			Mouse.click(getCenter(), left);
		}

	}

	public static boolean isOpen() { 
		if (Tabs.getOpenTab() != null) { 
			if (Tabs.getOpenTab().equals(Tab.INVENTORY)) { 
				return true; 
			} 
		} 
		return Bank.isOpen(); 
	}

	public static boolean open() {
		if (isOpen()) {
			return true;
		} else {
			Tabs.openTab(Tab.INVENTORY);
			Timing.waitFor(1000, new Condition() {
				public boolean isMet() {
					return isOpen();
				}
			});
			return true;
		}
	}
	
	public static int getCount() {
		open();
		int count = 0;
		for (final Slot slot : Slot.values()) {
			if (!slot.isEmpty()) {
				count += 1;
			}
		}
		return count;
	}
	
	public static int getCount(final Color color, final double threshold) {
		open();
		int count = 0;
		for (final Slot slot : Slot.values()) {
			for (final Color slot_color : slot.getColors()) {
				if (ColorUtil.getDistance(slot_color, color) <= threshold) {
					count += 1;
					break;
				}
			}
		}
		return count;
	}
	
	public static int getCount(final Color color) {
		return getCount(color, 0.0);
	}
	
	public static boolean isEmpty() {
		return getCount() == 0;
	}
	
	public static boolean isFull() {
		return getCount() == 28;
	}
	
	public static Slot getSlotAt(final int index) {
		for (final Slot slot : Slot.values()) {
			if (slot.ordinal() == index) {
				return slot;
			}
		}
		return null;
	}
	
	public static Slot getSlotWithColor(final Color color, final double threshold) {
		for (final Slot slot : Slot.values()) {
			for (final Color slot_color : slot.getColors()) {
				if (ColorUtil.getDistance(slot_color, color) <= threshold) {
					return slot;
				}
			}
		}
		return null;
	}

	public static Slot getSlotWithColor(final Color color) {
		return getSlotWithColor(color, 0.0);
	}
	
	public static boolean isItemSelected() {
		for (final Slot slot : Inventory.Slot.values()) {
			if (slot.isSelected()) {
				return true;
			}
		}
		return false;
	}

}