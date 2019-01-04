package com.svanloon.game.wizard.core.card;


/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class PokerCardFinder implements CardFinder {

	/**
	 * 
	 * Document the findCard method 
	 *
	 * @param i
	 * @return Card
	 */
	public Card findCard(int i) {
		Value value = Value.findValueByIndex( (i % 13) + 1);
		Suit suit = Suit.findSuitByIndex( (i / 13) % 4);
		return new Card(value, suit, i);
	}
	
	public int getCardsInDeck() {
		return 52;
	}
}
