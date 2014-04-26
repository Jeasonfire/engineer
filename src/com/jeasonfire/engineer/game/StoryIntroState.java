package com.jeasonfire.engineer.game;

import com.jeasonfire.engineer.graphics.screens.Screen;

public class StoryIntroState extends GameState {
	private long startTime = -1, endTime = -1, length = 10000;
	private String story = "You are an      engineer in a   research        facility,       researching     <REDACTED>.";
	private float time = 0;

	public StoryIntroState(Screen screen) {
		super(screen);
	}

	public void draw() {
		if (endTime < 0 && startTime > 0) {
			screen.drawString(story.substring(0,
					(int) (story.length() * time / (length / 1000))), 16, 16,
					1, 1.0f, (screen.getWidth() - 32) / 8);
		} else {
			screen.drawString(story, 16, 16, 1, (float) (1.0 / ((System.currentTimeMillis() - endTime) / 500.0)),
					(screen.getWidth() - 32) / 8);
		}
	}

	public void update(float delta) {
		if (startTime < 0) {
			startTime = System.currentTimeMillis();
		} else if (time + delta < length / 1000) {
			time += delta;
		}
		if (endTime < 0 && System.currentTimeMillis() - startTime > length) {
			endTime = System.currentTimeMillis();
		}
		if (System.currentTimeMillis() - endTime > 500) {
			// nextState = new Level1State();
		}
	}
}
