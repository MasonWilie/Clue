package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Card;
import clueGame.Board;
import clueGame.CardType;
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
		board.setConfigFiles("Board_Layout.csv", "ClueRooms.txt", "players.txt", "cards.txt");		
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
		System.out.println(person.getColor());
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
	
	@Test
	public void testLoadDeckCards() {
		ArrayList<Card> cards = board.getDeck();
		
		assertEquals(23, cards.size());
		
		int numPeople = 0;
		int numWeapons = 0;
		int numRooms = 0;
		
		for (int i = 0; i < cards.size(); i++) {
			if (cards.get(i).getType() == CardType.PERSON) {
				numPeople++;
			} else if (cards.get(i).getType() == CardType.WEAPON) {
				numWeapons++;
			} else if (cards.get(i).getType() == CardType.ROOM) {
				numRooms++;
			} else {
				System.out.println("This card isn't a PERSON, WEAPON or ROOM! Fix this.");
			}
		}
		
		assertEquals(9, numPeople);
		assertEquals(6, numWeapons);
		assertEquals(8, numRooms);
		
		String chosenRoom = "Library";
		String chosenWeapon = "Bazooka";
		String chosenPerson = "Fran";
		
		boolean roomFound=false;
		boolean weaponFound=false;
		boolean personFound=false;
		
		for(Card card : cards) {
			if (chosenRoom.equals(card.getName())) {
				roomFound=true;
			}else if (chosenWeapon.equals(card.getName())) {
				weaponFound=true;
			}else if (chosenPerson.equals(card.getName())) {
				personFound=true;
			}
			
			
		}
		
		assertTrue(personFound);
		assertTrue(weaponFound);
		assertTrue(roomFound);

	}
	
	@Test
	public void testDealingCards() {
		assertEquals(0, cards.size());
	}
}
