package com.phixyn.tetrisphix.gfx;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * This is a simple class used for loading/reading buffered images.
 * 
 * @author	Alpeche Pancha
 * @version 1.0.5, 19 December 2014
 */
public class BufferedImageLoader {
	
	// The BufferedImage object used to store the loaded image
	private BufferedImage image;
	
	/**
	 * Attempts to load an image file with the given URL.
	 * 
	 * @param path - the URL of the image file to be loaded
	 * @return image - a BufferedImage object of the image file loaded
	 */
	public BufferedImage loadImage(String path) {
		// Try to read the image and catch any thrown exceptions
		try {
			this.image = ImageIO.read(getClass().getResource(path));
		} catch (IOException ex) {
			System.out.println("[ERROR] Error loading image resource.");
			System.out.println("[ERROR] Reason: IOException.");
			ex.printStackTrace();
		} catch (IllegalArgumentException ex) {
			System.out.println("[ERROR] Error loading image resource.");
			System.out.println("[ERROR] Reason: IllegalArgumentException.");
			ex.printStackTrace();
		}
		return this.image;
	}

	public BufferedImage loadImage(FileInputStream imageFis) {
		// Try to read the image and catch any thrown exceptions
		try {
			this.image = ImageIO.read(imageFis);
		} catch (IOException ex) {
			System.out.println("[ERROR] Error loading image resource.");
			System.out.println("[ERROR] Reason: IOException.");
			ex.printStackTrace();
		} catch (IllegalArgumentException ex) {
			System.out.println("[ERROR] Error loading image resource.");
			System.out.println("[ERROR] Reason: IllegalArgumentException.");
			ex.printStackTrace();
		}
		return this.image;
	}
}
