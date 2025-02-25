/**
 * 
 */
/**
 * @author keyboard
 *
 */
module Offworld {
	requires transitive core;
	requires gluegen.rt;
	requires jnoise.core;
	requires jnoise.generators;
	requires jnoise.modifiers;
	requires jnoise.pipeline;
	
	
	exports game.core to core;
	exports game.effects to core;
	exports game.entities to core;
	exports game.physics to core;
	exports game.solarsystem to core;
	exports game.userinput to core;
	
	exports main to core;
}