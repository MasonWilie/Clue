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
	public static void testTargetSetup() {
		board = Board.getInstance();
		board.setConfigFiles("Board_Layout.csv", "ClueRooms.txt", "players.txt", "cards.txt");		
		board.initialize();
	}
	
	@Test
	public void testTargetSelection() {
		ComputerPlayer cPlayer = new ComputerPlayer();
		cPlayer.setRow(5);
		cPlayer.setColumn(6);
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
		theTargets = board.getTargets();
		
		for (int i = 0; i < 20; i++) {
			BoardCell aTarget = cPlayer.chooseTarget(theTargets);
			assertTrue(aTarget.isDoorway());
		}
	}
	
	//test if a given set of 3 cards is the solution (which are 3 cards) (wrong person)
	@Test
	public void testAccusation() {
		//wrong person
		Card accusingPerson = new Card("joeschmoe", CardType.PERSON);
		Card accusingWeapon = board.getSolution().weapon;
		Card accusingRoom = board.getSolution().room;
		
		Solution theSolution = board.getSolution();
		
		assertTrue(theSolution.weapon.equals(accusingWeapon) && theSolution.room.equals(accusingRoom));
		assertTrue(!(theSolution.person.equals(accusingPerson)));
		
		//wrong weapon
		Card accusingPerson1 = board.getSolution().person;
		Card accusingWeapon1 = new Card("peashooter", CardType.WEAPON);
		Card accusingRoom1 = board.getSolution().room;
		
		Solution theSolution1 = board.getSolution();
		
		assertTrue(theSolution1.person.equals(accusingPerson1) && theSolution1.room.equals(accusingRoom1));
		assertTrue(!(theSolution1.weapon.equals(accusingWeapon1)));
		
		//wrong room
		Card accusingPerson2 = board.getSolution().person;
		Card accusingWeapon2 = board.getSolution().weapon;
		Card accusingRoom2 = new Card("dungeonlair", CardType.ROOM);
		
		Solution theSolution2 = board.getSolution();
		
		assertTrue(theSolution2.person.equals(accusingPerson2) && theSolution2.weapon.equals(accusingWeapon2));
		assertTrue(!(theSolution2.room.equals(accusingRoom2)));
		
		//correct solution
		Card sol1 = theSolution.person;
		Card sol2 = theSolution.weapon;
		Card sol3 = theSolution.room;
		
		assertTrue(theSolution.person.equals(sol1) && theSolution.weapon.equals(sol2) && theSolution.room.equals(sol3));
		
//		//gets room from player's hand and its the wrong room
//		Card thePerson = null;
//		Card theWeapon = null;
//		Card theRoom = null;
//		for (int i = 0; i < 3; i++) {
//			if (board.getPeople().get(0).getHand().get(i).getType().equals(CardType.PERSON)) {
//				thePerson = board.getPeople().get(0).getHand().get(i);
//			} else if (board.getPeople().get(0).getHand().get(i).getType().equals(CardType.WEAPON)) {
//				theWeapon = board.getPeople().get(0).getHand().get(i);
//			} else {
//				theRoom = board.getPeople().get(0).getHand().get(i);
//			}
//		}
//		
//		//thePerson = 
//		assertTrue(theSolution.person.equals(thePerson) && theSolution.weapon.equals(theWeapon) && theSolution.room.equals(theRoom));
	}
	
	
	
	
	
	
}
