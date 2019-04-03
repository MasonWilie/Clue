package clueGame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * ComputerPlayer Class:
 * Represents a computer player and all attributes of a computer player.
 * 
 * @author Hunter Rich
 * @author Mason Wilie
 * 
 */

public class ComputerPlayer extends Player{
	
	
	public BoardCell pickLocation(Set<BoardCell> targets) {
		return null;
	}
	
	public Solution makeSuggestion() {
		Random rand = new Random();
		
		Set<Card> unseenWeaponCards = allWeaponCards;
		unseenWeaponCards.removeAll(weaponCards);
		
		Set<Card> unseenPersonCards = allPersonCards;
		unseenPersonCards.removeAll(personCards);
		
		Set<Card> unseenRoomCards = allRoomCards;
		unseenRoomCards.removeAll(roomCards);
		
		Solution guess;
		boolean newGuess = true;
		
		do {
			
			Card pGuess = null;
			Card rGuess = null;
			Card wGuess = null;
			
			int randIndex = rand.nextInt(unseenWeaponCards.size());
			int i = 0;
			for (Card card : unseenWeaponCards) {
				if (i == randIndex) {
					wGuess = card;
					break;
				}
				i++;
			}
			
			randIndex = rand.nextInt(unseenPersonCards.size());
			i = 0;
			for (Card card : unseenPersonCards) {
				if (i == randIndex) {
					pGuess = card;
					break;
				}
				i++;
			}
			
			randIndex = rand.nextInt(unseenRoomCards.size());
			i = 0;
			for (Card card : unseenRoomCards) {
				if (i == randIndex) {
					rGuess = card;
					break;
				}
				i++;
			}
			
			
			
			guess = new Solution(pGuess, rGuess, wGuess);
			
			newGuess = true;
			for (Solution g : prevGuesses) {
				if (g.equals(guess)) {
					newGuess = false;
					break;
				}
			}
			
		}while(!newGuess);
		
		return guess;
	}
	
	
	
	
	
	
	
	public BoardCell chooseTarget(Set<BoardCell> targets) {
		
		ArrayList<BoardCell> doors = new ArrayList<>();
		Random rand = new Random();
		
		for (BoardCell cell : targets) {
			if (cell.isDoorway()) {
				doors.add(cell);
			}
		}
		
		if (doors.isEmpty()) {
			int selectionIndex = rand.nextInt(targets.size());
			int index = 0;
			for (BoardCell cell : targets) {
				if (index == selectionIndex) {
					return cell;
				}
				index++;
			}
		}
		
		int selectionIndex = rand.nextInt(doors.size());
		return doors.get(selectionIndex);
		
	}
	
	
	
	
	
}
