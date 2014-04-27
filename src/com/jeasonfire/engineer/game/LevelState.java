package com.jeasonfire.engineer.game;

import com.jeasonfire.engineer.game.levels.Level;
import com.jeasonfire.engineer.graphics.screens.GameoverScreen;
import com.jeasonfire.engineer.graphics.screens.Screen;
import com.jeasonfire.engineer.graphics.screens.VictoryScreen;

public class LevelState extends GameState {
	protected Level level;
	
	public LevelState(Screen screen) {
		super(screen);
		level = new Level();
	}
	
	public void draw() {
		level.draw(screen);
	}

	public void update(float delta) {
		level.update(delta);
		if (level.victory) {
			screen.nextScreen = new VictoryScreen(screen.game, level.score);
		}
		if (level.gameover) {
			screen.nextScreen = new GameoverScreen(screen.game);
		}
	}
}
