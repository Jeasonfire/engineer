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
	
	public boolean collision(Entity e) {
		if (getX() > e.getX() + e.getWidth())
			return false;
		if (getX() + getWidth() - 1 < e.getX())
			return false;
		if (getY() > e.getY() + e.getHeight())
			return false;
		if (getY() + getHeight() - 1 < e.getY())
			return false;
		return true;
	}
	
	public boolean collision(Level level) {
		float x0 = getX();
		float x1 = getX() + getWidth() - 1;
		float y0 = getY();
		float y1 = getY() + getHeight() - 1;
		
		return level.isSolid(x0, y0) || level.isSolid(x0, y1) || level.isSolid(x1, y0) || level.isSolid(x1, y1);
	}
	
	public void move(float delta, Level level) {
		move(delta, level, true);
	}

	public void move(float delta, Level level, boolean collide) {
		x += xVel * speed * delta;
		if (collision(level) && collide)
			x -= xVel * speed * delta;
		y += yVel * speed * delta;
		if (collision(level) && collide)
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
