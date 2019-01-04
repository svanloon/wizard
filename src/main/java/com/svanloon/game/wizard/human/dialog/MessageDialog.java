package com.svanloon.game.wizard.human.dialog;

import java.awt.Component;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
/**
 * 
 * @author Administrator
 *
 */
public class MessageDialog extends JDialog {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * Constructs a new <code>MessageDialog</code> object. 
	 *
	 */
	public MessageDialog() {
		super();
	}
	
	/**
	 * 
	 * Document the display method 
	 *
	 * @param msg
	 * @param comp
	 */
	public void display(String msg, Component comp) {
		JPanel mainPanel = new JPanel();
		mainPanel.add(new JLabel(msg));
		this.add(mainPanel);
		this.setLocation(200, 200);
		this.pack();
		this.setVisible(true);
	}
}
