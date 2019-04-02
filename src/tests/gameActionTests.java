package tests;

import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import clueGame.Board;
import clueGame.ComputerPlayer;
import clueGame.BoardCell;

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
	@Before
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
			BoardCell aTarget = cPlayer.chooseLocation();
			assertTrue(theTargets.contains(aTarget));
		}
		
	}
}
