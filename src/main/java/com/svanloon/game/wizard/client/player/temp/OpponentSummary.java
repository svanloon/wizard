package com.svanloon.game.wizard.client.player.temp;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.svanloon.common.util.IconUtil;
import com.svanloon.game.wizard.client.player.AbstractOpponent;
import com.svanloon.game.wizard.client.player.AverageHand;
import com.svanloon.game.wizard.client.player.Opponent;
import com.svanloon.game.wizard.core.card.Card;
import com.svanloon.game.wizard.core.card.Suit;
import com.svanloon.game.wizard.core.card.Value;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class OpponentSummary extends JFrame {
	private static final long serialVersionUID = -7269637324702536709L;
	private AverageHand averageHand;
	private List<Opponent> opponents;
	private Card trump;
	private Card highCard;
	private Card lead;
	private JTable table;
	
	private boolean doGui = true; 
	
	/**
	 * 
	 * Constructs a new <code>OpponentSummary</code> object. 
	 *
	 */
	public OpponentSummary() {
		super();
		Image image = IconUtil.getIcon();
		this.setIconImage(image);
    	setLayout(new GridLayout(1,0));
        
        String[] columnNames = {"", "", "", "", "", "", ""};

        Object[][] data = {
        	{"", "Hearts", "Diamond", "Spades", "Clubs", "Jesters", "Wizard"},
        	{"Average", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0)},
        	{"Player 1", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0)},
        	{"Player 2", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0)},
        	{"Player 3", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0)},
        	{"Player 4", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0)},
        	{"Player 5", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0)},
        	{"Player 6", Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0)}
        };

        table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setEnabled(false);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        add(scrollPane);
        this.setSize(500,200);
        setTitle("Average and Actual Played");
        //setTitle(LanguageFactory.getInstance().getString(MessageId.CARDS_REMAINING_TO_BE_PLAYED));
	}

	/**
	 * 
	 * Document the setRound method 
	 *
	 * @param round
	 */
	public void setRound(int round) {
		averageHand = new AverageHand();
		averageHand.setRound(round);
		for(Opponent opponent:opponents) {
			opponent.setRound(round);
			opponent.setCardsRemaining(round);
		}
		update();
	}
	/**
	 * 
	 * Document the newTrick method 
	 *
	 */
	public void newTrick() {
		lead = null;
		highCard = null;
		for(Opponent opponent:opponents) {
			opponent.setPlayedTrick(false);
		}
		update();
	}
	/**
	 * 
	 * Document the setTrump method 
	 *
	 * @param trump
	 */
	public void setTrump(Card trump) {
		this.trump = trump;
	}
	/**
	 * @return the opponent
	 */
	public List<Opponent> getOpponents() {
		return opponents;
	}

	/**
	 * @param opponents the opponent to set
	 */
	public void setOpponent(List<Opponent> opponents) {
		this.opponents = opponents;
	}
	
	/**
	 * 
	 * Document the setBid method 
	 *
	 * @param id
	 * @param bid
	 */
	public void setBid(int id, int bid) {
		Opponent opponent = findOpponent(id);
		opponent.setBid(bid);
		update();
	}
	/**
	 * 
	 * Document the findOpponent method 
	 *
	 * @param pId
	 * @return Opponent
	 */
	public Opponent findOpponent(int pId) {
		for(Opponent opponent:opponents) {
			if(opponent.getId() == pId ) {
				return opponent;
			}
		}
		// not possible
		return null;
	}

	private void setLead(Card card) {
		if(lead == null && card.getValue().equals(Value.JESTER) == false) {
			lead = card;
		} 	
	}
	private void setHighCard(Card playedCard) {
		if(highCard == null) { // if no high card exists, just set highcard
			highCard = playedCard;
		} else if (highCard.getValue().equals(Value.JESTER)) { // if the high card is a jester, set the highcard
			highCard = playedCard;
		} else if(highCard.getValue().equals(Value.WIZARD)) { // if the high card is a wizard, nothing can beat it.
			// do nothing
		}else if(playedCard.getValue().equals(Value.JESTER)) { // if the card played is a jester, can't beat anything
			// do nothing
		} else if(playedCard.getValue().equals(Value.WIZARD)) { // if the card played is a wizard, it beats everything
			highCard = playedCard;
		} else if (trump != null && trump.getSuit().equals(Suit.NONE) == false && // if there's trump and the card lead isn't trump, set high card.
				highCard.getSuit().equals(trump.getSuit()) == false && 
				playedCard.getSuit().equals(trump.getSuit())
		) {
			highCard = playedCard;
		} else if (playedCard.getSuit().equals(highCard.getSuit()) && // if the high card and the card played are the same suit
																	  // and the card played has a higher value, set the high card.
				   playedCard.getValue().getIndex() > highCard.getValue().getIndex()
		) {
			highCard = playedCard;
		}
	}

	/**
	 * 
	 * Document the played method 
	 *
	 * @param id
	 * @param played
	 */
	public void played(int id, Card played) {
		setLead(played);
		setHighCard(played);

		Opponent opponent = findOpponent(id);
		opponent.setPlayedTrick(true);
		opponent.setCardsRemaining(opponent.getCardsRemaining() - 1);
		opponent.played(lead, highCard, played);
		
		update();
	}
	/**
	 * 
	 * Document the wonTrick method 
	 *
	 * @param id
	 */
	public void wonTrick(int id) {
		Opponent opponent = findOpponent(id);
		opponent.tookTrick();
	}
	/**
	 * 
	 * Document the changeScore method 
	 *
	 * @param id
	 * @param scoreChangeAmount
	 */
	public void changeScore(int id, int scoreChangeAmount) {
		Opponent opponent = findOpponent(id);
		opponent.changeScore(scoreChangeAmount);
		update();
	}

	/**
	 * @return the trump
	 */
	public Card getTrump() {
		return trump;
	}

	/**
	 * @return the highCard
	 */
	public Card getHighCard() {
		return highCard;
	}

	/**
	 * @return the lead
	 */
	public Card getLead() {
		return lead;
	}
	private void update() {
		if (doGui) {
			int i = 1;
			updatePlayer(i, averageHand);
			for(Opponent opponent: opponents) {
				i++;
				updatePlayer(i, opponent);
			}
			this.repaint();
		}
	}

	private void updatePlayer(int yOffset, AbstractOpponent opponent) {
		table.getModel();
		javax.swing.table.TableModel model = table.getModel();
		int x = 0;
		model.setValueAt(opponent.getName(), yOffset, x++);
		model.setValueAt(format(opponent.isHasHearts(), opponent.getHearts()), yOffset, x++);
		model.setValueAt(format(opponent.isHasDiamonds(), opponent.getDiamonds()), yOffset, x++);
		model.setValueAt(format(opponent.isHasSpades(), opponent.getSpades()), yOffset, x++);
		model.setValueAt(format(opponent.isHasClubs(), opponent.getClubs()), yOffset, x++);
		model.setValueAt(format(opponent.isHasJesters(),opponent.getJesters()), yOffset, x++);
		model.setValueAt(format(opponent.isHasWizards(), opponent.getWizards()), yOffset, x++);
	}

	private String format(boolean has, double value) {
		if(has == false) {
			return "out" + String.valueOf(value);
		}
		return String.valueOf(value);
	}

	/**
	 * 
	 * Document the isDoGui method 
	 *
	 * @return boolean
	 */
	public boolean isDoGui() {
		return doGui;
	}

	/**
	 * 
	 * Document the setDoGui method 
	 *
	 * @param pDoGui
	 */
	public void setDoGui(boolean pDoGui) {
		doGui = pDoGui;
	}
}
