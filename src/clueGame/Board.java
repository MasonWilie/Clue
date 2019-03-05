package clueGame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import experiment.BoardCell;

public class Board {
	private int numRows, numColumns;
	public static final int MAX_BOARD_SIZE = 50;
	
	private int BOARD_WIDTH, BOARD_HEIGHT;
	
	private BoardCell[][] board;
	
	private HashMap<Character, String> legend; 
	private HashMap<BoardCell, HashSet<BoardCell>> adjMatrix;
	
	private HashSet<BoardCell> targets;
	
	private String boardConfigFile;
	private String roomConfigFile;
	
	Board(){
		super();
		boardConfigFile = "Board_Layout.csv";
		roomConfigFile = "ClueRooms.txt";
	}
	
	public Board getInstance() {
		return this;
	}
	
	public void initialize() {}
	
	public void loadRoomConfig() throws IOException {
		File roomsFile = new File(roomConfigFile);
		BufferedReader in = new BufferedReader(new FileReader(roomsFile));
		
		String currentLine;
		while ((currentLine = in.readLine()) != null) {
			Character initial;
			String description;
			
			List<String> stringElements = Arrays.asList(currentLine.split(","));
			initial = stringElements.get(0).charAt(0);
			
			
			
		}
		
		
	}
	
	public void loadBoardConfig() {
		
		
	}
	
	private void calcAdjacencies() {
		HashSet<BoardCell> adjacenciesSet;
		
		// Calculates the adjacencies and adds them to a map with the cell as the key, takes into account boundary conditions
		for (int i = 0; i < BOARD_WIDTH; i++) {
			for (int j = 0; j < BOARD_HEIGHT; j++) {
				adjacenciesSet = new HashSet<BoardCell>();
				if (i != 0) { // Left of board
					adjacenciesSet.add(board[i-1][j]);
				}
				if (i != BOARD_WIDTH - 1) { // Right of board
					adjacenciesSet.add(board[i+1][j]);
				}
				if (j != 0) { // Top of board
					adjacenciesSet.add(board[i][j-1]);
				}
				if (j != BOARD_HEIGHT - 1) { // Bottom of board
					adjacenciesSet.add(board[i][j+1]);
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
	
	
}
