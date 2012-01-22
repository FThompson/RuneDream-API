package org.runedream.api.methods;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;

import org.runedream.api.Script;
import org.runedream.api.methods.Game;
import org.runedream.api.methods.Keyboard;
import org.runedream.api.methods.Mouse;
import org.runedream.api.util.Log;
import org.runedream.api.util.Random;
import org.runedream.api.wrappers.ColorPattern;
import org.runedream.api.wrappers.ColorPoint;

public class Bank {
	
	public static final ColorPattern OPEN_PATTERN = new ColorPattern(new ColorPoint[]{
			new ColorPoint(64, 33, 73, 66, 50), new ColorPoint(21, 265, 85, 79, 63),
			new ColorPoint(266, 319, 89, 76, 63), new ColorPoint(489, 285, 35, 30, 23)
	});
	public static final ColorPattern EQUIP_OPEN_PATTERN = new ColorPattern(new ColorPoint[]{
			new ColorPoint(24, 14, 73, 66, 50), new ColorPoint(268, 21, 255, 152, 31),
			new ColorPoint(474, 51, 206, 205, 62), new ColorPoint(352, 322, 73, 64, 52)
	});
	public static final ColorPattern NOTED_PATTERN = new ColorPattern(new ColorPoint[]{
			new ColorPoint(223, 303, 165, 161, 123), new ColorPoint(234, 403, 46, 36, 27),
			new ColorPoint(227, 314, 118, 115, 85), new ColorPoint(232, 313, 195, 192, 165)
	});

	/**
	 * An enum of bank buttons.
	 */
	public enum BankButton {
		EQUIPMENT_STATS("Equipment Stats", new Rectangle(460, 47, 34, 33)),
		BANK("Bank", new Rectangle(458, 36, 34, 35)),
		SEARCH("Search", new Rectangle(62, 296, 34, 24)),
		WITHDRAW_MODE("Withdraw Mode", new Rectangle(212, 296, 34, 24)),
		DEPOSIT_ALL_INVENTORY("Deposit All Inventory", new Rectangle(352, 296, 34, 24)),
		DEPOSIT_ALL_EQUIPMENT("Deposit All Equipment", new Rectangle(388, 296, 34, 24)),
		DEPOSIT_ALL_BOB("Deposit All BoB", new Rectangle(424, 296, 34, 24)),
		DEPOSIT_MONEY_POUCH("Deposit Money Pouch", new Rectangle(460, 296, 34, 24)), 
		CLOSE("Close Bank", new Rectangle(481, 27, 16, 15));

		private final String name;
		private final Rectangle bounds;

		private BankButton(final String name, final Rectangle bounds) {
			this.name = name;
			this.bounds = bounds;
		}

		/**
		 * Gets the name of the bank button.
		 * @return The button's name.
		 */
		public String getName() {
			return name;
		}

		/**
		 * Gets the interactive bounds of the button.
		 * @return The button's bounds.
		 */
		public Rectangle getBounds() {
			return bounds;
		}

		/**
		 * Gets the center point of the button.
		 * @return The button's center.
		 */
		public Point getCenter() {
			return new Point((int) (bounds.x + (bounds.width / 2)),
					(int) (bounds.y + (bounds.height / 2)));
		}

		/**
		 * Gets the color array of the button's display.
		 * @return The button's colors.
		 */
		public Color[][] getColors() {
			final Color[][] colors = new Color[bounds.width][bounds.height];
			for (int x = bounds.x; x < bounds.x + bounds.width; x++) {
				for (int y = bounds.y; y < bounds.y + bounds.height; y++) {
					colors[x][y] = Game.getColors()[x][y];
				}
			}
			return colors;
		}

		/**
		 * Clicks the button.
		 */
		public void click() {
			Mouse.click(getCenter(), 6, 6);
		}

	}

	/**
	 * Checks if the bank is open.
	 * @return <tt>true</tt> if bank is open; otherwise <tt>false</tt>.
	 */
	public static boolean isOpen() {
		return OPEN_PATTERN.isPresent(Game.getImage()) || isEquipmentOpen();
	}

	/**
	 * Opens the equipment stats view.
	 * @return <tt>true</tt> if opened; otherwise <tt>false</tt>.
	 */
	public static boolean openEquipmentView() {
		BankButton.EQUIPMENT_STATS.click();
		return Timing.waitFor(Random.random(1300, 1500), new Timing.Condition() {
			public boolean isMet() {
				return isEquipmentOpen();
			}
		});
	}


	/**
	 * Opens the bank view.
	 * @return <tt>true</tt> if opened; otherwise <tt>false</tt>.
	 */
	public static boolean openBankView() {
		BankButton.BANK.click();
		return Timing.waitFor(Random.random(1300, 1500), new Timing.Condition() {
			public boolean isMet() {
				return isOpen() && !isEquipmentOpen();
			}
		});
	}
	
	/**
	 * Checks if the equipment window is open.
	 * @return <tt>true</tt> if equipment window is open; otherwise <tt>false</tt>.
	 */
	public static boolean isEquipmentOpen() {
		return EQUIP_OPEN_PATTERN.isPresent(Game.getImage());
	}

	/**
	 * Searches for a given string in the bank's search.
	 * @param search The string to search for.
	 */
	public static void search(final String search) {
		BankButton.SEARCH.click();
		/*if (Timing.waitFor(Random.random(500, 600), new Timing.Condition() {
			public boolean isMet() {
				return isSearchOpen();
			}
		})) {
			Keyboard.sendKeys(search);
			Script.sleep(800, 1000);
		}*/
		// TODO ^ code once isSearchOpen() is implemented
		Script.sleep(800, 1000);
		Keyboard.sendKeys(search);
		Script.sleep(800, 1000);
	}
	
	/**
	 * Checks if the search is open.
	 * @return <tt>true</tt> if search is open; otherwise <tt>false</tt>.
	 */
	public static boolean isSearchOpen() {
		// TODO
		Log.log("Bank#isSearchOpen() not yet implemented.");
		return false;
	}

	/**
	 * Sets the withdrawal mode.
	 * @param noted <tt>true</tt> for noted; <tt>false</tt> for unnoted.
	 * @return <tt>true</tt> if successful; otherwise <tt>false</tt>.
	 */
	public static boolean setWithdrawalMode(final boolean noted) {
		if (isWithdrawalModeNoted() != noted) {
			BankButton.WITHDRAW_MODE.click();
			Timing.waitFor(Random.random(1000, 1200), new Timing.Condition() {
				public boolean isMet() {
					return isWithdrawalModeNoted() == noted;
				}
			});
		}
		return isWithdrawalModeNoted();
	}

	/**
	 * Checks if the current withdrawal mode is set to noted.
	 * @return <tt>true</tt> if noted; otherwise <tt>false</tt>.
	 */
	public static boolean isWithdrawalModeNoted() {
		return NOTED_PATTERN.isPresent(Game.getImage());
	}

	/**
	 * Clicks the deposit all button.
	 */
	public static void depositAll() {
		BankButton.DEPOSIT_ALL_INVENTORY.click();
	}

	/**
	 * Clicks the deposit all equipment button.
	 */
	public static void depositEquipment() {
		BankButton.DEPOSIT_ALL_EQUIPMENT.click();
	}

	/**
	 * Clicks the deposit beast of burden inventory button.
	 */
	public static void depositBoB() {
		BankButton.DEPOSIT_ALL_BOB.click();
	}

	/**
	 * Clicks the deposit money pouch button.
	 */
	public static void depositMoneyPouch() {
		BankButton.DEPOSIT_MONEY_POUCH.click();
	}

}