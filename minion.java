package td;

import info.gridworld.grid.*;
import info.gridworld.world.*;
import info.gridworld.actor.*;

import java.util.ArrayList;
import java.util.AbstractSet;
import java.util.AbstractMap;

public class Minion extends Actor {
	
	private Location start;
	private Location end;
	private int health;
        
        private HashSet<Location> open;
        private HashSet<Location> closed;
        
        private Map<Location, Integer> fcosts; //overall distance from beginning to end
        private Map<Location, Integer> gcosts; //distance from beginning to location
        private Map<Location, Integer> hcosts; //distance from end to location
        private Map<Location, Location> parents; 
        
        open.add(getLocation());
	
    public Minion(Location start, Location end) {
    	
    	this.start = start;
    	this.end = end;
    	health = 100;
        open = new HashSet<Location>();
        closed = new HashSet<Location>();
        fcosts = new HashMap<Location, Integer>();
        gcosts = new HashMap<Location, Integer>();
        hcosts = new HashMap<Location, Integer>();
        parents = new HashMap<Location, Location>();
    	setColor(null);
    	
    }
    
    
    public void damage(int amount)
    {
    	health -= amount;
    }
    
    public ArrayList<Location> getWalkableLocs(Location loc)
    {
        ArrayList<Location> adjacentLocs = getGrid().getValidAdjacentLocations(loc);
        for(int i = adjacentLocs.size() - 1; i >= 0; i--)
        {
            if(adjacentLocs.get(i) instanceof Barricade || adjacentLocs.get(i) instanceof Minion)
                adjacentLocs.remove(i);
        }
        
        return adjacentLocs;
    }
    
    public int getHcost(Location loc)
    {
        int x1 = loc.getRow();
        int x2 = end.getRow();
        
        int y1 = loc.getCol();
        int y2 = end.getCol();
        
        return 10 * (int) (Math.abs(x1 - x2) + Math.abs(y1 - y2));
    }
    
    public int getGcost(Location loc)
    {
        int gCost;
        Location parent = parents.get(loc);
        if(loc.getDirectionToward(parent) % 90 == 0)
            gCost = 10;
        else
            gCost = 14;
        
        
        if(parent.equals(start))
            return 0;
        else
            return gCost + getGcost(parent);        
    }
    
    public int getFcost(Location loc);
    {
        return getGcost(loc) + getHcost(loc);
    }
    
    public Location getMinLocation()
    {
        Location minLoc = open.get(0);
        int minFcost = fcosts.get(minLoc);
        for(Location loc : open)
        {
            int fcost = fcosts.get(loc);
            if(fcost <= minFcost)
            {
                minFcost = fcost;
                minLoc = loc;
            }
        }
        
        return minLoc;
    }
    
    public Location getNextMove()
    {
        open.add(start);
        parents.put(start, start);
        fcosts.put(start, getFcost());
        gcosts.put(start, getGcost());
        hcosts.put(start, getHcost());
        
        
        while(!closed.contains(end) && !open.isEmpty())
        {
            Location current = getMinLocation();
            open.remove(current);
            closed.add(current);
            
            for(Location loc : getWalkableLocs(current))
            {
                if(closed.contains(loc) == false)
                {
                    if(open.contains(loc) == false)
                    {
                        open.add(loc);
                        parents.put(loc, current);
                        fcosts.put(loc, getFcost());
                        gcosts.put(loc, getGcost());
                        hcosts.put(loc, getHcost());
                    }
                    else
                    {
                        if(getGcost(loc) < gcosts.get(loc))
                        {
                            parents.put(loc, current);
                            fcosts.put(loc, getFcost());
                            gcosts.put(loc, getGcost());
                        }
                    }

                }
            }
        }
        
        ArrayList<Location> path = new ArrayList<Location>();
        
        Location parent = parents.get(end);
        while(path.contains(start) == false)
        {

            path.add(parent);
            parent = parents.get(parent);
        }
        
        return path.get(path.size() - 2);
    }
    
    public void act()
    {
        moveTo(getNextMove());
    }
    
}
