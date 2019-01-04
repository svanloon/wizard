package com.svanloon.game.wizard.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class MessageCommunicator {

	private Socket s;
	private PrintWriter os;
	private BufferedReader is;

	// symbols
	private static final String START_OPENING = "<";
	private static final String START_CLOSING = ">";
	private static final String FINAL_OPENING = "</";
	private static final String FINAL_CLOSING = "/>";
	private static final String QUOTE = "\"";
	private static final String SPACE = " ";
	private static final String EQUALS_PLUS_QUOTE = "=" + QUOTE;
	private static final String NL = "\n";

	// tag names
	private static final String MESSAGE_TYPE = "messageType";
	private static final String MESSAGE_PARAM = "messsageParam";
	private static final String MESSAGE = "message";

	// attributes
	private static final String NAME = "name";
	private static final String VALUE = "value";

	private static final String MESSAGE_OPENING_TAG = START_OPENING + MESSAGE + START_CLOSING;
	private static final String MESSAGE_CLOSING_TAG =  FINAL_OPENING + MESSAGE + START_CLOSING;

	/**
	 * 
	 * Constructs a new <code>MessageCommunicator</code> object. 
	 *
	 */
	public MessageCommunicator() {
		super();
	}

	/**
	 * 
	 * Document the setSocket method 
	 *
	 * @param mainSocket
	 * @throws IOException
	 */
	public void setSocket(Socket mainSocket) throws IOException {
		this.s = mainSocket;
		this.is = new BufferedReader(new InputStreamReader(mainSocket.getInputStream()));
		this.os = new PrintWriter(mainSocket.getOutputStream());
	}
	/**
	 * 
	 * Document the sendMessage method 
	 *
	 * @param message
	 * @throws IOException
	 */
	public synchronized void sendMessage(Message message) throws IOException {
		String msg = createString(message);
		send(msg);
	}


	/**
	 * 
	 * Document the sendMessageAndGetResponse method 
	 *
	 * @param message
	 * @return Message
	 * @throws IOException
	 */
	public synchronized Message sendMessageAndGetResponse(Message message) throws IOException {
		String msg = createString(message);
		send(msg);
		return receive();
	}

	/**
	 * 
	 * Document the receive method 
	 *
	 * @return Message
	 * @throws IOException
	 */
	public synchronized Message receive() throws IOException {
		Message msg = null;
		while(true) {
			String line = getBufferedReader().readLine();
			if(line == null) {
				continue;
			}
			if(line.equals(MESSAGE_OPENING_TAG)) {
				// do nothing
			} else if (line.equals(MESSAGE_CLOSING_TAG)) {
				break;
			} else if (line.indexOf(MESSAGE_TYPE) > -1) {
				msg = new Message(MessageType.valueOf(findValue(line)));
			} else {
				if(msg != null) {
					String name = findName(line);
					String value = findValue(line);
					msg.addMsgParam(new MessageParam(name, value));
				} else {
					// missing message type
				}
			}
		}
		return msg;
	}
	/**
	 * 
	 * Document the close method 
	 *
	 */
	public void close() {
		try {
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		os.close();

		try {
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private BufferedReader getBufferedReader() {
		return this.is;
	}

	private String findName(String line) {
		return findAttribute(line, NAME);
	}

	private String findValue(String line) {
		return findAttribute(line, VALUE);
	}

	private String findAttribute(String line, String attributeName) {
		String attribute = attributeName + EQUALS_PLUS_QUOTE;
		int indexOfName = line.indexOf(attribute);
		String cutLeft = line.substring(indexOfName + attribute.length());
		String value = cutLeft.substring(0, cutLeft.indexOf(QUOTE));
		return value;		
	}

	private String createString(Message message) {
		StringBuffer sb = new StringBuffer();
		sb.append(MESSAGE_OPENING_TAG);
		sb.append(NL);

		String messageType = message.getMessageType().toString();
		sb.append(START_OPENING);
		sb.append(MESSAGE_TYPE);
		sb.append(SPACE);
		sb.append(createAttribute(VALUE, messageType));
		sb.append(FINAL_CLOSING);
		sb.append(NL);

		for(MessageParam mp:message.getMsgParams()) {

			sb.append(START_OPENING);
			sb.append(MESSAGE_PARAM);
			sb.append(SPACE);
			sb.append(createAttribute(NAME, mp.getName()));
			sb.append(SPACE);
			sb.append(createAttribute(VALUE, mp.getValue()));
			sb.append(FINAL_CLOSING);
			sb.append(NL);

		}

		sb.append(MESSAGE_CLOSING_TAG);
		sb.append(NL);

		return sb.toString();
	}

	private String createAttribute(String name, String value){
		StringBuffer sb = new StringBuffer();
		sb.append(name);
		sb.append(EQUALS_PLUS_QUOTE);
		sb.append(value);
		sb.append(QUOTE);
		return sb.toString();
	}

	private synchronized void send(String msg) throws IOException {
		os.write(msg);
		os.flush();
	}
}