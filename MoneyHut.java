package td;

import info.gridworld.actor.*;

public class MoneyHut extends Actor implements GameComponent{
	
	private static final int COST = 100;
	private static final int[] upgradeCost = {50, 100, 200};
	private static final int[] speed = {20, 15, 10};	

	private int timer;
	private int level = 1;
	private TDWorld world;
	
	public int getCost() {
		return COST;
	}
	
	public MoneyHut(TDWorld world) {
		this.world = world;
		setColor(null);
		timer = speed[0];
	}
	
	public void act() {
		timer--;
		if(timer == 0) {
			timer = speed[level - 1];
			int gold = (int)(Math.random() * (7 * level) + (level * 5));
			world.addGold(gold);
			System.out.println("Money Hut generated " + gold + " gold!");
		}
	}

	public void upgrade() {
		if(world.getGold() >= upgradeCost[level - 1]) {
			world.takeGold(upgradeCost[level - 1]);
			level++;
		 	System.out.println("Upgraded to level " + level +"!");	
		} else {
			System.out.println("Sorry, but you need " + upgradeCost[level - 1] + " gold to upgrade!");
		}
	}

}