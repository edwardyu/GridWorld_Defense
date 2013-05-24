/*
 * BasicTower.java
 * A BasicTower randomly selects a minion adjacent to it and inflicts damage to it.
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

public class BasicTower extends Barricade implements GameComponent{
	
    private static final int COST = 10;
    private static final int[] upgradeCost = {25, 50, 100};	
    private static int[] damageAmount = {10, 20, 30};
    private static int[] speed = {3, 2, 1};

    private int level = 1;

    private int timer;


/*
 * Constructs a BasicTower
 * @param world the world which controls the tower
 */	
    public BasicTower(TDWorld world) {
            super(world);
            setColor(null);
            timer = speed[level - 1];
    }

    /*
     * Gets the amount of money needed to build a BasicTower
     * @return amount of money needed to build a BasicTower
     */
    public int getCost() {
        return COST;
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
     * Attack a random minion in an adjacent location
     */
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
    
/*
 * Upgrade a BasicTower to the next level. This will increase its damage, speed, and cost.
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