package org.runedream.api.methods;

import java.awt.event.KeyEvent;

import org.runedream.api.Script;

/**
 * Game camera control methods.
 */
public class Camera {
	
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
	
	private static void rotate(final int key, final int millis) {
		Keyboard.pressKey(key);
		final long start = System.currentTimeMillis();
		while (start + millis > System.currentTimeMillis()) {
			Script.sleep(50);
		}
		Keyboard.releaseKey(key);
	}

}
