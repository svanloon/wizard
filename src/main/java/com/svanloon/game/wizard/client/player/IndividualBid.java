/*
 * $Id$ 
 * $HeadURL$
 */
package com.svanloon.game.wizard.client.player;


/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class IndividualBid {
	private Player player;

	private int bid;

	/**
	 * 
	 * Constructs a new <code>IndividualBid</code> object. 
	 *
	 * @param player
	 * @param bid
	 */
	public IndividualBid(Player player, int bid) {
		this.player = player;
		this.bid = bid;
	}

	/**
	 * 
	 * Document the getPlayer method 
	 *
	 * @return Player
	 */
	public Player getPlayer() {
		return this.player;
	}

	/**
	 * 
	 * Document the getBid method 
	 *
	 * @return int
	 */
	public int getBid() {
		return this.bid;
	}
}