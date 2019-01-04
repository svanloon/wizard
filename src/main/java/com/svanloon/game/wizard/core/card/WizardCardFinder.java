package com.svanloon.game.wizard.core.card;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class WizardCardFinder implements CardFinder {

	/**
	 * 
	 * Document the findCard method 
	 *
	 * @param i
	 * @return Card
	 */
	public Card findCard(int i) {
		Value value = Value.findValueByIndex(i % 15);
		Suit suit;
		int index;
		if (value.getIndex() == Value.JESTER.getIndex()	|| value.getIndex() == Value.WIZARD.getIndex()) {
			suit = Suit.NONE;
			index = ((i / 15) % 4) + 1;
		} else {
			suit = Suit.findSuitByIndex((i / 15) % 4);
			index = ((i / 15) % 4) + 1;
		}
		return new Card(value, suit, index);
	}

	public int getCardsInDeck() {
		return 60;
	}
}
