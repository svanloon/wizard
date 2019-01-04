package com.svanloon.game.wizard.core.card;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.svanloon.common.util.CollectionUtil;



/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class Hand extends ArrayList<Card>{

	private List<Card> heartList = new ArrayList<Card>();
	private List<Card> spadeList = new ArrayList<Card>();
	private List<Card> diamondList = new ArrayList<Card>();
	private List<Card> clubList = new ArrayList<Card>();
	private List<Card> jesterList = new ArrayList<Card>();
	private List<Card> wizardList = new ArrayList<Card>();

	/**
	 * 
	 */
	private static final long serialVersionUID = 20070418L;

	/**
	 * 
	 * Constructs a new <code>Hand</code> object. 
	 *
	 */
	public Hand() {
		super();
	}

	/**
	 * 
	 * Constructs a new <code>Hand</code> object. 
	 *
	 * @param cards
	 */
	public Hand(Collection<Card> cards) {
		super(cards);
	}
	
	@Override
	public boolean add(Card card) {
		boolean result = super.add(card);
		Suit suit = card.getSuit();
		Value value = card.getValue();
		if(suit.equals(Suit.HEART)) {
			heartList.add(card);
		} else if(suit.equals(Suit.SPADE)) {
			spadeList.add(card);
		} else if(suit.equals(Suit.CLUB)) {
			clubList.add(card);
		} else if(suit.equals(Suit.DIAMOND)) {
			diamondList.add(card);
		} else if(value.equals(Value.WIZARD)) {
			wizardList.add(card);
		} else if(value.equals(Value.JESTER)) {
			jesterList.add(card);
		}
		return result;
	}

	@Override
	public boolean remove(Object obj) {
		boolean result = super.remove(obj);
		if(obj instanceof Card == false) {
			return result;
			
		}
		Card card = (Card) obj;
		Suit suit = card.getSuit();
		Value value = card.getValue();
		if(suit.equals(Suit.HEART)) {
			heartList.remove(card);
		} else if(suit.equals(Suit.SPADE)) {
			spadeList.remove(card);
		} else if(suit.equals(Suit.CLUB)) {
			clubList.remove(card);
		} else if(suit.equals(Suit.DIAMOND)) {
			diamondList.remove(card);
		} else if(value.equals(Value.WIZARD)) {
			wizardList.remove(card);
		} else if(value.equals(Value.JESTER)) {
			jesterList.remove(card);
		}
		return result;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		int size = this.size();
		int count = 0;
		for (Card t: this) {
			sb.append(t.toString());
			if (count + 1 != size) {
				sb.append(", ");
			} else {
				sb.append("");
			}
			count++;
		}
		return sb.toString();
	}

	/**
	 * Sorts by Suit, then Value
	 */
	public void naturalSort() {
		Collection<Card> sorted = CollectionUtil.sort(this, new String[]{"getSuit", "getValue"});
		this.clear();
		this.addAll(sorted);
	}

	/**
	 * @return the clubList
	 */
	public List<Card> getClubList() {
		return clubList;
	}

	/**
	 * @return the diamondList
	 */
	public List<Card> getDiamondList() {
		return diamondList;
	}

	/**
	 * @return the heartList
	 */
	public List<Card> getHeartList() {
		return heartList;
	}

	/**
	 * @return the jesterList
	 */
	public List<Card> getJesterList() {
		return jesterList;
	}

	/**
	 * @return the spadeList
	 */
	public List<Card> getSpadeList() {
		return spadeList;
	}

	/**
	 * @return the wizardList
	 */
	public List<Card> getWizardList() {
		return wizardList;
	}
	
}