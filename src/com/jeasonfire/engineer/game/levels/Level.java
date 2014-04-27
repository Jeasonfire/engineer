package com.jeasonfire.engineer.game.levels;

import java.awt.Point;
import java.util.ArrayList;

import com.jeasonfire.engineer.game.entities.Entity;
import com.jeasonfire.engineer.game.entities.Player;
import com.jeasonfire.engineer.game.entities.Stairs;
import com.jeasonfire.engineer.game.entities.Turret;
import com.jeasonfire.engineer.graphics.HexColor;
import com.jeasonfire.engineer.graphics.screens.Screen;
import com.jeasonfire.engineer.graphics.sprites.Sprite;

public class Level {
	public static int tileSize = 16, cellSize = 3;
	public int[] tiles;
	public int width, height;
	public ArrayList<Entity> entities;
	public int currentLevel = 4, maxLevels = 4;
	private boolean generateNewLevel = true;
	protected int xScroll, yScroll;
	private float xScrollCenter, yScrollCenter;
	private float transparencyRange = 4;
	private long startTime = 0;
	private long tipLength = 5000;

	public boolean victory = false, gameover = false;

	private class SwitchGate {
		private ArrayList<Point> switchPoint;
		private ArrayList<Point> gatePoint;
		private boolean open = false;

		public SwitchGate() {
			switchPoint = new ArrayList<Point>();
			gatePoint = new ArrayList<Point>();
		}

		public void addSwitch(int x, int y) {
			switchPoint.add(new Point(x, y));
		}

		public void addGate(int x, int y) {
			gatePoint.add(new Point(x, y));
		}

		public boolean getNearSwitch(int x, int y) {
			for (Point p : switchPoint) {
				if (p.x == x && p.y == y) {
					return true;
				}
			}
			return false;
		}

		public void setOpen(boolean open) {
			this.open = open;
		}

		public boolean getOpen() {
			return open;
		}

		public int getSwitchX(int index) {
			return switchPoint.get(index).x;
		}

		public int getSwitchY(int index) {
			return switchPoint.get(index).y;
		}

		public int getSwitchSize() {
			return switchPoint.size();
		}

		public int getGateX(int index) {
			return gatePoint.get(index).x;
		}

		public int getGateY(int index) {
			return gatePoint.get(index).y;
		}

		public int getGateSize() {
			return gatePoint.size();
		}
	};

	private SwitchGate[] switchGates;

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
		generate(currentLevel);
	}

	public void generate(int level) {
		generateNewLevel = false;
		Sprite load = new Sprite("level" + level + ".png");
		this.width = load.getWidth() * cellSize;
		this.height = load.getHeight() * cellSize;
		this.tiles = new int[width * height];
		switchGates = new SwitchGate[256];
		entities = new ArrayList<Entity>();
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
				} else if (id == 0xFF00) {
					setPlayer(x, y);
				} else if (id == 0xAA0000) {
					setTurret(x, y);
				} else {
					setCell(id, x, y);
				}
			}
		}
		startTime = 0;
	}

	public void nextLevel() {
		currentLevel++;
		generateNewLevel = true;
		if (currentLevel > maxLevels)
			victory = true;
	}

	public void resetLevel() {
		generateNewLevel = true;
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
		switchGates[id].addSwitch(x, y);
		setCell(0, x, y);
	}

	public void setGate(int id, int x, int y) {
		if (switchGates[id] == null)
			switchGates[id] = new SwitchGate();
		switchGates[id].addGate(x, y);
	}

	public void setNextLevel(int x, int y) {
		setCell(0, x, y);
		entities.add(new Stairs(x * cellSize * tileSize + cellSize * tileSize
				/ 2 - tileSize / 2, y * cellSize * tileSize + cellSize
				* tileSize / 2 - tileSize / 2));
	}

	public void setPlayer(int x, int y) {
		setCell(0, x, y);
		entities.add(new Player(x * cellSize * tileSize + cellSize * tileSize
				/ 2 - tileSize / 2, y * cellSize * tileSize + cellSize
				* tileSize / 2 - tileSize / 2));
	}

	public void setTurret(int x, int y) {
		setCell(0, x, y);
		entities.add(new Turret(x * cellSize * tileSize + cellSize * tileSize
				/ 2 - tileSize / 2, y * cellSize * tileSize + cellSize
				* tileSize / 2 - tileSize / 2));
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
		case 0xAA:
			tiles[x + y * width] = 2;
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
				for (int i = 0; i < sg.getSwitchSize(); i++) {
					if (sg.getOpen()) {
						screen.drawShadedRectangle(0xCC00, 0xAA00, 0x8800,
								sg.getSwitchX(i) * cellSize * tileSize
										+ cellSize * tileSize / 2 - tileSize
										/ 2 - xScroll,
								sg.getSwitchY(i) * cellSize * tileSize
										+ cellSize * tileSize / 2 - tileSize
										/ 2 - yScroll, tileSize, tileSize);
					} else {
						screen.drawShadedRectangle(0xCC0000, 0xAA0000,
								0x880000, sg.getSwitchX(i) * cellSize
										* tileSize + cellSize * tileSize / 2
										- tileSize / 2 - xScroll,
								sg.getSwitchY(i) * cellSize * tileSize
										+ cellSize * tileSize / 2 - tileSize
										/ 2 - yScroll, tileSize, tileSize);
					}
				}
			}
		}
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i) instanceof Player) {
				xScroll = (int) (entities.get(i).getX() - screen.getWidth() / 2 + entities
						.get(i).getWidth() / 2);
				xScrollCenter = entities.get(i).getX();
				yScroll = (int) (entities.get(i).getY() - screen.getHeight()
						/ 2 + entities.get(i).getHeight() / 2);
				yScrollCenter = entities.get(i).getY();
			}
			entities.get(i).draw(screen, (int) xScroll, (int) yScroll);
		}

		if (currentLevel == 1 && System.currentTimeMillis() - startTime < tipLength) {
			screen.drawString("Switches turn      lasers off.".substring(0,
					(int) Math.min(30 * (System.currentTimeMillis() - startTime) / (tipLength / 2), 30)),
					8, 8);
		} else if (currentLevel == 1) {
			screen.drawString("Switches turn      lasers off.", 8, 8, 1,
					(float) (1.0 / ((System.currentTimeMillis() - tipLength - startTime) / 500.0) - 0.2));
		}
		
		if (currentLevel == 2 && System.currentTimeMillis() - startTime < tipLength) {
			screen.drawString("Shift to run!".substring(0,
					(int) Math.min(13 * (System.currentTimeMillis() - startTime) / (tipLength / 2), 13)),
					8, 8);
		} else if (currentLevel == 2) {
			screen.drawString("Shift to run!", 8, 8, 1,
					(float) (1.0 / ((System.currentTimeMillis() - tipLength - startTime) / 500.0) - 0.2));
		}
		
		if (currentLevel == 3 && System.currentTimeMillis() - startTime < tipLength) {
			screen.drawString("Bullets also kill  turrets.".substring(0,
					(int) Math.min(27 * (System.currentTimeMillis() - startTime) / (tipLength / 2), 27)),
					8, 8);
		} else if (currentLevel == 3) {
			screen.drawString("Bullets also kill  turrets.", 8, 8, 1,
					(float) (1.0 / ((System.currentTimeMillis() - tipLength - startTime) / 500.0) - 0.2));
		}
		
		if (currentLevel == 4 && System.currentTimeMillis() - startTime < tipLength) {
			screen.drawString("Switches can also  switch off. (Also, bullets aren't     laser-resistant)".substring(0,
					(int) Math.min(73 * (System.currentTimeMillis() - startTime) / (tipLength / 2), 73)),
					8, 8);
		} else if (currentLevel == 4) {
			screen.drawString("Switches can also  switch off. (Also, bullets aren't     laser-resistant)", 8, 8, 1,
					(float) (1.0 / ((System.currentTimeMillis() - tipLength - startTime) / 500.0) - 0.2));
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
		if (generateNewLevel)
			generate(currentLevel);
		if (startTime <= 0) {
			startTime = System.currentTimeMillis();
		}
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).update(delta, this);
		}
		for (SwitchGate sg : switchGates) {
			if (sg != null) {
				for (int i = 0; i < sg.getGateSize(); i++) {
					if (sg.getOpen()) {
						setCell(0, sg.getGateX(i), sg.getGateY(i));
					}
					if (!sg.getOpen()) {
						setCell(0xAA, sg.getGateX(i), sg.getGateY(i));
					}
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
		case 2:
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
