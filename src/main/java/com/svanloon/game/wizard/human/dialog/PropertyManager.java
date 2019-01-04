package com.svanloon.game.wizard.human.dialog;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
import java.util.Properties;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 */
public class PropertyManager {
	private Properties prop = null;
	private static final String APPLICATION_DIR = "wizard";
	private String propertyFileName;
	private PrintWriter os;
	/**
	 * 
	 * Constructs a new <code>PropertyManager</code> object. 
	 *
	 * @param propertyFileName
	 */
	public PropertyManager(String propertyFileName) {
		this.propertyFileName = propertyFileName;
	}

	private File getFile(boolean create) throws IOException {
		String userDir = System.getProperty("user.home");
		String dirName = userDir + File.separator + APPLICATION_DIR + File.separator;
		String fileName = dirName + propertyFileName;
		File file = new File(fileName);
		if(file.exists() == false) {
			if(create) {
				File dir = new File(dirName);
				if(dir.exists() == false && dir.mkdir() == false) {
					throw new FileNotFoundException("Couldn't create the dir " + dirName);
				}
				if(file.createNewFile() == false) {
					throw new FileNotFoundException("Couldn't create the file " + fileName);
				}
			} else {
				throw new FileNotFoundException("Couldn't file " + fileName);
			}
		}
		
		return file;
	}

	/**
	 * 
	 * Document the loadPropertyFile method 
	 *
	 * @param create
	 * @throws IOException
	 */
	public void loadProperties() throws IOException {
		File file = getFile(false);
		prop = new Properties();
		prop.load(new BufferedInputStream(new FileInputStream(file)));
	}

	/**
	 * 
	 * Document the getProperty method 
	 *
	 * @param key
	 * @return String
	 */
	public String getProperty(String key) {
		if(prop == null) {
			throw new IllegalStateException("the property file need to be loaded first");
		}
		return prop.getProperty(key);
	}

	/**
	 * 
	 * Document the getProperty method 
	 *
	 * @param key
	 * @param defaultValue 
	 * @return String
	 */
	public String getProperty(String key, String defaultValue) {
		String value = getProperty(key);
		if(value == null) {
			return defaultValue;
		}
		return value;
	}

	/**
	 * 
	 * Document the getPropertyAsInt method 
	 *
	 * @param key
	 * @param defaultValue 
	 * @return int
	 */
	public int getPropertyAsInt(String key, int defaultValue) {
		String value = getProperty(key);
		if(value == null) {
			return defaultValue;
		}
		return Integer.parseInt(value);
	}

	/**
	 * 
	 * Document the getPropertyAsInt method 
	 *
	 * @param key
	 * @param defaultValue 
	 * @return boolean
	 */
	public boolean getPropertyAsBoolean(String key, boolean defaultValue) {
		String value = getProperty(key);
		if(value == null) {
			return defaultValue;
		}
		return Boolean.parseBoolean(value);
	}
	/**
	 * 
	 * Document the getPropertyAsInt method 
	 *
	 * @param key
	 * @param defaultValue 
	 * @return Locale
	 */
	public Locale getPropertyAsLocale(String key, Locale defaultValue) {
		String value = getProperty(key);
		if(value == null) {
			return defaultValue;
		}
		return new Locale(value);
	}
	/**
	 * 
	 * Document the setProperty method 
	 *
	 * @param key
	 * @param value
	 */
	public void setProperty(String key, String value) {
		if(value == null) {
			return;
		}
		os.println(key + "="+value);
	}
	/**
	 * 
	 * Document the setProperty method 
	 *
	 * @param key
	 * @param value
	 */
	public void setProperty(String key, int value) {
		setProperty(key, String.valueOf(value));
	}
	/**
	 * 
	 * Document the setProperty method 
	 *
	 * @param key
	 * @param value
	 */
	public void setProperty(String key, boolean value) {
		setProperty(key, String.valueOf(value));
	}
	/**
	 * 
	 * Document the setProperty method 
	 *
	 * @param key
	 * @param locale
	 */
	public void setProperty(String key, Locale locale) {
		setProperty(key, String.valueOf(locale));
	}


	/**
	 * 
	 * Document the openPropertyFile method 
	 *
	 * @throws IOException
	 */
	public void openPropertyFileForWritting() throws IOException {
		File file = getFile(true);
		os = new PrintWriter(new BufferedOutputStream(new FileOutputStream(file)));
	}

	/**
	 * 
	 * Document the closeFile method 
	 *
	 */
	public void closePropertyFile() {
		if(os == null) {
			return;
		}
		os.flush();
		os.close();
	}
}
