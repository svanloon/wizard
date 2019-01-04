package com.svanloon.game.wizard.human;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.font.TextAttribute;
import java.net.URL;
import java.text.AttributedString;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.svanloon.game.wizard.core.card.Card;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class CardPlayedPanel  extends JPanel {
	private static final long serialVersionUID = 1L;
	private String deckDir;
	/**
	 * 
	 * Constructs a new <code>CardPlayedPanel</code> object. 
	 * @param label 
	 *
	 */
	public CardPlayedPanel(String label, String deckDir) {
		super();
		this.label = label;
		String fileName = "images/gray.png";
		URL imageUrl = this.getClass().getClassLoader().getResource(fileName); 
		gray = new ImageIcon(imageUrl).getImage();
		this.deckDir = deckDir;
	}
	private Image gray;

	private String label;
	/**
	 * 
	 * Document the setLabel method 
	 *
	 * @param label
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	private Card card;
	/**
	 * 
	 * Document the setCard method 
	 *
	 * @param card
	 */
	public void setCard(Card card) {
		this.card = card;
		this.repaint();
	}
	private boolean highlight = false;

	@Override
	public void paint(Graphics g) {
		Dimension d = this.getSize();
		double compWidth = d.getWidth();
		double compHeight = d.getHeight();
		g.setColor(GuiConstants.BACKGROUND_COLOR);
		g.clearRect(0, 0, (int)compWidth , (int) compHeight);
		g.fillRect(0, 0, (int) compWidth, (int) compHeight);

		Color old = g.getColor();
		
		if(label == null) {
			g.drawString("", (int)(compWidth/2.0) ,(int)(compHeight/2.0));
		} else {
			int offset = 2;
			g.setColor(GuiConstants.CARD_OUTLINE_COLOR);
			if(highlight) {
				Color bg = GuiConstants.CARD_HIGHLIGHT_COLOR;
				//Color bg = new Color(217, 48, 65);
				//Color bg = new Color(189, 42, 56);
				g.setColor(bg);
			}
			double width = 77;
			double height = 97;
			g.fillRoundRect((int)((compWidth - width)/2), 0, (int) width, (int) height, offset, offset);
			g.setColor(GuiConstants.BACKGROUND_COLOR);
			g.fillRoundRect((int)((compWidth - width)/2) + offset, 0 + offset, (int) width - offset*2, (int) height - offset*2, offset, offset);

			g.setColor(Color.BLACK);
			AttributedString aci = new AttributedString(label);
	
			Font font = GuiConstants.PLAYER_NAME_FONT;
			FontMetrics metrics = g.getFontMetrics(font);
			aci.addAttribute(TextAttribute.FONT, font);
			aci.addAttribute(TextAttribute.JUSTIFICATION, TextAttribute.JUSTIFICATION_FULL);
			g.drawString(aci.getIterator(), (int)((compWidth - metrics.stringWidth(label))/2.0) ,(int)(height/2.0));
		}
		g.setColor(old);

		if(card == null) {
			return;
		}

		g.fillRect(0, 0, (int) compWidth, (int) compHeight);

		CardButton cardButton = new CardButton(card, deckDir);
		double cardWidth = cardButton.getWidth();
		double cardHeight = cardButton.getHeight();
		g.drawImage(cardButton.getFaceImage(), (int)((compWidth - cardWidth)/2) ,0, (int)cardWidth, (int)cardHeight, this);
		if(fade) {
			g.drawImage(gray, (int)((compWidth - cardWidth)/2) ,0, (int)cardWidth, (int)cardHeight, this);
		}
	}
	private boolean fade;

	/**
	 * @return the highlight
	 */
	public boolean isHighlight() {
		return highlight;
	}

	/**
	 * @param highlight the highlight to set
	 */
	public void setHighlight(boolean highlight) {
		this.highlight = highlight;
		this.repaint();
	}

	/**
	 * @return the fade
	 */
	public boolean isFade() {
		return fade;
	}

	/**
	 * @param fade the fade to set
	 */
	public void setFade(boolean fade) {
		this.fade = fade;
		this.repaint();
	}
}
