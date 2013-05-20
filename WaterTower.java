package td;

import info.gridworld.actor.*;
import info.gridworld.world.*;
import info.gridworld.grid.*;
import java.util.ArrayList;

public class WaterTower extends BasicTower {
	
	private static final int COST = 10;
	private static final int[] upgradeCost = {25, 50, 100};	
	private static int[] damage = {10, 20, 30};
	private static int[] speed = {5, 3, 1};
        private int level = 1;
        private int timer;
        
	public WaterTower(TDWorld world) 
        {
            super(world);
            setColor(null);
            timer = speed[level - 1];
	}
	
        public void attack()
        {
            int dir = getDirection();
            ArrayList<Location> attackLocations = new ArrayList<Location>();
            Location loc = getLocation().getAdjacentLocation(dir);
            for(int i = 1; i <= 4; i++)
            {
                while (getGrid().isValid(loc)) 
                {
                    attackLocations.add(loc);
                    loc = getLocation().getAdjacentLocation(dir);
                }
                dir += 90;
                dir %= 360;
            }
            
            for(Location test : attackLocations)
            {
                if(getGrid().get(test) instanceof Minion)
                    ((Minion) getGrid().get(test)).damage(damage[level - 1]);
                
            }
            
            
            
        }
        
    public void upgrade() {
        if (getWorld().getGold() >= upgradeCost[level - 1]) {
            getWorld().takeGold(upgradeCost[level - 1]);
            level++;
            System.out.println("Upgraded to level " + level + "!");
        } else {
            System.out.println("Sorry, but you need " + upgradeCost[level - 1] + " gold to upgrade!");
        }
    }
	
}