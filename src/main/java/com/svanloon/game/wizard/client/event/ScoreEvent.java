package com.svanloon.game.wizard.client.event;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class ScoreEvent extends PlayerEvent {
	private int scoreChangeAmount;
	/**
	 * 
	 * Constructs a new <code>ScoreEvent</code> object. 
	 * @param id 
	 *
	 * @param playerName
	 * @param scoreChangeAmount
	 */
	public ScoreEvent(int id, int scoreChangeAmount) {
		super(id);
		this.scoreChangeAmount = scoreChangeAmount;
	}
	/**
	 * Returns the scoreChangeAmount.
	 *
	 * @return the scoreChangeAmount.
	 */
	public int getScoreChangeAmount() {
		return scoreChangeAmount;
	}
}
