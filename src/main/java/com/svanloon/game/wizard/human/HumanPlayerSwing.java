package com.svanloon.game.wizard.human;

import java.awt.Dimension;
import java.awt.Point;

import com.svanloon.game.wizard.core.card.Card;
import com.svanloon.game.wizard.human.dialog.PromptDialog;
import com.svanloon.game.wizard.human.dialog.UserPreferences;
import com.svanloon.game.wizard.language.LanguageFactory;
import com.svanloon.game.wizard.language.MessageId;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class HumanPlayerSwing extends AbstractSwingPlayer {

	private static final long serialVersionUID = 8101964106475257540L;

	/**
	 * 
	 * Constructs a new <code>HumanPlayerSwing</code> object. 
	 * @param numberOfPlayers 
	 */
	public HumanPlayerSwing(int numberOfPlayers, UserPreferences up) {
		super(numberOfPlayers, up);
		this.setDoGui(true);
	}

	@Override
	public Card playCard() {
		Card cardPlayed = cbml.findCard();
		return cardPlayed;
	}

	@Override
	public int bid(Card trump, int min, int max, int notAllowedToBid) {
		String suffix = "";
		if(notAllowedToBid > -1) {
			suffix = " (not allowed to bid " + notAllowedToBid + ") ";
		}
		PromptDialog dialog = new PromptDialog(LanguageFactory.getInstance().getString(MessageId.xsBid, "playerName", getName()), LanguageFactory.getInstance().getString(MessageId.whatDoYouWantToBid) + suffix, "", true, 0, round, notAllowedToBid);
		Point location = jFrame.getLocation();
		Dimension size = jFrame.getSize();
		Dimension dialogSize = dialog.getSize();
		dialog.setLocation((int)(location.getX() + size.getWidth() / 2.0 - dialogSize.getWidth() / 2.0), (int)(location.getY() + size.getHeight()/2.0 - dialogSize.getHeight()/2.0));
		dialog.prompt();
		int bid = dialog.getIntValue();
		return bid;
	}

}