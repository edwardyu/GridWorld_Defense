package td;

import info.gridworld.actor.*;
import info.gridworld.world.*;
import info.gridworld.grid.*;
import java.util.*;

public class FireTower extends Actor implements GameComponent{
	
	private static final int COST = 50;
	private static final int[] upgradeCost = {50, 150, 300};
	private static final int[] damage = {25, 35, 45};
	private static final int[] speed = {7, 6, 5};	
	private static final int[][] fireDamage = { {2,5}, {4,5}, {7,10} };

	private int level = 1;
	private TDWorld world;

	private int timer;

	public int getCost() {
		return COST;
	}
	
	public FireTower(TDWorld world) {
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
		for(Location l : ar) {
			Actor a = getGrid().get(l);
			if(a instanceof Minion) {
				((Minion)a).damage(damage[level - 1]);
				((Minion)a).applyFire(fireDamage[level - 1]);
			}
		}
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