package com.jeasonfire.engineer.game.entities;

import java.awt.Rectangle;

import com.jeasonfire.engineer.graphics.screens.Screen;
import com.jeasonfire.engineer.graphics.sprites.Sprite;

public abstract class Entity {
	public float x, y, w, h;
	public float xVel, yVel, speed;
	public Rectangle bounds;
	public Sprite sprite;
	
	public Entity(float x, float y, float w, float h, float speed, Rectangle bounds, Sprite sprite) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		this.speed = speed;
		this.bounds = bounds;
		this.sprite = sprite;
	}
	
	public abstract void update(float delta);
	
	public void draw(Screen screen, int xScroll, int yScroll) {
		screen.drawSprite(sprite, (int) (x - xScroll), (int) (y - yScroll));
	}
}
