package com.svanloon.game.wizard.human;

import com.svanloon.game.wizard.core.card.CardFinder;
import com.svanloon.game.wizard.core.card.Hand;
import com.svanloon.game.wizard.core.card.WizardCardFinder;

/**
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class FaceDownHandJPanel extends HandJPanel {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * Constructs a new <code>FaceDownHandJPanel</code> object. 
	 *
	 */
	public FaceDownHandJPanel(String deckDir){
		super(true, false, false, deckDir);
	}
	/**
	 * Constructs a new <code>VisibleHandJPanel</code> object. 
	 * @param alignVertical 
	 * @param displayRight 
	 */
	public FaceDownHandJPanel(boolean alignVertical, boolean displayRight, String deckDir) {
		super(false, alignVertical, displayRight, deckDir);
	}

	private int round;
	/**
	 * @param round
	 */
	public void setRound(int round) {
		this.round = round;
	}

	/**
	 */
	public void removeCard() {
		getButtons().remove(0);
		this.repaint();
	}

	/**
	 */
	public void dealtCards() {
		Hand hand = new Hand();
		CardFinder cardFinder = new WizardCardFinder();
		for(int i = 0; i < round; i++) {
			hand.add(cardFinder.findCard(i));
		}
		addHand(hand);
		this.repaint();
	}
}