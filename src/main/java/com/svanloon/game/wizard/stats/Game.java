package com.svanloon.game.wizard.stats;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class Game extends ArrayList<Round> {
	
	private static final long serialVersionUID = 20070425L;

	private Map<Integer, Integer> _scores = new LinkedHashMap<Integer, Integer>();
	private List<Integer> _winners = new ArrayList<Integer>();
	

	private Round currentRound;
	
	private Map<Integer, String> playerMap = new LinkedHashMap<Integer, String>();
	
	/**
	 * 
	 * Constructs a new <code>Game</code> object. 
	 *
	 */
	public Game() {
		super();
	}
	
	/**
	 * 
	 * @return Round
	 */
	public Round getCurrentRound() {
		return currentRound;
	}
	
	/**
	 * 
	 * @param roundId
	 */
	public void newRound(int roundId) {
		currentRound = new Round(this, roundId);
		this.add(currentRound);
	}
	
	/**
	 * 
	 * Document the addPlayer method 
	 *
	 * @param playerId
	 * @param playerName
	 */
	public void addPlayer(int playerId, String playerName) {
		playerMap.put(Integer.valueOf(playerId), playerName);
	}
	
	/**
	 * 
	 * Document the addWinner method 
	 *
	 * @param playerId
	 */
	public void addWinner(int playerId) {
		_winners.add(Integer.valueOf(playerId));
	}
	
	/**
	 * 
	 * Document the addScore method 
	 *
	 * @param pPlayerId
	 * @param pScore
	 */
	public void addScore(int pPlayerId, int pScore) {
		Integer playerId = Integer.valueOf(pPlayerId);
		Integer prev = _scores.get(playerId);
		if (prev == null) {
			prev = Integer.valueOf(0);
		}
		_scores.put(playerId, Integer.valueOf(pScore + prev.intValue()));
	}
	
	/**
	 * 
	 * Document the getScores method 
	 *
	 * @return Map
	 */
	public Map<Integer, Integer> getScores() {
		return _scores;
	}
	/**
	 * 
	 * Document the getWinners method 
	 *
	 * @return List
	 */
	public List<Integer> getWinners() {
		return _winners;
	}

	/**
	 * 
	 * Document the getPlayerMap method 
	 *
	 * @return Map
	 */
	public Map<Integer, String> getPlayerMap() {
		return playerMap;
	}
	
}
