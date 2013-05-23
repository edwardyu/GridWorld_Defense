/*
 * Shade.java
 * A Shade colors a square, and is used to mark the start and end locations.
 * @author Edward Yu, Ronbo Fan
 * Period: 6
 * Date: 5/19/13
 * 
 */
package td;

import info.gridworld.actor.*;

import java.awt.Color;

public class Shade extends Actor {
	
	public boolean isActive;
	private TDWorld world;

        /*
         * Switch the shade on and off.
         */
	public void toggle() {
		isActive = !isActive;
	}
	
        /*
         * Constructs a Shade object.
         * @param world the world which controls the shade
         */
	public Shade(TDWorld world) {
		this.world = world;
		setColor(Color.green);
		isActive = true;
	}
	
        /*
         * Does not do anything except sit there.
         */
	public void act() {
		if(isActive)
			return;
		
	}
	
}