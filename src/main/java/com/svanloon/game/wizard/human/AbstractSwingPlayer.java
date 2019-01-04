package com.svanloon.game.wizard.human;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;

import org.apache.log4j.Logger;

import com.svanloon.common.network.im.IMClient;
import com.svanloon.common.util.IconUtil;
import com.svanloon.game.wizard.client.event.GameOverEvent;
import com.svanloon.game.wizard.client.event.HandDealtEvent;
import com.svanloon.game.wizard.client.event.NewGameEvent;
import com.svanloon.game.wizard.client.event.NewRoundEvent;
import com.svanloon.game.wizard.client.event.NewTrickEvent;
import com.svanloon.game.wizard.client.event.NewTrumpEvent;
import com.svanloon.game.wizard.client.event.PlayerBidEvent;
import com.svanloon.game.wizard.client.event.PlayerNeedsToPlay;
import com.svanloon.game.wizard.client.event.PlayerPlayedEvent;
import com.svanloon.game.wizard.client.event.PlayerWonTrickEvent;
import com.svanloon.game.wizard.client.event.ScoreEvent;
import com.svanloon.game.wizard.client.player.Player;
import com.svanloon.game.wizard.client.player.temp.GameObserverListener;
import com.svanloon.game.wizard.core.card.Card;
import com.svanloon.game.wizard.core.card.Suit;
import com.svanloon.game.wizard.core.card.util.WizardCardGraph;
import com.svanloon.game.wizard.core.card.util.gui.CardGraphGui;
import com.svanloon.game.wizard.human.dialog.MessageDialog;
import com.svanloon.game.wizard.human.dialog.PickSuitDialog;
import com.svanloon.game.wizard.human.dialog.UserPreferences;
import com.svanloon.game.wizard.human.gui.ScoreBoard;
import com.svanloon.game.wizard.human.screen.CreditScreen;
import com.svanloon.game.wizard.human.screen.RulesScreen;
import com.svanloon.game.wizard.language.LanguageFactory;
import com.svanloon.game.wizard.language.MessageId;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public abstract class AbstractSwingPlayer extends GameObserverListener implements Player {
	private static Logger _logger = Logger.getLogger(AbstractSwingPlayer.class);

	private static final long serialVersionUID = 8101964106475257540L;
	protected int round = 0;
	private List<GuiPlayer> players = new ArrayList<GuiPlayer>();
	private GuiPlayer southPlayer;
	private GuiPlayer eastPlayer;
	private GuiPlayer westPlayer;
	private GuiPlayer northPlayer;
	private GuiPlayer southEastPlayer;
	private GuiPlayer southSouthEastPlayer;
	private IMClient imClient;
	private CardPlayedPanel trumpPanel;

	protected CardButtonMouseListener cbml;
	private ScoreBoard scoreBoard;

	private boolean isLead = false;
	private JTextField statusBar;

	private PlayerLayout wc;

	protected JFrame jFrame = new JFrame();
	private CardGraphGui cardTreeGui;
	private UserPreferences up;

	/**
	 * 
	 * Constructs a new <code>HumanPlayerSwing</code> object. 
	 * @param numberOfPlayers 
	 */
	public AbstractSwingPlayer(int numberOfPlayers, UserPreferences up) {
		super();
		this.up = up;
		jFrame.setSize(1200,720);
		initializeComponents(numberOfPlayers);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setTitle(LanguageFactory.getInstance().getString(MessageId.wizard));
		//jFrame.setResizable(false);
		Image image = IconUtil.getIcon();
		jFrame.setIconImage(image);

		JMenuBar menuBar = createMenuBar();

		jFrame.setJMenuBar(menuBar);

		WizardCardGraph wct = getWct();
		cardTreeGui = new CardGraphGui(wct);
		cardTreeGui.setVisible(false);
	}

	private JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		appendOptionsMenu(menuBar);
		appendHelpMenu(menuBar);

		return menuBar;
	}

	/**
	 * 
	 * Document the setUserPreferences method 
	 *
	 * @param up
	 */
	public void setUserPreferences(UserPreferences up) {
		scoreBoard.setVisible(up.getDisplayScoreBoard());
	}

	private void appendOptionsMenu(JMenuBar menuBar) {
		JMenu options = new JMenu(LanguageFactory.getInstance().getString(MessageId.OPTIONS));

		JMenuItem viewScore = new JMenuItem(LanguageFactory.getInstance().getString(MessageId.score));
		viewScore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scoreBoard.setVisible(scoreBoard.isVisible() == false);
			}
		});

		
		JMenuItem viewCardCount = new JMenuItem(LanguageFactory.getInstance().getString(MessageId.CARD_COUNTER));
		viewCardCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean x = isVisilibleCardCounter();
				setVisilibleCardCounter(!x);
			}
		});

		JMenuItem viewOpponentSummaryCount = new JMenuItem(LanguageFactory.getInstance().getString(MessageId.OPPONENT_SUMMARY));
		viewOpponentSummaryCount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean x = isVisibleOpponentSummary();
				setVisibleOpponentSummary(!x);
			}
		});
		
		JMenuItem viewIm = new JMenuItem(LanguageFactory.getInstance().getString(MessageId.IM));
		viewIm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(imClient != null) {
					imClient.setVisible(imClient.isVisible() == false);
				}
			}
		});

		options.add(viewScore);
		//options.add(viewCardCount);
		//options.add(viewOpponentSummaryCount);
		options.add(viewIm);

		menuBar.add(options);
	}


	private void appendHelpMenu(JMenuBar menuBar) {
		JMenu help = new JMenu(LanguageFactory.getInstance().getString(MessageId.HELP));

		JMenuItem rules = new JMenuItem(LanguageFactory.getInstance().getString(MessageId.RULES));
		help.add(rules);
		rules.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				RulesScreen is = new RulesScreen();
				is.show();				
			}
		});
		JMenuItem credit = new JMenuItem(LanguageFactory.getInstance().getString(MessageId.CREDITS));
		help.add(credit);
		credit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				CreditScreen cs = new CreditScreen();
				cs.show();
			}
		});
		menuBar.add(help);
	}

	@Override
	public void setName(String name) {
		super.setName(name);
		jFrame.setName(name);
	}

	private String findDirectoryDir() {
		if(up == null) {
			return "deck/";
		}
		String deckDir = up.getDeck();
		if(deckDir == null || deckDir.equals("") || deckDir.equalsIgnoreCase("Default")) {
			return "deck/";
		}

		String userHome = System.getProperty("user.home");
		File dir = new File(userHome + File.separator +  "wizard/expansion/decks/" + deckDir);
		if(dir.exists() && dir.isDirectory()) {
			return dir.getAbsolutePath() + File.separator;
		}
		return "deck/";
	}

	private void initializeComponents(int numberOfPlayers) {
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
			default:
				wc = null;
		}
		String deckDir = findDirectoryDir();
		// create components
		trumpPanel = new CardPlayedPanel(LanguageFactory.getInstance().getString(MessageId.trump), deckDir);
		scoreBoard = new ScoreBoard(numberOfPlayers);
		statusBar = new JTextField();
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

		southPlayer = new GuiPlayer();
		southPlayer.setCardPlayed(southCardPlayedPanel);
		southPlayer.setHandJPanel(south);
		southPlayer.setTrickSummary(southTrick);

		westPlayer = new GuiPlayer();
		westPlayer.setCardPlayed(westCardPlayedPanel);
		westPlayer.setHandJPanel(west);
		westPlayer.setTrickSummary(westTrick);

		northPlayer = new GuiPlayer();
		northPlayer.setCardPlayed(northCardPlayedPanel);
		northPlayer.setHandJPanel(north);
		northPlayer.setTrickSummary(northTrick);

		eastPlayer = new GuiPlayer();
		eastPlayer.setCardPlayed(eastCardPlayedPanel);
		eastPlayer.setHandJPanel(east);
		eastPlayer.setTrickSummary(eastTrick);

		southEastPlayer = new GuiPlayer();
		southEastPlayer.setCardPlayed(southEastCardPlayedPanel);
		southEastPlayer.setHandJPanel(southEast);
		southEastPlayer.setTrickSummary(southEastTrick);

		southSouthEastPlayer = new GuiPlayer();
		southSouthEastPlayer.setCardPlayed(southSouthEastCardPlayedPanel);
		southSouthEastPlayer.setHandJPanel(southSouthEast);
		southSouthEastPlayer.setTrickSummary(southSouthEastTrick);

		players.add(southPlayer);
		players.add(westPlayer);
		players.add(northPlayer);
		players.add(eastPlayer);
		players.add(southEastPlayer);
		players.add(southSouthEastPlayer);

		// set listeners
		cbml = new CardButtonMouseListener(south);
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
		jFrame.getContentPane().add(wc.getComponent());
		jFrame.pack();
	}

	@Override
	public void giveCard(Card card) {
		super.giveCard(card);
		southPlayer.getHandJPanel().repaint();
	}

	public abstract Card playCard();

	@Override
	public void playCardIsValid(Card card) {
		super.playCardIsValid(card);
		southPlayer.getCardPlayed().setCard(card);
		southPlayer.getHandJPanel().cardPlayedIsValid(card);
	}
	@Override
	public void playCardIsNotValid(Card card) {
		super.playCardIsNotValid(card);
		southPlayer.getHandJPanel().cardPlayedIsNotValid(card);
	}

	public abstract int bid(Card trump, int min, int max, int notAllowedToBid);


	@Override
	public void handleGameOver(GameOverEvent e) {
		super.handleGameOver(e);
		Collection<String> winningPlayers = e.getWinningPlayers();
		String msg;
		if(winningPlayers.size() > 1) {
			msg = "";
			int i = 0;
			boolean isFirst = true;
			for(String playerName:winningPlayers) {
				if(isFirst) {
					isFirst = false;
				} else {
					if(i == winningPlayers.size() - 1) {
						msg += ", ";
					} else {
						msg += " and ";
					}
				}
				msg += playerName;
				i++;
			}
			msg += " tied";
		} else {
			msg = LanguageFactory.getInstance().getString(MessageId.congratulationsToX, "playerName", winningPlayers.iterator().next()) ;
		}

		boolean win = false;
		for(Integer winningId : e.getIds()) {
			if(winningId.intValue() == getId()) {
				win = true;
			}
		}
		log(msg);
		MessageDialog md = new MessageDialog();
		Point location = jFrame.getLocation();
		Dimension size = jFrame.getSize();
		Dimension dialogSize = md.getSize();
		md.setLocation((int)(location.getX() + size.getWidth() / 2.0 - dialogSize.getWidth() / 2.0), (int)(location.getY() + size.getHeight()/2.0 - dialogSize.getHeight()/2.0));
		if(win) {
			md.setTitle(LanguageFactory.getInstance().getString(MessageId.youWon));
		} else {
			md.setTitle(LanguageFactory.getInstance().getString(MessageId.youLost));
		}
		md.display(msg, jFrame);
	}

	/**
	 * 
	 * Document the setVisible method 
	 *
	 * @param b
	 */
	public void setVisible(boolean b) {
		jFrame.setVisible(b);
	}

	@Override
	public void handleNewGame(NewGameEvent e) {
		super.handleNewGame(e);

		List<String> playerNames = e.getPlayerNames();
		List<Integer> ids = e.getIds();

		scoreBoard.addPlayers(ids, playerNames);

		int southIndex = 0;
		for(Integer id2: ids) {
			if(id2.intValue() == getId()) {
				break;
			}
			southIndex++;
		}

		int count = 0;
		Iterator<Integer> idsIter = ids.iterator();
		for(String playerName: playerNames) {
			int id2 = idsIter.next().intValue();
			int position = (count + (playerNames.size() - southIndex))%playerNames.size();
			if(position == 0) {
				southPlayer.setPlayerName(playerName);
				southPlayer.setId(id2);
			} else if(position == 1) {
				westPlayer.setPlayerName(playerName);
				westPlayer.setId(id2);
			} else if (position == 2) {
				northPlayer.setPlayerName(playerName);
				northPlayer.setId(id2);
			} else if (position == 3) {
				eastPlayer.setPlayerName(playerName);
				eastPlayer.setId(id2);
			} else if (position == 4) {
				southEastPlayer.setPlayerName(playerName);
				southEastPlayer.setId(id2);
			} else if (position == 5) {
				southSouthEastPlayer.setPlayerName(playerName);
				southSouthEastPlayer.setId(id2);
			}
			count++;
		}
	}

	private void pause() {
		try {
			Thread.sleep(200);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void handleNewRound(NewRoundEvent e) {
		super.handleNewRound(e);
		pause();
		pause();
		pause();
		round = e.getRound();
		scoreBoard.setRound(e.getRound());
		//log("Round " + e.getRound());
		for(GuiPlayer player: players) {
			player.getCardPlayed().setCard(null);
			player.getTrickSummary().handleNewRound();
			if(player.getId() != getId()) {
				player.getHandJPanel().setRound(round);
				player.getTrickSummary().handleNewRound();
			}
		}
	}

	@Override
	public void handleNewTrick(NewTrickEvent e) {
		super.handleNewTrick(e);
		pause();
		pause();
		pause();
		for(GuiPlayer player: players) {
			CardPlayedPanel cpp = player.getCardPlayed();
			cpp.setCard(null);
			cpp.setHighlight(false);
			cpp.setFade(false);

		}

		isLead = true;
	}

	@Override
	public void handlePlayerBid(PlayerBidEvent e) {
		super.handlePlayerBid(e);

		scoreBoard.setBid(e.getPlayerId(), e.getBid());
		GuiPlayer player = findGuiPlayer(e.getPlayerId());
		player.getTrickSummary().handleBid(e.getBid());

	}

	@Override
	public void handlePlayerPlayed(PlayerPlayedEvent e) {
		super.handlePlayerPlayed(e);
		pause();
		cardTreeGui.repaint();

		GuiPlayer player = findGuiPlayer(e.getPlayerId());
		if(player.getId() != getId()) {
			player.getHandJPanel().removeCard();
		}
		Card card = e.getCard();
		player.getCardPlayed().setCard(card);

		if(isLead) {
			isLead = false;
		}
	}

	@Override
	public void handleScore(ScoreEvent e) {
		super.handleScore(e);
		scoreBoard.setScore(e.getPlayerId(), e.getScoreChangeAmount());
		GuiPlayer player = findGuiPlayer(e.getPlayerId());
		player.getTrickSummary().handleEndOfRound(e.getScoreChangeAmount());
	}

	@Override
	public void handleNewTrump(NewTrumpEvent e) {
		super.handleNewTrump(e);
		Card trump = e.getCard();
		if(trump == null) {
			log("No trump");
		} else if(trump.getSuit().equals(Suit.NONE)) {
			if(trump.isJester()) {
				log("No trump");
			} else {
				log("Dealer's choice");
			}
		} else {
			log("The new trump is " + e.getCard().getSuit().getDescription().toLowerCase());
		}
		trumpPanel.setCard(trump);

		WizardCardGraph wct = getWct(); 
		for(Card card: getHand()) {
			wct.removeCard(card);
		}

		cardTreeGui.setCardTree(wct);
		cardTreeGui.repaint();

	}

	@Override
	public void handlePlayerWonTrick(PlayerWonTrickEvent e) {
		super.handlePlayerWonTrick(e);
		for(GuiPlayer player: players) {
			if(player.getId() != e.getPlayerId()) {
				player.getCardPlayed().setFade(true);
			} else {
				player.getTrickSummary().handleTrickTaken();
			}
		}
		_logger.info("The winning card is " + e.getCard().toString());
	}

	@Override
	public void handleHandDealt(HandDealtEvent e) {
		super.handleHandDealt(e);

		getHand().naturalSort();

		boolean nextPlayer = false;
		for(GuiPlayer player : players) {
			if(nextPlayer == true) {
				player.getCardPlayed().setHighlight(true);
				nextPlayer = false;
			} else {
				player.getCardPlayed().setHighlight(false);
			}
			if(player.getId() == getId()) {
				player.getHandJPanel().addHand(getHand());
			} else {
				player.getHandJPanel().dealtCards();
			}

			if(player.getId() == e.getPlayerId()) {
				nextPlayer = true;
			}
		}

		if(nextPlayer == true) {
			players.iterator().next().getCardPlayed().setHighlight(true);
		}
	}

	@Override
	public void handlePlayerNeedsToPlay(PlayerNeedsToPlay e){
		super.handlePlayerNeedsToPlay(e);

		GuiPlayer player = findGuiPlayer(e.getPlayerId());
		if(e.getPlayerId() == getId()) {
			log("It's your turn to play.");
			player.getCardPlayed().setHighlight(true);
		} else {
			log("Waiting for " +  player.getPlayerName() + " to play.");
			player.getCardPlayed().setHighlight(true);
		}
	}

	private void log(String msg) {
		if(true) {
			_logger.info(msg);
		}
		statusBar.setText(msg);
		statusBar.repaint();
	}

	public Suit pickTrump() {
		PickSuitDialog psd = new PickSuitDialog(getName());
		psd.prompt();
		return psd.getSuit();
	}

	private GuiPlayer findGuiPlayer(int pId) {
		for(GuiPlayer player: players) {
			if(pId == player.getId()) {
				return player;
			}
		}
		return null;
	}

	/**
	 * Returns the imClient.
	 *
	 * @return the imClient.
	 */
	public IMClient getImClient() {
		return imClient;
	}

	/**
	 * Sets the imClient.
	 *
	 * @param imClient The new value for imClient.
	 */
	public void setImClient(IMClient imClient) {
		this.imClient = imClient;
	}

}