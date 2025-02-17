package main;

import game.core.Camera;
import game.core.World;
import game.userinput.Keyboard;
import processing.core.PApplet;
import processing.core.PGraphics;
import processing.opengl.PGraphicsOpenGL;
public class GameApplet extends PApplet {
	
	public PGraphics GameBuffer, HUDBuffer, GameOverlayBuffer;
	public PGraphics vhsImage;
	public World world;
	public Camera camera;
	public Keyboard keyboard;
	/**
	 * GameApplet is the primary rendering application used by the game.
	 * @param args
	 */
	public GameApplet(String[] args) {
		
		System.out.println("Creating GameApplet...");
		
		String[] processingArgs = {"gameApplet"};
		GameApplet gameApplet = this;
		PApplet.runSketch(processingArgs, gameApplet);
		
		System.out.println("GameApplet created.");
		surface.setTitle("OffWorld [Loading]");
		
		this.keyboard = new Keyboard(this);
		
	}
	
	@Override
	public void settings() {
		this.size(1280, 720, GameApplet.P3D);
		this.noSmooth();
	}
	
	@Override
	public void setup() {
		//640x360 rendering resolution (scaled upwards to display resolution).
		int bufferWidth = 640;
		int bufferHeight = 360;
		this.frameRate(1000);
		this.randomSeed(0L);
		this.noiseSeed(0L);
		this.GameBuffer = this.createGraphics(bufferWidth, bufferHeight, GameApplet.P3D);
//		this.GameBuffer.textMode(PApplet.MODEL);
		((PGraphicsOpenGL) this.GameBuffer).textureSampling(2);
//		this.GameBuffer.hint(ENABLE_DEPTH_SORT);
		
		this.vhsImage = createGraphics(bufferWidth, bufferHeight, P3D);
//		((PGraphicsOpenGL) this.vhsImage).textureSampling(2);
		int c = color(0, 0, 0, 30);
		int d = color(20, 20, 20, 40);
//		int c = color(255, 187, 0, 90);
//		int d = color(114,114,114, 90);
		vhsImage.beginDraw();
		vhsImage.noStroke();
//		vhsImage.background(0, 100);
		boolean switchColor = false;
		int step = 3;
		for(int i = 0; i < vhsImage.height; i+=step) {
			vhsImage.fill(switchColor ? c : d);
			vhsImage.rect(0, i, vhsImage.width, step);
			switchColor = !switchColor;
		}
		vhsImage.endDraw();
		
		world = new World(this);
		camera = new Camera(world);
		world.setup();
	}
	
	
	
	public void openPGNoTrans(PGraphics graphic) {
		graphic.beginDraw();
		graphic.background(100, 100, 100, 0);
		
		graphic.pushMatrix();
	}
	/**
	 * Prepares a PGraphics object for rendering. 
	 * @param graphic
	 */
	public void openPGraphics(PGraphics graphic) {
		graphic.beginDraw();
//		graphic.background(100, 100, 100);
		
		graphic.pushMatrix();
		graphic.translate(GameApplet.floor(-this.camera.getX()), GameApplet.floor(-this.camera.getY()));
	}
	
	public void closePGraphics(PGraphics graphic) {
		graphic.popMatrix();
		graphic.endDraw();
	}
	
	public float scaleFactor() {
		return displayWidth/640;
	}
	
	
	public void keyReleased() {
		keyboard.keyReleased();
	}
	
	
	public void keyPressed() {
		keyboard.keyPressed();
	}
	
	@Override
	public void draw() {
		background(0, 0, 0, 0); // mog 114
		
//		fill(255, 0, 0);
//		ellipse(mouseX, mouseY, 25, 25);
		
		camera.update();
//		world.update();
		world.draw();
		
		this.tint(this.lerpColor(color(255, 255, 255), color(255, 0, 0), world.player.getSpeed()/30000f));
		this.image(GameBuffer, 0, 0, width, height);
		this.noTint();
		this.image(vhsImage, 0, 0, width, height);
		strokeWeight(2);
		stroke(255);
		fill(255, 0);
		ellipse(mouseX, mouseY, 9, 9);
		
		world.update();
		surface.setTitle("OffWorld ["+PApplet.round(this.frameRate)+"] FPS");
		
	}
}
