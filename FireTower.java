/*
 * FireTower.java
 * A FireTower damages all minions adjacent to it, and also applies fire damage, which burns the minion over a period of time.
 * @author Edward Yu, Ronbo Fan
 * Period: 6
 * Date: 5/19/13
 * 
 */
package td;

import info.gridworld.actor.*;
import info.gridworld.world.*;
import info.gridworld.grid.*;
import java.util.*;

public class FireTower extends BasicTower implements GameComponent
{
	
	private static final int COST = 50;
	private static final int[] upgradeCost = {50, 150, 300};
	private static final int[] damage = {25, 35, 45};
	private static final int[] speed = {7, 6, 5};	
	private static final int[][] fireDamage = { {2,5}, {4,5}, {7,10} };

	private int level = 1;
	//private TDWorld world;

	private int timer;
        
    /*
     * Gets the amount of money needed to build a FireTower
     * @return amount of money needed to build a FireTower
     */
	public int getCost() {
		return COST;
	}
	
    /*
     * Constructs a FireTower
     * @param world the world which controls the tower
     */	
	public FireTower(TDWorld world) {
		super(world);
		setColor(null);
		timer = speed[level - 1];
	}
        
    /*
     * The tower will attack every few turns, at a speed determined by the timer. 
     * The timer's speed is determined by the level.
     * 
     */	
	public void act() {
		timer--;
		if(timer == 0) {	
			attack();
			timer = speed[level - 1];
		}
	}
        
    /*
     * Attack all minions in adjacent locations
     */
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
        
    /*
     * Upgrade a FireTower to the next level. This will increase its damage, speed, and cost.
     */
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