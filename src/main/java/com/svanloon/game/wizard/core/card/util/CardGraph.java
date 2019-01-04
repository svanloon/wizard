package com.svanloon.game.wizard.core.card.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.svanloon.game.wizard.core.card.Card;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class CardGraph {

	private HashMap<Card, CardNode> cardNodeHashMapByCard = new HashMap<Card, CardNode>();

	private PointCalculator pc;
	/**
	 * 
	 * Constructs a new <code>CardTree</code> object.
	 * Should use createTree method instead. 
	 * @param pc 
	 *
	 */
	private CardGraph(PointCalculator pc) {
		super();
		this.pc = pc;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		Set keySet = cardNodeHashMapByCard.keySet();
		for (Iterator keySetIterator = keySet.iterator(); keySetIterator.hasNext();) {
			Card card = (Card) keySetIterator.next();
			CardNode cardNode = cardNodeHashMapByCard.get(card);
			sb.append("[{");
			sb.append(card.toString());
			sb.append("}, {");
			if (cardNode.getLighterNodeCollection() != null) {
				for (Iterator cardNodeIterator = cardNode.getLighterNodeCollection().iterator(); cardNodeIterator.hasNext();) {
					CardNode lessCardNode = (CardNode) cardNodeIterator.next();
					sb.append(lessCardNode.getCard().toString());
					if (cardNodeIterator.hasNext()) {
						sb.append(", ");
					}
				}
			}
			sb.append("}, {");
			if (cardNode.getHeavierNodeCollection() != null) {
				for (Iterator cardNodeIterator = cardNode.getHeavierNodeCollection().iterator(); cardNodeIterator.hasNext();) {
					CardNode heavierCardNode = (CardNode) cardNodeIterator.next();
					sb.append(heavierCardNode.getCard().toString());
					if (cardNodeIterator.hasNext()) {
						sb.append(", ");
					}
				}
			}
			sb.append("}] ");
		}
		return sb.toString();
	}
	/**
	 * 
	 * Document the createTree method 
	 *
	 * @param cardCollection
	 * @param pc
	 * @return CardTree
	 */
	public static CardGraph createTree(PointCalculator pc, List<Card> cardCollection) {
		CardGraph cardTree = new CardGraph(pc);
		for (Card card: cardCollection) {
			cardTree.addCard(card);
		}
		return cardTree;
	}

	/**
	 * 
	 * Document the addCard method 
	 *
	 * @param card
	 */
	public void addCard(Card card) {
		if (findCardNode(card) != null) {
			return;
		}

		CardNode cardNode = new CardNode(card);

		Set keySet = cardNodeHashMapByCard.keySet();
		for (Iterator keySetIterator = keySet.iterator(); keySetIterator.hasNext();) {
			Card indexCardKey = (Card) keySetIterator.next();

			CardNode indexedNode = cardNodeHashMapByCard.get(indexCardKey);
			int indexValue = pc.getIndex(indexCardKey.getValue());

			int cardValue = pc.getIndex(card.getValue());
			
			if ( indexValue + 1 == cardValue) {
				indexedNode.addHeavierNode(cardNode);
				cardNode.addLighterNode(indexedNode);
			} else if (indexValue - 1 == cardValue) {
				indexedNode.addLighterNode(cardNode);
				cardNode.addHeavierNode(indexedNode);
			}
		}
		cardNodeHashMapByCard.put(card, cardNode);
	}



	/**
	 * 
	 * Document the findCardNode method 
	 *
	 * @param card
	 * @return CardNode
	 */
	public CardNode findCardNode(Card card) {
		if (cardNodeHashMapByCard.containsKey(card) == false) {
			return null;
		}

		return cardNodeHashMapByCard.get(card);
	}

	/**
	 * straight is defined as a run of 3 or more
	 * @param cardCollection 
	 * @return  Collection
	 */
	public List<List<Card>> calcStraightCollections(Collection<Card> cardCollection) {
		List<List<Card>> straightCardCollection = new ArrayList<List<Card>>();
		CardPath cardPath = new CardPath();
		for (Card card: cardCollection) {
			CardNode cardNode = findCardNode(card);
			if (cardNode.getLighterNodeCollection().isEmpty()) {
				calcStraightCollectionForIndividualCardNode(cardNode, straightCardCollection, cardPath);
			}
		}
		if (straightCardCollection.isEmpty()) {
			return null;
		}
		return straightCardCollection;
	}

	/**
	 * the idea is that we create a tree and find all the different paths that
	 * are of greater than 3 that include end points, but not paths starting in
	 * the middle. for example, cards {4,2,5,3} has only one (weighted) path.
	 * the path is 2->3->4->5, where 2 and 5 are end points. 3,4,5 have nodes
	 * before it and can't be starting points. 2,3,4 have nodes after them, so
	 * they can't be ending points.
	 */
	private void calcStraightCollectionForIndividualCardNode(CardNode cardNode,	List<List<Card>> straightCardCollection, CardPath cardPath) {
		cardPath.push(cardNode);
		// if no potential next nodes...
		if (cardNode.getHeavierNodeCollection().isEmpty()) {
			// Debug.println(Debug.INFO, cardPath.toString());
			// add path to collection of paths
			if (cardPath.getSize() > 2) {
				straightCardCollection.add(cardPath.getCardPathCollection());
			}

			// pop path stack
			cardPath.pop();
			return;
		}

		for (CardNode heavierCardNode:  cardNode.getHeavierNodeCollection()) {
			calcStraightCollectionForIndividualCardNode(heavierCardNode, straightCardCollection, cardPath);
		}
		cardPath.pop();

	}
}
