package com.svanloon.game.wizard.human.dialog;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.WindowConstants;

import com.svanloon.game.wizard.core.card.Suit;
import com.svanloon.game.wizard.language.LanguageFactory;
import com.svanloon.game.wizard.language.MessageId;

/**
 * 
 * @author Administrator
 *
 */
public class PickSuitDialog extends JDialog implements ActionListener {

	private static final long serialVersionUID = 1L;

	/**
	 * @param playerName 
	 */
	public PickSuitDialog(String playerName) {
		super();
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.add(new JLabel(LanguageFactory.getInstance().getString(MessageId.whatSuitWouldYouLikeTrumpToBe)));
		mainPanel.add(addSuits());
		JButton okButton = new JButton(LanguageFactory.getInstance().getString(MessageId.ok));
		okButton.addActionListener(this);
		mainPanel.add(okButton);
		this.add(mainPanel);
		this.setAlwaysOnTop(true);
		this.setLocation(200, 200);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setTitle(LanguageFactory.getInstance().getString(MessageId.trump));
		this.setResizable(false);
		this.pack();
	}

	private Component addSuits() {

		
		JRadioButton diamond = new JRadioButton(Suit.DIAMOND.getDescription(), true);
		diamond.setActionCommand(Suit.DIAMOND.getShortName());
		diamond.addActionListener(this);
		selectedSuit = Suit.DIAMOND;
		JRadioButton heart = new JRadioButton(Suit.HEART.getDescription(), false);
		heart.setActionCommand(Suit.HEART.getShortName());
		heart.addActionListener(this);
		JRadioButton club = new JRadioButton(Suit.CLUB.getDescription(), false);
		club.setActionCommand(Suit.CLUB.getShortName());
		club.addActionListener(this);
		JRadioButton spade = new JRadioButton(Suit.SPADE.getDescription(), false);
		spade.setActionCommand(Suit.SPADE.getShortName());
		spade.addActionListener(this);
		ButtonGroup bg = new ButtonGroup();
		bg.add(diamond);
		bg.add(heart);
		bg.add(club);
		bg.add(spade);

		JPanel p =  new JPanel();
		p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
		p.add(diamond);
		p.add(heart);
		p.add(club);
		p.add(spade);
		
		return p;
	}

	private boolean block = false;
	/**
	 * 
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

	private Suit selectedSuit;

	/**
	 * 
	 * Document the getSuit method 
	 *
	 * @return Suit
	 */
	public Suit getSuit() {
		return selectedSuit;
	}

	public void actionPerformed(ActionEvent e) {
		if(Suit.CLUB.getShortName().equals(e.getActionCommand()) ) {
			selectedSuit = Suit.CLUB;
		} else if(Suit.HEART.getShortName().equals(e.getActionCommand()) ) {
			selectedSuit = Suit.HEART;
		} else if(Suit.DIAMOND.getShortName().equals(e.getActionCommand()) ) {
			selectedSuit = Suit.DIAMOND;
		} else if(Suit.SPADE.getShortName().equals(e.getActionCommand()) ) {
			selectedSuit = Suit.SPADE;
		} else {
			this.block = false;
			this.setVisible(false);
			this.dispose();
		}
	}
}
