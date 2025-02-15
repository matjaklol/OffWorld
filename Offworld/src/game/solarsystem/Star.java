package game.solarsystem;


import game.core.*;
import processing.core.PVector;
import java.util.ArrayList;
public class Star extends CelestialBody {
	private float mass;
	public Star(World world, long seed) {
		super(world, world.gameBuffer);
		this.setPosition(new PVector(0, 0));
		
		world.randomSeed(seed);
		this.mass = world.random(10_000, 50_000);
		graphic.sphereDetail(20);
	}
	
	
	@Override
	public void movementUpdate() {
		//No movement
	}
	
	
	public void update() {
		for(int i = 0; i < this.children.size(); i++) {
			this.children.get(i).update();
		}
	}
	
	public void draw() {
		graphic.pushMatrix();
		graphic.translate(this.position.x, this.position.y, -500);
//		graphic.fill(255);
		graphic.noStroke();
//		graphic.stroke(255,255,255);
		graphic.fill(237, 201, 21);
//		graphic.noFill();
		graphic.ellipse(0, 0, this.mass, this.mass);
//		graphic.sphere(400);
		graphic.popMatrix();
		
		
		for(int i = 0; i < this.children.size(); i++) {
			this.children.get(i).draw();
		}
	}
	
	
	
}
