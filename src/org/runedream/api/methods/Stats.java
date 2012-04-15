package org.runedream.api.methods;

import java.awt.Point;
import java.awt.Rectangle;

import org.runedream.api.methods.Mouse;
import org.runedream.api.wrappers.Tab;

/**
 * Stats interface convenience methods.
 * 
 * @author Viper
 */
public class Stats {
	
	public static final Rectangle BOUNDS = new Rectangle(545, 206, 192, 260);
	
	public enum Stat {
		ATTACK("Attack", new Rectangle(551, 212, 52, 22)),
		CONSTITUTION("Constitution", new Rectangle(614, 212, 52, 22)),
		MINING("Mining", new Rectangle(676, 212, 52, 22)),
		STRENGTH("Strength", new Rectangle(551, 238, 52, 22)),
		AGILITY("Agility", new Rectangle(614, 238, 52, 22)),
		SMITHING("Smithing", new Rectangle(676, 238, 52, 22)),
		DEFENCE("Defence", new Rectangle(551, 267, 52, 22)),
		HERBLORE("Herblore", new Rectangle(614, 267, 52, 22)),
		FISHING("Fishing", new Rectangle(676, 267, 52, 22)),
		RANGED("Ranged", new Rectangle(551, 294, 52, 22)),
		THIEVING("Thieving", new Rectangle(614, 294, 52, 22)),
		COOKING("Cooking", new Rectangle(676, 294, 52, 22)),
		PRAYER("Prayer", new Rectangle(551, 323, 52, 22)),
		CRAFTING("Crafting", new Rectangle(614, 323, 52, 22)),
		FIREMAKING("Firemaking", new Rectangle(676, 323, 52, 22)),
		MAGIC("Magic", new Rectangle(551, 351, 52, 22)),
		FLETCHING("Fletching", new Rectangle(614, 351, 52, 22)),
		WOODCUTTING("Woodcutting", new Rectangle(676, 351, 52, 22)),
		RUNECRAFTING("Runecrafting", new Rectangle(551, 380, 52, 22)),
		SLAYER("Slayer", new Rectangle(614, 380, 52, 22)),
		FARMING("Farming", new Rectangle(676, 380, 52, 22)),
		CONSTRUCTION("Construction", new Rectangle(551, 407, 52, 22)),
		HUNTER("Hunter", new Rectangle(614, 407, 52, 22)),
		SUMMONING("Summoning", new Rectangle(676, 407, 52, 22)),
		DUNGEONEERING("Dungeoneering", new Rectangle(551, 435, 52, 22));
		
		private final String name;
		private final Rectangle bounds;
		
		private Stat(final String name, final Rectangle bounds) {
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
		return Tabs.getOpenTab().equals(Tab.STATS);
	}
	
	public static void open() {
		Tabs.openTab(Tab.STATS);
	}

}