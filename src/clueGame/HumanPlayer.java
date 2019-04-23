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
	
	private boolean targetSelected;

	@Override
	public boolean makeMove(int row, int col) {
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
		
		
		
		
		
		if (!inTargets) {
			ControlGUI.handleErrors(ControlGUI.ErrorType.INVALID_TARGET);
			
			return false;
		}else {
			Board.getInstance().getCellAt(this.getRow(), this.getColumn()).setPlayer(null);
			this.setRow(row);
			this.setColumn(col);
			
		}
		
		Board.getInstance().getCellAt(this.getRow(), this.getColumn()).setPlayer(this);
		
		if (Board.getInstance().getCellAt(this.getRow(), this.getColumn()).getChosenTarget.isRoom()) {
			ControlGUI.displayModal();
		}
		Board.getInstance().setHumanHasSelectedTarget(true);
		return true;
	}

	
}
