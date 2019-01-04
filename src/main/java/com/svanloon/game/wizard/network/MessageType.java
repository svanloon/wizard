package com.svanloon.game.wizard.network;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public enum MessageType {
	/** */OK,
	// commands
	/** */BID,
	/** */GET_HAND,
	/** */GET_NAME,
	/** */GIVE_CARD,
	/** */PICK_TRUMP,
	/** */PLAY_CARD,
	/** */PLAY_CARD_IS_NOT_VALID,
	/** */PLAY_CARD_IS_VALID,
	// events
	/** */GAME_OVER,
	/** */HAND_DEALT,
	/** */NEW_GAME,
	/** */NEW_ROUND,
	/** */NEW_TRICK,
	/** */NEW_TRUMP,
	/** */PLAYER_BID,
	/** */PLAYER_PLAYED,
	/** */PLAYER_WON_TRICK,
	/** */SCORE_EVENT,
	/** */PLAYER_NEEDS_TO_PLAY;
}
