package experiment;

import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;

/**
 * IntBoard Class:
 * Initializes the game board for the clue game by populating it with cells, calculating
 * the adjacencies for each cell, and allowing the user to receive the possible targets
 * for a certain move.
 * 
 * @author Hunter Rich
 * @author Mason Wilie
 * 
 */
public class IntBoard {
	
	private final int BOARD_HEIGHT = 4;
	private final int BOARD_WIDTH = 4;
	
	private BoardCell[][] grid;
	private HashSet<BoardCell> targets;
	private Map<BoardCell, HashSet<BoardCell>> adjacencies;
	
	public IntBoard() {
		super();
		adjacencies = new HashMap<BoardCell, HashSet<BoardCell>>();
		
		grid = new BoardCell[BOARD_HEIGHT][BOARD_WIDTH]; // HashSetting the grid to the specified dimensions
		for (int i = 0; i < BOARD_HEIGHT; i++) {
			for (int j = 0; j < BOARD_WIDTH; j++) {
				grid[i][j] = new BoardCell(i, j);
			}
		}
		
		calcAdjacencies();
		
		
	}
	
	private void calcAdjacencies() {
		HashSet<BoardCell> adjacenciesSet;
		
		// Calculates the adjacencies and adds them to a map with the cell as the key, takes into account boundary conditions
		for (int i = 0; i < BOARD_WIDTH; i++) {
			for (int j = 0; j < BOARD_HEIGHT; j++) {
				adjacenciesSet = new HashSet<BoardCell>();
				if (i != 0) { // Left of board
					adjacenciesSet.add(grid[i-1][j]);
				}
				if (i != BOARD_WIDTH - 1) { // Right of board
					adjacenciesSet.add(grid[i+1][j]);
				}
				if (j != 0) { // Top of board
					adjacenciesSet.add(grid[i][j-1]);
				}
				if (j != BOARD_HEIGHT - 1) { // Bottom of board
					adjacenciesSet.add(grid[i][j+1]);
				}
				adjacencies.put(grid[i][j], adjacenciesSet);
			}
		}
	}
	
	public HashSet<BoardCell> getAdjList(BoardCell cell){
		return adjacencies.get(cell);
	}
	
	
	public void calcTargets(BoardCell startCell, int pathLength) {
		targets = new HashSet<BoardCell>();
		
		targets.add(startCell);
		
		for (int i = 0; i < pathLength; i++) {
			HashSet<BoardCell> tempTargets = new HashSet<BoardCell>();
			tempTargets.addAll(targets);
			for (BoardCell cell: targets) {
				tempTargets.addAll(getAdjList(cell));
			}
			targets.addAll(tempTargets);
		}
		targets.remove(startCell);
		
	}
	
	public HashSet<BoardCell> getTargets(){
		return targets;
	}
	
	public BoardCell getCell(int row, int column) {
		return grid[row][column];
	}
	
	
	
}
