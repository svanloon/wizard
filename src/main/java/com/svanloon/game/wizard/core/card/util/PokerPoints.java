package com.svanloon.game.wizard.core.card.util;

import com.svanloon.game.wizard.core.card.Value;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class PokerPoints implements PointCalculator, Cloneable {

	/**
	 * 
	 * Constructs a new <code>PokerPoints</code> object. 
	 *
	 */
	public PokerPoints() {
		super();
	}

	public int getIndex(Value value) {
		if(Value.ACE.equals(value)) {
			return 1;
		} else if(Value.TWO.equals(value)) {
			return 2;
		} else if(Value.THREE.equals(value)) {
			return 3;
		} else if(Value.FOUR.equals(value)) {
			return 4;
		} else if(Value.FIVE.equals(value)) {
			return 5;
		} else if(Value.SIX.equals(value)) {
			return 6;
		} else if(Value.SEVEN.equals(value)) {
			return 7;
		} else if(Value.EIGHT.equals(value)) {
			return 8;
		} else if(Value.NINE.equals(value)) {
			return 9;
		} else if(Value.TEN.equals(value)) {
			return 10;
		} else if(value.equals(Value.JACK)) {
			return 11;
		} else if(value.equals(Value.QUEEN)) {
			return 12;
		} else if(value.equals(Value.KING)) {
			return 13;
		}
		return -1;
	}

	public int getPoint(Value value) {
		return getIndex(value);
	}

}
