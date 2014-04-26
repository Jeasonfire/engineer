package com.jeasonfire.engineer.graphics.screens;

import java.awt.event.KeyEvent;

import com.jeasonfire.engineer.Game;
import com.jeasonfire.engineer.Input;
import com.jeasonfire.engineer.graphics.sprites.Sprite;

public class IntroScreen extends Screen {
	private float x = 0, y = 0;
	private Sprite test = new Sprite("testSprite.png");

	public IntroScreen(Game game) {
		super(game);
	}

	public void draw() {
		drawSprite(test, (int) x, (int) y, 2);
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
