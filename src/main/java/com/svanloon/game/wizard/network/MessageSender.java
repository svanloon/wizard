package com.svanloon.game.wizard.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class MessageSender extends MessageCommunicator {

	private int port;
	/**
	 * 
	 * Constructs a new <code>MessageSender</code> object. 
	 *
	 * @param port
	 * @throws IOException
	 */
	public MessageSender(int port) throws IOException {
		super();
		this.port = port;
		ServerSocket serverSocket = new ServerSocket(this.port);
		Socket mainSocket = serverSocket.accept();
		setSocket(mainSocket);

	}
}
