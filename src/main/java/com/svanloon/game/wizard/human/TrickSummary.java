package com.svanloon.game.wizard.human;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JLabel;

/**
 * 
 * @author Administrator
 *
 */
public class TrickSummary extends JLabel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int type = 2;

	/**
	 * 
	 */
	public TrickSummary() {
		super();
		this.setBackground(GuiConstants.BACKGROUND_COLOR);
		this.setBackground(Color.YELLOW);
		this.setLayout(new GridBagLayout());

		bidLabel.setForeground(Color.WHITE);
		scoreLabel.setForeground(Color.WHITE);
		tricksTakenLabel.setForeground(Color.WHITE);
		
		GridBagConstraints constraints = new GridBagConstraints(0,0,1,1,1.0,1.0,
				GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(0,0,0,0), 0,0);
		
		if(type == 0) {
			this.add(tricksTakenLabel,constraints);
		} else if(type == 1) {
			this.add(tricksTakenLabel,constraints);
			constraints.gridy = 1;
			this.add(bidLabel,constraints);
		} else if (type == 2) {
			this.add(tricksTakenLabel,constraints);
			constraints.gridy = 1;
			this.add(bidLabel,constraints);
			constraints.gridy = 2;
			this.add(scoreLabel,constraints);
		}

	}

	@Override
	public void setBackground(Color color) {
		super.setBackground(color);
	}

	private int tricksTaken;

	private JLabel bidLabel = new JLabel();
	private JLabel scoreLabel = new JLabel();
	private JLabel tricksTakenLabel = new JLabel();

	
	
	/**
	 * 
	 * Document the handleNewRound method 
	 *
	 */
	public void handleNewRound() {
		bidLabel.setText("");
		tricksTaken = 0;
		tricksTakenLabel.setText("Tricks: " + tricksTaken);
		repaint();
	}

	/**
	 * 
	 * Document the handleBid method 
	 *
	 * @param bid
	 */
	public void handleBid(int bid) {
		bidLabel.setText("Bid: " + bid);
		repaint();
	}

	/**
	 * 
	 * Document the handleTrickTaken method 
	 *
	 */
	public void handleTrickTaken() {
		tricksTaken++;
		tricksTakenLabel.setText("Tricks: " + tricksTaken);
		repaint();
	}

	private int score = 0;
	/**
	 * 
	 * Document the handleEndOfRound method 
	 *
	 * @param pScore
	 */
	public void handleEndOfRound(int pScore) {
		score += pScore;
		scoreLabel.setText("Score: " + score);
		repaint();
	}

}
