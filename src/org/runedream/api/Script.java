package org.runedream.api;

import java.awt.Color;
import java.awt.Graphics;
import java.util.EventListener;
import java.util.logging.Logger;

import org.runedream.api.util.Log;
import org.runedream.internal.script.ScriptManager;

/**
 * Abstract class to be extended by scripts.
 */
public abstract class Script implements EventListener {

	private final ScriptManifest manifest;
	private final Logger log;
	
	public Script() {
		manifest = super.getClass().getAnnotation(ScriptManifest.class);
		log = Logger.getLogger(super.getClass().getName());
	}
	
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
	 * Logs a message.
	 * @param message The object message to log.
	 */
	public final void log(final Object message) {
		Log.log(log, message);
	}
	
	/**
	 * Logs a message in a given color.
	 * @param message The object message to log.
	 * @param color The color to log the message in.
	 */
	public final void log(final Object message, final Color color) {
		Log.log(log, message, color);
	}
	
	/**
	 * Stops the script, calling onFinish().
	 */
	public static final void stop() {
		ScriptManager.stopScript();
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
