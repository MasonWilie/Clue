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
	
	protected Set<Card> allPersonCards;
	protected Set<Card> allRoomCards;
	protected Set<Card> allWeaponCards;
	
	protected Set<Card> personCards;
	protected Set<Card> roomCards;
	protected Set<Card> weaponCards;
	
	private BoardCell target;
	
	private ArrayList<Card> playerHand;
	
	
	public Player() {
		playerHand = new ArrayList<Card>();
		prevGuesses = new HashSet<>();
		
		allPersonCards = new HashSet<>();
		allRoomCards = new HashSet<>();
		allWeaponCards = new HashSet<>();
		
		personCards = new HashSet<>();
		roomCards = new HashSet<>();
		weaponCards = new HashSet<>();
		
	}
	
	public void setDeck(ArrayList<Card> ogDeck) {
		this.allCards = ogDeck;
		
		for (Card card : this.allCards) {
			switch(card.getType()) {
			case ROOM:
				allRoomCards.add(card);
				break;
			case WEAPON:
				allWeaponCards.add(card);
				break;
			case PERSON:
				allPersonCards.add(card);
				break;
			}
		}
		
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
		switch (aCard.getType()) {
		case ROOM:
			roomCards.add(aCard);
			break;
		case WEAPON:
			weaponCards.add(aCard);
			break;
		case PERSON:
			personCards.add(aCard);
			break;
		}
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
	
	
	public void setHand(ArrayList<Card> newHand) {
		playerHand = newHand;
		
		for (Card card : newHand) {
				switch (card.getType()) {
				case ROOM:
					roomCards.add(card);
					break;
				case WEAPON:
					weaponCards.add(card);
					break;
				case PERSON:
					personCards.add(card);
					break;
				}
		}
		
		
		
		
	}
	
	
	
}
