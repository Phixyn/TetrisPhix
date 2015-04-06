package com.phixyn.tetrisphix;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import com.phixyn.tetrisphix.piece.Piece;

/**
 * Class responsible for managing and responding to player input, both from
 * the keyboard and the mouse. Swing keybindings are used for keyboard input,
 * by adding actions to the component's action map and specifying the keys
 * that will trigger these actions in the component's input map.
 * A MouseListener implementation is used to handle mouse input.
 * 
 * @author Alpeche Pancha
 * @version 1.0.5, 19 December 2014
 * @see Board
 * @see Piece
 */
public class InputManager extends AbstractAction implements ActionListener, MouseListener {
	
	// Serial Version UID
	private static final long serialVersionUID = 1L;
	
	// Objects for the component and its input and action maps
	private Board board;
	InputMap inputMap;
	ActionMap actionMap;
	
	/**
	 * Class constructor, initializes our component object, retrieves its maps
	 * and adds all the actions to the maps.
	 * 
	 * @param board - the component object to which actions will be added.
	 */
	public InputManager(Board board) {
		this.board = board;
		
		// Retrieve the component's input and action maps. In this case, we're
		// adding actions to our board JPanel, so we retrieve the maps from it
		this.inputMap = board.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		this.actionMap = board.getActionMap();
		
		// Add each action and its corresponding key to the maps
		// As an alternative, these methods could be called from the component
		// that uses this class, as the method is public.
		addAction("A", "LEFT");
		addAction("D", "RIGHT");
		addAction("S", "DOWN");
		//addAction("Z", "ROTATE");
		addAction("R", "ROTATE");
		addAction("P", "PAUSE");
		addAction("Q", "QUIT");
	}
	
	/**
	 * Adds the specified key and action to the component's input and action
	 * maps, respectively.
	 * 
	 * @param keyName - a string denoting which key will be used for the action
	 * @param actionName - a string denoting the name of the action
	 */
	public void addAction(String keyName, String actionName) {
		this.inputMap.put(KeyStroke.getKeyStroke(keyName), "pressed " + actionName);
		// The second argument is of type AbstractAction, which we subclass
		this.actionMap.put("pressed " + actionName, this);
	}
	
	/**
	 * Overrides ActionListener's actionPerformed method. 
	 * <p>
	 * Checks for key presses and performs the appropriate actions if the
	 * player presses a key that corresponds to an action. Different
	 * methods from the Piece class are used to get information about the
	 * game's current piece. Additionally, the Board's canMove method is
	 * used to check for collision.
	 * 
	 * @param ev - an ActionEvent object containing information about the
	 * keyboard event that has occurred.
	 */
	@Override
	public void actionPerformed(ActionEvent ev) {
		// The current piece falling down in the board
		Piece currentPiece = board.currentPiece;
		String key = ev.getActionCommand();
		
		/* Tetromino movement - we use the board's canMove method in order to
		 * check if the current piece can move. We also need to check the
		 * board's state in order to ensure that pieces don't move when the
		 * game is paused.
		 */ 
		if (key.equals("a") &&
				board.isStarted &&
				!board.isPaused &&
				board.canMove(currentPiece.getRotation(), currentPiece.getX() - 1, currentPiece.getY())) {
			currentPiece.setX(currentPiece.getX() - 1);
		}
		
		if (key.equals("d") &&
				board.isStarted &&
				!board.isPaused &&
				board.canMove(currentPiece.getRotation(), currentPiece.getX() + 1, currentPiece.getY())) {
			currentPiece.setX(currentPiece.getX() + 1);
		}
		
		if (key.equals("s") &&
				board.isStarted &&
				!board.isPaused &&
				board.canMove(currentPiece.getRotation(), currentPiece.getX(), currentPiece.getY() + 1)) {
			while (board.canMove(currentPiece.getRotation(), currentPiece.getX(), currentPiece.getY() + 1)) {
				currentPiece.setY(currentPiece.getY() + 1);
			}
		}
		
		/* Rotate the piece. This time we pass a rotation to the board's
		 * canMove method in order to check if the rotation is possible.
		 * The way we calculate the next rotation is by performing a modulus
		 * operation between rotation + 1 and the piece's possible rotations.
		 * Because this will always be a value between 0 and the piece's
		 * possible rotations, we will always cycle through every possible
		 * rotation (i.e. when a L-Piece rotation reaches 4, its next rotation
		 * will be 0). */
		if (key.equals("r") &&
				board.isStarted &&
				!board.isPaused &&
				currentPiece.getPossibleRotations() > 0 &&
				board.canMove((currentPiece.getRotation() + 1) % currentPiece.getPossibleRotations(), currentPiece.getX(), currentPiece.getY())) {
			currentPiece.setRotation((currentPiece.getRotation() + 1) % currentPiece.getPossibleRotations());
		}
		
		// Pause or unpause the game
		if (key.equals("p") && board.isStarted) {
			currentPiece.setFalling(!currentPiece.isFalling());
			board.isPaused = !board.isPaused;
			if (board.timer.isRunning()) board.timer.stop();
			else board.timer.start();
		}
		
		// Quit the game. Show a confirmation dialog before doing so
		if (key.equals("q")) {
			board.isPaused = true;
			if (board.timer.isRunning()) board.timer.stop();
			Object[] options = {"Yes", "No", "Main Menu"};
			Object title = "Are you sure you want to quit?";
			int confDialog = JOptionPane.showOptionDialog(board, title, "Quit game?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);
			if (confDialog == JOptionPane.YES_OPTION) {
				System.exit(0);
			}
			else if (confDialog == JOptionPane.CANCEL_OPTION) {
				// Go back to the main menu
				board.isPaused = false;
				board.stop();
				GameMain.sceneManager.show(GameMain.scenes, "Menu");
			}
			else {
				board.isPaused = false;
				board.timer.start();
			}
		}
		
		// Praise the lord, this one line fixed everything
		board.repaint();
	}

	/**
	 * Overrides MouseListener's mouseClicked method.
	 * <p>
	 * Attempts to moves the current piece based on the mouse input from the
	 * player. Left clicking causes the current piece to move left, right
	 * clicking moves the piece right, and middle button click rotates the
	 * piece.
	 * <p>
	 * Different methods from the Piece class are used to get information about the
	 * game's current piece. Additionally, the Board's canMove method is
	 * used to check for collision.
	 * 
	 * @param ev - a MouseEvent object containing information about the mouse
	 * event that has occurred.
	 */
	@Override
	public void mouseClicked(MouseEvent ev) {
		// The current piece falling down in the board
		Piece currentPiece = board.currentPiece;
		
		// Move left
		if (ev.getButton() == MouseEvent.BUTTON1 &&
			!board.isPaused &&
			board.canMove(currentPiece.getRotation(), currentPiece.getX() - 1, currentPiece.getY())) {
			currentPiece.setX(currentPiece.getX() - 1);
		}

		// Rotate piece
		if (ev.getButton() == MouseEvent.BUTTON2 &&
			board.isStarted &&
			!board.isPaused &&
			currentPiece.getPossibleRotations() > 0 &&
			board.canMove((currentPiece.getRotation() + 1) % currentPiece.getPossibleRotations(), currentPiece.getX(), currentPiece.getY())) {
			currentPiece.setRotation((currentPiece.getRotation() + 1) % currentPiece.getPossibleRotations());
		}
		
		// Move right
		if (ev.getButton() == MouseEvent.BUTTON3 &&
			board.isStarted &&
			!board.isPaused &&
			board.canMove(currentPiece.getRotation(), currentPiece.getX() + 1, currentPiece.getY())) {
			currentPiece.setX(currentPiece.getX() + 1);
		}
	}

	/**
	 * Unimplemented overridden method from the MouseListener interface.
	 * 
	 * @param ev - a MouseEvent object containing information about the mouse
	 * event that has occurred.
	 */
	@Override
	public void mouseEntered(MouseEvent ev) { }

	/**
	 * Unimplemented overridden method from the MouseListener interface.
	 * 
	 * @param ev - a MouseEvent object containing information about the mouse
	 * event that has occurred.
	 */
	@Override
	public void mouseExited(MouseEvent ev) { /* unused */ }
	
	/**
	 * Unimplemented overridden method from the MouseListener interface.
	 * 
	 * @param ev - a MouseEvent object containing information about the mouse
	 * event that has occurred.
	 */
	@Override
	public void mousePressed(MouseEvent ev) { /* unused */ }

	/**
	 * Unimplemented overridden method from the MouseListener interface.
	 * 
	 * @param ev - a MouseEvent object containing information about the mouse
	 * event that has occurred.
	 */
	@Override
	public void mouseReleased(MouseEvent ev) { /* unused */ }
}
