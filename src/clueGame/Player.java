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
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
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
	
	protected ArrayList<Card> playerHand;

	
	
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
		this.allCards = new ArrayList<>();
		this.allCards.addAll(ogDeck);
		
		allRoomCards = new HashSet<>();
		allWeaponCards = new HashSet<>();
		allPersonCards = new HashSet<>();
		
		for (Card card : ogDeck) {
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
		playerHand = new ArrayList<>();
		playerHand.addAll(newHand);
		
		roomCards = new HashSet<>();
		weaponCards = new HashSet<>();
		personCards = new HashSet<>();
		
		
		// Divides cards into seperate hands
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
	
	public void paint(Graphics g, int diameter, int xPixLoc, int yPixLoc) {
		g.setColor(color);
		
		g.fillOval(xPixLoc, yPixLoc, diameter, diameter);
		
		
	}
	
public Card disproveSuggestion(Solution suggestion) {
		
		ArrayList<Card> sameCards = new ArrayList<>();
		
		
		// Get all the cards that the player has and are in the suggestion
		for (Card card : playerHand) {
			if (card.equals(suggestion.getPersonCard()) ||
				card.equals(suggestion.getRoomCard()) ||
				card.equals(suggestion.getWeaponCard())){
				sameCards.add(card);
			}
		}
		
		
		if (sameCards.size() == 0) return null; // Return null if can't disprove
		
		
		Random rand = new Random();
		
		return sameCards.get(rand.nextInt(sameCards.size())); // Return random one of the cards if can
		
		
		
		
		
		
	}
	
	
	
}
