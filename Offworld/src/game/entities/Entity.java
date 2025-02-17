package game.entities;


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
	
	
	public Entity(World world, PVector position){
		this.world = world;
		this.position = new PVector(position.x, position.y, position.z);
	}
	
	
	public Entity(World world) {
		this.world = world;
		this.position = new PVector();
	}
	
	public PVector getPosition() {
		return this.position;
	}
	
	public void setPosition(PVector newPosition) {
		
	}
	
	public float getSpeed() {
		return 0f;
	}
	
	protected void println(String data) {
		System.out.println("[E."+this.getClass().getSimpleName()+"] "+data);
	}
}
