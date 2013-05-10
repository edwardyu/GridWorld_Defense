package td;

import info.gridworld.grid.*;
import info.gridworld.world.*;
import info.gridworld.actor.*;

import java.util.ArrayList;


public class minion extends Actor {
	
	private Location start;
	private Location end;
	
    public minion(Location start, Location end) {
    	
    	this.start = start;
    	this.end = end;
    	setColor(null);
    	
    }
    
    public Location getNextMove()
    {
    	//return getLocation().getAdjacentLocation(getLocation().getDirectionToward(end));
    	
    	//Implement Dijkstra's Algorithm for Shortest Route Problems
    	
    	ArrayList<Location> path = new ArrayList<Location>(getLocation()); //path to the end point
    	ArrayList<Location> checked = new ArrayList<Location>(); //never double check a location that is in checked
    	ArrayList<Location> emptyAdjacentLocs = getGrid().getEmptyAdjacentLocations(getLocation());

        if(closeToEnd(getLocation()))
            return end;

        while(!closeToEnd(path.get(path.size() - 1)))
        {
            path.add(closestLoc(emptyAdjacentLocs, checked));
            checked.addAll(emptyAdjacentLocs);
            
            emptyAdjacentLocs = getGrid().getEmptyAdjacentLocations(path.get(path.size() - 1));
        }
    	
    	return path.get(1);
    	
    }
    
    private double distance(Location loc1, Location loc2)
    {
    	double x1 = loc1.getRow();
    	double x2 = loc2.getRow();
    	
    	double y1 = loc1.getCol();
    	double y2 = loc2.getCol();
    	
    	return Math.sqrt(Math.pow((x1 - x2), 2.0) + Math.pow((y1 - y2), 2.0));
    }
    
    private Location closestLoc(ArrayList<Location> locs, ArrayList<Location> checked)
    {
        double minDistance = distance(locs.get(0));
        Location minLoc = locs.get(0);
        
        for(Location loc : locs)
        {
            double dist = distance(getLocation(), loc);
            if(dist < minDistance && checked.contains(loc) == false)
            {
                minDistance = dist;
                minLoc = loc;
            }
        }
        
        if(checked.contains(minLoc))
            return null;
        else
            return minLoc;
    }
    
    private boolean closeToEnd(Location loc)
    {
    	ArrayList<Location> locs = getGrid().getValidAdjacentLocations(loc);
    	for(Location test : locs)
    	{
    		if(test.equals(end))
    			return true;
    	}
    	
    	return false;
    }
    
    public void act()
    {
    	Location nextMove = getNextMove();
    	if(getGrid().get(nextMove) instanceof Barricade || getGrid().get(nextMove) instanceof BasicTower)
    		super.act();
    			
    	else
    		moveTo(nextMove);
    }
    
}