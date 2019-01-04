package com.svanloon.game.wizard.core.gameOptions;


/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class GameOptions {
	private BidType bidType = BidType.STANDARD;
	private GameSpeed gameSpeed = GameSpeed.STANDARD;
	private int totalNumberOfPlayers = 4;
	private int totalHumanPlayers = 1;

	/**
	 * 
	 * Document the getBidType method 
	 *
	 * @return BidType
	 */
	public BidType getBidType() {
		return bidType;
	}

	/**
	 * Document the setBidType method 
	 * @param bidType
	 */
	public void setBidType(BidType bidType) {
		this.bidType = bidType;
	}

	/**
	 * Returns the gameSpeed.
	 *
	 * @return the gameSpeed.
	 */
	public GameSpeed getGameSpeed() {
		return gameSpeed;
	}

	/**
	 * Sets the gameSpeed.
	 *
	 * @param gameSpeed The new value for gameSpeed.
	 */
	public void setGameSpeed(GameSpeed gameSpeed) {
		this.gameSpeed = gameSpeed;
	}

	/**
	 * @return the totalHumanPlayers
	 */
	public int getTotalHumanPlayers() {
		return totalHumanPlayers;
	}

	/**
	 * @param totalHumanPlayers the totalHumanPlayers to set
	 */
	public void setTotalHumanPlayers(int totalHumanPlayers) {
		this.totalHumanPlayers = totalHumanPlayers;
	}

	/**
	 * @return the totalNumberOfPlayers
	 */
	public int getTotalNumberOfPlayers() {
		return totalNumberOfPlayers;
	}

	/**
	 * @param totalNumberOfPlayers the totalNumberOfPlayers to set
	 */
	public void setTotalNumberOfPlayers(int totalNumberOfPlayers) {
		this.totalNumberOfPlayers = totalNumberOfPlayers;
	}
}
