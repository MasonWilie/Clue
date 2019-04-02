package tests;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.ComputerPlayer;
import clueGame.Solution;
import clueGame.BoardCell;
import clueGame.Card;
import clueGame.CardType;

/**
 * gameActionTests Class:
 * Tests for selecting a target location, checking an accusation, disproving a suggestion
 * handling a suggestion, and creating a suggestion.
 * 
 * @author Hunter Rich
 * @author Mason Wilie
 * 
 */

public class gameActionTests {
	private static Board board;
	@BeforeClass
	public void testTargetSetup() {
		board = Board.getInstance();
		board.setConfigFiles("Board_Layout.csv", "ClueRooms.txt", "players.txt", "cards.txt");		
		board.initialize();
	}
	
	@Test
	public void testTargetSelection() {
		ComputerPlayer cPlayer = new ComputerPlayer();
		cPlayer.setRow(5);
		cPlayer.setRow(6);
		int currentRow = cPlayer.getRow();
		int currentCol = cPlayer.getColumn();
		board.calcTargets(currentRow, currentCol, 3);
		Set<BoardCell> theTargets = board.getTargets();
		
		//run through choosing many different targets and making sure they are in the list of targets
		for (int i = 0; i < 20; i++) {
			BoardCell aTarget = cPlayer.chooseTarget(theTargets);
			assertTrue(theTargets.contains(aTarget));
		}
		
		
		
		
		// Testing by doorways
		cPlayer.setRow(3);
		cPlayer.setColumn(7);
		
		currentRow = cPlayer.getRow();
		currentCol = cPlayer.getColumn();
		
		board.calcTargets(currentRow, currentCol, 6);
		
		for (int i = 0; i < 20; i++) {
			BoardCell aTarget = cPlayer.chooseTarget(theTargets);
			assertTrue(aTarget.isDoorway());
		}
	}
	
	//test if a given set of 3 cards is the solution (which are 3 cards)
	@Test
	public void testAccusation() {
		Card accusingPerson = new Card("Fran", CardType.PERSON);
		Card accusingWeapon = new Card("Bazooka", CardType.WEAPON);
		Card accusingRoom = new Card("Library", CardType.ROOM);
		
		Solution theSolution = board.getSolution();
		
		assertTrue(theSolution.person.equals(accusingPerson) && theSolution.weapon.equals(accusingWeapon) && theSolution.room.equals(accusingRoom));
		
		//make some more situations where we set the solution and then test it (should assert as true)
		//maybe make another one that is of different people
	}
	
	
	
	
	
	
}
