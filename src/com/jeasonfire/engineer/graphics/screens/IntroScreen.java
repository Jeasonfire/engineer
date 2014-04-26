package com.jeasonfire.engineer.graphics.screens;

import java.awt.event.KeyEvent;

import com.jeasonfire.engineer.Game;
import com.jeasonfire.engineer.Input;

public class IntroScreen extends Screen {
	private float x = 0, y = 0;
	
	public IntroScreen(Game game) {
		super(game);
	}

	public void draw() {
		drawShadedRectangle(0xCC00, 0xAA00, 0x8800, (int) x, (int) y, 40, 40);
	}

	public void update(float delta) {
		if (Input.keys[KeyEvent.VK_W]) {
			y -= delta * 100.0f;
		}
		if (Input.keys[KeyEvent.VK_A]) {
			x -= delta * 100.0f;
		}
		if (Input.keys[KeyEvent.VK_S]) {
			y += delta * 100.0f;
		}
		if (Input.keys[KeyEvent.VK_D]) {
			x += delta * 100.0f;
		}
	}
}
