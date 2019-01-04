/*
 * $Id$ 
 * $HeadURL$
 */
package com.svanloon.game.wizard.client.player;


/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class IndividualScore {
	private Player player;

	private int score;

	/**
	 * 
	 * Constructs a new <code>IndividualScore</code> object. 
	 *
	 * @param player
	 * @param score
	 */
	public IndividualScore(Player player, int score) {
		this.player = player;
		this.score = score;
	}
	/**
	 * 
	 * Document the setScore method 
	 *
	 * @param score
	 */
	public void setScore(int score) {
		this.score = score;
	}
	/**
	 * 
	 * Document the getScore method 
	 *
	 * @return int
	 */
	public int getScore() {
		return this.score;
	}

	/**
	 * 
	 * Document the getPlayer method 
	 *
	 * @return Player
	 */
	public Player getPlayer() {
		return this.player;
	}
}
