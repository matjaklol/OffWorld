package game.solarsystem;

import game.core.World;
import game.physics.MVector;
import main.GameApplet;
import processing.core.PVector;
import processing.core.PGraphics;

import java.time.Instant;
import java.util.ArrayList;
import game.core.Camera;
public class CelestialBody {
	protected MVector position = new MVector();
	protected World world;
	protected PGraphics graphic;
	protected long seed;
	
	public float mass = 0f;
	protected ArrayList<CelestialBody> children = new ArrayList<CelestialBody>();
	public CelestialBody(World world, PGraphics graphic) {
		this.world = world;
		this.graphic = graphic;
		this.position.setWorld(world);
	}
	
	
	/**
	 * getEpochTime()
	 * returns a timestamp value in a float form, which updates each call.
	 * This timestamp is useful for anything that orbits as it allows for planets to remain consistent to their orbits even if the game 
	 * is closed. 
	 * @return a float values in the form of (second).(milliseconds). Recommended that you divide this value by at least 1000.
	 */
	public static float getEpochTime() {
		return ((float) (Instant.now().getEpochSecond() - 1739758570L)) + ((float) (Instant.now().getNano()/1_000_000_000d));
		
	}
	public void setPosition(PVector newPosition) {
		this.position.x = newPosition.x;
		this.position.y = newPosition.y;
	}
	
	protected void generateChildren() {
		
	}
	
	/**
	 * isVisible()
	 * returns true if the current object is visible to the camera.
	 * returns false otherwise.
	 * @return
	 */
	protected boolean isVisible() {
		return this.world.camera.inCameraRange(this.position.x, this.position.y, 100f);
	}
	
	protected void updateMovement() {
		this.position.update();
	}
	
	protected void updateChildren() {
		for(int i = 0; i < this.children.size(); i++) {
			this.children.get(i).update();
		}
	}
	public void update() {
		this.updateMovement();
		this.updateChildren();
	}
	
	public void draw() {
		graphic.fill(255, 0, 255);
		graphic.ellipse(this.position.x, this.position.y, 25, 25);
	}
	
	
	
}
