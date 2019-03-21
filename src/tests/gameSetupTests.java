package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import com.sun.prism.paint.Color;

import clueGame.Board;
import clueGame.ComputerPlayer;
import clueGame.HumanPlayer;
import clueGame.Player;

public class gameSetupTests {
	
	private static Board board;
	@BeforeClass
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("CTest_ClueLayout.csv", "CTest_ClueLegend.txt", "players.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
	}
	
	@Test
	public void testPeople() {
		ArrayList<Player> people = board.getPeople();
		
		assertEquals(5, people.size());
		
		Player person = people.get(0);
		assertEquals("John", person.getPlayerName());
		assertTrue(person instanceof HumanPlayer);
		assertEquals(1, person.getRow());
		assertEquals(10, person.getColumn());
		assertEquals(Color.RED, person.getColor());
		
		person = people.get(2);
		assertEquals("Robo23", person.getPlayerName());
		assertTrue(person instanceof ComputerPlayer);
		assertEquals(9, person.getRow());
		assertEquals(7, person.getColumn());
		assertEquals(Color.WHITE, person.getColor());
		
		person = people.get(4);
		assertEquals("Susan", person.getPlayerName());
		assertTrue(person instanceof HumanPlayer);
		assertEquals(4, person.getRow());
		assertEquals(2, person.getColumn());
		assertEquals(Color.BLACK, person.getColor());
		
		
	}
	
	
	
}
