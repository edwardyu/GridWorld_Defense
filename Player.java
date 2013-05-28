package td;
/*
 * Player.java
 * The Player class is used for highscore purposes and allows the highscores to easily be sorted and managed
 * @author Edward Yu, Ronbo Fan
 * Period: 6
 * Date: 5/19/13
 * 
 */ 

public class Player implements Comparable {
	private String name;
	private int score;
	/*
	 * Constructs a Player with given name and score for highscore purposes
	 * @param s name of the player
	 * @param i score achieved by the player
	 */
	public Player(String s, int i) {
		name = s;
		score = i;
	}
	/*
	 * Get the name of the Player
	 * @return name of the player
	 */
	public String getName() {
		return name;
	}
	/*
	 * Get the Player's score
	 * @return score of the player
	 */
	public int getScore() {
		return score;
	}
	/*
	 * Compares this Player with another
	 * @return result of comparison between the two players
	 */
	public int compareTo(Object other) {
		return ((Player)other).getScore() - score;
	}
	/*
	 * Allows player data to be displayed neatly
	 * @return string containing formatted player name and score
	 */
	public String toString() {
		return name + ":" + score;
	}
	/*
	 * Tests for equality between two players
	 * If players have the same name and same score, then they are equal
	 * @return whether or not the two players are equal
	 */
	public boolean equals(Object other) {
		return name.equalsIgnoreCase(((Player)other).getName()) && score == ((Player)other).getScore();
	}
}