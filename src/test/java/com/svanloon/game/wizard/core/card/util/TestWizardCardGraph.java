package com.svanloon.game.wizard.core.card.util;

import com.svanloon.game.wizard.core.card.Card;
import com.svanloon.game.wizard.core.card.util.gui.CardGraphGui;
import java.awt.*;
import java.util.List;

import com.svanloon.game.wizard.core.card.Suit;
import com.svanloon.game.wizard.core.card.util.CardNode;
import com.svanloon.game.wizard.core.card.util.WizardCardGraph;
import javax.swing.*;
import junit.framework.TestCase;
import org.apache.log4j.Logger;


public class TestWizardCardGraph extends TestCase {

	private Logger _logger = Logger.getLogger(TestWizardCardGraph.class);

	public TestWizardCardGraph() {
		super();
	}

	/**
	 *
	 * Document the getFullWizardTree method
	 *
	 * @param trump
	 * @return WizardCardTree
	 */
	public WizardCardGraph getFullWizardTree(Suit trump) {
		WizardCardGraph wct = new WizardCardGraph(new WizardPoints());
		wct.setTrump(trump);
		return wct;
	}

	/**
	 *
	 * Document the getWizardCardTree method
	 * @param trump
	 * @param remainingCards
	 * @return WizardCardTree
	 */
	public WizardCardGraph getWizardCardTree(Suit trump, List<Card> remainingCards) {

		WizardCardGraph wct = getFullWizardTree(trump);
		for(Card card: WizardCardEnum.getValues()) {
			if(remainingCards.contains(card)) {
				continue;
			}
			wct.removeCard(card);
		}
		return wct;
	}

	/**
	 *
	 * Document the print method
	 *
	 * @param baseCardNode
	 */
	public void println(CardNode baseCardNode) {
		List<CardNode> cardNodeList = baseCardNode.getLighterNodeCollection();
		_logger.info(baseCardNode.getCard().toString());

		int i = 0;
		for(CardNode cardNode:cardNodeList) {
			if(i != 0) {
				_logger.info("*****");
			}
			println(cardNode);
			i++;
		}
	}

	/**
	 *
	 * Document the displayTree method
	 *
	 * @param wct
	 */
	public void displayGui(WizardCardGraph wct) {
		Dimension d = new Dimension(500,1200);
		CardGraphGui cardTreeGui = new CardGraphGui(wct);
		cardTreeGui.setSize(d);
		cardTreeGui.setPreferredSize(d);
		cardTreeGui.setMinimumSize(d);
		cardTreeGui.setMaximumSize(d);
		cardTreeGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cardTreeGui.setVisible(true);

	}

	public void testFullDeck() {
		int numberOfWizards = 4;
		int numberOfTrump = 0;

		// test no trump
		testFullDeck(null, numberOfWizards, numberOfTrump);
		testFullDeck(Suit.NONE, numberOfWizards, numberOfTrump);

		numberOfWizards = 4;
		numberOfTrump = 13;

		// test each suit
		testFullDeck(Suit.HEART, numberOfWizards, numberOfTrump);
		testFullDeck(Suit.CLUB, numberOfWizards, numberOfTrump);
		testFullDeck(Suit.SPADE, numberOfWizards, numberOfTrump);
		testFullDeck(Suit.DIAMOND, numberOfWizards, numberOfTrump);
	}

	private void testFullDeck(Suit trump, int numberOfWizards, int numberOfTrump) {
		WizardCardGraph wct = getFullWizardTree(trump);

		List<CardNode> higherCards;

		// jesters
		higherCards = wct.findHigherCards(WizardCardEnum.JESTER_1);
		assertEquals(56, higherCards.size());

		higherCards = wct.findHigherCards(WizardCardEnum.JESTER_2);
		assertEquals(56, higherCards.size());

		higherCards = wct.findHigherCards(WizardCardEnum.JESTER_3);
		assertEquals(56, higherCards.size());

		higherCards = wct.findHigherCards(WizardCardEnum.JESTER_4);
		assertEquals(56, higherCards.size());

		// wizards
		higherCards = wct.findHigherCards(WizardCardEnum.WIZARD_1);
		assertEquals(0, higherCards.size());

		higherCards = wct.findHigherCards(WizardCardEnum.WIZARD_2);
		assertEquals(0, higherCards.size());

		higherCards = wct.findHigherCards(WizardCardEnum.WIZARD_3);
		assertEquals(0, higherCards.size());

		higherCards = wct.findHigherCards(WizardCardEnum.WIZARD_4);
		assertEquals(0, higherCards.size());

		// aces
		int rank = 0;
		higherCards = wct.findHigherCards(WizardCardEnum.ACE_HEARTS);
		assertEquals(numberOfWizards + ((trump != null && trump.equals(Suit.HEART)? rank: numberOfTrump)), higherCards.size());

		higherCards = wct.findHigherCards(WizardCardEnum.ACE_SPADES);
		assertEquals(numberOfWizards + ((trump != null && trump.equals(Suit.SPADE)? rank: numberOfTrump)), higherCards.size());

		higherCards = wct.findHigherCards(WizardCardEnum.ACE_CLUBS);
		assertEquals(numberOfWizards + ((trump != null && trump.equals(Suit.CLUB)? rank: numberOfTrump)), higherCards.size());

		higherCards = wct.findHigherCards(WizardCardEnum.ACE_DIAMOND);
		assertEquals(numberOfWizards + ((trump != null && trump.equals(Suit.DIAMOND)? rank: numberOfTrump)), higherCards.size());

		// kings
		rank = 1;
		higherCards = wct.findHigherCards(WizardCardEnum.KING_HEARTS);
		assertEquals(numberOfWizards + ((trump != null && trump.equals(Suit.HEART)? rank: numberOfTrump + rank)), higherCards.size());

		higherCards = wct.findHigherCards(WizardCardEnum.KING_SPADES);
		assertEquals(numberOfWizards + ((trump != null && trump.equals(Suit.SPADE)? rank: numberOfTrump + rank)), higherCards.size());

		higherCards = wct.findHigherCards(WizardCardEnum.KING_CLUBS);
		assertEquals(numberOfWizards + ((trump != null && trump.equals(Suit.CLUB)? rank: numberOfTrump + rank)), higherCards.size());

		higherCards = wct.findHigherCards(WizardCardEnum.KING_DIAMOND);
		assertEquals(numberOfWizards + ((trump != null && trump.equals(Suit.DIAMOND)? rank: numberOfTrump + rank)), higherCards.size());

		// queen
		rank = 2;
		higherCards = wct.findHigherCards(WizardCardEnum.QUEEN_HEARTS);
		assertEquals(numberOfWizards + ((trump != null && trump.equals(Suit.HEART)? rank: numberOfTrump + rank)), higherCards.size());

		higherCards = wct.findHigherCards(WizardCardEnum.QUEEN_SPADES);
		assertEquals(numberOfWizards + ((trump != null && trump.equals(Suit.SPADE)? rank: numberOfTrump + rank)), higherCards.size());

		higherCards = wct.findHigherCards(WizardCardEnum.QUEEN_CLUBS);
		assertEquals(numberOfWizards + ((trump != null && trump.equals(Suit.CLUB)? rank: numberOfTrump + rank)), higherCards.size());

		higherCards = wct.findHigherCards(WizardCardEnum.QUEEN_DIAMOND);
		assertEquals(numberOfWizards + ((trump != null && trump.equals(Suit.DIAMOND)? rank: numberOfTrump + rank)), higherCards.size());

		// jack
		rank = 3;
		higherCards = wct.findHigherCards(WizardCardEnum.JACK_HEARTS);
		assertEquals(numberOfWizards + ((trump != null && trump.equals(Suit.HEART)? rank: numberOfTrump + rank)), higherCards.size());

		higherCards = wct.findHigherCards(WizardCardEnum.JACK_SPADES);
		assertEquals(numberOfWizards + ((trump != null && trump.equals(Suit.SPADE)? rank: numberOfTrump + rank)), higherCards.size());

		higherCards = wct.findHigherCards(WizardCardEnum.JACK_CLUBS);
		assertEquals(numberOfWizards + ((trump != null && trump.equals(Suit.CLUB)? rank: numberOfTrump + rank)), higherCards.size());

		higherCards = wct.findHigherCards(WizardCardEnum.JACK_DIAMOND);
		assertEquals(numberOfWizards + ((trump != null && trump.equals(Suit.DIAMOND)? rank: numberOfTrump + rank)), higherCards.size());

		//...


		// 3's
		rank = 11;
		higherCards = wct.findHigherCards(WizardCardEnum.THREE_HEARTS);
		assertEquals(numberOfWizards + ((trump != null && trump.equals(Suit.HEART)? rank: numberOfTrump + rank)), higherCards.size());

		higherCards = wct.findHigherCards(WizardCardEnum.THREE_SPADES);
		assertEquals(numberOfWizards + ((trump != null && trump.equals(Suit.SPADE)? rank: numberOfTrump + rank)), higherCards.size());

		higherCards = wct.findHigherCards(WizardCardEnum.THREE_CLUBS);
		assertEquals(numberOfWizards + ((trump != null && trump.equals(Suit.CLUB)? rank: numberOfTrump + rank)), higherCards.size());

		higherCards = wct.findHigherCards(WizardCardEnum.THREE_DIAMOND);
		assertEquals(numberOfWizards + ((trump != null && trump.equals(Suit.DIAMOND)? rank: numberOfTrump + rank)), higherCards.size());

		// 2's
		rank = 12;
		higherCards = wct.findHigherCards(WizardCardEnum.TWO_HEARTS);
		assertEquals(numberOfWizards + ((trump != null && trump.equals(Suit.HEART)? rank: numberOfTrump + rank)), higherCards.size());

		higherCards = wct.findHigherCards(WizardCardEnum.TWO_SPADES);
		assertEquals(numberOfWizards + ((trump != null && trump.equals(Suit.SPADE)? rank: numberOfTrump + rank)), higherCards.size());

		higherCards = wct.findHigherCards(WizardCardEnum.TWO_CLUBS);
		assertEquals(numberOfWizards + ((trump != null && trump.equals(Suit.CLUB)? rank: numberOfTrump + rank)), higherCards.size());

		higherCards = wct.findHigherCards(WizardCardEnum.TWO_DIAMOND);
		assertEquals(numberOfWizards + ((trump != null && trump.equals(Suit.DIAMOND)? rank: numberOfTrump + rank)), higherCards.size());
	}

}
