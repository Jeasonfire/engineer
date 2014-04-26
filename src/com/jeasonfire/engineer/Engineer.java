package com.jeasonfire.engineer;

import java.awt.Dimension;

import javax.swing.JApplet;
import javax.swing.JFrame;

public class Engineer extends JApplet {
	private static final long serialVersionUID = 1L;
	
	private static int GWIDTH = 160, GHEIGHT = 90, SCALE = 4;
	private static Game game;
	private static JFrame frame;
	
	public void init() {
		game = new Game(GWIDTH, GHEIGHT);
		add(game);
		Dimension size = new Dimension(GWIDTH * SCALE, GHEIGHT * SCALE);
		setMinimumSize(size);
		setPreferredSize(size);
		setMaximumSize(size);
		game.requestFocus();
	}
	
	public void start() {
		game.start();
	}
	
	public void stop() {
		game.stop();
	}
	
	public static void main(String[] args) {
		frame = new JFrame();
		game = new Game(GWIDTH, GHEIGHT);
		Dimension size = new Dimension(GWIDTH * SCALE, GHEIGHT * SCALE);
		game.setMinimumSize(size);
		game.setPreferredSize(size);
		game.setMaximumSize(size);
		frame.add(game);
		frame.pack();
		frame.setResizable(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Engineer");
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		game.requestFocus();
		game.start();
	}
}
