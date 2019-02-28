package experiment;

import java.util.Map;
import java.util.HashSet;

/* Contains the grid and adjacency lists
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
		
		grid = new BoardCell[BOARD_HEIGHT][BOARD_WIDTH]; // HashSetting the grid to the specified dimensions
		for (int i = 0; i < BOARD_HEIGHT; i++) {
			for (int j = 0; j < BOARD_WIDTH; j++) {
				grid[i][j] = new BoardCell(i, j);
			}
		}
		
		calcAdjacencies();
		
		
	}
	
	private void calcAdjacencies() {
		HashSet<BoardCell> adjacenciesSet = new HashSet<BoardCell>();
		for (int i = 0; i < BOARD_WIDTH; i++) {
			for (int j = 0; j < BOARD_HEIGHT; j++) {
				if (i == 0 && j == 0) {
					adjacenciesSet.add(grid[i+1][j]);
					adjacenciesSet.add(grid[i][j+1]);
				} else if (i == BOARD_WIDTH-1 && j == BOARD_HEIGHT-1) {
					adjacenciesSet.add(grid[i-1][j]);
					adjacenciesSet.add(grid[i][j-1]);
				} else if (i == BOARD_WIDTH-1) {
					adjacenciesSet.add(grid[i-1][j]);
					adjacenciesSet.add(grid[i][j+1]);
					adjacenciesSet.add(grid[i][j-1]);
				} else if (j == BOARD_HEIGHT-1) {
					adjacenciesSet.add(grid[i][j-1]);
					adjacenciesSet.add(grid[i+1][j]);
					adjacenciesSet.add(grid[i-1][j]);
				} else if (i == 0) {
					adjacenciesSet.add(grid[i+1][j]);
					adjacenciesSet.add(grid[i][j+1]);
					adjacenciesSet.add(grid[i][j-1]);
				} else if (j == 0) {
					adjacenciesSet.add(grid[i][j+1]);
					adjacenciesSet.add(grid[i+1][j]);
					adjacenciesSet.add(grid[i-1][j]);
				} else {
					adjacenciesSet.add(grid[i+1][j]);
					adjacenciesSet.add(grid[i-1][j]);
					adjacenciesSet.add(grid[i][j+1]);
					adjacenciesSet.add(grid[i][j-1]);
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
	}
	
	public HashSet<BoardCell> getTargets(){
		return targets;
	}
	
	public BoardCell getCell(int row, int column) {
		return grid[row][column];
	}
	
	
	
}
