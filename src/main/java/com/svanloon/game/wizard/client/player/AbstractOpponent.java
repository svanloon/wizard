package com.svanloon.game.wizard.client.player;

import com.svanloon.game.wizard.core.card.Card;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class AbstractOpponent {
	private double hearts;
	private double clubs;
	private double spades;
	private double diamonds;
	private double wizards;
	private double jesters;
	private double bid;
	private int round;
	private Card trump;
	private String name;
	private int id;
	private boolean hasHearts = true;
	private boolean hasSpades = true;
	private boolean hasClubs = true;
	private boolean hasDiamonds = true;
	private boolean hasWizards = true;
	private boolean hasJesters = true;
	/**
	 * 
	 * Document the setRound method 
	 *
	 * @param round
	 */
	public void setRound(int round) {
		this.round = round;
		hasHearts = true;
		hasSpades = true;
		hasClubs = true;
		hasDiamonds = true;
		hasWizards = true;
		hasJesters = true;
	}

	/**
	 * @return the clubs
	 */
	public double getClubs() {
		return clubs;
	}
	/**
	 * @param clubs the clubs to set
	 */
	public void setClubs(double clubs) {
		this.clubs = clubs;
	}
	/**
	 * @return the diamonds
	 */
	public double getDiamonds() {
		return diamonds;
	}
	/**
	 * @param diamonds the diamonds to set
	 */
	public void setDiamonds(double diamonds) {
		this.diamonds = diamonds;
	}
	/**
	 * @return the hearts
	 */
	public double getHearts() {
		return hearts;
	}
	/**
	 * @param hearts the hearts to set
	 */
	public void setHearts(double hearts) {
		this.hearts = hearts;
	}
	/**
	 * @return the jesters
	 */
	public double getJesters() {
		return jesters;
	}
	/**
	 * @param jesters the jesters to set
	 */
	public void setJesters(double jesters) {
		this.jesters = jesters;
	}
	/**
	 * @return the spades
	 */
	public double getSpades() {
		return spades;
	}
	/**
	 * @param spades the spades to set
	 */
	public void setSpades(double spades) {
		this.spades = spades;
	}
	/**
	 * @return the wizards
	 */
	public double getWizards() {
		return wizards;
	}
	/**
	 * @param wizards the wizards to set
	 */
	public void setWizards(double wizards) {
		this.wizards = wizards;
	}

	/**
	 * @return the trump
	 */
	public Card getTrump() {
		return trump;
	}

	/**
	 * @param trump the trump to set
	 */
	public void setTrump(Card trump) {
		this.trump = trump;
	}

	/**
	 * @return the round
	 */
	public int getRound() {
		return round;
	}

	/**
	 * @return the bid
	 */
	public double getBid() {
		return bid;
	}

	/**
	 * @param bid the bid to set
	 */
	public void setBid(double bid) {
		this.bid = bid;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the hasClubs
	 */
	public boolean isHasClubs() {
		return hasClubs;
	}

	/**
	 * @return the hasDiamonds
	 */
	public boolean isHasDiamonds() {
		return hasDiamonds;
	}

	/**
	 * @return the hasHearts
	 */
	public boolean isHasHearts() {
		return hasHearts;
	}

	/**
	 * @return the hasSpades
	 */
	public boolean isHasSpades() {
		return hasSpades;
	}

	/**
	 * @return the hasJesters
	 */
	public boolean isHasJesters() {
		return hasJesters;
	}

	/**
	 * @return the hasWizards
	 */
	public boolean isHasWizards() {
		return hasWizards;
	}

	/**
	 * @param hasClubs the hasClubs to set
	 */
	public void setHasClubs(boolean hasClubs) {
		this.hasClubs = hasClubs;
	}

	/**
	 * @param hasDiamonds the hasDiamonds to set
	 */
	public void setHasDiamonds(boolean hasDiamonds) {
		this.hasDiamonds = hasDiamonds;
	}

	/**
	 * @param hasHearts the hasHearts to set
	 */
	public void setHasHearts(boolean hasHearts) {
		this.hasHearts = hasHearts;
	}

	/**
	 * @param hasJesters the hasJesters to set
	 */
	public void setHasJesters(boolean hasJesters) {
		this.hasJesters = hasJesters;
	}

	/**
	 * @param hasSpades the hasSpades to set
	 */
	public void setHasSpades(boolean hasSpades) {
		this.hasSpades = hasSpades;
	}

	/**
	 * @param hasWizards the hasWizards to set
	 */
	public void setHasWizards(boolean hasWizards) {
		this.hasWizards = hasWizards;
	}
}
