package com.svanloon.common.network.im;

import org.apache.log4j.Logger;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class IMServer implements CommunicationListener {
	private static Logger _logger = Logger.getLogger(IMServer.class);
	private int port = 1356;
	private CommunicationNotifier communicationNotifier = new CommunicationNotifier();

	/**
	 * Constructs a new <code>IMServer</code> object. 
	 */
	public IMServer() {
		super();
	}

	/**
	 * 
	 * Document the addListener method 
	 *
	 * @param pPort
	 */
	public void addListener(int pPort) {
		CommunicationHandler ch = new CommunicationHandler(pPort);
		ch.addListener(this);
		communicationNotifier.addHandler(ch);
		Thread t = new Thread(ch);
		t.start();
	}

	/**
	 * 
	 * Document the getNextPort method 
	 *
	 * @return bob
	 */
	public int getNextPort() {
		return port++;
	}

	public void receiveMessage(String message) {
		_logger.info("received message and going to try to notify " + message);
		communicationNotifier.notify(message + "\n");
	}

	/**
	 * 
	 * Document the main method 
	 *
	 * @param args
	 */
	public static void main(String args[]) {
		IMServer imServer = new IMServer();
		imServer.addListener(imServer.getNextPort());
		imServer.addListener(imServer.getNextPort());
	}
}
