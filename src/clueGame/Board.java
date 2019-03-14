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
import java.util.Scanner;

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

public class Board {
	private int numRows, numColumns;
	
	public static final int MAX_BOARD_SIZE = 50;
		
	private BoardCell[][] board;
	
	private HashMap<Character, String> legend; 
	private HashMap<BoardCell, HashSet<BoardCell>> adjMatrix;
	
	private HashSet<BoardCell> targets;
	
	private String boardConfigFile;
	private String roomConfigFile;
	
	private static Board theInstance = new Board();
	// constructor is private to ensure only one can be created
	private Board() {
		legend = new HashMap<Character, String>();
		adjMatrix = new HashMap<BoardCell, HashSet<BoardCell>>();
	}
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}

	public void setConfigFiles(String newBoardConfig, String newRoomConfig) {
		boardConfigFile = "data\\" + newBoardConfig;
		roomConfigFile = "data\\" + newRoomConfig;
	}
	
	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}
	
	public BoardCell getCellAt(int row, int col) {
		return board[row][col]; // fix this
	}
	
	public void initialize() throws BadConfigFormatException{
		
		
		legend = new HashMap<Character, String>(); // Resets the legend
		adjMatrix = new HashMap<BoardCell, HashSet<BoardCell>>(); // Resets the adjacencies
		
		try {
			loadRoomConfig();
			loadBoardConfig();
			calcAdjacencies();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// Loads the initial and description of rooms from the room config file
	public void loadRoomConfig() throws FileNotFoundException, BadConfigFormatException {
		File roomsFile = new File(roomConfigFile);
		Scanner in = new Scanner(new FileReader(roomsFile));
		
		String currentLine;
		int lineCounter = 0;
		while (in.hasNextLine()) {
			currentLine = in.nextLine();
			Character initial;
			String description, roomType;
			
			List<String> stringElements = Arrays.asList(currentLine.split(","));
			
			if (stringElements.size() != 3) {
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
				throw new BadConfigFormatException("Room type on line " + lineCounter + " not equal to Card or Other. Value recieved: " + roomType);
			}
			
			legend.put(initial, description);
			lineCounter++;
		}
		
		
	}
	
	// Loads the configuration of the baord from the board csv file
	public void loadBoardConfig() throws FileNotFoundException, BadConfigFormatException {
		File boardFile = new File(boardConfigFile);
		Scanner in = new Scanner(new FileReader(boardFile));
		
		String nextLine;
		numRows = 0;
		ArrayList<String[]> grid = new ArrayList<String[]>();
		while(in.hasNextLine()) {
			nextLine = in.nextLine();
			String[] currentRow = nextLine.split(",");
			if (currentRow.length != 0) {
				grid.add(currentRow);
			}
			
		}
		
		numRows = grid.size();
		numColumns = grid.get(0).length;
		
		board = new BoardCell[numRows][numColumns];
		
		for (int i = 0; i < grid.size(); i++) {
			if (grid.get(i).length != numColumns) {
				throw new BadConfigFormatException("Row " + (i + 1) + " has a different number of columns");
			}
			for (int j = 0; j < grid.get(i).length; j++) {
				BoardCell cell = new BoardCell(i, j);
				if (grid.get(i)[j].length() == 2) {
					char direction = grid.get(i)[j].charAt(1);

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
				
				char initial = grid.get(i)[j].charAt(0);
				
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
		HashSet<BoardCell> adjacenciesSet;
		
		// Calculates the adjacencies and adds them to a map with the cell as the key, takes into account boundary conditions
		for (int i = 0; i < numColumns - 1; i++) {
			for (int j = 0; j < numRows - 1; j++) {
				adjacenciesSet = new HashSet<BoardCell>();
				if (i != 0) { // Left of board
					adjacenciesSet.add(board[i-1][j]);
				}
				if (j != numColumns - 1) { // Right of board
					adjacenciesSet.add(board[i][j + 1]);
				}
				if (j != 0) { // Top of board
					adjacenciesSet.add(board[i][j-1]);
				}
				if (i != numRows - 1) { // Bottom of board
					adjacenciesSet.add(board[i+1][j]);
				}
				adjMatrix.put(board[i][j], adjacenciesSet);
			}
		}
	}
	
	public HashSet<BoardCell> getAdjList(int x, int y) {
		return new HashSet<BoardCell>();
	}
	
	// Calculates the reachable cells starting at startCell and having pathLength tiles to move
	public void calcTargets(int x, int y, int pathLength) {
		targets = new HashSet<BoardCell>();
		
//		targets.add(startCell);
//		
//		for (int i = 0; i < pathLength; i++) {
//			HashSet<BoardCell> tempTargets = new HashSet<BoardCell>();
//			tempTargets.addAll(targets);
//			for (BoardCell cell: targets) {
//				tempTargets.addAll(adjMatrix.get(cell));
//			}
//			targets.addAll(tempTargets);
//		}
//		targets.remove(startCell);
		
	}
	
	public HashSet<BoardCell> getTargets(){
		return targets;
	}
	
	public HashMap<Character, String> getLegend(){
		return legend;
	}
}
