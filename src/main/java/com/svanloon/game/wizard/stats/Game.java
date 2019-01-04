package com.svanloon.game.wizard.stats;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Game extends ArrayList<Round> {

	private static final long serialVersionUID = 20070425L;

	private Map<Integer, Integer> _scores = new LinkedHashMap<Integer, Integer>();
	private List<Integer> _winners = new ArrayList<Integer>();


	private Round currentRound;

	private Map<Integer, String> playerMap = new LinkedHashMap<Integer, String>();

	public Game() {
		super();
	}

	public Round getCurrentRound() {
		return currentRound;
	}

	public void newRound(int roundId) {
		currentRound = new Round(this, roundId);
		this.add(currentRound);
	}

	public void addPlayer(int playerId, String playerName) {
		playerMap.put(playerId, playerName);
	}

	public void addWinner(int playerId) {
		_winners.add(playerId);
	}

	public void addScore(int pPlayerId, int pScore) {
		Integer playerId = pPlayerId;
		Integer prev = _scores.get(playerId);
		if (prev == null) {
			prev = 0;
		}
		_scores.put(playerId, pScore + prev.intValue());
	}

	public Map<Integer, Integer> getScores() {
		return _scores;
	}

	public List<Integer> getWinners() {
		return _winners;
	}

	public Map<Integer, String> getPlayerMap() {
		return playerMap;
	}

}
