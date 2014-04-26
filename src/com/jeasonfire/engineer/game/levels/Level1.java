package com.jeasonfire.engineer.game.levels;

import com.jeasonfire.engineer.graphics.HexColor;
import com.jeasonfire.engineer.graphics.sprites.Sprite;

public class Level1 extends Level {
	protected void generate() {
		Sprite load = new Sprite("level1.png");
		this.width = load.getWidth() * cellSize;
		this.height = load.getHeight() * cellSize;
		this.tiles = new int[width * height];
		for (int y = 0; y < height / cellSize; y++) {
			for (int x = 0; x < width / cellSize; x++) {
				int id = load.getPixel(x, y) & 0xFFFFFF;
				if (HexColor.getRed(id) == 0xFF) {						
					if (HexColor.getGreen(id) > 0) 
						setSwitch(HexColor.getGreen(id), x, y);
					if (HexColor.getBlue(id) > 0) 
						setGate(HexColor.getBlue(id), x, y);
				} else {
					setCell(id, x, y);
				}
			}
		}
	}
}
