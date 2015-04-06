package com.phixyn.tetrisphix;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.phixyn.tetrisphix.gfx.EasyJLabel;

/**
 * JPanel subclass for the left side panel of the Tetris game. Displays
 * game mode, level and lines cleared information. Provides methods to
 * update the different labels used in the panels.
 * 
 * @author 	Alpeche Pancha
 * @version 1.0.5, 19 December 2014
 * @see 	Tetris
 * @see 	RightPanel
 * @see		EasyJLabel
 */
public class LeftPanel extends JPanel {
	
	// Serial Version UID
	private static final long serialVersionUID = 1L;
	
	// Size for this panel
	private final Dimension SIZE = new Dimension(144, 480);
	
	// Sizes for the two different sub panels
	private final Dimension INFO_PANEL_SIZE = new Dimension(144, 103);
	private final Dimension LINES_PANEL_SIZE = new Dimension(144, 60);
	
	// Color objects for the panels
	private final Color PANEL_BG_COLOR = Color.BLACK;
	private final Color PANEL_BORDER_COLOR = new Color(96, 164, 184);
	
	// Declare and initialize the panels and labels
	JPanel infoPanel = new JPanel();
	JPanel linesPanel = new JPanel();
	EasyJLabel gameModeLabel = new EasyJLabel("");
	EasyJLabel levelLabel = new EasyJLabel("Level: 1");
	EasyJLabel linesLabel = new EasyJLabel("0");
	
	/**
	 * Constructor method. Sets up this panel and both of the sub panels.
	 */
	public LeftPanel() {
		// Setting up this JPanel
		this.setBackground(new Color(0, 0, 0, 0));
		this.setOpaque(false);
		this.setMinimumSize(SIZE);
		this.setMaximumSize(SIZE);
		this.setSize(SIZE);
		this.setPreferredSize(SIZE);
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		// Setting up "Info" JPanel
		this.infoPanel.setLayout(new GridLayout(2,1));
		this.infoPanel.setMinimumSize(INFO_PANEL_SIZE);
		this.infoPanel.setMaximumSize(INFO_PANEL_SIZE);
		this.infoPanel.setSize(INFO_PANEL_SIZE);
		this.infoPanel.setPreferredSize(INFO_PANEL_SIZE);
		this.infoPanel.setBackground(PANEL_BG_COLOR);
		this.infoPanel.setBorder(BorderFactory.createLineBorder(PANEL_BORDER_COLOR));
		this.infoPanel.add(this.gameModeLabel);
		this.infoPanel.add(this.levelLabel);
		
		// Defining constraints for "Info" JPanel
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridheight = 1;
		gbc.weighty = 1.0;
		gbc.anchor = GridBagConstraints.PAGE_START;

		this.add(this.infoPanel, gbc);
		
		// Setting up "Lines" JPanel
		this.linesPanel.setLayout(new GridLayout(2, 1));
		this.linesPanel.setMinimumSize(LINES_PANEL_SIZE);
		this.linesPanel.setMaximumSize(LINES_PANEL_SIZE);
		this.linesPanel.setSize(LINES_PANEL_SIZE);
		this.linesPanel.setPreferredSize(LINES_PANEL_SIZE);
		this.linesPanel.setBackground(PANEL_BG_COLOR);
		this.linesPanel.setBorder(BorderFactory.createLineBorder(PANEL_BORDER_COLOR));
		EasyJLabel linesTitleLabel = new EasyJLabel("Lines");
		this.linesPanel.add(linesTitleLabel);
		this.linesPanel.add(this.linesLabel);
		
		// Defining constraints for "Lines" JPanel
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridheight = 1;
		gbc.weighty = 1.0;
		gbc.anchor = GridBagConstraints.PAGE_END;
		// gbc.insets = new Insets(300, 0, 0, 0);	// external padding
		this.add(this.linesPanel, gbc);
	}
	
	/**
	 * Sets the text of the game mode label.
	 * 
	 * @param gameMode - name of the game mode
	 */
	public void setModeLabel(String gameMode) {
		this.gameModeLabel.setText(gameMode);
	}
	
	/**
	 * Sets the text of the level label.
	 * 
	 * @param level - current game level
	 */
	public void setLevelLabel(int level) {
		this.levelLabel.setText("Level: " + Integer.toString(level));
	}
	
	/**
	 * Sets the text of the lines label.
	 * 
	 * @param linesClear - current amount of lines cleared by the player.
	 */
	public void setLinesLabel(int linesClear) {
		this.linesLabel.setText(Integer.toString(linesClear));
	}
}
