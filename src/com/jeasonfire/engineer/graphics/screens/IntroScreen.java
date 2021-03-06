package com.jeasonfire.engineer.graphics.screens;

import com.jeasonfire.engineer.Game;
import com.jeasonfire.engineer.graphics.sprites.Sprite;

public class IntroScreen extends Screen {
	private Sprite test = new Sprite("intro.png");
	private float transparency = 0f;
	private long startTime = -1;

	public IntroScreen(Game game) {
		super(game);
	}

	public void draw() {
		drawSprite(test, width / 2 - test.getWidth() / 2, height / 2 - test.getHeight() / 2, 1, transparency);
	}

	public void update(float delta) {
		if (startTime == -1)
			startTime = System.currentTimeMillis();
		transparency = (float) (Math.sin((System.currentTimeMillis() - startTime) / 500.0));
		if (transparency < 0) 
			nextScreen = new MainMenuScreen(game);
	}
}
