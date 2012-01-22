package org.runedream.api;

import java.awt.Graphics;

import org.runedream.api.util.Random;

/**
 * Abstract class to be extended by scripts.
 */
public abstract class Script {

	private final ScriptManifest manifest = super.getClass().getAnnotation(ScriptManifest.class);
	
	/**
	 * Method for optional inheritance which is called after the initialization of a script.
	 * 
	 * @return <tt>true</tt> to start the script; <tt>false</tt> will terminate initialization.
	 */
	public boolean onStart() {
		return true;
	}

	/**
	 * Looping method called repeatedly on a running script. Script actions should be located here.
	 * 
	 * @return The value to sleep before looping again.
	 */
	public abstract int loop();
	
	/**
	 * Method for optional inheritance which is called upon the termination of a script.
	 */
	public void onStop() {
		
	}
	
	/**
	 * Method for optional inheritance which is called in the overriden paintComponent(Graphics)
	 * method of the bot panel to display script paint.
	 */
	public void onRepaint(final Graphics g1) {
		
	}

	/**
	 * Convenience method to get the manifest of this script.
	 * 
	 * @return This script's ScriptManifest.
	 */
	public final ScriptManifest getManifest() {
		return manifest;
	}
	
	/**
	 * Sleeps for a given amount of milliseconds.
	 * @param millis The milliseconds to sleep.
	 */
	public static final void sleep(final int millis) {
		try {
			Thread.sleep(millis);
		} catch (final InterruptedException e) {
		}
	}
	
	/**
	 * Sleeps for a random amount of milliseconds between two given values.
	 * @param min The minimum boundary to sleep.
	 * @param max The exclusive maximum boundary to sleep.
	 */
	public static final void sleep(final int min, final int max) {
		sleep(Random.random(min, max));
	}
	
	@Override
	public boolean equals(final Object object) {
		if (object instanceof Script && object.getClass().isAnnotationPresent(ScriptManifest.class)) {
			final ScriptManifest manifest = ((Script) object).getManifest();
			return this.manifest.name().equals(manifest.name())
					&& this.manifest.version() == manifest.version()
					&& this.manifest.authors().equals(manifest.authors())
					&& this.manifest.description().equals(manifest.description())
					&& this.manifest.language().equals(manifest.language());
		}
		return false;
	}

}
