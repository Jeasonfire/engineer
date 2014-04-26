package com.jeasonfire.engineer.game.levels;

import com.jeasonfire.engineer.graphics.screens.Screen;
import com.jeasonfire.engineer.graphics.sprites.Sprite;

public abstract class Level {
	public static int tileSize = 16;
	public int[] tiles;
	public int width, height;

	private static Sprite[] tileset;
	static {
		Sprite load = new Sprite("tileset.png");
		tileset = new Sprite[(load.getWidth() / tileSize)
				* (load.getWidth() / tileSize)];
		for (int y = 0; y < load.getHeight() / tileSize; y++) {
			for (int x = 0; x < load.getWidth() / tileSize; x++) {
				tileset[x + y * (load.getWidth() / tileSize)] = load.cut(x
						* tileSize, y * tileSize, tileSize, tileSize);
			}
		}
	};

	public Level() {
		generate();
	}

	protected abstract void generate();

	public void setTile(int id, int x, int y) {
		if (x + y * width < 0 || x + y * width >= tiles.length)
			return;
		switch (id) {
		case 0:
		case 0xFFFFFF:
			tiles[x + y * width] = 0;
			break;
		case 0xFF:
			tiles[x + y * width] = 1;
			break;
		}
	}

	public void draw(Screen screen, int xScroll, int yScroll) {
		for (int y = 0; y < height; y++) {
			int yy = y + yScroll / tileSize;
			if (yy < 0 || yy >= height)
				continue;
			int yp = y * tileSize - (yScroll % tileSize);
			if (yp < -tileSize || yp >= screen.getHeight())
				continue;
			for (int x = 0; x < width; x++) {
				int xx = x + xScroll / tileSize;
				if (xx < 0 || xx >= width)
					continue;
				int xp = x * tileSize - (xScroll % tileSize);
				if (xp < -tileSize || xp >= screen.getWidth())
					continue;
				screen.drawSprite(getTileSprite(tiles[xx + yy * width]), xp, yp);
			}
		}
	}

	public Sprite getTileSprite(int index) {
		if (index < 0 || index >= tileset.length)
			return new Sprite(tileSize, tileSize);
		return tileset[index];
	}
}
