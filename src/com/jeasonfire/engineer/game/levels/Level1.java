package com.jeasonfire.engineer.game.levels;

import com.jeasonfire.engineer.graphics.sprites.Sprite;

public class Level1 extends Level {
	protected void generate() {
		Sprite load = new Sprite("level1.png");
		int cellSize = 3;
		this.width = load.getWidth() * cellSize;
		this.height = load.getHeight() * cellSize;
		this.tiles = new int[width * height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				setTile(load.getPixel(x / cellSize, y / cellSize) & 0xFFFFFF, x, y);
			}
		}
	}
}
