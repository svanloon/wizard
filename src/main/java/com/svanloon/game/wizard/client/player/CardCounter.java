package com.svanloon.game.wizard.client.player;

import com.svanloon.game.wizard.core.card.Card;
import com.svanloon.game.wizard.core.card.Suit;
import com.svanloon.game.wizard.core.card.Value;

/**
 * @author Administrator
 */
public class CardCounter {
	private int[] hearts = new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1};
	private int[] clubs = new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1};
	private int[] diamonds = new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1};
	private int[] spades = new int[]{1,1,1,1,1,1,1,1,1,1,1,1,1};
	private int wizard = 4;
	private int jester = 4;
	private int cardsDealt = 0;
	private int cardsNotDealt = 60;
	private int cardsPlayed = 0;
	private int cardsRemainingInHands = 0;

	/**
	 * 
	 */
	public CardCounter() {
		super();
	}

	/**
	 * 
	 * Document the init method 
	 *
	 * @param numberOfPlayers
	 * @param cardsPerPlayer
	 */
	public void init(int numberOfPlayers, int cardsPerPlayer){
		cardsDealt = numberOfPlayers * cardsPerPlayer;
		cardsNotDealt = cardsNotDealt - cardsDealt;
		cardsRemainingInHands = cardsDealt;
	}

	/**
	 * 
	 * Document the setTrump method 
	 *
	 * @param card
	 */
	public void setTrump(Card card) {
		if(card == null) {
			return;
		}
		Value value = card.getValue();
		if(value == null) {
			return;
		}
		countCard(card);
		cardsDealt++;
		cardsNotDealt--;
		// undo the count for player played
		cardsRemainingInHands++;
		cardsPlayed--;
	}
	
	/**
	 * 
	 * Document the countCard method 
	 *
	 * @param card
	 */
	public void countCard(Card card) {
		Suit suit = card.getSuit();
		Value value = card.getValue();
		int index = findCardIndex(value);
		if(suit.equals(Suit.HEART)) {
			hearts[index] = 0;
		} else if(suit.equals(Suit.CLUB)) {
			clubs[index] = 0;
		} else if(suit.equals(Suit.SPADE)) {
			spades[index] = 0;
		} else if(suit.equals(Suit.DIAMOND)) {
			diamonds[index] = 0;
		} else if(suit.equals(Suit.NONE)) {
			if(value.equals(Value.WIZARD)) {
				wizard -= 1;
			} else {
				jester -= 1;
			}
		}
		
		cardsPlayed++;
		cardsRemainingInHands--;
	}

	private int findCardIndex(Value value) {
		return value.getIndex() - 1;
	}

	/**
	 * @return the cardsDealt
	 */
	public int getCardsDealt() {
		return cardsDealt;
	}

	/**
	 * @return the cardsNotDealt
	 */
	public int getCardsNotDealt() {
		return cardsNotDealt;
	}

	/**
	 * @return the cardsPlayed
	 */
	public int getCardsPlayed() {
		return cardsPlayed;
	}

	/**
	 * @return the cardsRemainingInHands
	 */
	public int getCardsRemainingInHands() {
		return cardsRemainingInHands;
	}

	/**
	 * @return the clubs
	 */
	public int[] getClubs() {
		return clubs;
	}

	/**
	 * @return the diamonds
	 */
	public int[] getDiamonds() {
		return diamonds;
	}

	/**
	 * @return the hearts
	 */
	public int[] getHearts() {
		return hearts;
	}

	/**
	 * @return the jester
	 */
	public int getJester() {
		return jester;
	}

	/**
	 * @return the spades
	 */
	public int[] getSpades() {
		return spades;
	}

	/**
	 * @return the wizard
	 */
	public int getWizard() {
		return wizard;
	}

	/**
	 * 
	 * Document the getHeartCount method 
	 *
	 * @return int
	 */
	public int getHeartCount(){
		return getCounter(hearts);
	}
	/**
	 * 
	 * Document the getDiamondCount method 
	 *
	 * @return int
	 */
	public int getDiamondCount(){
		return getCounter(diamonds);
	}

	/**
	 * 
	 * Document the getClubCount method 
	 *
	 * @return int
	 */
	public int getClubCount(){
		return getCounter(clubs);
	}

	/**
	 * 
	 * Document the getSpadeCount method 
	 *
	 * @return int
	 */
	public int getSpadeCount(){
		return getCounter(spades);
	}

	private int getCounter(int[] cards) {
		int i = 0;
		for(int j :cards) {
			i += j;
		}
		return i;
	}
}
