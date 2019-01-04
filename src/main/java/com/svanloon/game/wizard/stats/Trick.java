package com.svanloon.game.wizard.stats;

import java.util.ArrayList;
import java.util.Map;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class Trick extends ArrayList<Play> {
	private static final long serialVersionUID = 20070424L;

	private int winner;
	
	private StringBuilder notes = null;
	
	private Round round;
	private boolean wizardPlayed;
	/**
	 * 
	 * Constructs a new <code>Trick</code> object. 
	 * @param round 
	 *
	 */
	public Trick(Round round) {
		super();
		this.round = round;
	}
	/**
	 * 
	 * Document the getWinner method 
	 *
	 * @return int
	 */
	public int getWinner() {
		return winner;
	}
	/**
	 * 
	 * Document the setWinner method 
	 *
	 * @param pWinner
	 */
	public void setWinner(int pWinner) {
		winner = pWinner;
	}
	@Override
	public String toString() {
		String result = "[w " + winner; 
		result += super.toString();
		result += "]";
		return result;
	}

	/**
	 * 
	 * Document the isWizardPlayed method 
	 *
	 * @return boolean
	 */
	public boolean isWizardPlayed() {
		return wizardPlayed;
	}
	
	/**
	 * 
	 * Document the setWizardPlayed method 
	 *
	 * @param pWizardPlayed
	 */
	public void setWizardPlayed(boolean pWizardPlayed) {
		wizardPlayed = pWizardPlayed;
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
			notes.append(", ").append(pNotes);
		}
	}
	/**
	 * 
	 * Document the toString method 
	 *
	 * @param playerMap
	 * @return String
	 */
	public String toString(Map<Integer, String> playerMap) {
		StringBuilder sb = new StringBuilder();
		String card = null;
		Object[] plays = new Object[4];
		int i = 0;
		for (Play play: this) {
			if (play.getPlayerId() == this.getWinner()) {
				card = "*" + play.getCard();
			} else {
				card = play.getCard().toString();
			}
			plays[i++] = card;
//			if (playerMap != null) {
//				sb.append(String.format("%8s:%4s", playerMap.get(Integer.valueOf(play.getPlayerId())), card));
//			} else {
//				sb.append(String.format("%8s:%4s", Integer.valueOf(play.getPlayerId()), card));
//			}
		}
		sb.append(String.format(playerFormatter(), plays));
		
		if (this.getNotes() != null) {
			sb.append("  | ");
			sb.append(this.getNotes());
		}
		return sb.toString();
	}

	/**
	 * 
	 * Document the playerFormatter method 
	 *
	 * @return String
	 */
	public String playerFormatter() {
		StringBuilder sb = new StringBuilder();
		int slots = round.getGame().getPlayerMap().size() - 1;
		Play leader = this.get(0);
		// find leader position
		for (Integer playerId: round.getGame().getPlayerMap().keySet()) {
			if (playerId.intValue() == leader.getPlayerId()) {
				break;
			}
			sb.append(round.spacer);
			slots --;
		}
		
		for (int i=0; i<round.getGame().getPlayerMap().size(); i++) {
			sb.append(round.fmt);
		}
		for (int i=0; i<slots; i++) {
			sb.append(round.spacer);
		}
		
		return sb.toString();
	}
	
	
	/**
	 * @return the currentRound
	 */
	public Round getRound() {
		return round;
	}
}
