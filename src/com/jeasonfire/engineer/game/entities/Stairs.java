package com.jeasonfire.engineer.game.entities;

import java.awt.Rectangle;

import com.jeasonfire.engineer.game.levels.Level;
import com.jeasonfire.engineer.graphics.sprites.Sprite;

public class Stairs extends Entity {
	public static Sprite SPRITE = new Sprite("stairs.png");
	
	public Stairs(float x, float y) {
		super(x, y, 0, new Rectangle(0, 0, 16, 16), SPRITE);
	}

	public void update(float delta, final Level level) {
		for (Entity e : level.entities) {
			if (e instanceof Player && e.collision(this)) {
				level.nextLevel();
			}
		}
	}
}
