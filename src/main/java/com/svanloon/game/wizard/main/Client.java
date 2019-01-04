package com.svanloon.game.wizard.main;

import java.awt.Dimension;
import java.awt.Point;
import java.io.IOException;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import com.svanloon.common.network.im.IMClient;
import com.svanloon.game.wizard.human.HumanPlayerSwing;
import com.svanloon.game.wizard.human.dialog.PromptDialog;
import com.svanloon.game.wizard.human.dialog.UserPreferences;
import com.svanloon.game.wizard.human.dialog.UserPreferencesManager;
import com.svanloon.game.wizard.language.LanguageFactory;
import com.svanloon.game.wizard.language.MessageId;
import com.svanloon.game.wizard.network.Message;
import com.svanloon.game.wizard.network.MessageParam;
import com.svanloon.game.wizard.network.MessageParamConstants;
import com.svanloon.game.wizard.network.MessageReceiver;
import com.svanloon.game.wizard.network.MessageType;
import com.svanloon.game.wizard.network.command.ClientCommandNotifier;
import com.svanloon.game.wizard.network.event.ClientGameEventNotifier;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class Client implements Runnable {
	private static Logger _logger = Logger.getLogger(Client.class.getName());

	private int initialConnectionPort = 1975;
	
	//private int commandPort;
	//private int eventPort;

	private UserPreferences up;

	/**
	 * Constructs a new <code>Client</code> object. 
	 * @param up 
	 */
	public Client(UserPreferences up) {
		super();
		this.up = up;
	}

	public void run() {

		String name = getName();
		String ip = getIp();

		// estatablish connection with server
		MessageReceiver mr2 = null;
		Message message;
		try {
			mr2 = new MessageReceiver(ip, initialConnectionPort);
			message = mr2.receive();
			Message response = new Message(MessageType.GET_NAME);
			response.addMsgParam(new MessageParam(MessageParamConstants.PLAYER_NAME, name));
			mr2.sendMessage(response);
			mr2.close();
		} catch (UnknownHostException e2) {
			e2.printStackTrace();
			return;
		} catch (IOException e2) {
			e2.printStackTrace();
			return;
		} finally {
			if(mr2 != null) {
				mr2.close();
			}
		}
		
		int playerId = getPlayerId(message);
		int commandPort = getCommandPort(message);
		int eventPort = getEventPort(message);
		int messengerPort = getMessengerPort(message);
		int totalNumberOfPlayers = getTotalNumberOfPlayers(message);
		_logger.info("commandPort = " + commandPort);
		_logger.info("eventPort = " + eventPort);

		HumanPlayerSwing player = new HumanPlayerSwing(totalNumberOfPlayers, up);
		player.setUserPreferences(up);

		player.setName(name);
		player.setId(playerId);
		player.setVisible(true);

		ClientCommandNotifier mr = new ClientCommandNotifier(player, ip, commandPort);
		Thread mrt = new Thread(mr);
		mrt.setName("ClientCommandNotifier" + commandPort);
		mrt.start();

		MessageReceiver emr;
		try {
			emr = new MessageReceiver(ip, eventPort);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		ClientGameEventNotifier wen = new ClientGameEventNotifier(player, emr);
		Thread went = new Thread(wen);
		went.setName("ClientGameEventNotifier"+eventPort);
		went.start();

		IMClient imClient = new IMClient(name, ip, messengerPort);
		//imClient.setVisible(true);
		player.setImClient(imClient);
	}

	private int getCommandPort(Message message) {
		for(MessageParam mp:message.getMsgParams()) {
			if(mp.getName().equals(MessageParamConstants.COMMAND_PORT)) {
				return Integer.parseInt(mp.getValue());
			}
		}
		return -1;
	}

	private int getPlayerId(Message message) {
		for(MessageParam mp:message.getMsgParams()) {
			if(mp.getName().equals(MessageParamConstants.PLAYER_ID)) {
				return Integer.parseInt(mp.getValue());
			}
		}
		return -1;
	}

	private int getEventPort(Message message) {
		for(MessageParam mp:message.getMsgParams()) {
			if(mp.getName().equals(MessageParamConstants.EVENT_PORT)) {
				return Integer.parseInt(mp.getValue());
			}
		}
		return -1;
	}
	private int getMessengerPort(Message message) {
		for(MessageParam mp:message.getMsgParams()) {
			if(mp.getName().equals(MessageParamConstants.MESSENGER_PORT)) {
				return Integer.parseInt(mp.getValue());
			}
		}
		return -1;
	}

	private int getTotalNumberOfPlayers(Message message) {
		for(MessageParam mp:message.getMsgParams()) {
			if(mp.getName().equals(MessageParamConstants.NUMBER_OF_PLAYERS)) {
				return Integer.parseInt(mp.getValue());
			}
		}
		return -1;
	}

	private String getIp() {
		PromptDialog dialog = new PromptDialog(LanguageFactory.getInstance().getString(MessageId.ip), LanguageFactory.getInstance().getString(MessageId.whatIpDoYouWantToConnectTo), up.getIp(), false, 0, 255, -1);

		Point location = new Point(0,0);
		Dimension size = new Dimension(900,720);
		Dimension dialogSize = dialog.getSize();
		dialog.setLocation((int)(location.getX() + size.getWidth() / 2.0 - dialogSize.getWidth() / 2.0), (int)(location.getY() + size.getHeight()/2.0 - dialogSize.getHeight()/2.0));

		dialog.prompt();
		String ip = dialog.getValue();
		up.setIp(ip);
		new UserPreferencesManager().persist(up);
		return ip;
	}

	private String getName() {
		PromptDialog dialog = new PromptDialog(LanguageFactory.getInstance().getString(MessageId.name), LanguageFactory.getInstance().getString(MessageId.whatsYourName), up.getName(), false, 0, 255, -1);
		Point location = new Point(0,0);
		Dimension size = new Dimension(900,720);
		Dimension dialogSize = dialog.getSize();
		dialog.setLocation((int)(location.getX() + size.getWidth() / 2.0 - dialogSize.getWidth() / 2.0), (int)(location.getY() + size.getHeight()/2.0 - dialogSize.getHeight()/2.0));
		dialog.prompt();
		String name = dialog.getValue();
		up.setName(name);
		new UserPreferencesManager().persist(up);
		return name;
	}
}