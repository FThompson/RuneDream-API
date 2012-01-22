package org.runedream.api.methods;

import org.runedream.input.KeyboardHandler;

/**
 * Keyboard input methods.
 */
public class Keyboard {
	
	/**
	 * Presses (and holds) a given key.
	 * @param key The character to press.
	 */
	public static void pressKey(final char key) {
		KeyboardHandler.pressKey(key);
	}
	
	/**
	 * Presses (and holds) a given key.
	 * @param key The character to press.
	 */
	public static void pressKey(final int key) {
		KeyboardHandler.pressKey((char) key);
	}

	/**
	 * Releases a given key.
	 * @param key The character to release.
	 */
	public static void releaseKey(final char key) {
		KeyboardHandler.releaseKey(key);
	}

	/**
	 * Releases a given key.
	 * @param key The character to release.
	 */
	public static void releaseKey(final int key) {
		KeyboardHandler.releaseKey((char) key);
	}

	/**
	 * Sends a given key.
	 * @param key The character to type.
	 */
	public static void sendKey(final char key) {
		KeyboardHandler.sendKey(key);
	}
	
	public static void sendKey(final char key, final int delay) {
		KeyboardHandler.sendKey(key, delay);
	}

	/**
	 * Sends a given key.
	 * @param key The character to type.
	 */
	public static void sendKey(final int key) {
		KeyboardHandler.sendKey((char) key);
	}
	
	public static void sendKey(final int key, final int delay) {
		KeyboardHandler.sendKey((char) key, delay);
	}
	
	public static void sendKeys(final String keys, final boolean pressEnter, final int delay) {
		KeyboardHandler.sendKeys(keys, pressEnter, delay);
	}
	
	public static void sendKeys(final String keys, final boolean pressEnter, final int minDelay, final int maxDelay) {
		KeyboardHandler.sendKeys(keys, pressEnter, minDelay, maxDelay);
	}
	
	/**
	 * Sends a string.
	 * @param keys The string to type.
	 * @param pressEnter <tt>true</tt> to press enter after typing; <tt>false</tt> to not.
	 */
	public static void sendKeys(final String keys, final boolean pressEnter) {
		KeyboardHandler.sendKeys(keys, pressEnter);
	}

	/**
	 * Sends a string and presses enter.
	 * @param keys The string to type.
	 */
	public static void sendKeys(final String keys) {
		sendKeys(keys, true);
	}

	/**
	 * Sends a string instantly.
	 * @param keys The string to type.
	 * @param pressEnter <tt>true</tt> to press enter after typing; <tt>false</tt> to not.
	 */
	public static void sendKeysInstant(final String keys, final boolean pressEnter) {
		KeyboardHandler.sendKeysInstant(keys, pressEnter);
	}

	/**
	 * Sends a string instantly and presses enter.
	 * @param keys The string to type.
	 */
	public static void sendKeysInstant(final String keys) {
		sendKeysInstant(keys, true);
	}

}
