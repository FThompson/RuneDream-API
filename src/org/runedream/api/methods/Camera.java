package org.runedream.api.methods;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.runedream.api.util.Random;
import org.runedream.api.util.Time;

/**
 * Game camera control methods.
 * 
 * @author Vulcan
 */
public final class Camera {

	public static final int NORTH = 0;
	public static final int EAST = 90;
	public static final int SOUTH = 180;
	public static final int WEST = 270;
	public static final Rectangle INNER_COMPASS_BOUNDS = new Rectangle(529, 10, 29, 29);

	private Camera() {
	}

	/**
	 * Rotates the camera right for a given time.
	 * @param millis The time in milliseconds to rotate the camera.
	 */
	public static void rotateRight(final int millis) {
		rotate(KeyEvent.VK_RIGHT, millis);
	}

	/**
	 * Rotates the camera left for a given time.
	 * @param millis The time in milliseconds to rotate the camera.
	 */
	public static void rotateLeft(final int millis) {
		rotate(KeyEvent.VK_LEFT, millis);
	}

	/**
	 * Pitches the camera up for a given time.
	 * @param millis The time in milliseconds to pitch the camera.
	 */
	public static void pitchUp(final int millis) {
		rotate(KeyEvent.VK_UP, millis);
	}

	/**
	 * Pitches the camera down for a given time.
	 * @param millis The time in milliseconds to pitch the camera.
	 */
	public static void pitchDown(final int millis) {
		rotate(KeyEvent.VK_DOWN, millis);
	}

	private static synchronized void rotate(final int key, final int millis) {
		Keyboard.pressKey(key);
		final long start = System.currentTimeMillis();
		while (start + millis > System.currentTimeMillis()) {
			Time.sleep(50);
		}
		Keyboard.releaseKey(key);
	}

	/**
	 * Clicks the compass.
	 */
	public static void clickCompass() {
		Mouse.click(Random.getRandomPoint(INNER_COMPASS_BOUNDS));
	}

	public static synchronized boolean setCompassAngle(final int degrees) {
		final int startAngle = getAngleTo(degrees);
		if (startAngle < 5 && startAngle > -5) {
			return true;
		}
		final boolean left = getAngleTo(degrees) > 0;
		final long start = System.currentTimeMillis();
		final int timeout = Random.random(2500, 3000);
		final int key = !left ? KeyEvent.VK_LEFT : KeyEvent.VK_RIGHT;
		Keyboard.pressKey(key);
		while (start + timeout > System.currentTimeMillis()) {
			final int angle = getAngleTo(degrees);
			if (angle < 5 && angle > -5) {
				Keyboard.releaseKey(key);
				return true;
			}
		}
		Keyboard.releaseKey(key);
		return false;
	}

	/**
	 * Gets the angle between the compass and the given degrees.
	 * @param degrees The degrees to get the angle to.
	 * @return The angle to the given degrees.
	 */
	public static int getAngleTo(final int degrees) {
		int cur = getCompassAngle();
		if (cur < degrees) {
			cur += 360;
		}
		int diff = cur - degrees;
		if (diff > 180) {
			diff -= 360;
		}
		return diff;
	}

	/**
	 * Gets the current angle of the compass relative to true north, rotating clockwise. Examples:
	 * <br> - North is 0.
	 * <br> - East is 90.
	 * <br> - South is 180.
	 * <br> - West is 270.
	 * @param game The game client image.
	 * @return The angle of the compass, or -1 if unable to read compass.
	 * @author HelBorn, Vulcan
	 */
	public static int getCompassAngle() {
		final double radius = (INNER_COMPASS_BOUNDS.width / 2.0) - 1.0;
		final double centerX = INNER_COMPASS_BOUNDS.x + radius;
		final double centerY = INNER_COMPASS_BOUNDS.y + radius;
		final List<Integer> angles = getCompassBlackPoints(centerX + 1, centerY + 1, radius);
		if (angles == null) {
			return -1;
		}
		// Fixes the bug where the "N" reaches the 360 | 0 degrees border and
		// the points are read in the opposite order, which results in
		// inaccurate readings of +/- 10 when the compass is at ~90 degrees.
		double angle = angles.get(0);
		if ((angles.get(1) - angle) > 100) {
			angle = angles.get(1) + 10.0;
		} else if (angles.get(0) < 20 && angles.get(1) > 300) {
			angle = angles.get(1) - 10.0;
		} else {
			angle += 10.0;
		}
		angle *= Math.PI / 180;
		angle += Math.PI;
		final double curX = (centerX + radius * Math.cos(-angle));
		final double curY = (centerY + radius * Math.sin(-angle));
		return (int) ((Math.atan2(curY - (centerY - radius), curX - centerX) * 2) * 180 / Math.PI);
	}

	/**
	 * Gets the two black pixels from the "N" letter that touch the outer edge
	 * of the compass. The center between those two points is a very accurate
	 * reading of the compass's pointing direction.
	 * @param game The game client image.
	 * @param x The x coordinate of the compass.
	 * @param y The y coordinate of the compass.
	 * @param radius The compass's radius.
	 * @return  A list containing the two points, or null if not found.
	 * @author HelBorn
	 */
	private static List<Integer> getCompassBlackPoints(final double x, final double y, final double radius) {
		final BufferedImage game = Game.getImage();
		final List<Integer> angles = new ArrayList<Integer>();
		circleLoop: for (double i = 0; i < 360; i++) {
			final double angle = i * Math.PI / 180;
			final int curX = (int) (x + radius * Math.cos(angle));
			final int curY = (int) (y + radius * Math.sin(angle));
			for (final int a : angles) {
				if ((i - a) < 8) {
					continue circleLoop;
				}
			}
			final int rgb = game.getRGB(curX, curY);
			if (ColorUtil.getDistance(rgb, Color.BLACK.getRGB()) < 2) {
				angles.add((int) i);
				if (angles.size() == 2) {
					return angles;
				}
			}
		}
		return null;
	}
}
