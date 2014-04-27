package com.jeasonfire.engineer.graphics.screens;

import com.jeasonfire.engineer.Game;
import com.jeasonfire.engineer.graphics.HexColor;
import com.jeasonfire.engineer.graphics.sprites.Sprite;

public abstract class Screen {
	protected int width, height;
	private int[] pixels;
	public final Game game;
	public Screen nextScreen = null;

	private static Sprite charactersImg = new Sprite("font.png");
	private static String charactersStr = "abcdefghijklmnopqrstuvwxyz0123456789!?.,-_+*/@%|<>();:'\"";
	private static int charSize = charactersImg.getHeight();

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

	public void drawString(String text, int x, int y) {
		drawString(text, x, y, 1);
	}

	public void drawString(String text, int x, int y, int scale) {
		drawString(text, x, y, scale, 1.0f);
	}

	public void drawString(String text, int x, int y, int scale,
			float transparency) {
		drawString(text, x, y, scale, transparency, (width - x) / charSize);
	}

	public void drawString(String s, int x, int y, int scale,
			float transparency, int wrapWidth) {
		int yOffset = 0;
		String text = s.toLowerCase();
		x /= scale;
		y /= scale;
		for (int i = 0; i < text.length(); i++) {
			if (i - yOffset * wrapWidth >= wrapWidth)
				yOffset++;
			drawChar(text.charAt(i), x + i * charSize - yOffset * wrapWidth
					* charSize, y + yOffset * charSize, scale, transparency);
		}
	}

	public void drawChar(char c, int x, int y, int scale, float transparency) {
		for (int i = 0; i < charactersStr.length(); i++) {
			if (charactersStr.charAt(i) == c) {
				drawSprite(
						charactersImg.cut(i * charSize, 0, charSize, charSize),
						x * scale, y * scale, scale, transparency);
			}
		}
	}

	public void drawSprite(Sprite sprite, int x, int y) {
		drawSprite(sprite, x, y, 1);
	}

	public void drawSprite(Sprite sprite, int x, int y, int scale) {
		drawSprite(sprite, x, y, scale, 1.0f);
	}

	public void drawSprite(Sprite sprite, int x, int y, int scale,
			float transparency) {
		for (int yp = 0; yp < sprite.getHeight(); yp++) {
			int yy = yp * scale + y;
			for (int xp = 0; xp < sprite.getWidth(); xp++) {
				int xx = xp * scale + x;
				if (sprite.getPixel(xp, yp) != 0xFF00FF)
					drawRectangle(sprite.getPixel(xp, yp), xx, yy, scale,
							scale, transparency);
			}
		}
	}

	public void drawShadedRectangle(int brightColor, int midColor,
			int shadowColor, int x, int y, int w, int h) {
		drawShadedRectangle(brightColor, midColor, shadowColor, x, y, w, h, 1.0f);
	}

	public void drawShadedRectangle(int brightColor, int midColor,
			int shadowColor, int x, int y, int w, int h, float transparency) {
		drawRectangle(brightColor, x, y, w - 1, 1, transparency);
		drawRectangle(brightColor, x, y + 1, 1, h - 1, transparency);
		drawRectangle(midColor, x + 1, y + 1, w - 2, h - 2, transparency);
		drawRectangle(shadowColor, x + w - 1, y, 1, h, transparency);
		drawRectangle(shadowColor, x + 1, y + h - 1, w - 1, 1, transparency);
	}

	public void drawRectangle(int color, int x, int y, int w, int h) {
		drawRectangle(color, x, y, w, h, 1.0f);
	}

	public void drawRectangle(int color, int x, int y, int w, int h,
			float transparency) {
		if (transparency > 1) {
			transparency = 1;
		}
		if (transparency < 0) {
			transparency = 0;
		}
		for (int yp = 0; yp < h; yp++) {
			int yy = yp + y;
			for (int xp = 0; xp < w; xp++) {
				int xx = xp + x;
				if (transparency < 1f) {
					int r = (int) (HexColor.getRed(color) * transparency + HexColor
							.getRed(getPixel(xx, yy))) & 0xFF;
					int g = (int) (HexColor.getGreen(color) * transparency + HexColor
							.getGreen(getPixel(xx, yy))) & 0xFF;
					int b = (int) (HexColor.getBlue(color) * transparency + HexColor
							.getBlue(getPixel(xx, yy))) & 0xFF;
					int c = HexColor.getHex(r, g, b);
					setPixel(c, xx, yy);
				} else {
					setPixel(color, xx, yy);
				}
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
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}
