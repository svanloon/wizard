package com.svanloon.game.wizard.client.player.temp;

import java.util.ArrayList;
import java.util.List;

import com.svanloon.game.wizard.client.event.GameEventListener;
import com.svanloon.game.wizard.client.event.GameOverEvent;
import com.svanloon.game.wizard.client.event.HandDealtEvent;
import com.svanloon.game.wizard.client.event.NewGameEvent;
import com.svanloon.game.wizard.client.event.NewRoundEvent;
import com.svanloon.game.wizard.client.event.NewTrickEvent;
import com.svanloon.game.wizard.client.event.NewTrumpEvent;
import com.svanloon.game.wizard.client.event.PlayerBidEvent;
import com.svanloon.game.wizard.client.event.PlayerNeedsToPlay;
import com.svanloon.game.wizard.client.event.PlayerPlayedEvent;
import com.svanloon.game.wizard.client.event.PlayerWonTrickEvent;
import com.svanloon.game.wizard.client.event.ScoreEvent;
import com.svanloon.game.wizard.client.player.Opponent;
import com.svanloon.game.wizard.client.player.TournamentSummary;
import com.svanloon.game.wizard.core.card.Card;
import com.svanloon.game.wizard.core.card.Hand;
import com.svanloon.game.wizard.core.card.util.WizardCardGraph;
import com.svanloon.game.wizard.core.card.util.WizardPoints;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class GameObserverListener implements GameEventListener {
	private OpponentSummary opponentSummary = new OpponentSummary();
	private CardCountGui cardCounter = new CardCountGui();
	private WizardCardGraph wct; 
	private int id;
	private Hand hand = new Hand();
	private String name;

	/**
	 * Constructs a new <code>GameObserverListener</code> object. 
	 */
	public GameObserverListener() {
		super();
		wct = new WizardCardGraph(new WizardPoints());
	}
	/**
	 * 
	 * Document the setDoGui method 
	 *
	 * @param onOff
	 */
	public void setDoGui(boolean onOff) {
		opponentSummary.setDoGui(onOff);
		cardCounter.setDoGui(onOff);
	}
	
	/**
	 * 
	 * Document the setVisibleOpponentSummary method 
	 *
	 * @param visible
	 */
	public void setVisibleOpponentSummary(boolean visible) {
		opponentSummary.setVisible(visible);
	}
	/**
	 * 
	 * Document the isVisibleOpponentSummary method 
	 *
	 * @return boolean
	 */
	public boolean isVisibleOpponentSummary() {
		return opponentSummary.isVisible();
	}
	/**
	 * 
	 * Document the setVisilibleCardCounter method 
	 *
	 * @param visibility
	 */
	public void setVisilibleCardCounter(boolean visibility) {
		cardCounter.setVisible(visibility);
	}
	/**
	 * 
	 * Document the isVisilibleCardCounter method 
	 *
	 * @return boolean
	 */
	public boolean isVisilibleCardCounter() {
		return cardCounter.isVisible();
	}

	/**
	 * 
	 * Document the getHand method 
	 *
	 * @return Hand
	 */
	public Hand getHand() {
		return this.hand;
	}
	/**
	 * 
	 * Document the giveCard method 
	 *
	 * @param card
	 */
	public void giveCard(Card card) {
		this.hand.add(card);
	}
	/**
	 * 
	 * Document the playCardIsNotValid method 
	 *
	 * @param card
	 */
	public void playCardIsNotValid(Card card) {
		this.hand.remove(card);
		this.hand.add(card);
	}
	/**
	 * 
	 * Document the playCardIsValid method 
	 *
	 * @param card
	 */
	public void playCardIsValid(Card card) {
		this.hand.remove(card);
	}
	/**
	 * 
	 * Document the getId method 
	 *
	 * @return int
	 */
	public int getId() {
		return id;
	}
	/**
	 * 
	 * Document the setId method 
	 *
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * 
	 * Document the setName method 
	 *
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * 
	 * Document the getName method 
	 *
	 * @return String
	 */
	public String getName() {
		return name;
	}

	public void handleGameOver(GameOverEvent e) {
		for(Opponent opponent: opponentSummary.getOpponents()) {
			String name2 = opponent.getName();
			int score = opponent.getScore();
			TournamentSummary.getInstance().addScore(name2, score);
		}
	}

	public void handleHandDealt(HandDealtEvent e) {
		cardCounter.init(opponentSummary.getOpponents().size(), hand.size());
	}

	public void handleNewGame(NewGameEvent e) {
		List<Opponent> opponents = new ArrayList<Opponent>(); 
		
		for(int i = 0; i < e.getPlayerNames().size(); i++) {
			int tempId = e.getIds().get(i).intValue();
			String playerName = e.getPlayerNames().get(i);
			Opponent opponent = new Opponent(tempId, playerName);
			opponents.add(opponent);
		}
		opponentSummary.setOpponent(opponents);
	}
	
	public void handleNewRound(NewRoundEvent e) {
		cardCounter.newRound();
		opponentSummary.setRound(e.getRound());
	}

	public void handleNewTrick(NewTrickEvent e) {
		opponentSummary.newTrick();
	}

	public void handleNewTrump(NewTrumpEvent e) {
		Card trump = e.getCard();
		cardCounter.setTrump(trump);
		opponentSummary.setTrump(trump);
		wct = new WizardCardGraph(new WizardPoints());
		if(trump == null) {
			wct.setTrump(null);
		} else {
			wct.setTrump(trump.getSuit());
		}
	}

	public void handlePlayerBid(PlayerBidEvent e) {
		opponentSummary.setBid(e.getPlayerId(), e.getBid());
	}

	public void handlePlayerPlayed(PlayerPlayedEvent e) {
		Card playedCard = e.getCard();
		cardCounter.countCard(playedCard);
		opponentSummary.played(e.getPlayerId(), playedCard);
		wct.removeCard(playedCard);
	}

	public void handlePlayerWonTrick(PlayerWonTrickEvent e) {
		opponentSummary.wonTrick(e.getPlayerId());
	}

	public void handleScore(ScoreEvent e) {
		opponentSummary.changeScore(e.getPlayerId(), e.getScoreChangeAmount());
	}

	/**
	 * Returns the trump.
	 *
	 * @return the trump.
	 */
	public Card getTrump() {
		return opponentSummary.getTrump();
	}

	/**
	 * Returns the lead.
	 *
	 * @return the lead.
	 */
	public Card getLead() {
		return opponentSummary.getLead();	
	}

	/**
	 * Returns the highCard.
	 *
	 * @return the highCard.
	 */
	public Card getHighCard() {
		return opponentSummary.getHighCard();
	}

	public void handlePlayerNeedsToPlay(PlayerNeedsToPlay e) {
		// Auto-generated method stub
	}
	/**
	 * @return the opponentSummary
	 */
	public OpponentSummary getOpponentSummary() {
		return opponentSummary;
	}
	/**
	 * Returns the wct.
	 *
	 * @return the wct.
	 */
	public WizardCardGraph getWct() {
		return wct;
	}
}
