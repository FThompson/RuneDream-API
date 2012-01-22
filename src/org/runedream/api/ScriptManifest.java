package org.runedream.api;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ScriptManifest {
	
	String name();
	
	double version() default 1.0;
	
	String[] authors();
	
	String description() default "";
	
	String[] keywords() default { };

	/**
	 * The languages supported by this script. <tt>true</tt> means language is supported
	 * according to the following:
	 * </br>
	 * </br>
	 * English - 0
	 * </br>
	 * German - 1
	 * </br>
	 * French - 2
	 * </br>
	 * Portugues - 3
	 * </br>
	 */
	boolean[] language() default { true, false, false, false };

}
