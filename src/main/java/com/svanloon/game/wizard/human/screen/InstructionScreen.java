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
public class InstructionScreen {
	/**
	 * 
	 *
	 */
	public InstructionScreen() {
		super();
	}
	/**
	 * 
	 * Document the show method 
	 *
	 */
	public void show() {
		//Locale locale = LanguageFactory.getInstance().getLocale();
		//String fileName = "instrunctions"+locale.getLanguage()+".html";
		String fileName = "instructions.html";
		URL instructions = this.getClass().getClassLoader().getResource(fileName); 
		new Browser(LanguageFactory.getInstance().getString(MessageId.HELP), instructions);
	}
}
