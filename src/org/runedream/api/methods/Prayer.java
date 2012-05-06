package org.runedream.api.methods;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.runedream.api.methods.Game;
import org.runedream.api.methods.Mouse;
import org.runedream.api.util.Random;
import org.runedream.api.wrappers.Tab;

/**
 * Prayer interface convenience methods.
 * 
 * @author Amplex
 */
public final class Prayer {
	
	public static final Color ACTIVATED = new Color(171, 154, 108);
	public static final Color QUICKPRAYER_ACTIVATED = new Color(155, 186, 150);
	public static final Rectangle QUICKPRAYER_BOUNDS = new Rectangle(705, 53, 34, 33);
	public static final Rectangle QUICKPRAYER_TEXT_BOUNDS = new Rectangle(740, 62, 22, 23);
	
	private Prayer() {
	}

	/**
	 * Checks if the inventory tab is open.
	 * @return <tt>true</tt> if open; otherwise <tt>false</tt>.
	 */
	public static boolean isOpen() {
		return Tabs.getOpenTab() != null && Tabs.getOpenTab().equals(Tab.PRAYER);
	}

	/**
	 * Opens the prayer tab.
	 */
	public static void open() {
		Tabs.openTab(Tab.PRAYER);
	}
	
	/**
	 * Gets the center of the quick prayer button.
	 * @return The center of the quick prayer button.
	 */
	public static Point getQuickPrayerButtonCenter() {
		return new Point((int) (QUICKPRAYER_BOUNDS.x + (QUICKPRAYER_BOUNDS.width / 2)), (int) (QUICKPRAYER_BOUNDS.y + (QUICKPRAYER_BOUNDS.height / 2)));
	}
	
	/**
	 * Gets a random interaction point.
	 * @return A random interaction point.
	 */
	public static Point getQuickPrayerButtonRandomPoint() {
		return Random.getRandomPoint(QUICKPRAYER_BOUNDS);
	}
	
	/**
	 * Gets the quick prayer button color array.
	 * @return The quick prayer button color array.
	 */
	public static Color[] getQuickPrayerButtonColors() {
		final List<Color> colors = new LinkedList<Color>();
		for (int x = QUICKPRAYER_BOUNDS.x; x < QUICKPRAYER_BOUNDS.x + QUICKPRAYER_BOUNDS.width; x++) {
			for (int y = QUICKPRAYER_BOUNDS.y; y < QUICKPRAYER_BOUNDS.y + QUICKPRAYER_BOUNDS.height; y++) {
				final Point p = new Point(x, y);
				if (Game.isPointValid(p)) {
					colors.add(Game.getColorAt(p));
				}
			}
		}
		return colors.toArray(new Color[colors.size()]);
	}
	
	/**
	 * Checks if quick prayers are activated.
	 * @return <tt>true</tt> if activated; otherwise <tt>false</tt>.
	 */
	public static boolean isQuickPrayerActivated() {
		final List<Color> colors = Arrays.asList(getQuickPrayerButtonColors());
		return colors.contains(QUICKPRAYER_ACTIVATED);
	}
	
	/**
	 * Activates or deactivates quick prayers.
	 * @param activated <tt>true</tt> to activate quick prayers; <tt>false</tt> to deactivate.
	 * @return <tt>true</tt> if set successfully; otherwise <tt>false</tt>.
	 */
	public static boolean setQuickPrayerEnabled(final boolean activated) {
		if (isQuickPrayerActivated() != activated) {
			Mouse.click(getQuickPrayerButtonRandomPoint());
		}
		return isQuickPrayerActivated();
	}
	
	/**
	 * Modern prayer enum.
	 */
	public enum Modern implements Effect {
		THICK_SKIN("Thick Skin", 1, new Rectangle(555, 212, 30, 32)),
		BURST_STRENGTH("Burst Strength", 4, new Rectangle(592, 212, 30, 32)), 
		CLARITY_THOUGHT("Clarity Thought", 7, new Rectangle(629, 212, 30, 32)),
		SHARP_EYE("Sharp Eye", 8, new Rectangle(666, 212, 30, 32)), 
		MYSTIC_WILL("Mystic Will", 9, new Rectangle(703, 212, 30, 32)), 
		ROCK_SKIN("Rock Skin", 10, new Rectangle(555, 248, 30, 32)), 
		SUPERHUMAN_STRENGTH("Superhuman Strength", 13, new Rectangle(592, 248, 30, 32)), 
		IMPROVED_REFLEXES("Improved Reflexes", 16, new Rectangle(629, 248, 30, 32)), 
		RAPID_RESTORE("Rapid Restore", 19, new Rectangle(666, 248, 30, 32)), 
		RAPID_HEAL("Rapid Heal", 22, new Rectangle(703, 248, 30, 32)), 
		PROTECT_ITEM("Protect Item", 25, new Rectangle(555, 284, 30, 32)), 
		HAWK_EYE("Hawk Eye", 26, new Rectangle(592, 284, 30, 32)), 
		MYSTIC_LORE("Mystic Lore", 27, new Rectangle(629, 284, 30, 32)), 
		STEEL_SKIN("Steel Skin", 28, new Rectangle(666, 284, 30, 32)), 
		ULTIMATE_STRENGTH("Ultimate Strength", 31, new Rectangle(703, 284, 30, 32)), 
		INCREDIBLE_REFLEXES("Incredible Reflexes", 34, new Rectangle(555, 321, 30, 32)), 
		PROTECT_SUMMONING("Protect from Summoning", 35, new Rectangle(592, 321, 30, 32)), 
		PROTECT_MAGIC("Protect from Magic", 37, new Rectangle(629, 321, 30, 32)), 
		PROTECT_MISSILES("Protect from Missiles", 40, new Rectangle(666, 321, 30, 32)), 
		PROTECT_MELEE("Protect from Melee", 43, new Rectangle(703, 321, 30, 32)), 
		EAGLE_EYE("Eagle Eye", 45, new Rectangle(555, 357, 30, 32)), 
		MYSTIC_MIGHT("Mystic Might", 46, new Rectangle(592, 357, 30, 32)), 
		RETRIBUTION("Retribution", 47, new Rectangle(629, 357, 30, 32)), 
		REDEMPTION("Redemption", 49, new Rectangle(666, 357, 30, 32)), 
		SMITE("Smite", 52, new Rectangle(703, 357, 30, 32)), 
		CHIVALRY("Chivalry", 60, new Rectangle(555, 392, 30, 32)),
		RAPID_RENEWAL("Rapid Renewal", 65, new Rectangle(592, 392, 30, 32)),
		PIETY("Piety", 70, new Rectangle(629, 392, 30, 32)),
		RIGOUR("Rigour", 74, new Rectangle(666, 392, 30, 32)),
		AUGURY("Augury", 77, new Rectangle(703, 392, 30, 32));
		
		private final String name;
		private final int required_level;
		private final Rectangle bounds;

		private Modern(final String name, final int required_level, final Rectangle bounds) {
			this.name = name;
			this.required_level = required_level;
			this.bounds = bounds;
		}

		public String getName() {
			return name;
		}
		
		public int getLevel() {
			return required_level;
		}

		public Rectangle getBounds() {
			return bounds;
		}

		public Point getCenter() {
			return new Point((int) (bounds.x + (bounds.width / 2)), (int) (bounds.y + (bounds.height / 2)));
		}
		
		public Point getRandomPoint() {
			return Random.getRandomPoint(bounds);
		}
		
		public Color[] getColors() {
			open();
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
		
		public boolean isActivated() {
			open();
			final List<Color> colors = Arrays.asList(getColors());
			return colors.contains(ACTIVATED);
		}
		
		public boolean setEnabled(final boolean activated) {
			open();
			if (isActivated() != activated) {
				Mouse.click(getCenter(), 6, 6);
			}
			return isActivated();
		}
	}
	
	/**
	 * Ancient prayer enum.
	 * 
	 * @author Viper
	 */
	public enum Ancient implements Effect {
		PROTECT_ITEM("Protect Item", 50, new Rectangle(555, 212, 30, 32)),
		SAP_WARRIOR("Sap Warrior", 50, new Rectangle(592, 212, 30, 32)), 
		SAP_RANGER("Sap Ranger", 52, new Rectangle(629, 212, 30, 32)),
		SAP_MAGE("Sap Mage", 54, new Rectangle(666, 212, 30, 32)), 
		SAP_SPIRIT("Sap Spirit", 56, new Rectangle(703, 212, 30, 32)), 
		BERSERKER("Berserker", 59, new Rectangle(555, 248, 30, 32)), 
		DEFLECT_SUMMONING("Deflect Summoning", 62, new Rectangle(592, 248, 30, 32)), 
		DEFLECT_MAGIC("Deflect Magic", 65, new Rectangle(629, 248, 30, 32)), 
		DEFLECT_MISSILES("Deflect Missiles", 68, new Rectangle(666, 248, 30, 32)), 
		DEFLECT_MELEE("Deflect Melee", 71, new Rectangle(703, 248, 30, 32)), 
		LEECH_ATTACK("Lecch Attack", 74, new Rectangle(555, 284, 30, 32)), 
		LEECH_RANGED("Leech Ranged", 76, new Rectangle(592, 284, 30, 32)), 
		LEECH_MAGIC("Leech Magic", 78, new Rectangle(629, 284, 30, 32)), 
		LEECH_DEFENCE("Leech Defence", 80, new Rectangle(666, 284, 30, 32)), 
		LEECH_STRENGTH("Leech Strength", 82, new Rectangle(703, 284, 30, 32)), 
		LEECH_ENERGY("Leech Energy", 84, new Rectangle(555, 321, 30, 32)), 
		LEECH_SPECIAL_ATTACK("Leech Special Attack", 86, new Rectangle(592, 321, 30, 32)), 
		WRATH("Wrath", 89, new Rectangle(629, 321, 30, 32)), 
		SOUL_SPLIT("Soul Split", 92, new Rectangle(666, 321, 30, 32)), 
		TURMOIL("Turmoil", 95, new Rectangle(703, 321, 30, 32));
		
		private final String name;
		private final int required_level;
		private final Rectangle bounds;

		private Ancient(final String name, final int required_level, final Rectangle bounds) {
			this.name = name;
			this.required_level = required_level;
			this.bounds = bounds;
		}

		public String getName() {
			return name;
		}
		
		public int getLevel() {
			return required_level;
		}

		public Rectangle getBounds() {
			return bounds;
		}

		public Point getCenter() {
			return new Point((int) (bounds.x + (bounds.width / 2)), (int) (bounds.y + (bounds.height / 2)));
		}
		
		public Point getRandomPoint() {
			return Random.getRandomPoint(bounds);
		}
		
		public Color[] getColors() {
			open();
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
		
		public boolean isActivated() {
			open();
			final List<Color> colors = Arrays.asList(getColors());
			return colors.contains(ACTIVATED);
		}
		
		public boolean setEnabled(final boolean activated) {
			open();
			if (isActivated() != activated) {
				Mouse.click(getCenter(), 6, 6);
			}
			return isActivated();
		}
	}
	
	/**
	 * Prayer effect interface.
	 */
	public interface Effect {
		
		/**
		 * Gets the prayer's name.
		 * @return The prayer's name.
		 */
		public String getName();

		/**
		 * Gets the prayer's required level.
		 * @return The prayer's required level.
		 */
		public int getLevel();

		/**
		 * Gets the prayer's bounding rectangle.
		 * @return The prayer's bounding rectangle.
		 */
		public Rectangle getBounds();

		/**
		 * Gets the prayer's center point.
		 * @return The prayer's center point.
		 */
		public Point getCenter();
		
		/**
		 * Gets a random interaction point.
		 * @return A random interaction point.
		 */
		public Point getRandomPoint();

		/**
		 * Gets the prayer's color array.
		 * @return The prayer's color array.
		 */
		public Color[] getColors();

		/**
		 * Checks if the prayer is activated.
		 * @return <tt>true</tt> if active; otherwise <tt>false</tt>.
		 */
		public boolean isActivated();
		
		/**
		 * Activates or deactivates the prayer.
		 * @param activated <tt>true</tt> to activate the prayer; <tt>false</tt> to deactivate.
		 * @return <tt>true</tt> if succesfu
		 */
		public boolean setEnabled(final boolean activated);
	}

}
