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
import java.util.Scanner;
import java.util.Set;

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
	
	private Map<Character, String> legend; 
	private Map<BoardCell, Set<BoardCell>> adjMatrix;
	
	private Set<BoardCell> targets;
	
	private String boardConfigFile;
	private String roomConfigFile;
	
	private static Board theInstance = new Board();
	// constructor is private to ensure only one can be created
	private Board() {
		legend = new HashMap<Character, String>();
		adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
	}
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}

	public void setConfigFiles(String newBoardConfig, String newRoomConfig) {
		boardConfigFile = "data\\" + newBoardConfig;
		roomConfigFile = "data\\" + newRoomConfig;
	}
	

	
	public void initialize() throws BadConfigFormatException{
		
		
		legend = new HashMap<Character, String>(); // Resets the legend
		adjMatrix = new HashMap<BoardCell, Set<BoardCell>>(); // Resets the adjacencies
		
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
		ArrayList<String[]> grid = new ArrayList<String[]>();
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
		adjMatrix = new HashMap<BoardCell, Set<BoardCell>>();
		
		
		for (BoardCell[] row : board) {
			for (BoardCell cell : row) {
				Set<BoardCell> adjacenciesSet = new HashSet<BoardCell>();
				BoardCell tempCell;
				if (cell.isWalkway()) {
					if (cell.getRow() != (numRows - 1)) {// If the cell is not at the very bottom
						tempCell = getCellAt(cell.getRow() + 1, cell.getColumn());
						if (tempCell.isWalkway() || 
								(tempCell.isDoorway() && tempCell.getDoorDirection() == DoorDirection.UP)) {
							adjacenciesSet.add(tempCell);
						}
					}if(cell.getRow() != 0) {
						tempCell = getCellAt(cell.getRow() - 1, cell.getColumn());
						if (tempCell.isWalkway() || 
								(tempCell.isDoorway() && tempCell.getDoorDirection() == DoorDirection.DOWN)) {
							adjacenciesSet.add(tempCell);
						}
					}if (cell.getColumn() != (numColumns - 1)) {
						tempCell = getCellAt(cell.getRow(), cell.getColumn() + 1);
						if (tempCell.isWalkway() || 
								(tempCell.isDoorway() && tempCell.getDoorDirection() == DoorDirection.LEFT)) {
							adjacenciesSet.add(tempCell);
						}
					}if (cell.getColumn() != 0) {
						tempCell = getCellAt(cell.getRow(), cell.getColumn() - 1);
						if (tempCell.isWalkway() || 
								(tempCell.isDoorway() && tempCell.getDoorDirection() == DoorDirection.RIGHT)) {
							adjacenciesSet.add(tempCell);
						}
					}
					
				}else if (cell.isDoorway()) {
					
					switch(cell.getDoorDirection()) {
					case UP:
						adjacenciesSet.add(getCellAt(cell.getRow() - 1, cell.getColumn()));
						break;
					case DOWN:
						adjacenciesSet.add(getCellAt(cell.getRow() + 1, cell.getColumn()));
						break;
					case LEFT:
						adjacenciesSet.add(getCellAt(cell.getRow(), cell.getColumn() - 1));
						break;
					case RIGHT:
						adjacenciesSet.add(getCellAt(cell.getRow(), cell.getColumn() + 1));
						break;
					default:
						break;
					}
				}
				adjMatrix.put(cell, adjacenciesSet);
			}
		}
	}
	

	
	// Calculates the reachable cells starting at startCell and having pathLength tiles to move
	public void calcTargets(int row, int col, int pathLength) {
		targets = new HashSet<BoardCell>();
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
			return;
		}else {
			Set<BoardCell> adjacentCells = adjMatrix.get(newCell);
			for (BoardCell cell : adjacentCells) {
				if (cell != oldCell && cell != newCell && cell != startCell) {
					cellTargets(cell, newCell, steps - 1, startCell);
				}
				
			}
		}
		
		return;
		
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
}
