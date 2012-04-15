package org.runedream.api.wrappers;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.runedream.api.methods.Calculations;
import org.runedream.api.methods.Game;
import org.runedream.api.methods.Mouse;
import org.runedream.api.methods.OCR;
import org.runedream.api.util.Time;

/**
 * A wrapper describing a game entity (i.e. NPC, GameObject, etc.) consisting of a color or DTM within a set.
 * 
 * @see DTM
 * @author iSmokePurple
 */
public class Entity {

	private boolean useDTMs = false;
	private boolean boundedSearch = false;
	private Rectangle bounds = null;
	private List<DTM> dtms = new LinkedList<DTM>();
	private List<Color> colors = new LinkedList<Color>();
	
	public static final Point VIEWPORT_CENTER = new Point(261, 148);
	public static final Rectangle PLAYER_AREA = new Rectangle(236, 96, 49, 95);
	public static final Rectangle EXP_POPUP_AREA = new Rectangle(218, 0, 110, 65);

	/**
	 * Constructs an entity which when searched for, simply finds the nearest point to one of given colors.
	 * @param colors The colors to search for when {@link Entity#findPoint()} is called.
	 */
	public Entity(final Color...colors) {
		this.colors.addAll(Arrays.asList(colors));
	}

	/**
	 * Constructs an entity which when searched for, simply finds the nearest point to one of given colors.
	 * @param bounds The bounding rectangle to limit the finding to.
	 * @param colors The colors to search for when {@link Entity#findPoint()} is called.
	 */
	public Entity(final Rectangle bounds, final Color...colors) {
		this(colors);
		this.bounds = bounds;
		boundedSearch = true;
	}

	/**
	 * Constructs an entity which when searched for, simply finds the nearest point to one of given colors.
	 * @param bounds The bounding rectangle to limit the finding to.
	 * @param colors The colors to search for when {@link Entity#findPoint()} is called.
	 */
	public Entity(final Rectangle bounds, final DTM...dtms) {
		this(dtms);
		this.bounds = bounds;
		boundedSearch = true;
	}

	/**
	 * Constructs an entity which when searched for, simply finds the nearest point to one of given colors.
	 * @param colors The colors to search for when {@link Entity#findPoint()} is called.
	 */
	public Entity(final DTM...dtms) {
		this.dtms.addAll(Arrays.asList(dtms));
		useDTMs = true;
	}

	/**
	 * Interacts a given action with the entity.
	 * @param action The action to click.
	 * @return <tt>true</tt> if action clicked; otherwise <tt>false</tt>.
	 */
	public boolean interact(final String action) {
		final Point point = findPoint();
		if (point != null) {
			Mouse.move(point);
			Time.sleep(20, 30);
			final String uptext = OCR.getUpText();
			if (uptext != null && uptext.contains(action)) {
				Mouse.click(point);
				return true;
			} else {
				Mouse.click(false);
				Time.sleep(70, 100);
				final Menu menu = Menu.find();
				return menu != null && menu.interact(action);
			}
		}
		return false;
	}

	/**
	 * Finds an interaction point on the Entity.
	 * <br>
	 * Note: It is recommended that users of this class view the source to learn how this method works at
	 * <a href="https://github.com/RuneDream/RuneDream/blob/master/src/org/runedream/api/wrappers/Entity.java>
	 * Entity.java</a> before using it.
	 * @return A point located with regards to the constructor parameters.
	 */
	public Point findPoint() {
		if (!useDTMs) {
			for (final Color c : colors) {
				final Point target = Calculations.getNearestPoint(VIEWPORT_CENTER, c);
				if (target != null && Game.VIEWPORT.contains(target) && !EXP_POPUP_AREA.contains(target)
							&& !PLAYER_AREA.contains(target)) {
					if ((boundedSearch && bounds.contains(target)) || !boundedSearch) {
						return target;
					}
				}
			}
		} else {
			for (final DTM dtm : dtms) {
				if (boundedSearch) {
					for (final Point point : dtm.getPoints()) {
						if (bounds.contains(point)) {
							return point;
						}
					}
				} else {
					for (final Point point : dtm.getPoints()) {
						if (Game.VIEWPORT.contains(point)) {
							return point;
						}
					}
				}
			}
		}
		return null;
	}

}
