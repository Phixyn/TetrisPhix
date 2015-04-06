package com.phixyn.tetrisphix;

import java.awt.Dimension;
import javax.swing.JApplet;
import javax.swing.SwingUtilities;

/**
 * Applet class for the game. Extends JApplet and provides an init method used
 * to run the game as an applet. Uses the static variables provided by the
 * GameMain class in order to set up the applet and its content pane.
 * 
 * @author	Alpeche Pancha
 * @version 1.0.5, 19 December 2014
 * @see		GameMain
 * @see		Tetris
 * @see		HighScorePanel
 */
public class GameApplet extends JApplet {

	// Serial Version UID
	private static final long serialVersionUID = 1L;
	
	// Applet dimension
	private static final int WIDTH = 800;
	private static final int HEIGHT = 500;
	private static final Dimension SIZE = new Dimension(WIDTH, HEIGHT);
	
	/**
	 * Calls the createGui method on Swing's event dispatching thread (EDT),
	 * for thread safety purposes.
	 * 
	 * @see javax.swing.SwingUtilities
	 */
	public void init() {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					createGui();
				}
			});
		} catch (Exception ex) { 
			System.err.println("[ERROR] Failed to create applet.");
			ex.printStackTrace();
		}
	}
	
	/**
	 * Creates the applet's GUI. Sets up the JApplet and creates a new
	 * instance of GameMain. Adds the game to the scenes and tells the
	 * scene manager to show it.
	 */
	private void createGui() {
		setMinimumSize(SIZE);
		setMaximumSize(SIZE);
		setPreferredSize(SIZE);
		setSize(SIZE);
		setFocusable(true);
		requestFocus();
		setVisible(true);
		
		GameMain game = new GameMain();
		GameMain.scenes.add(game, "Menu");
		GameMain.sceneManager.show(GameMain.scenes, "Menu");
		// Set this content pane to display the scenes JPanel
		setContentPane(GameMain.scenes);
	}	
}
