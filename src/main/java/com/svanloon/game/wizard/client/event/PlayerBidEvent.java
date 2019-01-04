package com.svanloon.game.wizard.client.event;

/**
 * 
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class PlayerBidEvent extends PlayerEvent {
	private int bid;
	/**
	 * 
	 * Constructs a new <code>PlayerBidEvent</code> object. 
	 * @param id 
	 * @param playerName
	 * @param bid
	 */
	public PlayerBidEvent(int id, int bid) {
		super(id);
		this.bid = bid;
	}
	/**
	 * Returns the bid.
	 *
	 * @return the bid.
	 */
	public int getBid() {
		return bid;
	}
}
