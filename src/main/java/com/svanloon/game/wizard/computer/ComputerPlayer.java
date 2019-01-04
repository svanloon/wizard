package com.svanloon.game.wizard.computer;

import com.svanloon.game.wizard.client.player.Player;
import com.svanloon.game.wizard.client.player.temp.GameObserverListener;
import com.svanloon.game.wizard.client.player.temp.OpponentSummary;
import com.svanloon.game.wizard.core.card.Card;
import com.svanloon.game.wizard.core.card.Suit;
//import com.svanloon.game.wizard.player.human.AbstractSwingPlayer;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class ComputerPlayer
	//extends AbstractSwingPlayer
	extends GameObserverListener implements Player  
{
	private BasicStrategy bs;
	/**
	 * 
	 * Constructs a new <code>ComputerPlayer</code> object. 
	 *
	 * @param name
	 */
	public ComputerPlayer(String name) {
		super();
		super.setName(name);
		this.setDoGui(false);

		//setVisible(true);
		bs = new BasicStrategy(getHand());
		OpponentSummary os = this.getOpponentSummary();
		bs.setOpponentSummary(os);
	}

	@Override
	public void setId(int id) {
		super.setId(id);
		bs.setId(id);
	}

	public Suit pickTrump() {
		return bs.pickTrump();
	}

	public int bid(Card trump, int min, int max, int bidNotAllowed) {
		return bs.bid(trump, min, max, bidNotAllowed);
	}

	public Card playCard() {
		return bs.playCard(getWct());
	}
}