package com.svanloon.game.wizard.network.event;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;

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
import com.svanloon.game.wizard.core.card.Card;
import com.svanloon.game.wizard.core.card.Suit;
import com.svanloon.game.wizard.core.card.Value;
import com.svanloon.game.wizard.network.MessageReceiver;
import com.svanloon.game.wizard.network.Message;
import com.svanloon.game.wizard.network.MessageType;
import com.svanloon.game.wizard.network.MessageParam;
import com.svanloon.game.wizard.network.MessageParamConstants;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class ClientGameEventNotifier implements Runnable {
	private static Logger _logger = Logger.getLogger(ClientGameEventNotifier.class);
	private GameEventListener listener;
	private MessageReceiver emr;
	
	/**
	 * 
	 * Constructs a new <code>ClientGameEventNotifier</code> object. 
	 *
	 * @param listener
	 * @param emr
	 */
	public ClientGameEventNotifier(GameEventListener listener, MessageReceiver emr) {
		super();
		this.listener = listener;
		this.emr = emr;
	}

	public void run() {
		try {
			while(true) {
				try {
					Message message = emr.receive();
					handleMessage(message);
					Message m = new Message(MessageType.OK);
					emr.sendMessage(m);
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}
				try {
					Thread.sleep(35);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} finally {
			if(emr != null) {
				emr.close();
			}
		}
	}

	/**
	 * 
	 * Document the handleMessage method 
	 *
	 * @param message
	 */
	public void handleMessage(Message message) {
		MessageType mt = message.getMessageType();
		_logger.info("got " + mt);
		List<MessageParam> mps = message.getMsgParams();
		if(mt.equals(MessageType.GAME_OVER)) {
			Collection<String> playerNames = getPlayerNames(mps); 
			Collection<Integer> ids = getPlayerIds(mps);
			listener.handleGameOver(new GameOverEvent(ids, playerNames));
		} else if(mt.equals(MessageType.HAND_DEALT)) {
			int id = getPlayerId(mps);
			listener.handleHandDealt(new HandDealtEvent(id));
		} else if(mt.equals(MessageType.NEW_GAME)) {
			List<String> playerNames = getPlayerNames(mps);
			List<Integer> ids = getPlayerIds(mps);
			listener.handleNewGame(new NewGameEvent(ids, playerNames));
		} else if(mt.equals(MessageType.NEW_ROUND)) {
			int round = getInt(mps, MessageParamConstants.ROUND);
			listener.handleNewRound(new NewRoundEvent(round));
		} else if(mt.equals(MessageType.NEW_TRICK)) {
			listener.handleNewTrick(new NewTrickEvent());
		} else if(mt.equals(MessageType.NEW_TRUMP)) {
			Card card = getCard(mps);
			listener.handleNewTrump(new NewTrumpEvent(card));
		} else if(mt.equals(MessageType.PLAYER_BID)) {
			int bid = getInt(mps, MessageParamConstants.BID);
			int id = getPlayerId(mps);
			listener.handlePlayerBid(new PlayerBidEvent(id, bid));
		} else if(mt.equals(MessageType.PLAYER_PLAYED)) {
			Card card = getCard(mps);
			int id = getPlayerId(mps);
			listener.handlePlayerPlayed(new PlayerPlayedEvent(id, card));
		} else if(mt.equals(MessageType.PLAYER_WON_TRICK)) {
			int id = getPlayerId(mps);
			Card card = getCard(mps);
			listener.handlePlayerWonTrick(new PlayerWonTrickEvent(id, card));
		} else if(mt.equals(MessageType.SCORE_EVENT)) {
			int score = getInt(mps, MessageParamConstants.SCORE);
			int id = getPlayerId(mps);
			listener.handleScore(new ScoreEvent(id, score));
		} else if(mt.equals(MessageType.PLAYER_NEEDS_TO_PLAY)) {
			int id = getPlayerId(mps);
			listener.handlePlayerNeedsToPlay(new PlayerNeedsToPlay(id));
		}
	}

	private Card getCard(List<MessageParam> mps) {
		String suitShortName = null;
		String valueShortName = null;
		int index = -1;
		for(MessageParam mp:mps) {
			if(mp.getName().equals(MessageParamConstants.SUIT.toString())) {
				suitShortName = mp.getValue();
			} else if(mp.getName().equals(MessageParamConstants.VALUE.toString())) {
				valueShortName = mp.getValue();
			} else if(mp.getName().equals(MessageParamConstants.INDEX.toString())) {
				index = Integer.parseInt(mp.getValue());
			}
		}
		if(suitShortName == null) {
			return null;
		}
		Suit suit = Suit.findSuitByShortName(suitShortName);
		Value value;
		if(valueShortName != null) {
			value = Value.findValueByShortName(valueShortName);
		} else {
			value = null;
		}
		return new Card(value, suit, index);
	}

	private int getInt(List<MessageParam> mps, String name) {
		for(MessageParam mp:mps) {
			if(mp.getName().equals(name)) {
				return Integer.parseInt(mp.getValue());
			}
		}
		return -1;
	}

	private int getPlayerId(List<MessageParam> mps) {
		return getPlayerIds(mps).iterator().next().intValue();
	}

	private List<String> getPlayerNames(List<MessageParam> mps) {
		List<String> playerNames = new ArrayList<String>();
		for(MessageParam mp:mps) {
			if(mp.getName().equals(MessageParamConstants.PLAYER_NAME)) {
				playerNames.add(mp.getValue());
			}
		}
		return playerNames;
	}

	private List<Integer> getPlayerIds(List<MessageParam> mps) {
		List<Integer> playerIds = new ArrayList<Integer>();
		for(MessageParam mp:mps) {
			if(mp.getName().equals(MessageParamConstants.PLAYER_ID)) {
				playerIds.add(Integer.valueOf(mp.getValue()));
			}
		}
		return playerIds;
	}
}
