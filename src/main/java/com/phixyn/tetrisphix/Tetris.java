package com.phixyn.tetrisphix;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import com.phixyn.tetrisphix.gfx.BufferedImageLoader;

/**
 * Tetris is a JPanel subclass responsible for drawing all the panels that make
 * up the Tetris game. It also manages communication between these panels,
 * allowing information about the game's state to be shared between the panels.
 * <p>
 * The main purpose of this class is to update the game panels and redraw them.
 * This class does not deal with game logic. This class deals solely with the 
 * Tetris functionality of the game, and not other game scenes such as the 
 * main menu and the highscores panel.
 * 
 * @author	Alpeche Pancha
 * @version 1.0.5, 19 December 2014
 * @see Board
 * @see LeftPanel
 * @see RightPanel
 */
public class Tetris extends JPanel {
	
	// Serial Version UID
	private static final long serialVersionUID = 1L;
	
	// JFrame dimensions
	private final static int WIDTH = 800;
	private final static int HEIGHT = 500;
	private final static Dimension SIZE = new Dimension(WIDTH, HEIGHT);
	
	// Game mode and game state information
	public String gameMode;
	private String gameState;
	
	// Tetris game panels
	private Board board;
	private LeftPanel leftPanel;
	private RightPanel rightPanel;
	
	private int gameLevel = 1;
	
	private ScoreManager scoreManager = new ScoreManager();
	
	private BufferedImageLoader imageLoader = new BufferedImageLoader();
	
	// BufferedImage array containing the background images for each level
	private BufferedImage[] bgImageArray = new BufferedImage[10];
	
	// BufferedImage object containing the current background image
	private BufferedImage bgImage;
	
	/**
	 * Initializes and sets up the container JPanel. All of the JPanels that
	 * make up the game are added into this container JPanel.
	 * <p>
	 * Defines a Swing Timer wherein the update method is repeatedly called.
	 */
	public Tetris() {	
		// Set up the container JPanel
		// super();
		loadLevelBgImages();
		setMinimumSize(SIZE);
		setMaximumSize(SIZE);
		setPreferredSize(SIZE);
		setSize(SIZE);
		setBackground(Color.BLACK);
		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		// Create instances of the game panels
		this.leftPanel = new LeftPanel();
		this.board = new Board();
		this.rightPanel = new RightPanel();
		// Set initial next piece so that it won't be null
		this.rightPanel.setNextPiece(this.board.nextPiece);
		
		// Set constraints for the panels and add them to this JPanel
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 0;
		gbc.gridy = 0;
		// gbc.gridheight = 1;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.weightx = 0.5;
		gbc.weighty = 0.5;
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		// gbc.ipadx = 20;		// internal padding
		add(this.leftPanel, gbc);
		
		gbc.fill = GridBagConstraints.NONE;
		gbc.gridx = 1;
		gbc.gridwidth = 2;
		// gbc.gridheight = 2;
		gbc.ipadx = 0;
		gbc.weightx = 0.5;
		gbc.weighty = 0.5;
		gbc.anchor = GridBagConstraints.PAGE_START;
		// gbc.ipady = 10; // TODO new
		add(this.board, gbc);
		
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 3;
		gbc.gridwidth = 1;
		// gbc.gridheight = 1;
		// gbc.ipadx = 20;
		gbc.weightx = 0.5;
		gbc.weighty = 0.5;
		gbc.anchor = GridBagConstraints.FIRST_LINE_END;
		add(this.rightPanel, gbc);
		
		/* Create a Swing Timer which will be used to repeatedly call the
		 * update method and allow the game panels to be updated. */
		Timer timer = new Timer(100, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent ev) {
				update();
			}
		});
		timer.start();
		
		this.gameState = "INITIALIZED";
	}
	
	/**
	 * Loads all of the background images for each game level and stores them
	 * in our bgImageArray. Uses the BufferedImageLoader class to load images.
	 * 
	 * @see BufferedImageLoader
	 */
	private void loadLevelBgImages() {
		// TODO use imageLoader instead of creating a new instance
		BufferedImageLoader loader = new BufferedImageLoader();
		System.out.println("[INFO] Loading level images, please wait ...");
		try {
			// bgImageArray[0] = loader.loadImage(new FileInputStream("res/bg_lvl1.jpg"));
			bgImageArray[0] = loader.loadImage("/images/bg_lvl1.jpg");
			// TODO: if img != null ...
			System.out.println("[INFO] Loaded image 1/10 ...................");
			bgImageArray[1] = loader.loadImage("/images/bg_lvl2.jpg");
			System.out.println("[INFO] Loaded image 2/10 ...................");
			bgImageArray[2] = loader.loadImage("/images/bg_lvl3.jpg");
			System.out.println("[INFO] Loaded image 3/10 ...................");
			bgImageArray[3] = loader.loadImage("/images/bg_lvl4.jpg");
			System.out.println("[INFO] Loaded image 4/10 ...................");
			bgImageArray[4] = loader.loadImage("/images/bg_lvl5.jpg");
			System.out.println("[INFO] Loaded image 5/10 ...................");
			bgImageArray[5] = loader.loadImage("/images/bg_lvl6.jpg");
			System.out.println("[INFO] Loaded image 6/10 ...................");
			bgImageArray[6] = loader.loadImage("/images/bg_lvl7.png");
			System.out.println("[INFO] Loaded image 7/10 ...................");
			bgImageArray[7] = loader.loadImage("/images/bg_lvl8.jpg");
			System.out.println("[INFO] Loaded image 8/10 ...................");
			bgImageArray[8] = loader.loadImage("/images/bg_lvl9.jpg");
			System.out.println("[INFO] Loaded image 9/10 ...................");
			bgImageArray[9] = loader.loadImage("/images/bg_lvl10.jpg");
			System.out.println("[INFO] Loaded image 10/10 .................");
		// } catch (FileNotFoundException ex) {
		} catch (Exception ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
		System.out.println("[INFO] Level images loaded.");
		
		// Set the current background image to be level 1's image
		this.bgImage = bgImageArray[0];
	}

	/**
	 * Starts the game by starting the Swing Timer on the board panel.
	 * This method is called from the main menu, which also sets the gameMode
	 * flag according to the game mode the user chose from the menu. The game
	 * mode information is then shared with the game panels.
	 * 
	 * @see MainMenu
	 */
	public void start() {
		gameState = "PLAYING";
		// Start audio
		// SoundTest mySoundTest = new SoundTest();
		leftPanel.setModeLabel(this.gameMode);
		board.gameMode = this.gameMode;
		board.start();
	}

	/**
	 * Stop! Hammer time.
	 */
	public void stop() {
		gameState = "INITIALIZED";
		board.stop();
	}
	
	/**
	 * Shows a pop up dialog where the player can enter their name in order
	 * to save their score.
	 * 
	 * @see ScoreManager
	 * @see JOptionPane
	 */
	private void showHighScoreDialog() {
		// Show highscore dialog
		Object text = "Score: " +
				board.playerScore + "\nEnter your name:";
		Object initVal = "Player";
		boolean validName = false;
		
		while (!validName) {
			Object playerName = JOptionPane.showInputDialog(this, text, "Game over!", JOptionPane.QUESTION_MESSAGE, null, null, initVal);
			
			// Validate player name
			if (playerName == null) {
				validName = true;
			}
			// Name can't be empty
			else if (playerName.toString().equals("")) {
				JOptionPane.showMessageDialog(this, "Please enter a name!");
			}
			else if (playerName.toString().length() > 20) {
				JOptionPane.showMessageDialog(this, "Your name is too long! Maximum 20 characters.");
			}
			else {
				validName = true;
				scoreManager.addScore(playerName.toString(), board.playerScore, board.gameLevel, gameMode);
				HighScorePanel.updateScores();
			}
		}
	}
	
	/**
	 * Shows a pop over message with various post game over options.
	 * 
	 * @see JOptionPane
	 */
	private void showGameOverDialog() {
		// Show game over dialog
		final Object[] GAME_OVER_OPTIONS = {"New Game", "Main Menu", "Quit Game"};
		final JOptionPane GAME_OVER_PANE = new JOptionPane(
				"Game over man, game over!", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, GAME_OVER_OPTIONS, GAME_OVER_OPTIONS[0]);
		
		final JDialog GAME_OVER_DIALOG = new JDialog();
		GAME_OVER_DIALOG.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		GAME_OVER_DIALOG.setContentPane(GAME_OVER_PANE);
		GAME_OVER_DIALOG.setResizable(false);
		GAME_OVER_DIALOG.setTitle("Game over!");
		
		GAME_OVER_PANE.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent ev) {
				String prop = ev.getPropertyName();
				
				if (GAME_OVER_DIALOG.isVisible() &&
						ev.getSource() == GAME_OVER_PANE &&
						prop.equals(JOptionPane.VALUE_PROPERTY)) {
					
					if (GAME_OVER_PANE.getValue().equals(GAME_OVER_OPTIONS[0])) {
						// New Game
						start();
					}
					else if (GAME_OVER_PANE.getValue().equals(GAME_OVER_OPTIONS[1])) {
						// Back to main menu
						GameMain.sceneManager.show(GameMain.scenes, "Menu");
					}
					else if (GAME_OVER_PANE.getValue().equals(GAME_OVER_OPTIONS[2])) {
						// Rage quit
						System.exit(0);
					}
					
					GAME_OVER_DIALOG.setVisible(false);
				}
			}
		});
		
		GAME_OVER_DIALOG.pack();
		GAME_OVER_DIALOG.setLocationRelativeTo(null);
		GAME_OVER_DIALOG.setVisible(true);
	}
	
	/**
	 * Manages communication between all the game panels and allows
	 * them to share information about the game's state. Different components
	 * of these panels are updated as the game state and data changes.
	 */
	public void update() {
		if (gameState.equals("PLAYING")) {
			leftPanel.setLevelLabel(board.gameLevel);
			leftPanel.setLinesLabel(board.linesClear);
			leftPanel.repaint();
			
			rightPanel.setScoreLabel(board.playerScore);
			rightPanel.setNextPiece(board.nextPiece);
			rightPanel.repaint();
			
			// Update information about the current level
			// If the game level has changed, update the background image
			if (board.gameLevel != this.gameLevel) {
				this.gameLevel = board.gameLevel;
				/* Make sure we don't try to access anything with index greater
				 * than 9. Even if the player makes it to level 11 and above,
				 * we only have 10 images, so we'll stick to that. I made the
				 * last image pretty enough to last for a while. */ 
				if (board.gameLevel <= 10) {
					bgImage = bgImageArray[board.gameLevel - 1];
					repaint();
				}
			}			
			
			// Game over handling
			if (board.isOver) {
				gameState = "INITIALIZED";
				showHighScoreDialog();
				showGameOverDialog();
			}
			
			// TODO for windows issue: maybe repaint() here?
			// repaint();
		}
	}
	
	/**
	 * Draws the current level's background image.
	 * 
	 * @param g - Graphics object to be used for drawing.
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.drawImage(bgImage, 0, 0, bgImage.getWidth(), bgImage.getHeight(), null);
	}
}
