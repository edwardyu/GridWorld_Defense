package td;
/*
 * FireTower.java
 * A FireTower damages all minions adjacent to it for a greater amount of damage than a Basic Tower
 * @author Edward Yu, Ronbo Fan
 * Period: 6
 * Date: 5/19/13
 * 
 */


import info.gridworld.actor.*;
import info.gridworld.grid.*;
import java.util.*;

public class FireTower extends BasicTower implements GameComponent
{
	
	private static final int COST = 100;
	private static final int[] upgradeCost = {50, 150};
	private static final int[] damage = {25, 35, 45};
	private static final int[] speed = {7, 6, 5};	

	private int level = 1;

	private int timer;
    
	/*
	 * Gets the level of the structure
	 * @return current level of the structure, from 1 to 3
	 */
	
    public int getLevel() {
    	return level;
    }
    
    /*
     * Levels up the structure
     */
    
    public void levelUp() {
    	level++;
    }	
    
    /*
     * Modifies the name of the .gif that represents this tower
     * according to what levels it is
     * @return the suffix of the image
     */
    
    public String getImageSuffix() { 
		if (level == 1) {
			return "";
		}
		else if (level == 2) {
			return "_2";
		}
		else {
			return "_3"; 
		}
	} 
    
    /*
     * Gets the amount of money needed to build a FireTower
     * @return amount of money needed to build a FireTower
     */

	public int getCost() {
		return COST;
	}
	
	/*
	 * Gets the upgrade-cost array for this structure
	 * @return an int[] containing two values for the tower upgrades from levels 1-2 and 2-3
	 */
	
	public int[] getUpgradeCost() {
		return upgradeCost;
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