package com.svanloon.game.wizard.core.card;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class Deck {

	private CardFinder cardFinder = null;

	private static Logger _logger = Logger.getLogger(Deck.class);

	private int CARDS_IN_DECK;

	private List<Card> unshuffledDeck = new ArrayList<Card>();

	private List<Card> shuffledDeck = new ArrayList<Card>();

	private List<Card> notInDeck = new ArrayList<Card>();

	private static Long randomSeed = null;
	private static Random rnd = null;
	
	/**
	 * This gets a deck of 60 cards and shuffles it.
	 */
	public Deck() {
		CARDS_IN_DECK = getCardFinder().getCardsInDeck();
		for (int i = 0; i < this.CARDS_IN_DECK; i++) {
			this.unshuffledDeck.add(getCardFinder().findCard(i));
		}
	}

	/**
	 * 
	 * Constructs a new <code>Deck</code> object. 
	 *
	 * @param cardFinder
	 */
	public Deck(CardFinder cardFinder) {
		setCardFinder(cardFinder);
		CARDS_IN_DECK = getCardFinder().getCardsInDeck();
		for (int i = 0; i < this.CARDS_IN_DECK; i++) {
			this.unshuffledDeck.add(getCardFinder().findCard(i));
		}		
	}

	/**
	 * Puts the cards into deckArray.
	 */
	public final void shuffle() {
		if (rnd == null) {
			rnd = new Random();
			if (randomSeed != null){
				rnd.setSeed(randomSeed.longValue());
			}
		}
		
		for (int i = 0; i < this.CARDS_IN_DECK; i++) {
			int randomCardNumber = (int) (rnd.nextDouble() * this.unshuffledDeck.size());
			this.shuffledDeck.add(this.unshuffledDeck.get(randomCardNumber));
			this.unshuffledDeck.remove(randomCardNumber);
		}
	}

	/**
	 * Grabs a card from the top of the deck.
	 * @return Card
	 */
	public final Card getCard() {
		Card card = this.shuffledDeck.get(0);
		this.notInDeck.add(card);
		this.shuffledDeck.remove(0);
		return card;
	}

	/**
	 * Answers the question "Are there more cards left in the deck"
	 * @return boolean
	 */
	public final boolean hasCards() {
		return this.shuffledDeck.isEmpty() == false;
	}

	/**
	 * returns the number of cards that haven't been dealt out.
	 * @return int
	 */
	public final int getCardsLeft() {
		return this.shuffledDeck.size();
	}

	/**
	 * 
	 * Document the returnCard method 
	 *
	 * @param card
	 */
	public final void returnCard(Card card) {
		if (this.notInDeck.contains(card) == false) {
			_logger.warn("card " + card + " is not out");
		}

		this.notInDeck.remove(card);
		this.unshuffledDeck.add(card);
	}

	/**
	 * 
	 * Document the main method 
	 *
	 * @param args
	 */
	public static void main(String args[]) {
		Deck.setRandomSeed(Long.valueOf(187));
		for (int j = 0; j < 10; j++) {
			Deck deck = new Deck(new WizardCardFinder());
			deck.shuffle();
			for (int i = 0; i < deck.CARDS_IN_DECK; i++) {
				System.out.printf("%4s", deck.getCard());
			}
			System.out.println("");
		}
	}

	/**
	 * Set this if you want a constant deck.
	 * 
	 * @param pRandomSeed
	 */
	public static void setRandomSeed(Long pRandomSeed) {
		randomSeed = pRandomSeed;
	}
	/**
	 * 
	 * Document the setCardFinder method 
	 *
	 * @param cardFinder
	 */
	public void setCardFinder(CardFinder cardFinder) {
		this.cardFinder = cardFinder;
	}

	private CardFinder getCardFinder() {
		if(cardFinder == null) {
			cardFinder = new WizardCardFinder();
		}
		return cardFinder;
	}

}