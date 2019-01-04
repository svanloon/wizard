package com.svanloon.game.wizard.network;


/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class MessageParam {
	/**
	 * 
	 * Constructs a new <code>MessageParam</code> object. 
	 *
	 * @param name
	 * @param value
	 */
	public MessageParam(String name, String value) {
		this.name = name;
		this.value = value;
	}

	private String name;
	private String value;
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
