package game.entities;


import processing.core.PGraphics;
import processing.core.PVector;
import game.core.World;
/**
 * Generic Entity, from which all entities inherit behavior.
 * @author PC1
 *
 */
public class Entity {
	protected PVector position;
	protected World world;
	protected PGraphics graphic;
	
	
	public Entity(World world, PVector position){
		this.world = world;
		this.graphic = world.gameBuffer;
		this.position = new PVector(position.x, position.y, position.z);
	}
	
	
	public Entity(World world) {
		this(world, new PVector());
	}
	
	public PVector getPosition() {
		return this.position;
	}
	
	public void setPosition(PVector newPosition) {
		
	}
	
	public float getSpeed() {
		return 0f;
	}
	
	public void draw() {
		
	}
	
	protected void println(String data) {
		System.out.println("[E."+this.getClass().getSimpleName()+"] "+data);
	}
}
