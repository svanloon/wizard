package com.svanloon.game.wizard.human.screen;

import java.net.URL;
import java.util.Locale;

import com.svanloon.game.wizard.language.LanguageFactory;
import com.svanloon.game.wizard.language.MessageId;

/**
 * @author Administrator
 */
public class RulesScreen {
	/**
	 * 
	 *
	 */
	public RulesScreen() {
		super();
	}

	/**
	 * 
	 * Document the show method 
	 *
	 */
	public void show() {
		Locale locale = LanguageFactory.getInstance().getLocale();
		String fileName = "rules_"+locale.getLanguage()+".html";
		URL instructions = this.getClass().getClassLoader().getResource(fileName); 
		new Browser(LanguageFactory.getInstance().getString(MessageId.HELP), instructions);
	}
}
