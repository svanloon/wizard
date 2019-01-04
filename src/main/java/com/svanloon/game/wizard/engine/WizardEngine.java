package com.svanloon.game.wizard.engine;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.svanloon.game.wizard.client.event.GameEventBroadcaster;
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
import com.svanloon.game.wizard.client.player.Bid;
import com.svanloon.game.wizard.client.player.IndividualBid;
import com.svanloon.game.wizard.client.player.IndividualScore;
import com.svanloon.game.wizard.client.player.Player;
import com.svanloon.game.wizard.client.player.RoundSummary;
import com.svanloon.game.wizard.client.player.Score;
import com.svanloon.game.wizard.client.player.TrickTracker;
import com.svanloon.game.wizard.client.player.ValidityChecker;
import com.svanloon.game.wizard.core.card.Card;
import com.svanloon.game.wizard.core.card.CardFinder;
import com.svanloon.game.wizard.core.card.Deck;
import com.svanloon.game.wizard.core.card.Value;
import com.svanloon.game.wizard.core.card.WizardCardFinder;
import com.svanloon.game.wizard.core.gameOptions.BidType;
import com.svanloon.game.wizard.core.gameOptions.GameOptions;
import com.svanloon.game.wizard.core.gameOptions.GameSpeed;
import com.svanloon.game.wizard.stats.Game;
import com.svanloon.game.wizard.stats.GameArchive;
import com.svanloon.game.wizard.stats.Play;
import com.svanloon.game.wizard.stats.Round;
import com.svanloon.game.wizard.stats.Trick;

public class WizardEngine implements Runnable{
	private GameOptions go;
	private Game _game;
	private CardFinder cardFinder;
	/**
	 *
	 * Constructs a new <code>WizardEngine</code> object.
	 *	 */
	public WizardEngine(GameOptions go) {
		super();
		this.go = go;
		cardFinder = new WizardCardFinder();
		//cardFinder = new PokerCardFinder();
	}

	private List<Player> _playerCollection = new ArrayList<>();
	private Score overallScores = new Score();
	private GameEventBroadcaster gameEventNotifier = new GameEventBroadcaster();

	private Card dealCards(int round, int dealer, int lead) {
		//Deck deck = new Deck(new PokerCardFinder());
		Deck deck = new Deck(cardFinder);
		deck.shuffle();
		for (int i = 0; i < round; i++) {
			for (Player player: new PlayerIterator(_playerCollection, lead)) {
				player.giveCard(deck.getCard());
			}
		}
		Player dealerPlayer = this._playerCollection.get(dealer);
		gameEventNotifier.notify(new HandDealtEvent(dealerPlayer.getId()));

		if (deck.hasCards()) {
			return deck.getCard();
		}

		return null;
	}

	private boolean isPlayerWinning(int currPlayerId) {
		Collection<Integer> ids = calcWinningIds();
		if(ids.isEmpty()) {
			return true;
		}
		for(Integer id:ids) {
			if(id == currPlayerId) {
				return true;
			}
		}
		return false;
	}

	private Bid bid(Card trump, int lead, int cardsDealt, Round round) {
		Bid bid = new Bid();
		int minBid = 0;
		int playerCount = 0;
		for (Player player: new PlayerIterator(_playerCollection, lead)) {
			int notAllowedToBid = -1;

			if(playerCount == this._playerCollection.size() - 1) {
				boolean isPlayerWinning = isPlayerWinning(player.getId());
				boolean isEven = isPlayerWinning && go.getBidType().equals(BidType.EVEN);
				boolean isCanadian = isPlayerWinning &&  go.getBidType().equals(BidType.CANADIAN);
				boolean isHardCore = cardsDealt > 3 && go.getBidType().equals(BidType.HARDCORE);

				if(isEven || isCanadian || isHardCore) {
					int bidSoFar = bid.bidSoFar();
					if(cardsDealt - bidSoFar >= 0) {
						notAllowedToBid = cardsDealt - bidSoFar;
						// in canadian rules, zero is always allowed.
						if(isCanadian && notAllowedToBid == 0 ) {
							notAllowedToBid = -1;
						}
					}
					//else they can bid anything

				}
			}

			int bidInt = player.bid(trump, minBid, cardsDealt, notAllowedToBid);
			if(bidInt == notAllowedToBid) {
				System.err.print("illegal bid");
				throw new RuntimeException("illegal bid");
			}
			bid.addIndividualBid(new IndividualBid(player, bidInt));
			if(go.getBidType().equals(BidType.STANDARD) ||
				go.getBidType().equals(BidType.EVEN) ||
				go.getBidType().equals(BidType.CANADIAN) ||
				go.getBidType().equals(BidType.HARDCORE)
			) {
				gameEventNotifier.notify(new PlayerBidEvent(player.getId(), bidInt));
				round.setBid(player.getId(), bidInt);
			}
			playerCount++;
		}
		return bid;

	}

	private void scoreRound(RoundSummary roundSummary, Bid bid, Game game) {
		for (Player player: this._playerCollection) {
			int tricksWon = roundSummary.tricksWonBy(player);

			int bidInt = bid.findIndividualBidBy(player).getBid();
			int score;
			if (tricksWon == bidInt) {
				score = 20 + bidInt * 10;
			} else {
				score = -1 * Math.abs((bidInt - tricksWon) * 10);
			}

			gameEventNotifier.notify(new ScoreEvent(player.getId(), score));
			game.addScore(player.getId(), score);
			IndividualScore individualScore = new IndividualScore(player, score);
			this.overallScores.addIndividualScore(individualScore);
		}
	}

	private TrickTracker playTrick(Card trump, int lead, Round round) {
		gameEventNotifier.notify(new NewTrickEvent());
		round.newTrick();
		Trick trick = round.getCurrentTrick();
		TrickTracker trickTracker = new TrickTracker(trump);
		for (Player player: new PlayerIterator(_playerCollection, lead)) {
			Card card = null;
			for (boolean isValid = false; !isValid;) {
				// need to make sure the card is valid. otherwise repeat
				gameEventNotifier.notify(new PlayerNeedsToPlay(player.getId()));
				card = player.playCard();
				if (ValidityChecker.checkValidity(trickTracker, trump, card, player)) {
					isValid = true;
					gameEventNotifier.notify(new PlayerPlayedEvent(player.getId(), card));
					player.playCardIsValid(card);
				} else {
					player.playCardIsNotValid(card);
				}
			}
			trickTracker.addCardPlayed(player.getId(), card);
			trick.add(new Play(player.getId(), card));
			if (card != null && card.isWizard()) {
				trick.setWizardPlayed(true);
			}
		}
		int winnerId = trickTracker.winningPlay().getPlayerId();
		Card winningCard = trickTracker.winningPlay().getCard();
		trick.setWinner(winnerId);
		gameEventNotifier.notify(new PlayerWonTrickEvent(winnerId, winningCard));

		return trickTracker;
	}

	public void addPlayers(List<Player> players) {
		this._playerCollection = players;

	}

	public void addGameEventListener(GameEventListener listener) {
		gameEventNotifier.addListener(listener);
	}

	private List<String> getPlayerNames() {
		List<String> playerNames = new ArrayList<>();
		for(Player player: this._playerCollection) {
			String name = player.getName();
			playerNames.add(name);
		}
		return playerNames;
	}

	private List<Integer> getPlayerId() {
		List<Integer> playerIds = new ArrayList<>();
		for(Player player: this._playerCollection) {
			if(player.getId() < 1) {
				throw new RuntimeException("player "+player.getName()+" not initialized properly");
			}
			playerIds.add(player.getId());
		}
		return playerIds;
	}

	/**
	 *
	 * Document the getGame method
	 *
	 * @return Game
	 */
	public Game getGame() {
		return _game;
	}
	/**
	 *
	 * Document the run method
	 *
	 */
	public void run() {
		GameArchive ga = GameArchive.getInstance();
		ga.newGame();
		_game = ga.getCurrentGame();
		int startRound = 1;
		int endRound = (int)(1.0*cardFinder.getCardsInDeck() /_playerCollection.size());
		int dealer = -1;
		int lead = 0;
		int inc = 1;
		if(go.getGameSpeed().equals(GameSpeed.QUICK_PLAY)) {
			inc = 2;
			if(_playerCollection.size() != 4) {
				startRound = 2;
			}
		}


		List<Integer> playerIds = getPlayerId();
		List<String> playerNames = getPlayerNames();
		for (int i = 0; i < playerIds.size(); i++) {
			_game.addPlayer(playerIds.get(i),  playerNames.get(i));
		}
		gameEventNotifier.notify(new NewGameEvent(playerIds,playerNames));
		for (int roundId = startRound; roundId < endRound + 1; roundId = roundId + inc) {
			dealer = (dealer + 1)% _playerCollection.size();
			lead = (dealer + 1)% _playerCollection.size();
			gameEventNotifier.notify(new NewRoundEvent(roundId));
			_game.newRound(roundId);
			Round round = _game.getCurrentRound();
			Card trump = dealCards(roundId, dealer, lead);
			gameEventNotifier.notify(new NewTrumpEvent(trump));
			round.setTrump(trump);

			if(trump != null && trump.getValue().equals(Value.WIZARD)) {
				Player trumpPicker = _playerCollection.get(dealer);
				//_logger.info(trumpPicker.getName() + " gets to pick trump.");
				trump = new Card(null, trumpPicker.pickTrump(), -1);
				gameEventNotifier.notify(new NewTrumpEvent(trump));
				round.setTrump(trump);
			}
			int cardsDealt = roundId;
			Bid bid = bid(trump, lead, cardsDealt, round);
			if(go.getBidType().equals(BidType.HIDDEN)) {
				for(IndividualBid individualBid :bid.getBids()) {
					gameEventNotifier.notify(new PlayerBidEvent(individualBid.getPlayer().getId(), individualBid.getBid()));
					round.setBid(individualBid.getPlayer().getId(), individualBid.getBid());
				}
			}
			RoundSummary roundSummary = new RoundSummary();
			for (int i = 0; i < roundId; i++) {
				TrickTracker trickTracker = playTrick(trump, lead, round);
				roundSummary.addTrickTracker(trickTracker);
				int playerIdWhoWon = trickTracker.winningPlay().getPlayerId();
				lead = findPlayerIndex(playerIdWhoWon);
			}
			if(go.getBidType().equals(BidType.SECRET)) {
				for(IndividualBid individualBid :bid.getBids()) {
					gameEventNotifier.notify(new PlayerBidEvent(individualBid.getPlayer().getId(), individualBid.getBid()));
					round.setBid(individualBid.getPlayer().getId(), individualBid.getBid());
				}
			}
			scoreRound(roundSummary, bid, _game);
			this.overallScores.displayScore();
		}
		Collection<Integer> winningPlayerIds = calcWinningIds();
		Collection<String> winningPlayers = getPlayerNames(winningPlayerIds);
		for(int winningIds:winningPlayerIds) {
			_game.addWinner(winningIds);
		}
		gameEventNotifier.notify(new GameOverEvent(winningPlayerIds, winningPlayers));
	}

	private Collection<String> getPlayerNames(Collection<Integer> ids) {
		Collection<String> winningPlayers = new ArrayList<>();
		for(Integer id:ids) {
			winningPlayers.add(findPlayerById(id).getName());
		}
		return winningPlayers;
	}

	private Player findPlayerById(int id) {
		for(Player player: this._playerCollection) {
			if(player.getId() == id) {
				return player;
			}
		}
		return null;
	}

	private Collection<Integer>  calcWinningIds() {
		Collection<Integer> winningPlayerIds = new ArrayList<>();
		int highScore = Integer.MIN_VALUE;
		for(IndividualScore individualScore : this.overallScores.getIndividualScoreCollection()) {
			if(individualScore.getScore() > highScore) {
				highScore = individualScore.getScore();

				winningPlayerIds = new ArrayList<>();
				winningPlayerIds.add(individualScore.getPlayer().getId());
			} else if (individualScore.getScore() == highScore) {
				winningPlayerIds.add(individualScore.getPlayer().getId());
			}
		}
		return winningPlayerIds;
	}

	private int findPlayerIndex(int pPlayerId) {
		int i = 0;
		for(Player player: this._playerCollection) {
			if(player.getId() == pPlayerId) {
				return i;
			}
			i++;
		}
		return -1;
	}


	public static void main(String[] args) {
		WizardEngine wizardEngine = new WizardEngine(new GameOptions());
		Thread t = new Thread(wizardEngine);
		t.start();
	}

	private class PlayerIterator implements Iterator<Player>, Iterable<Player> {

		private int lead;
		private List<Player> players;
		private int i = 0;

		public PlayerIterator(List<Player> players, int lead) {
			this.players = players;
			this.lead = lead;
		}

		public boolean hasNext() {
			return (i < this.players.size());
		}

		public Player next() {
			Player result = this.players.get((lead + i) % this.players.size());
			i++;
			return result;
		}

		public void remove() {
			throw new RuntimeException("remove should not be called on PlayerIterator");
		}

		public Iterator<Player> iterator() {
			return this;
		}
	}
}