package clueGame;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Mouse implements MouseListener{
	
	private boolean hasClicked;
	private int col;
	private int row;
	
	private int cellDim;
	
	
	public Mouse(int cellDim) {
		this.cellDim = cellDim;
		this.hasClicked = false;
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
	

	@Override
	public void mouseClicked(MouseEvent e) {
		int colClickPix = e.getX();
		int rowClickPix = e.getY();
		
		col = (int)Math.floor((double)colClickPix / (double)cellDim);
		row = (int)Math.floor((double)rowClickPix / (double)cellDim);
		
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
