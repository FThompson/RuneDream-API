package org.runedream.api.methods;

import org.runedream.input.KeyboardHandler;

/**
 * Keyboard input methods.
 * 
 * @author Vulcan
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
	
	/**
	 * Sends a given key with a delay.
	 * @param key The character to type.
	 * @param delay The time to delay before typing.
	 */
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
	
	/**
	 * Sends a given key with a delay.
	 * @param key The character to type.
	 * @param delay The time to delay before typing.
	 */
	public static void sendKey(final int key, final int delay) {
		KeyboardHandler.sendKey((char) key, delay);
	}
	
	/**
	 * Sends a set of keys with a delay between each type.
	 * @param keys The string to type.
	 * @param pressEnter <tt>true</tt> to press Enter after typing the string; <tt>false</tt> to not.
	 * @param delay The time to delay between each key.
	 */
	public static void sendKeys(final String keys, final boolean pressEnter, final int delay) {
		KeyboardHandler.sendKeys(keys, pressEnter, delay);
	}
	
	/**
	 * Sends a set of keys with a delay between each type.
	 * @param keys The string to type.
	 * @param pressEnter <tt>true</tt> to press Enter after typing the string; <tt>false</tt> to not.
	 * @param minDelay The minimum time to delay between each key.
	 * @param maxDelay The maximum time to delay between each key.
	 */
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
