package com.svanloon.game.wizard.core.card.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.svanloon.game.wizard.core.card.Card;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class CardNode implements Cloneable {

	private Map<String, Object> params = new HashMap<String, Object>();

	private List<CardNode> lighterNodeCollection = new ArrayList<CardNode>();

	private List<CardNode> heavierNodeCollection = new ArrayList<CardNode>();

	private Card card;
	/**
	 * 
	 * Constructs a new <code>CardNode</code> object. 
	 *
	 * @param card
	 */
	public CardNode(Card card) {
		this.card = card;
	}
	/**
	 * 
	 * Document the getCard method 
	 *
	 * @return Card
	 */
	public Card getCard() {
		return card;
	}
	/**
	 * 
	 * Document the setCard method 
	 *
	 * @param card
	 */
	public void setCard(Card card) {
		this.card = card;
	}
	/**
	 * 
	 * Document the addLighterNode method 
	 *
	 * @param parentCardNode
	 */
	public void addLighterNode(CardNode parentCardNode) {
		if(lighterNodeCollection.contains(parentCardNode)) {
			return;
		}
		lighterNodeCollection.add(parentCardNode);
	}
	/**
	 * 
	 * Document the addHeavierNode method 
	 *
	 * @param childCardNode
	 */
	public void addHeavierNode(CardNode childCardNode) {
		if(heavierNodeCollection.contains(childCardNode)) {
			return;
		}
		heavierNodeCollection.add(childCardNode);
	}
	/**
	 * 
	 * Document the getLighterNodeCollection method 
	 *
	 * @return Collection
	 */
	public List<CardNode> getLighterNodeCollection() {
		return lighterNodeCollection;
	}
	/**
	 * 
	 * Document the getHeavierNodeCollection method 
	 *
	 * @return Collection
	 */
	public List<CardNode> getHeavierNodeCollection() {
		return heavierNodeCollection;
	}
	/**
	 * 
	 * Document the getParam method 
	 *
	 * @param key
	 * @return Object
	 */
	public Object getParam(String key) {
		return params.get(key);
	}
	/**
	 * 
	 * Document the getParam method 
	 *
	 * @param key
	 * @param value
	 */
	public void addParam(String key, Object value) {
		params.put(key, value);
	}
}
