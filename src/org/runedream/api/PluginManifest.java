package org.runedream.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation for defining Plugin attributes.
 * 
 * @author Vulcan
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface PluginManifest {
	
	/**
	 * The plugin's name.
	 */
	String name();
	
	/**
	 * The plugin's version.
	 */
	double version() default 1.0;

	/**
	 * The plugin's authors.
	 */
	String[] authors();

	/**
	 * A short description of the plugin.
	 */
	String description() default "";

	/**
	 * Keyboards related to the plugin.
	 */
	String[] keywords() default { };
	
	/**
	 * The x location in the desktop to locate the plugin initially.
	 */
	int x() default 100;

	/**
	 * The y location in the desktop to locate the plugin initially.
	 */
	int y() default 100;

	/**
	 * The width to set the dimension of the plugin to initially.
	 */
	int width() default 100;

	/**
	 * The height to set the dimension of the plugin to initially.
	 */
	int height() default 100;

	/**
	 * The plugin's script repository type.
	 */
	LoadableType type() default LoadableType.FREE;

}
