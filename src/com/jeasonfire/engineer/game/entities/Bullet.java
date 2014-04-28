package com.jeasonfire.engineer.game.entities;

import java.awt.Rectangle;

import com.jeasonfire.engineer.audio.Sound;
import com.jeasonfire.engineer.game.levels.Level;
import com.jeasonfire.engineer.graphics.sprites.Sprite;

public class Bullet extends Entity {
	public static Sprite SPRITE = new Sprite("bullet.png");
	private Entity host;

	public Bullet(float x, float y, float targetX, float targetY, Entity host) {
		super(x, y, 500.0f, new Rectangle(1, 1, 2, 2), SPRITE);
		double direction = Math.atan2(targetY - y, targetX - x);
		xVel = (float) Math.cos(direction);
		yVel = (float) Math.sin(direction);
		this.host = host;
	}

	public void update(float delta, Level level) {
		move(delta, level, false);
		if (collision(level)) {
			level.entities.remove(this);
		}
		for (int i = 0; i < level.entities.size(); i++) {
			if (level.entities.get(i) instanceof Player
					&& collision(level.entities.get(i))) {
				level.resetLevel();
				Sound.DEATH.play();
				if (level.currentLevel > 5) {
					level.score--;
					if (level.currentLevel == 10)
						level.score--;
					level.lives--;
				}
				break;
			} else if (level.entities.get(i) instanceof Turret
					&& collision(level.entities.get(i))
					&& !level.entities.get(i).equals(host)) {
				level.entities.remove(i);
				level.entities.remove(this);
				break;
			}
		}
	}
}
