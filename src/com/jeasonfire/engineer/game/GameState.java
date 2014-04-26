package com.jeasonfire.engineer.game;

import com.jeasonfire.engineer.graphics.screens.Screen;

public abstract class GameState {
	protected final Screen screen;
	public GameState nextState;
	
	public GameState(Screen screen) {
		this.screen = screen;
	}
	
	public abstract void draw();
	public abstract void update(float delta);
}
