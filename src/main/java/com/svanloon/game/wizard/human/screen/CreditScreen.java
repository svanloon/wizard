package com.svanloon.game.wizard.human.screen;

import java.net.URL;

import com.svanloon.game.wizard.language.LanguageFactory;
import com.svanloon.game.wizard.language.MessageId;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class CreditScreen {
	/**

	 * Constructs a new <code>CreditScreen</code> object. 
	 */
	public CreditScreen() {
		super();
	}
	/**
	 * Document the show method 
	 */
	public void show(){
		String fileName = "credits.html";
		URL credits = this.getClass().getClassLoader().getResource(fileName); 
		new Browser(LanguageFactory.getInstance().getString(MessageId.CREDITS), credits);	
	}
}
