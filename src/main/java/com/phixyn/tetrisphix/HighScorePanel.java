package com.phixyn.tetrisphix;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.phixyn.tetrisphix.gfx.FancyJButton;

/**
 * Class responsible to displaying the player highscores, stored in a text
 * file. Scores are obtained via the ScoreManager class. A JTable is used
 * to display the highscores and I seriously feel sorry for anyone who has to
 * use JTable extensively, because it's the worst Swing component I've had
 * to use in my life. 
 * 
 * @author	Alpeche Pancha
 * @version 1.0.5, 19 December 2014
 * @see 	DefaultTableModel
 * @see 	ScoreManager
 * @see		FancyJButton
 */
public class HighScorePanel extends JPanel {
	
	// Serial Version UID
	private static final long serialVersionUID = 1L;
	
	// Size of this panel
	private final Dimension SIZE = new Dimension(800, 490);
	
	// private final Dimension BUTTON_SIZE = new Dimension(120, 50);
	
	// Color for the main menu button
	private final Color BUTTON_BG_COLOR = new Color(0, 0, 0, 0);
	private final Color BUTTON_FG_COLOR = new Color(255, 255, 255);
	
	// Font objects for the button text and score text
	private final Font BUTTON_FONT = new Font(Font.MONOSPACED, Font.BOLD, 18);
	private final Font SCORE_HEADER_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 16);
	private final Font SCORE_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 16);
	
	// 2D Object array holding the scores, which will be used to make the table
	// model.
	private static Object[][] scores;
	
	// Header labels for the table header
	private final static String[] TABLE_COL_HEADER = {"Name", "Score", "Level", "Mode"};
	
	// JTable and a default table model object
	private JTable highScores;
	private static DefaultTableModel tableModel;
	
	/**
	 * Constructor method for the highscores panel.
	 * Initializes the panel, loads the scores and calls the createJTable
	 * method. Adds the JTable to the panel.
	 */
	@SuppressWarnings("serial")
	public HighScorePanel() {
		super(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		this.setSize(SIZE);
		this.setBackground(new Color(0, 0, 0));
		this.setFocusable(true);
		
		// Load scores from the score manager and create our table
		scores = ScoreManager.loadScores();
		createJTable();
		
		// Set up the gridbag layout constraints and add the table header and
		// table to this JPanel
		gbc.gridx = 0;
		gbc.gridy = 0;		
		add(highScores.getTableHeader(), gbc);
		gbc.gridy = 2;
		add(highScores, gbc);
		
		// Make a button that allows the player to return to main menu
		FancyJButton button1 = new FancyJButton("Main Menu");
		
		// Adds an action listener to the button that switches to the main menu
		// scene when the player clicks the button
		button1.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				GameMain.sceneManager.show(GameMain.scenes, "Menu");
			}
		});
		
		// Set up the constraints for the button and add it to the panel
		gbc.insets = new Insets(25, 0, 10, 0);
		gbc.gridy = 4;
		add(button1, gbc);
	}
	
	/**
	 * And the award for worst Swing component goes to...
	 * <p>
	 * Initializes and formats the JTable used to display the highscores.
	 */
	private void createJTable() {
		// Initialize table model and JTable
		tableModel = new DefaultTableModel(scores, TABLE_COL_HEADER);
		// tableModel.setDataVector(this.scores, TABLE_COL_HEADER);
		highScores = new JTable(tableModel);
		
		// Disable table header component and make sure it's not focusable
		highScores.getTableHeader().setEnabled(false);
		highScores.getTableHeader().setFocusable(false);
		
		// Format table header
		highScores.getTableHeader().setBorder(null);
		highScores.getTableHeader().setBackground(Color.BLACK);
		highScores.getTableHeader().setForeground(Color.WHITE);
		highScores.getTableHeader().setFont(SCORE_HEADER_FONT);
		
		// Disable table component and make sure it's not focusable
		// Hey, you can't cheat like that! PS: Try editing the file instead...
		highScores.setEnabled(false);
		highScores.setFocusable(false);
		
		// Create table cell renderers to align the text in the table
		// Seriously, whoever wrote Swing... why did they choose such long names?!
		DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
		leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);
		centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
		
		// Set width of first column (player name)
		highScores.getColumnModel().getColumn(0).setPreferredWidth(150);
		
		// Set the text alignment of the table columns
		highScores.getColumnModel().getColumn(0).setCellRenderer(leftRenderer);
		highScores.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
		highScores.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
		highScores.getColumnModel().getColumn(3).setCellRenderer(rightRenderer);
		
		// Finally, format the table and make it pretty
		highScores.setRowHeight(35);
		highScores.setGridColor(Color.BLACK);
		highScores.setBackground(Color.BLACK);
		highScores.setForeground(Color.WHITE);
		highScores.setFont(SCORE_FONT);
		
		// Seriously JTables suck. I hate all of Swing, but JTables are the worst.
	}

	/**
	 * Refreshes the JTable. Used when the highscores file has changes.
	 */
	public static void updateScores() {
		scores = ScoreManager.loadScores();
		tableModel.setDataVector(scores, TABLE_COL_HEADER);
	}	
}
