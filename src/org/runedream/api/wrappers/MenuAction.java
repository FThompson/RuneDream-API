package org.runedream.api.wrappers;

import java.awt.Rectangle;

/**
 * Convenience wrapper representing a menu action.
 * 
 * @author Vulcan
 * @see Menu
 */
public final class MenuAction {
	
	private String action = null;
	private Rectangle bounds = null;
	
	/**
	 * MenuAction constructor.
	 * @param action
	 * @param bounds
	 */
	public MenuAction(final String action, final Rectangle bounds) {
		this.action = action;
		this.bounds = bounds;
	}
	
	/**
	 * Gets the MenuAction's action.
	 * @return the MenuAction's action.
	 */
	public String getAction() {
		return action;
	}
	
	/**
	 * Gets the MenuAction's bounding rectangle.
	 * @return the MenuAction's bounding rectangle.
	 */
	public Rectangle getBounds() {
		return bounds;
	}
	
	/**
	 * Checks if the MenuAction's action contains a given string.
	 * @param action The String to check for containing.
	 * @return <tt>true</tt> if the action contains the given string; otherwise <tt>false</tt>.
	 * @see String#contains(CharSequence);
	 */
	public boolean contains(final String action) {
		return this.action.contains(action);
	}

}
