package game.solarsystem;

import game.core.*;
import java.time.Instant;
public class Planet extends CelestialBody {
	private int childCount = 0;
	private float distance;
	private float angle;
	public int color;
	
	private float epochSeconds;
	public Planet(World world, long seed, Star parentStar, float distanceStep) {
		super(world, world.gameBuffer);
		this.seed = seed;
		this.mass = world.random(parentStar.mass/390, parentStar.mass/220);
		this.color = world.color(world.random(50, 150), world.random(50, 150), world.random(50, 150));
		
		
		//Distance is = (distanceStep+n) * sun mass?
		this.distance = ((distanceStep+world.random(0, 1)) * parentStar.mass/2);
		this.angle = world.random(-180, 180);
		
		
		this.position.x = World.cos(angle) * this.distance;
		this.position.y = World.sin(angle) * this.distance;
		
		
		this.childCount = (int) world.random(0, 2);
		this.generateChildren();
	}
	
	@Override
	protected void generateChildren() {
		for(int i = 0; i < this.childCount; i++) {
			//Determine if it should be a moon as the first child:
			CelestialBody child = new Moon(world, seed, i, this);
			this.children.add(child);
		}
	}
	
	@Override
	protected void updateMovement() {
		
		if(this.world.camera.inCameraRange(this.position.x, this.position.y, mass*2)) {
			epochSeconds = CelestialBody.getEpochTime();
			epochSeconds/=(this.mass*1000);
			this.position.x = World.cos(World.radians((float) epochSeconds)+angle) * this.distance;
			this.position.y = World.sin(World.radians((float) epochSeconds)+angle) * this.distance;
		}
		
//		System.out.println("[x: "+this.position.x+", y: "+this.position.y+"]");
	}
	
	
	public void draw() {
		graphic.strokeWeight(3);
		graphic.stroke(this.color, 10);
		graphic.fill(0,0,0,0);
		graphic.ellipse(0, 0, this.distance*2, this.distance*2);
		graphic.noStroke();
		if(world.keyboard.getKey(32)) {
			graphic.stroke(this.color);
			graphic.line(this.position.x, this.position.y, world.player.getPosition().x, world.player.getPosition().y);
		}
		graphic.noStroke();
		graphic.fill(this.color, 100);
		graphic.ellipse(this.position.x, this.position.y, this.mass*1.1f, this.mass*1.1f);
		graphic.fill(this.color);
		graphic.ellipse(this.position.x, this.position.y, this.mass, this.mass);
	}
	
	
}
