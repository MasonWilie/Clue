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
	
	@Test
	public void createSuggestionTest() {
		
		Card ballroomCard = new Card("Ballroom", CardType.ROOM);
		
		
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
	
	@Test
	public void handleSuggestionTest() {
		//Suggestion no one can disprove returns null
		Solution solution = board.getSolution();
		Solution suggestion = board.getSolution();
		assertTrue(null.equals(handleSuggestion(suggestion)));
		
		//have a suggestion that has 2 correct cards and 1 card in accuser hand returns null
		ArrayList<Card> hand = new ArrayList<Card>();
		hand.add(solution.person);
		hand.add(solution.weapon);
		Card wrong = new Card("bad", CardType.ROOM);
		hand.add(wrong);
		board.getPeople().get(2).setHand(hand);
		Solution suggestion1 = ((ComputerPlayer)board.getPeople().get(2)).makeSuggestion(wrong);
		assertTrue(null.equals(handleSuggestion(suggestion1)));
		
		//have a suggestion that has 2 correct cards and 1 card in a human hand and returns that card they have
		ArrayList<Card> hand1 = new ArrayList<Card>();
		hand1.add(solution.person);
		hand1.add(solution.weapon);
		Card disprovingCard = new Card("bad", CardType.ROOM);
		hand1.add(disprovingCard);
		board.getPeople().get(1).setHand(hand1);
		Solution suggestion2 = ((ComputerPlayer)board.getPeople().get(2)).makeSuggestion(disprovingCard);
		assertTrue(disprovingCard.equals(handleSuggestion(suggestion2)));
		
		//have a suggestion that has 2 correct cards and 1 card in human hand who is also accuser returns null
		ArrayList<Card> hand2 = new ArrayList<Card>();
		hand2.add(solution.person);
		hand2.add(solution.weapon);
		Card disprovingCard1 = new Card("bad", CardType.ROOM);
		hand2.add(disprovingCard1);
		board.getPeople().get(1).setHand(hand2);
		Solution suggestion3 = ((ComputerPlayer)board.getPeople().get(1)).makeSuggestion(disprovingCard1);
		assertTrue(null.equals(handleSuggestion(suggestion3)));
		
		//suggestion with 1 correct card and 2 cards in 2 players' hands ...something
		ArrayList<Card> someonesHand1 = new ArrayList<Card>();
		ArrayList<Card> someonesHand2 = new ArrayList<Card>();
		Card aWrongPerson = new Card("badperson", CardType.PERSON);
		Card aWrongWeapon = new Card("badweapon", CardType.WEAPON);
		someonesHand1.add(aWrongPerson);
		someonesHand2.add(aWrongWeapon);
		ArrayList<Card> whatSomeoneWillSuggest = new ArrayList<Card>();
		whatSomeoneWillSuggest.add(aWrongPerson);
		whatSomeoneWillSuggest.add(aWrongWeapon);
		whatSomeoneWillSuggest.add(solution.room);
		board.getPeople().get(0).setHand(someonesHand1);
		board.getPeople().get(1).setHand(someonesHand2);
		board.getPeople().get(2).setHand(whatSomeoneWillSuggest);
		Solution aSuggestion = ((ComputerPlayer)board.getPeople().get(2)).makeSuggestion(solution.room);
		assertTrue(aWrongPerson.equals(handleSuggestion(aSuggestion)));
		
		//something
		ArrayList<Card> someonesHand11 = new ArrayList<Card>();
		ArrayList<Card> someonesHand21 = new ArrayList<Card>();
		Card aWrongPerson1 = new Card("badperson", CardType.PERSON);
		Card aWrongWeapon1 = new Card("badweapon", CardType.WEAPON);
		someonesHand11.add(aWrongPerson1);
		someonesHand21.add(aWrongWeapon1);
		ArrayList<Card> whatSomeoneWillSuggest1 = new ArrayList<Card>();
		whatSomeoneWillSuggest1.add(aWrongPerson1);
		whatSomeoneWillSuggest1.add(aWrongWeapon1);
		whatSomeoneWillSuggest1.add(solution.room);
		board.getPeople().get(3).setHand(someonesHand11);
		board.getPeople().get(4).setHand(someonesHand21);
		board.getPeople().get(2).setHand(whatSomeoneWillSuggest1);
		Solution aSuggestion1 = ((ComputerPlayer)board.getPeople().get(2)).makeSuggestion(solution.room);
		assertTrue(aWrongPerson1.equals(handleSuggestion(aSuggestion1)));
	}
	
	
}
