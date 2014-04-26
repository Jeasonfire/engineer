package com.jeasonfire.engineer.game;

import com.jeasonfire.engineer.game.levels.Level1;
import com.jeasonfire.engineer.graphics.screens.Screen;

public class Level1State extends LevelState {
	public Level1State(Screen screen) {
		super(screen);
		level = new Level1();
	}
}
