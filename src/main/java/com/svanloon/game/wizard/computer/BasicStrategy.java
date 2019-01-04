package com.svanloon.game.wizard.computer;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.svanloon.game.wizard.client.player.Opponent;
import com.svanloon.game.wizard.client.player.temp.OpponentSummary;
import com.svanloon.game.wizard.core.card.Card;
import com.svanloon.game.wizard.core.card.Hand;
import com.svanloon.game.wizard.core.card.Suit;
import com.svanloon.game.wizard.core.card.Value;
import com.svanloon.game.wizard.core.card.util.WizardCardGraph;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class BasicStrategy {
	private Logger _logger = Logger.getLogger(BasicStrategy.class);
	private Hand hand;
	private boolean canTakeTrick = false;
	private OpponentSummary os;
	private List<Card> higherCardOfSuitLead;
	private List<Card> lowerCardOfSuitLead;
	private List<Card> trumpCards;
	private List<Card> nonTrumpAndNotSuitLeadCards;
	private List<Card> aces;
	private List<Card> kings;
	private List<Card> queens;
	private List<Card> jacks;
	private Card lead;
	private Card highCard;
	private int id;
	
	/**
	 * Constructs a new <code>HandStatistics</code> object. 
	 * @param hand
	 * @param trump 
	 */
	public BasicStrategy(Hand hand) {
		super();
		this.hand = hand;
	}

	/**
	 * 
	 * Document the setOpponentSummary method 
	 *
	 * @param os
	 */
	public void setOpponentSummary(OpponentSummary os) {
		this.os = os;
	}
	/**
	 * 
	 * Document the pickTrump method 
	 *
	 * @return Suit
	 */
	public Suit pickTrump() {

		int heartCount = hand.getHeartList().size();
		int spadeCount = hand.getSpadeList().size();
		int clubCount = hand.getClubList().size();
		int diamondCount = hand.getDiamondList().size();

		int heartStrength = 0;
		int spadeStrength = 0;
		int diamondStrength = 0;
		int clubStrength = 0;

		for (Card card:this.hand) {
			Suit suit = card.getSuit();
			Value value = card.getValue();
			if(suit.equals(Suit.NONE)) {
				// nothing
			} else if(suit.equals(Suit.HEART)) {
				heartStrength += value.getIndex();
			} else if(suit.equals(Suit.SPADE)) {
				spadeStrength += value.getIndex();
			} else if(suit.equals(Suit.DIAMOND)) {
				diamondStrength += value.getIndex();
			} else if(suit.equals(Suit.CLUB)) {
				clubStrength += value.getIndex();
			}
		}

		if(heartCount >= spadeCount && heartCount >= clubCount && heartCount >= diamondCount) {
			if(heartStrength >= spadeStrength && heartStrength >= clubStrength && heartStrength >= diamondStrength) {
				return Suit.HEART;
			}
			if(spadeStrength >= clubStrength && spadeStrength >= diamondStrength) {
				return Suit.SPADE;
			}
			if(clubStrength >= diamondStrength) {
				return Suit.CLUB;
			}

			return Suit.DIAMOND;
		}

		if(spadeCount >= clubCount && spadeCount >= diamondCount) {
			if(spadeStrength >= clubStrength && spadeStrength >= diamondStrength) {
				return Suit.SPADE;
			}
			if(clubStrength >= diamondStrength) {
				return Suit.CLUB;
			}

			return Suit.DIAMOND;
		}

		if(clubCount >= diamondCount) {
			if(clubStrength >= diamondStrength) {
				return Suit.CLUB;
			}

			return Suit.DIAMOND;
		}

		return Suit.DIAMOND;
	}

	/**
	 * 
	 * Document the bid method 
	 *
	 * @param trump
	 * @param min 
	 * @param max 
	 * @param bidNotAllowed 
	 * @return int
	 */
	public int bid(Card trump, int min, int max, int bidNotAllowed) {
		categorizePossibilities();
		int bid;
		if(trump != null && trump.getSuit().equals(Suit.NONE) == false) {
			 bid =(int)( aces.size()/2.0 + kings.size()/4.0  + /*queens.size() + jacks.size()*/ + trumpCards.size() + hand.getWizardList().size());
			//_logger.info(id + " bid " + bid);
		} else {
			bid =(int)( aces.size() + kings.size() + queens.size()/2.0 + jacks.size()/2.0 + hand.getWizardList().size());				
		}
		if(bid == bidNotAllowed) {
			int oneLower = bid - 1;
			int oneHigher= bid + 1;
			if(oneLower  <= max && oneLower >= min) {
				return oneLower;
			} else if(oneHigher  <= max && oneHigher >= min) {
				return oneHigher;
			}
			// forced to bid something else
			for(int i = min; i < max + 1; i++) {
				if(i  <= max && i >= min && i != bidNotAllowed) {
					return i;
				}
			}
		}
		return bid;  
	}
	/**
	 * 
	 * Document the playCard method 
	 * @param wcg 
	 *
	 * @return Card
	 */
	public Card playCard(WizardCardGraph wcg) {
		Card lead2 = os.getLead();
		StringBuilder debug = new StringBuilder();
		debug.append(id + " [lead = " + lead2 + "]");

		setLeadAndHighCard(lead2, os.getHighCard());

		if(hand.size() == 1) {
			return hand.iterator().next();
		}

		Card card;
		if(desireToTakeTrick() > 0) {
			if(lead2 == null) {
				card = leadNormal();
			} else if(canTakeTrick) {
				if(desireToTakeTrick() < 30) {
					debug.append(" take Trick High");
					card = takeTrickHigh();
				} else {
					card = takeTrickMedium();
				}
			} else {
				debug.append(" take sluff low");
				card = sluffLow();
			}
		} else {
			if(lead2 == null) {
				debug.append(" take sluff low");
				card = sluffLow();
			} else {
				debug.append(" take sluff High");
				card = sluffHigh();				
			}
		}
		debug.append(" = " + card.toString());
		
		if(false) {
			_logger.info(debug.toString());
		}
		return card;
	}

	/**
	 * Sets the lead.
	 *
	 * @param lead The new value for lead.
	 * @param highCard 
	 */
	private void setLeadAndHighCard(Card lead, Card highCard) {
		this.lead = lead;
		this.highCard = highCard;
		categorizePossibilities();
		setCanTakeTrick();
	}

	private void setCanTakeTrick() {
		canTakeTrick = false;
		if(lead == null) {
			canTakeTrick = true;
			return;
		}

		if(highCard == null) {
			canTakeTrick = true;
			return;
		}


		if(highCard.getValue().equals(Value.WIZARD)) {
			canTakeTrick = false;
			return;
		}

		if(hand.getWizardList().size() > 0) {
			canTakeTrick = true;
			return;
		}

		// if the high card is trump and you have the lead suit, you can't take it.
		if(os.getTrump() != null && os.getTrump().getSuit().equals(Suit.NONE) && highCard.getSuit().equals(os.getTrump().getSuit()) && higherCardOfSuitLead.isEmpty() == false && lowerCardOfSuitLead.isEmpty() == false) {
			canTakeTrick = false;
			return;
		}

		if(higherCardOfSuitLead.isEmpty() && lowerCardOfSuitLead.isEmpty() && trumpCards.isEmpty() == false) {
			canTakeTrick = true;
			return;
		}

		if(higherCardOfSuitLead.isEmpty() == false) {
			canTakeTrick = true;
			return;
		}

	}

	private void categorizePossibilities() {
		higherCardOfSuitLead = new ArrayList<Card>();
		lowerCardOfSuitLead = new ArrayList<Card>();
		trumpCards = new ArrayList<Card>();
		nonTrumpAndNotSuitLeadCards = new ArrayList<Card>();
		aces = new ArrayList<Card>();
		kings = new ArrayList<Card>();
		queens = new ArrayList<Card>();
		jacks = new ArrayList<Card>();

		if(lead != null) {
			for(Card card:hand) {
				if(card.getSuit().equals(lead.getSuit())) {
					if(card.getValue().getIndex() > lead.getValue().getIndex()) {
						higherCardOfSuitLead.add(card);
					} else {
						lowerCardOfSuitLead.add(card);
					}
				}
			}
		}

		for(Card card:hand) {
			if(card.getSuit().equals(Suit.NONE)) {
				continue;
			}

			if(os.getTrump() != null && os.getTrump().getSuit().equals(Suit.NONE) == false && card.getSuit().equals(os.getTrump().getSuit())) {
				trumpCards.add(card);
			} else {
				if(card.getValue().equals(Value.ACE)) {
					aces.add(card);
				} else if(card.getValue().equals(Value.KING)) {
					kings.add(card);
				} else if(card.getValue().equals(Value.QUEEN)) {
					queens.add(card);
				} else if(card.getValue().equals(Value.JACK)) {
					jacks.add(card);
				}


				if(lead == null) {
					nonTrumpAndNotSuitLeadCards.add(card);
				} else {
					if(lead.getSuit().equals(card.getSuit()) == false) {
						nonTrumpAndNotSuitLeadCards.add(card);
					}
				}
			}
		}

	}
	private Card leadNormal() {
		//TODO check for short suiting

		List<Card> nonTrump = nonTrumpAndNotSuitLeadCards;
		if(nonTrump.isEmpty() == false) {
			return findHighest(nonTrump);
		}

		if(trumpCards.isEmpty() == false) {
			return trumpCards.iterator().next();
		}

		List<Card> wizardCards = hand.getWizardList();
		if(wizardCards.isEmpty() == false) {
			return wizardCards.iterator().next();
		}

		return hand.iterator().next();
	}

	private Card takeTrickMedium() {

		if(higherCardOfSuitLead.isEmpty() == false) {
			return findHighest(higherCardOfSuitLead);
		}

		List<Card> wizardCards = hand.getWizardList();
		if(wizardCards.isEmpty() == false) {
			return wizardCards.iterator().next();
		}

		if(lowerCardOfSuitLead.isEmpty() == false) {
			return findLowest(lowerCardOfSuitLead);
		}

		if(trumpCards.isEmpty() == false) {
			return findHighest(trumpCards);
		}

		return hand.iterator().next();
	}

	private Card takeTrickHigh() {
		List<Card> wizardCards = hand.getWizardList();
		if(wizardCards.isEmpty() == false) {
			return wizardCards.iterator().next();
		}

		if(higherCardOfSuitLead.isEmpty() == false) {
			return findHighest(higherCardOfSuitLead);
		}

		if(lowerCardOfSuitLead.isEmpty() == false) {
			return findLowest(lowerCardOfSuitLead);
		}

		if(trumpCards.isEmpty() == false) {
			return findHighest(trumpCards);
		}

		return hand.iterator().next();
	}

	private Card findHighest(List<Card> cards) {
		Card highest = null;
		for(Card card: cards) {
			if(highest == null) {
				highest = card;
				continue;
			}
			if(highest.getValue().getIndex() < card.getValue().getIndex()) {
				highest = card;
			}
		}
		return highest;
	}

	private Card findLowest(List<Card> cards) {
		Card lowest = null;
		for(Card card: cards) {
			if(lowest == null) {
				lowest = card;
				continue;
			}
			if(lowest.getValue().getIndex() > card.getValue().getIndex()) {
				lowest = card;
			}
		}
		return lowest;
	}

	private Card sluffLow() {
		//TODO check for short suiting

		List<Card> jesterCards = hand.getJesterList();
		if(jesterCards.isEmpty() == false) {
			return jesterCards.iterator().next();
		}

		if(lowerCardOfSuitLead.isEmpty() == false) {
			return findLowest(lowerCardOfSuitLead);
		}

		if(higherCardOfSuitLead.isEmpty() == false) {
			return findLowest(higherCardOfSuitLead);
		}

		if(nonTrumpAndNotSuitLeadCards.isEmpty() == false) {
			return findLowest(nonTrumpAndNotSuitLeadCards);
		}

		if(trumpCards.isEmpty() == false) {
			return findLowest(trumpCards);
		}

		List<Card> wizardCards = hand.getWizardList();
		if(wizardCards.isEmpty() == false) {
			return wizardCards.iterator().next();
		}

		return hand.iterator().next();
	}

	private Card sluffHigh() {
		// TODO check for short suiting

		if(lowerCardOfSuitLead.isEmpty() == false) {
			return findHighest(lowerCardOfSuitLead);
		}

		if(higherCardOfSuitLead.isEmpty() == false) {
			return findHighest(higherCardOfSuitLead);
		}

		if(nonTrumpAndNotSuitLeadCards.isEmpty() == false) {
			return findHighest(nonTrumpAndNotSuitLeadCards);
		}

		if(trumpCards.isEmpty() == false) {
			return findHighest(trumpCards);
		}

		List<Card> wizardCards = hand.getWizardList();
		if(wizardCards.isEmpty() == false) {
			return wizardCards.iterator().next();
		}

		List<Card> jesterCards = hand.getJesterList();
		if(jesterCards.isEmpty() == false) {
			return jesterCards.iterator().next();
		}

		return hand.iterator().next();
	}

	private int desireToTakeTrick() {
		Opponent opponent = os.findOpponent(id);
		int needed = Math.max(opponent.getNumberOfTricksNeeded(), 0);
		int round = opponent.getRound() + 1;
		int remaining = opponent.getCardsRemaining();
		if(false) {
			_logger.info(id + "[need "+needed+"], [round "+round+"], [remaining "+remaining+"]");
		}
		return needed*(round-remaining);

	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
}