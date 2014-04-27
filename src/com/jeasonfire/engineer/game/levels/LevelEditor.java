package com.jeasonfire.engineer.game.levels;

import java.awt.event.KeyEvent;

import javax.swing.JOptionPane;

import com.jeasonfire.engineer.Input;
import com.jeasonfire.engineer.game.entities.Player;
import com.jeasonfire.engineer.game.entities.Stairs;
import com.jeasonfire.engineer.game.entities.Turret;
import com.jeasonfire.engineer.graphics.screens.Screen;
import com.jeasonfire.engineer.graphics.sprites.Sprite;

public class LevelEditor extends Level {
	private float xScrollF = 0, yScrollF = 0;
	private int cursorX = 0, cursorY = 0;
	private int currentTile = 2;

	public LevelEditor() {
		generate(8 * cellSize, 8 * cellSize, 1);
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
			setCell(cellID - 1, cellX, cellY);
			break;
		case 4:
			if (getCellEntity(cellX, cellY) != null
					|| getCellSwitch(cellX, cellY) != null
					|| getCellGate(cellX, cellY) != null)
				break;
			setNextLevel(cellX, cellY);
			break;
		case 5:
			if (getCellEntity(cellX, cellY) != null
					|| getCellSwitch(cellX, cellY) != null
					|| getCellGate(cellX, cellY) != null)
				break;
			setTurret(cellX, cellY);
			break;
		case 6:
			if (getCellEntity(cellX, cellY) != null
					|| getCellSwitch(cellX, cellY) != null
					|| getCellGate(cellX, cellY) != null)
				break;
			setPlayer(cellX, cellY);
			break;
		case 7:
			if (getCellEntity(cellX, cellY) != null
					|| getCellSwitch(cellX, cellY) != null
					|| getCellGate(cellX, cellY) != null)
				break;
			setSwitch(Integer.parseInt(JOptionPane.showInputDialog(null,
					"Enter switch ID:")), cellX, cellY);
			break;
		case 8:
			if (getCellEntity(cellX, cellY) != null
					|| getCellSwitch(cellX, cellY) != null
					|| getCellGate(cellX, cellY) != null)
				break;
			setGate(Integer.parseInt(JOptionPane.showInputDialog(null,
					"Enter gate ID:")), cellX, cellY);
			break;
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
					screen.drawString("S"
							+ getSwitchGateID(getCellSwitch(x, y)), xx, yy
							+ tileSize / 4);
				}
				if (getCellGate(x, y) != null) {
					screen.drawShadedRectangle(0xCC0000, 0xAA0000, 0x880000,
							xx, yy, tileSize, tileSize);
					screen.drawString("G" + getSwitchGateID(getCellGate(x, y)),
							xx, yy + tileSize / 4);
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
		xScroll = (int) xScrollF;
		yScroll = (int) yScrollF;
	}
}
