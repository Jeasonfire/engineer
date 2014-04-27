package com.jeasonfire.engineer.game;

import com.jeasonfire.engineer.game.levels.Level;
import com.jeasonfire.engineer.graphics.screens.GameoverScreen;
import com.jeasonfire.engineer.graphics.screens.Screen;
import com.jeasonfire.engineer.graphics.screens.VictoryScreen;

public class LevelEditorState extends GameState {
	protected Level level;
	
	public LevelEditorState(Screen screen) {
		super(screen);
		level = new Level();
	}
	
	public void draw() {
		level.draw(screen);
	}

	public void update(float delta) {
		level.update(delta);
		if (level.victory) {
			screen.nextScreen = new VictoryScreen(screen.game);
		}
		if (level.gameover) {
			screen.nextScreen = new GameoverScreen(screen.game);
		}
	}
}
