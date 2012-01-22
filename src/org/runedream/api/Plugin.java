package org.runedream.api;

import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import org.runedream.internal.plugin.PluginManager;

/**
 * Abstract class to be extended by plugins.
 */
public abstract class Plugin extends JInternalFrame implements Runnable {

	private static final long serialVersionUID = 1L;
	private final PluginManifest manifest = super.getClass().getAnnotation(PluginManifest.class);
	
	public Plugin() {
		setTitle(manifest.name() + " v" + manifest.version());
		if (manifest.description() != null) {
			setToolTipText(manifest.description());
		}
		setLocation(manifest.x(), manifest.y());
		setSize(manifest.width(), manifest.height());
		setClosable(true);
		setIconifiable(true);
		addInternalFrameListener(new InternalFrameAdapter() {
			public void internalFrameClosed(final InternalFrameEvent e) {
				PluginManager.removePlugin(Plugin.this);
				onClose();
			}
		});
	}
	
	/**
	 * Method for optional inheritance which is called after the initialization of a plugin.
	 * Note: This method will be called before the plugin is added to the desktop and set visible.
	 * 
	 * @return <tt>true</tt> to start the plugin; <tt>false</tt> will terminate initialization.
	 */
	public boolean onStart() {
		return true;
	}

	/**
	 * Main method of plugin that is called if onStart() performs successfully.
	 */
	@Override
	public abstract void run();

	/**
	 * Method for inheritance of plugins which is called upon the termination of a plugin.
	 */
	public void onClose() {
		
	}

	/**
	 * Convenience method to get the manifest of this plugin.
	 * 
	 * @return This plugin's PluginManifest.
	 */
	public final PluginManifest getManifest() {
		return manifest;
	}
	
	@Override
	public boolean equals(final Object object) {
		if (object instanceof Plugin && object.getClass().isAnnotationPresent(PluginManifest.class)) {
			final PluginManifest manifest = ((Plugin) object).getManifest();
			return this.manifest.name().equals(manifest.name())
					&& this.manifest.version() == manifest.version()
					&& this.manifest.authors().equals(manifest.authors())
					&& this.manifest.description().equals(manifest.description());
		}
		return false;
	}
}
