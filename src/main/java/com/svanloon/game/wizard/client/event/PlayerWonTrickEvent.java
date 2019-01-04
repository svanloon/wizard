package com.svanloon.game.wizard.client.event;

import com.svanloon.game.wizard.core.card.Card;

/**
 * 
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class PlayerWonTrickEvent extends PlayerEvent {
	private Card card;
	/**
	 * 
	 * Constructs a new <code>PlayerWonTrickEvent</code> object. 
	 * @param id 
	 * @param card 
	 *
	 * @param playerName
	 */
	public PlayerWonTrickEvent(int id, Card card) {
		super(id);
		this.card = card;
	}
	/**
	 * Returns the card.
	 *
	 * @return the card.
	 */
	public Card getCard() {
		return card;
	}
	/**
	 * Sets the card.
	 *
	 * @param card The new value for card.
	 */
	public void setCard(Card card) {
		this.card = card;
	}
	
	@Override
	public String toString() {
		return "[winner " + super.toString() + ":" + card + "]";
	}
}
