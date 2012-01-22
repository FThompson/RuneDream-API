package org.runedream.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface PluginManifest {
	
	String name();
	
	double version() default 1.0;
	
	String[] authors();
	
	String description() default "";
	
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

}
