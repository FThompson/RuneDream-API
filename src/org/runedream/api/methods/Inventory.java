package org.runedream.api.methods;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.List;

import org.runedream.api.methods.Game;
import org.runedream.api.methods.Mouse;

public class Inventory {
	
	public static final Rectangle BOUNDS = new Rectangle(545, 206, 192, 260);
	
	public enum Slot {
		SLOT_0(0, new Rectangle(561, 212, 36, 32)),
		SLOT_1(1, new Rectangle(603, 212, 36, 32)),
		SLOT_2(2, new Rectangle(645, 212, 36, 32)),
		SLOT_3(3, new Rectangle(687, 212, 36, 32)),
		SLOT_4(4, new Rectangle(561, 248, 36, 32)),
		SLOT_5(5, new Rectangle(603, 248, 36, 32)),
		SLOT_6(6, new Rectangle(645, 248, 36, 32)),
		SLOT_7(7, new Rectangle(687, 248, 36, 32)),
		SLOT_8(8, new Rectangle(561, 283, 36, 32)),
		SLOT_9(9, new Rectangle(603, 283, 36, 32)),
		SLOT_10(10, new Rectangle(645, 283, 36, 32)),
		SLOT_11(11, new Rectangle(687, 283, 36, 32)),
		SLOT_12(12, new Rectangle(561, 319, 36, 32)),
		SLOT_13(13, new Rectangle(603, 319, 36, 32)),
		SLOT_14(14, new Rectangle(645, 319, 36, 32)),
		SLOT_15(15, new Rectangle(687, 319, 36, 32)),
		SLOT_16(16, new Rectangle(561, 355, 36, 32)),
		SLOT_17(17, new Rectangle(603, 355, 36, 32)),
		SLOT_18(18, new Rectangle(645, 355, 36, 32)),
		SLOT_19(19, new Rectangle(687, 355, 36, 32)),
		SLOT_20(20, new Rectangle(561, 391, 36, 32)),
		SLOT_21(21, new Rectangle(603, 391, 36, 32)),
		SLOT_22(22, new Rectangle(645, 391, 36, 32)),
		SLOT_23(23, new Rectangle(687, 391, 36, 32)),
		SLOT_24(24, new Rectangle(561, 427, 36, 32)),
		SLOT_25(25, new Rectangle(603, 427, 36, 32)),
		SLOT_26(26, new Rectangle(645, 427, 36, 32)),
		SLOT_27(27, new Rectangle(687, 427, 36, 32));
		
		private final int index;
		private final Rectangle bounds;
		
		private Slot(final int index, final Rectangle bounds) {
			this.index = index;
			this.bounds = bounds;
		}
		
		public static Slot getSlot(final int index) {
			for (final Slot slot : Slot.values()) {
				if (slot.getIndex() == index) {
					return slot;
				}
			}
			return null;
		}
		
		public int getIndex() {
			return index;
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
		
		// TODO: Use item corners and center for checking (dice)
		// because center point can be empty for certain items
		public boolean isEmpty() {
			final Color center_color = getCenterColor();
			final int min_red = 63;
			final int max_red = 72;
			final int min_green = 53;
			final int max_green = 64;
			final int min_blue = 44;
			final int max_blue = 52;
			if (center_color != null) {
				final int center_red = center_color.getRed();
				final int center_green = center_color.getGreen();
				final int center_blue = center_color.getBlue();
				if (center_red >= min_red && center_red <= max_red) {
					if (center_green >= min_green && center_green <= max_green) {
						if (center_blue >= min_blue && center_blue <= max_blue) {
							return true;
						}
					}
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
		return Game.getOpenTab().equals(Game.Tab.INVENTORY);
	}
	
	public static void open() {
		Game.openTab(Game.Tab.INVENTORY);
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
	
	public static int getCount(final Color... colors) {
		open();
		int count = 0;
		Slots: for (final Slot slot : Slot.values()) {
			if (!slot.isEmpty()) {
				for (final Color slot_color : slot.getColors()) {
					boolean found_color = false;
					for (final Color c : colors) {
						if (found_color) {
							continue Slots;
						}
						if (slot_color.equals(c)) {
							count++;
							found_color = true;
						}
					}
				}
			}
		}
		return count;
	}
	
	public static boolean isEmpty() {
		return getCount() == 0;
	}
	
	public static boolean isFull() {
		return getCount() == 28;
	}
	
	public static Slot getSlotAt(final int index) {
		for (final Slot slot : Slot.values()) {
			if (slot.getIndex() == index) {
				return slot;
			}
		}
		return null;
	}
	
	public static Slot getSlotWithColor(final Color color) {
		for (final Slot slot : Slot.values()) {
			for (final Color slot_color : slot.getColors()) {
				if (slot_color.equals(color)) {
					return slot;
				}
			}
		}
		return null;
	}

}
