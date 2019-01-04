package com.svanloon.common.util;

import java.net.MalformedURLException;
import java.net.URL;

public class UrlUtil {

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
