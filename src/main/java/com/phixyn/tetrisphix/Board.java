package com.phixyn.tetrisphix;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.phixyn.tetrisphix.piece.JPiece;
import com.phixyn.tetrisphix.piece.LPiece;
import com.phixyn.tetrisphix.piece.LinePiece;
import com.phixyn.tetrisphix.piece.Piece;
import com.phixyn.tetrisphix.piece.SPiece;
import com.phixyn.tetrisphix.piece.SquarePiece;
import com.phixyn.tetrisphix.piece.TPiece;
import com.phixyn.tetrisphix.piece.ZPiece;

/**
 * Class responsible for handling the game logic and doing most of the heavy
 * lifting in the game. It handles the drawing of the game board and the
 * Tetrominos. Additionally, it performs all game functions such as
 * generating Tetrominos, detecting collisions, clearing lines and checking if
 * the game is over or paused. For handling keyboard input, an instance of the
 * InputManager class is used.
 * 
 * @author	Alpeche Pancha
 * @version 1.0.5, 19 December 2014
 * @see 	InputManager
 * @see 	Piece
 */
public class Board extends JPanel {
	
	// Serial Version UID
	private static final long serialVersionUID = 1L;
	
	// Board grid's columns and rows
	private final int COLUMNS = 10;
	private final int ROWS = 20;
	
	// Size of each tile in the grid
	private final int TILE_SIZE = 24;
	
	// Board (JPanel) dimensions
	public final int BOARD_WIDTH = COLUMNS * TILE_SIZE;
	public final int BOARD_HEIGHT = ROWS * TILE_SIZE;
	private final Dimension SIZE = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
	
	// Board colors
	private final Color BACKGROUND_PANEL_COLOR = Color.BLACK;
	private final Color PANEL_BORDER_COLOR = new Color(144, 144, 144);
	// Board's background grid color
	private final Color BACKGROUND_GRID_COLOR = new Color(30, 30, 30);
	
	// Font for game information strings, such as "Paused" or "Game over"
	private final Font GAME_INFO_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 18);
	
	// Board (JPanel) X and Y coordinates
	public int x, y;
	
	/* 2D array representing the state of each of the tiles in the board's
	 * grid. If a value is above 0, then the tile is filled with a block.
	 * The number represents the sprite ID of the block or, in other
	 * words, its color. */
	private int[][] boardTiles = new int[COLUMNS][ROWS];
	
	// Game data constants
	private final int SCORE_MULTIPLIER = 10;
	private final int LINES_PER_LEVEL = 10;
	private final int INITIAL_GAME_TIMER_DELAY = 400;
	// Stop decrementing the game delay after we reach this number
	private final int MINIMUM_GAME_TIMER_DELAY = 50;
	// Decrement game delay by this amount every level
	private final int GAME_TIMER_DELAY_MULTIPLIER = 50;
	
	// Game data and information
	public int playerScore;
	public int linesClear;
	public int linesLeft;
	public int gameLevel;
	private int gameTimerDelay;
	public String gameMode;
	
	// Game state information
	public boolean isOver = false;
	public boolean isPaused = false;
	public boolean isStarted = false;
	
	// Piece (tetromino) objects
	public Piece currentPiece;
	public Piece nextPiece;
	
	// Keyboard and mouse input manager object
	private InputManager inputManager;
	
	// Swing Timer used to update the game according to the game's speed
	public Timer timer;
		
	/**
	 * Initializes and sets up the JPanel and game timer.
	 * The game timer is a Swing Timer which repeatedly calls the update
	 * method at a delay defined by the current game timer delay.
	 */
	public Board() {
		// Set up this JPanel
		super();
		setMinimumSize(SIZE);
		setMaximumSize(SIZE);
		setPreferredSize(SIZE);
		setSize(SIZE);
		setBackground(BACKGROUND_PANEL_COLOR);
		setBorder(BorderFactory.createLineBorder(PANEL_BORDER_COLOR));
		
		// Request focus for key and mouse input
		setFocusable(true);
		requestFocus();

		// What coordinates, sir?
		this.x = this.getX();
		this.y = this.getY();
		
		// Instantiate our InputManager class with this instance as its argument
		this.inputManager = new InputManager(this);
		// Add a mouse listener to this JPanel. Our InputManager class
		// implements the MouseListener interface
		this.addMouseListener(this.inputManager);
		
		// Generate the pieces, or tetrominos
		this.currentPiece = generatePiece();
		this.nextPiece = generatePiece();
		
		/* Define the Swing Timer. This will repeatedly call the update method
		 * with the delay defined by gameTimerDelay. This delay is decremented
		 * every time the player completes a level, causing the tetrominos to
		 * fall faster. The minimum delay is set to 50 by default, and by the
		 * time the player reaches it, they might not have any sanity left. */ 
		this.timer = new Timer(this.gameTimerDelay, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {				
				update();
			}
		});
	}
	
	/**
	 * Starts a new game. Resets the board by clearing its tiles, resets all
	 * game data and restarts the timer with the initial game timer delay.
	 */
	public void start() {
		// Set our boardTiles array to a new array with values initialized to 0
		boardTiles = new int[COLUMNS][ROWS];
		
		// Reset game data
		playerScore = 0;
		linesClear = 0;
		linesLeft = 10;
		gameLevel = 1;
		gameTimerDelay = INITIAL_GAME_TIMER_DELAY;
		isOver = false;
		isStarted = true;
		
		// Generate the pieces, or tetrominos, and start timer
		currentPiece = generatePiece();
		nextPiece = generatePiece();
		timer.setDelay(this.gameTimerDelay);
		timer.start();
	}

	/**
	 * Stops the game timer and sets the isStarted flag to false.
	 */
	public void stop() {
		isStarted = false;
		timer.stop();
	}
	
	/**
	 * Generates a random Piece object using a random integer generator and
	 * sets its X coordinate so that it spawns in the middle of the board.
	 * 
	 * @return 	thePiece - the generated Piece object itself, in all its glory.
	 * @see 	Piece
	 */
	private Piece generatePiece() {
		// Piece object to be returned
		Piece thePiece = null;
		
		// Generating the random integer
		Random pieceTypeGen = new Random();
		int pieceType = pieceTypeGen.nextInt(7);
		
		// Generating the piece and setting its X value so that it spawns
		// in the center of the board
		switch (pieceType) {
			case 0:
				thePiece = new SquarePiece();
				thePiece.setX(thePiece.getX() + COLUMNS / 2);
				break;
			case 1:
				thePiece = new LinePiece();
				thePiece.setX(thePiece.getX() + COLUMNS / 2);
				break;
			case 2:
				thePiece = new LPiece();
				thePiece.setX(thePiece.getX() + COLUMNS / 2);
				break;
			case 3:
				thePiece = new JPiece();
				thePiece.setX(thePiece.getX() + COLUMNS / 2);
				break;
			case 4:
				thePiece = new TPiece();
				thePiece.setX(thePiece.getX() + COLUMNS / 2);
				break;
			case 5:
				thePiece = new SPiece();
				thePiece.setX(thePiece.getX() + COLUMNS / 2);
				break;
			case 6:
				thePiece = new ZPiece();
				thePiece.setX(thePiece.getX() + COLUMNS / 2);
				break;
		}
		
		return thePiece;	
	}
	
	/**
	 * Adds a complete piece representation to the boardTiles 2D array.
	 * The piece representation is stored in a 3D integer array similar to the
	 * boardTiles array, called pieceTiles. Each value of pieceTiles consists
	 * of a 4 by 4 2D int array representing the piece - a value of 1
	 * represents a filled tile on the grid.
	 * <p>
	 * Thus, we can simply loop through the pieceTiles array and whenever we
	 * encounter a value of 1, we set the corresponding tile in boardTiles to a
	 * number. The number we set in boardTiles is the sprite ID of the piece,
	 * so that when we draw the tiles in boardTiles, we know which sprites to use.
	 * 
	 * @param 	piece	-	the Piece to be stored on the board
	 * @see		Piece
	 */
	private void addPiece(Piece piece) {
		// Get the current rotation of the piece.
		// Used to access the appropriate piece configuration.
		int pRotation = piece.getRotation();
		
		/* Store each block of the piece into the board.
		 * This is achieved by checking all the values of the pieceTiles array
		 * and storing them in the corresponding 4 by 4 area of the boardTiles
		 * array. We obtain the correct array indices by getting the X and Y
		 * coordinates of the piece and looping through the 4 by 4 area within
		 * these coordinates.
		 * 
		 * int px - piece X coordinate
		 * int py - piece Y coordinate
		 * int pcol - current column of the piece representation
		 * int prow - current row of the piece representation 
		 */
		for (int px = piece.getX(), pcol = 0; px < piece.getX() + 4; px++, pcol++)
		{
			for (int py = piece.getY(), prow = 0; py < piece.getY() + 4; py++, prow++)
			{	
				// Store only the blocks of the piece that are filled
				if (piece.getPieceTiles()[pRotation][prow][pcol] != 0) {
					try {
						/* Set the value in boardTiles to be the index of
						 * sprites + 1 (because it has to be above 0 for us to
						 * know that it represents a filled block!) */
						boardTiles[px][py - 1] = piece.getSpriteID() + 1;
					} catch (ArrayIndexOutOfBoundsException ex) {
						/* If we have reached the top of the board and cannot add any more
						 * tiles, then it's game over man, game over! */
						isOver = true;
					}
				}
			}
		}
	}
			
	/**
	 * Checks for collision against other pieces in the board and board limits.
	 * This is achieved by checking the 4 by 4 area of the blocks against the
	 * appropriate area in the board (the matching 4 by 4 area that the block
	 * is travelling).
	 * 
	 * @param pRotation - 	current piece rotation
	 * @param pX - 			current piece column
	 * @param pY - 			current piece row
	 * @return				boolean value indicating if the move is possible.
	 * @see					Piece
	 */
	public boolean canMove(int pRotation, int pX, int pY) {
		for (int pTileX = pX, bTileX = 0; pTileX < pX + 4; pTileX++, bTileX++)
		{
			for (int pTileY = pY, bTileY = 0; pTileY < pY + 4; pTileY++, bTileY++)
			{	
				// Check if the piece collides with the limits of the board
				if (pTileX < 0 || pTileX > COLUMNS - 1 || pTileY > ROWS - 1)
				{
					if (currentPiece.getPieceTiles()[pRotation][bTileY][bTileX] != 0) {
						// System.out.println("[DEBUG] Wall collision");
						return false;
					}
				}
				
				/* Check if the piece collides with any of the blocks already
				 * stored in the board.
				 * 
				 * Remember, a value in boardTiles is greater than 1 if it is
				 * filled with a block, thus not empty. Similarly, if a value
				 * in pieceTiles is 1, then it means that value represents a
				 * block. */
				if (pTileY >= 0) {
					if (currentPiece.getPieceTiles()[pRotation][bTileY][bTileX] > 0 && boardTiles[pTileX][pTileY] > 0) {
						// System.out.println("[DEBUG] Block collision");
						return false;
					}
				}
				
			}	/* End of inner for loop */
		}		/* End of for loop. */
		
		// If nothing above us returns false, then the move is indeed possible.
		return true;
	}
	
	/**
	 * Deletes a whole line from the board, by moving all the lines above it
	 * down by 1. In classic mode, a line is deleted regardless of the color of
	 * the blocks that fill it. In hardcore mode, a line must be filled with
	 * blocks of the same color in order to be cleared (good luck with that!).
	 * 
	 * @param lineY - Y position of the line to be deleted (in row, not pixels)
	 */
	private void deleteLine(int lineY) {
		// Was a line cleared successfully?
		boolean deleted = false;
		
		/* Delete a line in Classic Tetris mode. If a line is filled, it can be
		 * cleared, regardless of the colors of the blocks that fill the line. */
		if (gameMode.equals("Classic")) {
			for (int i = lineY; i > 0; i--) {
				// Loop through each line of the grid from the line we're
				// supposed to clear, until the bottom of the grid
				for (int j = 0; j < COLUMNS; j++) {
					// Shift the tiles down by one row
					boardTiles[j][i] = boardTiles[j][i-1];
				}
			}
			deleted = true;
		}
		
		/* Delete a line in Hardcore Tetris mode.
		 * If a line is filled and if all the blocks in the line are of the
		 * same color, it can be cleared. This is a more challenging mode,
		 * which drains more sanity from the player. */
		else if (gameMode.equals("Hardcore")) {
			// Indicates whether we can clear the line or not
			boolean canClear = false;
			// Color ID of the first block in the line we're checking
			int colorID = boardTiles[0][lineY];
			
			for (int c = 0; c < COLUMNS; c++) { 
				/* Go through each block in the row and check if they're all
				 * the same color. If so, canClear ends up with the value true. */
				if (boardTiles[c][lineY] == colorID) canClear = true;
				else canClear = false;
			}
			
			// If all the blocks in the row are the same color, we can clear. 
			if (canClear) {
				// Clear the line like we did before
				for (int i = lineY; i > 0; i--) {
					for (int j = 0; j < COLUMNS; j++) {
						boardTiles[j][i] = boardTiles[j][i-1];
					}
				}
				deleted = true;
			}
		}
		
		// If a line was deleted, update score, lines and level.
		if (deleted) {
			playerScore += SCORE_MULTIPLIER;
			linesClear++;
			linesLeft--;
			// If we cleared the number of lines required to advance level,
			// increment the game level and reset linesLeft.
			if (linesLeft == 0) {
				gameLevel++;
				linesLeft = LINES_PER_LEVEL;
				// If we haven't reached the minimum game speed, decrement it
				if (gameTimerDelay > MINIMUM_GAME_TIMER_DELAY) {
					gameTimerDelay -= GAME_TIMER_DELAY_MULTIPLIER;
					timer.setDelay(this.gameTimerDelay);
					timer.restart();
				}
			}
		}
	}
	
	/**
	 * Deletes all the lines in the board that are filled. This is done by
	 * looping through every row of the board and checking if it contains
	 * any blocks. If the row is filled, then we call the deleteLine method
	 * defined above. 
	 */
	public void deletePossibleLines() {
		for (int i = 0; i < ROWS; i++) {
			int j = 0;
			while (j < COLUMNS) {
				// If a column is empty, then the row can't be filled
				if (boardTiles[j][i] == 0) {
					break;
				}
				j++;
			}
			// If all the columns are filled, then we can delete the line
			if (j == COLUMNS) deleteLine(i);
		}
	}
	
	/**
	 * Checks if the game is over by seeing if the top most row of the board
	 * contains any blocks. If it does, then game over man, game over!
	 * 
	 * @return true if the top most row is filled, false otherwise.
	 */
	private boolean isGameOver() {
		for (int i = 0; i < COLUMNS; i++)
		{
			if (boardTiles[i][0] > 0) return true;
		}
		return false;
	}
	
	/**
	 * Attempts to move the current piece down by one row. If a piece has
	 * reached the bottom of the board or collided with the top of another
	 * piece in the board, then we make the piece stop, add it to the
	 * boardTiles array and update the current piece and next piece.
	 * Additionally, we check if we can clear any lines, if they are filled,
	 * by calling the deletePossibleLines method.
	 * <p>
	 * This method also checks if the game is over by calling the isGameOver
	 * method. If isGameOver returns true, it calls the stop method.
	 */
	public void update() {
		// Check if we can move the current piece down and do so if we can
		if (canMove(currentPiece.getRotation(), currentPiece.getX(), currentPiece.getY() + 1)) {
			currentPiece.setY(currentPiece.getY() + 1);
		}
		
		// If we can't move the current piece down, check if the game is over
		else {
			if (isGameOver()) {
				isOver = true;
				stop();
			}
			
			// If the game is not over, update the piece's Y coordinate once
			// more and add it to the board.
			else {
				currentPiece.setY(currentPiece.getY() + 1);
				currentPiece.setFalling(false);
				addPiece(currentPiece);
				deletePossibleLines();
				currentPiece = nextPiece;
				nextPiece = generatePiece();
			}
		}
		
		repaint();
	}
		
	/**
	 * Draws the board's background grid.
	 * 
	 * @param g2d - a Graphics2D object used for drawing the grid
	 */
	public void drawGrid(Graphics2D g2d) {
		g2d.setColor(BACKGROUND_GRID_COLOR);
		for (int xx = this.x; xx < this.BOARD_WIDTH + this.x; xx += TILE_SIZE) {
			for (int yy = this.y; yy < this.BOARD_HEIGHT + this.y; yy += TILE_SIZE) {
				g2d.drawRect(xx, yy, TILE_SIZE, TILE_SIZE);
			}
		}	
	}
	
	/**
	 * Draws the board grid, as well as the current piece and all the blocks
	 * stored in the boardTiles array.
	 * 
	 * @param g - a Graphics object which will be casted to a Graphics2D object
	 * used to draw graphics onto the screen.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		drawGrid(g2d);
		currentPiece.draw(g2d);
		/* Iterate through every cell in the boardTiles array and draw the
		 * block if the cell is filled. The boardTiles array contains the
		 * sprite ID which is used to determine the image to be drawn. */
		for (int row = 0; row < boardTiles.length; row++) {
			for (int col = 0; col < boardTiles[row].length; col++) {
				if (boardTiles[row][col] > 0) {
					g2d.drawImage(Piece.getBlockSprites().getSprites()[boardTiles[row][col] - 1], (row * TILE_SIZE), (col * TILE_SIZE), TILE_SIZE, TILE_SIZE, null);
				}
			}
		}
		
		// Game info text (sorry for the magic numbers)
		if (isOver) {
			g.setColor(Color.RED);
			g.setFont(GAME_INFO_FONT);
			g.drawString("GAME OVER!", BOARD_WIDTH / 2 - 55, BOARD_HEIGHT / 2 - 5);
		}
		else if (isPaused) {
			g.setColor(Color.WHITE);
			g.setFont(GAME_INFO_FONT);
			g.drawString("PAUSED", BOARD_WIDTH / 2 - 40, BOARD_HEIGHT / 2 - 5);
		}
	}
}
