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
    	
    	ArrayList<Location> path = new ArrayList<Location>();
    	
    	ArrayList<Location> emptyAdjacentLocs = getGrid().getEmptyAdjacentLocations(start);
    	Location nextMove = null;
    	double minDistance = distance(start, end);
    	
    	for(Location loc : emptyAdjacentLocs)
    	{
    		if(closeToEnd(loc))
    			return end;
    		else
    		{
    			if(distance(start, loc) < minDistance)
    			{
    				nextMove = loc;
    				minDistance = distance(start, loc);
    			}
    		}
    	}
    	
    	return nextMove;
    	
    }
    
    private double distance(Location loc1, Location loc2)
    {
    	double x1 = loc1.getRow();
    	double x2 = loc2.getRow();
    	
    	double y1 = loc1.getCol();
    	double y2 = loc2.getCol();
    	
    	return Math.sqrt(Math.pow((x1 - x2), 2.0) + Math.pow((y1 - y2), 2.0));
    }
    
    private double arrayMin(double[] values)
    {
    	double min = values[0];
    	for(double value : values)
    	{
    		if(value < min)
    			min = value;
    	}
    	
    	return min;
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