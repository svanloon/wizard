package com.svanloon.game.wizard.client.event;

/**
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public interface GameEventListener {
	/**
	 * @param e
	 */
	public void handleGameOver(GameOverEvent e);
	/**
	 * @param e
	 */
	public void handleHandDealt(HandDealtEvent e);
	/**
	 * @param e
	 */
	public void handleNewGame(NewGameEvent e);
	/**
	 * @param e
	 */
	public void handleNewRound(NewRoundEvent e);
	/**
	 * @param e
	 */
	public void handleNewTrick(NewTrickEvent e);
	/**
	 * @param e
	 */
	public void handleNewTrump(NewTrumpEvent e);
	/**
	 * @param e
	 */
	public void handlePlayerBid(PlayerBidEvent e);
	/**
	 * @param e
	 */
	public void handlePlayerPlayed(PlayerPlayedEvent e);
	/**
	 * @param e 
	 * 
	 */
	public void handlePlayerWonTrick(PlayerWonTrickEvent e);
	/**
	 * @param e
	 */
	public void handleScore(ScoreEvent e);
	/**
	 * @param e
	 */
	public void handlePlayerNeedsToPlay(PlayerNeedsToPlay e);
}
