package game.physics;

import processing.core.PVector;
import game.entities.Entity;
import game.core.World;
import main.*;

/**
 * A general purpose physics-based vector class, good for {@link Entity} positions/game objects.
 * Built in a similar manner to the {@link PVector} class
 * @author keyboard
 * @see {@linkplain PVector}
 */
public class MVector {
	public float x = 0.0f;
	public float y = 0.0f;
	public float z = 0.0f;
	private float maxSpeed = 100.0f;
	private float velX = 0.0f;
	private float velY = 0.0f;
	private float velZ = 0.0f;
	
	private float maxAcceleration = 100.0f;
	private float accX = 0.0f;
	private float accY = 0.0f;
	private float accZ = 0.0f;
	
	private float frictionConstant = 0.1f;
	private float mass = 20;
	private World world = null;
	
	
	/**
	 * Specific vector constructor that does not set a world reference. Be sure to call {@linkplain MVector#setWorld(World)} 
	 * if you plan on using this vector for more physics-based things.
	 * @param x
	 * @param y
	 * @param z
	 */
	public MVector(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Sets up this vector in a manner such that it is at a specific coordinate location along with having
	 * a world reference for a more advanced use case (specifically game physics).
	 * @param x
	 * @param y
	 * @param z
	 * @param world
	 */
	public MVector(float x, float y, float z, World world) {
		this(x, y, z);
		this.setWorld(world);
	}
	
	
	/**
	 * Simple vector construction. Please call {@linkplain MVector#setWorld(World)} to set the world reference
	 * for more advanced usage.
	 */
	public MVector() {
		this(0f, 0f, 0f);
	}
	
	
	/**
	 * Easy constructor for an MVector. Useful if you want a physics-based vector that originates at (0, 0, 0).
	 * @param world
	 */
	public MVector(World world) {
		this(0f, 0f, 0f, world);
	}
	
	
	/**
	 * Sets a world reference for this MVector to run from. Please assign a world
	 * reference if this object is to be used for more than just a simple vector.
	 * @param world
	 */
	public void setWorld(World world) {
		this.world = world;
	}
	
	/**
	 * Applies a singular impulse to this object, from a given PVector.
	 * @param impulse
	 * @see {@linkplain MVector#addVelocity(float, float, float)}
	 */
	public void addVelocity(PVector impulse) {
		this.addVelocity(impulse.x, impulse.y, impulse.z);
	}
	
	/**
	 * Applies a singular impulse to this object, from a given MVector.
	 * @param impulse
	 * @see {@link MVector#addVelocity(PVector)}
	 * @see {@link MVector#addVelocity(float, float, float)}
	 */
	public void addVelocity(MVector impulse) {
		this.addVelocity(impulse.x, impulse.y, impulse.z);
	}
	
	
	/**
	 * Takes 3 floats and applies them to this object's position, essentially acting as a 
	 * singular impulse. Will attempt to use {@linkplain World#delta} for physics approximations.
	 * {@summary Provides a single impulse on a given frame.}
	 * @param x
	 * @param y
	 * @param z
	 */
	public void addVelocity(float x, float y, float z) {
		this.velX += x * (world == null ? 1 : world.delta);
		this.velY += y * (world == null ? 1 : world.delta);
		this.velZ += z * (world == null ? 1 : world.delta);
	}
	
	/**
	 * Takes 2 floats and applies them to this object's position, giving it a single frame of velocity
	 * (which dissipates upon the next frame). A singular impulse.
	 * @see {@linkplain MVector#addVelocity(float, float, float)}
	 * @param x
	 * @param y
	 */
	public void addVelocity(float x, float y) {
		this.addVelocity(x, y, 0f);
	}
	
	
	
	/**
	 * Adds a certain amount of force to this vector. Velocity + Force, where Force = Acceleration.
	 * Takes a PVector as the primary parameter.
	 * @param impulse
	 */
	public void addForce(PVector impulse) {
		this.addForce(impulse.x, impulse.y, impulse.z);
	}
	
	
	/**
	 * Adds a certain amount of force to this vector. Velocity + Force, where Force = Acceleration.
	 * Takes an MVector as the primary parameter.
	 * @param impulse
	 */
	public void addForce(MVector impulse) {
		this.addForce(impulse.x, impulse.y, impulse.z);
	}
	
	/**
	 * This method adds 3 different floats to the velocity, essentially adding a certain amount of force (acceleration).
	 * Must use {@linkplain World#delta} for accurate physics, so be sure to link using {@linkplain MVector#setWorld(World)}
	 * @param x
	 * @param y
	 * @param z
	 */
	public void addForce(float x, float y, float z) {
		this.accX += x * (world == null ? 1 : world.delta);
		this.accY += y * (world == null ? 1 : world.delta);
		this.accZ += z * (world == null ? 1 : world.delta);
	}
	
	public void addForce(float x, float y) {
		this.addForce(x, y, 0f);
	}
	
	
	/**
	 * Forcefully sets the velocity to a given value.
	 * @param impulse
	 */
	public void setVelocity(PVector impulse) {
		this.setVelocity(impulse.x, impulse.y, impulse.z);
	}
	
	public void setVelocity(MVector impulse) {
		this.setVelocity(impulse.x, impulse.y, impulse.z);
	}
	
	public void setVelocity(float x, float y, float z) {
		this.velX = x;
		this.velY = y;
		this.velZ = z;
	}
	
	public void setVelocity(float x, float y) {
		this.setVelocity(x, y, velZ);
	}
	
	
	
	public void setAcceleration(float x, float y, float z) {
		this.accX = x;
		this.accY = y;
		this.accZ = z;
	}
	
	public void setAcceleration(PVector value) {
		this.setAcceleration(value.x, value.y, value.z);
	}
	
	public void setAcceleration(MVector value) {
		this.setAcceleration(value.x, value.y, value.z);
	}
	
	
	
	public void setAcceleration(float x, float y) {
		this.setAcceleration(x, y, this.accZ);
	}
	
	
	public void maxSpeed(float newMax) {
		this.maxSpeed = World.abs(newMax);
	}
	
	
	public void setFrictionConstant(float newConstant) {
		this.frictionConstant = newConstant;
	}
	
	
	public void mass(float newMass) {
		this.mass = newMass;
	}
	
	
	Float cacheX = Float.valueOf(0f);
	Float cacheY = Float.valueOf(0f);
	Float cacheZ = Float.valueOf(0f);
	/**
	 * Applies a damping force to the vector. If in space, the percentage should be low, however not too low as to lose control.
	 * Subtracts a certain percentage of the vector's velocity to dampen the final value.
	 * @param dampingPercentage as a value between 0 and 1
	 */
	public void damping(float dampingPercentage) {

		
		float velocityMagnitude = MVector.mag(velX, velY, velZ);
		normalize(velX, velY, velZ, velocityMagnitude);
		
		this.addVelocity(-normalizedCache[0] * frictionConstant * mass, -normalizedCache[1] * frictionConstant * mass, -normalizedCache[2] * frictionConstant * mass);
		if(velocityMagnitude < 0.01f) {
			this.setVelocity(0f, 0f, 0f);
		} else if(velocityMagnitude >= this.maxSpeed) {
			limitVelocity(velocityMagnitude, this.maxSpeed);
		}
		
//		System.out.println("Speed: "+World.round(velocityMagnitude));
		
		
//		this.setAcceleration(accX * dampingPercentage, accY * dampingPercentage, accZ * dampingPercentage);
//		float accelerationMagnitude = MVector.mag(accX, accY, accZ);
////		normalize(accX, accY, accZ, accelerationMagnitude);
//		this.addForce(-accX * frictionConstant, -accY * frictionConstant);
//		if(accelerationMagnitude < 0.4f) {
//			this.setAcceleration(0, 0, 0);
//		} else if(accelerationMagnitude >= this.maxAcceleration) {
////			System.out.println("MAX ACCELERATION!");
//			limitAcceleration(accelerationMagnitude, maxAcceleration);
//		}
		
		
	}
	
	
	
	
	private void normalize(float x, float y, float z) {
		this.normalize(x, y, z, MVector.mag(x, y, z));
	}
	
	
	private float[] normalizedCache = new float[3];
	private float[] normalize(float x, float y, float z, float mag) {
		if(mag != 0f && mag != 1f) {
			normalizedCache[0] = x/mag;
			normalizedCache[1] = y/mag;
			normalizedCache[2] = z/mag;
			return normalizedCache;
		}
		
		normalizedCache[0] = x;
		normalizedCache[1] = y;
		normalizedCache[2] = z;
		return normalizedCache;
	}
	
	
	private void normalize(Float x, Float y, Float z, float mag) {
		if(mag != 0f && mag != 1f) {
			x = x/mag;
			y = y/mag;
			z = z/mag;
		}
	}
	
	private void limitVelocity(float mag, float max) {
		if(MVector.magSq(velX, velY, velZ) > max * max) {
			normalize(velX, velY, velZ, mag);
			velX = normalizedCache[0] * max;
			velY = normalizedCache[1] * max;
			velZ = normalizedCache[2] * max;
		}
	}
	
	
	private void limitAcceleration(float mag, float max) {
		if(accX*accX + accY*accY + velZ*velZ > max * max) {
			normalize(accX, accY, accZ);
			accX = normalizedCache[0] * max;
			accY = normalizedCache[1] * max;
			accZ = normalizedCache[2] * max;
		}
	}
	
	public static float mag(float x, float y, float z) {
		return (float) Math.sqrt(x*x + y*y + z*z); 
	}
	
	public static float mag(PVector vector) {
		return vector.mag();
	}
	
	public static float mag(MVector vector) {
		return MVector.mag(vector.x, vector.y, vector.z);
	}
	
	
	public static float magSq(float x, float y, float z) {
		return (x*x + y*y + z*z);
	}
	
	
	
	
	public void update() {
//		this.limitVel();
		
		this.x += this.velX * world.delta;
		this.y += this.velY * world.delta;
		this.z += this.velZ * world.delta;
		this.velX += this.accX * world.delta;
		this.velY += this.accY * world.delta;
		this.velZ += this.accZ * world.delta;
		
	}

	public void maxAcceleration(float f) {
		this.maxAcceleration = f;
		
	}
	
}
