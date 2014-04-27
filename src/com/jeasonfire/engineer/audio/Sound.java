package com.jeasonfire.engineer.audio;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {
	public static Sound DEATH = new Sound("death.wav"), FOOTSTEP1 = new Sound(
			"footstep1.wav"), FOOTSTEP2 = new Sound("footstep2.wav"),
			MUSIC1 = new Sound("music1.wav"), MUSIC2 = new Sound("music2.wav"),
			PICKUP = new Sound("pickup.wav"), SHOOT = new Sound("shoot.wav");

	private AudioClip clip;

	public Sound(String filename) {
		clip = Applet.newAudioClip(Sound.class.getResource(filename));
	}

	public void play() {
		new Thread() {
			public void run() {
				clip.play();
			}
		}.start();
	}

	public void loop() {
		new Thread() {
			public void run() {
				clip.loop();
			}
		}.start();
	}

	public void stop() {
		new Thread() {
			public void run() {
				clip.stop();
			}
		}.start();
	}

	public static void stopAll() {
		DEATH.stop();
		FOOTSTEP1.stop();
		FOOTSTEP2.stop();
		MUSIC1.stop();
		MUSIC2.stop();
		PICKUP.stop();
		SHOOT.stop();
	}
}
