package com.svanloon.game.wizard.network;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class Message {
	private MessageType messageType;
	private List<MessageParam> msgParams = new ArrayList<MessageParam>();
	/**
	 * 
	 * Constructs a new <code>Message</code> object. 
	 *
	 * @param messageType
	 */
	public Message(MessageType messageType) {
		super();
		this.messageType = messageType;
	}
	/**
	 * @return the msgParams
	 */
	public List<MessageParam> getMsgParams() {
		return msgParams;
	}
	/**
	 * @param msgParams the msgParams to set
	 */
	public void setMsgParams(List<MessageParam> msgParams) {
		this.msgParams = msgParams;
	}
	
	/**
	 * 
	 * Document the addMsgParam method 
	 *
	 * @param mp
	 */
	public void addMsgParam(MessageParam mp) {
		this.msgParams.add(mp);
	}
	/**
	 * @return the messageType
	 */
	public MessageType getMessageType() {
		return messageType;
	}
}
