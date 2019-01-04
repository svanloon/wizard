package com.svanloon.game.wizard.network.command;

import java.io.IOException;

import com.svanloon.game.wizard.client.player.Player;
import com.svanloon.game.wizard.core.card.Card;
import com.svanloon.game.wizard.core.card.Hand;
import com.svanloon.game.wizard.core.card.Suit;
import com.svanloon.game.wizard.network.Message;
import com.svanloon.game.wizard.network.MessageParam;
import com.svanloon.game.wizard.network.MessageParamConstants;
import com.svanloon.game.wizard.network.MessageType;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class ServerNetworkPlayer implements Player {

	private ServerCommandListener ms;
	/**
	 * 
	 * Constructs a new <code>ServerNetworkPlayer</code> object. 
	 *
	 * @param ms
	 * @throws IOException
	 */
	public ServerNetworkPlayer(ServerCommandListener ms) throws IOException {
		 this.ms = ms;
	}

	// things that should be known already
	private String name;
	private Hand hand = new Hand();
	public Hand getHand() {
		return hand;
	}
	/**
	 * 
	 * Document the setName method 
	 *
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	// things that don't require a response
	public void giveCard(Card card) {
		hand.add(card);
		sendNoResponse(MessageType.GIVE_CARD, card);
	}
	public void playCardIsNotValid(Card card) {
		hand.remove(card);
		hand.add(card);
		sendNoResponse(MessageType.PLAY_CARD_IS_NOT_VALID, card);
	}

	public void playCardIsValid(Card card) {
		hand.remove(card);
		sendNoResponse(MessageType.PLAY_CARD_IS_VALID, card);
	}
	private void sendNoResponse(MessageType cc, Card card) {
		try {
			ms.sendCard(cc, card);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// calls that require an answer
	public Suit pickTrump() {
		try {
			return ms.getSuit(MessageType.PICK_TRUMP);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Suit.HEART;
	}

	public Card playCard() {
		try {
			return ms.getCard(MessageType.PLAY_CARD);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this.hand.iterator().next();
	}

	public int bid(Card trump, int min, int max, int notAllowedToBid) {
		try {
			Message message = new Message(MessageType.BID);
			ms.addCard(message, trump);
			message.addMsgParam(new MessageParam(MessageParamConstants.MIN, String.valueOf(min)));
			message.addMsgParam(new MessageParam(MessageParamConstants.MAX, String.valueOf(max)));
			message.addMsgParam(new MessageParam(MessageParamConstants.BID_NOT_ALLOWED, String.valueOf(notAllowedToBid)));
			return ms.getInt(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}
	private int id;
	public int getId() {
		return id;
	}

	public void setId(int index) {
		this.id = index;
	}
}