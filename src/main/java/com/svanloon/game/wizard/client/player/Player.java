package com.svanloon.game.wizard.client.player;

import com.svanloon.game.wizard.core.card.Card;
import com.svanloon.game.wizard.core.card.Hand;
import com.svanloon.game.wizard.core.card.Suit;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public interface Player {
	/**
	 * 
	 * @param id
	 */
	public void setId(int id);
	/**
	 * 
	 * @return int
	 */
	public int getId();
	/**
	 * 
	 * Document the getName method 
	 *
	 * @return String
	 */
	public String getName();

	/**
	 * 
	 * Document the giveCard method 
	 *
	 * @param card
	 */
	public void giveCard(Card card);

	/**
	 * 
	 * Document the playCard method 
	 *
	 * @return Card
	 */
	public Card playCard();
	/**
	 * Document the playCard method 
	 * @param card 
	 */
	public void playCardIsNotValid(Card card);
	/**
	 * 
	 * Document the playCardIsValid method 
	 *
	 * @param card
	 */
	public void playCardIsValid(Card card);
	/**
	 * 
	 * Document the bid method 
	 *
	 * @param trump
	 * @param min 
	 * @param max 
	 * @param notAllowedToBid 
	 * @return int
	 */
	public int bid(Card trump, int min, int max, int notAllowedToBid);

	/**
	 * 
	 * Document the getHand method 
	 *
	 * @return Hand
	 */
	public Hand getHand();
	
	/**
	 * 
	 * @return suit
	 */
	public Suit pickTrump();
}