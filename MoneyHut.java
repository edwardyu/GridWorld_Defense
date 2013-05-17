package td;

import info.gridworld.actor.*;

public class MoneyHut extends Barricade implements GameComponent{
	
	private static final int COST = 100;
	private static final int[] upgradeCost = {50, 100, 200};
	private static final int[] speed = {20, 15, 10};	

	private int timer;
	private int level = 1;
	
	public int getCost() {
		return COST;
	}
	
	public MoneyHut() {
		setColor(null);
		speed = 20; //extra gold every 20 steps
		timer = speed;
	}
	
	public void act() {
		timer--;
		if(timer == 0) {
			timer = speed;
			int gold = (int)(Math.random() * (7 * level) + (level * 5)
			getWorld().addGold();
			System.out.println("Money Hut generated " + gold + " gold!");
		}
		speed = 20 - ((level - 1) * 5);
	}

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