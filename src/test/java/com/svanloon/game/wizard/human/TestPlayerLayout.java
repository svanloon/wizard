package com.svanloon.game.wizard.human;

import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JTextField;

import com.svanloon.game.wizard.human.gui.ScoreBoard;
import com.svanloon.game.wizard.language.LanguageFactory;
import com.svanloon.game.wizard.language.MessageId;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class TestPlayerLayout {
	/**
	 * 
	 * Constructs a new <code>TestSixPlayerLayout</code> object. 
	 *
	 */
	public TestPlayerLayout() {
		super();
	}

	/**
	 * 
	 * Document the main method 
	 *
	 * @param args
	 */
	public static void main(String args[]) {
		TestPlayerLayout testPlayerLayout = new TestPlayerLayout();
		testPlayerLayout.testThree();
		testPlayerLayout.testFour();
		testPlayerLayout.testFive();
		testPlayerLayout.testSix();
	}

	/**
	 * 
	 * Document the test method 
	 *
	 */
	public void testSix() {
		initializeComponents(6);
	}
	/**
	 * 
	 * Document the test method 
	 *
	 */
	public void testFive() {
		initializeComponents(5);
	}

	/**
	 * 
	 * Document the test method 
	 *
	 */
	public void testFour() {
		initializeComponents(4);
	}
	/**
	 * 
	 * Document the test method 
	 *
	 */
	public void testThree() {
		initializeComponents(3);
	}
	private void initializeComponents(int numberOfPlayers) {
		PlayerLayout wc = new SixPlayerLayout();
		switch(numberOfPlayers) {
			case 3:
				wc = new ThreePlayerLayout();
				break;
			case 4:
				wc = new FourPlayerLayout();
				break;
			case 5:
				wc = new FivePlayerLayout();
				break;
			case 6:
				wc = new SixPlayerLayout();
				break;
		}

		String deckDir = "deck/";
		// create components
		CardPlayedPanel trumpPanel = new CardPlayedPanel(LanguageFactory.getInstance().getString(MessageId.trump), deckDir);
		ScoreBoard scoreBoard = new ScoreBoard(numberOfPlayers);
		JTextField statusBar = new JTextField();
		statusBar.setEditable(false);
		statusBar.setBorder(BorderFactory.createLoweredBevelBorder());

		CardPlayedPanel northCardPlayedPanel = new CardPlayedPanel(LanguageFactory.getInstance().getString(MessageId.north), deckDir);
		CardPlayedPanel southCardPlayedPanel = new CardPlayedPanel(LanguageFactory.getInstance().getString(MessageId.south), deckDir);
		CardPlayedPanel eastCardPlayedPanel = new CardPlayedPanel(LanguageFactory.getInstance().getString(MessageId.east), deckDir);
		CardPlayedPanel westCardPlayedPanel = new CardPlayedPanel(LanguageFactory.getInstance().getString(MessageId.west), deckDir);
		CardPlayedPanel southEastCardPlayedPanel = new CardPlayedPanel("southeast", deckDir);
		CardPlayedPanel southSouthEastCardPlayedPanel = new CardPlayedPanel("southsoutheast", deckDir);

		FaceDownHandJPanel south = new FaceDownHandJPanel(deckDir);
		FaceDownHandJPanel north = new FaceDownHandJPanel(false, false, deckDir);
		FaceDownHandJPanel east = new FaceDownHandJPanel(true, true, deckDir);
		FaceDownHandJPanel west = new FaceDownHandJPanel(true, false, deckDir);
		FaceDownHandJPanel southEast = new FaceDownHandJPanel(true, false, deckDir);
		FaceDownHandJPanel southSouthEast = new FaceDownHandJPanel(true, false, deckDir);

		TrickSummary northTrick = new TrickSummary();
		TrickSummary southTrick = new TrickSummary();
		TrickSummary eastTrick = new TrickSummary();
		TrickSummary westTrick = new TrickSummary();
		TrickSummary southSouthEastTrick = new TrickSummary();
		TrickSummary southEastTrick = new TrickSummary();

		GuiPlayer southPlayer = new GuiPlayer();
		southPlayer.setCardPlayed(southCardPlayedPanel);
		southPlayer.setHandJPanel(south);
		southPlayer.setTrickSummary(southTrick);

		GuiPlayer westPlayer = new GuiPlayer();
		westPlayer.setCardPlayed(westCardPlayedPanel);
		westPlayer.setHandJPanel(west);
		westPlayer.setTrickSummary(westTrick);

		GuiPlayer northPlayer = new GuiPlayer();
		northPlayer.setCardPlayed(northCardPlayedPanel);
		northPlayer.setHandJPanel(north);
		northPlayer.setTrickSummary(northTrick);

		GuiPlayer eastPlayer = new GuiPlayer();
		eastPlayer.setCardPlayed(eastCardPlayedPanel);
		eastPlayer.setHandJPanel(east);
		eastPlayer.setTrickSummary(eastTrick);

		GuiPlayer southEastPlayer = new GuiPlayer();
		southEastPlayer.setCardPlayed(southEastCardPlayedPanel);
		southEastPlayer.setHandJPanel(southEast);
		southEastPlayer.setTrickSummary(southEastTrick);

		GuiPlayer southSouthEastPlayer = new GuiPlayer();
		southSouthEastPlayer.setCardPlayed(southSouthEastCardPlayedPanel);
		southSouthEastPlayer.setHandJPanel(southSouthEast);
		southSouthEastPlayer.setTrickSummary(southSouthEastTrick);

		// set listeners
		CardButtonMouseListener cbml = new CardButtonMouseListener(south);
		south.addMouseListener(cbml);

		// add to wc
		wc.addComponent(WizardComponentEnum.TRUMP, trumpPanel);

		wc.addComponent(WizardComponentEnum.NORTH_CARD_PLAYED, northCardPlayedPanel);
		wc.addComponent(WizardComponentEnum.SOUTH_CARD_PLAYED, southCardPlayedPanel);
		wc.addComponent(WizardComponentEnum.EAST_CARD_PLAYED, eastCardPlayedPanel);
		wc.addComponent(WizardComponentEnum.WEST_CARD_PLAYED, westCardPlayedPanel);
		wc.addComponent(WizardComponentEnum.SOUTH_EAST_CARD_PLAYED, southEastCardPlayedPanel);
		wc.addComponent(WizardComponentEnum.SOUTH_SOUTH_EAST_CARD_PLAYED, southSouthEastCardPlayedPanel);

		wc.addComponent(WizardComponentEnum.SOUTH_HAND, south);
		wc.addComponent(WizardComponentEnum.NORTH_HAND, north);
		wc.addComponent(WizardComponentEnum.EAST_HAND,east);
		wc.addComponent(WizardComponentEnum.WEST_HAND, west);
		
		wc.addComponent(WizardComponentEnum.SOUTH_EAST_HAND, southEast);
		wc.addComponent(WizardComponentEnum.SOUTH_SOUTH_EAST_HAND, southSouthEast);
		
		wc.addComponent(WizardComponentEnum.SCORE_BOARD, scoreBoard);
		wc.addComponent(WizardComponentEnum.NORTH_TRICK, northTrick);
		wc.addComponent(WizardComponentEnum.SOUTH_TRICK, southTrick);
		wc.addComponent(WizardComponentEnum.EAST_TRICK, eastTrick);
		wc.addComponent(WizardComponentEnum.WEST_TRICK, westTrick);
		wc.addComponent(WizardComponentEnum.SOUTH_EAST_TRICK, southEastTrick);
		wc.addComponent(WizardComponentEnum.SOUTH_SOUTH_EAST_TRICK, southSouthEastTrick);

		wc.addComponent(WizardComponentEnum.STATUS_BAR, statusBar);
		List<GuiPlayer> players = new ArrayList<GuiPlayer>();
		players.add(southPlayer);
		players.add(westPlayer);
		players.add(northPlayer);
		players.add(eastPlayer);
		players.add(southEastPlayer);
		players.add(southSouthEastPlayer);

		for(GuiPlayer player:players) {
			player.getHandJPanel().setRound(10);
			player.getHandJPanel().dealtCards();

			player.getTrickSummary().handleNewRound();
			player.getTrickSummary().handleBid(10);
			player.getTrickSummary().handleEndOfRound(90);

			player.getTrickSummary().handleNewRound();
			player.getTrickSummary().handleBid(10);
			player.getTrickSummary().handleTrickTaken();
			player.getTrickSummary().handleTrickTaken();
			player.getTrickSummary().handleTrickTaken();
			player.getTrickSummary().handleTrickTaken();
			
		}

		JFrame jFrame = new JFrame();
		jFrame.getContentPane().add(wc.getComponent());
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.pack();
		jFrame.setVisible(true);
	}

}
