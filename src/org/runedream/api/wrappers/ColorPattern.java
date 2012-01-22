package org.runedream.api.wrappers;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.runedream.api.methods.ImageUtil;

public class ColorPattern {
	
	private final List<ColorPoint> points = new LinkedList<ColorPoint>();
	
	public ColorPattern(final ColorPoint[] points) {
		for (final ColorPoint cp : points) {
			this.points.add(cp);
		}
	}
	
	public ColorPattern() {
		this(new ColorPoint[0]);
	}
	
	public boolean isPresent(final BufferedImage image) {
		for (final ColorPoint cp : points) {
			if (image.getRGB(cp.getPoint().x, cp.getPoint().y) != cp.getColor().getRGB()) {
				return false;
			}
		}
		return true;
	}
	
	public Point getLocation(final BufferedImage image) {
		int xc = -1;
		int yc = -1;
		int trueCount = 0;
		boolean[] matches = new boolean[points.size()];
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				for (int i = 0; i < points.size(); i++) {
					final ColorPoint cp = points.get(i);
					if (cp.getColor().equals(ImageUtil.getColorAt(image, x, y))) {
						if (trueCount == 0) {
							final Point p = cp.getPoint();
							xc = p.x;
							yc = p.y;
						}
						matches[trueCount++] = true;
						break;
					} else {
						trueCount = 0;
						Arrays.fill(matches, false);
					}
				}
			}
		}
		return new Point(xc, yc);
	}
	
	public void add(final ColorPoint cp) {
		points.add(cp);
	}
	
	public void remove(final ColorPoint cp) {
		points.remove(cp);
	}
	
	public ColorPoint get(final int idx) {
		return points.get(idx);
	}
	
	public int getSize() {
		return points.size();
	}
	
	public ColorPoint[] getColorPoints() {
		return points.toArray(new ColorPoint[points.size()]);
	}
	
	public Point[] getPoints() {
		final Point[] pts = new Point[points.size()];
		for (int i = 0; i < pts.length; i++) {
			pts[i] = get(i).getPoint();
		}
		return pts;
	}
	
	public Color[] getColors() {
		final Color[] colors = new Color[points.size()];
		for (int i = 0; i < colors.length; i++) {
			colors[i] = get(i).getColor();
		}
		return colors;
	}

}
