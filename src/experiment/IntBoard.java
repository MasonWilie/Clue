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
		
	}
	
	public Set<BoardCell> getAdjList(BoardCell cell){
		return adjacencies.get(cell);
	}
	
	public void calcTargets(BoardCell startCell, int pathLength) {
		
	}
	
	public Set<BoardCell> getTargets(){
		return targets;
	}
	
	
	
}
