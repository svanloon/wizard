package com.svanloon.game.wizard.core.card.util;

import java.util.ArrayList;
import java.util.List;

import com.svanloon.game.wizard.core.card.Card;
import com.svanloon.game.wizard.core.card.Suit;
import com.svanloon.game.wizard.core.card.Value;

public class WizardCardEnum {
	private static List<Card> values = new ArrayList<Card>();
	/**
	 *
	 * Document the addValue method
	 *
	 * @param card
	 * @return Card
	 */
	public static Card addValue(Card card) {
		values.add(card);
		return card;
	}

	/** */public static Card WIZARD_1 = addValue(new Card(Value.WIZARD, Suit.NONE, 1));
	/** */public static Card WIZARD_2 = addValue(new Card(Value.WIZARD, Suit.NONE, 2));
	/** */public static Card WIZARD_3 = addValue(new Card(Value.WIZARD, Suit.NONE, 3));
	/** */public static Card WIZARD_4 = addValue(new Card(Value.WIZARD, Suit.NONE, 4));

	/** */public static Card TWO_DIAMOND = addValue(new Card(Value.TWO, Suit.DIAMOND, 1));
	/** */public static Card THREE_DIAMOND = addValue(new Card(Value.THREE, Suit.DIAMOND, 1));
	/** */public static Card FOUR_DIAMOND = addValue(new Card(Value.FOUR, Suit.DIAMOND, 1));
	/** */public static Card FIVE_DIAMOND = addValue(new Card(Value.FIVE, Suit.DIAMOND, 1));
	/** */public static Card SIX_DIAMOND = addValue(new Card(Value.SIX, Suit.DIAMOND, 1));
	/** */public static Card SEVEN_DIAMOND = addValue(new Card(Value.SEVEN, Suit.DIAMOND, 1));
	/** */public static Card EIGHT_DIAMOND = addValue(new Card(Value.EIGHT, Suit.DIAMOND, 1));
	/** */public static Card NINE_DIAMOND = addValue(new Card(Value.NINE, Suit.DIAMOND, 1));
	/** */public static Card TEN_DIAMOND = addValue(new Card(Value.TEN, Suit.DIAMOND, 1));
	/** */public static Card JACK_DIAMOND = addValue(new Card(Value.JACK, Suit.DIAMOND, 1));
	/** */public static Card QUEEN_DIAMOND = addValue(new Card(Value.QUEEN, Suit.DIAMOND, 1));
	/** */public static Card KING_DIAMOND = addValue(new Card(Value.KING, Suit.DIAMOND, 1));
	/** */public static Card ACE_DIAMOND = addValue(new Card(Value.ACE, Suit.DIAMOND, 1));


	/** */public static Card TWO_HEARTS = addValue(new Card(Value.TWO, Suit.HEART, 3));
	/** */public static Card THREE_HEARTS = addValue(new Card(Value.THREE, Suit.HEART, 3));
	/** */public static Card FOUR_HEARTS = addValue(new Card(Value.FOUR, Suit.HEART, 3));
	/** */public static Card FIVE_HEARTS = addValue(new Card(Value.FIVE, Suit.HEART, 3));
	/** */public static Card SIX_HEARTS = addValue(new Card(Value.SIX, Suit.HEART, 3));
	/** */public static Card SEVEN_HEARTS = addValue(new Card(Value.SEVEN, Suit.HEART, 3));
	/** */public static Card EIGHT_HEARTS = addValue(new Card(Value.EIGHT, Suit.HEART, 3));
	/** */public static Card NINE_HEARTS = addValue(new Card(Value.NINE, Suit.HEART, 3));
	/** */public static Card TEN_HEARTS = addValue(new Card(Value.TEN, Suit.HEART, 3));
	/** */public static Card JACK_HEARTS = addValue(new Card(Value.JACK, Suit.HEART, 3));
	/** */public static Card QUEEN_HEARTS = addValue(new Card(Value.QUEEN, Suit.HEART, 3));
	/** */public static Card KING_HEARTS = addValue(new Card(Value.KING, Suit.HEART, 3));
	/** */public static Card ACE_HEARTS = addValue(new Card(Value.ACE, Suit.HEART, 3));

	/** */public static Card TWO_CLUBS = addValue(new Card(Value.TWO, Suit.CLUB, 2));
	/** */public static Card THREE_CLUBS = addValue(new Card(Value.THREE, Suit.CLUB, 2));
	/** */public static Card FOUR_CLUBS = addValue(new Card(Value.FOUR, Suit.CLUB, 2));
	/** */public static Card FIVE_CLUBS = addValue(new Card(Value.FIVE, Suit.CLUB, 2));
	/** */public static Card SIX_CLUBS = addValue(new Card(Value.SIX, Suit.CLUB, 2));
	/** */public static Card SEVEN_CLUBS = addValue(new Card(Value.SEVEN, Suit.CLUB, 2));
	/** */public static Card EIGHT_CLUBS = addValue(new Card(Value.EIGHT, Suit.CLUB, 2));
	/** */public static Card NINE_CLUBS = addValue(new Card(Value.NINE, Suit.CLUB, 2));
	/** */public static Card TEN_CLUBS = addValue(new Card(Value.TEN, Suit.CLUB, 2));
	/** */public static Card JACK_CLUBS = addValue(new Card(Value.JACK, Suit.CLUB, 2));
	/** */public static Card QUEEN_CLUBS = addValue(new Card(Value.QUEEN, Suit.CLUB, 2));
	/** */public static Card KING_CLUBS = addValue(new Card(Value.KING, Suit.CLUB, 2));
	/** */public static Card ACE_CLUBS = addValue(new Card(Value.ACE, Suit.CLUB, 2));

	/** */public static Card TWO_SPADES = addValue(new Card(Value.TWO, Suit.SPADE, 4));
	/** */public static Card THREE_SPADES = addValue(new Card(Value.THREE, Suit.SPADE, 4));
	/** */public static Card FOUR_SPADES = addValue(new Card(Value.FOUR, Suit.SPADE, 4));
	/** */public static Card FIVE_SPADES = addValue(new Card(Value.FIVE, Suit.SPADE, 4));
	/** */public static Card SIX_SPADES = addValue(new Card(Value.SIX, Suit.SPADE, 4));
	/** */public static Card SEVEN_SPADES = addValue(new Card(Value.SEVEN, Suit.SPADE, 4));
	/** */public static Card EIGHT_SPADES = addValue(new Card(Value.EIGHT, Suit.SPADE, 4));
	/** */public static Card NINE_SPADES = addValue(new Card(Value.NINE, Suit.SPADE, 4));
	/** */public static Card TEN_SPADES = addValue(new Card(Value.TEN, Suit.SPADE, 4));
	/** */public static Card JACK_SPADES = addValue(new Card(Value.JACK, Suit.SPADE, 4));
	/** */public static Card QUEEN_SPADES = addValue(new Card(Value.QUEEN, Suit.SPADE, 4));
	/** */public static Card KING_SPADES = addValue(new Card(Value.KING, Suit.SPADE, 4));
	/** */public static Card ACE_SPADES = addValue(new Card(Value.ACE, Suit.SPADE, 4));

	/** */public static Card JESTER_1 = addValue(new Card(Value.JESTER, Suit.NONE, 1));
	/** */public static Card JESTER_2 = addValue(new Card(Value.JESTER, Suit.NONE, 2));
	/** */public static Card JESTER_3 = addValue(new Card(Value.JESTER, Suit.NONE, 3));
	/** */public static Card JESTER_4 = addValue(new Card(Value.JESTER, Suit.NONE, 4));

	/**
	 * Returns the values.
	 *
	 * @return the values.
	 */
	public static List<Card> getValues() {
		return values;
	}
}
