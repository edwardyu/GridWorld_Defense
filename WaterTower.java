package td;

import info.gridworld.actor.*;
import info.gridworld.world.*;
import info.gridworld.grid.*;
import java.util.ArrayList;

public class WaterTower extends BasicTower {
	
	private static final int COST = 100;
	private static final int[] upgradeCost = {25, 50, 100};	
	private static int[] damageAmount = {10, 20, 30};
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
                    m.damage(damageAmount[level - 1]);
            }

        }
        
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
        
        public void upgrade() {
            if (getWorld().getGold() >= upgradeCost[level - 1]) {
                getWorld().takeGold(upgradeCost[level - 1]);
                level++;
                System.out.println("Upgraded to level " + level + "!");
            } else {
                System.out.println("Sorry, but you need " + upgradeCost[level - 1] + " gold to upgrade!");
            }
        }
	
        public void act()
        {
            attack();
        }
}