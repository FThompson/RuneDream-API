package org.runedream.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation for defining Plugin attributes.
 * 
 * @author Vulcan
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ScriptManifest {
	
	/**
	 * The script's name.
	 */
	String name();

	/**
	 * The script's version.
	 */
	double version() default 1.0;

	/**
	 * The script's authors.
	 */
	String[] authors();

	/**
	 * A short description of the script.
	 */
	String description() default "";

	/**
	 * Keyboards related to the script.
	 */
	String[] keywords() default { };

	/**
	 * The languages supported by this script. <tt>true</tt> means language is supported
	 * according to the following:<br>
	 * <br>English - 0
	 * <br>German - 1
	 * <br>French - 2
	 * <br>Portugues - 3
	 */
	boolean[] language() default { true, false, false, false };

	/**
	 * The script's script repository type.
	 */
	LoadableType type() default LoadableType.FREE;

}
