package td;

import info.gridworld.actor.*;

import java.awt.Color;

public class Shade extends Barricade {
	
	public boolean isActive;

	public void toggle() {
		
	}
	
	public Shade() {
		setColor(Color.green);
		isActive = true;
	}
	
	public void act() {
		if(isActive)
			return;
		
	}
	
}