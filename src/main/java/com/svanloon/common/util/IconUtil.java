package com.svanloon.common.util;

import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;


/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class IconUtil {
	/**
	 * 
	 * Document the getIcon method 
	 *
	 * @return Image
	 */
	public static Image getIcon() {
		String fileName = "images/icon.gif";
		URL imageUrl = new UrlUtil().getUrl(fileName); 
		Image image = new ImageIcon(imageUrl).getImage();
		return image;
	}
}
