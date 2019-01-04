package com.svanloon.game.wizard.human;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import org.apache.log4j.Logger;

import com.svanloon.game.wizard.core.card.Card;

/**
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class CardButtonMouseListener implements MouseListener {
	private static Logger _logger = Logger.getLogger(CardButtonMouseListener.class);
	private HandJPanel hjp;

	/**
	 * @param hjp 
	 */
	public CardButtonMouseListener(HandJPanel hjp) {
		super();
		this.hjp = hjp;
	}

	private Card card = null;

	public void mouseClicked(MouseEvent e) {
		if(waiting == false) {
			return;
		}
		int clickCount = e.getClickCount();
		if(clickCount != 1 && clickCount != 2) {
			return;
		}

		Point p = e.getPoint();
		int x = (int) p.getX();
		int y = (int) p.getY();
		
		CardButton cardClicked = null;
		List<CardButton> buttons = hjp.getButtons();
		for(CardButton button : buttons) {
			int x1 = button.getX();
			int y1 = button.getY();
			int x2 = x1 + button.getWidth();
			int y2 = y1 + button.getHeight();
			if( x > x1 && y > y1 && x < x2 && y < y2) {
				cardClicked =  button;
			}
		}

		if(cardClicked != null) {
			Card card2 = cardClicked.getCard();
			String name = card2.toString();
			if(cardClicked.getY() == 0) {
				_logger.info("played Card " + name);
				this.card = cardClicked.getCard();
			} else if(cardClicked.getY() > 0) {
				for(CardButton button : buttons) {
					button.setY(20);
				}
				_logger.info("selected Card " + name);
				cardClicked.setY(0);
				
			} else {
				cardClicked.setY(20);
			}
			hjp.repaint();
		}
	}

	private boolean waiting = false;

	/**
	 * 
	 * Document the findCard method 
	 *
	 * @return card
	 */
	public Card findCard() {
		waiting = true;
		while(true) {
			if(card != null) {
				Card temp = card;
				card = null;
				waiting = false;
				_logger.info("trying to play " + temp);
				return temp;
			}
			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

	public void mouseEntered(MouseEvent e) {
		// do nothing
	}

	public void mouseExited(MouseEvent e) {
		// do nothing
	}

	public void mousePressed(MouseEvent e) {
		//		 do nothing
	}

	public void mouseReleased(MouseEvent e) {
		// do nothing
	}
}
