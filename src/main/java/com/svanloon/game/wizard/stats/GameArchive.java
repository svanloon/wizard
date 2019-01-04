package com.svanloon.game.wizard.stats;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class GameArchive extends ArrayList<Game>{

	private static final long serialVersionUID = 20070425L;

	private static Object _lock = new Object();

	private static GameArchive _instance = null;

	private Game currentGame;


	private GameArchive() {
		super();
	}

	/**
	 *
	 * Document the getInstance method
	 *
	 * @return GameArchive
	 */
	public static GameArchive getInstance() {
		if (_instance == null) {
			synchronized (_lock) {
				_instance = new GameArchive();
			}
		}
		return _instance;
	}
	/**
	 *
	 * Document the getCurrentGame method
	 *
	 * @return Game
	 */
	public Game getCurrentGame() {
		currentGame = get(size() - 1);
		return currentGame;
	}
	/**
	 *
	 * Document the getCurrentRound method
	 *
	 * @return Round
	 */
	public Round getCurrentRound() {
		return currentGame.getCurrentRound();
	}
	/**
	 *
	 * Document the getCurrentTrick method
	 *
	 * @return Trick
	 */
	public Trick getCurrentTrick() {
		return currentGame.getCurrentRound().getCurrentTrick();
	}

	/**
	 *
	 * Document the newGame method
	 *
	 */
	public void newGame() {
		currentGame = new Game();
		this.add(currentGame);
	}
	/**
	 *
	 * Document the newRound method
	 *
	 * @param roundId
	 */
	public void newRound(int roundId) {
		currentGame.newRound(roundId);
	}
	/**
	 *
	 * Document the newTrick method
	 *
	 */
	public void newTrick() {
		getCurrentRound().newTrick();
	}
	/**
	 *
	 * Document the toText method
	 *
	 * @param playerId
	 * @return String
	 */
	public String toText(int playerId) {
		StringBuilder result = new StringBuilder();
		List<Integer> otherPlayers = null;

		for (Game game: this) {
			//result.append(String.format("%3s\t%3s\t%3s\t%3s\t%s\n", "Rnd", "Bid", "Tk", "HS", "Notes"));
			if (otherPlayers == null) {
				otherPlayers = new ArrayList<Integer>(game.getPlayerMap().keySet());
				otherPlayers.remove(Integer.valueOf(playerId));
			}

			for (Round round: game) {

				result.append(String.format("#%2d|\tBid:%2d Tuk:%2d\t%1.2f [Trump:%3s]",
						Integer.valueOf(round.getRoundId()),
						Integer.valueOf(round.getBid(playerId)),
						Integer.valueOf(round.getTricksWon(playerId)),
						Double.valueOf(round.getHandStrength()),
						round.getTrump()));
				if (round.getNotes() != null) {
					result.append(round.getNotes());
				}
				result.append("\n");
				result.append(round.tricksToString(game.getPlayerMap())).append("\n");
			}
			for (Map.Entry<Integer, Integer> entry: game.getScores().entrySet()) {
				result.append(String.format("%8s scored: %3d\n", game.getPlayerMap().get(entry.getKey()), entry.getValue()));
			}
		}

		return result.toString();
	}
	/**
	 *
	 * Document the summarize method
	 *
	 * @return String
	 */
	public String summarize() {
		StringBuilder result = new StringBuilder();

		Map<Integer, String> players = getCurrentGame().getPlayerMap();

		int iterations = this.size();
		for (Map.Entry<Integer, String> entry: players.entrySet() ) {
			int playerId = entry.getKey().intValue();
			String playerName = entry.getValue();

			int countOverTook = 0;
			int countUnderTook = 0;
			int countPerfect = 0;
			int sumBids = 0;
			int sumTricksWon = 0;
			int gamesWon = 0;
			int sumScore = 0;
			for (Game game: this) {
				for (Round round: game) {
					if (round.getTricksWon(playerId) > round.getBid(playerId)) {
						countOverTook++;
					} else if (round.getTricksWon(playerId) < round.getBid(playerId)) {
						countUnderTook++;
					} else {
						countPerfect++;
					}
					sumBids += round.getBid(playerId);
					sumTricksWon += round.getTricksWon(playerId);
				}

				if (game.getWinners().contains(Integer.valueOf(playerId))) {
					gamesWon++;
				}
				sumScore += game.getScores().get(Integer.valueOf(playerId)).intValue();
			}


			result.append(String.format("%-10s: %3d (*%2d +%2d|%2d %4d|%4d ) %d \n",
					playerName,
					Long.valueOf(roundPercent(gamesWon, iterations)),
					Long.valueOf(roundPercent(countPerfect, getCurrentGame().size() * iterations)),
					Long.valueOf(roundPercent(countOverTook, getCurrentGame().size() * iterations)),
					Long.valueOf(roundPercent(countUnderTook, getCurrentGame().size() * iterations)),
					Integer.valueOf(sumBids),
					Integer.valueOf(sumTricksWon),
					Integer.valueOf(sumScore / iterations)));
		}

		return result.toString();
	}


	private static long roundPercent(int count, int rounds) {
		return Math.round((double) count / (double)rounds * 100);
	}

	/**
	 *
	 * Document the writeToFile method
	 *
	 * @param playerId
	 * @param path
	 */
	public void writeToFile(int playerId, String path) {
		FileWriter fileWriter = null;

		BufferedWriter buffWriter = null;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

		String fileName = "wizard_";
		fileName += currentGame.getPlayerMap().get(Integer.valueOf(playerId)) + "_";
		fileName += sdf.format(new Date());
		fileName += ".txt";

		try {

			fileWriter = new FileWriter(path + "/" + fileName);
			buffWriter = new BufferedWriter(fileWriter);
			GameArchive gameArchive = GameArchive.getInstance();
			buffWriter.write(gameArchive.toText(playerId));
			buffWriter.write(gameArchive.summarize());

		} catch(IOException e) {
			System.out.println("Exception "+ e);
		} finally {
			try {
				if(buffWriter != null) {
					buffWriter.close();
				}
			} catch (IOException e) {
				// ignore
			}
			try {
				if(fileWriter != null) {
					fileWriter.close();
				}
			} catch (IOException e) {
				// ignore
			}
		}
	}
}
