package td;

import info.gridworld.grid.*;
import info.gridworld.world.*;
import info.gridworld.actor.*;

import java.awt.*;

import java.util.ArrayList;


public class TDWorld extends World<Actor>
{    
    private static String DEFAULT_MESSAGE = "Welcome to Super TD!";
    
    public static final boolean DEBUG = true;
    
    public static boolean cheats = false;
    
    public final Location startLoc;
    public final Location endLoc;
    
    public static String lastAdded;
    
    public int hp;
    public int gold;
    
    public Actor nextToAdd;
    public int gameMode; // 0 - ingame 1 - won 2 - lost
    
    public Graphics2D g2;
    
    public void db(Object o) {
    	System.out.println(o.toString());
    }
    
    
    public TDWorld()
    {
        startLoc = new Location(getGrid().getNumRows() - 1, 0);
        endLoc = new Location(0, getGrid().getNumCols() - 1);
        load();
    }
    
    public TDWorld(Location loc1, Location loc2) {
    	startLoc = loc1;
    	endLoc = loc2;
    	load();
    } 

    public TDWorld(Grid<Actor> grid)
    {
        super(grid);
        startLoc = new Location(getGrid().getNumRows() - 1, 0);
        endLoc = new Location(0, getGrid().getNumCols() - 1);
        load();
    }
    
    public void load() {
    	gameMode = 0;
    	gold = 100;
    	hp = 20;
    	add(startLoc, new Shade());
    	add(endLoc, new Shade());
    }
    
    public void cheater() {
    	cheats = !cheats;
    }
    
    public void nextType(String s) {
    	db("startLoc: " + startLoc + " endLoc: " + endLoc);
    	switch(s) {
    		case "barricade":
    			nextToAdd = new Barricade();
    		break;
    		case "firetower":
    			nextToAdd = new FireTower();
    		break;
    		case "watertower":
    			nextToAdd = new WaterTower();
    		break;
    		case "magetower":
    			nextToAdd = new MageTower();
    		break;
    		case "moneyhut":
    			nextToAdd = new MoneyHut();
    		break;
    		case "basictower":
    			nextToAdd = new BasicTower();
    		break;
    		case "minion":
    			nextToAdd = new Minion(startLoc, endLoc);
    		break;
    		default:
    			System.out.println("error 1");
    		break;
    	}
    	lastAdded = s;
    }

    public void show()
    {
        if (getMessage() == null)
            setMessage(DEFAULT_MESSAGE);
        super.show();
    }    
    	
    /**
     * This method is called when the user clicks on a location in the
     * WorldFrame.
     * 
     * @param loc the grid location that the user selected
     * @return true if the world consumes the click, or false if the GUI should
     * invoke the Location->Edit menu action
     */
    public boolean locationClicked(Location loc)
    {
    	if(nextToAdd == null)
    		return true;
    	add(loc, nextToAdd);
    	
    	if(!cheats) {
    		nextToAdd = null;
    	} else {
    		nextType(lastAdded);
    	}
    	return true;
        //return false;
    }

    public void step()
    {
        Grid<Actor> gr = getGrid();
        ArrayList<Actor> actors = new ArrayList<Actor>();
        for (Location loc : gr.getOccupiedLocations())
            actors.add(gr.get(loc));

        for (Actor a : actors)
        {
            if (a.getGrid() == gr)
                a.act();
        }
        if(gr.get(startLoc) == null)
    		add(startLoc, new Shade());
        if(gr.get(endLoc) == null)
    		add(endLoc, new Shade());
        	
    }

    public void add(Location loc, Actor occupant)
    {
        occupant.putSelfInGrid(getGrid(), loc);
    }

    public void add(Actor occupant)
    {
        Location loc = getRandomEmptyLocation();
        if (loc != null)
            add(loc, occupant);
    }

    public Actor remove(Location loc)
    {
        Actor occupant = getGrid().get(loc);
        if (occupant == null)
            return null;
        occupant.removeSelfFromGrid();
        return occupant;
    }
}