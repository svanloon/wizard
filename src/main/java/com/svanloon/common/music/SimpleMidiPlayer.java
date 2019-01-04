package com.svanloon.common.music;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Receiver;
import javax.sound.midi.Transmitter;

/**
 * 
 * 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class SimpleMidiPlayer {
	private static Logger _logger = Logger.getLogger(SimpleMidiPlayer.class.getName());

	private Sequencer sm_sequencer = null;
	private Synthesizer sm_synthesizer = null;
	private List<URL> playList = null;
	/**
	 * 
	 * Constructs a new <code>SimpleMidiPlayer</code> object. 
	 *
	 */
	public SimpleMidiPlayer() {
		super();
	}

	private int currentSoundIndex = 0;

	/**
	 * 
	 * Document the setPlayList method 
	 *
	 * @param playList
	 */
	public void setPlayList(List<URL> playList) {
		this.playList = playList;
		currentSoundIndex = 0;
	}
	
	private void queueNextSong() throws InvalidMidiDataException, IOException, MidiUnavailableException {
		if(playList == null || playList.isEmpty()) {
			return;
		}

		if(playList.size() < currentSoundIndex) {
			return;
		}

		URL url = playList.get(currentSoundIndex);

		Sequence sequence = MidiSystem.getSequence(url);

		sm_sequencer = MidiSystem.getSequencer();

		if (sm_sequencer == null) {
			_logger.info("can't get a Sequencer");
			return;
		}

		sm_sequencer.addMetaEventListener(new MetaEventListener() {
			public void meta(MetaMessage event) {
				if (event.getType() == 47) {
					sm_sequencer.close();
					if (sm_synthesizer != null) {
						sm_synthesizer.close();
					}
					currentSoundIndex++;
					if(currentSoundIndex < playList.size()) {
						try {
							queueNextSong();
							play();
						} catch (InvalidMidiDataException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (MidiUnavailableException e) {
							e.printStackTrace();
						}
					}
					playing = false;
					return;
				}
			}
		});

		sm_sequencer.open();
		sm_sequencer.setSequence(sequence);

		if (!(sm_sequencer instanceof Synthesizer)) {
			sm_synthesizer = MidiSystem.getSynthesizer();
			sm_synthesizer.open();
			Receiver synthReceiver = sm_synthesizer.getReceiver();
			Transmitter seqTransmitter = sm_sequencer.getTransmitter();
			seqTransmitter.setReceiver(synthReceiver);
		}
	}

	/**
	 * 
	 * Document the shuffle method 
	 *
	 */
	public void shuffle() {
		if(playList == null || playList.isEmpty()) {
			return;
		}

		List<URL> shuffled = new ArrayList<URL>();
		int times = this.playList.size();
		for (int i = 0; i < times; i++) {
			int randomCardNumber = (int) (Math.random() * this.playList.size());
			shuffled.add(this.playList.get(randomCardNumber));
			this.playList.remove(randomCardNumber);
		}
		playList = shuffled;
	}

	/**
	 * 
	 * Document the next method 
	 *
	 */
	public void next() {
		stop();
		try {
			currentSoundIndex++;
			queueNextSong();
			play();
		} catch (InvalidMidiDataException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		}
	}

	private boolean playing = false;

	/**
	 * 
	 * Document the play method 
	 *
	 * @throws InvalidMidiDataException
	 * @throws IOException
	 * @throws MidiUnavailableException
	 */
	public void play() throws InvalidMidiDataException, IOException, MidiUnavailableException  {
		queueNextSong();
		if(sm_sequencer != null) {
			sm_sequencer.start();
			playing = true;
		}
	}
	/**
	 * 
	 * Document the stop method 
	 *
	 */
	public void stop() {
		if(sm_sequencer != null) {
			sm_sequencer.stop();
			playing = false;
		}
	}

	/**
	 * @return the playing
	 */
	public boolean isPlaying() {
		return playing;
	}

	/**
	 * @return the playList
	 */
	public List<URL> getPlayList() {
		return playList;
	}
}
