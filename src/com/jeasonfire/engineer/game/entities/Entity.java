package com.jeasonfire.engineer.game.entities;

import java.awt.Rectangle;

import com.jeasonfire.engineer.game.levels.Level;
import com.jeasonfire.engineer.graphics.screens.Screen;
import com.jeasonfire.engineer.graphics.sprites.Sprite;

public abstract class Entity {
	protected float x, y;
	protected float xVel, yVel, speed;
	protected Rectangle bounds;
	public Sprite sprite;
	
	public Entity(float x, float y, float speed, Rectangle bounds, Sprite sprite) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		this.bounds = bounds;
		this.sprite = sprite;
	}
	
	public abstract void update(float delta, Level level);
	
	public boolean collision(Level level) {
		float x0 = getX();
		float x1 = getX() + getWidth() - 1;
		float y0 = getY();
		float y1 = getY() + getHeight() - 1;
		
		return level.isSolid(x0, y0) || level.isSolid(x0, y1) || level.isSolid(x1, y0) || level.isSolid(x1, y1);
	}

	public void move(float delta, Level level) {
		x += xVel * speed * delta;
		if (collision(level))
			x -= xVel * speed * delta;
		y += yVel * speed * delta;
		if (collision(level))
			y -= yVel * speed * delta;
	}
	
	public void draw(Screen screen, int xScroll, int yScroll) {
		screen.drawSprite(sprite, (int) (x - xScroll), (int) (y - yScroll));
	}
	
	public float getX() {
		return x + bounds.x;
	}
	
	public float getY() {
		return y + bounds.y;
	}
	
	public int getWidth() {
		return bounds.width;
	}
	
	public int getHeight() {
		return bounds.height;
	}
}
