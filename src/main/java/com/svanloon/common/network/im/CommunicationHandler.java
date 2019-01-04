package com.svanloon.common.network.im;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;
import org.apache.log4j.Logger;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class CommunicationHandler implements Runnable {

	private List<CommunicationListener> listeners = new ArrayList<CommunicationListener>(); 
	private static Logger _logger = Logger.getLogger(CommunicationHandler.class);
	private Socket mainSocket;

	private String ip;
	private int port;
	private BufferedReader in = null;
	private PrintWriter out = null;

	private boolean setup = false;

	/**
	 * Constructs a new <code>IMServer</code> object. 
	 * @param port 
	 */
	public CommunicationHandler(int port) {
		super();
		this.port = port;
	}

	/**
	 * Constructs a new <code>IMServer</code> object. 
	 * @param ip 
	 * @param port 
	 */
	public CommunicationHandler(String ip, int port) {
		super();
		this.ip = ip;
		this.port = port;
	}

	private void setupConnection() throws IOException {
		if(ip == null) {
			_logger.info("waiting on port " + port);
			ServerSocket serverSocket = new ServerSocket(port);
			mainSocket = serverSocket.accept();
		} else {
			_logger.info("connecting to " + ip + ":"+ port);
			mainSocket = new Socket(ip, port);
		}
		in = new BufferedReader(new InputStreamReader(mainSocket.getInputStream(), "UTF-8"));
		out = new PrintWriter(new OutputStreamWriter(mainSocket.getOutputStream(), "UTF-8"), true);
		_logger.info("successfully opening connection");
	}

	/**
	 * 
	 * Document the addListener method 
	 *
	 * @param listener
	 */
	public void addListener(CommunicationListener listener) {
		listeners.add(listener);
	}

	public void run() {
		try {
			try {
				setupConnection();
			} catch (IOException e1) {
				_logger.error("exception opening socket", e1);
				return;
			}

			setup = true;

			while (true) {
				String line;
				try {
					// I think this is a blocking call
					_logger.info("waiting for line to read");
					line = in.readLine();
				} catch (IOException e) {
					_logger.error("exception reading in", e);
					return;
				}
 
				if (line != null) {
					//line = decryptString(line);
					for(CommunicationListener listener:listeners) {
						listener.receiveMessage(line);
					}
				} else {
					//_logger.warn("line was null");
				}

				try {
					Thread.sleep(200);
				} catch (InterruptedException i) {
					_logger.warn("InterruptException: " + i.getMessage());
				}
			}
		} finally {
			try {
				if(mainSocket != null) {
					mainSocket.close();
				}
			} catch (IOException e) {
				_logger.error("exception closing mainSocket", e);
			}
			try {
				if(in != null) {
					in.close();
				}
			} catch (IOException e) {
				_logger.error("exception closing in", e);
			}
			if(out != null) {
				out.close();
			}

			mainSocket = null;
			in = null;
			out = null;
			setup = false;
		}
	}

	/**
	 * Document the send method 
	 * @param message
	 */
	public void send(String message) {
		if(setup == false) {
			_logger.info("not setup yet. ignoring message " + message);
			return;
		}
		_logger.info("sending message " + message);
		//message = encryptString(message);
		out.write(message + "\n");
		out.flush();
		_logger.info("sent message");
	}

/*	private synchronized static String decryptString(String input) {
		if (input == null) {
			return null;
		}

		BASE64Decoder b64d = new BASE64Decoder();

		try {
			byte[] dByte = b64d.decodeBuffer(input);
			String decodedString = new String(dByte);
			return decodedString;
		} catch (IOException ioe) {
			System.out
					.println("Security.decryptString(): decryption attempt failed");
			return null;
		}
	}

	private synchronized static String encryptString(String input) {
		if (input == null) {
			return null;
		}

		BASE64Encoder b64e = new BASE64Encoder();
		byte[] srcByte = input.getBytes();
		String encodedString = b64e.encode(srcByte);
		return encodedString;
	}*/

	/**
	 * Returns the setup.
	 *
	 * @return the setup.
	 */
	public boolean isSetup() {
		return setup;
	}
}