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
	
	private Solution suggestion;
	private BoardCell[] lastRooms = new BoardCell[2];
	private int roomCounter = 0;
	
	
	public BoardCell pickLocation(Set<BoardCell> targets) {
		return null;
	}
	
	public Solution makeSuggestion(Card currentRoom) {
		Random rand = new Random();
		
		// Get all the cards that the player does not have in their possession
		Set<Card> unseenWeaponCards = new HashSet<>();
		unseenWeaponCards.addAll(allWeaponCards);
		unseenWeaponCards.removeAll(weaponCards);
		
		Set<Card> unseenPersonCards = new HashSet<>();
		unseenPersonCards.addAll(allPersonCards);
		unseenPersonCards.removeAll(personCards);
		
		Solution guess;
		boolean newGuess = true;
		
		do {
			
			Card pGuess = null;
			Card wGuess = null;
			int randIndex;
			
			// Pick a random weapon card that the player does not have
			if (unseenWeaponCards.size() == 1 || unseenWeaponCards.size() == 0) {
				randIndex = 0;
			}else {
				randIndex = rand.nextInt(unseenWeaponCards.size());
			}
			
			int i = 0;
			for (Card card : unseenWeaponCards) {
				if (i == randIndex) {
					wGuess = card;
					break;
				}
				i++;
			}
			
			
			// Pick a random person card that the player does not have
			if (unseenPersonCards.size() == 1 || unseenPersonCards.size() == 0) {
				randIndex = 0;
			}else {
				randIndex = rand.nextInt(unseenPersonCards.size());
			}	
			i = 0;
			for (Card card : unseenPersonCards) {
				if (i == randIndex) {
					pGuess = card;
					break;
				}
				i++;
			}
			
			
			guess = new Solution(pGuess, currentRoom, wGuess);
			
			// Determine if the solution has already been guessed
			newGuess = true;
			for (Solution g : prevGuesses) {
				if (g.equals(guess)) {
					newGuess = false;
					break;
				}
			}
			
		}while(!newGuess);
		
		prevGuesses.add(guess); // Add the guess to the set of already guessed solutions
		
		return guess;
	}
	
	public boolean makeAccusation() {
		return Board.getInstance().handleAccusation(suggestion, this);
	}
	
	public void takeTurn() { // If the player is ready to make an accusation, make the accusation
		if (suggestion != null && !suggestion.disproven) {
			if (this.makeAccusation()) {
				System.out.println("Damn I won");
				return;
			}
		}
		
		this.makeMove(0, 0); // Player move
		
		BoardCell currentCell = Board.getInstance().getCellAt(this.getRow(), this.getColumn());
		
		if (currentCell.isRoom()) { // If they are in a room, make a suggestion
			Card roomCard = new Card(Board.getInstance().getLegend().get(currentCell.getInitial()), CardType.ROOM);
			this.suggestion = this.makeSuggestion(roomCard);
			Board.getInstance().handleSuggestion(this.suggestion, this);
		}
		
		
		
	}
	
	
	// Selects a target for the player to go to
	public BoardCell chooseTarget(Set<BoardCell> targets) {
		
		ArrayList<BoardCell> doors = new ArrayList<>();
		Random rand = new Random();
		
		// Finds all doorways (which have priority)
		for (BoardCell cell : targets) {
			boolean validDoor = cell.isDoorway();
			validDoor &= this.lastRooms[0] == null || (cell != this.lastRooms[0] && cell.getInitial() != this.lastRooms[0].getInitial());
			validDoor &= this.lastRooms[1] == null || (cell != this.lastRooms[1] && cell.getInitial() != this.lastRooms[1].getInitial());
			
			if (validDoor) {
				doors.add(cell);
			}
		}
		
		// If there are no doorways, pick a random cell
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
		
		// If there are doorways, pick a random doorway
		int selectionIndex = rand.nextInt(doors.size());
		this.lastRooms[this.roomCounter % 2] = doors.get(selectionIndex);
		roomCounter++;
		
		return doors.get(selectionIndex);
		
	}
	
	//moves computer player to a random location from the list of possible locations to move from (chooseTarget)
	@Override
	public boolean makeMove(int row, int col /*these are necessary but unused*/) {
		Board.getInstance().calcTargets(this.getRow(), this.getColumn(), this.getDieRoll());
		BoardCell cellToMoveTo = chooseTarget(Board.getInstance().getTargets());	
		
		this.move(cellToMoveTo.getRow(), cellToMoveTo.getColumn());
		return true;
	}
	
	
	
	
	
	
	
	
}
