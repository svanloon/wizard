package com.svanloon.game.wizard.client.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class TournamentSummary {
	private static Logger _logger = Logger.getLogger(TournamentSummary.class);
	private static TournamentSummary _instance = null;
	/**
	 * 
	 * Document the getInstance method 
	 *
	 * @return TournamentSummary
	 */
	public static TournamentSummary getInstance() {
		if(_instance == null) {
			_instance = new TournamentSummary();
		}
		return _instance;
	}
	private Map<String, List<Integer>> playerSummary = new HashMap<String, List<Integer>>();

	private boolean isFirst = true;
	private int count = 0;
	/**
	 * 
	 * Document the newGame method 
	 *
	 */
	public void newGame() {
		isFirst = true;
		count = 0;
	}
	/**
	 * 
	 * Document the addScore method 
	 *
	 * @param playerName
	 * @param score
	 */
	public void addScore(String playerName, int score) {
		if(isFirst) {
			count++;
			if(count == 4) {
				isFirst = false;
			}
			List<Integer> games = playerSummary.get(playerName);
			if(games == null) {
				games = new ArrayList<Integer>();
				playerSummary.put(playerName, games);
			}
			games.add(Integer.valueOf(score));
		}
	}
	/**
	 * 
	 * Document the print method 
	 *
	 */
	public void print() {
		for(String name :playerSummary.keySet()) {
			StringBuffer sb = new StringBuffer();
			sb.append(name +" = [");
			int sum = 0;
			int count2 = 0;
			int max = Integer.MIN_VALUE;
			int min = Integer.MAX_VALUE;
			int belowZero = 0;
			int rock = 0;
			boolean isFirst2 = true;
			for(Integer score : playerSummary.get(name)) {
				if(isFirst2) {
					isFirst2 = false;
				} else {
					//sb.append(",\t");
				}
				count2++;
				sum += score.intValue();
				max = Math.max(max, score.intValue());
				min = Math.min(min, score.intValue());
				if(score.intValue() < 0) {
					belowZero++;
				} else if (score.intValue() == 0) {
					rock++;
				}
				//sb.append(score);
			}
			sb.append("] average=");
			sb.append(sum/count2);
			sb.append(", max=");
			sb.append(max);
			sb.append(", min = ");
			sb.append(min);
			sb.append(", worse than rock = ");
			sb.append(belowZero);
			sb.append(", same as rock = ");
			sb.append(rock);
			_logger.info(sb);
		}
		
	}
}
