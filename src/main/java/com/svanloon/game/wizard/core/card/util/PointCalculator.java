package com.svanloon.game.wizard.core.card.util;

import com.svanloon.game.wizard.core.card.Value;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public interface PointCalculator {

	/**
	 * 
	 * Document the intgetIndex method 
	 *
	 * @param value
	 * @return int
	 */
	public int getIndex(Value value);

	/**
	 * 
	 * Document the getPoint method 
	 *
	 * @param value
	 * @return int
	 */
	public int getPoint(Value value);
}
