package com.svanloon.game.wizard.client.event;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class GameEventBroadcaster {
	private Collection<GameEventListener> listeners = new ArrayList<GameEventListener>();

	/**
	 * 
	 * Document the addListener method 
	 *
	 * @param listener
	 */
	public void addListener(GameEventListener listener) {
		listeners.add(listener);
	}

	/**
	 * 
	 * Document the notify method 
	 *
	 * @param we
	 */
	public void notify(WizardEvent we) {
		if(we instanceof GameOverEvent) {
			GameOverEvent event = (GameOverEvent) we;
			for(GameEventListener listener:listeners) {
				listener.handleGameOver(event);
			}
		}else if(we instanceof HandDealtEvent) {
			HandDealtEvent event = (HandDealtEvent) we;
			for(GameEventListener listener:listeners) {
				listener.handleHandDealt(event);
			}		
		}else if(we instanceof NewGameEvent) {
			NewGameEvent event = (NewGameEvent) we;
			for(GameEventListener listener:listeners) {
				listener.handleNewGame(event);
			}
		} else if(we instanceof NewRoundEvent) {
			NewRoundEvent event = (NewRoundEvent) we;
			for(GameEventListener listener:listeners) {
				listener.handleNewRound(event);
			}
		} else if(we instanceof NewTrickEvent) {
			NewTrickEvent event = (NewTrickEvent) we;
			for(GameEventListener listener:listeners) {
				listener.handleNewTrick(event);
			}
		} else if(we instanceof NewTrumpEvent) {
			NewTrumpEvent event = (NewTrumpEvent) we;
			for(GameEventListener listener:listeners) {
				listener.handleNewTrump(event);
			}
		} else if(we instanceof PlayerBidEvent) {
			PlayerBidEvent event = (PlayerBidEvent) we;
			for(GameEventListener listener:listeners) {
				listener.handlePlayerBid(event);
			}
		} else if(we instanceof PlayerPlayedEvent) {
			PlayerPlayedEvent event = (PlayerPlayedEvent) we;
			for(GameEventListener listener:listeners) {
				listener.handlePlayerPlayed(event);
			}
		} else if(we instanceof PlayerWonTrickEvent) {
			PlayerWonTrickEvent event = (PlayerWonTrickEvent) we;
			for(GameEventListener listener:listeners) {
				listener.handlePlayerWonTrick(event);
			}
		} else if(we instanceof ScoreEvent) {
			ScoreEvent event = (ScoreEvent) we;
			for(GameEventListener listener:listeners) {
				listener.handleScore(event);
			}
		} else if(we instanceof PlayerNeedsToPlay) {
			PlayerNeedsToPlay event = (PlayerNeedsToPlay) we;
			for(GameEventListener listener:listeners) {
				listener.handlePlayerNeedsToPlay(event);
			}
		}
	}
}
