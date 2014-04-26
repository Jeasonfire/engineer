package com.jeasonfire.engineer.game.entities;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import com.jeasonfire.engineer.Input;
import com.jeasonfire.engineer.game.levels.Level;
import com.jeasonfire.engineer.graphics.sprites.Sprite;

public class Player extends Entity {
	private float justPressed = 0;
	
	public Player(float x, float y) {
		super(x, y, 75.0f, new Rectangle(2, 2, 12, 14),
				new Sprite("player.png"));
	}

	public void update(float delta, Level level) {
		if (Input.keys[KeyEvent.VK_W] || Input.keys[KeyEvent.VK_I]
				|| Input.keys[KeyEvent.VK_UP]) {
			yVel -= delta * 20;
			if (yVel < -1) {
				yVel = -1;
			}
		} else if (Input.keys[KeyEvent.VK_S] || Input.keys[KeyEvent.VK_K]
				|| Input.keys[KeyEvent.VK_DOWN]) {
			yVel += delta * 20;
			if (yVel > 1) {
				yVel = 1;
			}
		} else {
			yVel = 0;
		}
		if (Input.keys[KeyEvent.VK_D] || Input.keys[KeyEvent.VK_L]
				|| Input.keys[KeyEvent.VK_RIGHT]) {
			xVel += delta * 20;
			if (xVel > 1) {
				xVel = 1;
			}
		} else if (Input.keys[KeyEvent.VK_A] || Input.keys[KeyEvent.VK_J]
				|| Input.keys[KeyEvent.VK_LEFT]) {
			xVel -= delta * 20;
			if (xVel < -1) {
				xVel = -1;
			}
		} else {
			xVel = 0;
		}
		if (Input.keys[KeyEvent.VK_SHIFT]) {
			speed = 150.0f;
		} else {
			speed = 75.0f;
		}
		if (justPressed > 0) {
			justPressed -= delta;
			if (justPressed < 0) {
				justPressed = 0;
			}
		}
		if (justPressed == 0 && (Input.keys[KeyEvent.VK_SPACE] || Input.keys[KeyEvent.VK_CONTROL]
				|| Input.keys[KeyEvent.VK_ENTER])) {
			level.toggleSwitch(
					(int) (getX() / Level.cellSize / Level.tileSize),
					(int) (getY() / Level.cellSize / Level.tileSize));
			justPressed = 0.1f;
		}
		move(delta, level);
	}
}
