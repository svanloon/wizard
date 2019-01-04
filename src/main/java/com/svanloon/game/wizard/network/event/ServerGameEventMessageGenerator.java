package com.svanloon.game.wizard.network.event;

import com.svanloon.game.wizard.client.event.GameOverEvent;
import com.svanloon.game.wizard.client.event.HandDealtEvent;
import com.svanloon.game.wizard.client.event.NewGameEvent;
import com.svanloon.game.wizard.client.event.NewRoundEvent;
import com.svanloon.game.wizard.client.event.NewTrickEvent;
import com.svanloon.game.wizard.client.event.NewTrumpEvent;
import com.svanloon.game.wizard.client.event.PlayerBidEvent;
import com.svanloon.game.wizard.client.event.PlayerEvent;
import com.svanloon.game.wizard.client.event.PlayerNeedsToPlay;
import com.svanloon.game.wizard.client.event.PlayerPlayedEvent;
import com.svanloon.game.wizard.client.event.PlayerWonTrickEvent;
import com.svanloon.game.wizard.client.event.ScoreEvent;
import com.svanloon.game.wizard.client.event.WizardEvent;
import com.svanloon.game.wizard.core.card.Card;
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
public class ServerGameEventMessageGenerator {
	/**
	 * 
	 * Constructs a new <code>ServerGameEventMessageGenerator</code> object. 
	 *
	 */
	public ServerGameEventMessageGenerator() {
		super();
	}

	/**
	 * 
	 * Document the createMessage method 
	 *
	 * @param we
	 * @return Message
	 */
	public Message createMessage(WizardEvent we) {
		Message message;
		if(we instanceof GameOverEvent) {
			GameOverEvent event = (GameOverEvent) we;
			message = handleGameOver(event);
		}else if(we instanceof HandDealtEvent) {
			HandDealtEvent event = (HandDealtEvent) we;
			message = handleHandDealt(event);
		}else if(we instanceof NewGameEvent) {
			NewGameEvent event = (NewGameEvent) we;
			message = handleNewGame(event);
		} else if(we instanceof NewRoundEvent) {
			NewRoundEvent event = (NewRoundEvent) we;
			message = handleNewRound(event);
		} else if(we instanceof NewTrickEvent) {
			NewTrickEvent event = (NewTrickEvent) we;
			message = handleNewTrick(event);
		} else if(we instanceof NewTrumpEvent) {
			NewTrumpEvent event = (NewTrumpEvent) we;
			message = handleNewTrump(event);
		} else if(we instanceof PlayerBidEvent) {
			PlayerBidEvent event = (PlayerBidEvent) we;
			message = handlePlayerBid(event);
		} else if(we instanceof PlayerPlayedEvent) {
			PlayerPlayedEvent event = (PlayerPlayedEvent) we;
			message = handlePlayerPlayed(event);
		} else if(we instanceof PlayerWonTrickEvent) {
			PlayerWonTrickEvent event = (PlayerWonTrickEvent) we;
			message = handlePlayerWonTrick(event);
		} else if(we instanceof ScoreEvent) {
			ScoreEvent event = (ScoreEvent) we;
			message = handleScore(event);
		} else if(we instanceof PlayerNeedsToPlay) {
			PlayerNeedsToPlay event = (PlayerNeedsToPlay) we;
			message = handlePlayerNeedsToPlay(event);
		} else {
			message = null;
		}

		return message;
	}

	private Message handleGameOver(GameOverEvent e) {
		Message msg = new Message(MessageType.GAME_OVER);
		for(String name:e.getWinningPlayers()) {
			msg.addMsgParam(new MessageParam(MessageParamConstants.PLAYER_NAME, name));
		}
		for(Integer id:e.getIds()) {
			msg.addMsgParam(new MessageParam(MessageParamConstants.PLAYER_ID, String.valueOf(id)));
		}
		return msg;
	}

	private Message handleHandDealt(HandDealtEvent e) {
		Message msg = new Message(MessageType.HAND_DEALT);
		handlePlayerEvent(e, msg);
		return msg;
	}

	private Message handleNewGame(NewGameEvent e) {
		Message msg = new Message(MessageType.NEW_GAME);
		for(String name:e.getPlayerNames()) {
			msg.addMsgParam(new MessageParam(MessageParamConstants.PLAYER_NAME, name));
		}
		for(Integer id:e.getIds()) {
			msg.addMsgParam(new MessageParam(MessageParamConstants.PLAYER_ID, String.valueOf(id)));
		}
		return msg;
	}

	private Message handleNewRound(NewRoundEvent e) {
		Message msg = new Message(MessageType.NEW_ROUND);
		msg.addMsgParam(new MessageParam(MessageParamConstants.ROUND, String.valueOf(e.getRound())));
		return msg;
		
	}

	private Message handleNewTrick(NewTrickEvent e) {
		Message msg = new Message(MessageType.NEW_TRICK);
		return msg;
	}

	private Message handleNewTrump(NewTrumpEvent e) {
		Message msg = new Message(MessageType.NEW_TRUMP);
		Card card = e.getCard();
		if(card != null) {
			msg.addMsgParam(new MessageParam(MessageParamConstants.SUIT, card.getSuit().getShortName()));
			if(card.getValue() != null) {
				msg.addMsgParam(new MessageParam(MessageParamConstants.VALUE, card.getValue().getShortNm()));
			}
			msg.addMsgParam(new MessageParam(MessageParamConstants.INDEX, String.valueOf(card.getIndex())));
		}
		return msg;
		
	}

	private Message handlePlayerBid(PlayerBidEvent e) {
		Message msg = new Message(MessageType.PLAYER_BID);
		msg.addMsgParam(new MessageParam(MessageParamConstants.BID, String.valueOf(e.getBid())));
		handlePlayerEvent(e, msg);
		return msg;
		
	}

	private Message handlePlayerPlayed(PlayerPlayedEvent e) {
		Message msg = new Message(MessageType.PLAYER_PLAYED);
		Card card = e.getCard();
		msg.addMsgParam(new MessageParam(MessageParamConstants.SUIT, card.getSuit().getShortName()));
		msg.addMsgParam(new MessageParam(MessageParamConstants.VALUE, card.getValue().getShortNm()));
		msg.addMsgParam(new MessageParam(MessageParamConstants.INDEX, String.valueOf(card.getIndex())));
		handlePlayerEvent(e, msg);
		return msg;
		
	}

	private Message handlePlayerWonTrick(PlayerWonTrickEvent e) {
		Message msg = new Message(MessageType.PLAYER_WON_TRICK);
		handlePlayerEvent(e, msg);
		Card card = e.getCard();
		msg.addMsgParam(new MessageParam(MessageParamConstants.SUIT, card.getSuit().getShortName()));
		msg.addMsgParam(new MessageParam(MessageParamConstants.VALUE, card.getValue().getShortNm()));
		msg.addMsgParam(new MessageParam(MessageParamConstants.INDEX, String.valueOf(card.getIndex())));		
		return msg;
	}

	private Message handleScore(ScoreEvent e) {
		Message msg = new Message(MessageType.SCORE_EVENT);
		msg.addMsgParam(new MessageParam(MessageParamConstants.SCORE, String.valueOf(e.getScoreChangeAmount())));
		handlePlayerEvent(e, msg);
		return msg;
	}

	private Message handlePlayerNeedsToPlay(PlayerNeedsToPlay e) {
		Message msg = new Message(MessageType.PLAYER_NEEDS_TO_PLAY);
		handlePlayerEvent(e, msg);
		return msg;
	}

	private void handlePlayerEvent(PlayerEvent e, Message msg) {
		//msg.addMsgParam(new MessageParam(MessageParamConstants.PLAYER_NAME, e.getPlayerName()));
		msg.addMsgParam(new MessageParam(MessageParamConstants.PLAYER_ID, String.valueOf(e.getPlayerId())));
	}
}