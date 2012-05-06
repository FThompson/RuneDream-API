package org.runedream.api.methods;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

import org.runedream.api.methods.Bank;
import org.runedream.api.methods.Game;
import org.runedream.api.methods.ImageUtil;
import org.runedream.api.methods.Keyboard;
import org.runedream.api.methods.Mouse;
import org.runedream.api.methods.OCR;
import org.runedream.api.util.Random;
import org.runedream.api.util.Time;

/**
 * Banking-related methods.
 * <br>
 * Note: Many methods contain only partial implementations, which will be completed for 1.04.
 * 
 * @author Static
 */
public class Bank {

	/**
	 * An enum of bank buttons.
	 */
	public enum BankButton {
		EQUIPMENT_STATS("Equipment Stats", new Rectangle(460, 47, 34, 33)),
		BANK("Bank", new Rectangle(458, 36, 34, 35)),
		SEARCH("Search", new Rectangle(62, 298, 34, 20)),
		WITHDRAW_MODE("Withdraw Mode", new Rectangle(212, 296, 34, 24)),
		DEPOSIT_ALL_INVENTORY("Deposit All Inventory", new Rectangle(352, 298, 34, 20)),
		DEPOSIT_ALL_EQUIPMENT("Deposit All Equipment", new Rectangle(388, 298, 34, 20)),
		DEPOSIT_ALL_BOB("Deposit All BoB", new Rectangle(424, 298, 34, 20)),
		DEPOSIT_MONEY_POUCH("Deposit Money Pouch", new Rectangle(460, 298, 34, 20)), 
		CLOSE("Close Bank", new Rectangle(481, 27, 15, 15));

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
		 * Gets a random interaction point.
		 * @return A random interaction point.
		 */
		public Point getRandomPoint() {
			return Random.getRandomPoint(bounds);
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
			Mouse.click(getRandomPoint(), 3, 3);
		}
	}

	public static final Color BANK_COLOR = new Color(242, 170, 62);
	public static final Color SEARCH_COLOR = new Color(62, 53, 41);
	public static final Rectangle LABEL_BANK = new Rectangle(192, 22, 131, 24);
	public static final Rectangle LABEL_EQUIPMENT = new Rectangle(189, 24, 137, 22);
	public static final Rectangle ITEM_WINDOW = new Rectangle(23, 81, 474, 215);

	/**
	 * Checks if the bank is open.
	 * @return <tt>true</tt> if bank is open; otherwise <tt>false</tt>.
	 * @author Static
	 */
	public static boolean isOpen() {
		final String open = OCR.findString(LABEL_BANK, BANK_COLOR, OCR.FontType.UP_CHARS_EX);
		return open != null && open.equals("Bank of RuneScape");
	}

	/**
	 * Opens the equipment stats view.
	 * @return <tt>true</tt> if opened; otherwise <tt>false</tt>.
	 */
	public static boolean openEquipmentView() {
		BankButton.EQUIPMENT_STATS.click();
		return Time.waitFor(Random.random(1300, 1500), new Time.Condition() {
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
		return Time.waitFor(Random.random(1300, 1500), new Time.Condition() {
			public boolean isMet() {
				return isOpen() && !isEquipmentOpen();
			}
		});
	}

	/**
	 * Checks if the equipment window is open.
	 * @return <tt>true</tt> if equipment window is open; otherwise <tt>false</tt>.
	 * @author Aidden
	 */
	public static boolean isEquipmentOpen() {
		final String str = OCR.findString(LABEL_EQUIPMENT, BANK_COLOR, OCR.FontType.UP_CHARS_EX);
		return str != null && str.contains("Bank of RuneScape");
	}

	/**
	 * Withdraws an item with a given color.
	 * @param amount The amount of the item to deposit
	 * @param color The color of the item to deposit
	 * @param tolerance The tolerance to allow for color searching
	 * @return if the item was deposited or not
	 */
	public static boolean withdraw(final int amount, final Color color, final int tolerance) {
		final int pre_count = Inventory.getCount();
		final boolean left_click = amount == 1;
		boolean type_amount = false;
		int offset = 0;
		if (!left_click) {
			if (amount == -1) {
				offset = 80;
			} else if (amount == 0) {
				offset = 105;
			} else if (amount == 5) {
				offset = 40;
			} else if (amount == 10) {
				offset = 60;
			}
			if (amount > -1 && amount > 10) {
				offset = 90;
				type_amount = true;
			}
		}
		final List<Point> points = Game.getPointsWithColor(color, tolerance);
		if (points.size() > 0) {
			final Point point = points.get(Random.random(0, points.size()));
			if (ITEM_WINDOW.contains(point)) {
				if (left_click) {
					Mouse.click(point);
					Time.waitFor(2000, new Time.Condition() {
						public boolean isMet() {
							return Inventory.getCount() > pre_count;
						}
					});
					return Inventory.getCount() > pre_count;
				}
				Mouse.click(point, false);
				Time.sleep(400, 500);
				Mouse.click(Mouse.getLocation().x, Mouse.getLocation().y + offset);
				if (type_amount) {
					Time.sleep(1200, 1600);
					Keyboard.sendKeys(Integer.toString(amount));
				}
				Time.waitFor(2000, new Time.Condition() {
					public boolean isMet() {
						return Inventory.getCount() > pre_count;
					}
				});
				return Inventory.getCount() > pre_count;
			}
		}
		return false;
	}

	/**
	 * Deposits an item of with a given color.
	 * @param amount The amount of the item to deposit
	 * @param color The color of the item to deposit
	 * @param tolerance The tolerance to allow for color searching
	 * @return if the item was deposited or not
	 */
	public static boolean deposit(final int amount, final Color color, final int tolerance) {
		final int count = Inventory.getCount(color, tolerance);
		final Inventory.Slot slot = Inventory.getSlotWithColor(color, tolerance);
		final Point point = slot.getCenter();
		final boolean leftClick = amount == 1;
		boolean typeAmount = false;
		int offset = 0;
		if (!leftClick) {
			if (amount == -1) {
				offset = 80 - (count > 5 ? 0 : 20);
			} else if (amount == 0) {
				offset = 105 - (count > 5 ? 0 : 20);
			} else if (amount == 5) {
				offset = 40;
			} else if (amount == 10) {
				offset = 60;
			}
			if (amount > -1 && amount > 10) {
				offset = 90;
				typeAmount = true;
			}
		} else {
			Mouse.click(point);
			Time.waitFor(2000, new Time.Condition() {
				public boolean isMet() {
					return slot.isEmpty();
				}
			});
			return slot.isEmpty();
		}
		if (!slot.isEmpty()) {
			Mouse.click(point, false);
			Time.sleep(50, 125);
			Mouse.click((int) point.getX(), (int) point.getY() + (point.getY() + 50 < 460 ? offset : 463));
			if (typeAmount) {
				Time.sleep(1200, 1600);
				Keyboard.sendKeys(Integer.toString(amount));
			}
			Time.waitFor(1500, new Time.Condition() {
				public boolean isMet() {
					return slot.isEmpty();
				}
			});
			return slot.isEmpty();
		}
		return false;
	}

	/**
	 * Searches for a given string in the bank's search.
	 * @param search The string to search for.
	 */
	public static void search(final String search) {
		BankButton.SEARCH.click();
		if (Time.waitFor(Random.random(500, 600), new Time.Condition() {
			public boolean isMet() {
				return isSearchOpen();
			}
		})) {
			Time.sleep(800, 1000);
			Keyboard.sendKeys(search);
			Time.sleep(800, 1000);
		}		
	}

	/**
	 * Checks if the search is open.
	 * @return <tt>true</tt> if search is open; otherwise <tt>false</tt>.
	 * @author Aidden
	 */
	public static boolean isSearchOpen() {
		List<Point> points = ImageUtil.getPointsWithColor(Game.getImage(),
				Bank.BankButton.SEARCH.getBounds(), SEARCH_COLOR, 2);
		return points.size() == 0;
	}

	/**
	 * Sets the withdrawal mode.
	 * @param noted <tt>true</tt> for noted; <tt>false</tt> for unnoted.
	 * @return <tt>true</tt> if successful; otherwise <tt>false</tt>.
	 */
	public static boolean setWithdrawalMode(final boolean noted) {
		if (isWithdrawalModeNoted() != noted) {
			BankButton.WITHDRAW_MODE.click();
			Time.waitFor(Random.random(1000, 1200), new Time.Condition() {
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
	 * @author Aidden
	 */
	public static boolean isWithdrawalModeNoted() {
		List<Point> points = ImageUtil.getPointsWithColor(Game.getImage(),
				Bank.BankButton.WITHDRAW_MODE.getBounds(), SEARCH_COLOR, 2);
		return points.size() == 0;
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