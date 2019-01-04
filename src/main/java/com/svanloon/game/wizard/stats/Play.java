package com.svanloon.game.wizard.stats;

import com.svanloon.game.wizard.core.card.Card;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class Play {
	private int playerId;
	private Card card;
	/**
	 * 
	 * Constructs a new <code>Play</code> object. 
	 *
	 * @param playerId
	 * @param card
	 */
	public Play (int playerId, Card card) {
		this.playerId = playerId;
		this.card = card;
	}
	/**
	 * 
	 * Document the getCard method 
	 *
	 * @return Card
	 */
	public Card getCard() {
		return card;
	}
	/**
	 * 
	 * Document the setCard method 
	 *
	 * @param pCard
	 */
	public void setCard(Card pCard) {
		card = pCard;
	}
	/**
	 * 
	 * Document the getPlayerId method 
	 *
	 * @return int
	 */
	public int getPlayerId() {
		return playerId;
	}
	/**
	 * 
	 * Document the setPlayerId method 
	 *
	 * @param pPlayerId
	 */
	public void setPlayerId(int pPlayerId) {
		playerId = pPlayerId;
	}
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(playerId).append(":").append(card);
		return sb.toString();
	}
}
