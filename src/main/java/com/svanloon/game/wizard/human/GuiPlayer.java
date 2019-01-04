package com.svanloon.game.wizard.human;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class GuiPlayer {
	/**
	 * 
	 *
	 */
	public GuiPlayer() {
		super();
	}

	private int id;
	private String playerName;
	private TrickSummary trickSummary;
	private CardPlayedPanel cardPlayed;
	private FaceDownHandJPanel handJPanel;

	/**
	 * @return the handJPanel
	 */
	public FaceDownHandJPanel getHandJPanel() {
		return handJPanel;
	}
	/**
	 * @param handJPanel the handJPanel to set
	 */
	public void setHandJPanel(FaceDownHandJPanel handJPanel) {
		this.handJPanel = handJPanel;
	}
	/**
	 * @return the cardPlayed
	 */
	public CardPlayedPanel getCardPlayed() {
		return cardPlayed;
	}
	/**
	 * @param cardPlayed the cardPlayed to set
	 */
	public void setCardPlayed(CardPlayedPanel cardPlayed) {
		this.cardPlayed = cardPlayed;
	}
	/**
	 * @return the playerName
	 */
	public String getPlayerName() {
		return playerName;
	}
	/**
	 * @param playerName the playerName to set
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
		if(cardPlayed != null) {
			cardPlayed.setLabel(playerName);
		}
	}
	/**
	 * @return the trickSummary
	 */
	public TrickSummary getTrickSummary() {
		return trickSummary;
	}
	/**
	 * @param trickSummary the trickSummary to set
	 */
	public void setTrickSummary(TrickSummary trickSummary) {
		this.trickSummary = trickSummary;
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

}
