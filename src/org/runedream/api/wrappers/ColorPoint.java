package org.runedream.api.wrappers;

import java.awt.Color;
import java.awt.Point;

public class ColorPoint {
	
	private final Point p;
	private final Color c;
	
	public ColorPoint(final Point p, final Color c) {
		this.p = p;
		this.c = c;
	}
	
	public ColorPoint(final int x, final int y, final Color c) {
		this(new Point(x, y), c);
	}
	
	public ColorPoint(final Point p, final int r, final int g, final int b) {
		this(p, new Color(r, g, b));
	}
	
	public ColorPoint(final int x, final int y, final int r, final int g, final int b) {
		this(new Point(x, y), new Color(r, g, b));
	}
	
	public Point getPoint() {
		return p;
	}
	
	public Color getColor() {
		return c;
	}
	
	@Override
	public boolean equals(final Object object) {
		if (object instanceof ColorPoint) {
			final ColorPoint obj = (ColorPoint) object;
			return obj.getPoint().equals(p) && obj.getColor().equals(p);
		}
		return false;
	}

}
