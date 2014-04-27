package com.jeasonfire.engineer.game.entities;

import java.awt.Rectangle;
import java.awt.event.KeyEvent;

import com.jeasonfire.engineer.Input;
import com.jeasonfire.engineer.audio.Sound;
import com.jeasonfire.engineer.game.levels.Level;
import com.jeasonfire.engineer.graphics.screens.Screen;
import com.jeasonfire.engineer.graphics.sprites.Sprite;

public class Player extends Entity {
	public static Sprite SPRITE;
	public static Sprite[][] sprites;
	static {
		SPRITE = new Sprite("player.png");
		sprites = new Sprite[3][4];
		for (int y = 0; y < SPRITE.getHeight() / 16; y++) {
			for (int x = 0; x < SPRITE.getWidth() / 16; x++) {
				sprites[x][y] = SPRITE.cut(x * 16, y * 16, 16, 16);
			}
		}
		for (int x = 0; x < 3; x++) {
			sprites[x][3] = SPRITE.cut(x * 16, 0, 16, 16).mirror();
		}
	};

	private float justPressed = 0;
	private int xIndex = 0, yIndex = 0;
	private long lastFrameChange = 0;
	private boolean movingX = false, movingY = false;

	public Player(float x, float y) {
		super(x, y, 75.0f, new Rectangle(2, 2, 12, 14), SPRITE);
	}

	public void draw(Screen screen, int xScroll, int yScroll) {
		screen.drawSprite(sprites[getFrame()][yIndex], (int) (x - xScroll),
				(int) (y - yScroll));
	}

	private int getFrame() {
		if (!movingX && !movingY)
			return 0;
		if (xIndex < 2)
			return xIndex;
		if (xIndex == 2)
			return 0;
		if (xIndex == 3)
			return 2;
		return 0;
	}
	
	private void playFootstep() {
		if (xIndex == 0)
			Sound.FOOTSTEP1.play();
		if (xIndex == 1)
			Sound.FOOTSTEP2.play();
	}

	public void update(float delta, Level level) {
		if (System.currentTimeMillis() - lastFrameChange > (150 - speed) * 2) {
			lastFrameChange = System.currentTimeMillis();
			xIndex++;
			if (xIndex > 3) {
				xIndex = 0;
			}
			if (movingX || movingY) {
				playFootstep();
			}
		}

		if (Input.keys[KeyEvent.VK_W] || Input.keys[KeyEvent.VK_I]
				|| Input.keys[KeyEvent.VK_UP]) {
			yVel -= delta * 20;
			yIndex = 2;
			if (yVel < -1) {
				yVel = -1;
			}
			movingY = true;
		} else if (Input.keys[KeyEvent.VK_S] || Input.keys[KeyEvent.VK_K]
				|| Input.keys[KeyEvent.VK_DOWN]) {
			yVel += delta * 20;
			yIndex = 1;
			if (yVel > 1) {
				yVel = 1;
			}
			movingY = true;
		} else {
			yVel = 0;
			movingY = false;
		}
		if (Input.keys[KeyEvent.VK_D] || Input.keys[KeyEvent.VK_L]
				|| Input.keys[KeyEvent.VK_RIGHT]) {
			xVel += delta * 20;
			yIndex = 0;
			if (xVel > 1) {
				xVel = 1;
			}
			movingX = true;
		} else if (Input.keys[KeyEvent.VK_A] || Input.keys[KeyEvent.VK_J]
				|| Input.keys[KeyEvent.VK_LEFT]) {
			xVel -= delta * 20;
			yIndex = 3;
			if (xVel < -1) {
				xVel = -1;
			}
			movingX = true;
		} else {
			xVel = 0;
			movingX = false;
		}
		if (Input.keys[KeyEvent.VK_SHIFT]) {
			speed = 100.0f;
		} else {
			speed = 75.0f;
		}
		if (justPressed > 0) {
			justPressed -= delta;
			if (justPressed < 0) {
				justPressed = 0;
			}
		}
		if (justPressed == 0
				&& (Input.keys[KeyEvent.VK_SPACE]
						|| Input.keys[KeyEvent.VK_CONTROL] || Input.keys[KeyEvent.VK_ENTER])) {
			level.toggleSwitch(
					(int) (getX() / Level.cellSize / Level.tileSize),
					(int) (getY() / Level.cellSize / Level.tileSize));
			justPressed = 0.25f;
		}
		move(delta, level);
	}
}
