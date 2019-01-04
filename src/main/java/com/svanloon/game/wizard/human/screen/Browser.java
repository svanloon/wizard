package com.svanloon.game.wizard.human.screen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

import com.svanloon.common.util.IconUtil;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class Browser extends JFrame implements HyperlinkListener,
		ActionListener, WindowListener {
	private static final long serialVersionUID = 1L;

	private JIconButton homeButton;

	private JTextField urlField;

	private JEditorPane htmlPane;

	private String initialURL;
	/**
	 * 
	 * Constructs a new <code>Browser</code> object. 
	 *
	 * @param title
	 * @param initialURL
	 */
	public Browser(String title, URL initialURL) {
		super(title);
		this.initialURL = initialURL.getPath();
		addWindowListener(this);
		//WindowUtilities.setNativeLookAndFeel();

		this.setIconImage(IconUtil.getIcon());
		JPanel topPanel = new JPanel();
		topPanel.setBackground(Color.lightGray);
		homeButton = new JIconButton("home.gif");
		homeButton.addActionListener(this);
		JLabel urlLabel = new JLabel("URL:");
		urlField = new JTextField(30);
		urlField.setText(this.initialURL);
		urlField.addActionListener(this);
		topPanel.add(homeButton);
		topPanel.add(urlLabel);
		topPanel.add(urlField);
		getContentPane().add(topPanel, BorderLayout.NORTH);

		try {
			htmlPane = new JEditorPane(initialURL);
			htmlPane.setEditable(false);
			htmlPane.addHyperlinkListener(this);
			JScrollPane scrollPane = new JScrollPane(htmlPane);
			getContentPane().add(scrollPane, BorderLayout.CENTER);
		} catch (IOException ioe) {
			warnUser("Can't build HTML pane for " + initialURL + ": " + ioe);
		}

		Dimension screenSize = getToolkit().getScreenSize();
		int width = screenSize.width * 8 / 10;
		int height = screenSize.height * 8 / 10;
		setBounds(width / 8, height / 8, width, height);
		setVisible(true);
	}

	public void actionPerformed(ActionEvent event) {
		String url;
		if (event.getSource() == urlField) {
			url = urlField.getText();
		} else {
			// Clicked "home" button instead of entering URL
			url = initialURL;
		}
		try {
			htmlPane.setPage(new URL(url));
			urlField.setText(url);
		} catch (IOException ioe) {
			warnUser("Can't follow link to " + url + ": " + ioe);
		}
	}

	public void hyperlinkUpdate(HyperlinkEvent event) {
		System.out.println(event);
		if (event.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
			try {
				htmlPane.setPage(event.getURL());
				urlField.setText(event.getURL().toExternalForm());
			} catch (IOException ioe) {
				warnUser("Can't follow link to "
						+ event.getURL().toExternalForm() + ": " + ioe);
			}
		}
	}

	private void warnUser(String message) {
		JOptionPane.showMessageDialog(this, message, "Error",
				JOptionPane.ERROR_MESSAGE);
	}

	public void windowActivated(WindowEvent e) {
		//
	}

	public void windowClosed(WindowEvent e) {
		//
	}

	public void windowClosing(WindowEvent e) {
		this.setVisible(false);
		this.dispose();
	}

	public void windowDeactivated(WindowEvent e) {
		//
	}

	public void windowDeiconified(WindowEvent e) {
		//
	}

	public void windowIconified(WindowEvent e) {
		//
	}

	public void windowOpened(WindowEvent e) {
		//
	}

}

class JIconButton extends JButton {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * Constructs a new <code>JIconButton</code> object. 
	 *
	 * @param file
	 */
	public JIconButton(String file) {
		super(new ImageIcon(file));
		setContentAreaFilled(false);
		setBorderPainted(false);
		setFocusPainted(false);
	}
}
