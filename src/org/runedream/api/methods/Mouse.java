package org.runedream.api.methods;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;

import org.runedream.api.util.Random;
import org.runedream.api.util.Time;
import org.runedream.input.MouseHandler;

/**
 * Mouse input methods.
 * 
 * @author Vulcan
 */
public final class Mouse {
	
	private static int speed = 10;
	
	private Mouse() {
	}
	
	/**
	 * Moves the mouse to a given location with given randomness.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @param randX The maximum randomness in the x coordinate.
	 * @param randY The maximum randomness in the y coordinate.
	 */
	public static void move(final int x, final int y, final int randX, final int randY) {
		if (!Game.isPointValid(x, y)) {
			return;
		}
		final Point p = getLocation();
		MouseHandler.moveMouse(speed, p.x, p.y, x, y, randX, randY);
	}

	/**
	 * Moves the mouse to a given location with given randomness.
	 * @param p the Point
	 * @param randX The maximum randomness in the x coordinate.
	 * @param randY The maximum randomness in the y coordinate.
	 */
	public static void move(final Point p, final int randX, final int randY) {
		move(p.x, p.y, randX, randY);
	}

	/**
	 * Moves a point to a given location.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 */
	public static void move(final int x, final int y) {
		move(x, y, 0, 0);
	}

	/**
	 * Moves a point to a given location.
	 * @param p The point.
	 */
	public static void move(final Point p) {
		move(p.x, p.y, 0, 0);
	}
	
	/**
	 * Moves the mouse off-screen in a random direction.
	 */
	public static void moveOffScreen() {
		final Point p = getLocation();
		if (!Game.isPointValid(p)) {
			return;
		}
		final Dimension size = Game.getCanvasSize();
		int x = 0;
		int y = 0;
		if (Random.nextBoolean()) {
			x = Random.random(-1, size.width + 2);
			y = Random.nextBoolean() ? -1 : size.height + 1;
		} else {
			x = Random.nextBoolean() ? -1 : size.width + 1;
			y = Random.random(-1, size.height + 2);
		}
		MouseHandler.moveMouse(speed, p.x, p.y, x, y, 0, 0);
	}
	
	/**
	 * Moves the mouse slightly.
	 */
	public static void moveSlightly() {
		final Point p = new Point((int) (getLocation().x + (Math.random() * 50 > 25 ? 1 : -1)
				* (30 + Math.random() * 90)), (int) (getLocation().y + (Math.random() * 50 > 25 ? 1 : -1)
				* (30 + Math.random() * 90)));
		if (Game.isPointValid(p)) {
			move(p);
		} else {
			moveSlightly();
		}
	}
	
	/**
	 * Hops the mouse to a given location with given randomness.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @param randX The maximum randomness in the x coordinate.
	 * @param randY The maximum randomness in the y coordinate.
	 */
	public static void hop(final int x, final int y, final int randX, final int randY) {
		if (!Game.isPointValid(x, y)) {
			return;
		}
		MouseHandler.hopMouse(x, y, randX, randY);
	}

	/**
	 * Hops the mouse to a given location with given randomness.
	 * @param p the Point
	 * @param randX The maximum randomness in the x coordinate.
	 * @param randY The maximum randomness in the y coordinate.
	 */
	public static void hop(final Point p, final int randX, final int randY) {
		hop(p.x, p.y, randX, randY);
	}

	/**
	 * Hops a point to a given location.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 */
	public static void hop(final int x, final int y) {
		hop(x, y, 0, 0);
	}

	/**
	 * Hops a point to a given location.
	 * @param p The point.
	 */
	public static void hop(final Point p) {
		hop(p.x, p.y);
	}

	/**
	 * Clicks the mouse at a given location with given randomness.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @param randX The maximum randomness in the x coordinate.
	 * @param randY The maximum randomness in the y coordinate.
	 * @param left <tt>true</tt> for left-click; <tt>false</tt> for right-click.
	 */
	public static void click(final int x, final int y, final int randX, final int randY, final boolean left) {
		if (!Game.isPointValid(x, y)) {
			return;
		}
		if (!getLocation().equals(new Point(x, y))) {
			move(x, y, randX, randY);
		}
		press(left);
		release(left);
	}

	/**
	 * Clicks the mouse at a given location with given randomness.
	 * @param p The point.
	 * @param randX The maximum randomness in the x coordinate.
	 * @param randY The maximum randomness in the y coordinate.
	 * @param left <tt>true</tt> for left-click; <tt>false</tt> for right-click.
	 */
	public static void click(final Point p, final int randX, final int randY, final boolean left) {
		click(p.x, p.y, randX, randY, left);
	}

	/**
	 * Left-clicks the mouse at a given location with given randomness.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @param randX The maximum randomness in the x coordinate.
	 * @param randY The maximum randomness in the y coordinate.
	 */
	public static void click(final int x, final int y, final int randX, final int randY) {
		click(x, y, randX, randY, true);
	}

	/**
	 * Left-clicks the mouse at a given location with given randomness.
	 * @param p The point.
	 * @param randX The maximum randomness in the x coordinate.
	 * @param randY The maximum randomness in the y coordinate.
	 */
	public static void click(final Point p, final int randX, final int randY) {
		click(p.x, p.y, randX, randY);
	}

	/**
	 * Clicks the mouse at a given location.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @param left <tt>true</tt> for left-click; <tt>false</tt> for right-click.
	 */
	public static void click(final int x, final int y, final boolean left) {
		click(x, y, 0, 0, left);
	}

	/**
	 * Clicks the mouse at a given location.
	 * @param p The point.
	 * @param left <tt>true</tt> for left-click; <tt>false</tt> for right-click.
	 */
	public static void click(final Point p, final boolean left) {
		click(p.x, p.y, left);
	}

	/**
	 * Left-clicks the mouse at a given location.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 */
	public static void click(final int x, final int y) {
		click(x, y, true);
	}

	/**
	 * Left-clicks the mouse at a given location.
	 * @param p The point.
	 */
	public static void click(final Point p) {
		click(p.x, p.y);
	}

	/**
	 * Clicks the mouse.
	 * @param left <tt>true</tt> for left-click; <tt>false</tt> for right-click.
	 */
	public static void click(final boolean left) {
		click(getLocation(), left);
	}

	/**
	 * Left-clicks the mouse.
	 */
	public static void click() {
		click(true);
	}
	
	/**
	 * Clicks a gaussian-distribution generated random point within a rectangle.
	 * @param rect The rectangle to click within.
	 * @param left <tt>true</tt> for left-click; <tt>false</tt> for right-click.
	 */
	public static void click(final Rectangle rect, final boolean left) {
		click(Random.getRandomPoint(rect), left);
	}

	/**
	 * Clicks a gaussian-distribution generated random point within a rectangle.
	 * @param rect The rectangle to click within.
	 */
	public static void click(final Rectangle rect) {
		click(rect, true);
	}

	/**
	 * Clicks a gaussian-distribution generated random point within a polygon.
	 * @param poly The polygon to click within.
	 * @param left <tt>true</tt> for left-click; <tt>false</tt> for right-click.
	 */
	public static void click(final Polygon poly, final boolean left) {
		click(Random.getRandomPoint(poly), left);
	}

	/**
	 * Clicks a gaussian-distribution generated random point within a polygon.
	 * @param poly The polygon to click within.
	 */
	public static void click(final Polygon poly) {
		click(poly, true);
	}

	/**
	 * Moves the mouse slightly and then clicks it.
	 * @param left <tt>true</tt> for left-click; <tt>false</tt> for right-click.
	 */
	public static void clickSlightly(final boolean left) {
		moveSlightly();
		click(left);
	}

	/**
	 * Moves the mouse slightly and then clicks it.
	 */
	public static void clickSlightly() {
		clickSlightly(true);
	}
	
	/**
	 * Presses the mouse.
	 * @param left <tt>true</tt> for left button; <tt>false</tt> for right button.
	 */
	public static void press(final boolean left) {
		MouseHandler.getEventHandler().pressMouse(left ? 1 : 3);
	}

	/**
	 * Presses the mouse's left button.
	 */
	public static void press() {
		press(true);
	}

	/**
	 * Releases the mouse.
	 * @param left <tt>true</tt> for left button; <tt>false</tt> for right button.
	 */
	public static void release(final boolean left) {
		MouseHandler.getEventHandler().releaseMouse(left ? 1 : 3);
	}

	/**
	 * Releases the mouse's left button.
	 */
	public static void release() {
		release(true);
	}

	/**
	 * Drags the mouse from the current location to a given location.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 */
	public static void drag(final int x, final int y) {
		press();
		Time.sleep(150, 350);
		move(x, y);
		Time.sleep(150, 350);
		release();
	}

	/**
	 * Drags the mouse from the current location to a given location.
	 * @param p The point.
	 */
	public static void drag(final Point p) {
		drag(p.x, p.y);
	}
	
	/**
	 * Scrolls the mouse wheel up or down a given amount of units.
	 * @param units The amount of units to scroll the mouse wheel.
	 * @param up <tt>true</tt> to scroll up; <tt>false</tt> to scroll down.
	 */
	public static void scroll(final int units, final boolean up) {
		MouseHandler.getEventHandler().scrollMouse(units, up);
	}

	/**
	 * Scrolls the mouse wheel up or down 3 (standard) units.
	 * @param up <tt>true</tt> to scroll up; <tt>false</tt> to scroll down.
	 */
	public static void scroll(final boolean up) {
		scroll(3, up);
	}

	/**
	 * Scrolls the mouse wheel up 3 (standard) units.
	 */
	public static void scroll() {
		scroll(3, true);
	}
	
	/**
	 * Clicks the mouse wheel button.
	 */
	public static void clickWheel() {
		pressWheel();
		releaseWheel();
	}
	
	/**
	 * Presses the mouse wheel button.
	 */
	public static void pressWheel() {
		MouseHandler.getEventHandler().pressMouse(2);
	}
	
	/**
	 * Releases the mouse wheel button.
	 */
	public static void releaseWheel() {
		MouseHandler.getEventHandler().releaseMouse(2);
	}
	
	/**
	 * Gets the movement speed of the mouse.
	 * @return The speed.
	 */
	public static int getSpeed() {
		return speed;
	}
	
	/**
	 * Sets the movement speed of the mouse.
	 * @param speed A movement speed greater than 0.
	 */
	public static void setSpeed(final int speed) {
		Mouse.speed = speed;
	}
	
	/**
	 * Gets the location of the mouse.
	 * @return The location of the mouse.
	 */
	public static Point getLocation() {
		return MouseHandler.getEventHandler().getMouseLocation();
	}

	/**
	 * Gets the location of the last mouse press.
	 * @return The location of the last mouse press, or a Point(-1, -1) if none exists.
	 */
	public static Point getPressLocation() {
		return MouseHandler.getEventHandler().getPressLocation();
	}
	
	/**
	 * Gets the current target point of movement.
	 * @return The target point, or null if none exists.
	 */
	public static Point getTargetLocation() {
		return MouseHandler.getTarget();
	}

	/**
	 * Gets the time of the last mouse press.
	 * @return The time of the last mouse press, or -1 if none exists.
	 */
	public static long getPressTime() {
		return MouseHandler.getEventHandler().getPressTime();
	}
	
	/**
	 * Gets whether the mouse is on the game canvas or not.
	 * @return <tt>true</tt> if present; otherwise <tt>false</tt>.
	 */
	public static boolean isPresent() {
		return Game.isPointValid(getLocation());
	}
	
	/**
	 * Gets whether the mouse has a button (left, right, or wheel) pressed or not.
	 * @return <tt>true</tt> if a button is pressed; otherwise <tt>false</tt>.
	 */
	public static boolean isPressed() {
		return MouseHandler.getEventHandler().isDragging();
	}

	/**
	 * Gets a random x coordinate within a given distance.
	 * @param maxDistance The maximum distance to get an x coordinate from.
	 * @return A random x coordinate.
	 */
	public static int getRandomX(final int maxDistance) {
		final Point p = getLocation();
		if (p.x < 0 || maxDistance <= 0) {
			return -1;
		}
		if (Random.nextBoolean()) {
			return p.x - Random.random(0, p.x < maxDistance ? p.x : maxDistance);
		} else {
			final int dist = Game.getCanvasSize().width - p.x;
			return p.x + Random.random(1, dist < maxDistance && dist > 0 ? dist : maxDistance);
		}
	}

	/**
	 * Gets a random y coordinate within a given distance.
	 * @param maxDistance The maximum distance to get a y coordinate from.
	 * @return A random y coordinate.
	 */
	public static int getRandomY(final int maxDistance) {
		final Point p = getLocation();
		if (p.y < 0 || maxDistance <= 0) {
			return -1;
		}
		if (Random.nextBoolean()) {
			return p.y - Random.random(0, p.y < maxDistance ? p.y : maxDistance);
		} else {
			final int dist = Game.getCanvasSize().height - p.y;
			return p.y + Random.random(1, dist < maxDistance && dist > 0 ? dist : maxDistance);
		}
	}

	/**
	 * Gets a random point within a given distance.
	 * @param maxDistance The maximum distance to get a point from.
	 * @return A random point.
	 */
	public static Point getRandomPoint(final int maxDistance) {
		return new Point(getRandomX(maxDistance), getRandomY(maxDistance));
	}

}
