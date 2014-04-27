package com.jeasonfire.engineer.audio;

import java.applet.Applet;
import java.applet.AudioClip;

public class Sound {
	public static Sound DEATH = new Sound("death.wav"), FOOTSTEP1 = new Sound(
			"footstep1.wav"), FOOTSTEP2 = new Sound("footstep2.wav"),
			MUSIC1 = new Sound("music1.wav"), MUSIC2 = new Sound("music2.wav"),
			PICKUP = new Sound("pickup.wav"), SHOOT = new Sound("shoot.wav");
	public static boolean MUSIC_ON = true, SOUND_ON = true, justSoundOn = false, justMusicOn = false;
	
	private AudioClip clip;
	private String name;

	public Sound(String filename) {
		clip = Applet.newAudioClip(Sound.class.getResource(filename));
		name = filename.substring(0, filename.length() - 4);
	}

	public void play() {
		if (!MUSIC_ON && (name.equals("music1") || name.equals("music2")))
			return;
		if (!SOUND_ON && !name.equals("music1") && !name.equals("music2"))
			return;
		new Thread() {
			public void run() {
				clip.play();
			}
		}.start();
	}

	public void loop() {
		if (!MUSIC_ON && (name.equals("music1") || name.equals("music2")))
			return;
		if (!SOUND_ON && !name.equals("music1") && !name.equals("music2"))
			return;
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

	public static void toggleSound() {
		SOUND_ON = !SOUND_ON;
		if (!SOUND_ON) {
			DEATH.stop();
			FOOTSTEP1.stop();
			FOOTSTEP2.stop();
			PICKUP.stop();
			SHOOT.stop();
		}
		if (SOUND_ON)
			justSoundOn = true;
	}

	public static void toggleMusic() {
		MUSIC_ON = !MUSIC_ON;
		if (!MUSIC_ON) {
			MUSIC1.stop();
			MUSIC2.stop();
		}
		if (MUSIC_ON)
			justMusicOn = true;
	}
}
