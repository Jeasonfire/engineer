package com.jeasonfire.engineer.graphics.screens;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import com.jeasonfire.engineer.Game;
import com.jeasonfire.engineer.Input;
import com.jeasonfire.engineer.audio.Sound;

public class VictoryScreen extends Screen {
	private static int BACK = 0, NOTHING = 1;
	private int selection = 0;
	private Rectangle backBox;
	private int score;
	
	public VictoryScreen(Game game, int score) {
		super(game);
		this.score = score;
		backBox = new Rectangle(0, height - 12, width, 8);
		Sound.MUSIC1.loop();
	}

	public void draw() {
		drawString("Engineer", 16, 8, 2);
		drawString("Congratulations!", width / 2 - 64, 48);
		drawString("You won!", width / 2 - 32, 60);
		drawString("Your score: " + score, width / 2 - 56, 72);

		if (selection == BACK) {
			drawString("> Back <", width / 2 - 32, backBox.y);
		} else {
			drawString("Back", width / 2 - 16, backBox.y);
		}
	}

	public void select() {
		if (selection == BACK) {
			nextScreen = new MainMenuScreen(game);
		}
	}

	public void update(float delta) {
		/**
		 * Mouse
		 */
		if (backBox.contains(Input.msp)) {
			selection = BACK;
		} else {
			selection = NOTHING;
		}
		if (Input.mouseDown) {
			select();
		}

		/**
		 * Keyboard
		 */
		if (Input.keys[KeyEvent.VK_W] || Input.keys[KeyEvent.VK_I] || Input.keys[KeyEvent.VK_UP]) {
			selection = NOTHING;
		}
		if (Input.keys[KeyEvent.VK_S] || Input.keys[KeyEvent.VK_K] || Input.keys[KeyEvent.VK_DOWN]) {
			selection = BACK;
		}
		if (Input.keys[KeyEvent.VK_ENTER] || Input.keys[KeyEvent.VK_CONTROL] || Input.keys[KeyEvent.VK_SPACE]) {
			select();
		}
	}
}
