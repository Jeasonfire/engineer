package com.jeasonfire.engineer;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Input implements KeyListener, MouseListener, MouseMotionListener {
	public static boolean[] keys = new boolean[1024];
	public static boolean mouseDown = false;
	public static Point msp = new Point(0, 0);
	public static int lastKey = -1;
	private Game game;
	
	public Input(Game game) {
		this.game = game;
	}

	public void mouseDragged(MouseEvent e) {
		msp.x = e.getX() / (game.getWidth() / game.getGameWidth());
		msp.y = e.getY() / (game.getHeight() / game.getGameHeight());
	}

	public void mouseMoved(MouseEvent e) {
		msp.x = e.getX() / (game.getWidth() / game.getGameWidth());
		msp.y = e.getY() / (game.getHeight() / game.getGameHeight());
	}

	public void mousePressed(MouseEvent e) {
		mouseDown = true;
	}

	public void mouseReleased(MouseEvent e) {
		mouseDown = false;
	}

	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
		lastKey = e.getKeyCode();
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
		if (e.getKeyCode() == lastKey) {
			lastKey = -1;
		}
	}

	/**
	 * Unused
	 */

	public void keyTyped(KeyEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}
}
