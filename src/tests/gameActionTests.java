package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.ComputerPlayer;
import clueGame.ControlGUI;
import clueGame.HumanPlayer;
import clueGame.Player;
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
		board.setConfigFiles("Board_Layout.csv", "ClueRooms.txt", "players_test.txt", "cards.txt");		
		board.initialize();
		
		
	}

	// Tests selecting a space to move to based on the targets
	@Test
	public void testTargetSelection() {
		ComputerPlayer cPlayer = new ComputerPlayer();
		cPlayer.setRow(5);
		cPlayer.setColumn(6);
		int currentRow = cPlayer.getRow();
		int currentCol = cPlayer.getColumn();
		board.calcTargets(currentRow, currentCol, 3);
		Set<BoardCell> theTargets = board.getTargets();

		//run through choosing many different targets and making sure they are in the list of targets, checks randomness
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

		// Took doorway checking test out because we changed the way a computer selects targets.
		// They now do not go back to the same door until they have been to another place.
		
	}

	//test if a given set of 3 cards is the solution (which are 3 cards)
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

	}

	// Tests to make sure we suggest the right cards based on cards that have been seen. Also makes sure that the room suggestion matches the current location
	@Test
	public void createSuggestionTest() {

		Card ballroomCard = new Card("Closet", CardType.ROOM);

		//check if current position matches suggested room
		Solution theSuggestion = ((ComputerPlayer)board.getPeople().get(2)).makeSuggestion(ballroomCard);
		Card suggestedRoom = theSuggestion.room;
		int compRow = board.getPeople().get(2).getRow();
		int compCol = board.getPeople().get(2).getColumn();
		assertEquals(suggestedRoom.getName(), board.getLegend().get(board.getCellAt(compRow, compCol).getInitial()));


		//If only one weapon not seen, it's selected
		ArrayList<Card> crazyWeaponHand = new ArrayList<>();
		for (Card i : board.getOriginalDeck()) {
			if (i.getType().equals(CardType.WEAPON)) {
				crazyWeaponHand.add(i);
			}
		}
		Card weaponToPick = crazyWeaponHand.get(crazyWeaponHand.size()-1);
		crazyWeaponHand.remove(crazyWeaponHand.size()-1);
		board.getPeople().get(2).setHand(crazyWeaponHand);
		Solution theSuggestion2 = ((ComputerPlayer)board.getPeople().get(2)).makeSuggestion(ballroomCard);
		assertEquals(theSuggestion2.weapon, weaponToPick);


		//if only one person not seen, it's selected (can be same test as weapon)
		ArrayList<Card> crazyPersonHand = new ArrayList<>();
		for (Card i : board.getOriginalDeck()) {
			if (i.getType().equals(CardType.PERSON)) {
				crazyPersonHand.add(i);
			}
		}
		Card personToPick = crazyPersonHand.get(crazyPersonHand.size()-1);
		crazyPersonHand.remove(crazyPersonHand.size()-1);
		board.getPeople().get(2).setHand(crazyPersonHand);
		Solution theSuggestion3 = ((ComputerPlayer)board.getPeople().get(2)).makeSuggestion(ballroomCard);
		assertEquals(theSuggestion3.person, personToPick);


		//if multiple weapons not seen, one of them is randomly selected
		ArrayList<Card> crazyWeaponHand1 = new ArrayList<>();
		for (Card i : board.getOriginalDeck()) {
			if (i.getType().equals(CardType.WEAPON)) {
				crazyWeaponHand1.add(i);
			}
		}
		Card weaponToPick1 = crazyWeaponHand1.get(crazyWeaponHand1.size()-1);
		crazyWeaponHand1.remove(crazyWeaponHand1.size()-1);
		Card weaponToPick12 = crazyWeaponHand1.get(crazyWeaponHand1.size()-1);
		crazyWeaponHand1.remove(crazyWeaponHand1.size()-1);
		board.getPeople().get(2).setHand(crazyWeaponHand1);
		Solution theSuggestion4 = ((ComputerPlayer)board.getPeople().get(2)).makeSuggestion(ballroomCard);
		assertTrue(theSuggestion4.weapon.equals(weaponToPick1) || theSuggestion4.weapon.equals(weaponToPick12));


		//if multiple persons not seen, one of them is randomly selected
		ArrayList<Card> crazyPersonHand1 = new ArrayList<>();
		for (Card i : board.getOriginalDeck()) {
			if (i.getType().equals(CardType.PERSON)) {
				crazyPersonHand1.add(i);
			}
		}
		Card personToPick1 = crazyPersonHand1.get(crazyPersonHand1.size()-1);
		crazyPersonHand1.remove(crazyPersonHand1.size()-1);
		Card personToPick12 = crazyPersonHand1.get(crazyPersonHand1.size()-1);
		crazyPersonHand1.remove(crazyPersonHand1.size()-1);
		board.getPeople().get(2).setHand(crazyPersonHand1);
		Solution theSuggestion5 = ((ComputerPlayer)board.getPeople().get(2)).makeSuggestion(ballroomCard);
		assertTrue(theSuggestion5.person.equals(personToPick1) || theSuggestion5.person.equals(personToPick12));
	}

	//Tests returning null or a matching card depending on if a player can successfully disprove a suggestion
	@Test
	public void disproveSuggestionTest() {

		//If player has only one matching card it should be returned
		Solution theSolution = board.getSolution();

		Card testCard = new Card("right_person", CardType.PERSON);
		ArrayList<Card> theirHand = new ArrayList<Card>();
		Card aBadCard1 = new Card("Sillyweapon", CardType.WEAPON);
		Card aBadCard2 = new Card("Sillyroom", CardType.ROOM);
		theirHand.add(aBadCard1);
		theirHand.add(aBadCard2);
		theirHand.add(testCard);
		board.getPeople().get(2).setHand(theirHand);
		Solution theSuggestion = new Solution(testCard, new Card("Wrong", CardType.ROOM), new Card("Wronger", CardType.WEAPON));
		Card returnedCard = ((ComputerPlayer)board.getPeople().get(2)).disproveSuggestion(theSuggestion);
		assertEquals(returnedCard, testCard);


		//If players has >1 matching card, returned card should be chosen randomly
		ArrayList<Card> theirHand1 = new ArrayList<Card>();
		Card testRoom = new Card("Sillyroom", CardType.ROOM);
		Card testPerson = new Card("John", CardType.PERSON);
		Card testWeapon = new Card("Sword", CardType.WEAPON);
		theirHand1.add(testRoom);
		theirHand1.add(testPerson);
		theirHand1.add(testWeapon);
		board.getPeople().get(2).setHand(theirHand1);
		Card wrongRoom = new Card("Wrong!", CardType.ROOM);
		theSuggestion = new Solution(testPerson, wrongRoom, testWeapon);
		Card returnedCard1 = ((ComputerPlayer)board.getPeople().get(2)).disproveSuggestion(theSuggestion);
		assertTrue(returnedCard1.equals(testPerson) || returnedCard1.equals(testWeapon));


		//If player has no matching cards, null is returned
		ArrayList<Card> theirHand2 = new ArrayList<Card>();
		Card aBadCard31 = new Card("Sillyperson", CardType.PERSON);
		Card aBadCard32 = new Card("Sillyweapon", CardType.WEAPON);
		Card aBadCard33 = new Card("Sillyroom", CardType.ROOM);
		theirHand2.add(aBadCard31);
		theirHand2.add(aBadCard32);
		theirHand2.add(aBadCard33);
		board.getPeople().get(2).setHand(theirHand2);
		Card returnedCard9 = ((ComputerPlayer)board.getPeople().get(2)).disproveSuggestion(theSolution);
		assertTrue(returnedCard9 == null);
	}

	//Handles different scenarios of suggestions and accusations based on who can disprove what. Returns null or the answer.
	@Test
	public void handleSuggestionTest() {
		ArrayList<Player> oldPeople = board.getPeople();
		ArrayList<Player> people = new ArrayList<>();

		Solution gameSolution = board.getSolution();

		Player person1 = new HumanPlayer();
		Player person2 = new ComputerPlayer();
		Player person3 = new HumanPlayer();
		Player person4 = new ComputerPlayer();

		people.add(person1);
		people.add(person2);
		people.add(person3);
		people.add(person4);

		board.setPeople(people);


		Card rCard1 = new Card("Room 1", CardType.ROOM);
		Card pCard1 = new Card("Person 1", CardType.PERSON);
		Card wCard1 = new Card("Weapon 1", CardType.WEAPON);

		// Human accuser, No one can disprove
		Card handleResult = board.handleSuggestion(gameSolution, person1);
		assertEquals(null, handleResult);

		// Computer accusing, accuser can disprove
		ArrayList<Card> handPerson2 = new ArrayList<>();
		handPerson2.add(rCard1);
		person2.setHand(handPerson2);
		Solution suggestion = new Solution(pCard1, rCard1, wCard1);
		handleResult = board.handleSuggestion(suggestion, person2);
		assertEquals(null, handleResult);

		// Computer accusing, human can disprove
		ArrayList<Card> handPerson1 = new ArrayList<>();
		handPerson1.add(pCard1);
		person1.setHand(handPerson1);
		handleResult = board.handleSuggestion(suggestion, person2);
		assertEquals(pCard1, handleResult);

		// Suggestion only human can disprove, but human is accuser, returns null
		handPerson1 = new ArrayList<>();
		person1.setHand(handPerson1);
		handleResult = board.handleSuggestion(suggestion,  person2);
		assertEquals(null, handleResult);

		// Suggestion that two players can disprove, correct player (based on starting with next player in list) returns answer
		ArrayList<Card> handPerson3 = new ArrayList<>();
		handPerson3.add(wCard1);
		person3.setHand(handPerson3);
		handleResult = board.handleSuggestion(suggestion, person4);

		// Suggestion that human and another player can disprove, other player is next in list, ensure other player returns answer
		handPerson1.add(wCard1);
		person1.setHand(handPerson1);
		handleResult = board.handleSuggestion(suggestion, person4);
		assertEquals(wCard1, handleResult);


		board.setPeople(oldPeople);


	}


}
