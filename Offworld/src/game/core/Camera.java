package game.core;

import main.Main;
import processing.core.PVector;
import game.entities.Entity;
public class Camera {
	public static int LERP = 1;
	public static int SNAP = 0;
	
	private World world;
	private Entity entity;
	private PVector goalVector = new PVector(0, 0);
	
	private float x = 0f;
	private float y = 0f;
	
	
	public Camera(World world, float x, float y) {
		this.world = world;
		world.camera = this;
		this.x = x;
		this.y = y;
		
		println("Initialized at ("+x+", "+y+")");
	}
	
	public Camera(World world) {
		this(world, 0f, 0f);
	}
	
	
	
	
	/**
	 * Debug printout method. Good for debugging any issues that I might have in regards to the camera.
	 * @param value
	 */
	public void println(String value) {
		if(!Main.debugMode) {
			return;
		}
		System.out.println("[CAMERA] "+value);
	}
	
	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}
	/**
	 * Focuses on a new entity, ensuring that we smoothly interpolate to their position.
	 * Passes via reference, so don't clone any entities to the camera (PLEASE).
	 * @param e
	 */
	public void focusOn(Entity e) {
		this.entity = e;
		this.println("Focusing on a new entity. "+e);
	}
	
	/**
	 * Focuses on a given PVector, and if it was previously focused on an entity it will switch. PVector > Entity.
	 * @param p PASSES AS A COPY
	 */
	public void focusOn(PVector p) {
		this.entity = null;
		
		//Save the new position.
		this.goalVector.x = p.x;
		this.goalVector.y = p.y;
	}
	
	
	private boolean mouseCalculated = false;
	private PVector mouseVector = new PVector();
	public PVector getMouseInWorld() {
		//Our vector cache.
		if(this.mouseCalculated) {
			return new PVector(this.mouseVector.x, this.mouseVector.y);
		}
		
		//Convert the mouse's X/Y coordinates to screen coordinates.
		float x = ((float) Main.gameApplet.mouseX / (float) Main.gameApplet.width) * (640f);
		float y = ((float) Main.gameApplet.mouseY / (float) Main.gameApplet.height) * (360f);
		
		//Add the current camera offset.
		x+=this.x;
		y+=this.y;
		
		//Update the vector cache.
		this.mouseVector.x = x;
		this.mouseVector.y = y;
		this.mouseCalculated = true;
		
		//Return the vector.
		return new PVector(this.mouseVector.x, this.mouseVector.y);
	}
	
	private float lerpSpeed = 0.3f;
	public void setLerpSpeed(float f) {
		this.lerpSpeed = f;
	}
	
	public void resetLerpSpeed() {
		this.lerpSpeed = 0.6f;
	}
	
	private boolean snapping = false;
	public void setFollowMode(int value) {
		switch(value) {
		case 0: this.snapping = true;
		break;
		case 1: this.snapping = false;
		break;
		default: setFollowMode(1);
		}
	}
	
	
	
	
	public void update() {
		if(entity != null) {
			//Our goal (ensuring that the entity's coordinates are in the center of the screen).
			float goalX = entity.getPosition().x - 640/2;
			float goalY = entity.getPosition().y - 360/2;
			
			//The amount of influence the mouse should have over the camera.
			float amt = 8;
			
			//Adds a small offset to the camera target based on the player's mouse position.
			goalX += Main.gameApplet.mouseX/amt - Main.gameApplet.width/(amt*2);
			goalY += Main.gameApplet.mouseY/amt - Main.gameApplet.height/(amt*2);
			
			
			if(snapping) {
				x = goalX;
				y = goalY;
				mouseCalculated = false;
				return;
			}
			
			//Calculate the velocity needed to get to the given position, as a fraction (lerpSpeed).
			float lerpX = World.lerp(x, goalX, lerpSpeed) - x;
			float lerpY = World.lerp(y, goalY, lerpSpeed) - y;
			
			//Apply the necessary velocity to the camera, updating the position.
			this.x += lerpX * world.delta * 20;
			this.y += lerpY * world.delta * 20;
		} else {
			
			float goalX = goalVector.x - 640/2;
			float goalY = goalVector.y - 360/2;
			
			//Debug mouse offset
			goalX += Main.gameApplet.mouseX/8 - Main.gameApplet.width/(16);
			goalY += Main.gameApplet.mouseY/8 - Main.gameApplet.height/(16);
			
			//If we're focusing on a position instead, no need to calculate the mouse offset.
//			float lerpX = Main.lerp(x, goalVector.x-640/2, lerpSpeed) - x;
//			float lerpY = World.lerp(y, goalVector.y-360/2, lerpSpeed) - y;
			
			if(snapping) {
				x = goalX;
				y = goalY;
				mouseCalculated = false;
				return;
			}
			float lerpX = Main.lerp(x, goalX, lerpSpeed) - x;
			float lerpY = World.lerp(y, goalY, lerpSpeed) - y;
			//Still need to make it a smooth transition however.
			x += lerpX * (float) world.delta;
			y += lerpY * (float) world.delta;
		}
		
		mouseCalculated = false;
	}
	
	
	/**
	 * inCameraRange()
	 * calculates if a given circle is within the viewbox of the camera.
	 * 
	 * @param otherX
	 * @param otherY
	 * @param size
	 * @return true if the sphere of a given size is visible to the camera.
	 */
	public boolean inCameraRange(float otherX, float otherY, float size) {
		float testX, testY;
		if(otherX < this.x) {
			testX = this.x-320f;
		} else {
			testX = this.x+320f;
		}
		
		if(otherY < this.y) {
			testY = this.y-320f;
		} else {
			testY = this.y+320f;
		}
		
		float distX = otherX-testX;
		float distY = otherY-testY;
		float distance = World.sqrt((distX * distX) + (distY * distY));
		
		return distance <= size;
	}
}
