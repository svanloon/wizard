package com.svanloon.game.wizard.language;

import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class LanguageFactory {
	private static Logger _logger = Logger.getLogger(LanguageFactory.class);

	private static LanguageFactory instance = null;
	private Locale locale = null;

	private HashMap<MessageId, String> strings = new HashMap<MessageId, String>();

	/**
	 * @return the locale
	 */
	public Locale getLocale() {
		if(locale == null) {
			locale = Locale.ENGLISH;
			//locale = new Locale("sp");
		}
		return locale;
	}

	/**
	 *
	 * Document the setLocale method
	 *
	 * @param locale
	 */
	public static void setLocale(Locale locale) {
		if(Locale.ENGLISH.equals(locale)) {
			instance = new LanguageFactory(locale);
		} else {
			instance = new LanguageFactory(locale);
		}
	}
	private LanguageFactory() {
		this.locale = getLocale();
		loadProperties();
	}

	private LanguageFactory(Locale locale) {
		this.locale = locale;
		loadProperties();
	}

	private void loadProperties() {
		strings = new HashMap<MessageId, String>();
		ResourceBundle rb = ResourceBundle.getBundle("application", locale);
		for(MessageId key : MessageId.values()) {
			String value = rb.getString(key.toString());
			if(value == null || value.trim().equals("")) {
				_logger.warn("missing for " +key.toString()+ " is undefined.");
			}
			strings.put(key, value);
		}
	}

	/**
	 *
	 * Document the getInstance method
	 *
	 * @return LanguageFactory
	 */
	public static LanguageFactory getInstance() {
		if(instance == null) {
			instance = new LanguageFactory();
		}

		return instance;
	}

	/**
	 *
	 * Document the getString method
	 *
	 * @param key
	 * @param name
	 * @param value
	 * @return String
	 */
	public String getString(MessageId key, String name, String value) {
		String noParamsAdded = getString(key);
		String withParams;
		if(noParamsAdded != null) {
			String param = "%" + name + "%";
			int index = noParamsAdded.indexOf(param);
			if( index > -1) {
				withParams = noParamsAdded.substring(0, index) + value + noParamsAdded.substring(index + param.length());
			} else {
				withParams = noParamsAdded;
			}
		} else {
			withParams = noParamsAdded;
		}
		return withParams;
	}

	/**
	 *
	 * Document the getString method
	 *
	 * @param key
	 * @return String
	 */
	public String getString(MessageId key) {
		if(strings.containsKey(key)) {
			return strings.get(key);
		}

		return "UNDEFINED";
	}
}
