package game.solarsystem;

import game.core.*;
public class Moon extends CelestialBody {
	public Moon(World world, long seed, int distanceStep, CelestialBody parent) {
		super(world, world.gameBuffer);
	}
}
