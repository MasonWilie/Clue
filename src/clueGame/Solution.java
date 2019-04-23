package clueGame;

/**
 * Solution Class:
 * Holds the solution cards
 * 
 * @author Hunter Rich
 * @author Mason Wilie
 * 
 */

public class Solution {
	public Card person;
	public Card room;
	public Card weapon;
	
	public boolean disproven;
	
	public Solution(Card p, Card r, Card w) {
		this.person = p;
		this.room = r;
		this.weapon = w;
		this.disproven = false;
	}
	
	public Card getPersonCard() {
		return this.person;
	}
	
	public Card getRoomCard() {
		return this.room;
	}
	
	public Card getWeaponCard() {
		return this.weapon;
	}
	
	
	@Override
	public boolean equals(Object obj) {
		if (obj.getClass() != this.getClass()) return false;
		
		Solution solution = (Solution)obj; 
		
		return this.getPersonCard().equals(solution.getPersonCard()) &&
				this.getRoomCard().equals(solution.getRoomCard()) &&
				this.getWeaponCard().equals(solution.getWeaponCard());
		
		
	}
	
	
}
