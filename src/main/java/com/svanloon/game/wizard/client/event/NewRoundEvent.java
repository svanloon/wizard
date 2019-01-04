package com.svanloon.game.wizard.client.event;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class NewRoundEvent extends WizardEvent{
	private int round;
	/**
	 * 
	 * Constructs a new <code>NewRoundEvent</code> object. 
	 *
	 * @param round
	 */
	public NewRoundEvent(int round) {
		this.round = round;
	}

	/**
	 * @return int
	 */
	public int getRound() {
		return this.round;
	}

}
