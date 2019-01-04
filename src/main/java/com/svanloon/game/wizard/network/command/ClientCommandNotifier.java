package com.svanloon.game.wizard.network.command;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.svanloon.game.wizard.client.player.Player;
import com.svanloon.game.wizard.core.card.Card;
import com.svanloon.game.wizard.core.card.Suit;
import com.svanloon.game.wizard.core.card.Value;
import com.svanloon.game.wizard.network.Message;
import com.svanloon.game.wizard.network.MessageReceiver;
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
public class ClientCommandNotifier implements Runnable {
	private static Logger _logger = Logger.getLogger(ClientCommandNotifier.class);

	private Player player;
	private String ip;
	private int port;

	private MessageReceiver mr;
	/**
	 * 
	 * Constructs a new <code>ClientCommandNotifier</code> object. 
	 *
	 * @param player
	 * @param ip
	 * @param port
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public ClientCommandNotifier(Player player, String ip, int port) {
		super();
		this.player = player;
		this.ip = ip;
		this.port = port;
	}
	
	public void run() {
		try {
			this.mr = new MessageReceiver(this.ip, this.port);
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			while(true) {
				Message msg;
				try {
					msg = mr.receive();
					Message response = handleMessage(msg);
					mr.sendMessage(response);
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}
			}
		} finally {
			if(this.mr != null) {
				mr.close();
			}
		}
	}

	/**
	 * 
	 * Document the handleMessage method 
	 *
	 * @param msg
	 * @return Message
	 */
	public Message handleMessage(Message msg) {
		List<MessageParam> responseMps = new ArrayList<MessageParam>();
		List<MessageParam> mps = msg.getMsgParams();
		MessageType cc = msg.getMessageType();
		MessageType responseMessageType;
		if(cc.equals(MessageType.BID)) {
			Card card = getCard(mps);

			int min = findParameterAsInt(MessageParamConstants.MIN, mps);
			int max = findParameterAsInt(MessageParamConstants.MAX, mps);
			int bidNotAllowed = findParameterAsInt(MessageParamConstants.BID_NOT_ALLOWED, mps);
			int bid = player.bid(card, min, max, bidNotAllowed);

			responseMessageType = MessageType.BID;
			responseMps.add(new MessageParam(MessageParamConstants.BID, String.valueOf(bid)));
			
		//} else if (cc.equals(CommandConstants.GET_HAND)) {
		
		} else if (cc.equals(MessageType.GIVE_CARD)) {
			Card card = getCard(mps);
			player.giveCard(card);
			responseMessageType = MessageType.OK;
		} else if (cc.equals(MessageType.PICK_TRUMP)) {
			Suit suit = player.pickTrump();
			responseMessageType = MessageType.OK;
			responseMps.add(new MessageParam(MessageParamConstants.SUIT, suit.getShortName()));
		} else if (cc.equals(MessageType.PLAY_CARD)) {
			Card card = player.playCard();
			responseMessageType = MessageType.OK;
			responseMps.add(new MessageParam(MessageParamConstants.SUIT, card.getSuit().getShortName()));
			responseMps.add(new MessageParam(MessageParamConstants.VALUE, card.getValue().getShortNm()));
		} else if (cc.equals(MessageType.PLAY_CARD_IS_NOT_VALID)) {
			Card card = getCard(mps);
			player.playCardIsNotValid(card);
			responseMessageType = MessageType.OK;
		} else if (cc.equals(MessageType.PLAY_CARD_IS_VALID)) {
			Card card = getCard(mps);
			player.playCardIsValid(card);
			responseMessageType = MessageType.OK;
		} else {
			responseMessageType = MessageType.OK;
		}

		Message response = new Message(responseMessageType);
		response.setMsgParams(responseMps);
		return response;
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
			_logger.info("suitShortName is null");
			return null;
		}
		_logger.info("suitShortName = " + suitShortName);

		Value value;
		if(valueShortName == null) {
			value = null;
			_logger.info("valueShortName is null");
		} else {
			value = Value.findValueByShortName(valueShortName);
			_logger.info("valueShortName = " + valueShortName);
		}
		Suit suit = Suit.findSuitByShortName(suitShortName);
		
		return new Card(value, suit, index);
	}

	private int findParameterAsInt(String name, List<MessageParam> mps) {
		for(MessageParam mp:mps) {
			if(mp.getName().equals(name.toString())) {
				return Integer.parseInt(mp.getValue());
			}
		}
		return -1;
	}
}
