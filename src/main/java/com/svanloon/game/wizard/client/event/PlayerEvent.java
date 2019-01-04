package com.svanloon.game.wizard.client.event;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class PlayerEvent extends WizardEvent {
	private int playerId;

	/**
	 * 
	 * Constructs a new <code>PlayerEvent</code> object. 
	 *
	 * @param playerId
	 * @param playerName
	 */
	public PlayerEvent(int playerId) {
		super();
		this.playerId = playerId;
	}

	/**
	 * @return the playerId
	 */
	public int getPlayerId() {
		return playerId;
	}
	
	@Override
	public String toString() {
		return String.valueOf(playerId);
	}
}
