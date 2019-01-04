package com.svanloon.game.wizard.client.player.temp;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.svanloon.common.util.IconUtil;
import com.svanloon.game.wizard.client.player.CardCounter;
import com.svanloon.game.wizard.core.card.Card;
//import com.svanloon.game.wizard.language.LanguageFactory;
//import com.svanloon.game.wizard.language.MessageId;

/**
 * @author Administrator
 */
public class CardCountGui extends JFrame {

	private static final long serialVersionUID = 1L;
	private CardCounter cardCounter = null;
	private JTable table;
	
	private boolean doGui = true;
	/**
	 * Constructs a new <code>CardCountGui</code> object. 
	 */
	public CardCountGui() {
		super();
		Image image = IconUtil.getIcon();
		this.setIconImage(image);
    	setLayout(new GridLayout(1,0));
        
        String[] columnNames = {"", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};

        Object[][] data = {
        	{"", "Total", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"},
        	{"Heart", Integer.valueOf(13), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1)},
        	{"Clubs", Integer.valueOf(13), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1)},
        	{"Spades", Integer.valueOf(13), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1)},
        	{"Diamonds", Integer.valueOf(13), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1), Integer.valueOf(1)},
        	{"Wizard", Integer.valueOf(4), "", "", "", "", "", "", "", "", "", "", "", "", ""},
        	{"Jester", Integer.valueOf(4), "", "", "", "", "", "", "", "", "", "", "", "", ""},
        	{"Not Dealt", Integer.valueOf(60), "", "", "", "", "", "", "", "", "", "", "", "", ""},
        	{"In Hands", Integer.valueOf(0), "", "", "", "", "", "", "", "", "", "", "", "", ""},
        };

        table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setEnabled(false);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        add(scrollPane);
        this.setSize(500,200);
        //setTitle(LanguageFactory.getInstance().getString(MessageId.CARDS_REMAINING_TO_BE_PLAYED));
	}
	
	/**
	 * 
	 * Document the newRound method 
	 *
	 */
	public void newRound() {
		cardCounter = new CardCounter();
		update();
	}

	/**
	 * 
	 * Document the init method 
	 *
	 * @param numberOfPlayers
	 * @param cardsPerPlayer
	 */
	public void init(int numberOfPlayers, int cardsPerPlayer){
		cardCounter.init(numberOfPlayers, cardsPerPlayer);
		update();
	}
	
	/**
	 * 
	 * Document the setTrump method 
	 *
	 * @param card
	 */
	public void setTrump(Card card) {
		cardCounter.setTrump(card);
		update();
	}

	/**
	 * 
	 * Document the countCard method 
	 *
	 * @param card
	 */
	public void countCard(Card card) {
		cardCounter.countCard(card);
		update();
	}

	private void update() {
		if (doGui) {
			updateSuit(1, cardCounter.getHeartCount(), cardCounter.getHearts());
			updateSuit(2, cardCounter.getClubCount(), cardCounter.getClubs());
			updateSuit(3, cardCounter.getSpadeCount(), cardCounter.getSpades());
			updateSuit(4, cardCounter.getDiamondCount(), cardCounter.getDiamonds());
			updateCount(5, cardCounter.getWizard());
			updateCount(6, cardCounter.getJester());
			updateCount(7, cardCounter.getCardsNotDealt());
			updateCount(8, cardCounter.getCardsRemainingInHands());
		
			repaint();
		}
	}

	private void updateSuit(int yOffset, int count, int[] values) {
		updateCount(yOffset, count);
		javax.swing.table.TableModel model = table.getModel();
    	int xOffset = 2;
    	for(int i = 0; i < values.length; i++) {
	    	int x = xOffset + i;
	    	int value = values[i];
	    	model.setValueAt(Integer.valueOf(value), yOffset, x);
    	}
	}

	private void updateCount(int yOffset, int count) {
    	javax.swing.table.TableModel model = table.getModel();
    	model.setValueAt(Integer.valueOf(count), yOffset, 1);
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
