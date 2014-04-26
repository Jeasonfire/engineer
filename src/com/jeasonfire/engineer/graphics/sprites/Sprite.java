package com.jeasonfire.engineer.graphics.sprites;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Sprite {
	private int width, height;
	private int[] pixels;
	
	public Sprite(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		Random random = new Random();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pixels[x + y * width] = random.nextInt();
			}
		}
	}
	
	public Sprite(String file) {
		try {
			BufferedImage img = ImageIO.read(Sprite.class.getResource(file));
			this.width = img.getWidth();
			this.height = img.getHeight();
			pixels = new int[width * height];
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					pixels[x + y * width] = img.getRGB(x, y) & 0xFFFFFF;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public Sprite cut(int x, int y, int width, int height) {
		Sprite result = new Sprite(width, height);
		for (int yp = 0; yp < height; yp++) {
			int yy = yp + y;
			for (int xp = 0; xp < width; xp++) {
				int xx = xp + x;
				result.setPixel(getPixel(xx, yy), xp, yp);
			}
		}
		return result;
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
