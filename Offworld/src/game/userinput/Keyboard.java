package game.userinput;

import java.util.HashMap;
import java.util.Map;

import game.core.World;
import main.GameApplet;
import processing.event.KeyEvent;
import processing.core.PApplet;

/**
 * KeyboardHandler is a class that handles all keyboard inputs 
 * in regards to the world. Takes care of key typed events,
 * key press events, and more. Must be run in the world, using
 * specific method organization. 
 * 
 * @author keyboard
 * @see GameApplet
 */
public class Keyboard {
	//Create all the current needed maps. (key char and key code)
	private Map<Character, Boolean> keys;
	private Map<Integer, Boolean> keyCodes;
	private Map<Character, Boolean> typedKey;
	private Map<Integer, Boolean> typedKeyCode;
	
	/**
	 * The Applet Reference that the keyboard handler uses to get keyboard inputs.
	 */
	private GameApplet testScreen;
	
	/**
	 * Default Constructor. Initializes all internal maps and prepares
	 * everything for all the needed key-type logic.
	 * 
	 * @param toAdd the World/PApplet to use as the base for keyboard inputs.
	 * @see PApplet
	 * @see World
	 */
	public Keyboard(GameApplet toAdd) {
		println("Initializing...");
		this.testScreen = toAdd;
		keys = new HashMap<Character, Boolean>();
		keyCodes = new HashMap<Integer, Boolean>();
		typedKey = new HashMap<Character, Boolean>();
		typedKeyCode = new HashMap<Integer, Boolean>();
		
		toAdd.registerMethod("post", this);
		println("Initialized successfully.");
	}
	
	/**
	 * getKey(char) returns either true or false depending on whether a key is being held.
	 * 
	 * @param keyTyped the key to check
	 * @return boolean if the key is held down
	 */
	public boolean getKey(char keyTyped) {
		return keys.getOrDefault(keyTyped, false);
	}
	
	/**
	 * getKey(int) checks if the given keycode is inside of the key map.
	 * otherwise it returns false.
	 * 
	 * @param keyCode (the integer keycode) this is the keycode to check against.
	 * @return boolean if the keycode inputed is being pressed or not.
	 */
	public boolean getKey(int keyCode) {
		return keyCodes.getOrDefault(keyCode, false);
	}
	
	/**
	 * getTyped(char) returns true or false if a key (of specified char) was typed.
	 * @param keyTyped the character being typed.
	 * @return true/false depending on whether or not it was typed.
	 */
	public boolean getTyped(char keyTyped) {
		return typedKey.getOrDefault(keyTyped, false);
	}
	
	/**
	 * getTyped(int) returns true or false if the given keyCode was typed. 
	 * @param keyTyped the keycode that may have been typed.
	 * @return true/false depending on whether or not it was typed.
	 */
	public boolean getTyped(int keyTyped) {
		return typedKeyCode.getOrDefault(keyTyped, false);
	}
	
	/**
	 * updateKeys() is the primary update method that MUST be called for key typing to work.
	 * Call this method at the end of an update method inside of the primary PApplet or World.
	 * 
	 * @see World
	 * @see PApplet
	 */
	public void updateKeys() {
		//Remove the typed keys.
		for(Map.Entry<Character, Boolean> entry : typedKey.entrySet()) {
			typedKey.put(entry.getKey(), false);
		}
		for(Map.Entry<Integer, Boolean> entry : typedKeyCode.entrySet()) {
			typedKeyCode.put(entry.getKey(), false);
		}
		
	}
	
	/**
	 * Debug print method.
	 * @param val
	 */
	private void println(String val) {
		System.out.printf("[KEYBOARD] %s%n", val);
	}
	
	public void post() {
		this.updateKeys();
	}
	
	/**
	 * keyPressed() is the method that must be called by PApplet.keyPressed(),
	 * or by registering it via PApplet.registerMethods()
	 */
	public void keyPressed() {
		keys.put(testScreen.key, true);
		keyCodes.put(testScreen.keyCode, true);

	}
	
	
	/**
	 * keyReleased() is the method that must be called inside of
	 * World.keyReleased or PApplet.keyReleased().
	 * It handles the primary update logic behind key typing and 
	 * key down/up events.
	 * 
	 * @see World
	 * @see PApplet
	 */
	public void keyReleased() {
		//Set the current pressed key to false.
		keys.put(testScreen.key, false);
		
		//Same for the current pressed keycode.
		keyCodes.put(testScreen.keyCode, false);
		
		//Update the current typed key character along with the current typed keycode.
		typedKey.put(testScreen.key, true);
		typedKeyCode.put(testScreen.keyCode, true);
	}
	
	
}
