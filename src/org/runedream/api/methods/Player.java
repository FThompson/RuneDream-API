package org.runedream.api.methods;

import org.runedream.api.methods.ColorUtil;
import org.runedream.api.methods.Game;
import org.runedream.api.methods.OCR;

import java.awt.*;

/**
 * Convenience methods for reading the logged in player's information.
 * 
 * @author Static
 */
public class Player {
	
	public static final Color COLOR_POISONED = new Color(10, 126, 35);
	public static final Rectangle RECTANGLE_LIFE = new Rectangle(722, 23, 23, 23);
	public static final Rectangle RECTANGLE_PRAYER = new Rectangle(737, 62, 23, 23);
	public static final Rectangle RECTANGLE_ENERGY = new Rectangle(738, 101, 23, 23);
	public static final Rectangle RECTANGLE_SUMMONING = new Rectangle(722, 136, 23, 23);

	/**
	 * Gets the current player's life points.
	 * @return The current player's life points; or -1 if failed to read.
	 */
	public static int getLifePoints() {
		final String string = OCR.findString(RECTANGLE_LIFE, Color.GREEN, OCR.FontType.STAT_CHARS);
		return string != null ? Integer.parseInt(string) : -1;
	}

	/**
	 * Gets the current player's prayer points.
	 * @return The current player's prayer points; or -1 if failed to read.
	 */
	public static int getPrayerPoints() {
		final String string = OCR.findString(RECTANGLE_PRAYER, Color.GREEN, OCR.FontType.STAT_CHARS);
		return string != null ? Integer.parseInt(string) : -1;
	}

	/**
	 * Gets the current player's energy points.
	 * @return The current player's energy points; or -1 if failed to read.
	 */
	public static int getEnergy() {
		final String string = OCR.findString(RECTANGLE_ENERGY, Color.GREEN, OCR.FontType.STAT_CHARS);
		return string != null ? Integer.parseInt(string) : -1;
	}

	/**
	 * Gets the current player's summoning points.
	 * @return The current player's summoning points; or -1 if failed to read.
	 */
	public static int getSummoningPoints() {
		final String string = OCR.findString(RECTANGLE_SUMMONING, Color.GREEN, OCR.FontType.STAT_CHARS);
		return string != null ? Integer.parseInt(string) : -1;
	}

	/**
	 * Checks if the player is poisoned (the life points bubble is green).
	 * @return <tt>true</tt> if poisoned; otherwise <tt>false</tt>.
	 */
	public boolean isPoisoned() {
		for (int y = 19; y <= 42; y++) {
			if (ColorUtil.getDistance(COLOR_POISONED, Game.getColorAt(706, y)) <= 3) {
				return true;
			}
		}
		return false;
	}

}