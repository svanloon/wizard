package com.svanloon.game.wizard.human;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class FivePlayerLayout extends GridLayout implements PlayerLayout {
	private static final long serialVersionUID = 1L;

	/**
	 */
	public FivePlayerLayout() {
		super();
	}

	private Map<WizardComponentEnum, Component> componentMap = new HashMap<WizardComponentEnum, Component>();

	/**
	 * @param wce
	 * @param comp
	 */
	public void addComponent(WizardComponentEnum wce, Component comp) {
		componentMap.put(wce, comp);
	}
	private void setSize(Component comp, Dimension d) {
		comp.setPreferredSize(d);
		comp.setMaximumSize(d);
		comp.setMinimumSize(d);
	}

	private Component createSpacer() {
		JPanel j = new JPanel();
		setColor(j);
		return j;
	}
	
	private void setColor(Component comp) {
		comp.setBackground(GuiConstants.BACKGROUND_COLOR);
	}

	private Component spacer1 = createSpacer();
	private Component spacer2 = createSpacer();
	private Component spacer3 = createSpacer();
	private Component spacerul = createSpacer();
	private Component spacerur = createSpacer();
	private Component spacerll = createSpacer();
	private Component spacerlr = createSpacer();
	private Component spacerCenter = createSpacer();
	private Component spacerCenter2 = createSpacer();

	private void setAllSizes() {

		Component trump = componentMap.get(WizardComponentEnum.TRUMP);
		
		Component west = componentMap.get(WizardComponentEnum.WEST_HAND);
		Component north = componentMap.get(WizardComponentEnum.NORTH_HAND);
		Component south = componentMap.get(WizardComponentEnum.SOUTH_HAND);
		Component east = componentMap.get(WizardComponentEnum.EAST_HAND);

		Component southEast = componentMap.get(WizardComponentEnum.SOUTH_EAST_HAND);

		setColor(west);
		setColor(north);
		setColor(south);
		setColor(east);
		setColor(southEast);

		Component northCardPlayedPanel = componentMap.get(WizardComponentEnum.NORTH_CARD_PLAYED);
		Component southCardPlayedPanel = componentMap.get(WizardComponentEnum.SOUTH_CARD_PLAYED);
		Component eastCardPlayedPanel = componentMap.get(WizardComponentEnum.EAST_CARD_PLAYED);
		Component westCardPlayedPanel = componentMap.get(WizardComponentEnum.WEST_CARD_PLAYED);
		Component southEastCardPlayedPanel = componentMap.get(WizardComponentEnum.SOUTH_EAST_CARD_PLAYED);

		Component northTrick =  componentMap.get(WizardComponentEnum.NORTH_TRICK);
		Component westTrick =  componentMap.get(WizardComponentEnum.WEST_TRICK);
		Component southTrick =  componentMap.get(WizardComponentEnum.SOUTH_TRICK);
		Component eastTrick  =  componentMap.get(WizardComponentEnum.EAST_TRICK);
		Component southEastTrick  =  componentMap.get(WizardComponentEnum.SOUTH_EAST_TRICK);
		
		
		Component scoreBoard  =  componentMap.get(WizardComponentEnum.SCORE_BOARD);
		scoreBoard.setSize(500, 325);
		scoreBoard.setLocation(700, 20);
		
		int cardHeight = 97;
		int cardWidth = 77;
		int cardMove = 22;
		int fontsize = 12;
		int actualWidth = 77;
		int textHeight = fontsize*4;
		int textWidth = textHeight;
		int handWidth = 15*cardMove + cardWidth;

		// set sizes
		Dimension corner = new Dimension(cardHeight + cardMove, cardHeight + cardMove);
		Dimension innerCorner = new Dimension( cardWidth + textWidth, cardHeight + textWidth);

		setSize(trump, corner);
		setSize(west, new Dimension(cardHeight + cardMove, handWidth));
		setSize(spacer1, corner);

		setSize(north, new Dimension(handWidth, cardHeight + cardMove));

		int fudge = 0;
		setSize(spacerul, innerCorner);
		setSize(northCardPlayedPanel, new Dimension(fudge + cardWidth, cardHeight));
		setSize(northTrick, new Dimension(actualWidth, textHeight));
		setSize(spacerur, innerCorner);

		setSize(westTrick, new Dimension(actualWidth, textHeight));
		setSize(westCardPlayedPanel, new Dimension(cardWidth, cardHeight));
		setSize(spacerCenter, new Dimension(cardWidth, cardHeight));
		setSize(spacerCenter2, new Dimension(cardWidth, cardHeight));
		setSize(eastCardPlayedPanel, new Dimension(cardWidth, cardHeight));
		setSize(eastTrick, new Dimension(actualWidth, textHeight));

		setSize(spacerll, innerCorner);
		setSize(southCardPlayedPanel, new Dimension(fudge + cardWidth, cardHeight));
		setSize(southTrick, new Dimension(actualWidth, textHeight));
		setSize(spacerlr, innerCorner);
		setSize(south, new Dimension(handWidth, cardHeight + cardMove));

		setSize(spacer2, corner);
		setSize(east, new Dimension(cardHeight + cardMove, handWidth));
		setSize(spacer3, corner);

		setSize(southEastTrick, new Dimension(actualWidth, textHeight));

		setSize(southEastCardPlayedPanel, new Dimension(cardWidth, cardHeight));
		
		setSize(southEast, new Dimension(cardHeight + cardMove, handWidth));
	}

	/**
	 * @return Component
	 */
	public Component getComponent() {

		setAllSizes();
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel,BoxLayout.X_AXIS));
		setColor(mainPanel);

		JPanel col1 = new JPanel();
		setColor(col1);
		col1.setLayout(new BoxLayout(col1, BoxLayout.Y_AXIS));
		col1.add(componentMap.get(WizardComponentEnum.TRUMP));
		col1.add(componentMap.get(WizardComponentEnum.WEST_HAND));
		col1.add(spacer1);
		mainPanel.add(col1);

		JPanel col2 = new JPanel();
		setColor(col2);
		col2.setLayout(new BoxLayout(col2, BoxLayout.PAGE_AXIS));
		col2.add(componentMap.get(WizardComponentEnum.NORTH_HAND));

		JPanel play = new JPanel();
		play.setBackground(GuiConstants.BACKGROUND_COLOR);
		play.setLayout(new BoxLayout(play, BoxLayout.X_AXIS));

		JPanel miniCol1 = new JPanel();
		setColor(miniCol1);
		miniCol1.setLayout(new BoxLayout(miniCol1, BoxLayout.Y_AXIS));
		miniCol1.add(spacerul);
		JPanel temp = new JPanel();
		setColor(temp);
		temp.setLayout(new BoxLayout(temp,BoxLayout.X_AXIS));
		temp.add(componentMap.get(WizardComponentEnum.WEST_TRICK));
		temp.add(componentMap.get(WizardComponentEnum.WEST_CARD_PLAYED));
		miniCol1.add(temp);
		miniCol1.add(spacerll);
		play.add(miniCol1);

		JPanel miniCol2 = new JPanel();
		setColor(miniCol2);
		miniCol2.setLayout(new GridBagLayout());
		miniCol2.add(componentMap.get(WizardComponentEnum.NORTH_TRICK),
						new GridBagConstraints(0,0,1,1,0.0,0.0,
						GridBagConstraints.CENTER, GridBagConstraints.NONE,
						new Insets(0,0,0,0),0,0));
		miniCol2.add(componentMap.get(WizardComponentEnum.NORTH_CARD_PLAYED),
						new GridBagConstraints(0,1,1,1,0.0,0.0,
						GridBagConstraints.CENTER, GridBagConstraints.NONE,
						new Insets(0,0,0,0),0,0));
		miniCol2.add(spacerCenter,
						new GridBagConstraints(0,2,1,1,1.0,1.0,
						GridBagConstraints.CENTER, GridBagConstraints.NONE,
						new Insets(0,0,0,0),0,0));
		miniCol2.add(componentMap.get(WizardComponentEnum.SOUTH_CARD_PLAYED),
						new GridBagConstraints(0,3,1,1,0.0,0.0,
						GridBagConstraints.CENTER, GridBagConstraints.NONE,
						new Insets(0,0,0,0),0,0));
		miniCol2.add(componentMap.get(WizardComponentEnum.SOUTH_TRICK),
						new GridBagConstraints(0,4,1,1,0.0,0.0,
						GridBagConstraints.CENTER, GridBagConstraints.NONE,
						new Insets(0,0,0,0),0,0));
		
		play.add(miniCol2);

		JPanel miniCol3 = new JPanel();
		setColor(miniCol3);
		miniCol3.setLayout(new BoxLayout(miniCol3, BoxLayout.Y_AXIS));
		miniCol3.add(spacerur);
		miniCol3.add(spacerlr);
		play.add(miniCol3);

		col2.add(play);
		col2.add(componentMap.get(WizardComponentEnum.SOUTH_HAND));
		mainPanel.add(col2);


		JPanel col3 = new JPanel();
		col3.setLayout(new BoxLayout(col3, BoxLayout.Y_AXIS));
		setColor(col3);

		JPanel temp2 = new JPanel();
		setColor(temp2);
		temp2.setLayout(new BoxLayout(temp2,BoxLayout.X_AXIS)); 
		temp2.add(componentMap.get(WizardComponentEnum.EAST_CARD_PLAYED));
		temp2.add(componentMap.get(WizardComponentEnum.EAST_TRICK));
		col3.add(temp2);

		col3.add(spacerCenter2);

		JPanel temp3 = new JPanel();
		setColor(temp3);
		temp3.setLayout(new BoxLayout(temp3,BoxLayout.X_AXIS));
		temp3.add(componentMap.get(WizardComponentEnum.SOUTH_EAST_CARD_PLAYED));
		temp3.add(componentMap.get(WizardComponentEnum.SOUTH_EAST_TRICK));
		col3.add(temp3);

/*		JPanel temp4 = new JPanel();
		setColor(temp4);
		temp4.setLayout(new BoxLayout(temp4,BoxLayout.X_AXIS));
		temp4.add(componentMap.get(WizardComponentEnum.SOUTH_SOUTH_EAST_CARD_PLAYED));
		temp4.add(componentMap.get(WizardComponentEnum.SOUTH_SOUTH_EAST_TRICK));
		col3.add(temp4);
*/
		mainPanel.add(col3);

		JPanel col4 = new JPanel();
		setColor(col4);
		col4.setLayout(new BoxLayout(col4, BoxLayout.Y_AXIS));
		col4.add(spacer2);
		col4.add(componentMap.get(WizardComponentEnum.EAST_HAND));
		col4.add(spacer3);
		mainPanel.add(col4);

		mainPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		JPanel t = new JPanel();
		Component comp = componentMap.get(WizardComponentEnum.STATUS_BAR);
		comp.setBackground(GuiConstants.STATUS_BAR_BG);
		comp.setForeground(GuiConstants.STATUS_BAR_FG);		
		t.setLayout(new BorderLayout());
		t.add(mainPanel,BorderLayout.CENTER);
		t.add(comp,BorderLayout.SOUTH);
		
		t.setBackground(GuiConstants.BACKGROUND_COLOR);
		return t;
	}
}
