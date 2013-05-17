package td;

import info.gridworld.actor.*;

public class Barricade extends Actor implements GameComponent{
	
	private static final int COST = 5;
	private TDWorld world;
	
	public int getCost() {
		return COST;
	}
	
	public Barricade(TDWorld world) {
		this.world = world;
		setColor(null);
	}
	
	public void act() {
		
	}
	
}