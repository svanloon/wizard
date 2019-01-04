package com.svanloon.game.wizard.human.dialog;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import com.svanloon.game.wizard.language.LanguageFactory;
import com.svanloon.game.wizard.language.MessageId;

/**
 * 
 * Document the  class 
 *
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class PromptDialog extends JDialog implements ActionListener, KeyListener {
	private static final long serialVersionUID = 1L;
	private boolean allowNull = false;
	private boolean inputTypeInt = false;
	
	private int min = 0;
	private int max = 15;
	private int notAllowedToEqual = -1;

	private JTextField input;


	/**
	 * 
	 * Constructs a new <code>PromptDialog</code> object. 
	 *
	 * @param title
	 * @param msg
	 * @param defaultValue 
	 * @param inputTypeInt
	 * @param min
	 * @param max
	 * @param notAllowedToEqual 
	 */
	public PromptDialog(String title, String msg, String defaultValue, boolean inputTypeInt, int min, int max, int notAllowedToEqual) {
		super();
		this.min = min;
		this.max = max;
		this.notAllowedToEqual = notAllowedToEqual;
		this.inputTypeInt = inputTypeInt;
		JPanel mainPanel = new JPanel();
		this.getContentPane().add(mainPanel);

		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

		JPanel bob = new JPanel();
		bob.setLayout(new BoxLayout(bob, BoxLayout.X_AXIS));
		bob.add(new JLabel(msg));
		input = new JTextField();
		if(defaultValue != null && defaultValue.trim().equals("") == false) {
			input.setText(defaultValue);
			input.setCaretPosition(defaultValue.length());
		}
		bob.add(input);
		mainPanel.add(bob);
		input.addKeyListener(this);

		JButton okButton = new JButton(LanguageFactory.getInstance().getString(MessageId.ok));
		okButton.addActionListener(this);
		mainPanel.add(okButton);
		
		this.setPreferredSize(new Dimension(300,80));
		//this.setLocation(200, 200);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setTitle(title);
		this.setAlwaysOnTop(true);
		this.setResizable(false);
		this.pack();
	}

	/**
	 * 
	 * Document the prompt method 
	 *
	 */
	public void prompt() {
		this.setVisible(true);
		block = true;
		while(block) {
			try {
				Thread.sleep(35);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private boolean block = false;

	private boolean validateText() {
		String text = input.getText();
		if(allowNull == false && (text == null || text.trim().equals(""))) {
			return false;
		}
		text = text.trim();
		if(inputTypeInt) {
			int value;
			try {
				value = Integer.parseInt(text);
			} catch (NumberFormatException e) {
				return false;
			}
			if(value < min || value > max || value == notAllowedToEqual) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @return int
	 */
	public int getIntValue() {
		return Integer.parseInt(input.getText().trim());
	}

	/**
	 * @return String
	 */
	public String getValue() {
		return input.getText().trim();
	}

	public void actionPerformed(ActionEvent e) {
		handleOk();
	}

	private void handleOk() {
		if(validateText()) {
			this.setVisible(false);
			this.block = false;
			this.dispose();
		}
	}

	public void keyPressed(KeyEvent arg0) {
		// do nothing
	}

	public void keyReleased(KeyEvent arg0) {
		// do nothing
	}

	public void keyTyped(KeyEvent e) {
		if(e.getKeyChar() == '\n') {
			handleOk();
		}
	}
}