package com.svanloon.game.wizard.main;

import java.awt.Dimension;
import java.awt.Point;
import java.io.IOException;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import com.svanloon.game.wizard.client.event.GameEventListener;
import com.svanloon.game.wizard.client.player.Player;
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

public class BotRunner implements Runnable {
	private static Logger _logger = Logger.getLogger(BotRunner.class);

	private int initialConnectionPort = 1975;

	private int commandPort;
	private int eventPort;

	private Player scottPlayer;

	/**
	 * Constructs a new <code>Client</code> object.
	 * @param scottPlayer
	 */
	public BotRunner(Player scottPlayer) {
		super();
		this.scottPlayer = scottPlayer;
	}

	/**
	 *
	 * Document the main method
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		Player player = null;
		BotRunner client = new BotRunner(player);
		Thread t = new Thread(client);
		t.start();
		try {
			t.join();
		} catch (Exception e) {
			_logger.error(e);
		}
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

		commandPort = getCommandPort(message);
		eventPort = getEventPort(message);
		_logger.info("commandPort = " + commandPort);
		_logger.info("eventPort = " + eventPort);

		Player player = scottPlayer;

		ClientCommandNotifier mr = new ClientCommandNotifier(player, ip, commandPort);
		Thread mrt = new Thread(mr);
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

		ClientGameEventNotifier wen = new ClientGameEventNotifier((GameEventListener)player, emr);
		Thread went = new Thread(wen);
		went.start();

	}

	private int getCommandPort(Message message) {
		for(MessageParam mp:message.getMsgParams()) {
			if(mp.getName().equals(MessageParamConstants.COMMAND_PORT)) {
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

	private String getIp() {
		UserPreferences up = new UserPreferencesManager().load();
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
		UserPreferences up = new UserPreferencesManager().load();
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