package clueGame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.lang.reflect.Field;


import clueGame.BoardCell;

/**
 * Board class:
 * Initializes the game board for the clue game by populating it with cells, calculating
 * the adjacencies for each cell, and allowing the user to receive the possible targets
 * for a certain move.
 * 
 * @author Hunter Rich
 * @author Mason Wilie
 * 
 */

public class Board extends JPanel{
	private int numRows;
	private int numColumns;

	public static final int MAX_BOARD_SIZE = 50;

	private BoardCell[][] board;

	private Map<Character, String> legend; 
	private Map<BoardCell, Set<BoardCell>> adjMatrix;

	private Set<BoardCell> targets;

	private String boardConfigFile;
	private String roomConfigFile;
	private String peopleConfigFile;
	private String cardConfigFile;

	private Solution solution;
	private static final int BOARD_RES_X = 400;
	private static final int BOARD_RES_Y = 400;


	private ArrayList<Player> people;
	private ArrayList<Card> deck;
	private ArrayList<Card> originalDeck;

	private static Board theInstance = new Board();
	// constructor is private to ensure only one can be created
	private Board() {
		legend = new HashMap<>();
		adjMatrix = new HashMap<>();
	}

	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}

	public Color convertColor(String strColor) {
		Color color; 
		try {     
			// We can use reflection to convert the string to a color
			Field field = Class.forName("java.awt.Color").getField(strColor);
			color = (Color)field.get(null); 
		} catch (Exception e) {  
			color = null; // Not defined
		}
		return color;
	}


	public void setConfigFiles(String newBoardConfig, String newRoomConfig, String newPeopleConfig, String newCardConfig) {
		boardConfigFile = "data/" + newBoardConfig;
		roomConfigFile = "data/" + newRoomConfig;
		peopleConfigFile = "data/" + newPeopleConfig;
		cardConfigFile = "data/" + newCardConfig;
	}

	public void initialize() throws BadConfigFormatException {


		legend = new HashMap<>(); // Resets the legend
		adjMatrix = new HashMap<>(); // Resets the adjacencies

		try {
			loadRoomConfig();
			loadBoardConfig();
			loadPeopleConfig();
			loadDeckConfig();
			pickWinningCards();
			dealCards();
			calcAdjacencies();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Loads the initial and description of rooms from the room config file
	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException {
		File roomsFile = new File(roomConfigFile);
		Scanner in = new Scanner(new FileReader(roomsFile));


		int lineCounter = 0;
		while (in.hasNextLine()) {
			Character initial;
			String description;
			String roomType;

			List<String> stringElements = Arrays.asList(in.nextLine().split(","));

			if (stringElements.size() != 3) {
				in.close();
				throw new BadConfigFormatException("Too many elements in line " + lineCounter
						+ ". Format should follow Initial, Description, Card");
			}

			initial = stringElements.get(0).charAt(0);
			description = stringElements.get(1);
			roomType = stringElements.get(2);

			while(description.charAt(0) == ' ') {
				description = description.substring(1, description.length());
			}

			while(roomType.charAt(0) == ' ') {
				roomType = roomType.substring(1, roomType.length());
			}

			if (!roomType.equals("Card") && !roomType.equals("Other")) {
				in.close();
				throw new BadConfigFormatException("Room type on line " + lineCounter + " not equal to Card or Other. Value recieved: " + roomType);
			}

			legend.put(initial, description);
			lineCounter++;
		}
		in.close();

	}

	// Loads the configuration of the board from the board csv file
	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException {
		File boardFile = new File(boardConfigFile);
		Scanner in = new Scanner(new FileReader(boardFile));

		String nextLine;
		numRows = 0;
		ArrayList<String[]> grid = new ArrayList<>();
		while(in.hasNextLine()) {
			nextLine = in.nextLine();
			String[] currentRow = nextLine.split(",");
			if (currentRow.length != 0) {
				grid.add(currentRow);
			}

		}
		in.close();

		numRows = grid.size();
		numColumns = grid.get(0).length;

		board = new BoardCell[numRows][numColumns];

		for (int i = 0; i < numRows; i++) {
			if (grid.get(i).length != numColumns) {
				throw new BadConfigFormatException("Row " + (i + 1) + " has a different number of columns");
			}
			for (int j = 0; j < numColumns; j++) {
				BoardCell cell = new BoardCell(i, j);
				String tile = grid.get(i)[j];
				if (tile.length() == 2) {
					char direction = tile.charAt(1);

					switch(direction) {
					case 'U':
						cell.setDoorDirection(DoorDirection.UP);
						break;
					case 'D':
						cell.setDoorDirection(DoorDirection.DOWN);
						break;
					case 'L':
						cell.setDoorDirection(DoorDirection.LEFT);
						break;
					case 'R':
						cell.setDoorDirection(DoorDirection.RIGHT);
						break;
					default:
						cell.setDoorDirection(DoorDirection.NONE);
						break;
					}
				}else {
					cell.setDoorDirection(DoorDirection.NONE);
				}

				char initial = tile.charAt(0);

				if (legend.containsKey(initial)) {
					cell.setInitial(initial);
				}else {
					throw new BadConfigFormatException("Initial " + initial + ", at location (" + i + ", " + j + ")  not contained in legend");
				}




				board[i][j] = cell;
			}
		}

	}

	// Calculates the adjacent cells next to each cell and stores it into a map
	private void calcAdjacencies() {
		adjMatrix = new HashMap<>();

		for (BoardCell[] row : board) {
			for (BoardCell cell : row) {
				Set<BoardCell> adjacenciesSet = new HashSet<>();
				if (cell.isWalkway()) {
					adjacenciesSet = calcAdjWalkway(cell);
				}else if (cell.isDoorway()) {
					adjacenciesSet = calcAdjDoorway(cell);
				}
				adjMatrix.put(cell, adjacenciesSet);
			}
		}
	}

	private Set<BoardCell> calcAdjWalkway(BoardCell cell){
		Set<BoardCell> adjSet = new HashSet<>();

		BoardCell adjacentCell;
		if (cell.getRow() != (numRows - 1)) {// If the cell is not at the very bottom
			adjacentCell = getCellAt(cell.getRow() + 1, cell.getColumn());
			if (adjacentCell.isWalkway() || (adjacentCell.isDoorway() && adjacentCell.getDoorDirection() == DoorDirection.UP)) {
				adjSet.add(adjacentCell);
			}
		}if(cell.getRow() != 0) {
			adjacentCell = getCellAt(cell.getRow() - 1, cell.getColumn());
			if (adjacentCell.isWalkway() || (adjacentCell.isDoorway() && adjacentCell.getDoorDirection() == DoorDirection.DOWN)) {
				adjSet.add(adjacentCell);
			}
		}if (cell.getColumn() != (numColumns - 1)) {
			adjacentCell = getCellAt(cell.getRow(), cell.getColumn() + 1);
			if (adjacentCell.isWalkway() || (adjacentCell.isDoorway() && adjacentCell.getDoorDirection() == DoorDirection.LEFT)) {
				adjSet.add(adjacentCell);
			}
		}if (cell.getColumn() != 0) {
			adjacentCell = getCellAt(cell.getRow(), cell.getColumn() - 1);
			if (adjacentCell.isWalkway() || (adjacentCell.isDoorway() && adjacentCell.getDoorDirection() == DoorDirection.RIGHT)) {
				adjSet.add(adjacentCell);
			}
		}
		return adjSet;
	}


	private Set<BoardCell> calcAdjDoorway(BoardCell cell){
		Set<BoardCell> adjSet = new HashSet<>();
		switch(cell.getDoorDirection()) {	
		case UP:
			adjSet.add(getCellAt(cell.getRow() - 1, cell.getColumn()));
			break;
		case DOWN:
			adjSet.add(getCellAt(cell.getRow() + 1, cell.getColumn()));
			break;
		case LEFT:
			adjSet.add(getCellAt(cell.getRow(), cell.getColumn() - 1));
			break;
		case RIGHT:
			adjSet.add(getCellAt(cell.getRow(), cell.getColumn() + 1));
			break;
		default:
			break;
		}
		return adjSet;
	}

	// Calculates the reachable cells starting at startCell and having pathLength tiles to move
	public void calcTargets(int row, int col, int pathLength) {
		targets = new HashSet<>();
		BoardCell startCell = getCellAt(row, col);


		Set<BoardCell> adjacentCells = adjMatrix.get(startCell);
		for (BoardCell cell : adjacentCells) {
			if (cell != startCell) {
				cellTargets(cell, startCell, pathLength - 1, startCell);
			}

		}

		if (targets.contains(startCell)) {
			targets.remove(startCell);
		}


	}

	private void cellTargets(BoardCell newCell, BoardCell oldCell, int steps, BoardCell startCell){

		if (steps == 0 || newCell.isDoorway()) {
			targets.add(newCell);
		}else {
			Set<BoardCell> adjacentCells = adjMatrix.get(newCell);
			for (BoardCell cell : adjacentCells) {
				if (cell != oldCell && cell != newCell && cell != startCell) {
					cellTargets(cell, newCell, steps - 1, startCell);
				}

			}
		}

	}

	public Set<BoardCell> getTargets(){
		return targets;
	}

	public Map<Character, String> getLegend(){
		return legend;
	}

	public Set<BoardCell> getAdjList(int row, int col) {
		return adjMatrix.get(getCellAt(row, col));
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public BoardCell getCellAt(int row, int col) {
		return board[row][col];
	}


	// Loads game players into the game from the players text file
	public void loadPeopleConfig() throws FileNotFoundException, BadConfigFormatException{


		File playerFile = new File(peopleConfigFile);
		Scanner in = new Scanner(new FileReader(playerFile));

		String nextLine;
		int thisRow = 0;
		people = new ArrayList<>();
		while(in.hasNextLine()) {
			nextLine = in.nextLine();
			String[] currentRow = nextLine.split(" "); // Divides each line into its components
			if (currentRow[1].equals("h")) { // If it is a human player, use the HumanPlayer class
				people.add(new HumanPlayer());
			} else if (currentRow[1].equals("c")) { //If it is not a human palayer, it is a computer
				people.add(new ComputerPlayer());
			} else {
				people.add(new Player());
			}
			people.get(thisRow).setPlayerName(currentRow[0]); // Setting name
			people.get(thisRow).setRow(Integer.parseInt(currentRow[2])); // Setting row
			people.get(thisRow).setColumn(Integer.parseInt(currentRow[3])); // Setting column
			people.get(thisRow).setColor(convertColor(currentRow[4])); // Setting color
			thisRow++;
		}
		in.close(); // Closing file that we were reading from
	}

	// Returns all the poeple in the game
	public ArrayList<Player> getPeople(){
		return people;
	}

	// Loads the cards from the card file.
	public void loadDeckConfig() throws FileNotFoundException, BadConfigFormatException{
		originalDeck = new ArrayList<Card>();

		File playerFile = new File(cardConfigFile);
		Scanner in = new Scanner(new FileReader(playerFile));

		// Getting the first line, which represents the people cards, and making cards out of them
		if (in.hasNextLine()) {
			String[] names = in.nextLine().split(" ");
			for (String name : names) {
				originalDeck.add(new Card(name, CardType.PERSON));
			}
		}else { // If there was no first line, bad format
			in.close();
			throw new BadConfigFormatException("Missing lines in card config file");
		}

		if (in.hasNextLine()) { // Getting the second line, which represents the weapons
			String[] weapons = in.nextLine().split(" ");
			for(String weapon:weapons) {
				originalDeck.add(new Card(weapon, CardType.WEAPON));
			}
		}else { // no second line, bad format
			in.close();
			throw new BadConfigFormatException("Missing lines in card config file");
		}

		if (in.hasNextLine()) { // if there is a third line, bad format
			in.close();
			throw new BadConfigFormatException("Too many lines in card config file");
		}

		for(char initial : legend.keySet()) { // Iterating through the legend and making room cards out of each room
			if (initial != 'X' && initial != 'W') { // If it is not a walkway or the closet
				originalDeck.add(new Card(legend.get(initial), CardType.ROOM));
			}
		}
		
		for (Player person : people) {
			person.setDeck(originalDeck);
		}

		deck = originalDeck; // Setting the deck that will be played with, while keeping the original one that was read in
		in.close();
	}

	// Picks the 3 winning cards (person, weapon, room), and removes them from the play deck
	private void pickWinningCards() {
		List<Card> personDeck = new ArrayList<>();
		List<Card> roomDeck = new ArrayList<>();
		List<Card> weaponDeck = new ArrayList<>();

		// Seperating the cards into their respective decks based on type
		for (Card card : deck) {
			if (card.getType() == CardType.PERSON) {
				personDeck.add(card);
			}else if (card.getType() == CardType.ROOM) {
				roomDeck.add(card);
			}else {
				weaponDeck.add(card);
			}
		}

		deck = new ArrayList<>();

		Random rand = new Random();

		// Picking random indexes based on the size of those decks
		int wPersonIndex = rand.nextInt(personDeck.size());
		int wRoomIndex = rand.nextInt(roomDeck.size());
		int wWeaponIndex = rand.nextInt(weaponDeck.size());

		// Picking the winning cards
		Card winningPerson = personDeck.get(wPersonIndex);
		Card winningRoom = roomDeck.get(wRoomIndex);
		Card winningWeapon = weaponDeck.get(wWeaponIndex);

		solution = new Solution(winningPerson, winningRoom, winningWeapon);

		// removing the winning cards from the deck
		personDeck.remove(wPersonIndex);
		roomDeck.remove(wRoomIndex);
		weaponDeck.remove(wWeaponIndex);

		// Placing all the cards back into the deck, without the winning cards
		deck.addAll(personDeck);
		deck.addAll(weaponDeck);
		deck.addAll(roomDeck);

		shuffleDeck(); // Shuffeling the deck


	}

	public Solution getSolution() {
		return solution;
	}

	public void setSolution(Solution solution) {
		this.solution = solution;
	}

	private void shuffleDeck() {
		Random rand = new Random();

		for (int i = 0; i < 10; i++) { // Shuffles the deck 10 times
			ArrayList<Card> tempDeck = new ArrayList<>();
			while(!deck.isEmpty()) {
				int index = rand.nextInt(deck.size()); // Pick a random index from the deck
				tempDeck.add(deck.get(index)); // Place it in the temp deck
				deck.remove(index); // Remove it from the deck
			}
			deck = tempDeck; // set the deck back to equal the temp deck
		}
	}

	// Returns the deck
	public ArrayList<Card> getDeck(){
		return deck;
	}

	// Deals the cards to the players
	public void dealCards() {
		int peopleIter = 0;
		while (deck.size() > 0) {
			people.get(peopleIter).addToHand(deck.get(deck.size()-1));
			deck.remove(deck.size()-1);
			if (peopleIter+1 > people.size()-1) {
				peopleIter = 0;
			} else {
				peopleIter++;
			}
		}
	}


	// Returns the original deck
	public ArrayList<Card> getOriginalDeck(){
		return originalDeck;
	}

	public Card handleSuggestion(Solution suggestion, Player accuser) {
		if (suggestion.equals(solution)) return null;
		
		
		// Figure out where in the list of players the accuser is in order to start after them
		int accuserIndex = -1;
		for (int i = 0; i < people.size(); i++) {
			if (people.get(i) == accuser) {
				accuserIndex = i;
			}
		}
		int currentDisproverIndex = accuserIndex + 1; // Start with the person after the accuser
		
		while(currentDisproverIndex != accuserIndex) { // Loop until you make it back the accuser
			if (currentDisproverIndex == people.size()) currentDisproverIndex = 0; // Makes the list circular
			Card returnedCard = people.get(currentDisproverIndex).disproveSuggestion(suggestion); // Get if player can disprove
			
			if (returnedCard != null) return returnedCard; // If can disprove, return card
			currentDisproverIndex++; // Go to next player
		}
	
		return null;
	}

	public void setPeople(ArrayList<Player> newPeople) {
		people = newPeople;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponents(g);
		this.setBackground(Color.GRAY);
		
	}
}
