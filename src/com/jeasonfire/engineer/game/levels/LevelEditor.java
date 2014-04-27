package com.jeasonfire.engineer.game.levels;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import com.jeasonfire.engineer.Input;
import com.jeasonfire.engineer.game.entities.Entity;
import com.jeasonfire.engineer.game.entities.Player;
import com.jeasonfire.engineer.game.entities.Stairs;
import com.jeasonfire.engineer.game.entities.Turret;
import com.jeasonfire.engineer.graphics.HexColor;
import com.jeasonfire.engineer.graphics.screens.Screen;
import com.jeasonfire.engineer.graphics.sprites.Sprite;

public class LevelEditor extends Level {
	private float xScrollF = 0, yScrollF = 0;
	private int cursorX = 0, cursorY = 0;
	private int currentTile = 2;

	public LevelEditor() {
		generate(
				Integer.parseInt(JOptionPane.showInputDialog(null,
						"Enter level width:")) * cellSize,
				Integer.parseInt(JOptionPane.showInputDialog(null,
						"Enter level height:")) * cellSize, 1);
	}

	/**
	 * 1-3: - Tiles 4: - Stairs 5: - Turret 6: - Player
	 */
	public void placeCell(int cellX, int cellY, int cellID) {
		if (cellX < 0 || cellX >= width / cellSize || cellY < 0
				|| cellY >= height / cellSize)
			return;
		switch (cellID) {
		case 1:
		case 2:
		case 3:
			removeCellEntity(cellX, cellY);
			removeCellGate(cellX, cellY);
			removeCellSwitch(cellX, cellY);
			setCell(cellID - 1, cellX, cellY);
			break;
		case 4:
			if (getCellEntity(cellX, cellY) != null
					|| getCellSwitch(cellX, cellY) != null
					|| getCellGate(cellX, cellY) != null)
				break;
			removeCellEntity(cellX, cellY);
			removeCellGate(cellX, cellY);
			removeCellSwitch(cellX, cellY);
			setNextLevel(cellX, cellY);
			break;
		case 5:
			if (getCellEntity(cellX, cellY) != null
					|| getCellSwitch(cellX, cellY) != null
					|| getCellGate(cellX, cellY) != null)
				break;
			removeCellEntity(cellX, cellY);
			removeCellGate(cellX, cellY);
			removeCellSwitch(cellX, cellY);
			setTurret(cellX, cellY);
			break;
		case 6:
			if (getCellEntity(cellX, cellY) != null
					|| getCellSwitch(cellX, cellY) != null
					|| getCellGate(cellX, cellY) != null)
				break;
			removeCellEntity(cellX, cellY);
			removeCellGate(cellX, cellY);
			removeCellSwitch(cellX, cellY);
			setPlayer(cellX, cellY);
			break;
		case 7:
			if (getCellEntity(cellX, cellY) != null
					|| getCellSwitch(cellX, cellY) != null
					|| getCellGate(cellX, cellY) != null)
				break;
			removeCellEntity(cellX, cellY);
			removeCellGate(cellX, cellY);
			removeCellSwitch(cellX, cellY);
			setSwitch(Integer.parseInt(JOptionPane.showInputDialog(null,
					"Enter switch ID:")), cellX, cellY);
			break;
		case 8:
			if (getCellEntity(cellX, cellY) != null
					|| getCellSwitch(cellX, cellY) != null
					|| getCellGate(cellX, cellY) != null)
				break;
			removeCellEntity(cellX, cellY);
			removeCellGate(cellX, cellY);
			removeCellSwitch(cellX, cellY);
			setGate(Integer.parseInt(JOptionPane.showInputDialog(null,
					"Enter gate ID:")), cellX, cellY);
			break;
		}
	}

	public void save() {
		try {
			File file = new File(JOptionPane.showInputDialog(null,
					"Enter a filename:"));
			file.createNewFile();
			BufferedImage img = new BufferedImage(width / cellSize, height
					/ cellSize, BufferedImage.TYPE_INT_RGB);
			int[] pixels = ((DataBufferInt) img.getRaster().getDataBuffer())
					.getData();
			for (int y = 0; y < height / cellSize; y++) {
				for (int x = 0; x < width / cellSize; x++) {
					pixels[x + y * (width / cellSize)] = getRGB(x, y);
				}
			}
			ImageIO.write(img, "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public int getRGB(int x, int y) {
		if (getCellEntity(x, y) != null) {
			Entity e = getCellEntity(x, y);
			if (e instanceof Stairs)
				return 0xFFFFFFFF;
			if (e instanceof Turret)
				return 0xFFAA0000;
			if (e instanceof Player)
				return 0xFF00FF00;
		}
		if (getCellSwitch(x, y) != null) {
			return HexColor.getHex(0xFF, getCellSwitch(x, y).id, 0) + 0xFF000000;
		}
		if (getCellGate(x, y) != null) {
			return HexColor.getHex(0xFF, 0, getCellGate(x, y).id) + 0xFF000000;
		}
		switch (getCell(x, y)) {
		default:
		case 0:
			return 0;
		case 1:
			return 0xFF0000FF;
		case 2:
			return 0xFF0000AA;
		}
	}

	public Sprite getCurrentCellSprite(int cellID) {
		switch (cellID) {
		case 1:
		case 2:
		case 3:
			return getTileSprite(cellID - 1);
		case 4:
			return Stairs.SPRITE;
		case 5:
			return Turret.SPRITE;
		case 6:
			return Player.SPRITE;
		default:
			return new Sprite(tileSize, tileSize);
		}
	}

	public void draw(Screen screen) {
		for (int y = 0; y < height / cellSize; y++) {
			int yy = y * tileSize - yScroll;
			if (yy < -tileSize || yy >= screen.getHeight())
				continue;
			for (int x = 0; x < width / cellSize; x++) {
				int xx = x * tileSize - xScroll;
				if (xx < -tileSize || xx >= screen.getWidth())
					continue;
				screen.drawSprite(getTileSprite(getCell(x, y)), xx, yy);
				if (getCellEntity(x, y) != null) {
					screen.drawSprite(getCellEntity(x, y).sprite, xx, yy);
				}
				if (getCellSwitch(x, y) != null) {
					screen.drawShadedRectangle(0xCC0000, 0xAA0000, 0x880000,
							xx, yy, tileSize, tileSize);
					screen.drawString("S" + getCellSwitch(x, y).id, xx, yy
							+ tileSize / 4);
				}
				if (getCellGate(x, y) != null) {
					screen.drawShadedRectangle(0xCC0000, 0xAA0000, 0x880000,
							xx, yy, tileSize, tileSize);
					screen.drawString("G" + getCellGate(x, y).id, xx, yy
							+ tileSize / 4);
				}
			}
		}
		if (currentTile < 7) {
			screen.drawSprite(getCurrentCellSprite(currentTile), cursorX
					* tileSize - xScroll, cursorY * tileSize - yScroll, 1, 0.4f);
		} else if (currentTile == 7) {
			screen.drawShadedRectangle(0xCC0000, 0xAA0000, 0x880000, cursorX
					* tileSize - xScroll, cursorY * tileSize - yScroll,
					tileSize, tileSize, 0.4f);
			screen.drawString("S?", cursorX * tileSize - xScroll, cursorY
					* tileSize - yScroll + tileSize / 4, 1, 0.4f);
		} else if (currentTile == 8) {
			screen.drawShadedRectangle(0xCC0000, 0xAA0000, 0x880000, cursorX
					* tileSize - xScroll, cursorY * tileSize - yScroll,
					tileSize, tileSize, 0.4f);
			screen.drawString("G?", cursorX * tileSize - xScroll, cursorY
					* tileSize - yScroll + tileSize / 4, 1, 0.4f);
		}
	}

	public void update(float delta) {
		cursorX = (Input.msp.x + xScroll) / tileSize;
		cursorY = (Input.msp.y + yScroll) / tileSize;

		if (Input.keys[KeyEvent.VK_1]) {
			currentTile = 1;
		}
		if (Input.keys[KeyEvent.VK_2]) {
			currentTile = 2;
		}
		if (Input.keys[KeyEvent.VK_3]) {
			currentTile = 3;
		}
		if (Input.keys[KeyEvent.VK_4]) {
			currentTile = 4;
		}
		if (Input.keys[KeyEvent.VK_5]) {
			currentTile = 5;
		}
		if (Input.keys[KeyEvent.VK_6]) {
			currentTile = 6;
		}
		if (Input.keys[KeyEvent.VK_7]) {
			currentTile = 7;
		}
		if (Input.keys[KeyEvent.VK_8]) {
			currentTile = 8;
		}

		if (Input.mouseDown) {
			placeCell(cursorX, cursorY, currentTile);
			if (currentTile > 6)
				Input.mouseDown = false;
		}

		if (Input.keys[KeyEvent.VK_W] || Input.keys[KeyEvent.VK_I]
				|| Input.keys[KeyEvent.VK_UP]) {
			yScrollF -= delta * 200f;
		}
		if (Input.keys[KeyEvent.VK_A] || Input.keys[KeyEvent.VK_J]
				|| Input.keys[KeyEvent.VK_LEFT]) {
			xScrollF -= delta * 200f;
		}
		if (Input.keys[KeyEvent.VK_S] || Input.keys[KeyEvent.VK_K]
				|| Input.keys[KeyEvent.VK_DOWN]) {
			yScrollF += delta * 200f;
		}
		if (Input.keys[KeyEvent.VK_D] || Input.keys[KeyEvent.VK_L]
				|| Input.keys[KeyEvent.VK_RIGHT]) {
			xScrollF += delta * 200f;
		}
		if (Input.keys[KeyEvent.VK_Z]) {
			save();
			Input.keys[KeyEvent.VK_Z] = false;
		}
		xScroll = (int) xScrollF;
		yScroll = (int) yScrollF;
	}
}
