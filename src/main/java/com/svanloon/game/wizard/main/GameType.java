package com.svanloon.game.wizard.main;

public enum GameType {
	HOSTED(2),
	CONNECT_TO_A_GAME(3);

	private int index;

	GameType(int index) {
		this.index = index;
	}

	/**
	 *
	 * Document the findByIndex method
	 *
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
