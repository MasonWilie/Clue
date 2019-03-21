package clueGame;

//import com.sun.prism.paint.Color;
import java.awt.Color;

public class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	

	public Card disproveSuggestion(Solution suggestion) {
		return null;
	}
	
	
	// For tests only
	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public Color getColor() {
		return color;
	}

	public String getPlayerName() {
		return playerName;
	}


	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}


	public void setRow(int row) {
		this.row = row;
	}


	public void setColumn(int column) {
		this.column = column;
	}


	public void setColor(Color color) {
		this.color = color;
	}
	
	
}
