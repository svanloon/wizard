package com.svanloon.game.wizard.client.event;

import com.svanloon.game.wizard.core.card.Card;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class PlayerPlayedEvent extends PlayerEvent {
	private Card card;
	/**
	 * 
	 * Constructs a new <code>PlayerPlayedEvent</code> object. 
	 * @param id 
	 *
	 * @param playerName
	 * @param card
	 */
	public PlayerPlayedEvent (int id, Card card) {
		super(id);
		this.card = card;
	}
	/**
	 * @return the card
	 */
	public Card getCard() {
		return card;
	}
	
	@Override
	public String toString() {
		String player = super.toString();
		return player + ": played " + card;   
	}
}