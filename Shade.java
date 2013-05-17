package td;

import info.gridworld.actor.*;

import java.awt.Color;

public class Shade extends Actor {
	
	public boolean isActive;
	private TDWorld world;

	public void toggle() {
		isActive = !isActive;
	}
	
	public Shade(TDWorld world) {
		this.world = world;
		setColor(Color.green);
		isActive = true;
	}
	
	public void act() {
		if(isActive)
			return;
		
	}
	
}