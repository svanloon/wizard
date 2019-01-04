package com.svanloon.common.music;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.svanloon.common.util.UrlUtil;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class WavePlayer
{  
	/**
	 * Constructs a new <code>WavePlayer</code> object. 
	 */
	public WavePlayer() {
		super();
	}

	/**
	 * 
	 * Document the play method 
	 * @param fileName 
	 * @throws IOException 
	 * @throws UnsupportedAudioFileException 
	 * @throws LineUnavailableException 
	 *
	 */
	public void play(String fileName) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		URL url = new UrlUtil().getUrl(fileName);
		AudioInputStream stream = AudioSystem.getAudioInputStream(url);      
		AudioFormat format = stream.getFormat();
		DataLine.Info info = new DataLine.Info(Clip.class, format);      
		Clip clip = (Clip) AudioSystem.getLine(info);         
		clip.open(stream);      
		clip.start();
	}
}

