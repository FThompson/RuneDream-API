package org.runedream.api.methods;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

import org.runedream.api.methods.Game;
import org.runedream.api.methods.Mouse;
import org.runedream.api.util.Random;
import org.runedream.api.util.Time;
import org.runedream.api.wrappers.DTM;
import org.runedream.api.wrappers.Menu;
import org.runedream.api.wrappers.Tab;

/**
 * Inventory interface convenience methods.
 * 
 * @author Static
 */
public final class Inventory {
	
	private static int[] path = {0, 4, 8, 12, 16, 20, 24, 1, 5, 9, 13, 17, 21, 25, 2, 6, 10, 14, 18, 22, 26, 3, 7, 11, 15, 19, 23, 27};
	public static final Rectangle BOUNDS = new Rectangle(545, 206, 192, 260);
	public static final Color SLOT_BACKGROUND = new Color(63, 53, 44);

	private Inventory() {
	}

	/**
	 * An enumeration of inventory item slots.
	 * 
	 * @author Static
	 */
	public enum Slot {
		SLOT_0(new Rectangle(561, 212, 36, 32)),
		SLOT_1(new Rectangle(603, 212, 36, 32)),
		SLOT_2(new Rectangle(645, 212, 36, 32)),
		SLOT_3(new Rectangle(687, 212, 36, 32)),
		SLOT_4(new Rectangle(561, 248, 36, 32)),
		SLOT_5(new Rectangle(603, 248, 36, 32)),
		SLOT_6(new Rectangle(645, 248, 36, 32)),
		SLOT_7(new Rectangle(687, 248, 36, 32)),
		SLOT_8(new Rectangle(561, 284, 36, 32)),
		SLOT_9(new Rectangle(603, 284, 36, 32)),
		SLOT_10(new Rectangle(645, 284, 36, 32)),
		SLOT_11(new Rectangle(687, 284, 36, 32)),
		SLOT_12(new Rectangle(561, 320, 36, 32)),
		SLOT_13(new Rectangle(603, 320, 36, 32)),
		SLOT_14(new Rectangle(645, 320, 36, 32)),
		SLOT_15(new Rectangle(687, 320, 36, 32)),
		SLOT_16(new Rectangle(561, 356, 36, 32)),
		SLOT_17(new Rectangle(603, 356, 36, 32)),
		SLOT_18(new Rectangle(645, 356, 36, 32)),
		SLOT_19(new Rectangle(687, 356, 36, 32)),
		SLOT_20(new Rectangle(561, 392, 36, 32)),
		SLOT_21(new Rectangle(603, 392, 36, 32)),
		SLOT_22(new Rectangle(645, 392, 36, 32)),
		SLOT_23(new Rectangle(687, 392, 36, 32)),
		SLOT_24(new Rectangle(561, 428, 36, 32)),
		SLOT_25(new Rectangle(603, 428, 36, 32)),
		SLOT_26(new Rectangle(645, 428, 36, 32)),
		SLOT_27(new Rectangle(687, 428, 36, 32));

		private final Rectangle bounds;

		private Slot(final Rectangle bounds) {
			this.bounds = bounds;
		}

		/**
		 * Gets the slot's bounding rectangle.
		 * @return the slot's bounding rectangle.
		 */
		public Rectangle getBounds() {
			return bounds;
		}

		/**
		 * Gets the slot's center point.
		 * @return the slot's center point.
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
		 * Gets the slot's center point color.
		 * @return the slot's center point color.
		 */
		public Color getCenterColor() {
			open();
			final Point center = getCenter();
			return Game.isPointValid(center) ? Game.getColorAt(center) : null;
		}

		/**
		 * Gets the slot's color array.
		 * @return the slot's color array.
		 */
		public Color[] getColors() {
			open();
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

		/**
		 * Checks if the slot is empty.
		 * @return <tt>true</tt> if the slot is empty; otherwise <tt>false</tt>.
		 */
		public boolean isEmpty() {
			return !isFull();
		}

		/**
		 * Checks if the slot contains an item.
		 * @return <tt>true</tt> if the slot is full; otherwise <tt>false</tt>.
		 */
		public boolean isFull() {
			open();
			final BufferedImage image = Game.getImage();
			for (int x = bounds.x; x < bounds.x + bounds.width; x++) {
				for (int y = bounds.y; y < bounds.y + bounds.height; y++) {
					if (ColorUtil.getDistance(new Color(image.getRGB(x, y)), SLOT_BACKGROUND) > 20) {
						return true;
					}
				}
			}
			return false;
		}

		/**
		 * Checks if the item in the slot is selected.
		 * @return <tt>true</tt> if the item is selected; otherwise <tt>false</tt>.
		 */
		public boolean isSelected() {
			open();
			final Rectangle bounds = getBounds();
			Color previous_color = null;
			Point previous_point = null;
			for (int x = bounds.x; x < bounds.x + bounds.width; x += 1) {
				for (int y = bounds.y; y < bounds.y + bounds.height; y += 1) {
					if (previous_color != null && previous_point != null) {
						if (ColorUtil.getDistance(SLOT_BACKGROUND, previous_color) <= 10) {
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
		
		/**
		 * Gets the amount of items in the slot
		 *
		 * @return the amount of items in the slot
		 */
		public int getStackSize() {
			if (isEmpty()) {
				return 0;
			}
			final Rectangle bounds = new Rectangle(this.bounds.x - 1, this.bounds.y - 2, this.bounds.width + 1, 16);
			String count = OCR.findString(bounds, new Color(255, 255, 0), OCR.FontType.STAT_CHARS);
			if (count != null) {
				count = count.replaceAll("K", "000");
				count = count.replaceAll("M", "000000");
				count = count.replaceAll("B", "000000000");
				return Integer.parseInt(count);
			}
			return 1;
		}

		/**
		 * Checks if the slot contains one of a given set of DTMs.
		 * @param dtms The DTMs to check for.
		 * @return <tt>true</tt> if one of the DTMs was found within the slot; otherwise <tt>false</tt>.
		 */
		public boolean containsOneOf(final DTM... dtms) {
			open();
			for (final DTM dtm : dtms) {
				if (dtm != null) {
					final Point[] points = dtm.getAll(getBounds());
					if (points != null && points.length > 0) {
						return true;
					}
				}
			}
			return false;
		}

		/**
		 * Checks if the slot contains a given DTM.
		 * @param dtms The DTM to check for.
		 * @return <tt>true</tt> if the DTM was found within the slot; otherwise <tt>false</tt>.
		 */
		public boolean contains(DTM dtm) {
			return containsOneOf(dtm);
		}

		/**
		 * Clicks the slot.
		 */
		public void click() {
			click(true);
		}

		/**
		 * Clicks the slot.
		 * @param left <tt>true</tt> for left click; <tt>false</tt> for right click.
		 */
		public void click(final boolean left) {
			open();
			Mouse.click(getRandomPoint(), left);
		}

		/**
		 * Interacts a given action with the slot's item.
		 * @param action The action to interact.
		 * @return <tt>true</tt> if interacted successfully; otherwise <tt>false</tt>.
		 */
		public boolean interact(final String action) {
			open();
			return Menu.interact(action, getRandomPoint());
		}
		
		/**
		 * Moves the mouse over the slot.
		 */
		public boolean hover() {
			open();
			Mouse.move(getRandomPoint());
			return bounds.contains(Mouse.getLocation());
		}

		/**
		 * Gets the slot with a certain index.
		 * @return The slot with a certain index; or null if an invalid index given.
		 */
		public static Slot getSlotWithIndex(final int index) {
			for (final Slot slot : Slot.values()) {
				if (slot.ordinal() == index) {
					return slot;
				}
			}
			return null;
		}
	}

	/**
	 * Checks if the inventory tab is open.
	 * @return <tt>true</tt> if open; otherwise <tt>false</tt>.
	 */
	public static boolean isOpen() { 
		return Tabs.getOpenTab() != null && Tabs.getOpenTab().equals(Tab.INVENTORY);
	}

	/**
	 * Opens the inventory tab.
	 */
	public static boolean open() {
		Tabs.openTab(Tab.INVENTORY);
		return Tab.INVENTORY.isOpen();
	}

	/**
	 * Gets the count of items in the inventory.
	 * @return The count of items in the inventory.
	 */
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

	/**
	 * Gets the count of items with the given color in the inventory.
	 * @param color The color to check for.
	 * @param tolerance The tolerance to check the color within.
	 * @return The count of items with the given color in the inventory.
	 */
	public static int getCount(final Color color, final int tolerance) {
		open();
		int count = 0;
		for (final Slot slot : Slot.values()) {
			for (final Color slot_color : slot.getColors()) {
				if (ColorUtil.getDistance(slot_color, color) <= tolerance) {
					count++;
					break;
				}
			}
		}
		return count;
	}

	/**
	 * Gets the count of items with the given color in the inventory.
	 * @param color The color to check for.
	 * @return The count of items with the given color in the inventory.
	 */
	public static int getCount(final Color color) {
		return getCount(color, 0);
	}
	
	/**
	 * Gets the count of items with the given DTM in the inventory.
	 * @param dtm The DTM to check for.
	 * @return The count of items with the given DTM in the inventory.
	 */
	public static int getCount(final DTM dtm) {
		open();
		final Point[] points = dtm.getAll(Inventory.BOUNDS);
		if (points != null && points.length > 0) {
			int count = 0;
			for (final Slot slot : Slot.values()) {
				for (final Point p : points) {
					if (slot.getBounds().contains(p)) {
						count++;
						break;
					}
				}
			}
			return count;
		}
		return 0;
	}

	/**
	 * Checks if the inventory is empty.
	 * @return <tt>true</tt> if empty; otherwise <tt>false</tt>.
	 */
	public static boolean isEmpty() {
		return getCount() == 0;
	}

	/**
	 * Checks if the inventory is full.
	 * @return <tt>true</tt> if full; otherwise <tt>false</tt>.
	 */
	public static boolean isFull() {
		return getCount() == 28;
	}

	/**
	 * Gets the slot at a given index.
	 * @param index The index to get.
	 * @return The Slot at the given index.
	 */
	public static Slot getSlotAt(final int index) {
		open();
		for (final Slot slot : Slot.values()) {
			if (slot.ordinal() == index) {
				return slot;
			}
		}
		return null;
	}

	/**
	 * Gets the first slot with a given color.
	 * @param color The color to check for.
	 * @param tolerance The tolerance to check the color within.
	 * @return The first slow with the given color.
	 */
	public static Slot getSlotWithColor(final Color color, final int tolerance) {
		open();
		for (final Slot slot : Slot.values()) {
			for (final Color slot_color : slot.getColors()) {
				if (ColorUtil.getDistance(slot_color, color) <= tolerance) {
					return slot;
				}
			}
		}
		return null;
	}

	/**
	 * Gets the first slot with a given color.
	 * @param color The color to check for.
	 * @return The first slow with the given color.
	 */
	public static Slot getSlotWithColor(final Color color) {
		return getSlotWithColor(color, 0);
	}

	/**
	 * Gets the first slot with the given DTM.
	 * @param dtm The DTM to find.
	 * @return The first slot containing the given DTM; or null if none found.
	 */
	public static Slot getSlotWithDTM(final DTM dtm) {
		open();
		final Point[] points = dtm.getAll(BOUNDS);
		if (points != null && points.length > 0) {
			for (final Slot slot : Slot.values()) {
				for (final Point point : points) {
					if (slot.getBounds().contains(point)) {
						return slot;
					}
				}
			}
		}
		return null;
	}

	/**
	 * Gets all of the slots containing the given DTM.
	 * @param dtm The DTM to find.
	 * @return All of the slots containing the given DTM; or an empty array if none found.
	 */
	public static Slot[] getSlotsWithDTM(final DTM dtm) {
		open();
		final LinkedList<Slot> slots = new LinkedList<Slot>();
		final Point[] points = dtm.getAll(BOUNDS);
		if (points != null && points.length > 0) {
			for (final Slot slot : Slot.values()) {
				for (final Point point : points) {
					if (slot.getBounds().contains(point)) {
						slots.add(slot);
						break;
					}
				}
			}
		}
		return slots.toArray(new Slot[slots.size()]);
	}

	/**
	 * Checks if an item is selected.
	 * @return <tt>true</tt> if an item is selected; otherwise <tt>false</tt>.
	 */
	public static boolean isItemSelected() {
		open();
		for (final Slot slot : Inventory.Slot.values()) {
			if (slot.isSelected()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets the slots with one of a set of given DTMs valid within them.
	 * @params dtm The DTMs to search for.
	 * @return The slots found; or an empty array if none.
	 */
	public static Slot[] getSlots(final DTM... dtms) {
		open();
		final LinkedList<Slot> slots = new LinkedList<Slot>();
		for (Slot slot : Slot.values()) {
			if (slot.containsOneOf(dtms)) {
				slots.add(slot);
			}
		}
		return slots.toArray(new Slot[slots.size()]);
	}

	/**
	 * Gets the first slot with one of a set of given DTMs valid within it
	 * @param dtms The DTMs to search for.
	 * @return The slot found; or null if none.
	 */
	public static Slot getSlot(final DTM... dtms) {
		final Slot[] slots = getSlots(dtms);
		if (slots.length > 0) {
			return slots[0];
		}
		return null;
	}
	

	/**
	 * Drops everything in the inventory.
	 * @param mousekeys <tt>true</tt> to click using virtual mouse keys (hops the mouse).
	 * @return <tt>true</tt> if the inventory is cleared; otherwise <tt>false</tt>.
	 */
	public static boolean dropAll(final boolean mousekeys) {
		return dropAllExcept(mousekeys);
	}

	/**
	 * Drops everything in the inventory.
	 * @return <tt>true</tt> if the inventory is cleared; otherwise <tt>false</tt>.
	 */
	public static boolean dropAll() {
		return dropAll(false);
	}

	/**
	 * Drops everything in the inventory with a given DTM.
	 * @param mousekeys mousekeys <tt>true</tt> to click using virtual mouse keys (hops the mouse).
	 * @param dtms The DTMs to drop.
	 * @return <tt>true</tt> if the inventory is cleared of items with given DTMs; otherwise <tt>false</tt>.
	 */
	public static boolean dropAll(final boolean mousekeys, final DTM... dtms) {
		if (isEmpty()) {
			return true;
		}
		boolean first = true;
		for (int i = 0; i <= 27; i++) {
			final Slot slot = Slot.getSlotWithIndex(path[i]);
			if (!slot.isEmpty() && slot.containsOneOf(dtms)) {
				if (first) {
					if (!slot.getBounds().contains(Mouse.getLocation())) {
						slot.hover();
					}
					first = false;
				}
				if (slot.getBounds().contains(Mouse.getLocation())) {
					Mouse.click(false);
				} else {
					if (mousekeys) {
						final Point center = slot.getCenter();
						final Point p = new Point(center.x, center.y + Random.random(-2, 2));
						Mouse.hop(p);
						Mouse.click(false);
					} else {
						slot.click(false);
					}
				}
				final Time.Condition c = new Time.Condition() {
					public boolean isMet() {
						return Menu.find() != null;
					}
				};
				Time.waitFor(500, c);
				final Menu menu = Menu.find();
				if (menu != null) {
					if (!menu.click(mousekeys, menu.getIndex("Drop"))) {
						return false;
					} else {
						for (int i2 = 0; i2 <= 27; i2++) {
							final Slot s = getSlotAt(path[i2]);
							if (!s.isEmpty() && s.containsOneOf(dtms)) {
								final Point center = s.getCenter();
								if (mousekeys) {
									Mouse.hop(new Point(center.x, center.y + Random.random(-2, 2)));
									break;
								} else {
									if (s.hover()) {
										break;
									}
								}
							}
						}
					}
				}
			}
		}
		return true;
	}
	
	/**
	 * Drops everything in the inventory with a given DTM.
	 * @param dtms The DTMs to drop.
	 * @return <tt>true</tt> if the inventory is cleared of items with given DTMs; otherwise <tt>false</tt>.
	 */
	public static boolean dropAll(final DTM... dtms) {
		return dropAll(false, dtms);
	}
	
	/**
	 * Drops everything in the inventory except those with a given DTM.
	 * @param mousekeys mousekeys <tt>true</tt> to click using virtual mouse keys (hops the mouse).
	 * @param dtms The DTMs to skip.
	 * @return <tt>true</tt> if the inventory is cleared of items without given DTMs; otherwise <tt>false</tt>.
	 */
	public static boolean dropAllExcept(final boolean mousekeys, final DTM... dtms) {
		if (isEmpty()) {
			return true;
		}
		boolean first = true;
		for (int i = 0; i <= 27; i++) {
			final Slot slot = Slot.getSlotWithIndex(path[i]);
			if (!slot.isEmpty() && !slot.containsOneOf(dtms)) {
				if (first) {
					if (!slot.getBounds().contains(Mouse.getLocation())) {
						slot.hover();
					}
					first = false;
				}
				if (slot.getBounds().contains(Mouse.getLocation())) {
					Mouse.click(false);
				} else {
					if (mousekeys) {
						final Point center = slot.getCenter();
						final Point p = new Point(center.x, center.y + Random.random(-2, 2));
						Mouse.hop(p);
						Mouse.click(false);
					} else {
						slot.click(false);
					}
				}
				final Time.Condition c = new Time.Condition() {
					public boolean isMet() {
						return Menu.find() != null;
					}
				};
				Time.waitFor(500, c);
				final Menu menu = Menu.find();
				if (menu != null) {
					if (!menu.click(mousekeys, menu.getIndex("Drop"))) {
						return false;
					} else {
						for (int i2 = 0; i2 <= 27; i2++) {
							final Slot s = getSlotAt(path[i2]);
							if (!s.isEmpty() && !s.containsOneOf(dtms)) {
								final Point center = s.getCenter();
								if (mousekeys) {
									Mouse.hop(new Point(center.x, center.y + Random.random(-2, 2)));
									break;
								} else {
									if (s.hover()) {
										break;
									}
								}
							}
						}
					}
				}
			}
		}
		return true;
	}

	/**
	 * Drops everything in the inventory except those with a given DTM.
	 * @param dtms The DTMs to skip.
	 * @return <tt>true</tt> if the inventory is cleared of items without given DTMs; otherwise <tt>false</tt>.
	 */
	public boolean dropAllExcept(final DTM... dtms) {
		return dropAllExcept(false, dtms);
	}
}