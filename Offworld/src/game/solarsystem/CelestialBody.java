package game.solarsystem;

import game.core.World;
import game.physics.MVector;
import main.GameApplet;
import processing.core.PVector;
import processing.core.PGraphics;
import java.util.ArrayList;
public class CelestialBody {
	protected MVector position = new MVector();
	protected World world;
	protected PGraphics graphic;
	protected ArrayList<CelestialBody> children = new ArrayList<CelestialBody>();
	public CelestialBody(World world, PGraphics graphic) {
		this.world = world;
		this.graphic = graphic;
	}
	
	public void setPosition(PVector newPosition) {
		this.position.x = newPosition.x;
		this.position.y = newPosition.y;
	}
	
	protected void generateChildren() {
		
	}
	
	protected void movementUpdate() {
		this.position.update();
	}
	
	public void update() {
		this.movementUpdate();
	}
	
	public void draw() {
		graphic.fill(255, 0, 255);
		graphic.ellipse(this.position.x, this.position.y, 25, 25);
	}
	
	
	
}
