package com.phixyn.tetrisphix.piece;

/**
 * Subclass of Piece representing the Square-shaped Tetromino.
 * 
 * @author	Alpeche Pancha
 * @version 1.0.5, 19 December 2014
 * @see		Piece
 */
public class SquarePiece extends Piece {
	/**
	 * Calls the constructor of Piece and initializes the pieceTiles array with
	 * the 4 by 4 representation of the Square-shaped Tetromino.
	 */
	public SquarePiece() {
		super();
		// This piece has no possible rotations according to TGM, using the ARS
		this.possibleRotations = 0;
		
		this.pieceTiles = new int[][][] {
			/* Rotation 1 (EVERYTHING BECAUSE IT'S A SQUARE!) */
			{
			    {0, 0, 0, 0},
			    {0, 1, 1, 0},
			    {0, 1, 1, 0},
			    {0, 0, 0, 0}
			}
		};
	}		
}
