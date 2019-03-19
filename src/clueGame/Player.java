package clueGame;

import com.sun.prism.paint.Color;

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
	
	
}
