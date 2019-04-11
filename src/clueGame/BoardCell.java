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
	
	private Player playerOnCell;
	
	private int cellDim;
	private int boardRes_x, boardRes_y;
	private boolean drawLabel = false;
	private String label;
	
	public int getRow() { 
		return row;
	}
	
	public void setPlayer(Player newPlayer) {
		playerOnCell = newPlayer;
	}
	
	public void removePlayer() {
		playerOnCell = null;
	}
	
	public void setDrawLabel(String label) {
		this.drawLabel = true;
		this.label = label;
	}
	
	public void setCellDim(int dim) {
		this.cellDim = dim;
	}
	
	public void setBoardRes(int boardRes_x, int boardRes_y) {
		this.boardRes_x = boardRes_x;
		this.boardRes_y = boardRes_y;
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
		int xPixLoc = this.column * cellDim;
		int yPixLoc = this.row * cellDim;
		
		if (this.isDoorway()) {
			int thickness = (int)((double)cellDim * 0.15);
			if (thickness == 0) thickness = 1;
			
			int length = cellDim;
			
			int height=0, width=0;
			switch(this.getDoorDirection()) {
			case UP:
				height = thickness;
				width = length;
				break;
			case DOWN:
				height = thickness;
				width = length;
				yPixLoc += cellDim - thickness;
				break;
			case LEFT:
				height = length;
				width = thickness;
				break;
			case RIGHT:
				height = length;
				width = thickness;
				xPixLoc += cellDim - thickness;
			}
			g.setColor(Color.BLUE);
			g.fillRect(xPixLoc, yPixLoc, width, height);
			
		}else if (this.isWalkway()) {
			int borderWidth = (int)((double)cellDim * 0.05);
			if (borderWidth == 0) borderWidth = 1;
			
			
			g.setColor(Color.BLACK);
			g.fillRect(xPixLoc, yPixLoc, cellDim, cellDim);
			
			g.setColor(Color.YELLOW);
			g.fillRect(xPixLoc + borderWidth, yPixLoc + borderWidth, cellDim - (2 * borderWidth), cellDim - (2 * borderWidth));
		}
		
		if (drawLabel) {
			g.setColor(Color.BLUE);
			g.drawString(label, xPixLoc, yPixLoc);
		}
		
		if (playerOnCell != null) {
			playerOnCell.paint(g, cellDim, xPixLoc, yPixLoc);
		}
		
	}
	
	
}
