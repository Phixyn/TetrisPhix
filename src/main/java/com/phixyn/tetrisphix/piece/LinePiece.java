package com.phixyn.tetrisphix.piece;

/**
 * Subclass of Piece representing the I-shaped Tetromino.
 * 
 * @author	Alpeche Pancha
 * @version 1.0.5, 19 December 2014
 * @see		Piece
 */
public class LinePiece extends Piece {
	/**
	 * Calls the constructor of Piece and initializes the pieceTiles array with
	 * the 4 by 4 representation of the I-shaped Tetromino.
	 */
	public LinePiece() {
		super();
		// This piece has 2 possible rotations according to TGM, using the ARS
		this.possibleRotations = 2;
		
		this.pieceTiles = new int[][][] {
			/* Rotation 1 (DOWN) */
			{
				{0, 0, 0, 0},
			    {0, 0, 0, 0},
			    {1, 1, 1, 1},
			    {0, 0, 0, 0}
			},
			/* Rotation 2 (UP) */
			{
				{0, 1, 0, 0},
			    {0, 1, 0, 0},
			    {0, 1, 0, 0},
			    {0, 1, 0, 0}
			}
		};	
	}
}
