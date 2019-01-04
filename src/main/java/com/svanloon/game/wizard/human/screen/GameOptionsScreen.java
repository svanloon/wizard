package com.svanloon.game.wizard.human.screen;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.svanloon.game.wizard.core.gameOptions.BidType;
import com.svanloon.game.wizard.core.gameOptions.GameOptions;
import com.svanloon.game.wizard.core.gameOptions.GameSpeed;
import com.svanloon.game.wizard.human.dialog.GameOptionsManager;
import com.svanloon.game.wizard.language.LanguageFactory;
import com.svanloon.game.wizard.language.MessageId;

/**

 * @author Administrator
 */
public class GameOptionsScreen extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField totalNumberOfPlayers = new JTextField();
	private JTextField totalHumanPlayers = new JTextField();
	/**
	 * 
	 */
	public GameOptionsScreen() {
		super();
		gameOptions = new GameOptionsManager().load();

		JPanel options = new JPanel();
		options.setLayout(new GridLayout(4,2));

		options.add(new JLabel(LanguageFactory.getInstance().getString(MessageId.BID_TYPE)));
		final JComboBox bid = new JComboBox();
		for(BidType bidType : BidType.values()) {
			bid.addItem(bidType);
		}
		bid.setSelectedItem(gameOptions.getBidType());
		options.add(bid);
		bid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameOptions.setBidType((BidType) bid.getSelectedItem());
			}
		});

		options.add(new JLabel(LanguageFactory.getInstance().getString(MessageId.GAME_SPEED)));
		final JComboBox gameSpeed = new JComboBox();
		for(GameSpeed gameSpeed2 : GameSpeed.values()) {
			gameSpeed.addItem(gameSpeed2);
		}
		gameSpeed.setSelectedItem(gameOptions.getGameSpeed());
		options.add(gameSpeed);
		gameSpeed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameOptions.setGameSpeed((GameSpeed) gameSpeed.getSelectedItem());
			}
		});

		options.add(new JLabel("Total Number Of Players"));
		options.add(totalNumberOfPlayers);
		totalNumberOfPlayers.setText(String.valueOf(gameOptions.getTotalNumberOfPlayers()));

		options.add(new JLabel("Total Human Players"));
		options.add(totalHumanPlayers);
		totalHumanPlayers.setText(String.valueOf(gameOptions.getTotalHumanPlayers()));

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		JPanel buttonPanel = new JPanel();
		JButton ok = new JButton(LanguageFactory.getInstance().getString(MessageId.ok));
		buttonPanel.add(ok);
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				block = false;
				gameOptions.setTotalNumberOfPlayers(Integer.parseInt(totalNumberOfPlayers.getText().trim()));
				gameOptions.setTotalHumanPlayers(Integer.parseInt(totalHumanPlayers.getText().trim()));
				new GameOptionsManager().persist(gameOptions);
				setVisible(false);
				dispose();
			}
		});

		JButton cancel = new JButton(LanguageFactory.getInstance().getString(MessageId.CANCEL));
		buttonPanel.add(cancel);
		cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				block = false;
				gameOptions = new GameOptionsManager().load();
				setVisible(false);
				dispose();
			}
		});

		mainPanel.add(options);
		mainPanel.add(buttonPanel);
		this.add(mainPanel);
		this.pack();
		this.setResizable(false);
	}

	/**
	 * 
	 * Document the prompt method 
	 *
	 */
	public void prompt() {
		this.setVisible(true);
		//block = true;
		while(block) {
			try {
				Thread.sleep(35);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean block = false;

	private GameOptions gameOptions;

	/**
	 * 
	 * Document the getGameOptions method 
	 *
	 * @return GameOptions
	 */
	public GameOptions getGameOptions() {
		return gameOptions;
	}
}
