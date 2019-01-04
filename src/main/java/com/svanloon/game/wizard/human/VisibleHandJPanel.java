package com.svanloon.game.wizard.human;

/**
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class VisibleHandJPanel extends HandJPanel {
	private static final long serialVersionUID = 1L;
	/**
	 * Constructs a new <code>VisibleHandJPanel</code> object. 
	 */
	public VisibleHandJPanel(String deckDir) {
		super(true, false, false, deckDir);
	}
}