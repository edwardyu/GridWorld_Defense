package td;

import info.gridworld.grid.*;
import info.gridworld.world.*;
import info.gridworld.actor.*;

import java.util.ArrayList;


public class TDWorld extends World<Actor>
{    
    private static final String DEFAULT_MESSAGE = "Ronbo was here.";
    
    public static final boolean DEBUG = true;
    
    public void db(Object o) {
    	System.out.println(o.toString());
    }
    
    public Actor nextToAdd;
    
    public TDWorld()
    {
    }

    public TDWorld(Grid<Actor> grid)
    {
        super(grid);
    }
    
    public void nextType(String s) {
    	switch(s) {
    		case "barricade":
    			nextToAdd = new Barricade();
    			db("barricade");
    		break;
    		case "firetower":
    			nextToAdd = new FireTower();
    			db("firetower");
    		break;
    		case "watertower":
    			nextToAdd = new WaterTower();
    			db("watertower");
    		break;
    		case "magetower":
    			nextToAdd = new MageTower();
    			db("magetower");
    		break;
    		case "moneyhut":
    			nextToAdd = new MoneyHut();
    			db("moneyhut");
    		break;
    		case "basictower":
    			nextToAdd = new BasicTower();
    			db("basictower");
    		break;
    		default:
    			System.out.println("error 1");
    		break;
    	}
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
    	nextToAdd = null;
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