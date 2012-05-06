package org.runedream.api.util;

import org.runedream.api.methods.Skills;

/**
 * Skill tracking utility; tracks the statistics of a skill, updating when told to.
 * 
 * @author Aidden
 */
public class SkillTracker {

	private int currentExp;
	private int currentLvl;
	private final int startExp;
	private final int startLvl;
	private final Skills.Skill skill;
	
	/**
	 * Constructs a SkillTracker for a given Skill.
	 * @param skill The Skill to track.
	 */
	public SkillTracker(final Skills.Skill skill) {
		this.skill = skill;
		if (!Skills.isOpen()) {
			Skills.open();
		}
		startExp = skill.getExperience();
		currentExp = startExp;
		startLvl = Skills.Skill.getLevel(currentExp);
		currentLvl = startLvl;
	}
	
	/**
	 * Gets the Skill being tracked.
	 * @return The Skill being tracked.
	 */
	public Skills.Skill getSkill() {
		return skill;
	}
	
	/**
	 * Gets the last experience recorded by the tracker.
	 * @return The last experience recorded by the tracker.
	 */
	public int getExperience() {
		return currentExp;
	}
	
	/**
	 * Gets the gained experience in the Skill.
	 * @return The gained experience in the Skill.
	 */
	public int getExperienceGained() {
		return getExperience() - startExp;
	}
	
	/**
	 * Gets the last level recorded by the tracker.
	 * @return The last level recorded by the tracker.
	 */
	public int getLevel() {
		return currentLvl;
	}
	
	/**
	 * Gets the gained levels in the Skill.
	 * @return Gets the gained levels in the Skill.
	 */
	public int getLevelsGained() {
		return getLevel() - startLvl;
	}
	
	/**
	 * Adds an amount of experience to the tracker. Intended use is manual tracking.
	 * @param exp The amount of experience to add.
	 */
	public void addExperience(final int exp) {
		currentExp += exp;
		currentLvl = getLevel();
	}
	
	/**
	 * Recalculate current experience and current level using OCR for accuracy.
	 * <br>
	 * Note: Opens the Stats tab to check.
	 */
	public void update() {
		if (!Skills.isOpen()) {
			Skills.open();
		}
		currentExp = skill.getExperience();
		currentLvl = getLevel();
	}

}
