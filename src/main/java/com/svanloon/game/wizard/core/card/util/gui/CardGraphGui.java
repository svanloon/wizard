package com.svanloon.game.wizard.core.card.util.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;

import com.svanloon.game.wizard.core.card.Card;
import com.svanloon.game.wizard.core.card.util.CardNode;
import com.svanloon.game.wizard.core.card.util.WizardCardGraph;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class CardGraphGui extends JFrame {
	private static final long serialVersionUID = -6984669704645530416L;
	private WizardCardGraph cardTree;
	private Set<CardNode> drawn;

	/**
	 * 
	 * Constructs a new <code>CardTreeGui</code> object. 
	 * @param cardTree 
	 *
	 */
	public CardGraphGui(WizardCardGraph cardTree) {
		super();
		this.cardTree = cardTree;
	}

	@Override
	public void paint(Graphics g) {
		Dimension d = this.getSize();

		g.setColor(Color.WHITE);
		g.fillRect(0, 0, (int)d.getWidth(),(int)d.getHeight());

		int height = 25;
		int width = 50;
		drawn = new HashSet<CardNode>();
		int x = 25;
		List<Card> cardList = cardTree.findIsolates();
		for(Card card:cardList) {
			CardNode cardNode = cardTree.findCardNode(card);
			x = x + width + 5;
			draw(cardNode, g, x, 25, width, height);	
		}
	}
	
	private void draw(CardNode baseCardNode, Graphics g, int x, int y, int width, int height) {
		if(drawn.contains(baseCardNode)) {
			return;
		}

		g.setColor(Color.BLACK);

		drawn.add(baseCardNode);
		List<CardNode> cardNodeList = baseCardNode.getLighterNodeCollection();
		g.drawRect(x, y, width, height);
		g.drawString(baseCardNode.getCard().toString(), x + 10, y + 15);

		int i = 0;
		for(CardNode cardNode:cardNodeList) {
			draw(cardNode, g, x + i*width + i*5, y + height + 5, width, height);
			i++;
		}
	}

	/**
	 * Returns the cardTree.
	 *
	 * @return the cardTree.
	 */
	public WizardCardGraph getCardTree() {
		return cardTree;
	}

	/**
	 * Sets the cardTree.
	 *
	 * @param cardTree The new value for cardTree.
	 */
	public void setCardTree(WizardCardGraph cardTree) {
		this.cardTree = cardTree;
	}
}
