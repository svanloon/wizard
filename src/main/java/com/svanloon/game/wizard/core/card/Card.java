package com.svanloon.game.wizard.core.card;


/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class Card implements Cloneable {
	private Suit suit;
	private Value value;
	private int index;

	/**
	 * 
	 * Constructs a new <code>Card</code> object. 
	 *
	 * @param value
	 * @param suit
	 * @param index 
	 */
	public Card(Value value, Suit suit) {
		if (suit == null) {
			throw new IllegalArgumentException("suit == null");
		}
		this.value = value;
		this.suit = suit;
		this.index = -1;
	}

	/**
	 * 
	 * Constructs a new <code>Card</code> object. 
	 *
	 * @param value
	 * @param suit
	 * @param index 
	 */
	public Card(Value value, Suit suit, int index) {
		if (suit == null) {
			throw new IllegalArgumentException("suit == null");
		}
		this.value = value;
		this.suit = suit;
		this.index = index;
	}

	/**
	 * 
	 * Document the getPoint method 
	 *
	 * @return int
	 */
	public int getPoint() {
		return this.value.getIndex();
	}

	/**
	 * 
	 * Document the getValue method 
	 *
	 * @return Value
	 */
	public final Value getValue() {
		return this.value;
	}

	
	/**
	 * 
	 * Document the isGreater method 
	 *
	 * @param v
	 * @return boolean
	 */
	public final boolean isGreater(Value v) { 
		return this.value != null && this.value.getIndex() > v.getIndex();
	}
	
	/**
	 * 
	 * Document the isTenCard method 
	 *
	 * @return boolean
	 */
	public final boolean isTenCard() {
		return this.value.getIndex() > Value.NINE.getIndex();
	}

	/**
	 * 
	 * Document the isHeart method 
	 *
	 * @return boolean
	 */
	public final boolean isHeart() {
		return Suit.HEART.equals(getSuit());
	}

	/**
	 * 
	 * Document the isSpade method 
	 *
	 * @return boolean
	 */
	public final boolean isSpade() {
		return Suit.SPADE.equals(getSuit());
	}

	/**
	 * 
	 * Document the isDiamond method 
	 *
	 * @return boolean
	 */
	public final boolean isDiamond() {
		return Suit.DIAMOND.equals(getSuit());
	}

	/**
	 * 
	 * Document the isClub method 
	 *
	 * @return boolean
	 */
	public final boolean isClub() {
		return Suit.CLUB.equals(getSuit());
	}

	/**
	 * 
	 * Document the isNone method 
	 *
	 * @return boolean
	 */
	public final boolean isNone() {
		return Suit.NONE.equals(getSuit());
	}

	/**
	 * 
	 * Document the isWizard method 
	 *
	 * @return boolean
	 */
	public final boolean isWizard() {
		return this.getValue() != null && Value.WIZARD.getIndex() == this.getValue().getIndex();
	}

	/**
	 * 
	 * Document the isJester method 
	 *
	 * @return boolean
	 */
	public final boolean isJester() {
		return this.getValue() != null && Value.JESTER.getIndex() == this.getValue().getIndex();
	}

	/**
	 * 
	 * Document the isSameSuit method 
	 *
	 * @param card
	 * @return boolean
	 */
	public final boolean isSameSuit(Card card) {
		return getSuit().equals(card.getSuit());
	}

	@Override
	public final String toString() {
		if(value != null) {
			return this.value.getShortNm() + getSuit().getShortName();
		}
		return getSuit().getShortName();
	}

	/**
	 * 
	 * Document the compareTo method 
	 *
	 * @param c2
	 * @return int
	 */
	public final int compareTo(Card c2) {
		if (value == null || c2.getValue() == null) {
			return 0;
		}
		Integer i1 = new Integer(this.value.getIndex());
		Integer i2 = new Integer(c2.getValue().getIndex());
		return i1.compareTo(i2);
	}

	/**
	 * 
	 * Document the getSuit method 
	 *
	 * @return Suit
	 */
	public Suit getSuit() {
		return this.suit;
	}



	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	@Override
	public int hashCode() {
		if(this.value == null) {
			return this.suit.hashCode();
		}
		return this.suit.hashCode() + this.value.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Card == false) {
			return false;
		}

		Card that = (Card) obj;

		// trump is is sometimes just a suit without a value
		if (this.value == null || that.getValue() == null) {
			return false;
		}

		// when a deck is used without index, then the values should be less than 0
		if(this.index < 0 || that.index < 0) {
			return this.value.equals(that.getValue()) && this.suit != null && this.suit.equals(that.getSuit());			
		}

		return this.value.equals(that.getValue()) && this.suit != null && this.suit.equals(that.getSuit()) && this.index == that.index;
		
	}
}