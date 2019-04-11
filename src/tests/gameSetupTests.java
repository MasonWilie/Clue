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

/**
 * GameSetupTests Class:
 * Tests for loading people, loading deck, and dealing functionalities
 * 
 * @author Hunter Rich
 * @author Mason Wilie
 * 
 */

public class gameSetupTests {

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

	@Test
	//Tests that loading in the people from players.txt is successful
	public void testPeople() {
		ArrayList<Player> people = board.getPeople();

		//Tests that the right amount of people have been loaded in
		assertEquals(5, people.size());

		//Tests each person's name, color, whether they are a human or a computer player, and starting location
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
	//Tests to make sure loading in the cards is done correctly
	public void testLoadDeckCards() {
		ArrayList<Card> cards = board.getOriginalDeck();

		//Checks that the number of cards in the deck is correct
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

		//Checks that the right number of people, weapons, and rooms exist in the deck of cards
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

		//Check to make sure a room, weapon and person are contained in the deck
		assertTrue(personFound);
		assertTrue(weaponFound);
		assertTrue(roomFound);

	}


	@Test
	//Checks that the cards were dealt to the players correctly
	public void testDealingCards() {
		ArrayList<Card> deck = board.getDeck();
		ArrayList<Player> people = board.getPeople();
		//Checks that all the cards have been dealt from the deck
		assertEquals(0, deck.size());

		boolean closeNumCards = true;

		for (int i = 0; i < people.size()-1; i++) {
			if (Math.abs(people.get(i).getHandSize() - people.get(i+1).getHandSize()) < 2) {
			} else {
				closeNumCards = false;
				break;
			}
		}
		//Checks to make sure that all players have roughly the same number of cards
		assertTrue(closeNumCards);

		String testCard1 = "Fran";
		String testCard2 = "WetNoodle";
		String testCard3 = "Library";
		int numTestCards = 3;
		int occurrenceOfCard = 0;

		for (int a = 0; a < numTestCards; a++) {
			for (int i = 0; i < people.size(); i++) {
				for (int j = 0; j < people.get(i).getHandSize(); j++) {
					if (people.get(i).getHand().get(j).getName().equals(testCard1) && a == 0) {
						occurrenceOfCard++;
					} else if (people.get(i).getHand().get(j).getName().equals(testCard2) && a == 1) {
						occurrenceOfCard++;
					} else if (people.get(i).getHand().get(j).getName().equals(testCard3) && a == 2) {
						occurrenceOfCard++;
					}
				}
			}
			//Checks to make sure that a given card does not exist in multiple players' hands
			assertTrue(occurrenceOfCard <= 1);
			occurrenceOfCard = 0;
		}
	}

}
