package com.phixyn.tetrisphix.gfx;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JButton;

/**
 * This class provides an easy way to make custom JButtons for the game.
 * 
 * @author	Alpeche Pancha
 * @version 1.0.5, 19 December 2014
 * @see javax.swing.JButton
 */
public class FancyJButton extends JButton {
	
	// Serial Version UID
	private static final long serialVersionUID = 1L;
	
	// Button colors
	// private final Color BG_COLOR = new Color(0, 0, 0, 0);
	private final Color FG_COLOR = Color.WHITE;
	// Mouse over state color
	private final Color HOVER_COLOR = new Color(227, 163, 255);
	
	// Button text font
	private final Font BUTTON_FONT = new Font(Font.MONOSPACED, Font.BOLD, 24);
	
	// Button text
	private String buttonText;
	
	/**
	 * Constructor method that sets the text of the JButton.
	 * Sets all the other JButton properties to the default ones.
	 * 
	 * @param text - the JButton text
	 */
	public FancyJButton(String text) {
		this.buttonText = text;
		setText(text);
		//setText("<html>" + text + "&nbsp;<font color=#ffffdd> </font></html>");
		setText("<html><font color=#ffffdd>" + text + "</font></html>");
		//setOpaque(true);
		//setBackground(BG_COLOR);
		setForeground(FG_COLOR);
		setFont(BUTTON_FONT);
		setBorderPainted(false);
		setFocusPainted(false);
		setContentAreaFilled(false);
		setBorder(BorderFactory.createEmptyBorder());
	}
	
	/**
	 * Draws the JButton and renders its text in the appropriate color,
	 * depending on the state of the button.
	 * 
	 * @param g - a Graphics object used to draw graphics onto the screen.
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//Rectangle myRect = this.getVisibleRect();
		//this.getParent().repaint(myRect.x, myRect.y, myRect.width, myRect.height);
		if (getModel().isRollover()) {
			setForeground(HOVER_COLOR);
			setText("<html><font color=#e3a3ff>" + buttonText + "</font></html>");
		}
		else {
			setForeground(FG_COLOR);
			setText("<html><font color=#ffffdd>" + buttonText + "</font></html>");
		}
	}
}
