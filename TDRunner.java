package td;
/*
 * TDRunner.java
 * The TDRunner class creates a game and runs it. 
 * @author Edward Yu, Ronbo Fan
 * Period: 6
 * Date: 5/19/13
 * 
 */


import java.util.*;

public class TDRunner 
{
	public static void main(String[] args) 
	{
		// Hide the tooltips
 		System.setProperty("info.gridworld.gui.tooltips", "hide");
		
 		// Include this statement to not highlight a selected cell 		  
 		System.setProperty("info.gridworld.gui.selection", "hide");   	 		    
 			
 		// Set the title for the frame
		System.setProperty("info.gridworld.gui.frametitle", "GridDefense by Ronbo and Edward");
		
		TDWorld world = new TDWorld();
		world.show();
		
		Scanner scan = new Scanner(System.in);
		world.instructions();
		while(!world.gameOver) {
			String command = scan.nextLine();
			command = command.toLowerCase();
			if(command.contains("output")) {
				String[] s = command.split(" ");
				if(s.length > 1) {
					StringBuilder build = new StringBuilder("");
					for(int k = 1; k < s.length; k++) {
						build.append(s[k] + " ");
					}
					world.getMenu().output(build.toString());
					continue;
				}
			}
			switch(command) {
				case "endgame":
					//world.end();
				break;
				case "ronboswag":
					world.cheater();
				break;
				case "getgold":
					System.out.println(world.getGold());
				break;
				case "addgold":
					if(world.cheats) {
						System.out.println("cheat code for 10k gold activated");
						world.addGold(10000);
					}
				break;
				case "barricade":
				case "firetower":
				case "watertower":
				case "moneyhut":
				case "basictower":
				case "magetower":
				case "minion":
					world.nextType(command);
				break;
				default:
					System.out.println("Not a valid command, sorry.");
				break;
			}
		}
		scan.close();
		
	}
}