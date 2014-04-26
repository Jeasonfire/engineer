package com.jeasonfire.engineer.graphics.screens;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import com.jeasonfire.engineer.Game;
import com.jeasonfire.engineer.Input;

public class MainMenuScreen extends Screen {
	private static int PLAY = 0, INFO = 1;
	private int selection = 0;
	private Rectangle playBox, infoBox;
	
	public MainMenuScreen(Game game) {
		super(game);
		playBox = new Rectangle(0, 48, width, 8);
		infoBox = new Rectangle(0, 60, width, 8);
	}

	public void draw() {
		drawString("Engineer", 16, 16, 2);
		if (selection == PLAY) {
			drawString("> Play <", width / 2 - 32, playBox.y);
		} else {
			drawString("Play", width / 2 - 16, playBox.y);
		}
		if (selection == INFO) {
			drawString("> Info <", width / 2 - 32, infoBox.y);
		} else {
			drawString("Info", width / 2 - 16, infoBox.y);
		}
	}

	public void update(float delta) {
		/**
		 * Mouse
		 */
		if (playBox.contains(Input.msp)) {
			selection = PLAY;
		}
		if (infoBox.contains(Input.msp)) {
			selection = INFO;
		}
		
		/**
		 * Keyboard
		 */
		if (Input.keys[KeyEvent.VK_W] || Input.keys[KeyEvent.VK_UP]) {
			selection = PLAY;
		}
		if (Input.keys[KeyEvent.VK_S] || Input.keys[KeyEvent.VK_DOWN]) {
			selection = INFO;
		}
	}
}
