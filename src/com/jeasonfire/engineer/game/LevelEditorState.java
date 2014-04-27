package com.jeasonfire.engineer.game;

import com.jeasonfire.engineer.game.levels.Level;
import com.jeasonfire.engineer.game.levels.LevelEditor;
import com.jeasonfire.engineer.graphics.screens.Screen;

public class LevelEditorState extends GameState {
	protected Level levelEditor;
	
	public LevelEditorState(Screen screen) {
		super(screen);
		levelEditor = new LevelEditor();
	}
	
	public void draw() {
		levelEditor.draw(screen);
	}

	public void update(float delta) {
		levelEditor.update(delta);
	}
}
