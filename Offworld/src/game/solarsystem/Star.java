package game.solarsystem;


import game.core.*;
import processing.core.PVector;
import java.util.ArrayList;
public class Star extends CelestialBody {
	private long seed;
	private int planetCount;
	public Star(World world, long seed) {
		super(world, world.gameBuffer);
		this.setPosition(new PVector(0, 0));
		this.seed = seed;
		
		this.mass = world.random(100_000, 500_000);
		this.planetCount = (int) world.random(5);
		
		this.generateChildren();
		
		float angle = world.random(360);
		world.player.setPosition(new PVector(World.cos(angle) * mass/2, World.sin(angle) * mass/2));
	}
	
	protected void generateChildren() {
		for(int i = 0; i < this.planetCount; i++) {
			this.children.add(new Planet(world, seed, this, i));
		}
	}
	
	
	@Override
	protected boolean isVisible() {
		return this.world.camera.inCameraRange(this.position.x, this.position.y, mass);
	}
	
	
	private float heatzone = 0f;
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
		
		heatzone+=world.delta;
		graphic.pushMatrix();
		graphic.translate(this.position.x, this.position.y, 0);
		graphic.noStroke();
		graphic.fill(237, 201, 21);
		graphic.ellipse(0, 0, this.mass, this.mass);
		graphic.fill(255, 255, 255, 10+(World.sin(heatzone)*40));
		graphic.ellipse(0, 0, this.mass * 2, this.mass * 2);
		graphic.popMatrix();
		
		
		
	}
	
	
	
}
