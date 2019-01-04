package com.svanloon.game.wizard.network.event;

import java.io.IOException;

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
import com.svanloon.game.wizard.client.event.WizardEvent;
import com.svanloon.game.wizard.network.Message;
import com.svanloon.game.wizard.network.MessageSender;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class ServerGameEventListener implements GameEventListener, Runnable {

	public void run() {
		try {
			this.ems = new MessageSender(port);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}

	private MessageSender ems = null;
	private int port;
	/**
	 * 
	 * Constructs a new <code>ServerGameEventListener</code> object. 
	 *
	 * @param port
	 */
	public ServerGameEventListener(int port) {
		super();
		this.port = port;
	}

	public void handleGameOver(GameOverEvent e) {
		send(e);
	}

	public void handleHandDealt(HandDealtEvent e) {
		send(e);	
	}

	public void handleNewGame(NewGameEvent e) {
		send(e);	
	}

	public void handleNewRound(NewRoundEvent e) {
		send(e);
	}

	public void handleNewTrick(NewTrickEvent e) {
		send(e);
	}

	public void handleNewTrump(NewTrumpEvent e) {
		send(e);
	}

	public void handlePlayerBid(PlayerBidEvent e) {
		send(e);
	}

	public void handlePlayerPlayed(PlayerPlayedEvent e) {
		send(e);
	}

	public void handlePlayerWonTrick(PlayerWonTrickEvent e) {
		send(e);
	}

	public void handleScore(ScoreEvent e) {
		send(e);
	}

	private void send(WizardEvent we) {
		try {
			ServerGameEventMessageGenerator wmg = new ServerGameEventMessageGenerator();
			Message message = wmg.createMessage(we);
			while (ems == null) {
				Thread.sleep(35);
			}
			ems.sendMessageAndGetResponse(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void handlePlayerNeedsToPlay(PlayerNeedsToPlay e) {
		send(e);
	}
}