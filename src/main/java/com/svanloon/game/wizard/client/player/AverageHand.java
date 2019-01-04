package com.svanloon.game.wizard.client.player;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class AverageHand extends AbstractOpponent {
	/**
	 * 
	 * Constructs a new <code>AverageHand</code> object. 
	 *
	 */
	public AverageHand() {
		super();
	}

	@Override
	public void setRound(int round) {
		super.setRound(round);
		double suitProbablility = 13.0/60.0;
		setHearts(suitProbablility * round);
		setSpades(suitProbablility * round);
		setClubs(suitProbablility * round);
		setDiamonds(suitProbablility * round);

		double specialCardProbablility = 4.0/60.0;		
		setWizards(specialCardProbablility * round);
		setJesters(specialCardProbablility * round);		
	}
}
