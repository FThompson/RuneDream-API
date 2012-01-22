package org.runedream.api.methods;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.runedream.api.methods.Game;
import org.runedream.api.methods.Mouse;

public class Prayer {
	
	public static final Color ACTIVATED = new Color(171, 154, 108);
	public static final Color QUICKPRAYER_ACTIVATED = new Color(155, 186, 150);
	public static final Rectangle QUICKPRAYER_BOUNDS = new Rectangle(705, 53, 34, 33);
	public static final Rectangle QUICKPRAYER_TEXT_BOUNDS = new Rectangle(740, 62, 22, 23);
	
	public static boolean isOpen() {
		return Game.Tab.PRAYER.isOpen();
	}
	
	public static void open() {
		Game.Tab.PRAYER.open();
	}
	
	public static Point getButtonCenter() {
		return new Point((int) (QUICKPRAYER_BOUNDS.x + (QUICKPRAYER_BOUNDS.width / 2)), (int) (QUICKPRAYER_BOUNDS.y + (QUICKPRAYER_BOUNDS.height / 2)));
	}
	
	public static Color[] getButtonColors() {
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
	
	public static boolean isQuickPrayerActivated() {
		final List<Color> colors = Arrays.asList(getButtonColors());
		return colors.contains(QUICKPRAYER_ACTIVATED);
	}
	
	public static boolean setQuickPrayerEnabled(final boolean activated) {
		if (isQuickPrayerActivated() != activated) {
			Mouse.click(getButtonCenter());
		}
		return isQuickPrayerActivated();
	}
		
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
		
	public enum Ancient implements Effect {
		; // TODO
		
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
	
	public interface Effect {
		
		public String getName();
		
		public int getLevel();
		
		public Rectangle getBounds();
		
		public Point getCenter();
		
		public Color[] getColors();
		
		public boolean isActivated();
		
		public boolean setEnabled(final boolean activated);
		
	}

}
