package org.runedream.api.methods;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import javax.imageio.ImageIO;

import org.runedream.api.methods.Environment;
import org.runedream.api.methods.Game;
import org.runedream.api.util.Log;

/**
 * Optical Character Recognition (OCR) methods.
 * 
 * @author battlegaurd
 */
public class OCR {

	private static final Font[][] ALL_LETTERS = new Font[FontType.values().length][62];
	
	private static class Letter {

		private int x0;
		private int x1;
		private char letter;		

		public Letter(final char letter, final int x0, final int x1) {					
			this.letter = letter;
			this.x0 = x0;
			this.x1 = x1;
		}	
	}

	private static class Font {

		private Point[] goodPts;
		private Point[] badPts;
		private char letter;
		private Rectangle letBox;

		public Font(final ArrayList<Point> goodpoints, final ArrayList<Point> badpoints,
				final char letter, final Rectangle letBox) {
			goodPts = new Point[goodpoints.size()];
			goodPts = goodpoints.toArray(goodPts);
			badPts = new Point[badpoints.size()];
			badPts = badpoints.toArray(goodPts);
			this.letBox = letBox;
			this.letter = letter;
		}		
	}

	/**
	 * Enumeration of the fonts available to use.
	 */
	public static enum FontType {
		BIG_CHARS("BigChars"),
		NPC_CHARS("NPCChars"),
		FRIEND_CHARS("FriendChars"),
		LOGIN_CHARS("LoginChars"),
		SMALL_CHARS("SmallChars"),
		STAT_CHARS("StatChars"),
		UP_CHARS("UpChars"),
		UP_CHARS_EX("UpCharsEx");

		private String name;

		private FontType(final String name) {
			this.name = name;
		}

		/**
		 * Gets the name of this FontType.
		 * @return The FontType's name.
		 */
		public String getName() {
			return name;
		}
	}

	static {
		for (int i = 0; i < ALL_LETTERS.length; i++) {
			ALL_LETTERS[i] = grabFontSet(ALL_LETTERS[i], FontType.values()[i].getName());
		}
	}

	/**
	 * NOTE THE MORE VARIABLES YOU DEFINE THE FASTER THIS WILL BE AND THE SMALLER THE RECTANGLE THE FASTER IT WILL BE
	 * SO TRY NOT TO RUN THIS WITH NO FONT SPECIFIED AND NO COLOR OF THE TEXT SPECIFIED BECAUSE IT DRASTICALLY INCREASES THE AMOUNT
	 * OF OPERATIONS IT MUST PERFORM. I AM WORKING ON A HELPER SCRIPT THAT WILL MAKE THIS A LOT EASIER -battlegaurd
	 */
	
	/**
	 * Finds text within a rectangle of the specified font.
	 * <br>
	 * To optimize performance, minimize the bounds of the rectangle, define the color, and define the font.
	 * @param fontC The color of the text inside of the rectangle; or null to search for any color.
	 * @param rec The rectangle to search within.
	 * @param curfont The font to find a string of.
	 * @return The text found within the rectangle.
	 */
	public static String findString(final Rectangle rec, final Color fontC, final FontType font) {		
		return findString(fontC, rec, ALL_LETTERS[font.ordinal()], false);
	}

	/**
	 * Finds text within a rectangle of any supported font.
	 * <br>
	 * To optimize performance, minimize the bounds of the rectangle and define the color.
	 * @param rec The rectangle to search within.
	 * @param fontC The color of the text inside of the rectangle; or null to search for any color.
	 * @return The text found within the rectangle.
	 */
	public static String findString(final Rectangle rec, final Color fontC) {
		return findString(fontC, rec, null, false);
	}

	/**
	 * Finds text within a rectangle of the specified font.
	 * <br>
	 * To optimize performance, minimize the bounds of the rectangle, define the color, and define the font.
	 * @param rec The rectangle to search within.
	 * @param fontC The color of the text inside of the rectangle; or null to search for any color.
	 * @param curfont The font to find a string of.
	 * @param multiColorText <tt>true</tt> if the text should be detected in multiple colors; otherwise <tt>false</tt>.
	 * @return The text found within the rectangle.
	 */
	public static String findString(final Rectangle rec, final Color fontC, final FontType font, final boolean multiColorText) {		
		return findString(fontC, rec, ALL_LETTERS[font.ordinal()], multiColorText);
	}

	/**
	 * Finds text within a rectangle of any supported font.
	 * <br>
	 * To optimize performance, minimize the bounds of the rectangle and define the color.
	 * @param rec The rectangle to search within.
	 * @param fontC The color of the text inside of the rectangle; or null to search for any color.
	 * @param multiColorText <tt>true</tt> if the text should be detected in multiple colors; otherwise <tt>false</tt>.
	 * @return The text found within the rectangle.
	 */
	public static String findString(final Rectangle rec, final Color fontC, final boolean multiColorText) {
		return findString(fontC, rec, null, multiColorText);
	}

	/**
	 * Finds text within a rectangle.
	 * <br>
	 * To optimize performance, minimize the bounds of the rectangle, define the color, and define the font.
	 * @param fontC The color of the text inside of the rectangle; or null to search for any color.
	 * @param rec The rectangle to search within.
	 * @param font The font type to search for; or null to search for all font types
	 * @param multiColorText <tt>true</tt> if the text should be detected in multiple colors; otherwise <tt>false</tt>.
	 * @return The text found within the rectangle.
	 */
	private static String findString(Color fontC, final Rectangle rec, final Font[] font, final boolean multiColorText) {
		ArrayList<Letter> nums = new ArrayList<Letter>();
		Font[] foundFont = font;		
		for (int y = rec.y; y < rec.y + rec.height; y++) {
			M1: for (int x = rec.x; x < rec.x + rec.width; x++) {
				Color c = Game.getColorAt(x, y);
				if (fontC != null && !fontC.equals(c)) {
					continue;	
				}
				for (int i = 0; i < ALL_LETTERS.length; i++) {
					Font[] curFont = ALL_LETTERS[i];
					if (foundFont != null) {
						curFont = foundFont;
					}
					M2: for (int j = 0; j < curFont.length; j++) {
						final Rectangle loc = new Rectangle(x - curFont[j].goodPts[0].x,
								y - curFont[j].goodPts[0].y, curFont[j].letBox.width, curFont[j].letBox.height);
						if (!rec.contains(loc)) {
							continue;
						}
						for (int k = 0; k < curFont[j].goodPts.length; k++) {
							if (!checkColor(Game.getColorAt(loc.x + curFont[j].goodPts[k].x,
									loc.y + curFont[j].goodPts[k].y), c, 40)) {
								continue M2;
							}
						}
						for (int k = 0; k < curFont[j].badPts.length; k++) {
							if (checkColor(Game.getColorAt(loc.x + curFont[j].badPts[k].x,
									loc.y + curFont[j].badPts[k].y), c, 40)) {
								continue M2;
							}
						}
						nums.add(new Letter(curFont[j].letter, loc.x, loc.x + curFont[j].letBox.width));
						foundFont = curFont;
						if (!multiColorText) {
							fontC = c;
						}
						continue M1;
					}
					if (foundFont != null) {
						break;
					}
				}		
			}
		}
		return sortLetters(nums);
	}
	
	/**
	 * Gets the game uptext (text in the top left corner).
	 * @return The game uptext.
	 */
	public static String getUpText() {
		return UpTextOCR.getUpText();
	}

	/**
	 * Load all images for the current font and store the font information to the Font array
	 * @param fontset : font array that information will be stored
	 * @param fontname : name of the current font
	 * @return : a filled array of font information
	 */
	private static Font[] grabFontSet(final Font[] fontset, final String fontname) {
		int cnt = 0;
		final String base = Environment.getStorageDirectoryPath() + File.separator
				+ "Cache" + File.separator + "Fonts" + File.separator + fontname;
		for (int i = 48; i < 123; i++) {
			try {
				if ((i >= 58 && i < 65) || (i >= 91 && i < 97)) {
					continue;
				}
				final BufferedImage img = ImageIO.read(new File(base, i + ".bmp"));
				fontset[cnt++] = parseBMP(img, (char) i);
			} catch (final IOException e) {
				Log.log("Failed to read bitmap " + i + " on font " + fontname);
			}
		}
		return fontset;
	}

	/**
	 * Parses a letter font from a bitmap image.
	 * @param img The image to parse.
	 * @param letter The character to parse.
	 * @return The letter font.
	 */
	private static Font parseBMP(final BufferedImage img, final char letter) {
		final ArrayList<Point> goodPts = new ArrayList<Point>();
		final ArrayList<Point> badPts = new ArrayList<Point>();
		for (int y = 0; y < img.getHeight(); y++) {
			for (int x = 0; x < img.getWidth(); x++) {
				final Color c2 = new Color(img.getRGB(x, y));
				if (c2.equals(Color.WHITE)) {
					goodPts.add(new Point(x, y));			    				
				} else {
					badPts.add(new Point(x, y)); 
				}
			}
		}
		return new Font(goodPts, badPts, letter, new Rectangle(img.getWidth(), img.getHeight()));
	}

	private static boolean checkColor(final Color c, final Color c2, final int tol) {
		return checkColor(c.getRed(), c2.getRed()) + checkColor(c.getGreen(), c2.getGreen())
				+ checkColor(c.getBlue(), c2.getBlue()) < tol;
	}

	private static int checkColor(final int rgb, final int val) {
		return Math.abs(rgb - val);
	}

	// WE COULD MAYBE REMOVE THIS IF WE ONLY SEARCH ALONG THE MIDDLE OF THE BOX FOR COLORS. THIS WOULD ALSO ALLOW US TO NOT HAVE TO DO ANYWHERE CLOSE
	// TO THE AMOUNT OF TRAVERSALS AND WOULD REMOVE A LOT OF EXTRA RUNTIME
	// THE ONLY DOWN SIDE WOULD BE THE PERSON MUST MAKE SURE HE HAS HIS BOX CENTERED IN A WAY THAT THE 1 Y LINE THEY SPECIFY HITS ALL THE LETTERS
	// THIS COULD ALSO BRING IN THE PROBLEM OF THE PERSON NOT KNOWING WHERE THE TEXT IS ON SCREEN AND WOULD MAKE HIS JOB A LOT MORE TROUBLESOME
	private static String sortLetters(final ArrayList<Letter> letters) {
		String text = "";
		Letter oldLetter = null;
		while (!letters.isEmpty()) {
			Letter curLetter = new Letter('X', 800, 800);
			for (int i = 0; i < letters.size(); i++) {
				if (letters.get(i).x0 < curLetter.x0) {
					curLetter = letters.get(i);
				}				
			}
			if (oldLetter != null && curLetter.x0 - oldLetter.x1 > 1){
				text += " ";
			}
			oldLetter = curLetter;			
			text += curLetter.letter;
			letters.remove(curLetter);
		}
		return text;
	}

	/**
	 * Inner class used to read uptext.
	 */
	private static class UpTextOCR {

		private static class Character {
			
			private char character;
			private int height;
			private int width;
			private int[] fontPointsX;
			private int[] fontPointsY;
			private int[] shadowPointsX;
			private int[] shadowPointsY;

			private Character(final char character, final int[] x, final int[] y) {
				this.character = character;
				this.fontPointsX = x;
				this.fontPointsY = y;
				final ArrayList<Point> shadowPoints = new ArrayList<Point>();
				main: for (int i = 0; i < fontPointsX.length; i++) {
					for (int j = 0; j < fontPointsX.length; j++) {
						if (fontPointsX[i] + 1 == fontPointsX[j]
								&& fontPointsY[i] + 1 == fontPointsY[j]) {
							continue main;
						}
					}
					shadowPoints.add(new Point(fontPointsX[i] + 1, fontPointsY[i] + 1));
					if (fontPointsX[i] + 1 > width) {
						width = fontPointsX[i] + 1;
					}
					if (fontPointsY[i] + 1 > height) {
						height = fontPointsY[i] + 1;
					}
				}
				shadowPointsX = new int[shadowPoints.size()];
				shadowPointsY = new int[shadowPoints.size()];
				for (int i = 0; i < shadowPointsX.length; i++) {
					shadowPointsX[i] = shadowPoints.get(i).x;
					shadowPointsY[i] = shadowPoints.get(i).y;
				}
			}

			private char getCharacter() {
				return character;
			}

			private int getHeight() {
				return height;
			}

			private int getWidth() {
				return width;
			}
		}
		
		private static Character[] optionCharacters = {
				new Character('a', new int[] { 1, 2, 3, 4, 1, 2, 3, 4, 0, 4, 0, 4,
						1, 2, 3, 4 }, new int[] { 0, 0, 0, 1, 2, 2, 2, 2, 3, 3, 4,
						4, 5, 5, 5, 5 }),
				new Character('b', new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3,
						1, 2, 3, 4, 4, 4, 4 }, new int[] { 0, 1, 2, 3, 4, 5, 6, 7,
						8, 3, 3, 3, 8, 8, 8, 4, 5, 6, 7 }),
				new Character('c',
						new int[] { 1, 2, 3, 0, 0, 0, 0, 4, 4, 1, 2, 3 },
						new int[] { 0, 0, 0, 1, 2, 3, 4, 1, 4, 5, 5, 5 }),
				new Character('d', new int[] { 4, 4, 4, 4, 4, 4, 4, 4, 4, 1, 2, 3,
						0, 0, 0, 0, 1, 2, 3 }, new int[] { 0, 1, 2, 3, 4, 5, 6, 7,
						8, 3, 3, 3, 4, 5, 6, 7, 8, 8, 8 }),
				new Character('e', new int[] { 1, 2, 3, 0, 4, 0, 1, 2, 3, 4, 0, 0,
						4, 1, 2, 3 }, new int[] { 0, 0, 0, 1, 1, 2, 2, 2, 2, 2, 3,
						4, 4, 5, 5, 5 }),
				new Character('f', new int[] { 2, 3, 1, 1, 0, 1, 2, 3, 1, 1, 1, 1,
						1 }, new int[] { 0, 0, 1, 2, 3, 3, 3, 3, 4, 5, 6, 7, 8 }),
				new Character('g', new int[] { 1, 2, 3, 4, 0, 0, 0, 0, 4, 4, 4, 4,
						1, 2, 3, 4, 4, 3, 2, 1 }, new int[] { 0, 0, 0, 0, 1, 2, 3,
						4, 1, 2, 3, 4, 5, 5, 5, 5, 6, 7, 7, 7 }),
				new Character('h', new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3,
						4, 4, 4, 4, 4 }, new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 3,
						3, 3, 4, 5, 6, 7, 8 }),
				new Character('i', new int[] { 0, 0, 0, 0, 0, 0, 0 }, new int[] {
						0, 3, 4, 5, 6, 7, 8 }),
				new Character('j', new int[] { 0, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2 },
						new int[] { 10, 3, 10, 0, 3, 4, 5, 6, 7, 8, 9 }),
				new Character('k', new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 2,
						3, 3, 4, 4 }, new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 6, 5,
						6, 4, 7, 3, 8 }),
				new Character('l', new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0 },
						new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8 }),
				new Character('m', new int[] { 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 4, 4,
						4, 4, 5, 6, 7, 8, 8, 8, 8, 8 }, new int[] { 0, 1, 2, 3, 4,
						5, 0, 0, 0, 1, 2, 3, 4, 5, 0, 0, 0, 1, 2, 3, 4, 5 }),
				new Character('n', new int[] { 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 4, 4,
						4, 4 }, new int[] { 0, 1, 2, 3, 4, 5, 0, 0, 0, 1, 2, 3, 4,
						5 }),
				new Character('o', new int[] { 1, 2, 3, 0, 4, 0, 4, 0, 4, 0, 4, 1,
						2, 3 }, new int[] { 0, 0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5, 5,
						5 }),
				new Character('p', new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 1,
						2, 3, 4, 4, 4, 4 }, new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 0,
						0, 0, 5, 5, 5, 1, 2, 3, 4 }),
				new Character('q', new int[] { 1, 2, 3, 4, 0, 4, 0, 4, 0, 4, 0, 4,
						1, 2, 3, 4, 4, 4 }, new int[] { 0, 0, 0, 0, 1, 1, 2, 2, 3,
						3, 4, 4, 5, 5, 5, 5, 6, 7 }),
				new Character('r', new int[] { 0, 0, 0, 0, 0, 0, 1, 2, 3 },
						new int[] { 0, 1, 2, 3, 4, 5, 1, 0, 0 }),
				new Character('s',
						new int[] { 1, 2, 3, 0, 0, 1, 2, 3, 3, 0, 1, 2 },
						new int[] { 0, 0, 0, 1, 2, 2, 3, 3, 4, 5, 5, 5 }),
				new Character('t',
						new int[] { 1, 1, 0, 1, 2, 3, 1, 1, 1, 1, 2, 3 },
						new int[] { 0, 1, 2, 2, 2, 2, 3, 4, 5, 6, 7, 7 }),
				new Character('u', new int[] { 0, 0, 0, 0, 0, 4, 4, 4, 4, 4, 1, 2,
						3, 4 }, new int[] { 0, 1, 2, 3, 4, 0, 1, 2, 3, 4, 5, 5, 5,
						5 }),
				new Character('v', new int[] { 0, 4, 0, 4, 1, 3, 1, 3, 2, 2 },
						new int[] { 0, 0, 1, 1, 2, 2, 3, 3, 4, 4 }),
				new Character('w', new int[] { 0, 3, 6, 0, 3, 6, 0, 2, 4, 6, 0, 2,
						4, 6, 1, 5, 1, 5 }, new int[] { 0, 0, 0, 1, 1, 1, 2, 2, 2,
						2, 3, 3, 3, 3, 4, 4, 5, 5 }),
				new Character('x', new int[] { 0, 4, 1, 3, 2, 2, 1, 3, 0, 4 },
						new int[] { 0, 0, 1, 1, 2, 3, 4, 4, 5, 5 }),
				new Character('y',
						new int[] { 0, 4, 1, 1, 1, 3, 3, 3, 2, 2, 2, 1 },
						new int[] { 0, 0, 1, 2, 3, 1, 2, 3, 4, 5, 6, 7 }),
				new Character('z',
						new int[] { 0, 1, 2, 3, 3, 2, 1, 0, 0, 1, 2, 3 },
						new int[] { 0, 0, 0, 0, 1, 2, 3, 4, 5, 5, 5, 5 }),

				new Character('A', new int[] { 2, 3, 2, 3, 1, 4, 1, 4, 1, 4, 0, 1,
						2, 3, 4, 5, 0, 5, 0, 5 }, new int[] { 0, 0, 1, 1, 2, 2, 3,
						3, 4, 4, 5, 5, 5, 5, 5, 5, 6, 6, 7, 7 }),
				new Character('B', new int[] { 0, 1, 2, 3, 0, 4, 0, 4, 0, 1, 2, 3,
						4, 0, 5, 0, 5, 0, 5, 0, 1, 2, 3, 4 }, new int[] { 0, 0, 0,
						0, 1, 1, 2, 2, 3, 3, 3, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 7, 7,
						7 }),
				new Character('C', new int[] { 2, 3, 4, 5, 1, 6, 0, 0, 0, 0, 1, 6,
						2, 3, 4, 5 }, new int[] { 0, 0, 0, 0, 1, 1, 2, 3, 4, 5, 6,
						6, 7, 7, 7, 7 }),
				new Character('D', new int[] { 0, 1, 2, 3, 4, 0, 5, 0, 6, 0, 6, 0,
						6, 0, 6, 0, 5, 0, 1, 2, 3, 4 }, new int[] { 0, 0, 0, 0, 0,
						1, 1, 2, 2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 7, 7, 7 }),
				new Character('E', new int[] { 0, 1, 2, 3, 4, 0, 0, 0, 1, 2, 3, 4,
						0, 0, 0, 0, 1, 2, 3, 4 }, new int[] { 0, 0, 0, 0, 0, 1, 2,
						3, 3, 3, 3, 3, 4, 5, 6, 7, 7, 7, 7, 7 }),
				new Character('F', new int[] { 0, 1, 2, 3, 4, 0, 0, 0, 1, 2, 3, 0,
						0, 0, 0 }, new int[] { 0, 0, 0, 0, 0, 1, 2, 3, 3, 3, 3, 4,
						5, 6, 7 }),
				new Character('G', new int[] { 2, 3, 4, 5, 1, 6, 0, 0, 0, 0, 1, 2,
						3, 4, 5, 6, 6, 6, 5, 4 }, new int[] { 0, 0, 0, 0, 1, 1, 2,
						3, 4, 5, 6, 7, 7, 7, 7, 6, 5, 4, 4, 4 }),
				new Character('H', new int[] { 0, 5, 0, 5, 0, 5, 0, 1, 2, 3, 4, 5,
						0, 5, 0, 5, 0, 5, 0, 5 }, new int[] { 0, 0, 1, 1, 2, 2, 3,
						3, 3, 3, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7 }),
				new Character('I',
						new int[] { 0, 1, 2, 1, 1, 1, 1, 1, 1, 0, 1, 2 },
						new int[] { 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 7, 7 }),
				new Character('J',
						new int[] { 1, 2, 3, 3, 3, 3, 3, 3, 3, 0, 1, 2 },
						new int[] { 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 7, 7 }),
				new Character('K', new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 2, 3,
						3, 4, 4, 5, 5 }, new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 4, 3,
						4, 2, 5, 1, 6, 0, 7 }),
				new Character('L',
						new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4 },
						new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 7, 7, 7, 7 }),
				new Character('M', new int[] { 0, 1, 5, 6, 0, 1, 5, 6, 0, 2, 4, 6,
						0, 2, 4, 6, 0, 3, 6, 0, 3, 6, 0, 6, 0, 6 }, new int[] { 0,
						0, 0, 0, 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 5, 5,
						5, 6, 6, 7, 7 }),
				new Character('N', new int[] { 0, 1, 5, 0, 1, 5, 0, 2, 5, 0, 2, 5,
						0, 3, 5, 0, 3, 5, 0, 4, 5, 0, 4, 5 }, new int[] { 0, 0, 0,
						1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 5, 6, 6, 6, 7, 7,
						7 }),
				new Character('O', new int[] { 2, 3, 4, 1, 5, 0, 6, 0, 6, 0, 6, 0,
						6, 1, 5, 2, 3, 4 }, new int[] { 0, 0, 0, 1, 1, 2, 2, 3, 3,
						4, 4, 5, 5, 6, 6, 7, 7, 7 }),
				new Character('P', new int[] { 0, 1, 2, 3, 0, 4, 0, 4, 0, 4, 0, 1,
						2, 3, 0, 0, 0 }, new int[] { 0, 0, 0, 0, 1, 1, 2, 2, 3, 3,
						4, 4, 4, 4, 5, 6, 7 }),
				new Character('Q', new int[] { 2, 3, 4, 1, 5, 0, 6, 0, 6, 0, 6, 0,
						6, 1, 5, 2, 3, 4, 4, 5, 6 }, new int[] { 0, 0, 0, 1, 1, 2,
						2, 3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 7, 8, 9, 9 }),
				new Character('R', new int[] { 0, 1, 2, 3, 0, 4, 0, 4, 0, 4, 0, 1,
						2, 3, 0, 0, 0, 3, 4, 5 }, new int[] { 0, 0, 0, 0, 1, 1, 2,
						2, 3, 3, 4, 4, 4, 4, 5, 6, 7, 5, 6, 7 }),
				new Character('S', new int[] { 1, 2, 3, 4, 0, 5, 0, 1, 2, 3, 4, 5,
						5, 0, 1, 2, 3, 4 }, new int[] { 0, 0, 0, 0, 1, 1, 2, 3, 3,
						4, 4, 5, 6, 6, 7, 7, 7, 7 }),
				new Character('T', new int[] { 0, 1, 2, 3, 4, 5, 6, 3, 3, 3, 3, 3,
						3, 3 }, new int[] { 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6,
						7 }),
				new Character('U', new int[] { 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5,
						5, 5, 5, 5, 5, 5 }, new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 7,
						7, 7, 6, 5, 4, 3, 2, 1, 0 }),
				new Character('V', new int[] { 0, 5, 0, 5, 0, 5, 1, 4, 1, 4, 1, 4,
						2, 3, 2, 3 }, new int[] { 0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5,
						5, 6, 6, 7, 7 }),
				new Character('W', new int[] { 0, 4, 8, 0, 4, 8, 1, 3, 5, 7, 1, 3,
						5, 7, 1, 3, 5, 7, 1, 3, 5, 7, 2, 6, 2, 6 }, new int[] { 0,
						0, 0, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 4, 4, 4, 4, 5, 5, 5,
						5, 6, 6, 7, 7 }),
				new Character('X', new int[] { 0, 5, 0, 5, 1, 4, 2, 3, 2, 3, 1, 4,
						0, 5, 0, 5 }, new int[] { 0, 0, 1, 1, 2, 2, 3, 3, 4, 4, 5,
						5, 6, 6, 7, 7 }),
				new Character('Y', new int[] { 0, 6, 1, 5, 2, 4, 3, 3, 3, 3, 3 },
						new int[] { 0, 0, 1, 1, 2, 2, 3, 4, 5, 6, 7 }),
				new Character('Z', new int[] { 0, 1, 2, 3, 4, 5, 5, 4, 3, 2, 1, 0,
						0, 1, 2, 3, 4, 5 }, new int[] { 0, 0, 0, 0, 0, 0, 1, 2, 3,
						4, 5, 6, 7, 7, 7, 7, 7, 7 }),
				new Character('0', new int[] { 1, 2, 3, 0, 4, 0, 4, 0, 4, 0, 4, 0,
						4, 0, 4, 1, 2, 3 }, new int[] { 0, 0, 0, 1, 1, 2, 2, 3, 3,
						4, 4, 5, 5, 6, 6, 7, 7, 7 }),
				new Character('1', new int[] { 2, 0, 1, 2, 2, 2, 2, 2, 2, 0, 1, 2,
						3, 4 }, new int[] { 0, 1, 1, 1, 2, 3, 4, 5, 6, 7, 7, 7, 7,
						7 }),
				new Character('2', new int[] { 1, 2, 3, 0, 4, 4, 3, 2, 1, 0, 0, 1,
						2, 3, 4 }, new int[] { 0, 0, 0, 1, 1, 2, 3, 4, 5, 6, 7, 7,
						7, 7, 7 }),
				new Character('3', new int[] { 1, 2, 3, 0, 4, 4, 3, 2, 4, 4, 4, 0,
						1, 2, 3 }, new int[] { 0, 0, 0, 1, 1, 2, 3, 3, 4, 5, 6, 6,
						7, 7, 7 }),
				new Character('4', new int[] { 4, 4, 3, 4, 2, 4, 1, 4, 0, 0, 1, 2,
						3, 4, 5, 4, 4 }, new int[] { 0, 1, 1, 2, 2, 3, 3, 4, 4, 5,
						5, 5, 5, 5, 5, 6, 7 }),
				new Character('5', new int[] { 0, 1, 2, 3, 4, 0, 0, 0, 1, 2, 3, 4,
						4, 0, 4, 1, 2, 3 }, new int[] { 0, 0, 0, 0, 0, 1, 2, 3, 3,
						3, 3, 4, 5, 6, 6, 7, 7, 7 }),
				new Character('6', new int[] { 2, 3, 1, 0, 0, 1, 2, 3, 0, 4, 0, 4,
						0, 4, 1, 2, 3 }, new int[] { 0, 0, 1, 2, 3, 3, 3, 3, 4, 4,
						5, 5, 6, 6, 7, 7, 7 }),
				new Character('7',
						new int[] { 0, 1, 2, 3, 4, 4, 3, 3, 2, 2, 1, 1 },
						new int[] { 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7 }),
				new Character('8', new int[] { 1, 2, 3, 0, 4, 0, 4, 1, 2, 3, 0, 4,
						0, 4, 0, 4, 1, 2, 3 }, new int[] { 0, 0, 0, 1, 1, 2, 2, 3,
						3, 3, 4, 4, 5, 5, 6, 6, 7, 7, 7 }),
				new Character('9', new int[] { 1, 2, 3, 0, 4, 0, 4, 0, 4, 1, 2, 3,
						4, 4, 3, 2, 1 }, new int[] { 0, 0, 0, 1, 1, 2, 2, 3, 3, 4,
						4, 4, 4, 5, 6, 7, 7 }),
				new Character('-', new int[] { 0, 1, 2 }, new int[] { 0, 0, 0 }),
				new Character('/', new int[] { 0, 0, 1, 1, 2, 2, 3, 3, 4, 4 },
						new int[] { 9, 8, 7, 6, 5, 4, 3, 2, 1, 0 }),
				new Character('(', new int[] { 2, 1, 1, 0, 0, 0, 0, 0, 1, 1, 2 },
						new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }),
				new Character(')', new int[] { 0, 1, 1, 2, 2, 2, 2, 2, 1, 1, 0 },
						new int[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 }) };

		static {
			Arrays.sort(optionCharacters, new Comparator<Character>() {
				public int compare(Character arg0, Character arg1) {
					if (arg0.width < arg1.width) {
						return 1;
					}
					if (arg0.width > arg1.width) {
						return -1;
					}
					if (arg0.fontPointsX.length < arg1.fontPointsX.length) {
						return 1;
					}
					if (arg0.fontPointsX.length > arg1.fontPointsX.length) {
						return -1;
					}
					return 0;
				}
			});
		}

		private static String getUpText() {
			final StringBuilder builder = new StringBuilder();
			final BufferedImage gameImage = Game.getImage();
			final int leftUpperX = 5;
			final int leftUpperY = 5;
			final int width = Game.VIEWPORT.width - 10;
			final int height = 20;
			final boolean[][] ocrImage = new boolean[width][height];
			for (int x = leftUpperX; x < leftUpperX + width; x++) {
				for (int y = leftUpperY; y < leftUpperY + height; y++) {
					final int color = gameImage.getRGB(x, y) & 0xFFFFFF;
					if (getDistanceSquare(color, 14474460) < 12500 // WHITE
							|| getDistanceSquare(color, 56540) < 12500 // CYAN
							|| getDistanceSquare(color, 14474240) < 12500 // YELLOW
							|| getDistanceSquare(color, 15106620) < 12500 // ORANGE
					) {
						ocrImage[x - leftUpperX][y - leftUpperY] = true;
					} else {
						ocrImage[x - leftUpperX][y - leftUpperY] = false;
					}

				}
			}
			// First, find a capital letter in the area ((0,0),(50,height))
			int posX = 0;
			x: for (int x = 0; x < 50; x++) {
				for (int y = 0; y < height; y++) {
					c: for (Character c : optionCharacters) {
						if (c.getCharacter() < 'A' || c.getCharacter() > 'Z') {
							continue c;
						}
						if (y + c.getHeight() >= 20) {
							continue c;
						}
						if (x + c.getWidth() >= 25) {
							continue c;
						}
						for (int j = 0; j < c.fontPointsX.length; j++) {
							if (!ocrImage[x + c.fontPointsX[j]][y
									+ c.fontPointsY[j]]) {
								continue c;
							}
						}
						for (int j = 0; j < c.shadowPointsX.length; j++) {
							if (ocrImage[x + c.shadowPointsX[j]][y
									+ c.shadowPointsY[j]]) {
								continue c;
							}
						}
						builder.append(c.getCharacter());
						posX = x + c.getWidth();
						break x;
					}
				}
			}
			// now read the rest of the characters
			int lastPosX = 0;
			for (; posX < Game.VIEWPORT.width - 10; posX++) {
				y: for (int y = 0; y < height; y++) {
					c: for (Character c : optionCharacters) {
						if (y + c.getHeight() >= height) {
							continue c;
						}
						if (posX + c.getWidth() >= Game.VIEWPORT.width - 10) {
							continue c;
						}
						for (int j = 0; j < c.fontPointsX.length; j++) {
							if (!ocrImage[posX + c.fontPointsX[j]][y
									+ c.fontPointsY[j]]) {
								continue c;
							}
						}
						for (int j = 0; j < c.shadowPointsX.length; j++) {
							if (ocrImage[posX + c.shadowPointsX[j]][y
									+ c.shadowPointsY[j]]) {
								continue c;
							}
						}
						if (lastPosX != 0 && posX - lastPosX > 5) {
							builder.append(' ');
						}
						builder.append(c.getCharacter());
						posX += c.getWidth() - 1;
						lastPosX = posX + 1;
						break y;
					}
				}
			}
			return builder.toString();
		}

		private static int getDistanceSquare(int c1, int c2) {
			int rd = ((c1 >> 16) & 0xFF) - ((c2 >> 16) & 0xFF);
			int gd = ((c1 >> 8) & 0xFF) - ((c2 >> 8) & 0xFF);
			int bd = (c1 & 0xFF) - (c2 & 0xFF);
			return rd * rd + gd * gd + bd * bd;
		}
	}
}

