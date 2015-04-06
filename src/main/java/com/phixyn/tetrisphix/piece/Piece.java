package com.phixyn.tetrisphix.piece;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import com.phixyn.tetrisphix.gfx.Tetrominos;

/**
 * Abstract class used to represent Tetromino shapes. Different shapes are
 * subclasses of this class that override the pieceTiles 3D array with their
 * respective piece representations. The pieceTiles array is perhaps the most
 * important part of this class and is crucial in understanding how the pieces
 * are represented in the game. This array is explained in more detail below.
 * <p>
 * This abstract class also provides a method to draw the entire Tetromino
 * shape using a sprite image chosen at random. Additionally, it provides
 * various getter and setter methods used by other classes to access
 * information about the piece.
 * 
 * @author	Alpeche Pancha
 * @version 1.0.5, 19 December 2014
 * @see		Tetrominos
 */
public abstract class Piece {
	
	// Size, in pixels, of a single block
	protected final int TILE_SIZE = 24;
	
	// X and Y coordinates, in columns and rows, of a piece
	protected int x, y;
	
	// Current rotation of the piece, commonly used to access a piece's
	// configuration in pieceTiles
	protected int rotation;
	
	// Total number of possible rotations for this piece
	protected int possibleRotations;
	
	// ID of the sprite used to draw the solid blocks of the piece
	protected int spriteID;
	
	/* Perhaps the most important part of this class.
	 * 
	 * This is a 3D array that holds information about the piece's shape
	 * for each of its possible rotations. It stores a 4 by 4 matrix for each
	 * possible rotation. The values in these matrices are either 0 or 1.
	 * A value of 1 means that the cell is a solid block and should be drawn.
	 * 
	 * For example, pieceTiles[0][1][2] means that for the first rotation
	 * of the piece, the cell at the second row and third column is a solid
	 * block. For a more concrete example, see the individual subclasses and
	 * how they all initialize this array. */
	protected int[][][] pieceTiles;
	
	// Boolean value indicating if the piece is currently moving down the board
	protected boolean falling;
	
	// Tetrominos object used to access the spritesheet image containing sprites
	// for the piece blocks
	protected static Tetrominos blockSprites;
	
	// BufferedImage object containing the image that will be drawn for the
	// solid blocks that make up the piece
	protected BufferedImage pieceSprite;
	
	/**
	 * Initializes the piece's attributes and selects a random sprite from
	 * the Tetrominos spritesheet.
	 * 
	 * @see Tetrominos
	 */
	public Piece() {
		// Set the piece's column and row to -2. This will help the piece
		// to spawn at a correct position on the board
		this.x = -2;
		this.y = -2;
		
		this.rotation = 0;

		// Generate a number between 0 and 6, since there are 7 different
		// sprites available on our spritesheet
		Random rand = new Random();
		this.spriteID = rand.nextInt(7);
		
		this.pieceTiles = new int[4 /* rotation */][4 /* rows */][4 /* columns */];
		
		this.falling = true;

		// Load the Tetrominos spritesheet
		blockSprites = new Tetrominos();
		
		// Obtain the sprite from the spritesheet
		this.pieceSprite = blockSprites.getSprites()[this.spriteID];
	}
	
	/**
	 * Iterates through the pieceTile matrix and draws each block of the piece.
	 * This is a very hard method to explain and it's important to know exactly
	 * what the pieceTiles array contains in order to fully understand it.
	 * <p>
	 * When we draw a block using the sprite image, the X coordinate of the
	 * image is the piece's current column in the grid multiplied by the
	 * TILE_SIZE, plus the row multiplied by the TILE_SIZE. The image's Y
	 * coordinate is calculated in a similar way.
	 * 
	 * @param g2d - Graphics2D object used to draw the piece's sprite image
	 */
	public void draw(Graphics2D g2d) {
		// Iterate through the matrix of the current rotation
		// For each row in the matrix
		for (int row = 0; row < pieceTiles[rotation].length; row++) {
			// For each column in the matrix
			for (int col = 0; col < pieceTiles[rotation][row].length; col++) {
				// If the current cell's value is 1, it represents a block
				if (pieceTiles[rotation][col][row] > 0) {
					// Therefore, we draw it
					g2d.drawImage(pieceSprite,
							(x * TILE_SIZE + row * TILE_SIZE),
							(y * TILE_SIZE + col * TILE_SIZE),
							TILE_SIZE, TILE_SIZE, null);
				}
			}
		}
	}
		
	// Getter methods
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}

	public int getRotation() {
		return this.rotation;
	}
	
	public int getPossibleRotations() {
		return this.possibleRotations;
	}
	
	public int getSpriteID() {
		return this.spriteID;
	}

	public int[][][] getPieceTiles() {
		return this.pieceTiles;
	}
	
	public boolean isFalling() {
		return this.falling;
	}

	// Access the spritesheet
	public static Tetrominos getBlockSprites() {
		return blockSprites;
	}
	
	public BufferedImage getPieceSprite() {
		return this.pieceSprite;
	}
			
	// Setter methods
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setFalling(boolean falling) {
		this.falling = falling;
	}
	
	public void setRotation(int pRotation) {
		this.rotation = pRotation;
	}
}
