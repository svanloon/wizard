package com.svanloon.common.util;

import java.awt.Image;
import java.net.URL;
import java.util.HashMap;

import javax.swing.ImageIcon;

import org.apache.log4j.Logger;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class ImageUtil {
	private static Logger _logger = Logger.getLogger(ImageUtil.class);
	private static HashMap<String, Image> imageMap = new HashMap<String, Image>();

	/**
	 * 
	 * Document the getImage method 
	 *
	 * @param fileName
	 * @return Image
	 */
	public static Image getImage(String fileName) {
		Image image; 
		if(imageMap.containsKey(fileName)) { 
			image = imageMap.get(fileName); 
		} else if(imageMap.containsKey(fileName.replace(".jpg", ".gif"))) {
			image = imageMap.get(fileName.replace(".jpg", ".gif"));
		} else {
			_logger.info(fileName);
			URL imageUrl = new UrlUtil().getUrl(fileName);
			try {
				ImageIcon imageIcon = new ImageIcon(imageUrl);
				image = imageIcon.getImage(); 
				imageMap.put(fileName, image); 
			}catch (NullPointerException e) {
				imageUrl = new UrlUtil().getUrl(fileName.replace(".jpg", ".gif"));
				ImageIcon imageIcon = new ImageIcon(imageUrl);
				image = imageIcon.getImage(); 
				imageMap.put(fileName, image);
			}
		} 
		return image;		
	}

}
