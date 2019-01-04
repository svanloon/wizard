package com.svanloon.game.wizard.stats;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.svanloon.game.wizard.core.card.Card;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class Round extends ArrayList<Trick>{
	
	private static final long serialVersionUID = 20070430L;
	
	private int roundId;
	private Map<Integer, Integer> _bids = new LinkedHashMap<Integer, Integer>();
	private Map<Integer, String> hands = new LinkedHashMap<Integer, String>();
	
	private StringBuilder notes = null;
	private double handStrength;
	private Integer leadPlayerId;
	
	private Game game;
	
	// trick, card
	private Trick currentTrick;
	
	private Card trump;
	private int wizardsPlayed;
	private int jestersPlayed;
	/**
	 * 
	 * Constructs a new <code>Round</code> object. 
	 * @param game 
	 *
	 * @param id
	 */
	public Round(Game game, int id) {
		super(id);
		this.game = game;
		this.roundId = id;
	}
	/**
	 * 
	 * Document the newTrick method 
	 *
	 */
	public void newTrick() {
		currentTrick = new Trick(this);
		this.add(currentTrick);
	}
	/**
	 * 
	 * Document the getTricksWon method 
	 *
	 * @param playerId
	 * @return int
	 */
	public int getTricksWon(int playerId) {
		int result = 0;
		for (Trick trick: this) {
			if (trick.getWinner() == playerId) {
				result++;
			}
		}
		return result;
	}
	/**
	 * 
	 * Document the play method 
	 *
	 * @param playerId
	 * @param card
	 */
	public void play(int playerId, Card card) {
		if (card != null) {
			currentTrick.add(new Play(playerId, card));
			if (card.isWizard()) {
				wizardsPlayed ++;
				currentTrick.setWizardPlayed(true);
			} else if (card.isJester()) {
				jestersPlayed ++;
			}
		}
	}
	/**
	 * 
	 * Document the playToString method 
	 *
	 * @param playerMap
	 * @return String
	 */
	public String tricksToString(Map<Integer, String> playerMap) {
		StringBuilder sb = new StringBuilder();
		sb.append(playerHeader());
		for (Trick trick: this) {
			sb.append(trick.toString(playerMap)).append("\n");
		}
		if (sb.length() > 0 ) {
			return sb.toString();
		}
		return "[]\n";
	}
	
	String fmt = "%4s";
	String spacer = "    ";
	String underscore = " ---";
	
	/**
	 * 
	 * Document the playerHeader method 
	 *
	 * @return String
	 */
	public String playerHeader() {
		StringBuilder bids = new StringBuilder();
		StringBuilder names = new StringBuilder();
		StringBuilder line = new StringBuilder();
		int slots = game.getPlayerMap().size();
		slots += slots - 1;
		while (slots > 0) {
			for (Map.Entry<Integer, String> entry: game.getPlayerMap().entrySet()) {
				Integer playerId = entry.getKey();
				String name = entry.getValue();
				if (name.length() > spacer.length() - 1) {
					name = name.substring(0, spacer.length() - 1);
				}
				bids.append(String.format(fmt, getBid(playerId.intValue()) + "/" + getTricksWon(playerId.intValue())));
				names.append(String.format(fmt, name));
				line.append(underscore);
				
				slots --;
				if (slots == 0) {
					break;
				}
			}
		}
		// in the names line, show the current scores
		names.append("\n");
		names.append(bids);
		names.append("\n");
		names.append(line);
		names.append("\n");
		return names.toString();
	}
	
	
	/**
	 * 
	 * Document the getCurrentTrick method 
	 *
	 * @return Trick
	 */
	public Trick getCurrentTrick() {
		return currentTrick;
	}
	/**
	 * 
	 * Document the getBid method 
	 *
	 * @param playerId
	 * @return int
	 */
	public int getBid(int playerId) {
		return _bids.get(Integer.valueOf(playerId)).intValue();
	}
	/**
	 * 
	 * Document the getBids method 
	 *
	 * @return Map
	 */
	public Map<Integer, Integer> getBids() {
		return _bids;
	}
	/**
	 * 
	 * Document the getHand method 
	 *
	 * @param playerId
	 * @return String
	 */
	public String getHand(int playerId) {
		return hands.get(Integer.valueOf(playerId));
	}
	/**
	 * 
	 * Document the getHandStrength method 
	 *
	 * @return double
	 */
	public double getHandStrength() {
		return handStrength;
	}
	/**
	 * 
	 * Document the getRoundId method 
	 *
	 * @return int
	 */
	public int getRoundId() {
		return roundId;
	}
	/**
	 * 
	 * Document the getLeadPlayerId method 
	 *
	 * @return Integer
	 */
	public Integer getLeadPlayerId() {
		return leadPlayerId;
	}
	
	/**
	 * 
	 * Document the getTrump method 
	 *
	 * @return Card
	 */
	public Card getTrump() {
		return trump;
	}
	/**
	 * 
	 * Document the getWizardsPlayed method 
	 *
	 * @return int
	 */
	public int getWizardsPlayed() {
		return wizardsPlayed;
	}
	/**
	 * 
	 * Document the setBid method 
	 *
	 * @param playerId
	 * @param pBid
	 */
	public void setBid(int playerId, int pBid) {
		_bids.put(Integer.valueOf(playerId), Integer.valueOf(pBid));
	}
	/**
	 * 
	 * Document the setHand method 
	 *
	 * @param playerId
	 * @param pHand
	 */
	public void setHand(int playerId, String pHand) {
		hands.put(Integer.valueOf(playerId), pHand);
	}
	/**
	 * 
	 * Document the setHandStrength method 
	 *
	 * @param pHandStrength
	 */
	public void setHandStrength(double pHandStrength) {
		handStrength = pHandStrength;
	}
	/**
	 * 
	 * Document the getJestersPlayed method 
	 *
	 * @return int
	 */
	public int getJestersPlayed() {
		return jestersPlayed;
	}
	/**
	 * 
	 * Document the removeWizard method 
	 *
	 */
	public void removeWizard() {
		wizardsPlayed--;
	}
	
	/**
	 * 
	 * Document the removeJester method 
	 *
	 */
	public void removeJester() {
		jestersPlayed--;
	}
	/**
	 * 
	 * Document the setTrump method 
	 *
	 * @param pTrump
	 */
	public void setTrump(Card pTrump) {
		trump = pTrump;
	}
	/**
	 * 
	 * Document the getNotes method 
	 *
	 * @return String
	 */
	public String getNotes() {
		if (notes != null) {
			return notes.toString();
		}
		return null;
	}
	/**
	 * 
	 * Document the addNotes method 
	 *
	 * @param pNotes
	 */
	public void addNotes(String pNotes) {
		if (notes == null) {
			notes = new StringBuilder();
			notes.append(pNotes);
		} else {
			notes.append(",").append(pNotes);
		}
	}
	/**
	 * @return the currentGame
	 */
	public Game getGame() {
		return game;
	}
	
}
