package game.effects;


import main.GameApplet;
import de.articdive.jnoise.generators.noisegen.worley.WorleyNoiseGenerator;
import de.articdive.jnoise.pipeline.JNoise;
import game.core.World;
import processing.core.PGraphics;
import processing.core.PVector;
import processing.opengl.PGraphicsOpenGL;


/**
 * Cosmetic background effect for space exploration. Just some simple twinkling stars that take a given seed and render at a specified
 * coordinate position.
 * @author keyboard
 *
 */
public class BackgroundStars {
	
	/*	Graphical Variables
	 * 	References to the world/main applet.
	 *  Used for setting the seed and others.
	 */
	private PGraphics starBuffer;
	private GameApplet applet;
	private World world;
	
	/**
	 * The current noise generator. Finds a value between 0-1 at a given position.
	 */
	private JNoise noise;
	
	/**
	 * The offset values to help protect against any repetition/symmetry at (0,0)
	 */
	private int randX, randY;
	
	/**
	 * How many pixels to skip by each step.
	 */
	private int step = 2;
	
	/**
	 * Noise value cache.
	 */
	private double noiseVal = 0.0f;
	
	
	/*
	 * Variables for star twinkle effect.
	 */
	private float individualStarTwinkleState = 0f;
	private float starTwinkleTime = 0f;
	
	
	/**
	 * Creates a new BackgroundStars object. Use {@linkplain BackgroundStars#getBuffer()} to actually display the image.
	 * Be sure to call {@linkplain BackgroundStars#draw(int, int)} to actually render what the stars should look like.
	 * @param world reference.
	 * @param seed to use for the given backdrop. Use the current solar system seed.
	 */
	public BackgroundStars(World world, long seed) {
		//Create the buffer to render to.
		this.starBuffer = world.applet.createGraphics(640, 360, World.P3D);
//		((PGraphicsOpenGL) this.starBuffer).textureSampling(2);
		
		//Assign references that are needed later on.
		this.world = world;
		this.applet = world.applet;
		
		//Generate the random offset from the given solar system seed.
		this.applet.randomSeed(seed);
		this.randX = (int) applet.random(80000);
		this.randY = (int ) applet.random(80000);
		
		this.randX += this.randX%2==0?0:1;
		this.randY += this.randY%2==0?0:1;
		
		//Create the noise generator with the supplied seed.
		this.noise = JNoise.newBuilder().white(seed).clamp(0d, 1d).build();
		
		//Lastly set up the initial time for the star to twinkle at.
		this.starTwinkleTime = applet.random(0, 1000);
	}
	
	
	
	/**
	 * Returns the current starBuffer for rendering. Use {@linkplain GameApplet#image(processing.core.PImage, float, float)}
	 * @return the current star buffer image.
	 */
	public PGraphics getBuffer() {
		return this.starBuffer;
	}
	
	private float angle = World.radians(0);
	private int widthChange = 0;
	private int heightChange = 0;
	private float starX = 0f;
	private float starY = 0f;
	/**
	 * The actual update/draw method. Needs an X/Y to draw at, usually being the camera.x and camera.y.
	 * 
	 * 
	 * BE SURE TO FLOOR THE GIVEN VALUE, SO AS TO PASS IN AN INTEGER.
	 * @param offsetX floor({@linkplain game.core.Camera#getX()})
	 * @param offsetY floor({@linkplain game.core.Camera#getY()})
	 */
	public void draw(int offsetX, int offsetY) {
		//Increase the current time.
		this.starTwinkleTime += world.delta;
//		angle += world.delta * 0.5;
		//Begin drawing to buffer.
		this.starBuffer.beginDraw();
		this.starBuffer.background(0);
		
//		Disable stroke for more performance.
		this.starBuffer.noStroke();
		
		this.starBuffer.translate(starBuffer.width/2, starBuffer.height/2);
		this.starBuffer.rotate(-angle);
		
		//Loop over each pixel, skipping by the step, usually 2.
		//If the step is a nonmultiple of 2, we need to adjust in order to keep each star rendered.
		//Just set to -1 (so we loop further) if needed.
		for(int x = -320 + (offsetX%step == 0 ? 0 : -1); x < 320; x+=step) {
			for(int y = -180 + (offsetY%step == 0 ? 0 : -1); y < 180; y+=step) {
				
				//Calculate the noise at the given pixel, with the offset of our render location (camera) and random offset (randX/Y).
				noiseVal = noise.evaluateNoise((x + offsetX + randX), (y + offsetY + randY));
				//If the noise value is above a certain threshold, go ahead and draw a star.
				if(noiseVal > 0.997d) {
					//Figure out how far into the twinkle cycle this star should be.
					individualStarTwinkleState = (float) noise.evaluateNoise((x + offsetX), (y + offsetY)) * 1000;
					
					//Fill a general white outline.
					starBuffer.fill(255, 255, 255, 10);
					starBuffer.rect(x-1, y-1, 3, 3);
					
					//Fill the star white with a certain offset on how far into the twinkle cycle this star is.
					starBuffer.fill(170 + (World.sin(starTwinkleTime + individualStarTwinkleState) * 85));
					starBuffer.rect(x, y, 1, 1);
					
					
				}
			}
		}
		
//		widthChange = World.ceil(World.abs(World.cos(World.radians(angle)) * 140));
//		heightChange = World.ceil(World.abs(World.sin(World.radians(angle)) * 140));
//		int fullBoundX = (widthChange%2==0?widthChange:widthChange-1) + 180 + (offsetX%2 == 0 ? 0 : -1);
//		int fullBoundY = (heightChange%2==0?heightChange:heightChange-1) + 180 + (offsetY%2 == 0 ? 0 : -1);
//		
//		fullBoundX += (fullBoundX%2==0 ? 0:-1);
//		fullBoundY += (fullBoundY%2==0 ? 0:-1);
//		for(int x = -fullBoundX; x < fullBoundX; x+=step) {
//			for(int y = -fullBoundY; y < fullBoundY; y+=step) {
//				
//				//Calculate the noise at the given pixel, with the offset of our render location (camera) and random offset (randX/Y).
//				noiseVal = noise.evaluateNoise((x + (offsetX==2?offsetX-1:offsetX) + randX), (y + (offsetY==2?offsetY-1:offsetY) + randY));
//				//If the noise value is above a certain threshold, go ahead and draw a star.
//				if(noiseVal > 0.997d) {
//					//Figure out how far into the twinkle cycle this star should be.
//					individualStarTwinkleState = (float) noise.evaluateNoise((x + offsetX), (y + offsetY)) * 1000;
//					
//					//Fill a general white outline.
//					starBuffer.fill(255, 255, 255, 10);
//					starBuffer.rect(x-1, y-1, 3, 3);
//					
//					//Fill the star white with a certain offset on how far into the twinkle cycle this star is.
//					starBuffer.fill(170 + (World.sin(starTwinkleTime + individualStarTwinkleState) * 85));
//					starBuffer.rect(x, y, 1, 1);
//					
//					
//				}
//			}
//		}
		
		//End the draw call.
		this.starBuffer.endDraw();
	}
	
	
}
