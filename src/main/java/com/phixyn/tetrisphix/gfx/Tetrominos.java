package com.phixyn.tetrisphix.gfx;

import java.awt.image.BufferedImage;

import com.phixyn.tetrisphix.piece.Piece;

/**
 * This class handles the Tetrominos spritesheet image and splits it into the
 * different sprites that are contained in that spritesheet with the
 * loadSprites method. It is used by the Piece class to retrieve a sprite
 * chosen at random.
 * 
 * @author Alpeche Pancha
 * @version 1.0.5, 19 December 2014
 * @see BufferedImageLoader
 * @see Piece
 */
public class Tetrominos {

	// The spritesheet image
	private BufferedImage spritesheet;
	// An array of BufferedImage objects containing all the sprites in the sheet
	private BufferedImage[] sprites = new BufferedImage[7];
	
	/**
	 * Constructor method that attempts to load the spritesheet image file.
	 * If it loads successfully, then the we retrieve all the sprites by
	 * calling the loadSprites method.
	 */
	public Tetrominos() {
		// Loader object used to load the spritesheet image
		BufferedImageLoader loader = new BufferedImageLoader();
		
		try {
			// spritesheet = loader.loadImage(new FileInputStream("res/tetromino_spritesheet.png"));
			// TODO fix this block of code (no try except needed?)
			spritesheet = loader.loadImage("/images/tetromino_spritesheet.png");
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			loadSprites();
		}
	}
	
	/**
	 * Retrieves all the sprites in the spritesheet by calling the getSubimage
	 * method. Each sprite is 25 by 25, and arranged in a single row.
	 * Therefore, each sub image's X coordinate will be a multiple of 25.
	 */
	private void loadSprites() {
		sprites[0] = spritesheet.getSubimage(0, 0, 25, 25);
		sprites[1] = spritesheet.getSubimage(25, 0, 25, 25);
		sprites[2] = spritesheet.getSubimage(50, 0, 25, 25);
		sprites[3] = spritesheet.getSubimage(75, 0, 25, 25);
		sprites[4] = spritesheet.getSubimage(100, 0, 25, 25);
		sprites[5] = spritesheet.getSubimage(125, 0, 25, 25);
		sprites[6] = spritesheet.getSubimage(150, 0, 25, 25);
	}
	
	/**
	 * Getter method for the sprites array.
	 * 
	 * @return sprites[] - a BufferedImage array containing all the sprites in
	 * this spritesheet.
	 */
	public BufferedImage[] getSprites() {
		return this.sprites;
	}
}
