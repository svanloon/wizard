package com.svanloon.game.wizard.core.card.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.svanloon.game.wizard.core.card.Card;
import com.svanloon.game.wizard.core.card.CardFinder;
import com.svanloon.game.wizard.core.card.Suit;
import com.svanloon.game.wizard.core.card.Value;
import com.svanloon.game.wizard.core.card.WizardCardFinder;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class WizardCardGraph implements Cloneable {

	private HashMap<Card, CardNode> cardNodeHashMapByCard = new HashMap<Card, CardNode>();
	private PointCalculator pc;

	/**
	 * 
	 * Constructs a new <code>WizardCardTree</code> object. 
	 *
	 * @param pc
	 */
	public WizardCardGraph(PointCalculator pc) {
		super();
		this.pc = pc;
		CardFinder cardFinder = new WizardCardFinder();
		for(int i = 0; i < cardFinder.getCardsInDeck(); i++) {
			addCard(cardFinder.findCard(i));	
		}
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
	 * 
	 * Document the findHigherCards method 
	 *
	 * @param card
	 * @return List
	 */
	public List<CardNode> findHigherCards(Card card) {
		CardNode cardNode = findCardNode(card);
		if(cardNode == null) {
			return new ArrayList<CardNode>();
		}
		
		List<CardNode> cardList = new ArrayList<CardNode>();
		for(CardNode heavierCardNode:cardNode.getHeavierNodeCollection()) {
			cardList.add(heavierCardNode);

			for(CardNode moreCardNode: findHigherCards(heavierCardNode.getCard())) {
				if(cardList.contains(moreCardNode) == false) {
					cardList.add(moreCardNode);
				}
			}
		}
		return cardList;
	}

	/**
	 * 
	 * Document the findHigherCards method 
	 *
	 * @param card
	 * @return List
	 */
	public List<CardNode> findImmediatelyLowerCards(Card card) {
		CardNode cardNode = findCardNode(card);
		if(cardNode == null) {
			return new ArrayList<CardNode>();
		}
		
		List<CardNode> cardList = new ArrayList<CardNode>();
		for(CardNode lighterCardNode:cardNode.getLighterNodeCollection()) {
			cardList.add(lighterCardNode);

			for(CardNode moreCardNode: findImmediatelyLowerCards(lighterCardNode.getCard())) {
				if(cardList.contains(moreCardNode) == false) {
					cardList.add(moreCardNode);
				}
			}
		}
		return cardList;
	}

	/**
	 * 
	 * Document the findHigherCards method 
	 *
	 * @param card
	 * @return List
	 */
	public List<CardNode> findAllLowerCards(Card card) {
		List<CardNode> higherCards = findHigherCards(card);
		
		List<CardNode> lowerCards = new ArrayList<CardNode>();

		for(CardNode cardNode :cardNodeHashMapByCard.values()) {
			if(higherCards.contains(cardNode) || cardNode.getCard().equals(card)) {
				continue;
			}
			lowerCards.add(cardNode);
		}

		return lowerCards;
	}

	/**
	 * 
	 * Document the removeCard method 
	 *
	 * @param card
	 */
	public void removeCard(Card card) {
		CardNode cardNode = findCardNode(card);
		if (cardNode == null) {
			return;
		}
		
		cardNodeHashMapByCard.remove(card);

		for(CardNode lighterNode: cardNode.getLighterNodeCollection()) {
			lighterNode.getHeavierNodeCollection().remove(cardNode);
			for(CardNode heavierNode: cardNode.getHeavierNodeCollection()) {
				heavierNode.getLighterNodeCollection().remove(cardNode);
				heavierNode.addLighterNode(lighterNode);
				lighterNode.addHeavierNode(heavierNode);
			}
		}

		// this is done just in case there aren't any lighter nodes.
		for(CardNode heavierNode: cardNode.getHeavierNodeCollection()) {
			heavierNode.getLighterNodeCollection().remove(cardNode);
		}

		cardNode.getHeavierNodeCollection().clear();
		cardNode.getLighterNodeCollection().clear();
	}

	private void addCard(Card card) {
		if (findCardNode(card) != null) {
			return;
		}

		CardNode cardNode = new CardNode(card);
		for (Card indexedCard:cardNodeHashMapByCard.keySet()) {
			CardNode indexedCardNode = cardNodeHashMapByCard.get(indexedCard);
			int indexedValue = pc.getIndex(indexedCard.getValue());
			int cardValue = pc.getIndex(card.getValue());

			if(indexedCard.getSuit().equals(card.getSuit())) {
				if (indexedValue + 1 == cardValue) {
					indexedCardNode.addHeavierNode(cardNode);
					cardNode.addLighterNode(indexedCardNode);
				} else if (cardValue + 1 == indexedValue) {
					cardNode.addHeavierNode(indexedCardNode);
					indexedCardNode.addLighterNode(cardNode);
				}
			}
		}
		cardNodeHashMapByCard.put(card, cardNode);
	}

	/**
	 * 
	 * Document the setTrump method 
	 *
	 * @param trumpSuit
	 */
	public void setTrump(Suit trumpSuit) {
		List<Card> cardList = findIsolates();
		Card heart = null;
		Card spade = null;
		Card diamond = null;
		Card club = null;

		List<CardNode> wizardCardNodes = new ArrayList<CardNode>();
		List<CardNode> jesterCardNodes = new ArrayList<CardNode>();

		for(Card card: cardList) {
			Suit suit = card.getSuit();
			if(suit.equals(Suit.HEART)) {
				heart = card;
			} else if(suit.equals(Suit.DIAMOND)) {
				diamond = card;
			} else if(suit.equals(Suit.CLUB)) {
				club = card;
			} else if(suit.equals(Suit.SPADE)) {
				spade = card;
			} else if (card.getValue().equals(Value.JESTER)) {
				jesterCardNodes.add(findCardNode(card));
			} else if (card.getValue().equals(Value.WIZARD)) {
				wizardCardNodes.add(findCardNode(card));
			}
		}


		CardNode heartCardNode = findCardNode(heart);
		CardNode clubCardNode = findCardNode(club);
		CardNode diamondCardNode = findCardNode(diamond);
		CardNode spadeCardNode = findCardNode(spade);

		CardNode bottomHeart = findBottom(heartCardNode);
		CardNode bottomClub = findBottom(clubCardNode);
		CardNode bottomDiamond = findBottom(diamondCardNode);
		CardNode bottomSpade = findBottom(spadeCardNode);

		if(trumpSuit == null || trumpSuit.equals(Suit.NONE)) {
			// set wizards higher than all suits
			for(CardNode wizardCardNode: wizardCardNodes) {
				heartCardNode.addHeavierNode(wizardCardNode);
				wizardCardNode.addLighterNode(heartCardNode);
	
				clubCardNode.addHeavierNode(wizardCardNode);
				wizardCardNode.addLighterNode(clubCardNode);
	
				diamondCardNode.addHeavierNode(wizardCardNode);
				wizardCardNode.addLighterNode(diamondCardNode);
	
				spadeCardNode.addHeavierNode(wizardCardNode);
				wizardCardNode.addLighterNode(spadeCardNode);
			}

			// set jesters lower than all suits
			for(CardNode jesterCardNode: jesterCardNodes) {
				bottomHeart.addLighterNode(jesterCardNode);
				bottomClub.addLighterNode(jesterCardNode);
				bottomDiamond.addLighterNode(jesterCardNode);
				bottomSpade.addLighterNode(jesterCardNode);
				jesterCardNode.addHeavierNode(bottomHeart);
				jesterCardNode.addHeavierNode(bottomClub);
				jesterCardNode.addHeavierNode(bottomDiamond);
				jesterCardNode.addHeavierNode(bottomSpade);
			}
		} else {
			CardNode trump;
			if(trumpSuit.equals(Suit.HEART)) {
				trump = findBottom(heartCardNode);

				// set wizards higher than trump
				for(CardNode wizardCardNode: wizardCardNodes) {
					wizardCardNode.addLighterNode(heartCardNode);
					heartCardNode.addHeavierNode(wizardCardNode);
				}

				// set other suits lower than trump
				trump.addLighterNode(clubCardNode);
				clubCardNode.addHeavierNode(trump);

				trump.addLighterNode(spadeCardNode);
				spadeCardNode.addHeavierNode(trump);

				trump.addLighterNode(diamondCardNode);
				diamondCardNode.addHeavierNode(trump);

				// set jesters lower than other suits
				// set jesters lower than all suits
				for(CardNode jesterCardNode: jesterCardNodes) {
					bottomClub.addLighterNode(jesterCardNode);
					bottomDiamond.addLighterNode(jesterCardNode);
					bottomSpade.addLighterNode(jesterCardNode);
					jesterCardNode.addHeavierNode(bottomClub);
					jesterCardNode.addHeavierNode(bottomDiamond);
					jesterCardNode.addHeavierNode(bottomSpade);
				}

			} else if(trumpSuit.equals(Suit.DIAMOND)) {
				trump = findBottom(diamondCardNode);

				// set wizards higher than trump
				for(CardNode wizardCardNode: wizardCardNodes) {
					wizardCardNode.addLighterNode(diamondCardNode);
					diamondCardNode.addHeavierNode(wizardCardNode);
				}

				// set other suits lower than trump
				trump.addLighterNode(clubCardNode);
				clubCardNode.addHeavierNode(trump);

				trump.addLighterNode(spadeCardNode);
				spadeCardNode.addHeavierNode(trump);

				trump.addLighterNode(heartCardNode);
				heartCardNode.addHeavierNode(trump);

				// set jesters lower than other suits
				for(CardNode jesterCardNode: jesterCardNodes) {
					bottomClub.addLighterNode(jesterCardNode);
					bottomHeart.addLighterNode(jesterCardNode);
					bottomSpade.addLighterNode(jesterCardNode);
					jesterCardNode.addHeavierNode(bottomHeart);
					jesterCardNode.addHeavierNode(bottomClub);
					jesterCardNode.addHeavierNode(bottomSpade);
				}

			} else if(trumpSuit.equals(Suit.CLUB)) {
				trump = findBottom(clubCardNode);

				// set wizards higher than trump
				for(CardNode wizardCardNode: wizardCardNodes) {
					wizardCardNode.addLighterNode(clubCardNode);
					clubCardNode.addHeavierNode(wizardCardNode);
				}

				// set other suits lower than trump
				trump.addLighterNode(spadeCardNode);
				spadeCardNode.addHeavierNode(trump);

				trump.addLighterNode(diamondCardNode);
				diamondCardNode.addHeavierNode(trump);

				trump.addLighterNode(heartCardNode);
				heartCardNode.addHeavierNode(trump);

				// set jesters lower than other suits
				for(CardNode jesterCardNode: jesterCardNodes) {
					bottomDiamond.addLighterNode(jesterCardNode);
					bottomHeart.addLighterNode(jesterCardNode);
					bottomSpade.addLighterNode(jesterCardNode);
					jesterCardNode.addHeavierNode(bottomHeart);
					jesterCardNode.addHeavierNode(bottomDiamond);
					jesterCardNode.addHeavierNode(bottomSpade);
				}

			} else {
				//Suit.SPADES
				trump = findBottom(spadeCardNode);

				// set wizards higher than trump
				for(CardNode wizardCardNode: wizardCardNodes) {
					wizardCardNode.addLighterNode(spadeCardNode);
					spadeCardNode.addHeavierNode(wizardCardNode);
				}

				// set other suits lower than trump
				trump.addLighterNode(clubCardNode);
				clubCardNode.addHeavierNode(trump);


				trump.addLighterNode(diamondCardNode);
				diamondCardNode.addHeavierNode(trump);

				trump.addLighterNode(heartCardNode);
				heartCardNode.addHeavierNode(trump);

				// set jesters lower than other suits
				for(CardNode jesterCardNode: jesterCardNodes) {
					bottomDiamond.addLighterNode(jesterCardNode);
					bottomHeart.addLighterNode(jesterCardNode);
					bottomClub.addLighterNode(jesterCardNode);
					jesterCardNode.addHeavierNode(bottomHeart);
					jesterCardNode.addHeavierNode(bottomClub);
					jesterCardNode.addHeavierNode(bottomDiamond);
				}
			}	
		}
	}

	/**
	 * 
	 * This method should only be used internal to find the lowest value while setTrump is being called. 
	 *
	 * @param cardNode
	 * @return CardNode
	 */
	private CardNode findBottom(CardNode cardNode) {
		if(cardNode.getLighterNodeCollection().isEmpty() == false) {
			return findBottom(cardNode.getLighterNodeCollection().iterator().next());
		}
		return cardNode;
	}

	/**
	 * 
	 * Document the findIsolates method 
	 *
	 * @return List
	 */
	public List<Card> findIsolates() {
		List<Card> cards = new ArrayList<Card>();
		for(Card key:cardNodeHashMapByCard.keySet()) {
			CardNode cardNode = cardNodeHashMapByCard.get(key);
			if(cardNode.getHeavierNodeCollection().isEmpty() == false) {
				continue;
			}
			cards.add(cardNode.getCard());
		}
		return cards;
	}
}
