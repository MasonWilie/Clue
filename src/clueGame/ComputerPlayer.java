package clueGame;

import java.util.ArrayList;
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
	
	
	
	public BoardCell chooseTarget(ArrayList<BoardCell> targets) {
		return new BoardCell(-1, -1);
	}
	
	
	
	
}
