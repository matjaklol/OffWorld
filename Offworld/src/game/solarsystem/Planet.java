package game.solarsystem;

import game.core.*;
import processing.core.PVector;
import java.time.Instant;
public class Planet extends CelestialBody {
	private int childCount = 0;
	private float distance;
	private float angle;
	public int color;
	private CelestialBody parent;
	
	private float epochSeconds;
	public Planet(World world, long seed, CelestialBody parentStar, float distanceStep, int maxKids, PVector defaultRange) {
		super(world, world.gameBuffer);
		this.parent = parentStar;
		this.seed = seed;
		this.mass = world.random(parentStar.mass/(defaultRange.x==0?400:defaultRange.x), parentStar.mass/(defaultRange.y==0?100:defaultRange.y));
		this.color = world.color(world.random(50, 150), world.random(50, 150), world.random(50, 150));
		
		
		//Distance is = (distanceStep+n) * sun mass?
		this.distance = ((distanceStep+world.random(0, 1)) * parentStar.mass/2);
		this.angle = world.random(-180, 180);
		
		
		this.position.x = World.cos(angle) * this.distance;
		this.position.y = World.sin(angle) * this.distance;
		
		
		this.childCount = (int) world.random(0, maxKids);
		world.player.setPosition(this.position.x + this.parent.position.x, this.position.y + this.parent.position.y);
//		world.player.setPosition(this.position + parentStar.position);
		this.generateChildren();
	}
	
	public Planet(World world, long seed, CelestialBody parent, float distanceStep) {
		this(world, seed, parent, distanceStep, 2, new PVector(300, 50));
	}
	
	@Override
	protected void generateChildren() {
		for(int i = 0; i < this.childCount; i++) {
			//Determine if it should be a moon as the first child:
			CelestialBody child = new Planet(world, seed, this, i+1.5f, 0, new PVector(16, 4));
			this.children.add(child);
		}
	}
	
	@Override
	protected void updateMovement() {
		
		if(this.world.camera.inCameraRange(this.position.x + this.parent.position.x, this.position.y + this.parent.position.y , mass*2)) {
			epochSeconds = CelestialBody.getEpochTime();
			epochSeconds/=(this.mass*100);
			this.position.x = World.cos(World.radians((float) epochSeconds)+angle) * this.distance;
			this.position.y = World.sin(World.radians((float) epochSeconds)+angle) * this.distance;
		}
		
//		System.out.println("[x: "+this.position.x+", y: "+this.position.y+"]");
	}
	
	
	public void draw() {
		for(int i = 0; i < this.children.size(); i++) {
			this.children.get(i).draw();
		}
		
		
		graphic.strokeWeight(3);
		graphic.stroke(this.color, 10);
		graphic.fill(0,0,0,0);
		graphic.ellipse(this.parent.position.x, this.parent.position.y, this.distance*2, this.distance*2);
		graphic.noStroke();
		
		if(world.keyboard.getKey(32)) {
			graphic.stroke(this.color);
			graphic.line(this.position.x + this.parent.position.x, this.position.y + this.parent.position.y , world.player.getPosition().x, world.player.getPosition().y);
		}
		
		if(!this.world.camera.inCameraRange(this.position.x + this.parent.position.x, this.position.y + this.parent.position.y, mass)) {
			return;
		}
		
		
		graphic.noStroke();
		graphic.fill(this.color, 100);
		graphic.ellipse(this.position.x + this.parent.position.x, this.position.y + this.parent.position.y, this.mass*1.1f, this.mass*1.1f);
		graphic.fill(this.color);
		graphic.ellipse(this.position.x + this.parent.position.x, this.position.y + this.parent.position.y, this.mass, this.mass);
		
		
	}
	
	
}
