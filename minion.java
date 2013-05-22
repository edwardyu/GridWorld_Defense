/*
 * Minion.java
 * The Minion class creates a minion whose job is to get to the end square. 
 * The minion will always take the shortest path to the end, or remove itself if its health is 0 or less. 
 * Once it reaches the end, the player will lose a life. 
 * @author Edward Yu, Ronbo Fan
 * Period: 6
 * Date: 5/19/13
 * 
 */
package td;

import info.gridworld.grid.*;
import info.gridworld.world.*;
import info.gridworld.actor.*;
import java.awt.Color;

import java.util.*;

public class Minion extends Actor
{
	
	private Location start; //the start location for the minion
	private Location end;  //the target location
	private int health;    //how much health the minion has
	private int[] fireTicks; //{dps, time}
        private int reward; //money gained from killing a minion

        
        private HashSet<Location> open; //list of possible locations to check 
        private HashSet<Location> closed; //list of checked locations
        
        private Map<Location, Integer> fcosts; //overall distance from beginning to end
        private Map<Location, Integer> gcosts; //distance from beginning to location
        private Map<Location, Integer> hcosts; //distance from end to location
        private Map<Location, Location> parents; //each location points towards its parent square. The parent will eventually reach start
  
	
	private TDWorld world;
	
    public Minion(Location start, Location end, TDWorld world) {
        this.world = world;
    	this.start = start;
    	this.end = end;
    	
    	health = 100;
        reward = 10;
        open = new HashSet<Location>();
        closed = new HashSet<Location>();
        fcosts = new HashMap<Location, Integer>();
        gcosts = new HashMap<Location, Integer>();
        hcosts = new HashMap<Location, Integer>();
        parents = new HashMap<Location, Location>();
    	setColor(null);
    	//System.out.println("open size: " + open.size() + "\n" + open);
    }
    
    //TODO: comment here
    public int getReward()
    {
        return reward;
    }
    
    //TODO: comment here
    public int getHealth()
    {
        return health;
    }
    
    
    /*
     * Moves to the best square to get to the end point, as determined by getNextMove(), or removes itself 
     * from grid if it has no health or if it's close to the end. 
     */
    public void act()
    {
        //setColor(null);
        start = getLocation();
        Location nextMove = getNextMove();
        
        if(health <= 0)
        {
            removeSelfFromGrid();
            world.addGold(reward);
            return;
        }
        
        else if(closeToEnd(start))
        {
            world.loseLife();
            removeSelfFromGrid();
            return;
        }
        
        else if(!getLocation().equals(nextMove))
            moveTo(nextMove);
        
        //reset everything
//        start = getLocation();
        open = new HashSet<Location>();
        closed = new HashSet<Location>();
        fcosts = new HashMap<Location, Integer>();
        gcosts = new HashMap<Location, Integer>();
        hcosts = new HashMap<Location, Integer>();
        parents = new HashMap<Location, Location>();
    }
    
    //TODO: Comment here
    public void applyFire(int[] fireDamage) {
    	fireTicks = fireDamage;
    }
    
    /*
     * Removes some health from the minion
     * @param amount the amount of health to be deducted
     */
    public void damage(int amount)
    {
    	health -= amount;
        //setColor(Color.RED);
        System.out.println("Health: " + health);
        /*
        if(health <= 0)
            removeSelfFromGrid();
        */
    }
    
    /*
     * Determines if a location is adjacent to the endpoint
     * @param loc the location to be checked
     * @return true if adjacent to endpoint, false otherwise
     */
    public boolean closeToEnd(Location loc)
    {
        if(getGrid().getValidAdjacentLocations(loc).contains(end))
            return true;
        else
            return false;
    }
    
    /*
     * Gets all the locations a minion can move to. A minion cannot eat Barricades or other Minions, but it can eat other Actors.
     * @param loc the location around which to check for walkable locations.
     * @return an ArrayList of walkable locations that are adjacent to loc
     */
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
    
    /*
     * Estimates the distance from loc to the endpoint using the Manhattan method, which ignores barriers.
     * The distance is simply the number of rows + the number of columns it takes to get to end.
     * @param loc the location from which to calculate
     * @return the estimated distance to end
     */
    public int getHcost(Location loc)
    {
        //manhattan method for estimating distance from end.
        int x1 = loc.getRow();
        int x2 = end.getRow();
        
        int y1 = loc.getCol();
        int y2 = end.getCol();
        
        return 10 * (int) (Math.abs(x1 - x2) + Math.abs(y1 - y2));
    }
    
    /*
     * Recursively estimates the distance from start to loc. Moving horizontally 
     * or vertically costs 10, but moving diagonally costs 14.
     * @param loc the location to which to calculate.
     * @return the estimated distance from start to loc.
     */
    public int getGcost(Location loc)
    {
        int gcost;
        Location parent = parents.get(loc);
        //if loc is directly to the side of parent, the cost of moving there is 10
        //System.out.println("Parent: " + parent.toString());
        if(loc.getDirectionToward(parent) % 90 == 0)
            gcost = 10;
        //if loc is diagonal from parent, then the cost of moving there is 
        //10 * sqrt(2), or approximately 14
        else
            gcost = 14;
        
        
        if(parent.equals(start))
            return 0;
        else
            return gcost + getGcost(parent);        
    }
    
    /*
     * The estimated distance from start to end, assuming we go through loc.
     * @param loc the location we must go through
     * @return the estimated distance from start to end (lower is better)
     */
    public int getFcost(Location loc)
    {
        //a bit wasteful 
        return getGcost(loc) + getHcost(loc);
    }
    
    /*
     * Out of all the locations to be checked (in the HashSet open), return the 
     * location with the lowest estimated distance from beginning to end (fcost).
     * @return the Location with the lowest fcost (the Location with the lowest estimated distance from beginning to end).
     */
    public Location getMinLocation()
    {
    	Object[] loc2 = open.toArray();
    	Location minLoc = (Location) loc2[0];
    	//if(!fcosts.containsKey(minLoc))
    	//	return null;
    	//System.out.println("minloc: " + minLoc);
        //System.out.println("Fcosts: " + fcosts.toString());
        int minFcost = fcosts.get(minLoc);
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
    
    /*
     * Gets the best move for the minion so it follows the shortest path to the end.
     * Implements the A* pathfinding algorithm.
     * @return the move which brings it closer to the endpoint.
     */
    public Location getNextMove()
    {
        open.add(start);
        
        parents.put(start, start);
        hcosts.put(start, getHcost(start));
        gcosts.put(start, getGcost(start));
        fcosts.put(start, getFcost(start));
        
        
        //check all possible paths
        //stop if we reach the end or there are no locations left to check
        while(!closed.contains(end) || !open.isEmpty())
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
            if(open.isEmpty())
                break;
        }
        
        //no path to end exists
        if(!closed.contains(end))
        {
            //System.out.println("No path to end exists.");
            return getLocation();
        }
        
        
        //reverse generate the path to the start, starting from the end. 
        ArrayList<Location> path = new ArrayList<Location>();
        
        Location parent = parents.get(end);
        while(!path.contains(start))
        {

            path.add(parent);
            parent = parents.get(parent);
        }
        
        //return the Location right after start
        if(path.size() >= 2)
            return path.get(path.size() - 2);
        else
            return getLocation();
            
    }
}
