package com.jeasonfire.engineer.graphics.screens;

import com.jeasonfire.engineer.Game;

public class MainMenuScreen extends Screen {
	public MainMenuScreen(Game game) {
		super(game);
	}

	public void draw() {
		drawString("Engineer", 16, 16, 2);
	}

	public void update(float delta) {
	}
}
