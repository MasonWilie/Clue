package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 * BoardCell Class:
 * Represents one cell of the game board
 * 
 * @author Hunter Rich
 * @author Mason Wilie
 * 
 */
public class BoardCell {
	private int row, column;
	private char initial;
	DoorDirection direction;
	
	public BoardCell(int setRow, int setColumn) {
		row = setRow;
		column = setColumn;
	}
	
	public boolean isWalkway() {
		return initial == 'W';
	}
	
	public boolean isRoom() throws FileNotFoundException {
		//check the first char in each row of ClueRooms.txt
		FileReader reader = new FileReader("ClueRooms.txt");
		Scanner in = new Scanner(reader);
		int lineNumber = 1;
		while (in.hasNextLine()) {
			String line = in.nextLine();
			initial = line.charAt(0);
			lineNumber++;
		}
		return initial != 'W';
	}
	
	public boolean isDoorway() {
		if (direction == DoorDirection.NONE) {
			return false;
		} else {
			return true;
		}
	}
	
}
