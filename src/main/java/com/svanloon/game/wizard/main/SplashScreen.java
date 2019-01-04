package com.svanloon.game.wizard.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ResourceBundle;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import com.svanloon.game.wizard.core.gameOptions.GameOptions;
import com.svanloon.game.wizard.human.dialog.UserPreferences;
import com.svanloon.game.wizard.human.dialog.UserPreferencesScreen;
import com.svanloon.game.wizard.human.screen.CreditScreen;
import com.svanloon.game.wizard.human.screen.GameOptionsScreen;
import com.svanloon.game.wizard.human.screen.InstructionScreen;
import com.svanloon.game.wizard.human.screen.RulesScreen;
import com.svanloon.game.wizard.language.LanguageFactory;
import com.svanloon.game.wizard.language.MessageId;

/**
 * @author Administrator
 */
public class SplashScreen extends JDialog implements ActionListener, WindowListener, Runnable {
	private static Logger _logger = Logger.getLogger(SplashScreen.class);
	private static final long serialVersionUID = 1L;
	private GameType gameType = GameType.HOSTED;
	private int displayScreen = -1;
	
	public void run() {
		if(displayScreen == 1) {
			UserPreferencesScreen ups2 = new UserPreferencesScreen();
			ups2.prompt();
			this.ups = ups2;
		} else if(displayScreen == 2) {
			GameOptionsScreen gos2 = new GameOptionsScreen();
			gos2.prompt();
			this.gos = gos2;
		}
	}
	private SplashScreen ss = this;
	private UserPreferencesScreen ups;
	private GameOptionsScreen gos;

	/**
	 * 
	 * Constructs a new <code>SplashScreen</code> object. 
	 *
	 */
	public SplashScreen() {
		super();
		ups = new UserPreferencesScreen();
		gos = new GameOptionsScreen();
		LanguageFactory.setLocale(ups.getUserPreferences().getLocale());

		Color bg = new Color(52,53,110);
		JLabel ul = new JLabel(new ImageIcon(this.getClass().getClassLoader().getResource("images/splash.gif")));
		JButton startButton = createButton(LanguageFactory.getInstance().getString(MessageId.START_GAME));
		JButton connectButton = createButton(LanguageFactory.getInstance().getString(MessageId.CONNECT_TO_A_GAME));
		JButton instructionsButton = createButton(LanguageFactory.getInstance().getString(MessageId.INSTRUCTIONS));
		JButton rulesButton = createButton(LanguageFactory.getInstance().getString(MessageId.RULES));
		JButton gameOptionsButton = createButton(LanguageFactory.getInstance().getString(MessageId.GAME_OPTIONS));
		JButton userPreferenceButton = createButton(LanguageFactory.getInstance().getString(MessageId.USER_PREFERENCES));
		JButton creditsButton = createButton(LanguageFactory.getInstance().getString(MessageId.CREDITS));

		startButton.addActionListener(this);
		connectButton.addActionListener(this);
		rulesButton.addActionListener(this);
		creditsButton.addActionListener(this);
		instructionsButton.addActionListener(this);
		userPreferenceButton.addActionListener(this);
		gameOptionsButton.addActionListener(this);
		// create Layout panel
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		mainPanel.setBackground(bg);

		JPanel col1 = new JPanel();
		col1.setLayout(new BoxLayout(col1, BoxLayout.Y_AXIS));
		col1.setBackground(bg);
		
		// combine panel
		
		col1.add(startButton);
		col1.add(connectButton);
		col1.add(instructionsButton);
		col1.add(rulesButton);
		col1.add(gameOptionsButton);
		col1.add(userPreferenceButton);
		col1.add(creditsButton);
		ResourceBundle rb = ResourceBundle.getBundle("wizard");
		String version = rb.getString("version");
		JLabel versionLabel = new JLabel("Build: " + version);
		versionLabel.setForeground(Color.WHITE);
		col1.add(versionLabel);

		mainPanel.add(ul);
		mainPanel.add(col1);
		this.add(mainPanel);

		this.pack();
		Dimension s = this.getSize();
		this.setSize((int)(s.getWidth()*1.05), (int)(s.getHeight()*1.05));
		this.addWindowListener(this);
	}

	/**
	 * @return the UserPreferences
	 */
	public UserPreferences getUserPreferences() {
		return ups.getUserPreferences();
	}

	private JButton createButton(String message) {
		JButton b = new JButton(message);
		Dimension d = new Dimension(200, 20);
		b.setPreferredSize(d);
		b.setMinimumSize(d);
		b.setMaximumSize(d);
		return b;
	}
	private boolean block = false;

	/**
	 * Document the prompt method 
	 */
	public void prompt() {
		this.setVisible(true);
		block = true;
		while(block) {
			try {
				Thread.sleep(35);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * Document the getGameType method 
	 *
	 * @return gameType
	 */
	public GameOptions getGameOptions() {
		return gos.getGameOptions();
	}


	public void actionPerformed(ActionEvent e) {
		String actionCommand = e.getActionCommand();
		boolean close = false;
		if(LanguageFactory.getInstance().getString(MessageId.START_GAME).equals(actionCommand)) {
			close = true;
			gameType = GameType.HOSTED;
		}else if(LanguageFactory.getInstance().getString(MessageId.CONNECT_TO_A_GAME).equals(actionCommand)) {
			close = true;
			gameType = GameType.CONNECT_TO_A_GAME;
		} else if(LanguageFactory.getInstance().getString(MessageId.RULES).equals(actionCommand)) {
			RulesScreen rs = new RulesScreen();
			rs.show();
		} else if(LanguageFactory.getInstance().getString(MessageId.CREDITS).equals(actionCommand)) {
			CreditScreen cs = new CreditScreen();
			cs.show();
		} else if(LanguageFactory.getInstance().getString(MessageId.INSTRUCTIONS).equals(actionCommand)) {
			InstructionScreen is = new InstructionScreen();
			is.show();
		} else if(LanguageFactory.getInstance().getString(MessageId.GAME_OPTIONS).equals(actionCommand)) {
			gos.setAlwaysOnTop(true);
			displayScreen = 2;
			Thread t = new Thread(ss);
			t.start();
		} else if(LanguageFactory.getInstance().getString(MessageId.USER_PREFERENCES).equals(actionCommand)) {
			displayScreen = 1;
			ups.setAlwaysOnTop(true);
			Thread t = new Thread(ss);
			t.start();
		} else {
			_logger.info(actionCommand);
		}

		if(close) {
			this.block = false;
			this.setVisible(false);
			this.dispose();
		}
	}


	/**
	 * 
	 * Document the main method 
	 *
	 * @param args
	 */
	public static void main(String args[]) {
		SplashScreen ss = new SplashScreen();
		ss.prompt();
	}

	public void windowActivated(WindowEvent e) {
		// Auto-generated method stub
		
	}

	public void windowClosed(WindowEvent e) {
		// Auto-generated method stub
	}

	public void windowClosing(WindowEvent e) {
		// Auto-generated method stub
		block = false;
		System.exit(1);		
	}

	public void windowDeactivated(WindowEvent e) {
		// Auto-generated method stub
		
	}

	public void windowDeiconified(WindowEvent e) {
		// Auto-generated method stub
		
	}

	public void windowIconified(WindowEvent e) {
		// Auto-generated method stub
		
	}

	public void windowOpened(WindowEvent e) {
		// Auto-generated method stub
		
	}

	/**
	 * @return the gameType
	 */
	public GameType getGameType() {
		return gameType;
	}
}
