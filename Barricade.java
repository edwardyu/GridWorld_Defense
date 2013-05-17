package td;

import info.gridworld.actor.*;

public class Barricade extends Actor implements GameComponent{
	
	private static final int COST = 5;
	
	public int getCost() {
		return COST;
	}
	
	public Barricade() {
		setColor(null);
	}
	
	public void act() {
		
	}
	
}