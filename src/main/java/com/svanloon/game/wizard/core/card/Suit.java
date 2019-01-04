package com.svanloon.game.wizard.core.card;

import java.awt.Color;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public enum Suit implements Comparable<Suit> {

	/** */DIAMOND(0, "D", "Diamonds", "\u2666", Color.RED),

	/** */CLUB (1, "C", "Clubs", "\u2663", Color.BLACK),

	/** */HEART(2, "H", "Hearts", "\u2665", Color.RED),

	/** */SPADE(3, "S", "Spades", "\u2660",	Color.BLACK),

	/** */NONE(4, "N", "None", "N", Color.BLUE);

	
	private int index;

	private String shortName;

	private String description;
	private String symbol;
	private Color color;
	/**
	 * 
	 * Document the findSuitByShortName method 
	 *
	 * @param shortName
	 * @return Suit
	 */
	public static Suit findSuitByShortName(String shortName) {
		if (shortName.equalsIgnoreCase("H")) {
			return HEART;
		} else if (shortName.equalsIgnoreCase("D")) {
			return DIAMOND;
		} else if (shortName.equalsIgnoreCase("S")) {
			return SPADE;
		} else if (shortName.equalsIgnoreCase("C")) {
			return CLUB;
		} else if (shortName.equalsIgnoreCase("N")) {
			return NONE;
		} else {
			throw new Error("Suit with shortName " + shortName + " not found.");
		}
	}
	/**
	 * 
	 * Document the findSuitByIndex method 
	 *
	 * @param index
	 * @return Suit
	 */
	public static Suit findSuitByIndex(int index) {
		if (index == Suit.HEART.index) {
			return Suit.HEART;
		} else if (index == Suit.DIAMOND.index) {
			return Suit.DIAMOND;
		} else if (index == Suit.SPADE.index) {
			return Suit.SPADE;
		} else if (index == Suit.CLUB.index) {
			return Suit.CLUB;
		} else if (index == Suit.NONE.index) {
			return Suit.NONE;
		} else {
			throw new Error("Suit with index " + index + " not found.");
		}
	}

	private Suit(int index, String shortName, String description, String symbol, Color color) {
		this.index = index;
		this.description = description;
		this.shortName = shortName;
		this.symbol = symbol;
		this.color = color;
	}

	/**
	 * 
	 * Document the getIndex method 
	 *
	 * @return int
	 */
	public int getIndex() {
		return this.index;
	}
	/**
	 * 
	 * Document the setIndex method 
	 *
	 * @param index
	 */
	public void setIndex(int index) {
		this.index = index;
	}
	/**
	 * 
	 * Document the getDescription method 
	 *
	 * @return String
	 */
	public String getDescription() {
		return this.description;
	}
	/**
	 * 
	 * Document the setDescription method 
	 *
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * 
	 * Document the getShortName method 
	 *
	 * @return String
	 */
	public String getShortName() {
		return this.shortName;
	}
	/**
	 * 
	 * Document the setShortName method 
	 *
	 * @param shortName
	 */
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Override
	public String toString() {
		return this.description;
	}
	/**
	 * Returns the color.
	 *
	 * @return the color.
	 */
	public Color getColor() {
		return color;
	}
	/**
	 * Returns the symbol.
	 *
	 * @return the symbol.
	 */
	public String getSymbol() {
		return symbol;
	}
}