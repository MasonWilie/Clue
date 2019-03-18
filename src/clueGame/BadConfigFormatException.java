package clueGame;

/**
 * BadConfigFormatExeption Class:
 * Exception that is thrown when the room or board config files are not
 * formatted correctly.
 * 
 * @author Hunter Rich
 * @author Mason Wilie
 * 
 */

public class BadConfigFormatException extends Exception {

	private String message;

	
	public BadConfigFormatException() {
		super("Error with format of configuration file\n");
	}
	
	public BadConfigFormatException(String newMessage) {
		super(newMessage);
	}
	
	
	public String toString() {
		return message;
	}
	
}
