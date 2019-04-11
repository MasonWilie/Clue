package tests;

/*
 * This program tests that adjacencies and targets are calculated correctly.
 * 
 * This is our version of the tests using our game board.
 * Hunter Rich, Mason Wilie
 */

import java.util.Set;

//Doing a static import allows me to write assertEquals rather than
//assertEquals
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class CTest_BoardAdjTargetTests2 {
	// We make the Board static because we can load it one time and 
	// then do all the tests. 
	private static Board board;
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("Board_Layout.csv", "ClueRooms.txt", "players_test.txt", "cards.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
	}

	// Ensure that player does not move around within room
	// These cells are ORANGE on the planning spreadsheet
	@Test
	public void testAdjacenciesInsideRooms()
	{
		// Test a corner
		Set<BoardCell> testList = board.getAdjList(0, 0);
		assertEquals(0, testList.size());
		// Test one that has walkway underneath
		testList = board.getAdjList(7, 0);
		assertEquals(0, testList.size());
		// Test one that has walkway above
		testList = board.getAdjList(13, 0);
		assertEquals(0, testList.size());
		// Test one that is in middle of room
		testList = board.getAdjList(14, 17);
		assertEquals(0, testList.size());
		// Test one beside a door
		testList = board.getAdjList(6, 16);
		assertEquals(0, testList.size());
		// Test one in a corner of room
		testList = board.getAdjList(10, 15);
		assertEquals(0, testList.size());
	}

	// Ensure that the adjacency list from a doorway is only the
	// walkway. NOTE: This test could be merged with door 
	// direction test. 
	// These tests are PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyRoomExit()
	{
		// TEST DOORWAY RIGHT 
		Set<BoardCell> testList = board.getAdjList(19, 6);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(19, 7)));
		// TEST DOORWAY LEFT 
		testList = board.getAdjList(22, 19);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(22, 18)));
		//TEST DOORWAY DOWN
		testList = board.getAdjList(7, 16);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(8, 16)));
		//TEST DOORWAY UP
		testList = board.getAdjList(10, 17);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(9, 17)));
		//TEST DOORWAY UP, WHERE THERE'S A WALKWAY TO THE LEFT
		testList = board.getAdjList(12, 22);
		assertEquals(1, testList.size());
		assertTrue(testList.contains(board.getCellAt(11, 22)));
		
	}
	
	// Test adjacency at entrance to rooms
	// These tests are GREEN in planning spreadsheet
	@Test
	public void testAdjacencyDoorways()
	{
		// Test beside a door direction DOWN
		Set<BoardCell> testList = board.getAdjList(9, 2);
		assertTrue(testList.contains(board.getCellAt(10, 2)));
		assertTrue(testList.contains(board.getCellAt(8, 2)));
		assertTrue(testList.contains(board.getCellAt(9, 3)));
		assertTrue(testList.contains(board.getCellAt(9, 1)));
		assertEquals(4, testList.size());
		// Test beside a door direction RIGHT
		testList = board.getAdjList(19, 7);
		assertTrue(testList.contains(board.getCellAt(19, 8)));
		assertTrue(testList.contains(board.getCellAt(19, 6)));
		assertTrue(testList.contains(board.getCellAt(20, 7)));
		assertTrue(testList.contains(board.getCellAt(18, 7)));
		assertEquals(4, testList.size());
		// Test beside a door direction LEFT
		testList = board.getAdjList(8, 21);
		assertTrue(testList.contains(board.getCellAt(9, 21)));
		assertTrue(testList.contains(board.getCellAt(7, 21)));
		assertTrue(testList.contains(board.getCellAt(8, 22)));
		assertTrue(testList.contains(board.getCellAt(8, 20)));
		assertEquals(4, testList.size());
		// Test beside a door direction UP
		testList = board.getAdjList(16, 10);
		assertTrue(testList.contains(board.getCellAt(17, 10)));
		assertTrue(testList.contains(board.getCellAt(15, 10)));
		assertTrue(testList.contains(board.getCellAt(16, 9)));
		assertTrue(testList.contains(board.getCellAt(16, 11)));
		assertEquals(4, testList.size());
	}

	// Test a variety of walkway scenarios
	// These tests are LIGHT PURPLE on the planning spreadsheet
	@Test
	public void testAdjacencyWalkways()
	{
		// Test on top edge of board, two walkway pieces
		Set<BoardCell> testList = board.getAdjList(0, 7);
		assertTrue(testList.contains(board.getCellAt(0, 8)));
		assertTrue(testList.contains(board.getCellAt(1, 7)));
		assertEquals(2, testList.size());
		
		// Test on left edge of board, one walkway piece
		testList = board.getAdjList(8, 0);
		assertTrue(testList.contains(board.getCellAt(9, 0)));
		assertEquals(1, testList.size());

		// Test a walkway next to closet, 2 walkway pieces
		testList = board.getAdjList(12, 9);
		assertTrue(testList.contains(board.getCellAt(12, 10)));
		assertTrue(testList.contains(board.getCellAt(13, 9)));
		assertEquals(2, testList.size());

		// Test surrounded by 4 walkways
		testList = board.getAdjList(11,4);
		assertTrue(testList.contains(board.getCellAt(11, 5)));
		assertTrue(testList.contains(board.getCellAt(11, 3)));
		assertTrue(testList.contains(board.getCellAt(12, 4)));
		assertTrue(testList.contains(board.getCellAt(10, 4)));
		assertEquals(4, testList.size());
		
		// Test on bottom edge of board, next to 1 room piece
		testList = board.getAdjList(24, 18);
		assertTrue(testList.contains(board.getCellAt(23, 18)));
		assertTrue(testList.contains(board.getCellAt(24, 17)));
		assertEquals(2, testList.size());
		
		// Test on right edge of board, next to 1 room piece
		testList = board.getAdjList(10, 24);
		assertTrue(testList.contains(board.getCellAt(11, 24)));
		assertTrue(testList.contains(board.getCellAt(10, 23)));
		assertEquals(2, testList.size());

		// Test on walkway next to door that is not in the needed
		// direction to enter
		testList = board.getAdjList(12, 21);
		assertTrue(testList.contains(board.getCellAt(11, 21)));
		assertTrue(testList.contains(board.getCellAt(13, 21)));
		assertTrue(testList.contains(board.getCellAt(12, 20)));
		assertEquals(3, testList.size());
	}
	
	
	// Tests of just walkways, 1 step, includes on edge of board
	// and beside room
	// Have already tested adjacency lists on all four edges, will
	// only test two edges here
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(24, 17, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(23, 17)));
		assertTrue(targets.contains(board.getCellAt(24, 18)));	
		
		board.calcTargets(10, 24, 1);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(11, 24)));	
		assertTrue(targets.contains(board.getCellAt(10, 23)));			
	}
	
	// Tests of just walkways, 2 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(10, 1, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCellAt(9, 0)));
		assertTrue(targets.contains(board.getCellAt(11, 2)));
		assertTrue(targets.contains(board.getCellAt(11, 0)));
		assertTrue(targets.contains(board.getCellAt(9, 2)));
		assertTrue(targets.contains(board.getCellAt(12, 1)));
		assertTrue(targets.contains(board.getCellAt(10, 3)));
		
		board.calcTargets(17, 13, 2);
		targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCellAt(17, 15)));
		assertTrue(targets.contains(board.getCellAt(18, 14)));	
		assertTrue(targets.contains(board.getCellAt(16, 14)));
		assertTrue(targets.contains(board.getCellAt(16, 12)));
		assertTrue(targets.contains(board.getCellAt(15, 13)));
	}
	
	// Tests of just walkways, 4 steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(24, 0, 4);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(24, 4)));
		assertTrue(targets.contains(board.getCellAt(23, 3)));
		assertTrue(targets.contains(board.getCellAt(24, 2)));
		assertTrue(targets.contains(board.getCellAt(23, 1)));
		
		board.calcTargets(0, 20, 4);
		targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(1, 19)));
		assertTrue(targets.contains(board.getCellAt(2, 20)));
		assertTrue(targets.contains(board.getCellAt(3, 19)));
		assertTrue(targets.contains(board.getCellAt(4, 20)));
		
		// Includes a path that doesn't have enough length plus one door
		board.calcTargets(9, 0, 4);
		targets= board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCellAt(9, 4)));
		assertTrue(targets.contains(board.getCellAt(9, 2)));
		assertTrue(targets.contains(board.getCellAt(10, 3)));
		assertTrue(targets.contains(board.getCellAt(10, 1)));
		assertTrue(targets.contains(board.getCellAt(11, 2)));
		assertTrue(targets.contains(board.getCellAt(12, 1)));	
		assertTrue(targets.contains(board.getCellAt(8, 2)));
		assertTrue(targets.contains(board.getCellAt(11, 0)));	
	}	
	
	// Tests of just walkways plus one door, 6 steps
	// These are LIGHT BLUE on the planning spreadsheet

	@Test
	public void testTargetsSixSteps() {
		board.calcTargets(24, 17, 6);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(8, targets.size());
		assertTrue(targets.contains(board.getCellAt(18, 17)));
		assertTrue(targets.contains(board.getCellAt(20, 17)));	
		assertTrue(targets.contains(board.getCellAt(22, 17)));	
		assertTrue(targets.contains(board.getCellAt(19, 18)));	
		assertTrue(targets.contains(board.getCellAt(21, 18)));	
		assertTrue(targets.contains(board.getCellAt(23, 18)));	
		assertTrue(targets.contains(board.getCellAt(22, 19)));
		assertTrue(targets.contains(board.getCellAt(22, 17)));
		
	}	
	
	// Test getting into a room
	// These are LIGHT BLUE on the planning spreadsheet

	@Test 
	public void testTargetsIntoRoom()
	{
		// One room is exactly 2 away
		board.calcTargets(19, 8, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(5, targets.size());
		// directly left (can't go right 2 steps)
		assertTrue(targets.contains(board.getCellAt(19, 6)));
		// directly up and down
		assertTrue(targets.contains(board.getCellAt(17, 8)));
		assertTrue(targets.contains(board.getCellAt(21, 8)));
		// one up/down, one left/right
		assertTrue(targets.contains(board.getCellAt(18, 7)));
		assertTrue(targets.contains(board.getCellAt(20, 7)));
	}
	
	// Test getting into room, doesn't require all steps
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testTargetsIntoRoomShortcut() 
	{
		board.calcTargets(11, 24, 3);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(6, targets.size());
		//up and then left
		assertTrue(targets.contains(board.getCellAt(10, 22)));
		//up left down
		assertTrue(targets.contains(board.getCellAt(11, 23)));
		//left up right
		assertTrue(targets.contains(board.getCellAt(10, 24)));
		//left only
		assertTrue(targets.contains(board.getCellAt(11, 21)));
		// into the rooms
		assertTrue(targets.contains(board.getCellAt(12, 23)));
		assertTrue(targets.contains(board.getCellAt(12, 22)));			
	}

	// Test getting out of a room
	// These are LIGHT BLUE on the planning spreadsheet
	@Test
	public void testRoomExit()
	{
		// Take one step, essentially just the adj list
		board.calcTargets(8, 2, 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(9, 2)));
		// Take two steps
		board.calcTargets(8, 2, 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(10, 2)));
		assertTrue(targets.contains(board.getCellAt(9, 3)));
		assertTrue(targets.contains(board.getCellAt(9, 1)));
	}

}
