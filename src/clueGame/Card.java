package clueGame;

/**
 * Card Class:
 * Represents a card and all attributes of a card
 * 
 * @author Hunter Rich
 * @author Mason Wilie
 * 
 */

public class Card {
	private String cardName;
	private CardType type;
	
	public Card(String name, CardType type) {
		this.cardName = name;
		this.type = type;
	}
	
	public String getName() {
		return cardName;
	}
	
	public boolean equals() {
		return true;
	}
	
	public CardType getType() {
		return type;
	}
}