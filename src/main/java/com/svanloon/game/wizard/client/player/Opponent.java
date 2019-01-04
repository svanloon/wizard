package com.svanloon.game.wizard.client.player;

import com.svanloon.game.wizard.core.card.Card;
import com.svanloon.game.wizard.core.card.Suit;
import com.svanloon.game.wizard.core.card.Value;

/**
 * Document the  class 
 */
public class Opponent extends AbstractOpponent{

	private int numberOfTricksNeeded = 0;
	private boolean playedTrick = false;
	private boolean needToBid = false;
	private int score = 0;
	private int cardsRemaining = 0;

	/**
	 * Constructs a new <code>Opponent</code> object.
	 * @param id 
	 * @param name
	 */
	public Opponent(int id, String name) {
		setId(id);
		setName(name);
	}

	/**
	 * 
	 * Document the setCurrentRound method 
	 *
	 * @param round
	 */
	@Override
	public void setRound(int round) {
		super.setRound(round);
		this.playedTrick = false;
		setHearts(0);
		setClubs(0);
		setSpades(0);
		setDiamonds(0);
		setJesters(0);
		setWizards(0);
	}

	/**
	 * 
	 * Document the setBid method 
	 *
	 * @param bid
	 */
	public void setBid(int bid) {
		super.setBid(bid);
		this.numberOfTricksNeeded = bid;
		this.needToBid = false;
	}

	/**
	 * Document the tookTrick method 
	 */
	public void tookTrick() {
		this.numberOfTricksNeeded -= 1;
	}

	/**
	 * Returns the playedTrick.
	 *
	 * @return the playedTrick.
	 */
	public boolean isPlayedTrick() {
		return playedTrick;
	}

	/**
	 * Sets the playedTrick.
	 *
	 * @param playedTrick The new value for playedTrick.
	 */
	public void setPlayedTrick(boolean playedTrick) {
		this.playedTrick = playedTrick;
	}

	/**
	 * 
	 * Document the played method 
	 *
	 * @param lead
	 * @param highCard
	 * @param played
	 */
	public void played(Card lead, Card highCard, Card played) {
		Suit suit = played.getSuit();
		Value value = played.getValue();
		if(suit.equals(Suit.HEART)) {
			setHearts(getHearts() + 1);
		} else if(suit.equals(Suit.SPADE)) {
			setSpades(getSpades() + 1);
		} else if(suit.equals(Suit.CLUB)) {
			setClubs(getSpades() + 1);
		} else if(suit.equals(Suit.DIAMOND)) {
			setDiamonds(getDiamonds() + 1);
		} else if(value.equals(Value.WIZARD)) {
			setWizards(getWizards() + 1);
		} else if(value.equals(Value.JESTER)) {
			setJesters(getJesters() + 1);
		}
		if(suit.equals(Suit.NONE)) {
			return;
		}
		if(lead == null) {
			return;
		}
		Suit leadSuit = lead.getSuit();
		if(leadSuit.equals(Suit.NONE)) {
			return;
		}
		// -1 means out of that suit;
		if(suit.equals(leadSuit) == false) {
			if(leadSuit.equals(Suit.HEART)) {
				setHasHearts(false);
			} else if(leadSuit.equals(Suit.SPADE)) {
				setHasSpades(false);
			} else if(leadSuit.equals(Suit.CLUB)) {
				setHasClubs(false);
			} else if(leadSuit.equals(Suit.DIAMOND)) {
				setHasDiamonds(false);
			}
		}
	}

	/**
	 * Returns the needToBid.
	 *
	 * @return the needToBid.
	 */
	public boolean needsToBid() {
		return needToBid;
	}

	/**
	 * 
	 * Document the changeScore method 
	 *
	 * @param scoreChange
	 */
	public void changeScore(int scoreChange) {
		this.score += scoreChange;
	}

	/**
	 * 
	 * Document the getScore method 
	 *
	 * @return int
	 */
	public int getScore() {
		return score;
	}
	/**
	 * Returns the cardsRemaining.
	 *
	 * @return the cardsRemaining.
	 */
	public int getCardsRemaining() {
		return cardsRemaining;
	}

	/**
	 * Sets the cardsRemaining.
	 *
	 * @param cardsRemaining The new value for cardsRemaining.
	 */
	public void setCardsRemaining(int cardsRemaining) {
		this.cardsRemaining = cardsRemaining;
	}

	/**
	 * 
	 * Document the getNumberOfTricksNeeded method 
	 *
	 * @return int
	 */
	public int getNumberOfTricksNeeded() {
		return numberOfTricksNeeded;
	}

}
