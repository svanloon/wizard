package com.svanloon.game.wizard.core.card;

/**
 * 
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public enum Value {
	/** */JESTER(0, "S", "Jester"),
	/** */TWO(1, "2", "Two"),
	/** */THREE(2, "3", "Three"),
	/** */FOUR(3, "4", "Four"),
	/** */FIVE(4, "5", "Five"),
	/** */SIX(5, "6", "Six"),
	/** */SEVEN(6, "7", "Seven"),
	/** */EIGHT(7, "8", "Eight"),
	/** */NINE(8, "9", "Nine"),
	/** */TEN(9, "10", "Ten"),
	/** */JACK(10, "J", "Jack"),
	/** */QUEEN(11, "Q", "Queen"),
	/** */KING(12, "K", "King"),
	/** */ACE(13, "A", "Ace"),
	/** */WIZARD(14, "W", "Wizard");

	private int index;

	private String shortNm;

	private String description;
	/**
	 * 
	 * Document the findValueByShortName method 
	 *
	 * @param shortName
	 * @return Value
	 */
	public static Value findValueByShortName(String shortName) {
		if (shortName.equalsIgnoreCase(ACE.getShortNm())) {
			return ACE;
		} else if (shortName.equalsIgnoreCase(TWO.getShortNm())) {
			return TWO;
		} else if (shortName.equalsIgnoreCase(THREE.getShortNm())) {
			return THREE;
		} else if (shortName.equalsIgnoreCase(FOUR.getShortNm())) {
			return FOUR;
		} else if (shortName.equalsIgnoreCase(FIVE.getShortNm())) {
			return FIVE;
		} else if (shortName.equalsIgnoreCase(SIX.getShortNm())) {
			return SIX;
		} else if (shortName.equalsIgnoreCase(SEVEN.getShortNm())) {
			return SEVEN;
		} else if (shortName.equalsIgnoreCase(EIGHT.getShortNm())) {
			return EIGHT;
		} else if (shortName.equalsIgnoreCase(NINE.getShortNm())) {
			return NINE;
		} else if (shortName.equalsIgnoreCase(TEN.getShortNm())) {
			return TEN;
		} else if (shortName.equalsIgnoreCase(JACK.getShortNm())) {
			return JACK;
		} else if (shortName.equalsIgnoreCase(QUEEN.getShortNm())) {
			return QUEEN;
		} else if (shortName.equalsIgnoreCase(KING.getShortNm())) {
			return KING;
		} else if (shortName.equalsIgnoreCase(WIZARD.getShortNm())) {
			return WIZARD;
		} else if (shortName.equalsIgnoreCase(JESTER.getShortNm())) {
			return JESTER;
		}
		return null;
	}
	/**
	 * 
	 * Document the findValueByIndex method 
	 *
	 * @param index
	 * @return Value
	 */
	public static Value findValueByIndex(int index) {
		for(Value value:Value.values()) {
			if(value.index == index) {
				return value;
			}
		}

		throw new Error("Value index " + index + " not found.");
	}

	private Value(int index, String shortNm, String description) {
		this.index = index;
		this.shortNm = shortNm;
		this.description = description;
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
	 * Document the getShortNm method 
	 *
	 * @return String
	 */
	public String getShortNm() {
		return this.shortNm;
	}
	/**
	 * 
	 * Document the setShortNm method 
	 *
	 * @param shortNm
	 */
	public void setShortNm(String shortNm) {
		this.shortNm = shortNm;
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
	 * Document the isGreater method 
	 *
	 * @param v
	 * @return boolean
	 */
	public boolean isGreater(Value v) {
		if (v == null) {
			return false;
		}
		return (index > v.getIndex());

	}
}