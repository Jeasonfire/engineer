package com.jeasonfire.engineer.game.entities;

import java.awt.Rectangle;

import com.jeasonfire.engineer.game.levels.Level;
import com.jeasonfire.engineer.graphics.sprites.Sprite;

public class Turret extends Entity {
	private float cooldown = 0;

	public Turret(float x, float y) {
		super(x, y, 0, new Rectangle(0, 0, 16, 16), new Sprite("turret.png"));
	}

	public void update(float delta, Level level) {
		if (cooldown > 0) {
			cooldown -= delta;
		}
		if (cooldown <= 0) {
			shoot(level);
			cooldown = 1;
		}
	}

	private void shoot(Level level) {
		for (int i = 0; i < level.entities.size(); i++) {
			if (level.entities.get(i) instanceof Player) {
				level.entities.add(new Bullet(getX() + getWidth() / 2, getY() + getHeight(), level.entities
						.get(i).getX(), level.entities.get(i).getY()));
			}
		}
	}
}
