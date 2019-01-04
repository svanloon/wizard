package com.svanloon.game.wizard.client.player;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class Score {

	private Collection<IndividualScore> individualScoreCollection = new ArrayList<IndividualScore>();

	/**
	 * 
	 * Document the displayScore method 
	 *
	 */
	public void displayScore() {
		//for (IndividualScore individualScore: this.individualScoreCollection) {
		//	_logger.info(individualScore.getPlayer().getName()
		//			+ " = " + individualScore.getScore() + " points");
		//}
	}

	/**
	 * 
	 * Document the addIndividualScore method 
	 *
	 * @param individualScore
	 */
	public void addIndividualScore(IndividualScore individualScore) {
		Player currentPlayer = individualScore.getPlayer();
		IndividualScore previousIndividualScore = null;
		for (IndividualScore tempIndividualScore:this.individualScoreCollection) {
			if (tempIndividualScore.getPlayer().equals(currentPlayer)) {
				previousIndividualScore = tempIndividualScore;
			}
		}
		if (previousIndividualScore == null) {
			this.individualScoreCollection.add(individualScore);
		} else {
			this.individualScoreCollection.remove(previousIndividualScore);
			IndividualScore combinedIndividualScore = new IndividualScore(
					currentPlayer, previousIndividualScore.getScore()
							+ individualScore.getScore());
			this.individualScoreCollection.add(combinedIndividualScore);
		}
	}

	/**
	 * @return the individualScoreCollection
	 */
	public Collection<IndividualScore> getIndividualScoreCollection() {
		return individualScoreCollection;
	}

}

