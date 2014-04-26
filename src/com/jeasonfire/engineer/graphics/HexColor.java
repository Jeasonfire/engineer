package com.jeasonfire.engineer.graphics;

public class HexColor {
	private int hex;
	
	public HexColor(int r, int g, int b) {
		hex += (r & 0xFF) << 16;
		hex += (g & 0xFF) << 8;
		hex += b & 0xFF;
	}
	
	public HexColor(int hex) {
		this.hex = hex;
	}
	
	public int getRed() {
		return (hex & 0xFF0000) >> 16;
	}
	
	public int getGreen() {
		return (hex & 0xFF00) >> 8;
	}

	public int getBlue() {
		return (hex & 0xFF);
	}
	
	public int getHex() {
		return hex;
	}
	
	public static int getRed(int hex) {
		return new HexColor(hex).getRed();
	}
	
	public static int getGreen(int hex) {
		return new HexColor(hex).getGreen();
	}
	
	public static int getBlue(int hex) {
		return new HexColor(hex).getBlue();
	}
	
	public static int getHex(int r, int g, int b) {
		return new HexColor(r, g, b).getHex();
	}
}
