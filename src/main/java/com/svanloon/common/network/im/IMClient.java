package com.svanloon.common.network.im;

import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.apache.log4j.Logger;

import com.svanloon.common.util.ImageUtil;

public class IMClient extends JFrame implements ActionListener, CommunicationListener, KeyListener {
	private static Logger _logger = Logger.getLogger(IMClient.class);
	private static final long serialVersionUID = -162683526416751751L;

	private JList conversationList = new JList();

	private JTextArea sendTextArea = new JTextArea(10, 5);
	private JScrollPane coversationScrollPane;

	private static final String SEND = "Send";
	private CommunicationHandler ch;
	private String name;
	private String[] images;
	private String[] sounds;

	/**
	 * Creates new form Messanger
	 */
	public IMClient(String name, String ip, int port) {
		this.name = name;
		init();
		this.pack();
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ch = new CommunicationHandler(ip, port);
		ch.addListener(this);
		this.setTitle(name + "'s IM");
		Thread t = new Thread(ch);
		t.start();
		preloadImages();
		preloadSounds();
	}

	private void preloadImages() {
		String userDir = System.getProperty("user.home");
		File dir = new File(userDir + "/wizard/expansion/im/bams");
		if(!dir.exists()) {
			images = null;
			return;
		}

		File[] fileList = dir.listFiles();
		List<String> fileNames = new ArrayList<String>();
		for(File file:fileList) {
			if(file.isDirectory()) {
				continue;
			}
			String fileName = file.getAbsolutePath().toUpperCase();
			if(fileName.indexOf(".JPG") > 0 || fileName.indexOf(".GIF") > 0) {
				fileNames.add(file.getAbsolutePath());
			}
		}

		if(fileNames.isEmpty()) {
			return;
		}

		images = fileNames.toArray(new String[fileNames.size()]);

		Arrays.stream(images).forEach(ImageUtil::getImage);
	}

	private void preloadSounds() {
		String userDir = System.getProperty("user.home");
		File dir = new File(userDir + "/wizard/expansion/im/sound");
		if(dir.exists() == false) {
			sounds = null;
			return;
		}

		File[] fileList = dir.listFiles();
		List<String> fileNames = new ArrayList<String>();
		for(File file:fileList) {
			if(file.isDirectory()) {
				continue;
			}
			String fileName = file.getAbsolutePath().toUpperCase();
			if(fileName.indexOf(".WAV") > 0) {
				fileNames.add(file.getAbsolutePath());
			}
		}

		if(fileNames.isEmpty()) {
			return;
		}

		sounds = fileNames.toArray(new String[fileNames.size()]);

		/*
		for(int i = 0; i < sounds.length; i++) {
			SoundUtil.getSound(sounds[i]);
		}
		*/
	}

	/**
	 *
	 * Document the initOtherStuff method
	 *
	 */
	private void init() {

		conversationList.setModel(new DefaultListModel());
		conversationList.setVisibleRowCount(5);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());

		coversationScrollPane = new JScrollPane(conversationList);
		mainPanel.add(coversationScrollPane, BorderLayout.CENTER);

		sendTextArea.addKeyListener(this);

		JPanel southPanel = new JPanel(new BorderLayout());
		JButton sendButton = new JButton(SEND);
		sendButton.addActionListener(this);
		southPanel.add(sendTextArea, BorderLayout.CENTER);
		southPanel.add(sendButton, BorderLayout.SOUTH);
		southPanel.setBorder(BorderFactory.createLoweredBevelBorder());

		mainPanel.add(southPanel, BorderLayout.SOUTH);
		this.add(mainPanel);

	}

	public void actionPerformed(java.awt.event.ActionEvent p1) {
		if (p1.getActionCommand().equalsIgnoreCase(SEND)) {
			handleSend();
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[]) {
		String ip = "127.0.0.1";
		int port = 1356;
		String name = "steve";
		IMClient messeger = new IMClient(name, ip, port);
		messeger.setVisible(true);
		messeger.pack();

		IMClient messeger2 = new IMClient(name+1, ip, port+1);
		messeger2.setVisible(true);
		messeger2.pack();
	}

	private void handleSend() {
		String line = sendTextArea.getText();
		if(line == null || line.trim().equals("")) {
			return;
		}
		sendTextArea.setText("");
		ch.send(name + ": " + line + "\n");

	}

	public void keyPressed(KeyEvent arg0) {
		// do nothing
	}

	public void keyReleased(KeyEvent arg0) {
		// do nothing
	}

	public void keyTyped(KeyEvent e) {
		if(e.getKeyChar() == '\n') {
			handleSend();
		}
	}

	public void receiveMessage(String message) {
		_logger.info("updating conversationText with " + message);
		// use the GUI thread to update the List
		javax.swing.SwingUtilities.invokeLater(new ConversationListUpdater(message));

		if(message.toUpperCase().indexOf("BUZZ") > 0 && message.toUpperCase().lastIndexOf(name.toUpperCase()) > 0) {
			buzz();
		}

		if(message.toUpperCase().indexOf("BAM") > 0 /*&& message.toUpperCase().lastIndexOf(name.toUpperCase()) > 0*/) {
			bam();
		}
	}

	private class ConversationListUpdater implements Runnable{
		private String message;
		/**
		 *
		 * Constructs a new <code>ConversationListUpdater</code> object.
		 *
		 * @param message
		 */
		private ConversationListUpdater(String message){
			this.message = message;
		}
		public void run() {
        	DefaultListModel model = (DefaultListModel)conversationList.getModel();
    		model.addElement(message);
    		conversationList.ensureIndexIsVisible(model.getSize()-1);
        }
	}

	private void buzz() {
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			public void run() {
				for(int i = 0; i < 25; i++) {
					Point point = getLocation();
					if(i%2 == 0) {
						setLocation((int)(point.getX() + 5), (int)(point.getY() + 5));
					} else {
						setLocation((int)(point.getX() - 5), (int)(point.getY() - 5));
					}
					repaint();
					try {
						Thread.sleep(25);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}

	private void bam() {
		return;
	}

}