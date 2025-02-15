package main;


import processing.core.PApplet;
public class Main extends PApplet {
	
	public static GameApplet gameApplet;
	public static boolean debugMode = true;
	/** 
	 * The primary constructor/entry point. Creates the main PApplet.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		gameApplet = new GameApplet(args);
	}

}
