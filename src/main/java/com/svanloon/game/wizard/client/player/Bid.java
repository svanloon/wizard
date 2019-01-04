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
public class Bid {
	private Collection<IndividualBid> individualBidCollection = new ArrayList<IndividualBid>();

	/**
	 * 
	 * Document the addIndividualBid method 
	 *
	 * @param individualBid
	 */
	public void addIndividualBid(IndividualBid individualBid) {
		this.individualBidCollection.add(individualBid);
	}

	/**
	 * 
	 * Document the findIndividualBidBy method 
	 *
	 * @param player
	 * @return IndividualBid
	 */
	public IndividualBid findIndividualBidBy(Player player) {
		IndividualBid previousIndividualBid = null;
		for (IndividualBid tempIndividualBid: this.individualBidCollection) {
			if (tempIndividualBid.getPlayer().equals(player)) {
				previousIndividualBid = tempIndividualBid;
			}
		}
		return previousIndividualBid;
	}

	/**
	 * 
	 * Document the getBids method 
	 *
	 * @return IndividualBids
	 */
	public Collection<IndividualBid> getBids() {
		return individualBidCollection;
	}

	/**
	 * 
	 * Document the bidSoFar method 
	 *
	 * @return int
	 */
	public int bidSoFar() {
		int bid = 0;
		for (IndividualBid tempIndividualBid: this.individualBidCollection) {
			bid += tempIndividualBid.getBid();
		}
		return bid;
	}
}


