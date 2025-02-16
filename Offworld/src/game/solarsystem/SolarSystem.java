package game.solarsystem;

import game.core.*;
import main.GameApplet;
import java.util.ArrayList;
public class SolarSystem {
	private long seed;
	private World world;
	private ArrayList<CelestialBody> children = new ArrayList<CelestialBody>();
	public SolarSystem(World world, long seed) {
		this.world = world;
		this.seed = seed;
		world.randomSeed(seed);
		generateSystem();
	}
	
	private void generateSystem() {
		//Generate the central star(s).
		this.children.add(new Star(world, seed));
		
	}
	
	
	public void update() {
		for(int i = 0; i < this.children.size(); i++) {
			this.children.get(i).update();
		}
	}
	
	
	public void draw() {
		for(int i = 0; i < this.children.size(); i++) {
			this.children.get(i).draw();
		}
	}
	
}
