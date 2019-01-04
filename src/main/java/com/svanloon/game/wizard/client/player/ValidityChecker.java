package com.svanloon.game.wizard.client.player;

import org.apache.log4j.Logger;

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
public class ValidityChecker {
	private static Logger _logger = Logger.getLogger(ValidityChecker.class);
	/**
	 * 
	 * Document the checkValidity method 
	 *
	 * @param trickTracker
	 * @param trump
	 * @param attemptedToPlayCard
	 * @param player
	 * @return boolean
	 */
	@SuppressWarnings("unused")
	public static boolean checkValidity(TrickTracker trickTracker, Card trump, Card attemptedToPlayCard, Player player) {
		// played a wizard or jester
		if(attemptedToPlayCard.getSuit().equals(Suit.NONE)) {
			return true;
		}

		// no cards have been played yet, so anything is available.
		if(trickTracker.isLead()) {
			return true;
		}

		Card lead = null;
		for(Play play : trickTracker.getPlayCollection() ) {
			Card temp = play.getCard();
			if(temp.getSuit().equals(Suit.NONE)) {
				if(temp.getValue().equals(Value.JESTER)) {
					// jester played, so find the next non-jester for what's lead.
					continue;
				}
				// wizard lead so anything can be played
				return true; 
			}
			lead = temp;
			break;
		}

		// only jesters have been led so far, so anything is allowed.
		if(lead == null) {
			return true;
		}

		// if you played the lead suit, that's fine.
		if(attemptedToPlayCard.getSuit().equals(lead.getSuit())) {
			return true;
		}

		for(Card otherCard:player.getHand()) {
			if(otherCard.getSuit().equals(lead.getSuit())) {
				// has a card of the lead suit, but didn't play it and the card they played wasn't a jester or wizard.
				_logger.info(player.getName() + " tried to play a " + attemptedToPlayCard + " when " + lead + " was lead and they have a " + otherCard);
				return false;
			}
		}

		return true;
	}

}
