package com.svanloon.game.wizard.client.player;

import java.util.ArrayList;
import java.util.Collection;


/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class RoundSummary {

	private Collection<TrickTracker> trickTrackerCollection = new ArrayList<TrickTracker>();

	/**
	 * 
	 * Document the addTrickTracker method 
	 *
	 * @param trickTracker
	 */
	public void addTrickTracker(TrickTracker trickTracker) {
		this.trickTrackerCollection.add(trickTracker);
	}

	/**
	 * 
	 * Document the tricksWonBy method 
	 *
	 * @param player
	 * @return int
	 */
	public int tricksWonBy(Player player) {
		int count = 0;
		for (TrickTracker trickTracker: this.trickTrackerCollection) {
			if (trickTracker.winningPlay().getPlayerId() == player.getId()) {
				count++;
			}
		}
		return count;

	}
}
