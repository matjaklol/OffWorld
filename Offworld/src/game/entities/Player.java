package game.entities;

import processing.core.PVector;
import game.core.World;
import game.userinput.Keyboard;
import game.physics.MVector;
public class Player extends Entity{
	private Keyboard keyboard;
	private MVector vec;
	private float speed = 800.0f;
	public Player(World world) {
		super(world);
		this.keyboard = world.keyboard;
		this.vec = new MVector(world);
		this.vec.maxSpeed(200f);
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
		
		speedVector.setMag(speed);
		this.vec.addVelocity(speedVector);
		this.vec.damping(0.01f);
		this.vec.update();
		
	}
	
	public void update() {
		this.movement();
	}
	
	
}
