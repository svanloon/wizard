package com.svanloon.game.wizard.client.event;

import java.util.List;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class NewGameEvent extends WizardEvent{
	private List<String> playerNames;
	private List<Integer> ids;
	/**
	 * 
	 * Constructs a new <code>NewGameEvent</code> object. 
	 * @param ids
	 * @param playerNames
	 */
	public NewGameEvent(List<Integer> ids, List<String> playerNames) {
		this.playerNames = playerNames;
		this.ids = ids;
		if(ids == null) {
			throw new RuntimeException("yeah");
		}
	}
	/**
	 * @return the players
	 */
	public List<String> getPlayerNames() {
		return playerNames;
	}
	/**
	 * @return the ids
	 */
	public List<Integer> getIds() {
		return ids;
	}
}
