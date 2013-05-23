/*
 * MoneyHut.java
 * A MoneyHut generates money, like an investment.
 * @author Edward Yu, Ronbo Fan
 * Period: 6
 * Date: 5/19/13
 * 
 */
package td;

import info.gridworld.actor.*;

public class MoneyHut extends Barricade implements GameComponent{
	
	private static final int COST = 100;
	private static final int[] upgradeCost = {50, 100, 200};
	private static final int[] speed = {20, 15, 10};	

	private int timer;
	private int level = 1;
	//private TDWorld world;
        
    /*
     * Gets the amount of money needed to build a MoneyHut
     * @return amount of money needed to build a MoneyHut
     */	
	public int getCost() {
		return COST;
	}

        /*
         * Constructs a MoneyHut
         * @param world the world which controls it
         */
	public MoneyHut(TDWorld world) {
		super(world);
		setColor(null);
		timer = speed[0];
	}
	
        /*
         * Every few turns the moneyhut will generate a random amount of gold in the range [5, 36], depending on level
         * The speed is dependent on the level as well. 
         */
	public void act() {
		timer--;
		if(timer == 0) {
			timer = speed[level - 1];
			int gold = (int)(Math.random() * (7 * level) + (level * 5));
			getWorld().addGold(gold);
			System.out.println("Money Hut generated " + gold + " gold!");
		}
	}
/*
 * Upgrade a moneyhut to the next level. This will generate more money.
 */
	public void upgrade() {
		if(getWorld().getGold() >= upgradeCost[level - 1]) {
			getWorld().takeGold(upgradeCost[level - 1]);
			level++;
		 	System.out.println("Upgraded to level " + level +"!");	
		} else {
			System.out.println("Sorry, but you need " + upgradeCost[level - 1] + " gold to upgrade!");
		}
	}

}