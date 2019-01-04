package com.svanloon.game.wizard.human.dialog;

import java.util.Locale;

import com.svanloon.game.wizard.language.LanguageFactory;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class UserPreferences {
	private String deck = "Default";
	private int trickSummaryType = 1;
	private Locale locale = LanguageFactory.getInstance().getLocale();
	private String name = "";
	private String ip = "";

	/**
	 * Constructs a new <code>UserPreferences</code> object. 
	 */
	public UserPreferences() {
		super();
	}
	/**
	 * 
	 * Document the setLocale method 
	 *
	 * @param locale
	 */
	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	/**
	 * 
	 * Document the getLocale method 
	 *
	 * @return Locale
	 */
	public Locale getLocale() {
		return locale; 
	}
	/**
	 * 
	 * Document the getDeck method 
	 *
	 * @return String
	 */
	public String getDeck() {
		return deck;
	}

	/**
	 * 
	 * Document the getTrickSummaryType method 
	 *
	 * @return int
	 */
	public int getTrickSummaryType() {
		return trickSummaryType;
	}
	private boolean displayScoreBoard;
	/**
	 * 
	 * Document the getDisplayScoreBoard method 
	 *
	 * @return boolean
	 */
	public boolean getDisplayScoreBoard() {
		return displayScoreBoard;
	}
	/**
	 * 
	 * Document the displayTotal method 
	 *
	 * @return boolean
	 */
	public boolean displayTotal() {
		return false;
	}
	/**
	 * 
	 * Document the displayRunningTotal method 
	 *
	 * @return boolean
	 */
	public boolean displayRunningTotal() {
		return true;
	}
	/**
	 * 
	 * Document the displayScoreChange method 
	 *
	 * @return boolean
	 */
	public boolean displayScoreChange() {
		return false;
	}
	/**
	 * 
	 * Document the displayActualTaken method 
	 *
	 * @return boolean
	 */
	public boolean displayActualTaken() {
		return false;
	}
	/**
	 * 
	 * Document the displayBid method 
	 *
	 * @return boolean
	 */
	public boolean displayBid() {
		return true;
	}
	/**
	 * @param deck the deck to set
	 */
	public void setDeck(String deck) {
		this.deck = deck;
	}
	/**
	 * @param trickSummaryType the trickSummaryType to set
	 */
	public void setTrickSummaryType(int trickSummaryType) {
		this.trickSummaryType = trickSummaryType;
	}
	/**
	 * @param displayScoreBoard the displayScoreBoard to set
	 */
	public void setDisplayScoreBoard(boolean displayScoreBoard) {
		this.displayScoreBoard = displayScoreBoard;
	}
	/**
	 * returns the ip.
	 *
	 * @return the ip.
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * Sets the ip.
	 *
	 * @param ip The new value for ip.
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * Returns the name.
	 *
	 * @return the name.
	 */
	public String getName() {
		return name;
	}
	/**
	 * Sets the name.
	 *
	 * @param name The new value for name.
	 */
	public void setName(String name) {
		this.name = name;
	}
}
