package td;


public class Player implements Comparable {
	private String name;
	private int score;
	public Player(String s, int i) {
		name = s;
		score = i;
	}
	public String getName() {
		return name;
	}
	public int getScore() {
		return score;
	}
	public int compareTo(Object other) {
		return ((Player)other).getScore() - score;
	}
	public String toString() {
		return name + ":" + score;
	}
	public boolean equals(Object other) {
		return name.equals(((Player)other).getName()) && score == ((Player)other).getScore();
	}
}