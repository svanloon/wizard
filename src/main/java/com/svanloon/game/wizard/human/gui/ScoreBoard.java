package com.svanloon.game.wizard.human.gui;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.svanloon.common.util.IconUtil;
import com.svanloon.game.wizard.language.LanguageFactory;
import com.svanloon.game.wizard.language.MessageId;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.List;

/**
 * 
 * @author svanloon
 * @version $Rev$, $LastChangedDate$
 */
public class ScoreBoard extends JFrame {
	private static final long serialVersionUID = 1L;
    private int round = 0;
    private List<Integer> _playerIds;

	/**
	 * 
	 * Document the addPlayers method 
	 * @param ids
	 * @param playerNames
	 */
	public void addPlayers(List<Integer> ids, List<String> playerNames) {
		this._playerIds = ids;
        javax.swing.table.TableModel model = table.getModel();
        int i = 0;
		for(String name : playerNames) {
			i++;
			model.setValueAt(name, 0, i*2 - 1);
		}
		this.repaint();
	}

	private JTable table;

	private int lastRow;
    /**
     * 
     * Constructs a new <code>ScoreBoard</code> object. 
     * @param numberOfPlayers 
     *
     */
    public ScoreBoard(int numberOfPlayers) {
    	super();
		Image image = IconUtil.getIcon();
		this.setIconImage(image);
    	setLayout(new GridLayout(1,0));
        
    	int columnNumbers = 1 + numberOfPlayers*2;
    	String[] columnNames = new String[columnNumbers];
    	for(int i = 0; i < columnNames.length; i++) {
    		columnNames[i] = "";
    	}

    	int rounds = (int)(60.0 /numberOfPlayers);
    	int rowNumbers = 3 +  rounds;
        Object[][] data = new Object[rowNumbers][columnNumbers]; 

        // initialize

        // initialize playerNames
    	for(int j = 0; j < data[0].length; j++) {
    		if(j == 0) {
    			data[0][j] = "";
    		} else if(j%2 == 0) {
    			data[0][j] = "";
    		} else {
    			data[0][j] = "Player " + (j/2 + 1);
    		}
    	}

    	// initialize playerNames
    	for(int j = 0; j < data[1].length; j++) {
    		if(j == 0) {
    			data[1][j] = LanguageFactory.getInstance().getString(MessageId.round);
    		} else if(j%2 == 0) {
    			data[1][j] = LanguageFactory.getInstance().getString(MessageId.score);
    		} else {
    			data[1][j] = LanguageFactory.getInstance().getString(MessageId.bid);
    		}
    	}

        // initialize rounds
    	int bob = 0;
        for(int i = 2; i < rowNumbers - 1; i++ ) {
        	for(int j = 0; j < data[i].length; j++) {
        		if(j == 0) {
        			bob++;
        			data[i][j] = String.valueOf(bob);
        		} else {
        			data[i][j] = "";
        		}
        	}
        }
    	// initialize 
        lastRow = rowNumbers - 1;
    	for(int j = 0; j < data[lastRow].length; j++) {
    		if(j == 0) {
    			data[rowNumbers - 1][j] = LanguageFactory.getInstance().getString(MessageId.total);
    		} else if(j%2 == 0) {
    			data[rowNumbers - 1][j] = Integer.valueOf(0);
    		} else {
    			data[rowNumbers - 1][j] = "";
    		}
    	}

    	table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setEnabled(false);

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        add(scrollPane);

    }

    /**
     * 
     * Document the setRound method 
     *
     * @param round
     */
    public void setRound(int round) {
    	this.round = round;
    }
    /**
     * 
     * Document the setBid method 
     * @param id 
     * @param bid
     */
    public void setBid(int id, int bid) {
    	int index = findPlayerIndex(id);
    	javax.swing.table.TableModel model = table.getModel();
    	model.setValueAt(Integer.valueOf(bid), 1 + round, index * 2 + 1);
    }

    /**
     * 
     * Document the setScore method 
     * @param id 
     * @param score
     */
    public void setScore(int id, int score) {
    	int index = findPlayerIndex(id);
    	javax.swing.table.TableModel model = table.getModel();
    	int scoreColumnIndex  = index * 2 + 2;
    	int roundRowIndex = 1 + round; 
    	model.setValueAt(Integer.valueOf(score), roundRowIndex, scoreColumnIndex);
    	
    	// Set Total Scorce
    	int totalRowIndex = lastRow;
    	Integer totalScore = (Integer) model.getValueAt(totalRowIndex, scoreColumnIndex);
    	Integer newTotalScore = Integer.valueOf(totalScore.intValue() + score); 
    	model.setValueAt(newTotalScore, totalRowIndex, scoreColumnIndex);
    	this.repaint();
    }

    private int findPlayerIndex(int id) {
    	int i = 0;
    	for(Integer id2 : _playerIds) {
    		if(id2.intValue() == id) {
    			return i;
    		}
    		i++;
    	}
    	return -1;
    }
}

