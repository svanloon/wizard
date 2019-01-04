package com.svanloon.game.wizard.client.player;

import java.util.Collection;


/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public interface Board {
	/**
	 * 
	 * Document the initializePlayers method 
	 *
	 * @return Collection
	 */
	public Collection<Player> initializePlayers();

	/**
	 * 
	 * Document the getNorthSeat method 
	 *
	 * @return Seat
	 */
	public Seat getNorthSeat();

	/**
	 * 
	 * Document the getSouthSeat method 
	 *
	 * @return Seat
	 */
	public Seat getSouthSeat();

	/**
	 * 
	 * Document the getEastSeat method 
	 *
	 * @return Seat
	 */
	public Seat getEastSeat();

	/**
	 * 
	 * Document the getWestSeat method 
	 *
	 * @return Seat
	 */
	public Seat getWestSeat();

	/**
	 * 
	 * Document the stateChanged method 
	 *
	 */
	public void stateChanged();

}
