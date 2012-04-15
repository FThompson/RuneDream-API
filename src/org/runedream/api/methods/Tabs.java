package org.runedream.api.methods;

import org.runedream.api.wrappers.Tab;

/**
 * Methods for interacting with game tabs.
 * 
 * @author Vulcan
 */
public class Tabs {
	
	/**
	 * Opens a given tab.
	 * @param tab The tab to open.
	 */
	public static void openTab(final Tab tab) {
		Mouse.click(tab.getCenter(), 6, 6);
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
