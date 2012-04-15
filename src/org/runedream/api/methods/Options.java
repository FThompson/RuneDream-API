package org.runedream.api.methods;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.runedream.api.wrappers.Tab;

/**
 * Options tab convenience methods.
 * 
 * @author Amplex
 */
public class Options {

	public static boolean isOpen() {
		return Tabs.getOpenTab().equals(Tab.OPTIONS);
	}
	
	public static void open() {
		Tabs.openTab(Tab.OPTIONS);
	}
	
	public enum ChatMode {
		ALL("View all", new Rectangle(3, 481, 55, 21)),
		GAME("View Game", new Rectangle(60, 481, 55, 21)),
		PUBLIC("View Public", new Rectangle(117, 481, 55, 21)),
		PRIVATE("View Private", new Rectangle(174, 481, 55, 21)), 
		FRIENDS("View Friends Chats", new Rectangle(231, 481, 55, 21)),
		CLAN("View Clan", new Rectangle(288, 481, 55, 21)),
		TRADE("View Trade", new Rectangle(345, 481, 55, 21)),
		ASSIST("View Assist", new Rectangle(402, 481, 55, 21));
		
		public enum ChatSetting {
			ON(new Color(0, 255, 0)),
			OFF(new Color(255, 0, 0)),
			FRIENDS(new Color(255, 255, 0)),
			HIDE(null); // TODO
			
			private Color color = null;
			
			private ChatSetting(final Color color) {
				this.color = color;
			}
			
			public Color getColor() {
				return color;
			}
		}

		private final String name;
		private final Rectangle bounds;

		private ChatMode(final String name, final Rectangle bounds) {
			this.name = name;
			this.bounds = bounds;
		}	
		
		public String getName() {
			return name;
		}

		public Rectangle getBounds() {
			return bounds;
		}

		public Point getCenter() {
			return new Point((int) (bounds.x + (bounds.width / 2)), (int) (bounds.y + (bounds.height / 2)));
		}

		public Color[] getColors() {
			final List<Color> colors = new LinkedList<Color>();
			for (int x = bounds.x; x < bounds.x + bounds.width; x++) {
				for (int y = bounds.y; y < bounds.y + bounds.height; y++) {
					final Point p = new Point(x, y);
					if (Game.isPointValid(p)) {
						colors.add(Game.getColorAt(p));
					}
				}
			}
			return colors.toArray(new Color[colors.size()]);
		}

		public void click() {
			Mouse.click(getCenter(), 6, 6);
		}
		
		public ChatSetting getSetting() {
			final List<Color> button_colors = Arrays.asList(getColors());
			for (final ChatSetting setting : ChatSetting.values()) {
				if (button_colors.contains(setting)) {
					return setting;
				}
			}
			return ChatSetting.OFF;
		}

	}
	
	public enum Settings {
		GRAPHICS("Graphics Settings", new Rectangle(566, 246, 39, 39)), 
		AUDIO("Audio Settings", new Rectangle(622, 246, 39, 39)), 
		MOUSE_BUTTONS("Toggle Mouse Buttons", new Rectangle(678, 246, 39, 39)),
		PROFANITY_FILTER("Toggle Profanity Filter", new Rectangle(566, 328, 39, 39)),
		CHAT_EFFECTS("Toggle Chat Effects", new Rectangle(622, 328, 39, 39)),
		CHAT_SETUP("Open Chat Setup", new Rectangle(678, 328, 39, 39)), 
		ACCEPT_AID("Toggle Accept Aid", new Rectangle(566, 380, 39, 39)),
		HOUSE_OPTIONS("Open House Options", new Rectangle(622, 380, 39, 39)),
		ADVENTURE_OPTIONS("Adventure log settings", new Rectangle(678, 380, 39, 39)),
		RIGHT_CLICK_REPORTING("Toggle Right Click Reporting", new Rectangle(700, 436, 15, 15));
		
		private final String name;
		private final Rectangle bounds;

		private Settings(final String name, final Rectangle bounds) {
			this.name = name;
			this.bounds = bounds;
		}
		
		public String getName() {
			return name;
		}

		public Rectangle getBounds() {
			return bounds;
		}

		public Point getCenter() {
			return new Point((int) (bounds.x + (bounds.width / 2)), (int) (bounds.y + (bounds.height / 2)));
		}
		
		public void click() {
			Mouse.click(getCenter(), 6, 6);
		}
		
	}

}
