package com.jeasonfire.engineer.graphics.screens;

import com.jeasonfire.engineer.Game;
import com.jeasonfire.engineer.game.GameState;
import com.jeasonfire.engineer.game.LevelEditorState;

public class LevelEditorScreen extends Screen {
	private GameState state;
	
	public LevelEditorScreen(Game game) {
		super(game);
		state = new LevelEditorState(this);
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
