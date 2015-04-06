package com.phixyn.tetrisphix.piece;

/**
 * Subclass of Piece representing the L-shaped Tetromino.
 * 
 * @author	Alpeche Pancha
 * @version 1.0.5, 19 December 2014
 * @see		Piece
 */
public class LPiece extends Piece {
	/**
	 * Calls the constructor of Piece and initializes the pieceTiles array with
	 * the 4 by 4 representation of the L-shaped Tetromino.
	 */
	public LPiece() {
		super();
		// This piece has 4 possible rotations according to TGM, using the ARS
		this.possibleRotations = 4;
		
		this.pieceTiles = new int[][][] {
			/* Rotation 1 (UP) */
			{
				{0, 0, 0, 0},
			    {0, 0, 0, 1},
			    {0, 1, 1, 1},
			    {0, 0, 0, 0}
			},
			/* Rotation 2 (RIGHT) */
			{
				{0, 0, 0, 0},
			    {0, 0, 1, 0},
			    {0, 0, 1, 0},
			    {0, 0, 1, 1}
			},
			/* Rotation 3 (DOWN) */
			{
				{0, 0, 0, 0},
			    {0, 0, 0, 0},
			    {0, 1, 1, 1},
			    {0, 1, 0, 0}
			},
			/* Rotation 4 (LEFT) */
			{
				{0, 0, 0, 0},
			    {0, 1, 1, 0},
			    {0, 0, 1, 0},
			    {0, 0, 1, 0}
		    }
		};
	}
}
