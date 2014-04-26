package com.jeasonfire.engineer.game.levels;

import com.jeasonfire.engineer.graphics.sprites.Sprite;

public class Level1 extends Level {
	protected void generate() {
		Sprite load = new Sprite("level1.png");
		this.width = load.getWidth();
		this.height = load.getHeight();
		this.tiles = new int[width * height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				setTile(load.getPixel(x, y) & 0xFFFFFF, x, y);
			}
		}
	}
}
