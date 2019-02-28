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
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid.length; j++) {
				if (i == 0 && j == 0) {
					targets.add(grid[i+1][j]);
					targets.add(grid[i][j+1]);
				} else if (i == grid.length-1 && j == grid.length
				targets.add(grid[i+1][j]);
				targets.add(grid[i+1][j]);
				targets.add(grid[i+1][j]);
				targets.add(grid[i+1][j]);
				adjacencies.put(grid[i][j], )
			}
		}
	}
	
	public HashSet<BoardCell> getAdjList(BoardCell cell){
		return adjacencies.get(cell);
	}
	
	public void calcTargets(BoardCell startCell, int pathLength) {
		targets = new HashSet<BoardCell>;
	}
	
	public HashSet<BoardCell> getTargets(){
		return targets;
	}
	
	public BoardCell getCell(int row, int column) {
		return grid[row][column];
	}
	
	
	
}
