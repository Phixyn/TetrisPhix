package com.phixyn.tetrisphix;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import com.phixyn.tetrisphix.gfx.EasyJLabel;
import com.phixyn.tetrisphix.piece.Piece;

/**
 * JPanel subclass for the right side panel of the Tetris game. Displays the
 * next piece to be spawned in the game and the player's score.
 * <p>
 * Provides methods to set the next piece and score label as well as an inner
 * class for the "next" panel JPanel.
 * 
 * @author 	Alpeche Pancha
 * @version 1.0.5, 19 December 2014
 * @see 	Tetris
 * @see 	LeftPanel
 * @see		Piece
 * @see		EasyJLabel
 */
public class RightPanel extends JPanel {

	// Serial Version UID
	private static final long serialVersionUID = 1L;
	
	// Size for this panel
	private final Dimension SIZE = new Dimension(144, 480);
	
	// Sizes of the sub panels
	private final Dimension NEXT_PANEL_SIZE = new Dimension(144, 103);
	private final Dimension SCORE_PANEL_SIZE = new Dimension(144, 60);
	
	// Color objects for the panels
	private final Color PANEL_BG_COLOR = Color.BLACK;
	private final Color PANEL_BORDER_COLOR = new Color(96, 164, 184);
	
	// Declare and initialize the panels and label
	private NextPanel nextPanel = new NextPanel();
	private JPanel scorePanel = new JPanel();
	private EasyJLabel scoreLabel = new EasyJLabel("0");
	
	// Piece object for the nextPiece in the game
	private Piece nextPiece;
	
	public RightPanel() {
		// Setting up this JPanel
		this.setBackground(new Color(0, 0, 0, 0));
		this.setOpaque(false);
		this.setMinimumSize(SIZE);
		this.setMaximumSize(SIZE);
		this.setSize(SIZE);
		this.setPreferredSize(SIZE);
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		// TODO this.setOpaque(false);
		
		// Defining constraints for "Next" JPanel
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.weighty = 1.0;
		gbc.anchor = GridBagConstraints.PAGE_START;
		this.add(this.nextPanel, gbc);
		
		// Setting up the "Score" JPanel
		this.scorePanel.setLayout(new GridLayout(2, 1));
		this.scorePanel.setMinimumSize(SCORE_PANEL_SIZE);
		this.scorePanel.setMaximumSize(SCORE_PANEL_SIZE);
		this.scorePanel.setSize(SCORE_PANEL_SIZE);
		this.scorePanel.setPreferredSize(SCORE_PANEL_SIZE);
		this.scorePanel.setBackground(PANEL_BG_COLOR);
		this.scorePanel.setBorder(BorderFactory.createLineBorder(PANEL_BORDER_COLOR));
		EasyJLabel scoreTitleLabel = new EasyJLabel("Score");
		this.scorePanel.add(scoreTitleLabel);
		this.scorePanel.add(scoreLabel);
		
		// Defining constraints for "Score" JPanel
		gbc.gridy = 1;
		gbc.weighty = 1.0;
		gbc.anchor = GridBagConstraints.PAGE_END;
		// gbc.insets = new Insets(300, 0, 0, 0);
		this.add(this.scorePanel, gbc);
	}
	
	/**
	 * Inner class for the "next" panel. Overrides the paintComponent method to
	 * draw the next piece.
	 * 
	 * @author Alpeche Pancha
	 * @version 1.0.0, 08 December 2014
	 * @see Piece
	 * @see EasyJLabel
	 */
	private class NextPanel extends JPanel {
		
		// Serial Version UID
		private static final long serialVersionUID = 1L;

		/**
		 * Constructor method for the panel. Sets up the panel.
		 */
		public NextPanel() {
			// Setting up "Next" JPanel
			this.setLayout(new GridLayout(3,1));
			this.setMinimumSize(NEXT_PANEL_SIZE);
			this.setMaximumSize(NEXT_PANEL_SIZE);
			this.setSize(NEXT_PANEL_SIZE);
			this.setPreferredSize(NEXT_PANEL_SIZE);
			this.setBackground(PANEL_BG_COLOR);
			this.setBorder(BorderFactory.createLineBorder(PANEL_BORDER_COLOR));
			EasyJLabel nextTitleLabel = new EasyJLabel("Next");
			this.add(nextTitleLabel);
		}
		
		/**
		 * Draws the next piece onto the panel, by iterating through the
		 * pieceTiles array and drawing each square image appropriately.
		 * 
		 * @param g - Graphics object used to draw images to the screen
		 * @see Piece
		 */
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D)g;
			int[][][] pieceTiles = nextPiece.getPieceTiles();
			int rotation = nextPiece.getRotation();
			BufferedImage pieceSprite = nextPiece.getPieceSprite();
			int x = 12, y = 12;
			final int TILE_SIZE = 24;
			
			for (int row = 0; row < pieceTiles[rotation].length; row++) {
				for (int col = 0; col < pieceTiles[rotation][row].length; col++) {
					if (pieceTiles[rotation][col][row] > 0) {
						g2d.drawImage(pieceSprite,
								(x + row * TILE_SIZE),
								(y + col * TILE_SIZE),
								TILE_SIZE, TILE_SIZE, null);
					}
				}
			}
		}
	}
	
	/**
	 * Set the next piece object.
	 * 
	 * @param piece - the Piece object
	 */
	public void setNextPiece(Piece piece) {
		this.nextPiece = piece;
	}
	
	/**
	 * Set the score label text.
	 * 
	 * @param score - the current score of the player.
	 */
	public void setScoreLabel(int score) {
		this.scoreLabel.setText(Integer.toString(score));		
	}
}
