package td;

import info.gridworld.actor.*;
import info.gridworld.world.*;
import info.gridworld.grid.*;
import java.util.*;

public class BasicTower extends Actor implements GameComponent{
	
	private static final int COST = 10;
	private static final int[] upgradeCost = {25, 50, 100};	
	private static int[] damage = {10, 20, 30};
	private static int[] speed = {5, 3, 1};

	private int level = 1;
	private TDWorld world;
	
	private int timer;

	public int getCost() {
		return COST;
	}
	
	public BasicTower(TDWorld world) {
		this.world = world;
		setColor(null);
		timer = speed[level - 1];
	}
	
	public void act() {
		timer--;
		if(timer == 0) {	
			attack();
			timer = speed[level - 1];
		}
	}
	
	public void attack() {
		ArrayList<Location> ar = getGrid().getOccupiedAdjacentLocations(getLocation());
		ArrayList<Minion> m = new ArrayList<Minion>();
		for(int k = 0; k < ar.size(); k++) {
			if(getGrid().get(ar.get(k)) instanceof Minion) {
				m.add((Minion)(getGrid().get(ar.get(k))));
			}
		}
		if(m.size() == 0)
			return;
		m.get((int)(Math.random() * ar.size())).damage(damage[level - 1]);
	}	

	public void upgrade() {
		if(world.getGold() >= upgradeCost[level - 1]) {
			world.takeGold(upgradeCost[level - 1]);
			level++;
		 	System.out.println("Upgraded to level " + level +"!");	
		} else {
			System.out.println("Sorry, but you need " + upgradeCost[level - 1] + " gold to upgrade!");
		}
	}

}