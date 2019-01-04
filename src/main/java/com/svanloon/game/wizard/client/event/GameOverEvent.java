package com.svanloon.game.wizard.client.event;

import java.util.Collection;

/**
 * 
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class GameOverEvent extends WizardEvent{
	private Collection<String> winningPlayerCollection;
	private Collection<Integer> ids;
	/**
	 * 
	 * Constructs a new <code>GameOverEvent</code> object.
	 * @param ids 
	 * @param winningPlayerCollection 
	 */
	public GameOverEvent (Collection<Integer> ids, Collection<String> winningPlayerCollection) {
		super();
		this.winningPlayerCollection = winningPlayerCollection;
		this.ids = ids;
	}
	/**
	 * Returns the winningPlayer.
	 *
	 * @return the winningPlayer.
	 */
	public Collection<String> getWinningPlayers() {
		return winningPlayerCollection;
	}
	/**
	 * @return the ids
	 */
	public Collection<Integer> getIds() {
		return ids;
	}
}
