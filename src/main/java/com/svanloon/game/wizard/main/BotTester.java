package com.svanloon.game.wizard.main;

import com.svanloon.game.wizard.computer.ComputerPlayer;
import com.svanloon.game.wizard.core.gameOptions.GameOptions;
import com.svanloon.game.wizard.stats.GameArchive;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class BotTester {
	/**
	 * Constructs a new <code>Wizard</code> object. 
	 */
	public BotTester() {
		super();
	}

	private void play() {
		GameOptions go = new GameOptions();
		go.setTotalNumberOfPlayers(4);
		go.setTotalHumanPlayers(-1);
		int numberOfPlayers = go.getTotalNumberOfPlayers();
		
		int numberOfGames = 1000;
		for(int i = 0; i < numberOfGames; i++) {
			Server server = new Server(null, go, numberOfPlayers);
			server.setNumberOfPlayers(go.getTotalHumanPlayers());
			server.addBot(new ComputerPlayer("Computer 1"));
			server.addBot(new ComputerPlayer("Computer 2"));
			server.addBot(new ComputerPlayer("Computer 3"));
			server.addBot(new ComputerPlayer("Computer 4"));
			server.addBot(new ComputerPlayer("Computer 5"));
			server.addBot(new ComputerPlayer("Computer 6"));
			server.run();
		}

		System.out.println(GameArchive.getInstance().summarize());
	}

	/**
	 * 
	 * Document the main method 
	 *
	 * @param args
	 */
	public static void main(String args[]) {
		BotTester wizard = new BotTester();
		wizard.play();
	}
}
