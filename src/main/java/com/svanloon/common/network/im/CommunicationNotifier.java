package com.svanloon.common.network.im;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class CommunicationNotifier {
	private List<CommunicationHandler> handlers = new ArrayList<CommunicationHandler>();

	/**
	 * 
	 * Document the addHandler method 
	 *
	 * @param handler
	 */
	public void addHandler(CommunicationHandler handler) {
		handlers.add(handler);
	}

	/**
	 * 
	 * Document the notify method 
	 *
	 * @param message
	 */
	public void notify(String message) {
		for(CommunicationHandler handler:handlers) {
			handler.send(message);
		}
	}

}
