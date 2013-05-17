/*
 * Minion.java
 * The Minion class creates a minion whose job is to get to the end square. 
 * The minion will always take the shortest path to the end, or remove itself if its heatlh is 0 or less. 
 * @author Edward Yu, Ronbo Fan
 * @date 5/16/13
 * @period 6
 */
package td;

import info.gridworld.grid.*;
import info.gridworld.world.*;
import info.gridworld.actor.*;
import java.awt.Color;

import java.util.*;

public class minion extends Actor {
	
	private Location start; //the start location for the minion
	private Location end;  //the target location
	private int health;
        
        private HashSet<Location> open; //list of possible locations to check 
        private HashSet<Location> closed; //list of checked locations
        
        private Map<Location, Integer> fcosts; //overall distance from beginning to end
        private Map<Location, Integer> gcosts; //distance from beginning to location
        private Map<Location, Integer> hcosts; //distance from end to location
        private Map<Location, Location> parents; 
  
	
    public minion(Location start, Location end) {
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
    	System.out.println("open size: " + open.size() + "\n" + open);
    }
    
    
    public void damage(int amount)
    {
    	health -= amount;
        setColor(Color.RED);
    }
    
    public ArrayList<Location> getWalkableLocs(Location loc)
    {
        ArrayList<Location> adjacentLocs = getGrid().getValidAdjacentLocations(loc);
        for(int i = adjacentLocs.size() - 1; i >= 0; i--)
        {
            //remove barricades or minions from walkable locations
            if(getGrid().get(adjacentLocs.get(i)) instanceof Barricade || getGrid().get(adjacentLocs.get(i)) instanceof Minion)
                adjacentLocs.remove(i);
        }
        
        return adjacentLocs;
    }
    
    public int getHcost(Location loc)
    {
        //manhattan method for estimating distance from end.
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
        //if loc is directly to the side of parent, the cost of moving there is 10
        if(loc.getDirectionToward(parent) % 90 == 0)
            gCost = 10;
        //if loc is diagonal from parent, then the cost of moving there is 10 * sqrt(2), or approximately 14
        else
            gCost = 14;
        
        
        if(parent.equals(start))
            return 0;
        else
            return gCost + getGcost(parent);        
    }
    
    public int getFcost(Location loc)
    {
        //a bit wasteful 
        return getGcost(loc) + getHcost(loc);
    }
    
    public Location getMinLocation()
    {
    	Object[] loc2 = open.toArray();
    	Location minLoc = (Location) loc2[0];
    	//System.out.println(loc2[0] + " ddd " + minLoc);
    	//System.out.println("fcosts: " + fcosts);
    	if(!fcosts.containsKey(minLoc))
    		return null;
        int minFcost = getFcost(minLoc);
        for(Location loc : open)
        {
            int fcost = getFcost(loc);
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
        /*
        parents.put(start, start);
        fcosts.put(start, getFcost(start));
        gcosts.put(start, getGcost(start));
        hcosts.put(start, getHcost(start));
        */
        
        while(!closed.contains(end) && !open.isEmpty())
        {
            Location current = getMinLocation();
            open.remove(current);
            closed.add(current);
            
            for(Location loc : getWalkableLocs(current))
            {
                if(!closed.contains(loc))
                {
                    if(!open.contains(loc))
                    {
                        open.add(loc);
                        parents.put(loc, current);
                        fcosts.put(loc, getFcost(loc));
                        gcosts.put(loc, getGcost(loc));
                        hcosts.put(loc, getHcost(loc));
                    }
                    else if(getGcost(loc) < gcosts.get(loc))
                    {
                        parents.put(loc, current);
                        fcosts.put(loc, getFcost(loc));
                        gcosts.put(loc, getGcost(loc));
                    }
                    

                }
            }
        }
        
        ArrayList<Location> path = new ArrayList<Location>();
        
        Location parent = parents.get(end);
        while(!path.contains(start))
        {

            path.add(parent);
            parent = parents.get(parent);
        }
        
        if(path.size() >= 2)
            return path.get(path.size() - 2);
        else
        {
            System.out.println("No path to end exists.");
            return null;
        }
            
    }
    /*
    public void putSelfInGrid(Grid<Actor> gr, Location loc)
	{
		super.putSelfInGrid(gr, loc);
		open.add(getLocation());
	}
	*/
        @Override
    public void act()
    {
        setColor(null);
        
        if(health <= 0)
            removeSelfFromGrid();
        else if(getLocation().equals(end))
            removeSelfFromGrid();
        else
            moveTo(getNextMove());
    }
    
}
