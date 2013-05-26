package td;
/*
 * WaterTower.java
 * A WaterTower damages all minions in all cardinal directions. It is blocked by barricades and other towers.
 * @author Edward Yu, Ronbo Fan
 * Period: 6
 * Date: 5/19/13
 * 
 */ 


import info.gridworld.grid.*;
import java.util.ArrayList;

public class WaterTower extends BasicTower implements GameComponent
{
	
	private static final int COST = 50;
	private static final int[] upgradeCost = {25, 50, 100};	
	private static int[] damageAmount = {10, 20, 30};
	private static int[] speed = {5, 3, 1};
        private int level = 1;
        private int timer;
        public int getLevel() {
        	return level;
        }
        public void levelUp() {
        	level++;
        }
        /*
         * Gets the amount of money needed to build a MoneyHut
         * @return amount of money needed to build a MoneyHut
         */

    	public int getCost() {
    		return COST;
    	}
    	
    	public int[] getUpgradeCost() {
    		return upgradeCost;
    	}
        
    /*
     * Constructs a MageTower
     * @param world the world which controls the tower
     */
	public WaterTower(TDWorld world) 
        {
            super(world);
            setColor(null);
            timer = speed[level - 1];
	}
        
    /*
     * Attack all minions in all cardinal directions.
     * More than one minion can be damaged in the attack.
     */       
        public void attack()
        {
            int dir = getDirection();
            ArrayList<Location> attackLocations = new ArrayList<Location>();
            
            for(int i = 1; i <= 4; i++)
            {
                Location loc = getLocation().getAdjacentLocation(dir);
                while (getGrid().isValid(loc)) 
                {
                    if(getGrid().get(loc) != null && !(getGrid().get(loc) instanceof Minion))
                        break;
                    attackLocations.add(loc);
                    loc = loc.getAdjacentLocation(dir);
                }
                dir += 90;
                dir %= 360;
            }
            
            
            for(Minion m : getMinions())
            {
                if(attackLocations.contains(m.getLocation()))
                {
                    m.damage(damageAmount[level - 1]);
                    return;
                }
                    
                
            }

        }
        
        /*
         * Get all the Minions in the grid
         * @return an ArrayList of all the minions in the grid.
         */
        public ArrayList<Minion> getMinions()
        {
            ArrayList<Location> occupied = getGrid().getOccupiedLocations();
            ArrayList<Minion> minions = new ArrayList<Minion>();
            
            for(Location l : occupied)
            {
                if(getGrid().get(l) instanceof Minion)
                    minions.add((Minion) getGrid().get(l));
            }
            
            return minions;
        }

    /*
     * Upgrade a MageTower to the next level. This will increase its damage, speed, and cost.
     */
        public void upgrade() {
            if (getWorld().getGold() >= upgradeCost[level - 1]) {
                getWorld().takeGold(upgradeCost[level - 1]);
                level++;
                System.out.println("Upgraded to level " + level + "!");
            } else {
                System.out.println("Sorry, but you need " + upgradeCost[level - 1] + " gold to upgrade!");
            }
        }
        
    /*
     * The tower will attack every few turns, at a speed determined by the timer. 
     * The timer's speed is determined by the level.
     * 
     */	
        public void act()
        {
            timer--;
            if (timer == 0) {
                attack();
                timer = speed[level - 1];
             }
        }
}