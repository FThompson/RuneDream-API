package org.runedream.api.methods;

import java.awt.event.KeyEvent;

import org.runedream.api.util.Time;
import org.runedream.api.wrappers.Tab;

/**
 * Methods for interacting with game tabs.
 * 
 * @author Vulcan
 */
public final class Tabs {
	
	private Tabs() {
	}
	
	/**
	 * Opens a given tab.
	 * @param tab The tab to open.
	 * @param fkey <tt>true</tt> to use function key shortcut if possible.
	 */
	public static boolean openTab(final Tab tab, final boolean fkey) {
		if (!tab.isOpen()) {
			if (fkey && tab.hasFunctionKey()) {
				Keyboard.sendKey(KeyEvent.CHAR_UNDEFINED, tab.getFunctionKey());
			} else {
				Mouse.click(tab.getRandomPoint());
			}
			Time.sleep(60, 100);
		}
		return tab.isOpen();
	}

	/**
	 * Opens a given tab.
	 * @param tab The tab to open.
	 */
	public static boolean openTab(final Tab tab) {
		return openTab(tab, true);
	}
	
	/**
	 * Gets the open tab.
	 * @return The open tab.
	 */
	public static Tab getOpenTab() {
		for (final Tab tab : Tab.values()) {
			if (tab.isOpen()) {
				return tab;
			}
		}
		return null;
	}

}
