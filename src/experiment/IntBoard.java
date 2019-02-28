package experiment;

import java.util.Map;
import java.util.Set;

/* Contains the grid and adjacency lists
 * 
 */
public class IntBoard {
	
	private BoardCell[][] grid;
	private Set<BoardCell> targets;
	private Map<BoardCell, Set<BoardCell>> adjacencies;
	
	public IntBoard() {
		
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
	
	public Set<BoardCell> getAdjList(BoardCell cell){
		return adjacencies.get(cell);
	}
	
	public void calcTargets(BoardCell startCell, int pathLength) {
		
	}
	
	public Set<BoardCell> getTargets(){
		return targets;
	}
	
	public BoardCell getCell(int row, int column) {
		return grid[row][column];
	}
	
	
	
}
