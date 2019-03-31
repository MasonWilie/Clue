package clueGame;

import java.io.FileNotFoundException;

/**
 * BoardCell Class:
 * Represents one cell in the gameboard.
 * 
 * @author Hunter Rich
 * @author Mason Wilie
 * 
 */
public class BoardCell {
	private int row, column;
	private char initial;
	DoorDirection direction;
	
	public int getRow() { 
		return row;
	}

	public DoorDirection getDoorDirection() {
		return direction;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public char getInitial() {
		return initial;
	}

	public void setInitial(char initial) {
		this.initial = initial;
	}

	public BoardCell(int setRow, int setColumn) {
		row = setRow;
		column = setColumn;
	}
	
	public boolean isWalkway() {
		return initial == 'W';
	}
	
	public boolean isRoom() throws FileNotFoundException {
		return initial != 'W';
	}
	
	public boolean isDoorway() {
		return !(direction.equals(DoorDirection.NONE));
	}
	
	public void setDoorDirection(DoorDirection newDirection) {
		direction = newDirection;
	}
	
}
