package com.svanloon.game.wizard.network;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class MessageReceiver extends MessageCommunicator {

	private String ip;
	private int port;
	/**
	 * 
	 * Constructs a new <code>MessageReceiver</code> object. 
	 *
	 * @param ip
	 * @param port
	 * @throws UnknownHostException
	 * @throws IOException
	 */
	public MessageReceiver(String ip, int port) throws UnknownHostException, IOException {
		super();
		this.ip = ip;
		this.port = port;
		Socket mainSocket = new Socket(this.ip, this.port);
		setSocket(mainSocket);
	}
}
