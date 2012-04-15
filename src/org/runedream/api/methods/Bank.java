package org.runedream.api.methods;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.List;

import org.runedream.api.methods.Game;
import org.runedream.api.methods.Keyboard;
import org.runedream.api.methods.Mouse;
import org.runedream.api.util.Log;
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

	public static final Rectangle ITEM_WINDOW = new Rectangle(23, 81, 474, 215);

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
		final List<Point> bank_points = ImageUtil.getPointsWithColor(Game.getImage(), new Color(242, 170, 62), 0.01);
		if (bank_points.size() > 0) {
			final Point bank = bank_points.get(Random.random(0, bank_points.size()));
			return Game.VIEWPORT.contains(bank) || isEquipmentOpen();

		}
		return false;
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
		Log.log("Not yet implemented: Bank#isEquipmentOpen()");
		return false;
	}
	
	/**
	 * 
	 * @param amount The amount of the item to deposit
	 * @param color The color of the item to deposit
	 * @param threshold The threshold to allow for color searching
	 * @return if the item was deposited or not
	 */
	public static boolean withdraw(final int amount, final Color color, final double threshold) {
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
		final List<Point> points = Game.getPointsWithColor(color, threshold);
		if (points.size() > 0) {
			final Point point = points.get(Random.random(0, points.size()));
			if (ITEM_WINDOW.contains(point)) {
				if (left_click) {
					Mouse.click(point);
					Timing.waitFor(2000, new Timing.Condition() {
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
				Timing.waitFor(2000, new Timing.Condition() {
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
	 * 
	 * @param amount The amount of the item to deposit
	 * @param color The color of the item to deposit
	 * @param threshold The threshold to allow for color searching
	 * @return if the item was deposited or not
	 */
	public static boolean deposit(final int amount, final Color color, final double threshold) {
		final int item_count = Inventory.getCount(color, threshold);
		final Inventory.Slot slot = Inventory.getSlotWithColor(color, threshold);
		final Point point = slot.getCenter();
		final boolean left_click = amount == 1;
		boolean type_amount = false;
		int offset = 0;
		if (!left_click) {
			if (amount == -1) {
				offset = 80 - (item_count > 5 ? 0 : 20);
			} else if (amount == 0) {
				offset = 105 - (item_count > 5 ? 0 : 20);
			} else if (amount == 5) {
				offset = 40;
			} else if (amount == 10) {
				offset = 60;
			}
			if (amount > -1 && amount > 10) {
				offset = 90;
				type_amount = true;
			}
		} else {
			Mouse.click(point);
			Timing.waitFor(2000, new Timing.Condition() {
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
			if (type_amount) {
				Time.sleep(1200, 1600);
				Keyboard.sendKeys(Integer.toString(amount));
			}
			Timing.waitFor(1500, new Timing.Condition() {
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
		/*if (Timing.waitFor(Random.random(500, 600), new Timing.Condition() {
			public boolean isMet() {
				return isSearchOpen();
			}
		})) {
			Keyboard.sendKeys(search);
			Time.sleep(800, 1000);
		}*/
		// TODO ^ code once isSearchOpen() is implemented
		Time.sleep(800, 1000);
		Keyboard.sendKeys(search);
		Time.sleep(800, 1000);
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
		Log.log("Not yet implemented: isWithdrawalModeNoted()");
		return false;
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

