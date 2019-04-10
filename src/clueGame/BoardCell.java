package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.io.FileNotFoundException;

import javax.swing.JPanel;

/**
 * BoardCell Class:
 * Represents one cell in the gameboard.
 * 
 * @author Hunter Rich
 * @author Mason Wilie
 * 
 */
public class BoardCell extends JPanel{
	private int row, column;
	private char initial;
	DoorDirection direction;
	
	private int cellDim;
	
	public int getRow() { 
		return row;
	}
	
	public void setCellDim(int dim) {
		this.cellDim = dim;
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
	
	public void paint(Graphics g) {
		if (this.isDoorway()) {
			
		}else if (this.isWalkway()) {
			
		}
		
	}
	
}
