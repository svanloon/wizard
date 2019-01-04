package com.svanloon.game.wizard.human;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

import org.apache.log4j.Logger;

import com.svanloon.game.wizard.core.card.Card;
import com.svanloon.game.wizard.core.card.Hand;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public abstract class HandJPanel extends JPanel {
	private static Logger _logger = Logger.getLogger(HandJPanel.class); 
	private static final long serialVersionUID = 1L;

	private boolean showFace;
	private boolean displaySideways;
	private boolean displayRight;
	private String deckDir;

	/**
	 * Constructs a new <code>HandJPanel</code> object. 
	 * @param showFace 
	 * @param displaySideways 
	 * @param displayRight
	 */
	public HandJPanel(boolean showFace, boolean displaySideways, boolean displayRight, String deckDir) {
		super();
		this.showFace = showFace;
		this.displaySideways = displaySideways;
		this.displayRight = displayRight;
		this.deckDir = deckDir;
	}
	private double visableCardWidth = 22;

	/**
	 * 
	 * Document the addCards method 
	 *
	 * @param hand
	 */
	public void addHand(Hand hand) {
		log("added hand");
		buttons = new ArrayList<CardButton>();
		double size = hand.size();

		double cardWidth = 73;

		Dimension d = this.getSize();
		double width;
		if(displaySideways) {
			width = d.getHeight();
		} else {
			width = d.getWidth();
		}
		double takenUpSpace = size * visableCardWidth + (cardWidth - visableCardWidth);
		double initialOffset = (width - takenUpSpace)/ 2.0;


		int i = 0;
		for(Card card: hand) {
			int x = (int)(initialOffset + i*visableCardWidth);
			int y = 20;
			
			buttons.add(new CardButton(card, x, y, deckDir));

			i++;
		}
		this.repaint();
	}

	@Override
	public void paint(Graphics g) {
		log("paint");
		Dimension d = this.getSize();
		g.setColor(GuiConstants.BACKGROUND_COLOR);
		g.clearRect(0, 0, (int)d.getWidth(), (int)d.getHeight());
		g.fillRect(0, 0, (int)d.getWidth(), (int)d.getHeight());
		if(buttons == null || buttons.isEmpty()) {
			return;
		}
		for(CardButton button:buttons) {
			addCard(button.getCard(), button.getX(), button.getY(), g);
		}
		
	}
	
	private ArrayList<CardButton> buttons = null; 

	private void addCard(Card card, int x, int y, Graphics g) {
		log("display card " + card);
		CardButton cardButton = new CardButton(card, deckDir);
		//waitForImage(cardButton);
		if(showFace) {
			log("image " + cardButton.getFaceImage());
			g.drawImage(cardButton.getFaceImage(), x,y, cardButton.getWidth(), cardButton.getHeight(), this);
		} else if(displaySideways == false) {
			g.drawImage(cardButton.getVerticalBackImage(), x,y, cardButton.getWidth(), cardButton.getHeight(), this);
		} else if(displayRight) {
			g.drawImage(cardButton.getHorizontalLeftBackImage(), 0,x, cardButton.getHeight(), cardButton.getWidth(), this);
		} else {
			g.drawImage(cardButton.getHorizontalLeftBackImage(), y, x, cardButton.getHeight(), cardButton.getWidth(), this);
		}
	}

	/**
	 * 
	 * @return buttons
	 */
	public ArrayList<CardButton> getButtons() {
		return buttons;
	}

	/**
	 * 
	 * Document the cardPlayedIsValid method 
	 *
	 * @param card
	 */
	public void cardPlayedIsValid(Card card) {
		for(CardButton button:buttons) {
			if(button.getCard().equals(card)) {
				buttons.remove(button);
				break;
			}
		}
		log("card is valid");
		this.repaint();
	}

	/**
	 * 
	 * Document the cardPlayedIsNotValid method 
	 *
	 * @param card
	 */
	public void cardPlayedIsNotValid(Card card) {
		for(CardButton button:buttons) {
			if(button.getCard().equals(card)) {
				button.setY(20);
				break;
			}
		}
		log("card is not valid");
		this.repaint();
	}

	private void log(String msg) {
		if(false) {
			_logger.info(msg);
		}
	}
}
