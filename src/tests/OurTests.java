package tests;

/*
 * This program tests that config files are loaded properly.
 */

// Doing a static import allows me to write assertEquals rather than
// Assert.assertEquals
import static org.junit.Assert.*;

import java.util.Map;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;
import clueGame.DoorDirection;

public class OurTests {
	// Constants that I will use to test whether the file was loaded correctly
	public static final int LEGEND_SIZE = 10;
	public static final int NUM_ROWS = 25;
	public static final int NUM_COLUMNS = 25;

	// NOTE: I made Board static because I only want to set it up one 
	// time (using @BeforeClass), no need to do setup before each test.
	private static Board board;
	
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("Board_Layout.csv", "ClueRooms.txt", "players.txt", "cards.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
	}
	@Test
	public void testRooms() {
		// Get the map of initial => room 
		Map<Character, String> legend = board.getLegend();
		// Ensure we read the correct number of rooms
		
		for (Character name: legend.keySet()) {
			System.out.println(name + ": " + legend.get(name));
		}
		
		
		
		assertEquals(LEGEND_SIZE, legend.size());
		// To ensure data is correctly loaded, test retrieving a few rooms 
		// from the hash, including the first and last in the file and a few others
		assertEquals("Library", legend.get('L'));
		assertEquals("Ballroom", legend.get('B'));
		assertEquals("Billiard Room", legend.get('R'));
		assertEquals("Closet", legend.get('X'));
		assertEquals("Walkway", legend.get('W'));
	}
	
	@Test
	public void testBoardDimensions() {
		// Ensure we have the proper number of rows and columns
		assertEquals(NUM_ROWS, board.getNumRows());
		assertEquals(NUM_COLUMNS, board.getNumColumns());		
	}
	
	// Test a doorway in each direction (RIGHT/LEFT/UP/DOWN), plus 
	// two cells that are not a doorway.
	// These cells are white on the planning spreadsheet
	@Test
	public void FourDoorDirections() {
		BoardCell room = board.getCellAt(1, 6);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.RIGHT, room.getDoorDirection());
		
		room = board.getCellAt(7, 16);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.DOWN, room.getDoorDirection());
		
		room = board.getCellAt(8, 22);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.LEFT, room.getDoorDirection());
		
		room = board.getCellAt(17, 11);
		assertTrue(room.isDoorway());
		assertEquals(DoorDirection.UP, room.getDoorDirection());
		// Test that room pieces that aren't doors know it
		
		room = board.getCellAt(1, 7);
		assertFalse(room.isDoorway());	
		// Test that walkways are not doors
		
		BoardCell cell = board.getCellAt(0, 0);
		assertFalse(cell.isDoorway());		

	}
	
	// Test that we have the correct number of doors
	@Test
	public void testNumberOfDoorways() 
	{
		int numDoors = 0;
		for (int row=0; row<board.getNumRows(); row++)
			for (int col=0; col<board.getNumColumns(); col++) {
				BoardCell cell = board.getCellAt(row, col);
				if (cell.isDoorway())
					numDoors++;
			}
		Assert.assertEquals(14, numDoors);
	}

	// Test a few room cells to ensure the room initial is correct.
	@Test
	public void testRoomInitials() {
		
		assertEquals('K', board.getCellAt(13, 17).getInitial());
		assertEquals('B', board.getCellAt(19, 1).getInitial());
		assertEquals('S', board.getCellAt(2, 10).getInitial());
		
		// Testing a doorway
		assertEquals('L', board.getCellAt(1, 6).getInitial());
		
		// Test a walkway
		assertEquals('W', board.getCellAt(10, 0).getInitial());
		
		// Testing the corners
		assertEquals('L', board.getCellAt(0, 0).getInitial());
		assertEquals('H', board.getCellAt(0, 24).getInitial());
		assertEquals('W', board.getCellAt(24, 0).getInitial());
		assertEquals('O', board.getCellAt(24, 24).getInitial());

	}
	

}
