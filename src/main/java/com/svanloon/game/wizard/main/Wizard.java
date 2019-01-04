package com.svanloon.game.wizard.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.svanloon.game.wizard.computer.ComputerPlayer;
import com.svanloon.game.wizard.core.gameOptions.GameOptions;
import com.svanloon.game.wizard.human.HumanPlayerSwing;
import com.svanloon.game.wizard.human.dialog.UserPreferences;
import com.svanloon.game.wizard.language.LanguageFactory;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class Wizard {
	private static final String HOSTED_THREAD = "Hosted";
	private static final String CONNECT_TO_A_GAME_THREAD = "Connect to a game";
	private static final String WIZARD_THREAD = "Wizard Thread";

	/**
	 * Constructs a new <code>Wizard</code> object. 
	 */
	public Wizard() {
		super();
	}

	private void play() {
		SplashScreen splashScreen = new SplashScreen();
		splashScreen.prompt();
		UserPreferences up = splashScreen.getUserPreferences();
		GameOptions go = splashScreen.getGameOptions();
		GameType gameType = splashScreen.getGameType();
		Locale locale = up.getLocale();

		LanguageFactory.setLocale(locale);

		// prompt to start game, host game, or connect to host
		if(gameType.equals(GameType.HOSTED)) {
			int numberOfPlayers = go.getTotalNumberOfPlayers();
			HumanPlayerSwing hps = new HumanPlayerSwing(numberOfPlayers, up);
			hps.setVisible(true);
			hps.setUserPreferences(up);
			Server server = new Server(hps, go, numberOfPlayers);
			server.setNumberOfPlayers(go.getTotalHumanPlayers());
			List<String> names = createListOfRandomNames();
			int counter = 0;
			for(String name : names) {
				server.addBot(new ComputerPlayer(name));
				counter++;
				if(counter == 6) {
					break;
				}
			}
			Thread t2 = new Thread(server);
			t2.setName(HOSTED_THREAD);
			t2.start();
		} else if(gameType.equals(GameType.CONNECT_TO_A_GAME)) {
			Client client = new Client(up);
			Thread t3 = new Thread(client);
			t3.setName(CONNECT_TO_A_GAME_THREAD);
			t3.start();
		}
	}

	private List<String> createListOfRandomNames() {
		List<String> shuffled = new ArrayList<String>();
		List<String> names = new ArrayList<String>();
		names.add("Carl");
		names.add("Judith");
		names.add("Jenny");
		names.add("Andrew");
		names.add("Emil");
		names.add("Jesse");

		int times = names.size();
		for (int i = 0; i < times; i++) {
			int randomCardNumber = (int) (Math.random() * names.size());
			shuffled.add(names.get(randomCardNumber));
			names.remove(randomCardNumber);
		}
		return shuffled;
	}
	
	/**
	 * 
	 * Document the main method 
	 *
	 * @param args
	 */
	public static void main(String args[]) {
		Wizard wizard = new Wizard();
		wizard.play();
		Thread.currentThread().setName(WIZARD_THREAD);
	}
}
