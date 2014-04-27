package com.jeasonfire.engineer.graphics.screens;

import com.jeasonfire.engineer.Game;
import com.jeasonfire.engineer.audio.Sound;
import com.jeasonfire.engineer.game.GameState;
import com.jeasonfire.engineer.game.StoryIntroState;

public class GameScreen extends Screen {
	private GameState state;
	
	public GameScreen(Game game) {
		super(game);
		state = new StoryIntroState(this);
		Sound.MUSIC2.loop();
	}

	public void draw() {
		state.draw();
	}

	public void update(float delta) {
		state.update(delta);
		if (state.nextState != null) {
			state = state.nextState;
		}
	}
}
