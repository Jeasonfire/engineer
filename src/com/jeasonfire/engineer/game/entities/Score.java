package com.jeasonfire.engineer.game.entities;

import java.awt.Rectangle;
import java.util.Random;

import com.jeasonfire.engineer.game.levels.Level;
import com.jeasonfire.engineer.graphics.sprites.Sprite;

public class Score extends Entity {
	public static Sprite SPRITE = new Sprite("stairs.png");
	private static Random random = new Random();
	
	public Score(float x, float y) {
		super(x, y, 0, new Rectangle(0, 0, 16, 16), SPRITE);
	}

	public void update(float delta, final Level level) {
		level.addScore(random.nextInt(5) * 10);
	}
}
