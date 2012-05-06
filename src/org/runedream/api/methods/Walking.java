package org.runedream.api.methods;

import org.runedream.api.methods.Game;
import org.runedream.api.wrappers.DTM;

import java.awt.Point;

/**
 * Methods related to game walking.
 * 
 * @author Dang
 */
public final class Walking {

	public static final Point MINIMAP_CENTER = new Point(627, 85);
	public static final DTM FLAG = DTM.fromString("254_10_0_5", "254_10_0_-5_1_5", "255_34_0_1_-6_5", "226_10_0_1_0_5");

	private Walking() {
	}
	
	/**
	 * Checks if a destination is present on the minimap.
	 * @return <tt>true</tt> if the destination flag was found; otherwise <tt>false</tt>.
	 */
	public static boolean hasDestination() {
		return getFlagLocation() != null;
	}

	/**
	 * Gets the location of the destination flag.
	 * @return The location of the destination flag; or null if not found.
	 */
	public static Point getFlagLocation() {
		return FLAG.getPoint(Game.MINIMAP);
	}

	/**
	 * Gets the distance to the destination flag, if found.
	 * @return The distance to the destination flag, if found.
	 */
	public static int getDistanceToDestination() {
		final Point flagPoint = getFlagLocation();
		return flagPoint != null ? Calculations.getDistanceBetween(MINIMAP_CENTER, flagPoint) : -1;
	}

}