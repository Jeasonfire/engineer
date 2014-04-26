package com.jeasonfire.engineer.graphics.screens;

import com.jeasonfire.engineer.Game;

public abstract class Screen {
	protected int width, height;
	private int[] pixels;
	protected final Game game;
	
	public Screen(Game game) {
		this.game = game;
		this.width = game.getGameWidth();
		this.height = game.getGameHeight();
		this.pixels = new int[width * height];
	}
	
	public abstract void draw();
	public abstract void update(float delta);
	
	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}
	
	public void drawShadedRectangle(int brightColor, int midColor, int shadowColor, int x, int y, int w, int h) {
		drawRectangle(brightColor, x, y, w - 1, 1);
		drawRectangle(brightColor, x, y + 1, 1, h - 1);
		drawRectangle(midColor, x + 1, y + 1, w - 2, h - 2);
		drawRectangle(shadowColor, x + w - 1, y, 1, h);
		drawRectangle(shadowColor, x + 1, y + h - 1, w - 1, 1);
	}
	
	public void drawRectangle(int color, int x, int y, int w, int h) {
		for (int yp = 0; yp < h; yp++) {
			int yy = yp + y;
			for (int xp = 0; xp < w; xp++) {
				int xx = xp + x;
				setPixel(color, xx, yy);
			}
		}
	}
	
	public void setPixel(int color, int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height)
			return;
		pixels[x + y * width] = color;
	}
	
	public int getPixel(int x, int y) {
		if (x < 0 || x >= width || y < 0 || y >= height)
			return -1;
		return pixels[x + y * width];
	}
}
