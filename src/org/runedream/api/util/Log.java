package org.runedream.api.util;

import java.awt.Color;
import java.awt.Font;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.runedream.RuneDream;

/**
 * Methods for logging.
 * 
 * @author Vulcan
 */
public class Log {
	
	public static final Logger DEFAULT = Logger.getLogger(RuneDream.class.getName());
	
	/**
	 * Logs a message.
	 * @param log The logger to log to.
	 * @param message The message to log.
	 * @param level The level to log at.
	 * @param params Any other log params.
	 */
	public static void log(final Logger log, final Object message, final Level level, final Object[] params) {
		if (log != null) {
			log.setParent(DEFAULT);
			log.log(level, message.toString(), params);
		} else {
			DEFAULT.log(level, message.toString(), params);
		}
	}

	/**
	 * Logs a message.
	 * @param log The logger to log to.
	 * @param message The message to log.
	 * @param level The level to log at.
	 * @param color The color to log in.
	 * @param font The font to log in.
	 */
	public static void log(final Logger log, final Object message, final Level level, final Color color, final Font font) {
		log(log, message, level, new Object[]{ color, font });
	}

	/**
	 * Logs a message.
	 * @param log The logger to log to.
	 * @param message The message to log.
	 * @param level The level to log at.
	 * @param color The color to log in.
	 */
	public static void log(final Logger log, final Object message, final Level level, final Color color) {
		log(log, message, level, new Object[]{ color });
	}

	/**
	 * Logs a message.
	 * @param log The logger to log to.
	 * @param message The message to log.
	 * @param level The level to log at.
	 * @param font The font to log in.
	 */
	public static void log(final Logger log, final Object message, final Level level, final Font font) {
		log(log, message, level, new Object[]{ font });
	}

	/**
	 * Logs a message.
	 * @param log The logger to log to.
	 * @param message The message to log.
	 * @param params Any other log params.
	 */
	public static void log(final Logger log, final Object message, final Object[] params) {
		log(log, message, Level.INFO, params);
	}

	/**
	 * Logs a message.
	 * @param log The logger to log to.
	 * @param message The message to log.
	 * @param color The color to log in.
	 * @param font The font to log in.
	 */
	public static void log(final Logger log, final Object message, final Color color, final Font font) {
		log(log, message, Level.INFO, new Object[]{ color, font });
	}

	/**
	 * Logs a message.
	 * @param log The logger to log to.
	 * @param message The message to log.
	 * @param color The color to log in.
	 */
	public static void log(final Logger log, final Object message, final Color color) {
		log(log, message, Level.INFO, new Object[]{ color });
	}

	/**
	 * Logs a message.
	 * @param log The logger to log to.
	 * @param message The message to log.
	 * @param font The font to log in.
	 */
	public static void log(final Logger log, final Object message, final Font font) {
		log(log, message, Level.INFO, new Object[]{ font });
	}

	/**
	 * Logs a message.
	 * @param log The logger to log to.
	 * @param message The message to log.
	 * @param level The level to log at.
	 */
	public static void log(final Logger log, final Object message, final Level level) {
		log(log, message, level, new Object[0]);
	}

	/**
	 * Logs a message.
	 * @param log The logger to log to.
	 * @param message The message to log.
	 */
	public static void log(final Logger log, final Object message) {
		log(log, message, Level.INFO, new Object[0]);
	}

	/**
	 * Logs a message.
	 * @param message The message to log.
	 * @param level The level to log at.
	 * @param params Any other log params.
	 */
	public static void log(final Object message, final Level level, final Object[] params) {
		log(null, message, level, params);
	}

	/**
	 * Logs a message.
	 * @param message The message to log.
	 * @param level The level to log at.
	 * @param color The color to log in.
	 * @param font The font to log in.
	 */
	public static void log(final Object message, final Level level, final Color color, final Font font) {
		log(message, level, new Object[]{ color, font });
	}

	/**
	 * Logs a message.
	 * @param message The message to log.
	 * @param level The level to log at.
	 * @param color The color to log in.
	 */
	public static void log(final Object message, final Level level, final Color color) {
		log(message, level, new Object[]{ color });
	}

	/**
	 * Logs a message.
	 * @param message The message to log.
	 * @param level The level to log at.
	 * @param font The font to log in.
	 */
	public static void log(final Object message, final Level level, final Font font) {
		log(message, level, new Object[]{ font });
	}

	/**
	 * Logs a message.
	 * @param message The message to log.
	 * @param params Any other log params.
	 */
	public static void log(final Object message, final Object[] params) {
		log(message, Level.INFO, params);
	}

	/**
	 * Logs a message.
	 * @param message The message to log.
	 * @param color The color to log in.
	 * @param font The font to log in.
	 */
	public static void log(final Object message, final Color color, final Font font) {
		log(message, Level.INFO, new Object[]{ color, font });
	}

	/**
	 * Logs a message.
	 * @param message The message to log.
	 * @param color The color to log in.
	 */
	public static void log(final Object message, final Color color) {
		log(message, Level.INFO, new Object[]{ color });
	}

	/**
	 * Logs a message.
	 * @param message The message to log.
	 * @param font The font to log in.
	 */
	public static void log(final Object message, final Font font) {
		log(message, Level.INFO, new Object[]{ font });
	}

	/**
	 * Logs a message.
	 * @param message The message to log.
	 * @param level The level to log at.
	 */
	public static void log(final Object message, final Level level) {
		log(null, message, level, new Object[0]);
	}

	/**
	 * Logs a message.
	 * @param message The message to log.
	 */
	public static void log(final Object message) {
		log(message, Level.INFO, new Object[0]);
	}
	
	static {
		 DEFAULT.setLevel(Level.ALL);
	}

}