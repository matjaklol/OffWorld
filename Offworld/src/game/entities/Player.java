package game.entities;

import processing.core.PGraphics;
import processing.core.PVector;

import java.lang.reflect.Method;

import game.core.Camera;
import game.core.World;
import main.GameApplet;
import main.Main;
import game.userinput.Keyboard;
import game.physics.MVector;
import processing.event.MouseEvent;


public class Player extends Entity{
	private Keyboard keyboard;
	private MVector vec;
	private float speed = 500.0f;
	public Player(World world) {
		super(world);
		
		
		this.keyboard = world.keyboard;
		this.vec = new MVector(world);
		this.vec.maxSpeed(300f);
		this.vec.mass(63);
		
		this.vec.setFrictionConstant(0.4f);
		
		Main.gameApplet.mouse.registerMethod("mouseEvent", this);
	}
	
	private void movement() {
		this.position.x = vec.x;
		this.position.y = vec.y;
		
		PVector speedVector = new PVector(0, 0);
		if(keyboard.getKey(83)) {
//			this.vec.impulse(0, speed);
			speedVector.y += 1;
//			this.position.y += world.delta * speed;
		}
		if(keyboard.getKey(87)) {
			speedVector.y += -1;
//			this.position.y -= world.delta * speed;
//			this.vec.impulse(0, -speed);
		}
		
		if(keyboard.getKey(65)) {
//			this.position.x -= world.delta * speed;
			speedVector.x -= 1;
//			this.vec.impulse(-speed, 0);
		} 
		if(keyboard.getKey(68)) {
//			this.position.x += world.delta * speed;
			speedVector.x += 1;
//			this.vec.impulse(speed, 0);
		}
		
		if(keyboard.getKey(16) && speedVector.mag() > 0) {
			this.vec.maxSpeed(90_000);
			this.speed = 1600f;
			world.camera.setFollowMode(Camera.SNAP);
		} else {
			world.camera.setFollowMode(Camera.LERP);
			this.speed = 500f;
			this.vec.maxSpeed(300);
		}
		
		speedVector.setMag(speed);
		this.vec.addVelocity(speedVector);
		this.vec.damping(0.01f);
		this.vec.update();
		
		
	}
	
	public void mouseEvent(MouseEvent event) {
		if(event.getAction() == MouseEvent.MOVE) {
			println("MOVED");
			return;
		}
		if(event.getAction() == MouseEvent.CLICK && event.getButton() == World.LEFT) {
			this.println("CLICKED!!!!");
		}
		
		if(event.getAction() == MouseEvent.WHEEL) {
			int amount = event.getCount();
			if(amount > 0) {
				println("Zooming out? "+amount);
			} else {
				println("Zooming in? " + amount);
			}
		}
	}
	public float getSpeed() {
		return vec.getSpeed();
	}
	public void setPosition(PVector position) {
		this.position = position;
		this.vec.x = position.x;
		this.vec.y = position.y;
	}
	
	public void update() {
		this.movement();
	}

	public void setPosition(MVector position) {
		this.position = new PVector(position.x, position.y);
		this.vec.x = position.x;
		this.vec.y = position.y;
		
	}

	public void setPosition(float x, float y) {
		this.setPosition(new PVector(x, y));
		
	}
	
	
	private int myWidth = 8;
	public void draw() {
		this.graphic.fill(255, 255, 255);
		this.graphic.noStroke();
		this.graphic.rect(World.round(this.position.x - myWidth/2), World.round(this.position.y - myWidth/2), myWidth, myWidth);
	}
	
	
}
