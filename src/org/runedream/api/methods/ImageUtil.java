package org.runedream.api.methods;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

/**
 * Image-related utility methods.
 */
public class ImageUtil {

	/**
	 * Gets an image's array of RGB values.
	 * @param image The image to get the RGB values of.
	 * @return An array of RGB values.
	 */
	public static int[] getPixels(final BufferedImage image) {
		final int w = image.getWidth();
		final int h = image.getHeight();
		final int[] rgbs = new int[w * h];
		return image.getRGB(0, 0, w, h, rgbs, 0, w);
	}
	
	/**
	 * Gets the color at a given x-y coordinate on the given image.
	 * @param image The image.
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @return The color at the given coordinates.
	 */
	public static Color getColorAt(final BufferedImage image, final int x, final int y) {
		if (x >= 0 && y >= 0 && x < image.getWidth() && y < image.getHeight()) {
			return new Color(image.getRGB(x, y));
		}
		return Color.BLACK;
	}
	
	/**
	 * Gets the color at a given point on the given image.
	 * @param image The image.
	 * @param p The point.
	 * @return The color at the given point.
	 */
	public static Color getColorAt(final BufferedImage image, final Point p) {
		return getColorAt(image, p.x, p.y);
	}
	
	/**
	 * Gets the array of all colors of the given image.
	 * @param image The image.
	 * @return A two-dimensional array of the colors of the given image.
	 */
	public static Color[][] getColors(final BufferedImage image) {
		final int w = image.getWidth();
		final int h = image.getHeight();
		final Color[][] colors = new Color[w][h];
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				colors[x][y] = getColorAt(image, x, y);
			}
		}
		return colors;
	}

	/**
	 * Gets all points within an image which have a color within threshold distance of a given color.
	 * @param image The image to scan.
	 * @param bounds The bounds to scan within.
	 * @param color The color to scan for.
	 * @param threshold The threshold to scan by.
	 * @return A list of points where the color of the image is within the threshold.
	 */
	public static List<Point> getPointsWithColor(final BufferedImage image, final Rectangle bounds,
			final Color color, final double threshold) {
		final List<Point> points = new LinkedList<Point>();
		final Color[][] colors = getColors(image);
		for (int x = bounds.x; x < bounds.width; x++) {
			for (int y = bounds.y; y < bounds.height; y++) {
				if (ColorUtil.getDistance(colors[x][y], color) <= threshold) {
					points.add(new Point(x, y));
				}
			}
		}
		return points;
	}

	/**
	 * Gets all points within an image which have a color equal to a given color.
	 * @param image The image to scan.
	 * @param bounds The bounds to scan within.
	 * @param color The color to scan for.
	 * @return A list of points where the color of the image is equal.
	 */
	public static List<Point> getPointsWithColor(final BufferedImage image,
			final Rectangle bounds, final Color color) {
		return getPointsWithColor(image, bounds, color, 0.0);
	}
	
	/**
	 * Gets all points within an image which have a color within threshold distance of a given color.
	 * @param image The image to scan.
	 * @param color The color to scan for.
	 * @param threshold The threshold to scan by.
	 * @return A list of points where the color of the image is within the threshold.
	 */
	public static List<Point> getPointsWithColor(final BufferedImage image,
			final Color color, final double threshold) {
		return getPointsWithColor(image, new Rectangle(image.getWidth(), image.getHeight()), color, threshold);
	}

	/**
	 * Gets all points within an image which have a color equal to a given color.
	 * @param image The image to scan.
	 * @param color The color to scan for.
	 * @return A list of points where the color of the image is equal.
	 */
	public static List<Point> getPointsWithColor(final BufferedImage image, final Color color) {
		return getPointsWithColor(image, color, 0.0);
	}

	/**
	 * Makes a color in a given image transparent.
	 * @param source The source image.
	 * @param color The color to make transparent.
	 * @return The altered image.
	 */
	public static BufferedImage makeColorTransparent(final BufferedImage source, final Color color) {  
		final BufferedImage image = new BufferedImage(source.getWidth(),
				source.getHeight(), BufferedImage.TYPE_INT_ARGB);  
		final Graphics2D g = image.createGraphics();  
		g.setComposite(AlphaComposite.Src);
		g.drawImage(image, null, 0, 0);
		g.dispose();
		final int colorRGB = color.getRGB();
		for(int i = 0; i < image.getHeight(); i++) {  
			for(int j = 0; j < image.getWidth(); j++) {  
				if (image.getRGB(j, i) == colorRGB) {  
					image.setRGB(j, i, 0x8F1C1C);
				}  
			}  
		}  
		return image;  
	}  

	/**
	 * Makes an image translucent by a given transparency factor.
	 * @param source The source image.
	 * @param transperancy The transparency factor to apply.
	 * @return The altered image.
	 */
	public static BufferedImage makeImageTranslucent(final BufferedImage source, final float transperancy) {   
		final BufferedImage image = new BufferedImage(source.getWidth(),
				source.getHeight(), BufferedImage.TRANSLUCENT);  
		final Graphics2D g = image.createGraphics();  
		g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, transperancy));  
		g.drawImage(image, null, 0, 0);  
		g.dispose();  
		return image;  
	}  

	/**
	 * Flips a given image horizontally.
	 * @param source The source image.
	 * @return The flipped image.
	 */
	public static BufferedImage flipHorizontally(final BufferedImage source) {  
		final int w = source.getWidth();  
		final int h = source.getHeight();  
		final BufferedImage image = new BufferedImage(w, h, source.getType());  
		final Graphics2D g = image.createGraphics();  
		g.drawImage(source, 0, 0, w, h, w, 0, 0, h, null);  
		g.dispose();  
		return image;  
	}  

	/**
	 * Flips a given image vertically.
	 * @param source The source image.
	 * @return The flipped image.
	 */
	public static BufferedImage flipVertically(final BufferedImage source) {  
		final int w = source.getWidth();  
		final int h = source.getHeight();  
		final BufferedImage image = new BufferedImage(w, h, source.getType());  
		final Graphics2D g = image.createGraphics();  
		g.drawImage(source, 0, 0, w, h, 0, h, w, 0, null);  
		g.dispose();  
		return image;  
	}  

	/**
	 * Rotates a given image to a given angle.
	 * @param source The source image.
	 * @param angle The angle to rotate by (in degrees).
	 * @return The rotated image.
	 */
	public static BufferedImage rotate(final BufferedImage source, final int angle) {  
		final int w = source.getWidth();  
		final int h = source.getHeight();  
		final BufferedImage image = new BufferedImage(w, h, source.getType());  
		final Graphics2D g = image.createGraphics();  
		g.rotate(Math.toRadians(angle), w / 2, h / 2);  
		g.drawImage(source, null, 0, 0);  
		return image;  
	}  

	/**
	 * Resizes a given image to a given width and height.
	 * @param source The source image.
	 * @param width The new width.
	 * @param height The new height.
	 * @return The altered image.
	 */
	public static BufferedImage resize(final BufferedImage source, final int width, final int height) {  
		final int w = source.getWidth();  
		final int h = source.getHeight();  
		final BufferedImage image = new BufferedImage(width, height, source.getType());  
		final Graphics2D g = image.createGraphics();  
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);  
		g.drawImage(source, 0, 0, width, height, 0, 0, w, h, null);  
		g.dispose();  
		return image;  
	}  

	/**
	 * Splits a given image into an amount of same-sized sub-images.
	 * @param source The source image.
	 * @param cols The amount of columns.
	 * @param rows The amount of rows.
	 * @return The array of images derived from the source image.
	 */
	public static BufferedImage[] splitImage(final BufferedImage source, final int cols, final int rows) {  
		final int w = source.getWidth() / cols;  
		final int h = source.getHeight() / rows;  
		int num = 0;  
		final BufferedImage imgs[] = new BufferedImage[w * h];  
		for (int y = 0; y < rows; y++) {  
			for (int x = 0; x < cols; x++) {  
				imgs[num] = new BufferedImage(w, h, source.getType());  
				final Graphics2D g = imgs[num].createGraphics();  
				g.drawImage(source, 0, 0, w, h, w * x, h * y, w * x + w, h * y + h, null);  
				g.dispose();  
				num++;  
			}  
		}  
		return imgs;  
	}

	/**
	 * Finds all locations in a parent image where a smaller image is matched by a given threshold.
	 * @param large The large, parent image.
	 * @param small The small image to search for in the large image.
	 * @param threshold The image distance threshold, ranging between 0.0 and 1.0.
	 * @return A list of points where matches were found.
	 */
	public static List<Point> findMatchLocations(final BufferedImage large,
			final BufferedImage small, final double threshold) {
		return findMatchLocations(large, small, threshold, false);
	}

	/**
	 * Finds all locations in a parent image where a smaller image is exactly matched.
	 * @param large The large, parent image.
	 * @param small The small image to search for in the large image.
	 * @return A list of points where matches were found.
	 */
	public static List<Point> findMatchLocations(final BufferedImage large, final BufferedImage small) {
		return findMatchLocations(large, small, 0.0);
	}
	
	private static List<Point> findMatchLocations(final BufferedImage large,
			final BufferedImage small, final double threshold, final boolean breakAfterFirst) {
		final List<Point> locs = new LinkedList<Point>();
		for (int y = 0; y < large.getHeight() - small.getHeight(); y++) {
			for (int x = 0; x < large.getWidth() - small.getWidth(); x++) {
				if (imageDistance(large, x, y, small) <= threshold) {
					locs.add(new Point(x,y));
					if (breakAfterFirst) {
						return locs;
					}
				}
			}
		}
		return locs;
	}

	private static double imageDistance(final BufferedImage large,
			final int bx, final int by, final BufferedImage small) {
		float dist = 0.0F;
		for (int y = 0; y < small.getHeight(); y++) {
			for (int x = 0; x < small.getWidth(); x++) {
				for (int colorChannel = 0; colorChannel < 3; colorChannel++) {
					dist += Math.pow(small.getRGB(x, y) - large.getRGB(x, y), 2);
				}
			}
		}
		return Math.sqrt(dist) / small.getWidth() / large.getHeight();
	}

	/**
	 * Gets whether a larger image contains a smaller image or not by a given threshold.
	 * @param large The large, parent image.
	 * @param small The small image to search for in the large image.
	 * @param threshold The image distance threshold, ranging between 0.0 and 1.0.
	 * @return <tt>true</tt> if the large image contains the small image; otherwise <tt>false</tt>.
	 */
	public static boolean imageContains(final BufferedImage large,
			final BufferedImage small, final double threshold) {
		return findMatchLocations(large, small, threshold, true).size() > 0;
	}

	/**
	 * Gets whether a larger image contains an exact match of a smaller image or not.
	 * @param large The large, parent image.
	 * @param small The small image to search for in the large image.
	 * @return <tt>true</tt> if the large image contains the small image; otherwise <tt>false</tt>.
	 */
	public static boolean imageContains(final BufferedImage large, final BufferedImage small) {
		return imageContains(large, small, 0.0);
	}
	
	/**
	 * Gets whether the sub image of a larger image contains a smaller image by a given threshold or not.
	 * @param large The large, parent image.
	 * @param x The x coordinate of the sub image.
	 * @param y The y coordinate of the sub image.
	 * @param width The width of the sub image.
	 * @param height The height of the sub image.
	 * @param small The small image to search for in the large image.
	 * @param threshold The image distance threshold, ranging between 0.0 and 1.0.
	 * @return <tt>true</tt> if the sub image of the large image contains the small image; otherwise <tt>false</tt>.
	 */
	public static boolean subImageContains(final BufferedImage large, final int x, final int y,
			final int width, final int height, final BufferedImage small, final double threshold) {
		return imageContains(large.getSubimage(x, y, width, height), small, threshold);
	}
	
	/**
	 * Gets whether the sub image of a larger image contains an exact match of a smaller image or not.
	 * @param large The large, parent image.
	 * @param x The x coordinate of the sub image.
	 * @param y The y coordinate of the sub image.
	 * @param width The width of the sub image.
	 * @param height The height of the sub image.
	 * @param small The small image to search for in the large image.
	 * @return <tt>true</tt> if the sub image of the large image contains the small image; otherwise <tt>false</tt>.
	 */
	public static boolean subImageContains(final BufferedImage large, final int x, final int y,
			final int width, final int height, final BufferedImage small) {
		return subImageContains(large, x, y, width, height, small, 0.0);
	}

	/**
	 * Gets whether the sub image of a larger image contains a smaller image by a given threshold or not.
	 * @param large The large, parent image.
	 * @param sub The bounds of the sub image.
	 * @param small The small image to search for in the large image.
	 * @param threshold The image distance threshold, ranging between 0.0 and 1.0.
	 * @return <tt>true</tt> if the sub image of the large image contains the small image; otherwise <tt>false</tt>.
	 */
	public static boolean subImageContains(final BufferedImage large,
			final Rectangle sub, final BufferedImage small, final double threshold) {
		return subImageContains(large, sub.x, sub.y, sub.width, sub.height, small, threshold);
	}

	/**
	 * Gets whether the sub image of a larger image contains an exact match of a smaller image or not.
	 * @param large The large, parent image.
	 * @param sub The bounds of the sub image.
	 * @param small The small image to search for in the large image.
	 * @return <tt>true</tt> if the sub image of the large image contains the small image; otherwise <tt>false</tt>.
	 */
	public static boolean subImageContains(final BufferedImage large,
			final Rectangle sub, final BufferedImage small) {
		return subImageContains(large, sub, small, 0.0);
	}

}
