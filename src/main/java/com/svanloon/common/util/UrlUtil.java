package com.svanloon.common.util;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class UrlUtil {
	/**
	 * 
	 * Document the getUrl method 
	 *
	 * @param fileName
	 * @return URL
	 */
	public URL getUrl(String fileName) {
		URL url = this.getClass().getClassLoader().getResource(fileName);
		if(url == null) {
			try {
				url = new URL("file:///" + fileName);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		return url;
	}
}
