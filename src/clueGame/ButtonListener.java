package clueGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ButtonListener Class:
 * Handles button press events for the buttons in our ControlGUI class.
 * 
 * @author Hunter Rich
 * @author Mason Wilie
 * 
 */


public class ButtonListener implements ActionListener{
	
	private boolean beenPressed;
	
	public ButtonListener() {
		super();
		beenPressed = false;
	}
	
	
	/*
	 * Returns true if the button has been pressed, sets beenPressed back to false
	 */
	public boolean beenPressed() {
		boolean returnValue = beenPressed;
		beenPressed = false;
		return returnValue;
	}
	
	/*
	 * Sets the indicator 'beenPressed' if the button is pressed
	 */
	public void actionPerformed(ActionEvent e) {
		beenPressed = true;
	}
}
