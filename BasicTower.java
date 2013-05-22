package td;

import info.gridworld.actor.*;
import info.gridworld.world.*;
import info.gridworld.grid.*;
import java.util.*;

public class BasicTower extends Barricade implements GameComponent{
	
	private static final int COST = 10;
	private static final int[] upgradeCost = {25, 50, 100};	
	private static int[] damageAmount = {10, 20, 30};
	private static int[] speed = {5, 3, 1};

	private int level = 1;
	//private TDWorld world;
	
	private int timer;

	public int getCost() {
		return COST;
	}
	
	public BasicTower(TDWorld world) {
		super(world);
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
		if(m.isEmpty())
			return;
		m.get((int)(Math.random() * m.size())).damage(damageAmount[level - 1]);
                
	}	

	public void upgrade() {
		if(getWorld().getGold() >= upgradeCost[level - 1]) {
			getWorld().takeGold(upgradeCost[level - 1]);
			level++;
		 	System.out.println("Upgraded to level " + level +"!");	
		} else {
			System.out.println("Sorry, but you need " + upgradeCost[level - 1] + " gold to upgrade!");
		}
	}

}