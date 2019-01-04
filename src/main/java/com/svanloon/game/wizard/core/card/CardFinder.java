package com.svanloon.game.wizard.core.card;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public interface CardFinder {

	/**
	 * 
	 * Document the findCard method 
	 *
	 * @param i
	 * @return Card
	 */
	public Card findCard(int i);

	/**
	 * 
	 * Document the getCardsInDeck method 
	 *
	 * @return int
	 */
	public int getCardsInDeck();
}
