package clueGame;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;
import java.util.Set;

/**
 * HumanPlayer Class:
 * Represents a human player and all attributes of a human player.
 * 
 * @author Hunter Rich
 * @author Mason Wilie
 * 
 */

public class HumanPlayer extends Player{

	@Override
	public void makeMove(int row, int col) {
		System.out.println("Die roll: " + Integer.toString(this.getDieRoll()));
		Board.getInstance().calcTargets(this.getRow(), this.getColumn(), this.getDieRoll());
		Set<BoardCell> theTargets = Board.getInstance().getTargets();
		//check if the playerchoice boardcell is in the list of targets
		
		boolean inTargets = false;

		
		for (BoardCell cell : theTargets) {
			if (cell.getRow() == row && cell.getColumn() == col) {
				inTargets = true;
				break;
				
			}
		}
		
		
		Board.getInstance().getCellAt(this.getRow(), this.getColumn()).setPlayer(null);
		
		
		if (!inTargets) {
			System.out.println("Choose a valid target");
			return;
		}else {
			this.setRow(row);
			this.setColumn(col);
			
		}
		
		Board.getInstance().getCellAt(this.getRow(), this.getColumn()).setPlayer(this);
		
		System.out.println("New row: " + this.getRow());
		System.out.println("New col: " + this.getColumn());
		
		
		Board.getInstance().setHumanHasSelectedTarget(true);
	}
	
}
