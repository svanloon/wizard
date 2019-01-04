package com.svanloon.game.wizard.main;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public enum GameType {
	/** */HOSTED(2),
	/** */CONNECT_TO_A_GAME(3);
	
	private int index;

	private GameType(int index) {
		this.index = index;
	}

	/**
	 * 
	 * Document the findByIndex method 
	 *
	 * @param pIndex
	 * @return GameType
	 */
	public GameType findByIndex(int pIndex) {
		for(GameType value: values()) {
			if(value.index == pIndex) {
				return value;
			}
		}

		return null;
	}
}
