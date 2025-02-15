package game.core;

import processing.core.*;
import processing.opengl.PGraphicsOpenGL;
import game.effects.BackgroundStars;
import game.entities.Player;
import game.solarsystem.SolarSystem;
import game.userinput.Keyboard;
import main.GameApplet;

public class World extends PApplet{
	private double oldMillis = 0d;
	private double newMillis = 1d;
	public double delta = (newMillis-oldMillis)/1000.0d;
	
	public GameApplet applet;
	public Camera camera;
	public PGraphics gameBuffer;
	
	
	public Keyboard keyboard;
	public BackgroundStars starsEffect;
	public Player player;
	
	public SolarSystem solarSystem;
	public World(GameApplet applet) {
		this.applet = applet;
		this.gameBuffer = applet.GameBuffer;
		
		
		
		this.starsEffect = new BackgroundStars(this, 0L);
		for(int i = 0; i < this.points.length; i++) {
			points[i] = new PVector(applet.random(-640/2, 640/2), applet.random(-360/2, 360/2), applet.random(-50, 200));
		}
		
		this.keyboard = applet.keyboard;
	}
	
	public void setup() {
		
		player = new Player(this);
		camera.focusOn(player);
		
		this.solarSystem = new SolarSystem(this, 0L);
	}
	
	public void update() {
		
//		camera.focusOn(new PVector((float) (camera.getX() + delta), camera.getY()));
//		System.out.println(camera.getX());
		
		player.update();
		solarSystem.update();
		
		
		
		
		oldMillis = newMillis;
		newMillis = applet.millis();
		
		this.delta = (newMillis-oldMillis)/1000.0d;
	}
	
	
	PVector[] points = new PVector[450];
	@Override
	public void draw() {
		
//		applet.openPGNoTrans(starsEffect.getBuffer());
//		starsEffect.draw();
//		applet.closePGraphics(starsEffect.getBuffer());
		this.starsEffect.draw(World.floor(camera.getX()), World.floor(camera.getY()));
		applet.openPGraphics(applet.GameBuffer);
		this.gameBuffer.background(0,0,0);
		this.gameBuffer.image(starsEffect.getBuffer(), World.floor(camera.getX()), World.floor(camera.getY()), gameBuffer.width, gameBuffer.height);
		this.solarSystem.draw();
		
//		this.gameBuffer.fill(0,0,255);
//		this.gameBuffer.ellipse(0, 0, 255, 255);
		
		gameBuffer.text("text lol", 0, 0);
		applet.closePGraphics(gameBuffer);
		keyboard.updateKeys();
		
//		this.update();
		
	}
}
