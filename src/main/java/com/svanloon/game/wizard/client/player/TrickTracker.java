package com.svanloon.game.wizard.client.player;

import java.util.ArrayList;
import java.util.List;

import com.svanloon.game.wizard.core.card.Card;
import com.svanloon.game.wizard.core.card.Suit;
import com.svanloon.game.wizard.core.card.Value;
import com.svanloon.game.wizard.stats.Play;


/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class TrickTracker {

	private Card trump;

	private List<Play> playCollection = new ArrayList<Play>();
	
	private Play highPlay = null;
	
	/**
	 * 
	 * Constructs a new <code>TrickTracker</code> object. 
	 *
	 * @param trump
	 */
	public TrickTracker(Card trump) {
		this.trump = trump;
	}

	/**
	 * 
	 * Document the isLead method 
	 *
	 * @return boolean
	 */
	public boolean isLead() {
		return playCollection.isEmpty();
	}

	/**
	 * 
	 * Document the addCardPlayed method 
	 *
	 * @param playerId
	 * @param card
	 */
	public void addCardPlayed(int playerId, Card card) {
		Play play = new Play(playerId, card);
		this.playCollection.add(play);
		setHighPlay(play);
	}
	
	/**
	 * 
	 * Only sets the card if the incoming card is higher than the current highest play. 
	 *
	 */
	private void setHighPlay(Play play) {
		
		// if nothing is high yet, then it's high.
		if (highPlay == null) {
			highPlay = play;
		// if the high card is a jester and the card played is a jester, then the first jester takes it.
		} else if (highPlay.getCard().getValue().equals(Value.JESTER)
				&& play.getCard().getValue().equals(Value.JESTER) == false) {
			highPlay = play;
		// if the high card is the same suit as the card played, then the higher value is the high play.
		} else if (play.getCard().getSuit().equals(highPlay.getCard().getSuit())
					&& play.getCard().getValue().getIndex() > highPlay.getCard().getValue().getIndex()) {
			highPlay = play;
		// if the high card isn't trump and the person played trump, then trump wins.
		} else if (this.trump != null && trump.getSuit().equals(Suit.NONE) == false
				&& play.getCard().getSuit().equals(this.trump.getSuit())
				&& highPlay.getCard().getValue().equals(Value.WIZARD) == false
				&& highPlay.getCard().getSuit().equals(this.trump.getSuit()) == false) {
			highPlay = play;
		// if the high card is not a wizard and someone played a wizard, then they win.
		} else if (highPlay.getCard().getValue().equals(Value.WIZARD) == false
				&& play.getCard().getValue().equals(Value.WIZARD)) {
			highPlay = play;
		}
	}

	/**
	 * 
	 * Document the winningPlay method 
	 *
	 * @return Play
	 */
	public Play winningPlay() {
		return highPlay;
	}
	
	/**
	 * returns the playCollection.
	 *
	 * @return the playCollection.
	 */
	public List<Play> getPlayCollection() {
		return playCollection;
	}
}