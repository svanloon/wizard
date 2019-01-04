package com.svanloon.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import com.svanloon.game.wizard.core.card.Card;
import com.svanloon.game.wizard.core.card.Suit;
import com.svanloon.game.wizard.core.card.Value;

class SVComparator implements Comparator<Object> {

	private String[] methodNameList;

	/**
	 * 
	 * Constructs a new <code>SVComparator</code> object. 
	 *
	 * @param methodNameList
	 */
	public SVComparator(String[] methodNameList) {
		this.methodNameList = methodNameList;
	}

	public int compare(Object object1, Object object2) {
		return sortObject(object1, object2, 0);
	}

	private int sortObject(Object object1, Object object2, int methodNumber2) {

		if (methodNumber2 == this.methodNameList.length) {
			return 0;
		}

		int methodNumber = methodNumber2;
		if (object1 == null || object2 == null) {
			if (object1 == null && object2 == null) {
				return 0;
			} else if (object1 == null) {
				return 1;
			} else {
				return -1;
			}
		}

		String methodName = this.methodNameList[methodNumber];
		Object methodResult1 = Reflection.invokeMethod(object1, methodName);
		Object methodResult2 = Reflection.invokeMethod(object2, methodName);

		if (methodResult1 == null || methodResult2 == null) {
			if (methodResult1 == null && methodResult2 == null) {
				return 0;
			} else if (methodResult1 == null) {
				return 1;
			} else {
				return -1;
			}
		}

		int compareInt;
		if(methodResult1 instanceof Suit) {
			compareInt = ((Suit)methodResult2).getIndex() - ((Suit)methodResult1).getIndex();
		} else if(methodResult1 instanceof Value) {
			compareInt = ((Value)methodResult2).getIndex() - ((Value)methodResult1).getIndex();
		} else {
			compareInt = ((Integer) Reflection.invokeMethod(methodResult1,
					"compareTo", new Object[] { methodResult2 })).intValue();			
		}


		if (compareInt == 0) {
			methodNumber++;
			return sortObject(object1, object2, methodNumber);
		}
		return compareInt;
	}
}

/**
 * 
 * 
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class CollectionUtil {

	/**
	 * 
	 * @param collectionToBeSorted
	 * @param methodNameList
	 * @return Collection
	 */
	public static Collection<Card> sort(
			Collection<Card> collectionToBeSorted, String[] methodNameList) {
		ArrayList<Card> arrayListToBeSorted = new ArrayList<Card>(
				collectionToBeSorted);
		Collections.sort(arrayListToBeSorted, new SVComparator(methodNameList));
		return arrayListToBeSorted;
	}

}
