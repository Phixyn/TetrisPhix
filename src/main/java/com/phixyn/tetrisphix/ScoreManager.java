package com.phixyn.tetrisphix;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

/**
 * Class representing a single score. Contains information such as the player's
 * name, score, game level and game mode. Provides a method to retrieve the 
 * score.
 * 
 * @author	Alpeche Pancha
 * @version 1.0.5, 19 December 2014
 * @see 	ScoreManager
 */
class Score {

	// Score information
	private String playerName;
	private int score;
	private int level;
	private String gameMode;
	
	/** Constructor method. Intializes all class variables.
	 * 
	 * @param name - 	the player's name
	 * @param score - 	the player's score
	 * @param level - 	the level reached by the player
	 * @param mode - 	the game mode played
	 */
	public Score(String name, int score, int level, String mode) {
		this.playerName = name;
		this.score = score;
		this.level = level;
		this.gameMode = mode;
	}
	
	/**
	 * Getter method for the player's score.
	 * 
	 * @return score - the player's score.
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * Returns a string representation of this score object.
	 * 
	 * @return string containing a single line of text with information about
	 * the score.
	 */
	public String toString() {
		String score = this.playerName;
		score += "\t" + this.score;
		score += "\t" + this.level;
		score += "\t" + this.gameMode;
		return score;
	}
	
}

/** 
 * Class responsible for managing the highscores. Provides methods
 * that interact with the highscores file.
 * 
 * @author	Alpeche Pancha
 * @version 1.0.0, 08 December 2014
 * @see 	Score 
 * @see 	HighScorePanel
 */
public class ScoreManager implements Comparator<Score> {
	
	// The ArrayList object used to temporarily store scores and sort them
	private ArrayList<Score> scores = new ArrayList<Score>();
	
	// Name of the highscores text file
	public static final String SCORES_FILE = System.getProperty("user.home") + File.separator + "TetrisPhix.dat";
	
	/**
	 * Constructor method, simply calls the updateScoreArray method.
	 */
	public ScoreManager() {
		createScoreFile();
		updateScoreArray();
	}
		
	/**
	 * Checks if the highscores file is present. If it is not, a new one is
	 * created, with default scores.
	 */
	public void createScoreFile() {
		// File object for the text file
		File scoresFile = new File(SCORES_FILE);
		
		// If the file doesn't exist, we create a new one, with default scores
		if (!scoresFile.exists()) {
			System.out.println("[INFO] Highscores file not found. Creating a new one.");
			/*try {
				scoresFile.createNewFile();
			} catch (IOException ex) {
				System.out.println("[ERROR] Error creating highscores file.");
				System.out.println("[ERROR] Reason: IOException.");
				ex.printStackTrace();
			}*/
			
			// Add default scores to the scores array
			for (int i = 0; i < 10; i++) {
				scores.add(new Score("Player", (i + 1) * 100, i + 2, "Classic"));
			}
			
			// Write scores to file
			updateScoreFile();
		}
	}
	
	/**
	 * Load scores in file to a 2D Object array to be used in the highscore
	 * panel's JTable. The JTable needs a 2D Object array passed to its
	 * constructor, which is why we choose this data type.
	 * 
	 * @return a 2D Object array ready to be used by a JTable constructor
	 * @see HighScorePanel
	 */
	public static Object[][] loadScores() {
		File scoresFile = new File(ScoreManager.SCORES_FILE);
		
		// Objects for storing the score
		Object[][] scoreObj = new Object[10][4];
		// Object for storing the different elements of the score
		Object[] myTest = new Object[4];
		
		// Attempt to read the file and read the scores into the objects defined
		try {
			Scanner scoreScanner = new Scanner(scoresFile);
			while (scoreScanner.hasNext()) {
				for (int i = 0; i < 10; i++) {
					myTest[0] = scoreScanner.next() + "\t";
					myTest[1] = scoreScanner.nextInt() + "\t";
					myTest[2] = scoreScanner.nextInt() + "\t";
					myTest[3] = scoreScanner.next();
					for (int j = 0; j < 4; j++) {
						scoreObj[i][j] = myTest[j];
					}					
				}
			}
			scoreScanner.close();
		} catch (FileNotFoundException ex) {
			System.out.println("[ERROR] Error loading highscores file.");
			System.out.println("[ERROR] Reason: File not found.");
			System.out.println("[ERROR] Method: loadScores()");
		}
		
		return scoreObj;
	}
	
	/**
	 * Reads scores from the highscores file and writes them into our ArrayList
	 * object.
	 */
	private void updateScoreArray() {
		File scoresFile = new File(ScoreManager.SCORES_FILE);
		
		// Attempt to read from file and catch the appropriate exceptions
		try {
			Scanner scoreScanner = new Scanner(scoresFile);
			while (scoreScanner.hasNext()) {
				for (int i = 0; i < 10; i++) {
					scores.add(new Score(scoreScanner.next(), scoreScanner.nextInt(), scoreScanner.nextInt(), scoreScanner.next()));
				}
			}
			scoreScanner.close();
		} catch (FileNotFoundException ex) {
			System.out.println("[ERROR] Error writing to highscores file.");
			System.out.println("[ERROR] Reason: File not found.");
			System.out.println("[ERROR] Method: updateScoreArray()");
			// If the scores file was deleted during the execution of the game
			//System.out.println("[INFO] Creating new highscores file.");
			//createScoreFile();
		}
	}
	
	/**
	 * Write scores from our ArrayList object into the scores file.
	 */
	private void updateScoreFile() {
		// Collections.sort(scores, this);
		Collections.sort(scores, Collections.reverseOrder(this));
		
		// Attempt to write to file and catch the appropriate exceptions
		try {
			File scoresFile = new File(SCORES_FILE);
			PrintWriter scoresOut = new PrintWriter(scoresFile);
			for (int i = 9; i >= 0; i--) {
				scoresOut.println(scores.get(i));
			}
			scoresOut.flush();
			scoresOut.close();
		} catch (FileNotFoundException ex) {
			System.out.println("[ERROR] Error writing to highscores file.");
			System.out.println("[ERROR] Reason: File not found.");
			System.out.println("[ERROR] Method: updateScoreFile()");
		} catch (SecurityException ex) {
			System.out.println("[ERROR] Error writing to highscores file.");
			System.out.println("[ERROR] Reason: A security manager has denied file access permission.");
		}
	}
	
	/**
	 * Adds a new Score object to our ArrayList object.
	 */
	public void addScore(String name, int score, int level, String mode) {
		Score scoreToAdd = new Score(name, score, level, mode);
		Collections.sort(scores, Collections.reverseOrder(this));
		if (scores.size() == 10 && this.compare(scoreToAdd, scores.get(0)) <= 0) {
			scores.remove(0);	// remove lowest score
			scores.add(scoreToAdd);
			updateScoreFile();
		}
	}
	
	/**
	 * Comparator method used to compare scores and sort them.
	 */
	@Override
	public int compare(Score sc1, Score sc2) {	
		int score1 = sc1.getScore();
		int score2 = sc2.getScore();
		
		if (score1 > score2) {
			return -1;
		}
		else if (score1 < score2) {
			return 1;
		}
		else {
			return 0;
		}
	}
}
