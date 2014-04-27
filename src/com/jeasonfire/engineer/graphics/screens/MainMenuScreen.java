package com.jeasonfire.engineer.graphics.screens;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import com.jeasonfire.engineer.Game;
import com.jeasonfire.engineer.Input;
import com.jeasonfire.engineer.audio.Sound;

public class MainMenuScreen extends Screen {
	private static int PLAY = 0, INFO = 1, LEVEL_EDITOR = 2;
	private int selection = 0;
	private Rectangle playBox, infoBox, levelEditorBox;
	private float justPressed = 0;
	
	public MainMenuScreen(Game game) {
		super(game);
		playBox = new Rectangle(0, 48, width, 8);
		infoBox = new Rectangle(0, 60, width, 8);
		levelEditorBox = new Rectangle(0, 72, width, 8);
		Sound.MUSIC1.loop();
	}

	public void draw() {
		drawString("Engineer", 16, 8, 2);
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
		if (selection == LEVEL_EDITOR) {
			drawString("> Level Editor <", width / 2 - 64, levelEditorBox.y);
		} else {
			drawString("Level Editor", width / 2 - 48, levelEditorBox.y);
		}
	}
	
	public void select() {
		if (selection == PLAY) {
			nextScreen = new GameScreen(game);
		}
		if (selection == INFO) {
			nextScreen = new InfoScreen(game);
		}
		if (selection == LEVEL_EDITOR) {
			nextScreen = new LevelEditorScreen(game);
		}
	}

	public void update(float delta) {
		/**
		 * Music
		 */
		if (Sound.justMusicOn) {
			Sound.justMusicOn = false;
			Sound.MUSIC1.loop();
		}
		
		/**
		 * Mouse
		 */
		if (playBox.contains(Input.msp)) {
			selection = PLAY;
		}
		if (infoBox.contains(Input.msp)) {
			selection = INFO;
		}
		if (levelEditorBox.contains(Input.msp)) {
			selection = LEVEL_EDITOR;
		}
		if (Input.mouseDown) {
			select();
		}
		
		/**
		 * Keyboard
		 */
		if (justPressed > 0) {
			justPressed -= delta;
			return;
		}
		if (Input.keys[KeyEvent.VK_W] || Input.keys[KeyEvent.VK_I] || Input.keys[KeyEvent.VK_UP]) {
			selection--;
			justPressed = 0.25f;
			if (selection < 0) {
				selection = 0;
			}
		}
		if (Input.keys[KeyEvent.VK_S] || Input.keys[KeyEvent.VK_K] || Input.keys[KeyEvent.VK_DOWN]) {
			selection++;
			justPressed  = 0.25f;
			if (selection > 2) {
				selection = 2;
			}
		}
		if (Input.keys[KeyEvent.VK_ENTER] || Input.keys[KeyEvent.VK_CONTROL] || Input.keys[KeyEvent.VK_SPACE]) {
			justPressed = 0.25f;
			select();
		}
	}
}
