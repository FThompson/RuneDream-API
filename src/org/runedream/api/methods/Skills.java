package org.runedream.api.methods;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

import org.runedream.api.methods.Game;
import org.runedream.api.methods.ImageUtil;
import org.runedream.api.methods.Mouse;
import org.runedream.api.methods.OCR;
import org.runedream.api.methods.Tabs;
import org.runedream.api.util.Random;
import org.runedream.api.wrappers.Tab;

/**
 * Stats Interface convenience methods.
 * 
 * @author Aidden
 */
public final class Skills {
	
	private Skills() {
	}

	/**
	 * Enumeration of the game skills.
	 */
	public enum Skill {
		ATTACK("Attack", new Rectangle(550, 210, 59, 26)),
		STRENGTH("Strength", new Rectangle(550, 238, 59, 26)),
		DEFENCE("Defence", new Rectangle(550, 266, 59, 26)),
		RANGED("Ranged", new Rectangle(550, 294, 59, 26)),
		PRAYER("Prayer", new Rectangle(550, 322, 59, 26)),
		MAGIC("Magic", new Rectangle(550, 350, 59, 26)),
		RUNECRAFTING("Runecrafting", new Rectangle(550, 378, 59,26)),
		CONSTRUCTION("Construction", new Rectangle(550, 406, 59, 26)),
		DUNGEONEERING("Dungeoneering", new Rectangle(550, 434, 59, 26)),
		CONSTITUTION("Constitution", new Rectangle(612, 210, 59, 26)),
		AGILITY("Agility", new Rectangle(612, 238, 59, 26)),
		HERBLORE("Herblore", new Rectangle(612, 266, 59, 26)),
		THIEVING("Thieving", new Rectangle(612, 294, 59, 26)),
		CRAFTING("Crafting", new Rectangle(612, 322, 59, 26)),
		FLETCHING("Fletching", new Rectangle(612, 350, 59, 26)),
		SLAYER("Slayer", new Rectangle(612, 378, 59, 26)),
		HUNTER("Hunter", new Rectangle(612, 406, 59, 26)),
		MINING("Mining", new Rectangle(674, 210, 59, 26)),
		SMITHING("Smithing", new Rectangle(674, 238, 59, 26)),
		FISHING("Fishing", new Rectangle(674, 266, 59, 26)),
		COOKING("Cooking", new Rectangle(674, 294, 59, 26)),
		FIREMAKING("Firemaking", new Rectangle(674, 322, 59, 26)),
		WOODCUTTING("Woodcutting", new Rectangle(674, 350, 59, 26)),
		FARMING("Farming", new Rectangle(674, 378, 59, 26)),
		SUMMONING("Summoning", new Rectangle(674, 406, 59, 26));

		private final String skill;
		private final Rectangle bounds;
		public static final Rectangle BOUNDS = new Rectangle(545, 206, 192, 260);

		private Skill(final String skill, final Rectangle bounds) {
			this.skill = skill;
			this.bounds = bounds;
		}

		/**
		 * Gets the skill's name.
		 * @return The skill's name.
		 */
		public String getSkill() {
			return skill;
		}

		/**
		 * Gets the skill's bounding rectangle.
		 * @return The skill's bounding rectangle.
		 */
		public Rectangle getBounds() {
			return bounds;
		}

		/**
		 * Gets the skill's current level.
		 * @return Current level
		 */
		public int getLevel() {
			final Rectangle lvlBounds = new Rectangle(bounds.x + 35, bounds.y + 12, bounds.width - 35, bounds.height - 12);
			final String str = OCR.findString(lvlBounds, new Color(255, 140, 0), OCR.FontType.STAT_CHARS);
			try {
				return Integer.parseInt(str);
			} catch (final NumberFormatException e) {
				return -1;
			}
		}

		/**
		 * Get current level from experience
		 * @param exp; Current experience
		 * @return Current level
		 */
		public static int getLevel(final int exp) {
			double xp = 0.00;
			for (int i = 0; i < 120; i += 1) {
				xp += (i / 4.00) + 75.00 * Math.pow(2, i / 7.00);
				if (xp > exp) {
					return i - 1;
				}
			}
			return 0;
		}

		/**
		 * Get the current experience of the skill
		 * @return Current experience
		 */
		public int getExperience() {
			hover();
			final Rectangle expBounds = getTooltipBounds();
			expBounds.y += 16;
			expBounds.height = 20;
			String xp = OCR.findString(expBounds, new Color(245,178,65), OCR.FontType.SMALL_CHARS);
			xp = xp.replaceAll("[^\\d]", "");
			return Integer.parseInt(xp);		
		}

		/**
		 * Get the experience required to level up
		 * @return Remaining experience
		 */
		public int getExperienceToLevel() {
			hover();
			final Rectangle remBounds = getTooltipBounds();
			remBounds.y += 44;
			remBounds.height = 20;
			String rem = OCR.findString(remBounds, new Color(245,178,65), OCR.FontType.SMALL_CHARS);
			rem = rem.replaceAll("[^\\d]", "");
			return Integer.parseInt(rem);
		}

		/**
		 * Get the skills center point
		 * @return Skill's center point
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
		 * Click the skill
		 */
		public void click() {
			click(true);
		}

		/**
		 * Click the skill
		 * @param left <tt>true</tt> for left click; <tt>false</tt> for right click 
		 */
		public void click(final boolean left) {
			Mouse.click(getRandomPoint(), left);
		}

		/**
		 * Hover over the skill
		 */
		public void hover(){
			Mouse.move(getRandomPoint());
		}

		/**
		 * Get rectangle around hover menu
		 * @return Bounds of hover rectangle
		 */
		public Rectangle getTooltipBounds() {
			Color border = new Color(235,236,230);
			List<Point> points = ImageUtil.getPointsWithColor(Game.getImage(), BOUNDS, border);
			int x = 0, y = 0, w = 0, h = 0;
			for (int i = 0; i < points.size(); i++) {
				if (i == 0) {
					x = points.get(i).x;
					w = points.get(i).x;
					y = points.get(i).y;
					h = points.get(i).y;
				}

				if (points.get(i).x < x) 
					x = points.get(i).x;

				if (points.get(i).x > w) 
					w = points.get(i).x;

				if (points.get(i).y < y) 
					y = points.get(i).y;

				if (points.get(i).y > h) 
					h = points.get(i).y;
			}
			w -= x;
			h -= y;
			return new Rectangle(x, y, w, h);
		}
	}
	
	/**
	 * Check if the Stats tab is open
	 * @return <tt>true</tt> if open; otherwise <tt>false</tt>
	 */
	public static boolean isOpen() {
		return Tabs.getOpenTab().equals(Tab.STATS);
	}

	/**
	 * Open the Stats tab
	 */
	public static void open() {
		Tabs.openTab(Tab.STATS);
	}
}