package com.jeasonfire.engineer.game.levels;

import java.util.ArrayList;

import com.jeasonfire.engineer.game.entities.Entity;
import com.jeasonfire.engineer.game.entities.Player;
import com.jeasonfire.engineer.graphics.screens.Screen;
import com.jeasonfire.engineer.graphics.sprites.Sprite;

public abstract class Level {
	public static int tileSize = 16;
	public int[] tiles;
	public int width, height;
	protected ArrayList<Entity> entities;
	protected int xScroll, yScroll;

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
		entities = new ArrayList<Entity>();
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
		case 0xFF00:
			tiles[x + y * width] = 0;
			entities.add(new Player(x * tileSize, y * tileSize));
			break;
		}
	}

	public void draw(Screen screen) {
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
		for (Entity e : entities) {
			if (e instanceof Player) {
				xScroll = (int) (e.getX() - screen.getWidth() / 2 + e
						.getWidth() / 2);
				yScroll = (int) (e.getY() - screen.getHeight() / 2 + e
						.getHeight() / 2);
			}
			e.draw(screen, (int) xScroll, (int) yScroll);
		}
	}

	public void update(float delta) {
		for (Entity e : entities) {
			e.update(delta, this);
		}
	}

	public Sprite getTileSprite(int index) {
		if (index < 0 || index >= tileset.length)
			return new Sprite(tileSize, tileSize);
		return tileset[index];
	}
	
	public boolean getTileSolid(int id) {
		switch (id) {
		default:
		case 0:
			return false;
		case 1:
			return true;
		}
	}

	public boolean isSolid(float x, float y) {
		if (x < 0 || x / tileSize >= width || y < 0 || y / tileSize >= height)
			return false;
		return getTileSolid(tiles[(int) (x / tileSize) + (int) (y / tileSize) * width]);
	}
}
