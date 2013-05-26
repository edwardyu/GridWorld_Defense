package td;
/*
 * Barricade.java
 * The Barricade class prevents minions from reaching a certain square.  
 * @author Edward Yu, Ronbo Fan
 * Period: 6
 * Date: 5/19/13
 * 
 */



import info.gridworld.actor.*;

public class Barricade extends Actor implements GameComponent
{
	
    private static final int COST = 5;
    private TDWorld world;


/*
 * Constructs a barricade, which blocks objects from going through it
 * @param world the world in which the barricade will be placed.
 */
    public Barricade(TDWorld world) 
    {
            this.world = world;
            setColor(null);
    }

    /*
     * Gets the amount of money needed to buy a barricade
     * @return amount of money needed to buy a barricade
     */
    public int getCost() 
    {
            return COST;
    }
    
    /*
     * Barricades just sit there, like rocks
     */
    public void act()
    {

    }
    
 /*
  * Gets the world the barricade is in
  * @return world
  */       
    public TDWorld getWorld()
    {
            return world;
    }
	
}