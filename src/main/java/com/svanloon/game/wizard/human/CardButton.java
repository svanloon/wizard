package com.svanloon.game.wizard.human;

import java.awt.Image;

import com.svanloon.common.util.ImageUtil;
import com.svanloon.game.wizard.core.card.Card;
import com.svanloon.game.wizard.core.card.Suit;

public class CardButton {
	private int x;
	private int y;
	private int height = 97;
	private int width = 76;
	private Card card;
	private Image faceImage;
	private Image verticalBackImage;
	private Image horizontalLeftBackImage;

	public CardButton(Card card, String deckDir) {
		this(card, 0,0, deckDir);
	}

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

	private Image getImage(String cardName, String deckDir) {
		String path = deckDir;
		String extention = ".gif";
		if(deckDir.equals("deck/") == false) {
			extention = ".jpg";
		}

		String fileName = path + cardName + extention;
		return ImageUtil.getImage(fileName);
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public Image getHorizontalLeftBackImage() {
		return horizontalLeftBackImage;
	}

	public Image getVerticalBackImage() {
		return verticalBackImage;
	}

	public Image getFaceImage() {
		return faceImage;
	}

	public void setFaceImage(Image image) {
		this.faceImage = image;
	}
}
