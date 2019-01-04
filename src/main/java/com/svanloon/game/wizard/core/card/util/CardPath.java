package com.svanloon.game.wizard.core.card.util;

import java.util.ArrayList;
import java.util.List;

import com.svanloon.game.wizard.core.card.Card;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class CardPath {

	private List<CardNode> cardPathCollection = new ArrayList<CardNode>();

	/**
	 * 
	 * Constructs a new <code>CardPath</code> object. 
	 *
	 */
	public CardPath() {
		super();
	}
	/**
	 * 
	 * Document the push method 
	 *
	 * @param cardNode
	 */
	public void push(CardNode cardNode) {
		cardPathCollection.add(cardNode);
	}
	/**
	 * 
	 * Document the pop method 
	 *
	 * @return CardNode
	 */
	public CardNode pop() {
		if (cardPathCollection.size() == 0) {
			return null;
		}
		return cardPathCollection.remove(cardPathCollection.size() - 1);
	}

	/**
	 * 
	 * Document the peek method 
	 *
	 * @return CardNode
	 */
	public CardNode peek() {
		return cardPathCollection.get(cardPathCollection.size());
	}

	/**
	 * 
	 * Document the getSize method 
	 *
	 * @return int
	 */
	public int getSize() {
		return cardPathCollection.size();
	}
	/**
	 * 
	 * Document the getCardPathCollection method 
	 *
	 * @return Collection
	 */
	public List<Card> getCardPathCollection() {
		List<Card> tempCardCollection = new ArrayList<Card>();
		for (CardNode cardNode: cardPathCollection) {
			tempCardCollection.add(cardNode.getCard());
		}
		return tempCardCollection;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		List<Card> cardCollection = getCardPathCollection();
		for (Card card: cardCollection) {
			if (sb.length() != 0) {
				sb.append(",");
			}
			sb.append(card.toString());
		}
		return sb.toString();
	}
}
