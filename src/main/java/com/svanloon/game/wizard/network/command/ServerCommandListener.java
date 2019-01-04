package com.svanloon.game.wizard.network.command;

import java.io.IOException;
import java.util.List;

import com.svanloon.game.wizard.core.card.Card;
import com.svanloon.game.wizard.core.card.Suit;
import com.svanloon.game.wizard.core.card.Value;
import com.svanloon.game.wizard.network.Message;
import com.svanloon.game.wizard.network.MessageSender;
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
public class ServerCommandListener implements Runnable {

	public void run() {
		try {
			ms = new MessageSender(this.port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private MessageSender ms;
	private int port;
	/**
	 * 
	 * Constructs a new <code>ServerCommandListener</code> object. 
	 *
	 * @param port
	 */
	public ServerCommandListener(int port) {
		super();
		this.port = port;
		
	}

	/**
	 * 
	 * Document the sendCard method 
	 *
	 * @param cc
	 * @param card
	 * @throws IOException
	 */
	public void sendCard(MessageType cc, Card card) throws IOException {
		Message message = new Message(cc);
		message.addMsgParam(new MessageParam(MessageParamConstants.SUIT, card.getSuit().getShortName()));
		message.addMsgParam(new MessageParam(MessageParamConstants.VALUE, card.getValue().getShortNm()));
		message.addMsgParam(new MessageParam(MessageParamConstants.INDEX, String.valueOf(card.getIndex())));
		ms.sendMessageAndGetResponse(message);
	}

	/**
	 * 
	 * Document the getSuit method 
	 *
	 * @param cc
	 * @return Suit
	 * @throws IOException
	 */
	public Suit getSuit(MessageType cc) throws IOException {
		Message message = new Message(cc);
		List<MessageParam> mps = send(message).getMsgParams();
		String suitShortName = null;
		for(MessageParam mp:mps) {
			if(mp.getName().equals(MessageParamConstants.SUIT.toString())) {
				suitShortName = mp.getValue();
			}
		}
		Suit suit = Suit.findSuitByShortName(suitShortName);
		return suit;
	}
	/**
	 * 
	 * Document the getCard method 
	 *
	 * @param cc
	 * @return Card
	 * @throws IOException
	 */
	public Card getCard(MessageType cc) throws IOException {
		Message message = new Message(cc);
		List<MessageParam> mps = send(message).getMsgParams();
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
		Suit suit = Suit.findSuitByShortName(suitShortName);
		Value value = Value.findValueByShortName(valueShortName);
		return new Card(value, suit, index);
	}

	/**
	 * 
	 * Document the getInt method 
	 *
	 * @param message
	 * @return int
	 * @throws IOException
	 */
	public int getInt(Message message) throws IOException {
		List<MessageParam> mps = send(message).getMsgParams();
		for(MessageParam mp:mps) {
			if(mp.getName().equals(MessageParamConstants.BID.toString())) {
				return Integer.parseInt(mp.getValue());
			}
		}
		return -1;
		
	}
	/**
	 * 
	 * Document the addCard method 
	 *
	 * @param message
	 * @param card
	 */
	public void addCard(Message message, Card card) {
		if(card != null) {
			message.addMsgParam(new MessageParam(MessageParamConstants.SUIT, card.getSuit().getShortName()));
			if(card.getValue() != null) {
				message.addMsgParam(new MessageParam(MessageParamConstants.VALUE, card.getValue().getShortNm()));
			}
			message.addMsgParam(new MessageParam(MessageParamConstants.INDEX, String.valueOf(card.getIndex())));
		}
	}

	private Message send(Message msg) throws IOException {
		Message response = ms.sendMessageAndGetResponse(msg);
		return response;
	}
}
