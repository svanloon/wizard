package com.svanloon.game.wizard.human;

import java.awt.Component;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public interface PlayerLayout {
	/**
	 * 
	 * Document the addComponent method 
	 *
	 * @param wce
	 * @param comp
	 */
	public void addComponent(WizardComponentEnum wce, Component comp);
	/**
	 * 
	 * Document the getComponent method 
	 *
	 * @return Component
	 */
	public Component getComponent();
}
