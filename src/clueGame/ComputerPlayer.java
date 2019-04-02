package clueGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * ComputerPlayer Class:
 * Represents a computer player and all attributes of a computer player.
 * 
 * @author Hunter Rich
 * @author Mason Wilie
 * 
 */

public class ComputerPlayer extends Player{
	
	public BoardCell pickLocation(Set<BoardCell> targets) {
		return null;
	}
	
	public void makeAccusation() {
		
	}
	
	public void createSuggestion() {
		
	}
	
	
	
	public BoardCell chooseTarget(Set<BoardCell> targets) {
		
		ArrayList<BoardCell> doors = new ArrayList<>();
		Random rand = new Random();
		
		for (BoardCell cell : targets) {
			if (cell.isDoorway()) {
				doors.add(cell);
			}
		}
		
		if (doors.isEmpty()) {
			int selectionIndex = rand.nextInt(targets.size());
			int index = 0;
			for (BoardCell cell : targets) {
				if (index == selectionIndex) {
					return cell;
				}
				index++;
			}
		}
		
		int selectionIndex = rand.nextInt(doors.size());
		return doors.get(selectionIndex);
		
	}
	
	
	
	
}
