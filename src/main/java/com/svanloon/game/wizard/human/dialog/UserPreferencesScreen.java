package com.svanloon.game.wizard.human.dialog;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.svanloon.game.wizard.language.LanguageFactory;
import com.svanloon.game.wizard.language.MessageId;

/**
 * @author Administrator
 */
public class UserPreferencesScreen extends JDialog {
	private static final long serialVersionUID = 1L;
	private UserPreferences up;
	private boolean block = false;

	/**
	 * 
	 */
	public UserPreferencesScreen() {
		super();
		up = new UserPreferencesManager().load();

		Dimension d = new Dimension(200, 20);
		final JComboBox language = createComboBox(d);
		language.addItem(LanguageFactory.getInstance().getString(MessageId.ENGLISH));
		if(up.getLocale().equals(Locale.ENGLISH)) {
			language.setSelectedIndex(0);
		}

		language.addItem(LanguageFactory.getInstance().getString(MessageId.SPANISH));
		if(up.getLocale().equals(new Locale("sp"))) {
			language.setSelectedIndex(1);
		}

		language.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent e) {
			String actionCommand = language.getSelectedItem().toString();
			if(actionCommand.equals(LanguageFactory.getInstance().getString(MessageId.ENGLISH))) {
				up.setLocale(Locale.ENGLISH);
			} else if(actionCommand.equals(LanguageFactory.getInstance().getString(MessageId.SPANISH))) {
				up.setLocale(new Locale("sp"));
			}
		}});

		final JComboBox displayScoreBoard = createComboBox(d);
		displayScoreBoard.addItem(LanguageFactory.getInstance().getString(MessageId.NO));
		if(up.getDisplayScoreBoard() == false) {
			displayScoreBoard.setSelectedIndex(0);
		}
		displayScoreBoard.addItem(LanguageFactory.getInstance().getString(MessageId.YES));
		if(up.getDisplayScoreBoard()) {
			displayScoreBoard.setSelectedIndex(1);
		}
		displayScoreBoard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int i = displayScoreBoard.getSelectedIndex();
				if(i == 1) {
					up.setDisplayScoreBoard(true);
				} else {
					up.setDisplayScoreBoard(false);
				}
			}
		});

		final JComboBox decks = createComboBox(d);
		decks.addItem("Default");
		if("Default".equals(up.getName())) {
			decks.setSelectedIndex(0);
		}
		int i = 1;
		for(String deck: getDecks()) {
			String shortedName = deck.substring(deck.lastIndexOf(File.separator) + 1); 
			decks.addItem(shortedName);
			if(shortedName.equals(up.getDeck())) {
				decks.setSelectedIndex(i);
			}
			i++;
		}

		decks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				up.setDeck(decks.getSelectedItem().toString());
			}
		});

		JPanel options = new JPanel();
		options.setLayout(new GridLayout(6, 2));
		options.add(new JLabel(LanguageFactory.getInstance().getString(MessageId.LANGUAGE)));
		options.add(language);
		options.add(new JLabel(LanguageFactory.getInstance().getString(MessageId.DISPLAY_SCORE_BOARD)));
		options.add(displayScoreBoard);

		options.add(new JLabel(LanguageFactory.getInstance().getString(MessageId.DECK)));
		options.add(decks);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		JButton ok = new JButton(LanguageFactory.getInstance().getString(MessageId.ok));
		buttonPanel.add(ok);
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new UserPreferencesManager().persist(up);
				block = false;
				setVisible(false);
				dispose();
			}
		});

		JButton cancel= new JButton(LanguageFactory.getInstance().getString(MessageId.CANCEL));
		buttonPanel.add(cancel);
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				up = new UserPreferencesManager().load();
				block = false;
				setVisible(false);
				dispose();
			}
		});

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(options);
		mainPanel.add(buttonPanel);
		this.add(mainPanel);
		this.pack();
		this.setResizable(false);
	}

	private List<String> getDecks() {
		List<String> decks = new ArrayList<String>();

		String userHome = System.getProperty("user.home");
		File decksDir = new File(userHome + File.separator + "wizard/expansion/decks");
		if(decksDir.exists() == false ) {
			return decks;
		}
		File[] dirList = decksDir.listFiles();
		
		for(File deck: dirList) {
			if(deck.isFile()) {
				continue;
			}
			if(deck.getName().equals(".") || deck.getName().equals("..")) {
				continue;
			}
			decks.add(deck.getAbsolutePath());
		}

		return decks;
	}
	/**
	 * 
	 * Document the prompt method 
	 *
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

	private JComboBox createComboBox(Dimension d) {
		JComboBox language = new JComboBox();
		language.setSize(d);
		language.setPreferredSize(d);
		language.setMinimumSize(d);
		language.setMaximumSize(d);
		return language;
	}
	/**
	 * @return up
	 */
	public UserPreferences getUserPreferences() {
		return up;
	}

	/**
	 * 
	 * Document the main method 
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		UserPreferencesScreen userPreferencesScreen = new UserPreferencesScreen();
		userPreferencesScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		userPreferencesScreen.prompt();
	}

}
