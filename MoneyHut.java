package td;
/*
 * MoneyHut.java
 * A MoneyHut generates money, like an investment.
 * @author Edward Yu, Ronbo Fan
 * Period: 6
 * Date: 5/19/13
 * 
 */

public class MoneyHut extends BasicTower implements GameComponent{
	
	private static final int COST = 100;
	private static final int[] upgradeCost = {500, 1000};
	private static final int[] speed = {35, 25, 15};	

	private int timer;
	private int level = 1;
	
	/*
	 * Gets the level of the structure
	 * @return current level of the structure, from 1 to 3
	 */
	
    public int getLevel() {
    	return level;
    }
    
    /*
     * Levels up the structure
     */
    
    public void levelUp() {
    	level++;
    }	
    
    /*
     * Modifies the name of the .gif that represents this tower
     * according to what levels it is
     * @return the suffix of the image
     */
    
    public String getImageSuffix() { 
		if (level == 1) {
			return "";
		}
		else if (level == 2) {
			return "_2";
		}
		else {
			return "_3"; 
		}
	} 
    
    /*
     * Gets the amount of money needed to build a MoneyHut
     * @return amount of money needed to build a MoneyHut
     */

	public int getCost() {
		return COST;
	}
	
	/*
	 * Gets the upgrade-cost array for this structure
	 * @return an int[] containing two values for the tower upgrades from levels 1-2 and 2-3
	 */
	
	public int[] getUpgradeCost() {
		return upgradeCost;
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
     * Every few turns the MoneyHut will generate a random amount of gold in the range [5, 36], depending on level
     * The speed is dependent on the level as well. 
     */
	public void act() {
		timer--;
		if(timer == 0) {
			timer = speed[level - 1];
			int gold = (int)(Math.random() * (7 * level) + (level * 5));
			getWorld().addGold(gold);
			System.out.println("Money Hut generated " + gold + " gold!");
                        getWorld().printGold();
		}
	}
	/*
	 * Upgrade a MoneyHut to the next level. This will generate more money.
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