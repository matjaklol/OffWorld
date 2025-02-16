package game.solarsystem;

import game.core.*;
public class Planet extends CelestialBody {
	private int childCount = 0;
	public int color;
	public Planet(World world, long seed, Star parentStar, int distanceStep) {
		super(world, world.gameBuffer);
		this.seed = seed;
		this.mass = world.random(parentStar.mass/390, parentStar.mass/220);
		this.color = world.color(world.random(50, 150), world.random(50, 150), world.random(50, 150));
		//Distance is = (distanceStep+n) * sun mass?
		float angle = world.random(-180, 180);
		this.position.x = World.cos(angle) * ((distanceStep+2) * parentStar.mass/2);
		this.position.y = World.sin(angle) * ((distanceStep+2) * parentStar.mass/2);
		
		
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
	
	
	public void draw() {
		if(world.keyboard.getKey(32)) {
			graphic.stroke(this.color);
			graphic.line(this.position.x, this.position.y, world.player.getPosition().x, world.player.getPosition().y);
		}
		graphic.noStroke();
		graphic.fill(this.color);
		graphic.ellipse(this.position.x, this.position.y, this.mass, this.mass);
	}
	
	
}
