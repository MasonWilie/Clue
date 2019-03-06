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

public class BadConfigFormatException extends RuntimeException {

	private String message;

	
	public BadConfigFormatException() {
		super();
		message = "Error with format of configuration file\n";
	}
	
	public BadConfigFormatException(String newMessage) {
		message = newMessage;
	}
	
	
	public String toString() {
		return message;
	}
	
}
