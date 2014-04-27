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
		for (int i = 0; i < level.entities.size(); i++) {
			if (level.entities.get(i) instanceof Player && level.entities.get(i).collision(this)) {
				level.nextLevel();
			}
		}
	}
}
