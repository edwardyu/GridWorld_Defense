package td;
/*
 * GameComponent.java
 * The GameComponent interface is implemented by all place-able structures
 * in GridDefense. It is used as an identifier for things that cost gold
 * and are placed by the user
 * @author Edward Yu, Ronbo Fan
 * Period: 6
 * Date: 5/19/13
 * 
 */


public interface GameComponent {	
	/*
	 * Gets the cost of placing the given structure
	 * @return price of the structure
	 */
	int getCost();	
}