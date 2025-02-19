package game.entities;

import processing.core.PVector;
import game.core.Camera;
import game.core.World;
import game.userinput.Keyboard;
import game.physics.MVector;
public class Player extends Entity{
	private Keyboard keyboard;
	private MVector vec;
	private float speed = 25.0f;
	private float maxVelocity = 300f;
	public Player(World world) {
		super(world);
		this.keyboard = world.keyboard;
		world.camera.setFollowMode(Camera.SNAP);
		this.vec = new MVector(world);
		this.vec.maxSpeed(300f);
		this.vec.mass(63);
//		this.vec.maxAcceleration(50f);
		this.vec.setFrictionConstant(0.5f);
		// TODO Auto-generated constructor stub
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
		
		speedVector.mult(speed);
		
		if(keyboard.getKey(16) && speedVector.mag() > 0) {
			this.vec.maxSpeed(90_000);
			this.speed = 2000;
//			world.camera.setFollowMode(Camera.SNAP);
		} else {
//			world.camera.setFollowMode(Camera.LERP);
			this.vec.maxSpeed(300);
			this.speed = 300;
		}
		speedVector.mult(speed);
		this.vec.addVelocity(speedVector);
		this.vec.damping(0.1f);
		this.vec.update();
		
		
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
	
	
}
