package com.svanloon.game.wizard.main;

import java.awt.Dimension;
import java.awt.Point;
import java.io.IOException;
import java.net.BindException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.svanloon.common.network.im.IMClient;
import com.svanloon.common.network.im.IMServer;
import com.svanloon.game.wizard.client.event.GameEventListener;
import com.svanloon.game.wizard.client.player.Player;
import com.svanloon.game.wizard.core.gameOptions.GameOptions;
import com.svanloon.game.wizard.engine.WizardEngine;
import com.svanloon.game.wizard.human.HumanPlayerSwing;
import com.svanloon.game.wizard.human.dialog.PromptDialog;
import com.svanloon.game.wizard.human.dialog.UserPreferences;
import com.svanloon.game.wizard.human.dialog.UserPreferencesManager;
import com.svanloon.game.wizard.language.LanguageFactory;
import com.svanloon.game.wizard.language.MessageId;
import com.svanloon.game.wizard.network.Message;
import com.svanloon.game.wizard.network.MessageParam;
import com.svanloon.game.wizard.network.MessageParamConstants;
import com.svanloon.game.wizard.network.MessageSender;
import com.svanloon.game.wizard.network.MessageType;
import com.svanloon.game.wizard.network.command.ServerCommandListener;
import com.svanloon.game.wizard.network.command.ServerNetworkPlayer;
import com.svanloon.game.wizard.network.event.ServerGameEventListener;
import com.svanloon.game.wizard.stats.Game;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class Server implements Runnable {
	private static Logger _logger = Logger.getLogger(Server.class);
	private static final String NOT_ENOUGH_COMPUTER_PLAYERS = "Not enough computer players";
	private static final String WIZARD_ENGINE_THREAD = "WizardEngine";
	private Game game;
	private int initialConnectionPort = 1975;
	
	private int initialCommandPort = 2075;
	private int initialEventPort = 2176;

	private IMServer imServer = new IMServer();
	private Integer numberOfPlayers = null;
	private Collection<Player> bots = new ArrayList<Player>();
	private GameOptions go;
	private HumanPlayerSwing hps;
	private Collection<GameEventListener> listeners = new ArrayList<GameEventListener>();
	private int totalNumberOfPlayers;


	/**
	 * Constructs a new <code>Server</code> object. 
	 * @param hps 
	 * @param go 
	 * @param totalNumberOfPlayers 
	 */
	public Server(HumanPlayerSwing hps, GameOptions go, int totalNumberOfPlayers) {
		super();
		this.hps = hps;
		this.go = go;
		this.totalNumberOfPlayers = totalNumberOfPlayers;
	}

	/**
	 * 
	 * Document the addBot method 
	 *
	 * @param listener
	 */
	public void addGameEventListener(GameEventListener listener) {
		listeners.add(listener);
	}

	/**
	 * 
	 * Document the addBot method 
	 *
	 * @param player
	 */
	public void addBot(Player player) {
		bots.add(player);
	}

	public void run() {
		//_logger.info("Starting Server");
		int numberOfHumanPlayers = getNumberOfPlayers();

		WizardEngine wizardEngine = new WizardEngine(go);

		Iterator<Player> botsIterator = bots.iterator();

		List<Player> players = new ArrayList<Player>();

		for (GameEventListener listener: listeners) {
			wizardEngine.addGameEventListener(listener);
		}

		for(int totalPlayerNumberIndex = 0 ; totalPlayerNumberIndex < totalNumberOfPlayers; totalPlayerNumberIndex++) {
			//_logger.info("Getting player " + i + "'s information");
			int id = (totalPlayerNumberIndex+1)*21601;
			Player player;
			if((numberOfHumanPlayers == -1) || // computer players play themselves
			   (numberOfHumanPlayers == 0 && totalPlayerNumberIndex > 0) || // assumes 1 player + n-1 computers 
			   (numberOfHumanPlayers == 1 && totalPlayerNumberIndex > 0) ||  // assumes 1 player + n-1 computers
			   (numberOfHumanPlayers == 2 && 
					   (
							 (totalNumberOfPlayers == 3 && totalPlayerNumberIndex == 1) ||
						     (totalNumberOfPlayers == 4 && (totalPlayerNumberIndex == 1 || totalPlayerNumberIndex == 3)) ||
						     (totalNumberOfPlayers == 5 && (totalPlayerNumberIndex == 1 || totalPlayerNumberIndex == 3 || totalPlayerNumberIndex == 4)) ||
						     (totalNumberOfPlayers == 6 && (totalPlayerNumberIndex == 1 || totalPlayerNumberIndex == 3 || totalPlayerNumberIndex == 4 || totalPlayerNumberIndex == 5))
					   )
			   ) ||
			   (numberOfHumanPlayers == 3 && 
					   (
						     (totalNumberOfPlayers == 4 && (totalPlayerNumberIndex == 3)) ||
						     (totalNumberOfPlayers == 5 && (totalPlayerNumberIndex == 1 || totalPlayerNumberIndex == 3)) ||
						     (totalNumberOfPlayers == 6 && (totalPlayerNumberIndex == 1 || totalPlayerNumberIndex == 3 || totalPlayerNumberIndex == 5))
					   )
			   ) ||
			   (numberOfHumanPlayers == 4 && 
					   (
						     (totalNumberOfPlayers == 5 && (totalPlayerNumberIndex == 1)) ||
						     (totalNumberOfPlayers == 6 && (totalPlayerNumberIndex == 1 || totalPlayerNumberIndex == 3))
					   )
			   ) ||
			   (numberOfHumanPlayers == 5 && 
					   (
						     (totalNumberOfPlayers == 6 && (totalPlayerNumberIndex == 1 ))
					   )
			   )
			   
			 ) {
				
				Player bot;
				if (botsIterator.hasNext()) {
					bot = botsIterator.next();

					if (bot instanceof GameEventListener) {
						wizardEngine.addGameEventListener((GameEventListener) bot );
					}

					player = bot;
				} else {
					_logger.error(NOT_ENOUGH_COMPUTER_PLAYERS);
					throw new IllegalStateException(NOT_ENOUGH_COMPUTER_PLAYERS);
				}
				
			} else if (numberOfHumanPlayers > 1 && totalPlayerNumberIndex > 0) {
				try {
					player = getNetworkPlayer(wizardEngine, totalPlayerNumberIndex, id, go);
				} catch (IOException e) {
					e.printStackTrace();
					return;
				}
			} else {
				player = getLocalPlayer(wizardEngine);
			}
			player.setId(id);
			
			players.add(player);
		}
	
		wizardEngine.addPlayers(players);
		
		Thread t = new Thread(wizardEngine);
		t.setName(WIZARD_ENGINE_THREAD);
		t.start();

		try {
			t.join();
		} catch (Exception e) {
			_logger.error(e);
		}

		game = wizardEngine.getGame();
	}

	/**
	 * 
	 * Document the getGame method 
	 *
	 * @return Game
	 */
	public Game getGame() {
		return game;
	}

	/**
	 * 
	 * Document the setNumberOfPlayers method 
	 *
	 * @param numberOfPlayers
	 */
	public void setNumberOfPlayers(int numberOfPlayers) {
		this.numberOfPlayers = Integer.valueOf(numberOfPlayers);
	}

	private int getNumberOfPlayers() {
		if(numberOfPlayers == null) {
			PromptDialog dialog = new PromptDialog(LanguageFactory.getInstance().getString(MessageId.players), LanguageFactory.getInstance().getString(MessageId.howManyHumanPlayersDoYouWant), "", true, -1, 4, -2);
			Point location = new Point(0,0);
			Dimension size = new Dimension(900,720);
			Dimension dialogSize = dialog.getSize();
			dialog.setLocation((int)(location.getX() + size.getWidth() / 2.0 - dialogSize.getWidth() / 2.0), (int)(location.getY() + size.getHeight()/2.0 - dialogSize.getHeight()/2.0));
			dialog.prompt();
			return dialog.getIntValue();
		}

		return numberOfPlayers.intValue();
	}

	private Player getLocalPlayer(WizardEngine wizardEngine) {
		String name;
		if(hps.getName() == null) {
			name = getLocalName();	
		} else {
			name = hps.getName();
		}
		
		HumanPlayerSwing player = hps;
		player.setName(name);
		wizardEngine.addGameEventListener(player);

		int port = imServer.getNextPort();
		imServer.addListener(port);
		IMClient imClient = new IMClient(name, "localhost", port);
		player.setImClient(imClient);

		return player;
	}

	private String getLocalName() {
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

	private Player getNetworkPlayer(WizardEngine wizardEngine, int i, int id, GameOptions pGo) throws IOException {
		int eventPort = initialEventPort + i;
		int commandPort = initialCommandPort + i;

		int messengerPort = imServer.getNextPort();
		imServer.addListener(messengerPort);

		_logger.info("creating game event listener");
		ServerGameEventListener listener = new ServerGameEventListener(eventPort);
		Thread t = new Thread(listener);
		t.setName("GameEventListener" + eventPort);
		t.start();
		_logger.info("created game event listener on port " + eventPort );

		_logger.info("creating command listener");
		ServerCommandListener scl = new ServerCommandListener(commandPort);
		Thread t2 = new Thread(scl);
		t2.setName("CommandListener" + commandPort);
		t2.start();
		_logger.info("created command listener on port " + commandPort);

		String name = getName(id, eventPort, commandPort,messengerPort, pGo);
		_logger.info("Got name = " + name + " over the network");

		wizardEngine.addGameEventListener(listener);

		_logger.info("creating player");
		ServerNetworkPlayer np = new ServerNetworkPlayer(scl);
		_logger.info("created player");
		np.setName(name);

		return np;
	}

	private Message createGetNameMessage(int id, int eventPort, int commandPort, int messengerPort, GameOptions pGo) {
		Message msg = new Message(MessageType.GET_NAME);
		msg.addMsgParam(new MessageParam(MessageParamConstants.PLAYER_ID, String.valueOf(id)));
		msg.addMsgParam(new MessageParam(MessageParamConstants.COMMAND_PORT, String.valueOf(commandPort)));
		msg.addMsgParam(new MessageParam(MessageParamConstants.EVENT_PORT, String.valueOf(eventPort)));
		msg.addMsgParam(new MessageParam(MessageParamConstants.MESSENGER_PORT, String.valueOf(messengerPort)));
		msg.addMsgParam(new MessageParam(MessageParamConstants.NUMBER_OF_PLAYERS, String.valueOf(pGo.getTotalNumberOfPlayers())));
		return msg;
	}

	private String getName(int id, int eventPort, int commandPort, int messengerPort, GameOptions pGo) {
		_logger.info("waiting on port " + initialConnectionPort);
		MessageSender ms = null;
		try {
			while(true) {
				try {
					ms = new MessageSender(initialConnectionPort);
					Message msg = createGetNameMessage(id, eventPort, commandPort, messengerPort, pGo);
					Message response = ms.sendMessageAndGetResponse(msg);
					for(MessageParam mp :response.getMsgParams()) {
						if(mp.getName().equals(MessageParamConstants.PLAYER_NAME)) {
							return mp.getValue();
						}
					}
				} catch (BindException be) {
					System.gc();
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					continue;
				} catch (IOException e) {
					e.printStackTrace();
					return "ERROR " + eventPort;
				}
			}
		} finally {
			if(ms != null) {
				ms.close();
			}
		}
	}
}