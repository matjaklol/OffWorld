package game.solarsystem;


import game.core.*;
import processing.core.PVector;
import java.util.ArrayList;
public class Star extends CelestialBody {
	private float mass;
	private long seed;
	private int planetCount;
	public Star(World world, long seed) {
		super(world, world.gameBuffer);
		this.setPosition(new PVector(0, 0));
		this.seed = seed;
		
		this.mass = world.random(10_000, 50_000);
		this.planetCount = (int) world.random(5);
		
		this.generateChildren();
	}
	
	protected void generateChildren() {
		for(int i = 0; i < this.planetCount; i++) {
			this.children.add(new Planet(world, seed, this));
		}
	}
	
	
	@Override
	protected boolean isVisible() {
		return this.world.camera.inCameraRange(this.position.x, this.position.y, mass);
	}
	
	@Override
	public void draw() {
		
		for(int i = 0; i < this.children.size(); i++) {
			this.children.get(i).draw();
		}
		if(world.keyboard.getKey(32)) {
			graphic.stroke(207, 8, 61);
			graphic.line(position.x, position.y, world.player.getPosition().x, world.player.getPosition().y);
		}
		if(!this.isVisible()) {
			return;
		}
		graphic.pushMatrix();
		graphic.translate(this.position.x, this.position.y, 0);
		graphic.noStroke();
		graphic.fill(237, 201, 21);
		graphic.ellipse(0, 0, this.mass, this.mass);
		graphic.popMatrix();
		
		
		
	}
	
	
	
}
