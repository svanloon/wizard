package com.svanloon.game.wizard.human.dialog;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.log4j.Logger;




/**
 * 
 * Document the  class 
 *
 * @author svanloon
 */
public class UserPreferencesManager extends PropertyManager {
	private static Logger _logger = Logger.getLogger(UserPreferencesManager.class);

	private static final String DECK = "DECK";
	private static final String DISPLAY_SCORE_BOARD = "DISPLAY_SCORE_BOARD";
	private static final String LOCALE = "LOCALE";
	private static final String TRICK_SUMMARY_TYPE = "TRICK_SUMMARY_TYPE";
	private static final String NAME = "NAME";
	private static final String IP = "IP";

	/**
	 * Constructs a new <code>UserPreferencesManager</code> object. 
	 */
	public UserPreferencesManager() {
		super("userPreferences.properties");
	}


	/**
	 * 
	 * Document the load method 
	 *
	 * @return UserPreferences
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	public UserPreferences load() {

		try {
			loadProperties();
		} catch (IOException e) {
			return new UserPreferences();
		}
		UserPreferences up = new UserPreferences();
		up.setDeck(getProperty(DECK, up.getDeck()));
		up.setDisplayScoreBoard(getPropertyAsBoolean(DISPLAY_SCORE_BOARD, up.getDisplayScoreBoard()));
		up.setLocale(getPropertyAsLocale(LOCALE, up.getLocale()));
		up.setTrickSummaryType(getPropertyAsInt(TRICK_SUMMARY_TYPE, up.getTrickSummaryType()));
		up.setName(getProperty(NAME, up.getName()));
		up.setIp(getProperty(IP, up.getIp()));
		return up;
	}


	/**
	 * 
	 * Document the persist method 
	 *
	 * @param up
	 * @throws FileNotFoundException 
	 */
	public void persist(UserPreferences up) {
		
		try {
			openPropertyFileForWritting();
		} catch (IOException e) {
			_logger.warn("Couldn't create user perferences file", e);
			return;
		}

		setProperty(LOCALE, up.getLocale());
		setProperty(DECK, up.getDeck());
		setProperty(DISPLAY_SCORE_BOARD, up.getDisplayScoreBoard());
		setProperty(TRICK_SUMMARY_TYPE, up.getTrickSummaryType());
		setProperty(NAME, up.getName());
		setProperty(IP, up.getIp());

		closePropertyFile();
	}
}