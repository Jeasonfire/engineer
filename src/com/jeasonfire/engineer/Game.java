package com.jeasonfire.engineer;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.jeasonfire.engineer.audio.Sound;
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
	private float screenChange = 0;
	
	// Sounds
	private BufferedImage soundIcon, musicIcon, redX;
	private Rectangle soundBox, musicBox;
	private long justClicked = 0;
	
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
		
		try {
			soundIcon = ImageIO.read(Game.class.getResource("graphics/sprites/soundIcon.png"));
			musicIcon = ImageIO.read(Game.class.getResource("graphics/sprites/musicIcon.png"));
			redX = ImageIO.read(Game.class.getResource("graphics/sprites/redX.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		soundBox = new Rectangle(getWidth() - 18, getHeight() - 36, 16, 16);
		musicBox = new Rectangle(getWidth() - 18, getHeight() - 18, 16, 16);
	}

	public void update(float delta) {
		if (screenChange > 0) {
			screenChange -= delta;
			return;
		}
		if (soundBox.contains(Input.mspWin) && Input.mouseDown && System.currentTimeMillis() - justClicked > 250) {
			Sound.toggleSound();
			Input.mouseDown = false;
			justClicked = System.currentTimeMillis();
			System.out.println("Sound! (" + Sound.SOUND_ON + ")");
		}
		if (musicBox.contains(Input.mspWin) && Input.mouseDown && System.currentTimeMillis() - justClicked > 250) {
			Sound.toggleMusic();
			Input.mouseDown = false;
			justClicked = System.currentTimeMillis();
			System.out.println("Music! (" + Sound.MUSIC_ON + ")");
		}
		screen.update(delta);
		if (screen.nextScreen != null) {
			screen = screen.nextScreen;
			screenChange = 0.2f;
		}
		soundBox = new Rectangle(getWidth() - 18, getHeight() - 36, 16, 16);
		musicBox = new Rectangle(getWidth() - 18, getHeight() - 18, 16, 16);
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
		g.drawImage(soundIcon, soundBox.x, soundBox.y, soundBox.width, soundBox.height, null);
		if (!Sound.SOUND_ON)
			g.drawImage(redX, soundBox.x, soundBox.y, soundBox.width, soundBox.height, null);
		g.drawImage(musicIcon, musicBox.x, musicBox.y, musicBox.width, musicBox.height, null);
		if (!Sound.MUSIC_ON)
			g.drawImage(redX, musicBox.x, musicBox.y, musicBox.width, musicBox.height, null);
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
