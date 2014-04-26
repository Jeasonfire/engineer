package com.jeasonfire.engineer.game;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.jeasonfire.engineer.Input;
import com.jeasonfire.engineer.game.entities.Entity;
import com.jeasonfire.engineer.game.levels.Level;
import com.jeasonfire.engineer.graphics.screens.Screen;

public class LevelState extends GameState {
	protected Level level;
	protected float xScroll, yScroll;
	protected ArrayList<Entity> entities;
	
	public LevelState(Screen screen) {
		super(screen);
		entities = new ArrayList<Entity>();
	}
	
	public void draw() {
		level.draw(screen, (int) xScroll, (int) yScroll);
		for (Entity e : entities) {
			e.draw(screen, (int) xScroll, (int) yScroll);
		}
	}

	public void update(float delta) {
		for (Entity e : entities) {
			e.update(delta);
		}
		
		if (Input.keys[KeyEvent.VK_W]) {
			yScroll -= delta * 200f;
		}
		if (Input.keys[KeyEvent.VK_A]) {
			xScroll -= delta * 200f;
		}
		if (Input.keys[KeyEvent.VK_S]) {
			yScroll += delta * 200f;
		}
		if (Input.keys[KeyEvent.VK_D]) {
			xScroll += delta * 200f;
		}
	}
}
