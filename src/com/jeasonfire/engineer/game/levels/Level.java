package com.jeasonfire.engineer.game.levels;

import java.awt.Point;
import java.util.ArrayList;

import com.jeasonfire.engineer.game.entities.Entity;
import com.jeasonfire.engineer.game.entities.Player;
import com.jeasonfire.engineer.game.entities.Stairs;
import com.jeasonfire.engineer.graphics.HexColor;
import com.jeasonfire.engineer.graphics.screens.Screen;
import com.jeasonfire.engineer.graphics.sprites.Sprite;

public class Level {
	public static int tileSize = 16, cellSize = 3;
	public int[] tiles;
	public int width, height;
	public ArrayList<Entity> entities;
	public int currentLevel = 1, maxLevels = 1;
	protected int xScroll, yScroll;
	private float xScrollCenter, yScrollCenter;
	private float transparencyRange = 4;
	
	public boolean victory = false, gameover = false;

	private class SwitchGate {
		private Point switchPoint;
		private Point gatePoint;
		private boolean open = false;

		public void setSwitch(int x, int y) {
			switchPoint = new Point(x, y);
		}

		public void setGate(int x, int y) {
			gatePoint = new Point(x, y);
		}

		public boolean getNearSwitch(int x, int y) {
			return switchPoint.x == x && switchPoint.y == y;
		}

		public void setOpen(boolean open) {
			this.open = open;
		}

		public boolean getOpen() {
			return open;
		}

		public int getSwitchX() {
			return switchPoint.x;
		}

		public int getSwitchY() {
			return switchPoint.y;
		}

		public int getGateX() {
			return gatePoint.x;
		}

		public int getGateY() {
			return gatePoint.y;
		}
	};

	private SwitchGate[] switchGates = new SwitchGate[256];

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
		generate(currentLevel);
	}

	public void generate(int level) {
		Sprite load = new Sprite("level" + level + ".png");
		this.width = load.getWidth() * cellSize;
		this.height = load.getHeight() * cellSize;
		this.tiles = new int[width * height];
		for (int y = 0; y < height / cellSize; y++) {
			for (int x = 0; x < width / cellSize; x++) {
				int id = load.getPixel(x, y) & 0xFFFFFF;
				if (id == 0xFFFFFF) {
					setNextLevel(x, y);
				} else if (HexColor.getRed(id) == 0xFF) {						
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
	public void nextLevel() {
		currentLevel++;
		if (currentLevel <= maxLevels)
			generate(currentLevel);
		else
			victory = true;
	}

	public void toggleSwitch(int x, int y) {
		for (SwitchGate sg : switchGates) {
			if (sg != null && sg.getNearSwitch(x, y)) {
				sg.setOpen(!sg.getOpen());
			}
		}
	}

	public void setSwitch(int id, int x, int y) {
		if (switchGates[id] == null)
			switchGates[id] = new SwitchGate();
		switchGates[id].setSwitch(x, y);
		setCell(0, x, y);
	}

	public void setGate(int id, int x, int y) {
		if (switchGates[id] == null)
			switchGates[id] = new SwitchGate();
		switchGates[id].setGate(x, y);
		setCell(1, x, y);
	}

	public void setNextLevel(int x, int y) {
		setCell(0, x, y);
		entities.add(new Stairs(x * cellSize * tileSize, y * cellSize * tileSize));
	}
	
	public void setCell(int id, int x, int y) {
		for (int i = 0; i < cellSize * cellSize; i++) {
			setTile(id, x * cellSize + i % cellSize, y * cellSize + i
					/ cellSize);
		}
	}

	public int getCell(int x, int y) {
		return tiles[(x * cellSize) + (y * cellSize + cellSize / 2) * width];
	}

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
				screen.drawSprite(getTileSprite(tiles[xx + yy * width]), xp,
						yp, 1, getTransparency(xx, yy));
			}
		}
		for (SwitchGate sg : switchGates) {
			if (sg != null) {
				if (sg.getOpen()) {
					screen.drawShadedRectangle(0xCC00, 0xAA00, 0x8800,
							sg.getSwitchX() * cellSize * tileSize + cellSize
									* tileSize / 2 - tileSize / 2 - xScroll,
							sg.getSwitchY() * cellSize * tileSize + cellSize
									* tileSize / 2 - tileSize / 2 - yScroll,
							tileSize, tileSize);
				} else {
					screen.drawShadedRectangle(0xCC0000, 0xAA0000, 0x880000,
							sg.getSwitchX() * cellSize * tileSize + cellSize
									* tileSize / 2 - tileSize / 2 - xScroll,
							sg.getSwitchY() * cellSize * tileSize + cellSize
									* tileSize / 2 - tileSize / 2 - yScroll,
							tileSize, tileSize);
				}
			}
		}
		for (Entity e : entities) {
			if (e instanceof Player) {
				xScroll = (int) (e.getX() - screen.getWidth() / 2 + e
						.getWidth() / 2);
				xScrollCenter = e.getX();
				yScroll = (int) (e.getY() - screen.getHeight() / 2 + e
						.getHeight() / 2);
				yScrollCenter = e.getY();
			}
			e.draw(screen, (int) xScroll, (int) yScroll);
		}
	}

	public float getTransparency(float x, float y) {
		float xt = x - xScrollCenter / tileSize;
		float yt = y - yScrollCenter / tileSize;
		float intensity = (float) (transparencyRange - Math.sqrt(xt * xt + yt
				* yt));
		if (intensity < 0)
			return 0;
		intensity = 1 - 1 / intensity;
		return intensity;
	}

	public void update(float delta) {
		for (Entity e : entities) {
			e.update(delta, this);
		}
		for (SwitchGate sg : switchGates) {
			if (sg != null) {
				if (sg.getOpen()) {
					setCell(0, sg.getGateX(), sg.getGateY());
				}
				if (!sg.getOpen()) {
					setCell(0xFF, sg.getGateX(), sg.getGateY());
				}
			}
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
		return getTileSolid(tiles[(int) (x / tileSize) + (int) (y / tileSize)
				* width]);
	}
}
