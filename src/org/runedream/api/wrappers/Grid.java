package org.runedream.api.wrappers;

import org.runedream.api.methods.ColorUtil;
import org.runedream.api.methods.Game;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

/**
 * Wrapper used to create a grid of smaller rectangle squares., useful for optimizing detection methods.
 * <br>
 * Note: It is faster if the piece size is bigger. It is highly recommended to make the Grid variable final.
 *
 * @author Dang
 */

public class Grid {

	private final int pieceSize;
	private final Rectangle bounds;
	private final LinkedList<Rectangle> rectangles = new LinkedList<Rectangle>();

	/**
	 * Constructs a new Grid and sets its rectangles.
	 * @param bounds The bounds in which to create the grid.
	 * @param pieceSize The size of each piece of the grid.
	 */
	public Grid(final Rectangle bounds, final int pieceSize) {
		this.bounds = bounds;
		this.pieceSize = pieceSize;
		this.setRectangles();
	}

	/**
	 * Constructs a new Grid of the game screen and sets its rectangles.
	 * @param pieceSize The size of each piece of the grid.
	 */
	public Grid(final int pieceSize) {
		this(new Rectangle(0, 0, Game.getImage().getWidth(), Game.getImage().getHeight()), pieceSize);
	}

	/**
	 * Sets the grid.
	 */
	private void setRectangles() {
		this.rectangles.clear();
		for (int x = 0; x < this.bounds.width; x += this.pieceSize) {
			if (x + this.pieceSize > bounds.width) {
				break;
			}
			for (int y = 0; y < this.bounds.height; y += this.pieceSize) {
				if (y + this.pieceSize > this.bounds.height) {
					break;
				}
				this.rectangles.add(new Rectangle(x, y, this.pieceSize, this.pieceSize));
			}

		}
	}

	/**
	 * Gets the rectangle of the given index.
	 * @param index The index of the piece to get.
	 * @return The piece at the given index.
	 */
	public Rectangle getPiece(final int index) {
		return this.rectangles.get(index);
	}

	/**
	 * Adds a rectangle to the grid.
	 * @param rects The rectangles to add to the grid.
	 */
	public void add(final Rectangle... rects) {
		for (final Rectangle rectangle : rects) {
			this.rectangles.add(rectangle);
		}
	}

	/**
	 * Removes a rectangle from the grid.
	 * @param index The index of the rectangle to remove.
	 */
	public void remove(final int index) {
		this.rectangles.remove(index);
	}

	/**
	 * Gets all of the grid's rectangles.
	 * @return The list of rectangles in the grid.
	 */
	public LinkedList<Rectangle> getPieces() {
		return this.rectangles;
	}

	/**
	 * Gets all of the grid's rectangles.
	 * @return An array of rectangles in the grid.
	 */
	public Rectangle[] getPiecesArray() {
		return this.rectangles.toArray(new Rectangle[this.rectangles.size()]);
	}

	/**
	 * Gets the array of colors of a given index rectangle.
	 * @param index The index of the rectangle.
	 * @return The array of colors in the piece.
	 */
	public Color[] getColors(final int index) {
		final BufferedImage image = Game.getImage();
		final Rectangle rect = this.getPiece(index);
		final List<Color> colors = new LinkedList<Color>();
		for (int x = 0; x < rect.width; x += 1) {
			for (int y = 0; y < rect.height; y += 1) {
				colors.add(new Color(image.getRGB(x, y)));
			}
		}
		return colors.toArray(new Color[colors.size()]);
	}

	/**
	 * Gets the array of colors of given index rectangles.
	 * @param indexes The indexes of the rectangles.
	 * @return The array of colors in the pieces.
	 */
	public Color[] getColors(final int... indexes) {
		final List<Color> colors = new LinkedList<Color>();
		for (final int index : indexes) {
			final Color[] index_colors = this.getColors(index);
			for (final Color c : index_colors) {
				colors.add(c);
			}
		}
		return colors.toArray(new Color[colors.size()]);
	}

	/**
	 * Checks if the rectangle of a given index contains a color with a tolerance.
	 * @param index The index value of the piece to check.
	 * @param tolerance The tolerance to check with.
	 * @param color The color to search for.
	 * @return <tt>true</tt> if the piece contains the color with the tolerance; otherwise <tt>false</tt>.
	 */
	public boolean contains(final int index, final int tolerance, final Color color) {
		final BufferedImage image = getImageAt(index);
		final int width = image.getWidth();
		final int height = image.getHeight();
		for (int x = 0; x < width; x += 1) {
			for (int y = 0; y < height; y += 1) {
				if (ColorUtil.isTolerable(color, new Color(image.getRGB(x, y)), tolerance)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Checks if the rectangle of a given index contains a color.
	 * @param index The index value of the piece to check.
	 * @param color The color to search for.
	 * @return <tt>true</tt> if the piece contains the color; otherwise <tt>false</tt>.
	 */
	public boolean contains(final int index, final Color color) {
		return contains(index, 0, color);
	}

	/**
	 * Checks if the rectangle of a given index contains all colors with a tolerance.
	 * @param index The index value of the piece to check.
	 * @param tolerance The tolerance to check with.
	 * @param colors The colors to search for.
	 * @return <tt>true</tt> if the piece contains all of the colors with the tolerance; otherwise <tt>false</tt>.
	 */
	public boolean containsAll(final int index, int tolerance, final Color... colors) {
		for (final Color color : colors) {
			if (!contains(index, tolerance, color)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks if the rectangle of a given index contains all colors.
	 * @param index The index value of the piece to check.
	 * @param colors The colors to search for.
	 * @return <tt>true</tt> if the piece contains all of the colors; otherwise <tt>false</tt>.
	 */
	public boolean containsAll(final int index, final Color... colors) {
		return containsAll(index, 0, colors);
	}

	/**
	 * Checks if the rectangle of a given index contains a point.
	 * @param index The index value of the piece to check.
	 * @param point The point to check for.
	 * @return <tt>true</tt> if the piece contains the point; otherwise <tt>false</tt>.
	 */
	public boolean contains(final int index, final Point point) {
		return getPiece(index).contains(point);
	}

	/**
	 * Checks if the rectangle of a given index contains all points.
	 * @param index The index value of the piece to check.
	 * @param points The points to check for.
	 * @return <tt>true</tt> if the piece contains the points; otherwise <tt>false</tt>.
	 */
	public boolean containsAll(final int index, final Point... points) {
		final Rectangle piece = getPiece(index);
		for (final Point point : points) {
			if (!piece.contains(point)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Gets all rectangles containing an array of colors with a tolerance.
	 * @param tolerance The tolerance to check with.
	 * @param colors The colors to search for.
	 * @return An array of rectangles with colors with the tolerance.
	 */
	public Rectangle[] getContaining(final int tolerance, final Color... colors) {
		final List<Rectangle> pieces = new LinkedList<Rectangle>();
		for (int index = 0; index < rectangles.size(); index += 1) {
			if (containsAll(index, tolerance, colors)) {
				pieces.add(rectangles.get(index));
			}
		}
		return pieces.toArray(new Rectangle[pieces.size()]);
	}

	/**
	 * Gets all rectangles containing an array of colors.
	 * @param colors The colors to search for.
	 * @return An array of rectangles with colors.
	 */
	public Rectangle[] getContaining(final Color... colors) {
		return this.getContaining(0, colors);
	}

	/**
	 * Gets the rectangle containing a point.
	 * @param point The point to search for.
	 * @return The rectangle containing the point.
	 */
	public Rectangle getContaining(final Point point) {
		for (final Rectangle rectangle : rectangles) {
			if (rectangle.contains(point)) {
				return rectangle;
			}
		}
		return null;
	}

	/**
	 * Gets the rectangle containing all points.
	 * @param points The points to search for.
	 * @return The rectangle containing the points.
	 */
	public Rectangle getContaining(final Point... points) {
		for (int index = 0; index < rectangles.size(); index += 1) {
			if (containsAll(index, points)) {
				return getPiece(index);
			}
		}
		return null;
	}

	/**
	 * Gets the piece size.
	 * @return The size of each piece.
	 */
	public int getPieceSize() {
		return this.pieceSize;
	}

	/**
	 * Gets the image of the rectangle at the given index.
	 * @param index The index of the rectangle.
	 * @return An image of the piece.
	 */
	public BufferedImage getImageAt(final int index) {
		final Rectangle bound = getPiece(index);
		final BufferedImage game = Game.getImage();
		return game.getSubimage(bound.x, bound.y, bound.width, bound.height);
	}

	/**
	 * Gets the images of the rectangles at the given indexes.
	 * @param indexes The indexes of the rectangles.
	 * @return Images of the pieces.
	 */
	public BufferedImage[] getImageAt(final int... indexes) {
		final List<BufferedImage> images = new LinkedList<BufferedImage>();
		for (final int index : indexes) {
			images.add(getImageAt(index));
		}
		return images.toArray(new BufferedImage[images.size()]);
	}

	/**
	 * Draws the grid's rectangles on a given Graphics object.
	 * @param g The graphics object to draw on.
	 */
	public void draw(final Graphics g) {
		for (final Rectangle r : this.rectangles) {
			g.drawRect(r.x, r.y, r.width, r.height);
		}
	}

	/**
	 * Draws the grid's rectangle intersection points on a given Graphics object.
	 * @param g The graphics object to draw on.
	 */
	public void drawPoints(final Graphics g) {
		for (final Rectangle r : rectangles) {
			final int x = r.x, y = r.y;
			g.drawLine(x, y, x, y);
		}
	}

}