package com.jeasonfire.engineer.game;

import com.jeasonfire.engineer.game.levels.Level;
import com.jeasonfire.engineer.graphics.screens.Screen;

public class LevelState extends GameState {
	protected Level level;
	
	public LevelState(Screen screen) {
		super(screen);
	}
	
	public void draw() {
		level.draw(screen);
	}

	public void update(float delta) {
		level.update(delta);
	}
}
