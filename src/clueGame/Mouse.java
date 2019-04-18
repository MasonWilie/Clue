package clueGame;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Mouse Class:
 * Handles mouse click events in our ControlGUI class. Is able to return 
 * row and column in format that makes sense in the game.
 * 
 * @author Hunter Rich
 * @author Mason Wilie
 * 
 */

public class Mouse implements MouseListener{
	
	private boolean hasClicked;
	private int col;
	private int row;
	
	private int colPixOffset;
	private int rowPixOffset;
	
	private int cellDim;
	
	
	public Mouse(int cellDim) {
		this.cellDim = cellDim;
		this.hasClicked = false;
		
		this.colPixOffset = 0;
		this.rowPixOffset = 0;
	}
	
	public boolean hasClicked() {
		boolean returnValue = hasClicked;
		hasClicked = false;
		return returnValue;
	}
	
	public int getClickRow() {
		return row;
	}
	
	public int getClickCol() {
		return col;
	}
	
	public void setColOffset(int offset) {
		colPixOffset = offset;
	}
	
	public void setRowOffset(int offset) {
		rowPixOffset = offset;
	}
	

	@Override
	public void mouseClicked(MouseEvent e) {
		int colClickPix = e.getX() - colPixOffset;
		int rowClickPix = e.getY() - rowPixOffset;
		
		col = (int)Math.floor((double)colClickPix / (double)cellDim);
		row = (int)Math.floor((double)rowClickPix / (double)cellDim) - 1;
		
		hasClicked = true;
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


}
