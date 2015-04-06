package com.phixyn.tetrisphix.gfx;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;

/**
 * This class provides an easy way to make custom JLabels for the game.
 * 
 * @author	Alpeche Pancha
 * @version 1.0.5, 19 December 2014
 * @see javax.swing.JLabel
 */
public class EasyJLabel extends JLabel {
	
	// Serial Version UID
	private static final long serialVersionUID = 1L;
	// Label text
	private String text;
	// Default color
	private Color color = new Color(255, 255, 255);
	// Default font
	private Font font = new Font(Font.SANS_SERIF, Font.BOLD, 16);
	// Default alignment
	private int alignment = javax.swing.SwingConstants.CENTER;
	
	/**
	 * Constructor method that sets the text of the JLabel.
	 * Sets all the other JLabel properties to the default ones.
	 * 
	 * @param text - the JLabel text
	 */
	public EasyJLabel(String text) {
		this.text = text;
		setText(text);
		setFont(this.font);
		setForeground(this.color);
		setHorizontalAlignment(this.alignment);
	}
	
	/**
	 * Constructor method that sets the text and color of the JLabel.
	 * Sets all the other JLabel properties to the default ones.
	 * 
	 * @param text - the JLabel text
	 * @param color - the JLabel text color
	 */
	public EasyJLabel(String text, Color color) {
		this.text = text;
		this.color = color;
		setText(text);
		setFont(this.font);
		setForeground(color);
		setHorizontalAlignment(this.alignment);
	}
	
	/**
	 * Constructor method that sets all of the JLabels properties to the
	 * speficied ones.
	 * 
	 * @param text - the JLabel text
	 * @param color - the JLabel text color
	 * @param font - the JLabel font object
	 * @param alignment - the JLabel text alignment
	 */
	public EasyJLabel(String text, Color color, Font font, int alignment) {
		this.text = text;
		this.color = color;
		this.font = font;
		this.alignment = alignment;
		
		setText(text);
		setFont(font);
		setForeground(color);
		setHorizontalAlignment(alignment);
	}
}
