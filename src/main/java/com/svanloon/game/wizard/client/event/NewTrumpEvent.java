package com.svanloon.game.wizard.client.event;

import com.svanloon.game.wizard.core.card.Card;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class NewTrumpEvent extends WizardEvent {
	private Card card;
	/**
	 * 
	 * Constructs a new <code>NewTrumpEvent</code> object. 
	 *
	 * @param card
	 */
	public NewTrumpEvent(Card card) {
		super();
		this.card = card;
	}
	/**
	 * @return the card
	 */
	public Card getCard() {
		return card;
	}
}
