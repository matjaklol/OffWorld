package game.solarsystem;

import game.core.*;
public class Planet extends CelestialBody {
	public Planet(World world) {
		super(world, world.gameBuffer);
	}
}
