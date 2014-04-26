package com.jeasonfire.engineer.game.entities;

import java.awt.Rectangle;

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
	
	public abstract void update(float delta);

	public void move(float delta) {
		x += xVel * speed * delta;
		y += yVel * speed * delta;
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
