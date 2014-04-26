package com.jeasonfire.engineer;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import com.jeasonfire.engineer.graphics.screens.IntroScreen;
import com.jeasonfire.engineer.graphics.screens.Screen;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;

	private int width, height;
	private int[] pixels;
	private BufferedImage screenImage;
	private boolean running;
	private Thread thread;
	private Input input;
	private Screen screen;

	public Game(int width, int height) {
		this.width = width;
		this.height = height;
		screenImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) screenImage.getRaster().getDataBuffer())
				.getData();
		input = new Input(this);
		addKeyListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);
		screen = new IntroScreen(this);
	}

	public void update(float delta) {
		screen.update(delta);
		if (screen.nextScreen != null) {
			screen = screen.nextScreen;
		}
	}

	public void clear() {
		screen.clear();
	}

	public void draw() {
		screen.draw();
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.getPixel(i % width, i / width);
		}
	}

	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		clear();
		draw();

		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		if (getWidth() / getHeight() > width / height) {
			g.drawImage(screenImage,
					(getWidth() - getHeight() * width / height) / 2, 0,
					getHeight() * width / height, getHeight(), null);
		} else {
			g.drawImage(screenImage, 0, (getHeight() - getWidth() / width
					* height) / 2, getWidth(), getWidth() / width * height,
					null);
		}
		g.dispose();
		bs.show();
	}
	
	public int getGameWidth() {
		return width;
	}
	
	public int getGameHeight() {
		return height;
	}

	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Game Thread");
		thread.start();
	}

	public synchronized void stop() {
		running = false;
		new Thread() {
			public void run() {
				try {
					thread.join();
				} catch (InterruptedException ex) {
					ex.printStackTrace();
				}
			}
		}.start();
		System.exit(0);
	}

	public void run() {
		long timer = System.currentTimeMillis();
		float delta = 0;
		int frames = 0;
		long nowTime, lastTime = System.nanoTime();
		while (running) {
			nowTime = System.nanoTime();
			delta = (nowTime - lastTime) / 1000000000.0f;
			render();
			update(delta);
			frames++;
			lastTime = nowTime;
			if (System.currentTimeMillis() - timer > 1000) {
				System.out.println("FPS: " + frames);
				frames = 0;
				timer = System.currentTimeMillis();
			}
		}
	}
}
