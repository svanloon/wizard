package com.svanloon.game.wizard.stats;

import java.io.File;

import com.svanloon.game.wizard.client.event.GameEventListener;
import com.svanloon.game.wizard.client.event.GameOverEvent;
import com.svanloon.game.wizard.client.event.HandDealtEvent;
import com.svanloon.game.wizard.client.event.NewGameEvent;
import com.svanloon.game.wizard.client.event.NewRoundEvent;
import com.svanloon.game.wizard.client.event.NewTrickEvent;
import com.svanloon.game.wizard.client.event.NewTrumpEvent;
import com.svanloon.game.wizard.client.event.PlayerBidEvent;
import com.svanloon.game.wizard.client.event.PlayerNeedsToPlay;
import com.svanloon.game.wizard.client.event.PlayerPlayedEvent;
import com.svanloon.game.wizard.client.event.PlayerWonTrickEvent;
import com.svanloon.game.wizard.client.event.ScoreEvent;


/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class StatsListener implements GameEventListener {

	private GameArchive gameArchive = GameArchive.getInstance();

	boolean writeGamesToFile;
	/**
	 * 
	 * Constructs a new <code>StatsListener</code> object. 
	 *
	 * @param writeGamesToFile
	 */
	public StatsListener(boolean writeGamesToFile) {
		this.writeGamesToFile = writeGamesToFile;
	}
	
	public void handleGameOver(GameOverEvent pE) {
		for (Integer winner: pE.getIds()) {
			gameArchive.getCurrentGame().addWinner(winner.intValue());
		}
		
		if (writeGamesToFile) {
			for (Integer playerId: gameArchive.getCurrentGame().getPlayerMap().keySet()) {
					String path = System.getProperty("user.home");
					path += File.separator + "wizard";
					gameArchive.writeToFile(playerId.intValue(), path);
			}
		}
	}

	/**
	 * 
	 */
	public void handleHandDealt(HandDealtEvent pE) {
		// intentionally blank
	}

	public void handleNewGame(NewGameEvent pE) {
		gameArchive.newGame();
		for (int i = 0; i < pE.getIds().size(); i++) {
			gameArchive.getCurrentGame().addPlayer(pE.getIds().get(i).intValue(), pE.getPlayerNames().get(i));
		}
	}

	public void handleNewRound(NewRoundEvent pE) {
		gameArchive.newRound(pE.getRound());
	}

	public void handleNewTrick(NewTrickEvent pE) {
		
		gameArchive.newTrick();
	}

	public void handleNewTrump(NewTrumpEvent pE) {
		gameArchive.getCurrentRound().setTrump(pE.getCard());
	}

	public void handlePlayerBid(PlayerBidEvent pE) {
		gameArchive.getCurrentRound().setBid(pE.getPlayerId(), pE.getBid());
	}

	public void handlePlayerNeedsToPlay(PlayerNeedsToPlay pE) {
		// nothing to do
	}

	public void handlePlayerPlayed(PlayerPlayedEvent pE) {
		gameArchive.getCurrentTrick().add(new Play(pE.getPlayerId(), pE.getCard()));
		if (pE.getCard().isWizard()) {
			gameArchive.getCurrentTrick().setWizardPlayed(true);
		}
	}

	public void handlePlayerWonTrick(PlayerWonTrickEvent pE) {
		gameArchive.getCurrentTrick().setWinner(pE.getPlayerId());
	}

	public void handleScore(ScoreEvent pE) {
		gameArchive.getCurrentGame().addScore(pE.getPlayerId(), pE.getScoreChangeAmount());
	}

	/**
	 * get the game archive
	 * @return GameArchive
	 */
	public GameArchive getGameArchive() {
		return gameArchive;
	}
}
