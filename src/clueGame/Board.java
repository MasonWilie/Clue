package clueGame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import clueGame.BoardCell;

public class Board {
	private int numRows, numColumns;
	
	public static final int MAX_BOARD_SIZE = 50;
		
	private BoardCell[][] board;
	
	private HashMap<Character, String> legend; 
	private HashMap<BoardCell, HashSet<BoardCell>> adjMatrix;
	
	private HashSet<BoardCell> targets;
	
	private String boardConfigFile;
	private String roomConfigFile;
	
	Board(){
		super();
		
	}

	public void setConfigFiles(String newBoardConfig, String newRoomConfig) {
		boardConfigFile = newBoardConfig;
		roomConfigFile = newRoomConfig;
	}
	
	public static Board getInstance() {
		
		return new Board();
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
	
	public void initialize(){
		
		legend = new HashMap<Character, String>();
		adjMatrix = new HashMap<BoardCell, HashSet<BoardCell>>();
		
		
		try {
			loadRoomConfig();
			loadBoardConfig();
			
			calcAdjacencies();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadRoomConfig() throws IOException {
		File roomsFile = new File(roomConfigFile);
		BufferedReader in = new BufferedReader(new FileReader(roomsFile));
		
		String currentLine;
		while ((currentLine = in.readLine()) != null) {
			Character initial;
			String description;
			
			List<String> stringElements = Arrays.asList(currentLine.split(","));
			initial = stringElements.get(0).charAt(0);
			description = stringElements.get(1);
			
			while(description.charAt(0) == ' ') {
				description = description.substring(1, description.length());
			}
			
			legend.put(initial, description);
			
		}
		
		
	}
	
	public void loadBoardConfig() throws IOException {
		File boardFile = new File(boardConfigFile);
		BufferedReader in = new BufferedReader(new FileReader(boardFile));
		
		String nextLine;
		numRows = 0;
		ArrayList<String[]> grid = new ArrayList<String[]>();
		while((nextLine = in.readLine()) != null) {
			String[] currentRow = nextLine.split(",");
			grid.add(currentRow);
		}
		
		numRows = grid.size();
		numColumns = grid.get(0).length;
		
		board = new BoardCell[numRows][numColumns];
		
		for (int i = 0; i < grid.size(); i++) {
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
				cell.setInitial(grid.get(i)[j].charAt(0));;
				
				
				
				board[i][j] = cell;
			}
		}
		
	}
	
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
	
	
	public void calcTargets(BoardCell startCell, int pathLength) {
		targets = new HashSet<BoardCell>();
		
		targets.add(startCell);
		
		for (int i = 0; i < pathLength; i++) {
			HashSet<BoardCell> tempTargets = new HashSet<BoardCell>();
			tempTargets.addAll(targets);
			for (BoardCell cell: targets) {
				tempTargets.addAll(adjMatrix.get(cell));
			}
			targets.addAll(tempTargets);
		}
		targets.remove(startCell);
		
	}
	
	public HashMap<Character, String> getLegend(){
		return legend;
	}
}
