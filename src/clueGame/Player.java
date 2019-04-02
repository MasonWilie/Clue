package clueGame;

//import com.sun.prism.paint.Color;
import java.awt.Color;
import java.util.ArrayList;

public class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	//added this
	private ArrayList<Card> playerHand;
	
	public Player() {
		playerHand = new ArrayList<Card>();
	}
	
	public int getHandSize() {
		return playerHand.size();
	}
	
	public ArrayList<Card> getHand(){
		return playerHand;
	}
	

	public Card disproveSuggestion(Solution suggestion) {
		return null;
	}
	
	public void addToHand(Card aCard) {
		playerHand.add(aCard);
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
