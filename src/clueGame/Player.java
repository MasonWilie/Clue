package clueGame;

/**
 * Player Class:
 * Parent for the HumanPlayer and ComputerPlayer class, holds information and methods used by each of the
 * players throughout the game.
 * 
 * @author Hunter Rich
 * @author Mason Wilie
 * 
 */

//import com.sun.prism.paint.Color;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Player {
	private String playerName;
	private int row;
	private int column;
	private Color color;
	
	protected Set<Solution> prevGuesses;
	protected ArrayList<Card> allCards;
	
	private BoardCell target;
	
	private ArrayList<Card> playerHand;
	
	
	public Player() {
		playerHand = new ArrayList<Card>();
		prevGuesses = new HashSet<>();
	}
	
	public void setDeck(ArrayList<Card> ogDeck) {
		this.allCards = ogDeck;
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
