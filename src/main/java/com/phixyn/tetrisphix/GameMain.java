package com.phixyn.tetrisphix;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;

import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.phixyn.tetrisphix.gfx.BufferedImageLoader;
import com.phixyn.tetrisphix.gfx.FancyJButton;

/**
 * Main game class. Provides a main method used to run the game as an
 * application.
 * <p>
 * This class is responsible for displaying the main menu and switching between
 * the different scenes of the game (such as the highscores panel and the
 * Tetris game). It is a subclass of JPanel in which the different menu buttons
 * are displayed.
 * <p>
 * This class also holds a few static variables used by other classes to switch
 * between different scenes. For example, the Tetris class may want to display
 * the main menu after the game is over. It does so by using the sceneManager
 * object, which is an instance of the CardLayout layout manager, to change to
 * the main menu scene.
 * 
 * @author	Alpeche Pancha
 * @version 1.0.5, 19 December 2014
 * @see		Tetris
 * @see		HighScorePanel
 * @see		FancyJButton
 */
public class GameMain extends JPanel {
	
	// Serial Version UID
	private static final long serialVersionUID = 1L;
	
	// Title string for JFrame
	private final static String RELEASE = "(19 Dec 2014)[PUBLIC]<bin/105a>";
	private final static String VERSION = "v1.0.5 Alpha";
	private final static String TITLE = "TetrisPhix " + VERSION;
	
	
	// Window size
	private final static Dimension WINDOW_SIZE = new Dimension(800, 500);
	
	// Color and Font objects used to format the menu buttons
	// private final Dimension BUTTON_SIZE = new Dimension(120, 50);
	private final Color BUTTON_BG_COLOR = new Color(0, 0, 0, 0);
	private final Color BUTTON_FG_COLOR = Color.WHITE;
	private final Font BUTTON_FONT = new Font(Font.MONOSPACED, Font.BOLD, 24);
	
	// File name of the background image
	// private final String BG_IMAGE_FILE = "/bg_menu.png";
	private FileInputStream BG_IMAGE_FILE;
	// Background image
	private BufferedImage bgImage;
	
	// CardLayout object used to switch between the different JPanels held
	// in the scenes JPanel.
	public static CardLayout sceneManager = new CardLayout();
	
	// This JPanel will hold all of the game scenes such as the Tetris game,
	// the highscores panel and the main menu itself. It is a container panel
	// that uses the CardLayout manager to display different panels at a time
	public static JPanel scenes = new JPanel(sceneManager);
			
	/**
	 * Constructor for the class. Sets up the game by initializing all of the
	 * different components of the game, such as the highscores panel and the
	 * Tetris panel.  
	 * <p>
	 * Sets up the JPanel, adds the different game scenes to the static scenes
	 * object and adds the menu buttons to this JPanel.
	 * <p>
	 * Suppresses serial warnings from the anonymous AbstractAction classes used
	 * for the actionListeners of the buttons.
	 * 
	 * @see ScoreManager
	 * @see FancyJButton
	 */
	@SuppressWarnings("serial")
	public GameMain() {		
		// Set up this JPanel
		// super(new GridBagLayout());
		//EasyJLabel versionLabel = new EasyJLabel("test");
		//add(versionLabel);
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		setSize(WINDOW_SIZE);
		setBackground(Color.BLACK);
		setFocusable(true);
		/*
		try {
			this.BG_IMAGE_FILE = new FileInputStream("res/bg_menu.png");
		} catch (FileNotFoundException ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}*/
		// Load background image
		BufferedImageLoader loader = new BufferedImageLoader();
		System.out.println("[INFO] Loading bg_menu.png");
		// bgImage = loader.loadImage(this.BG_IMAGE_FILE);
		bgImage = loader.loadImage("/images/bg_menu.png");
		System.out.println("[INFO] Done.");
		
		// TODO change this comment. Create a new highscore file if one doesn't exist
		// ScoreManager scoreManager = new ScoreManager();
		// scoreManager.createScoreFile();
		
		// Initialize our Tetris game and add it to our scenes
		final Tetris TETRIS = new Tetris();
		scenes.add(TETRIS, "Tetris");
		
		// Initialize the highscore panel and add it to our scenes
		// It is important that this is initialized after Tetris,
		// because Tetris initializes the ScoreManager.
		HighScorePanel highScorePanel = new HighScorePanel();
		scenes.add(highScorePanel, "HighScores");
		
		// Define and format our menu buttons
		// Classic mode button
		FancyJButton classicButt = new FancyJButton("Classic Mode");
		
		// Add an action listener to this button
		classicButt.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				// When the button is pressed, we should set the game mode to
				// classic, start the game and show the scene.
				sceneManager.show(scenes, "Tetris");
				TETRIS.gameMode = "Classic";
				TETRIS.start();
			}
		});

		// Define grid bag layout constraints and add our first button to this panel
		gbc.insets = new Insets(10, 0, 10, 0);
		gbc.gridx = 0;
		gbc.gridy = 0;
		add(classicButt, gbc);
		
		// Hardcore mode button (I'm sorry for the name)
		FancyJButton hardButt = new FancyJButton("Hardcore Mode");

		// Add an action listener for this button, similar to the classic button
		hardButt.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				sceneManager.show(scenes, "Tetris");				
				TETRIS.gameMode = "Hardcore";
				TETRIS.start();
			}
		});
		
		// Set grid bag constraints and add button to panel
		gbc.gridy = 1;
		add(hardButt, gbc);
		
		// Highscores button
		FancyJButton highButt = new FancyJButton("Highscores");
		
		// Action listener that shows us the highscore panel scene
		highButt.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				// Show the highscores panel
				sceneManager.show(scenes, "HighScores");
			}
		});
		
		// Adding to panel
		gbc.gridy = 2;
		add(highButt, gbc);
		
		// Last button, quit game
		FancyJButton quitButt = new FancyJButton("Quit Game");
		
		// Action listener for exiting
		quitButt.addActionListener(new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				System.exit(0);
			}
		});
		
		// You know the drill
		gbc.gridy = 3;
		add(quitButt, gbc);
	}
	
	/**
	 * Draws the background image of the main menu.
	 * 
	 * @param g - Graphics object to be used for drawing.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(bgImage, 0, 0, bgImage.getWidth(), bgImage.getHeight(), null);
	}
	
	/**
	 * Main method to run the game as an application. Creates a new JFrame used
	 * to display the scenes JPanel. The JFrame drawing is done from the event
	 * dispatch thread (Swing's EDT) for thread safety purposes. Initially,
	 * the menu scene is shown. Different classes will change the game scene
	 * by using the static objects provided in this class.
	 * 
	 * @see javax.swing.SwingUtilities
	 */
	public static void main(String[] args) {
		// Use the event dispatch thread to build the UI for thread-safety
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// TODO move/remove this
				System.out.println(TITLE + " " + RELEASE + "\n");
				JFrame frame = new JFrame(TITLE);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				frame.setResizable(false);
				// Add game scene to our scenes JPanel and show it using the
				// CardLayout manager
				GameMain game = new GameMain();
				scenes.add(game, "Menu");
				sceneManager.show(scenes, "Menu");
				//sceneManager.show(scenes, "Tetris");
				
				frame.setContentPane(scenes);
				frame.pack();
				frame.setVisible(true);
				// Center the application window
				frame.setLocationRelativeTo(null);
			}
		});
	}
}
