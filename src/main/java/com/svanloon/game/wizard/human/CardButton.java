package com.svanloon.game.wizard.human;

import java.awt.Image;

import com.svanloon.common.util.ImageUtil;
import com.svanloon.game.wizard.core.card.Card;
import com.svanloon.game.wizard.core.card.Suit;

/**
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class CardButton {
	private int x;
	private int y;
	private int height = 97;
	private int width = 76;
	private Card card;
	private Image faceImage;
	private Image verticalBackImage;
	private Image horizontalLeftBackImage;

	/**
	 * 
	 * Constructs a new <code>CardButton</code> object. 
	 *
	 * @param card
	 */
	public CardButton(Card card, String deckDir) {
		this(card, 0,0, deckDir);
	}

	/**
	 * 
	 * Constructs a new <code>MyButton</code> object. 
	 * @param card 
	 * @param x
	 * @param y
	 */
	public CardButton(Card card, int x, int y, String deckDir) {
		this.card = card;
		this.x = x;
		this.y = y;
		String fileName;
		if(card.getValue() == null) {
			fileName = card.getSuit().getDescription().toLowerCase();	
		} else if (card.getSuit().equals(Suit.NONE) == false) {
			fileName = card.getValue().getIndex() + card.getSuit().getDescription().toLowerCase();
		} else {
			fileName = card.getIndex() + card.getValue().getDescription().toLowerCase().toString() + "s";
		}
		this.faceImage = getImage(fileName, deckDir);
		this.verticalBackImage = getImage("bkcvrCorners", deckDir);
		this.horizontalLeftBackImage = getImage("bkcvrCornersFlipL", deckDir);
	}

	/**
	 * 
	 * Document the getImage method 
	 *
	 * @param pDeck
	 * @param cardName
	 * @return Image
	 */
	private Image getImage(String cardName, String deckDir) {
		String path = deckDir;
		String extention = ".gif";
		if(deckDir.equals("deck/") == false) {
			extention = ".jpg";
		}

		String fileName = path + cardName + extention; 
		return ImageUtil.getImage(fileName);
	}

	/**
	 * Returns the height.
	 *
	 * @return the height.
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * Returns the width.
	 *
	 * @return the width.
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * Returns the x.
	 *
	 * @return the x.
	 */
	public int getX() {
		return x;
	}
	/**
	 * Sets the x.
	 *
	 * @param x The new value for x.
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * Returns the y.
	 *
	 * @return the y.
	 */
	public int getY() {
		return y;
	}
	/**
	 * Sets the y.
	 *
	 * @param y The new value for y.
	 */
	public void setY(int y) {
		this.y = y;
	}
	/**
	 * @return the card
	 */
	public Card getCard() {
		return card;
	}
	/**
	 * @param card the card to set
	 */
	public void setCard(Card card) {
		this.card = card;
	}

	/**
	 * Returns the horizontalBackImage.
	 *
	 * @return the horizontalBackImage.
	 */
	public Image getHorizontalLeftBackImage() {
		return horizontalLeftBackImage;
	}
	/**
	 * Returns the verticalBackImage.
	 *
	 * @return the verticalBackImage.
	 */
	public Image getVerticalBackImage() {
		return verticalBackImage;
	}
	/**
	 * @return the image
	 */
	public Image getFaceImage() {
		return faceImage;
	}

	/**
	 * @param image the image to set
	 */
	public void setFaceImage(Image image) {
		this.faceImage = image;
	}
}
