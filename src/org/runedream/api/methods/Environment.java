package org.runedream.api.methods;

import java.io.File;
import java.io.IOException;

import javax.swing.JInternalFrame;
import javax.swing.SwingUtilities;

import org.runedream.Configuration;
import org.runedream.RuneDream;
import org.runedream.api.util.Log;
import org.runedream.util.ScreenshotUtil;

/**
 * Bot environment utility methods.
 */
public class Environment {
	
	public static final int INPUT_MOUSE = 1;
	public static final int INPUT_KEYBOARD = 2;
	
	/**
	 * Gets the storage directory file.
	 * @return The storage directory file.
	 */
	public static File getStorageDirectory() {
		return new File(getStorageDirectoryPath());
	}
	
	/**
	 * Gets the storage directory file path.
	 * @return The storage directory file path.
	 */
	public static String getStorageDirectoryPath() {
		return Configuration.Paths.getStorageDirectory();
	}
	
	/**
	 * Gets the currently set input flags.
	 * @return The input flags.
	 */
	public static int getInputFlags() {
		return RuneDream.getUI().getEventHandler().getInputFlags();
	}
	
	/**
	 * Sets the input flags.
	 * @param inputFlags The input flags to set. 0 for disabled input,
	 * INPUT_MOUSE for mouse input only, INPUT_KEYBOARD for keyboard input only,
	 * or (INPUT_MOUSE | INPUT_KEYBOARD) for all input enabled.
	 */
	public static void setInputFlags(final int inputFlags) {
		RuneDream.getUI().getEventHandler().setInputFlags(inputFlags);
	}
	
	/**
	 * Saves a screenshot of the game image with any script/debug paint.
	 */
	public static void saveScreenshot() {
		try {
			final File file = ScreenshotUtil.takeAndSave();
			if (file != null) {
				Log.log("Saved screenshot to " + file.getPath());
			}
		} catch (final IOException e) {
			Log.log("Failed to save screenshot.");
		}
	}
	
	/**
	 * Gets the user's forum username.
	 * @return The user's username.
	 */
	public static String getUsername() {
		return RuneDream.getUsername();
	}
	
	/**
	 * Gets the user's forum user id.
	 * @return The user's id.
	 */
	public static int getUserID() {
		return RuneDream.getUserID();
	}
	
	/**
	 * Checks if the user has a rank of sponsor+ or not.
	 * @return <tt>true</tt> if sponsor+; otherwise <tt>false</tt>.
	 */
	public static boolean hasSponsorPermissions() {
		return RuneDream.hasSponsorPermissions();
	}
	
	/**
	 * Checks if RuneDream is running in lite mode or not.
	 * @return <tt>true</tt> if lite mode; otherwise <tt>false</tt>.
	 */
	public static boolean isLiteMode() {
		return RuneDream.isLiteMode();
	}
	
	/**
	 * Adds an internal frame to the desktop display. Intended for use with plugins only.
	 * @param frame The JInternalFrame to add.
	 */
	public static void addInternalFrame(final JInternalFrame frame) {
		if (!isLiteMode()) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					RuneDream.getDesktop().add(frame);
				}
			});
		}
	}

}
