package com.svanloon.game.wizard.human.dialog;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.svanloon.game.wizard.core.gameOptions.BidType;
import com.svanloon.game.wizard.core.gameOptions.GameOptions;
import com.svanloon.game.wizard.core.gameOptions.GameSpeed;


public class GameOptionsManager extends PropertyManager {
	private static Logger _logger = Logger.getLogger(GameOptionsManager.class);
	private static final String BID_TYPE = "BID_TYPE";
	private static final String GAME_SPEED = "GAME_SPEED";

	/**
	 * Constructs a new <code>GameOptionsManager</code> object.
	 */
	public GameOptionsManager() {
		super("gameoption.properties");
	}

	/**

	 * Document the load method
	 * @return GameOptions
	 */
	public GameOptions load() {
		try {
			loadProperties();
		} catch (IOException e) {
			_logger.info("Game Options are not set up, using defaults instead");
			return new GameOptions();
		}

		GameOptions go = new GameOptions();
		go.setBidType(BidType.valueOf(getProperty(BID_TYPE, go.getBidType().toString())));
		go.setGameSpeed(GameSpeed.valueOf(getProperty(GAME_SPEED, go.getGameSpeed().toString())));
		return go;
	}

	/**
	 * Document the persist method
	 */
	public void persist(GameOptions go) {
		try {
			openPropertyFileForWritting();
		} catch (IOException e) {
			_logger.warn("Couldn't create user perferences file", e);
			return;
		}

		setProperty(BID_TYPE, go.getBidType().toString());
		setProperty(GAME_SPEED, go.getGameSpeed().toString());
		closePropertyFile();
	}

}
