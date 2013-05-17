package td;

import info.gridworld.grid.*;
import info.gridworld.world.*;
import info.gridworld.actor.*;

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
		System.setProperty("info.gridworld.gui.frametitle", "Super TD");
		
		TDWorld world = new TDWorld();
		world.add(new Barricade());
		world.show();
		
		Scanner scan = new Scanner(System.in);
		
		while(world.gameMode == 0) {
			String command = scan.nextLine();
			command = command.toLowerCase();
			switch(command) {
				case "endgame":
					//world.end();
				break;
				case "haxm0deronboswag":
					world.cheater();
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
		
	}
}