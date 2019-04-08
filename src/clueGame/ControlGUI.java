package clueGame;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

/**
 * ControlGUI Class:
 * Creates and displays the graphical user interface for the clue game.
 * 
 * @author Hunter Rich
 * @author Mason Wilie
 * 
 */

public class ControlGUI extends JPanel{
	
	private ControlGUI gameGUI;
	private JFrame frame;
	
	private final int FRAME_X = 800;
	private final int FRAME_Y = 250;
	
	
	public ControlGUI() {
		setLayout(new GridLayout(2, 0));
		JPanel topButtons = createTopButtonsPanel();
		JPanel bottomButtons = createBottomButtonsPanel();
		add(topButtons);
		add(bottomButtons);
		
		
	}
	
	/* createTopButtonsPanel():
	 * Creates the top 3 frames which display whose turn it is, and give buttons for next player
	 * and make accusation.
	 * 
	 */
	
	public JPanel createTopButtonsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0, 3));
		
		JPanel whoseTurn = createTextField("Whose turn?");
		JPanel nextPlayer = createButtonPanel("Next player");
		JPanel makeAccusation = createButtonPanel("Make an accusation");

		panel.add(whoseTurn);
		panel.add(nextPlayer);
		panel.add(makeAccusation);
		
		return panel;
	}
	
	/* createButtonPanel():
	 * Creates a button with the textField specified as an argument, then returns that button as a JPanel
	 * 
	 */
	public JPanel createButtonPanel(String textField) {
		JButton button = new JButton(textField);
		JPanel buttonPanel = new JPanel();
		button.setPreferredSize(new Dimension(FRAME_X/3 - 5, (int)(FRAME_Y/2.5) - 5));
		buttonPanel.add(button);
		return buttonPanel;
	}
	
	
	/* createTextField():
	 * Creates a text field used in the top buttons, with the label specified by the argument 'prompt'
	 * 
	 */
	public JPanel createTextField(String prompt) {
		JPanel panel = new JPanel();
		
		panel.setLayout(new GridLayout(3, 0));
		
		JLabel text = new JLabel(prompt, SwingConstants.CENTER);
		JTextField textBox = new JTextField();
		textBox.setEditable(false);
		
		textBox.setPreferredSize(new Dimension((int)(FRAME_X / 4), 20));
		
		
		panel.add(text);
		panel.add(textBox);
		
		return panel;
	}
	
	/* createBottomButtonsPanel():
	 * Creates the fields that appear at the bottom of the GUI including the dice roll, the guess, and the response.
	 * 
	 */
	public JPanel createBottomButtonsPanel() {
		JPanel bottomButtons = new JPanel();
		bottomButtons.setLayout(new FlowLayout());
		
		JPanel die = createTextFieldBox("Die", "Roll", 40, true);
		JPanel guess = createTextFieldBox("Guess", "Guess", 350, false);
		JPanel guessResult = createTextFieldBox("Guess Result", "Response", 120, true);
		
		bottomButtons.add(die);
		bottomButtons.add(guess);
		bottomButtons.add(guessResult);
		
		bottomButtons.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		
		return bottomButtons;
	}
	
	
	/* createTextFieldBox():
	 * Creates the text field with a border and label as seen in the bottom of the GUI
	 * 
	 */
	public JPanel createTextFieldBox(String label, String prompt, int width, boolean useCols) {
		JPanel textFieldBox = new JPanel();
		
		if (!useCols)
			textFieldBox.setLayout(new GridLayout(2, 0));
		else
			textFieldBox.setLayout(new GridLayout(0, 2));
		JLabel text = new JLabel(prompt);
		
		JTextField textField = new JTextField();
		textField.setEditable(false);
		
		TitledBorder title = BorderFactory.createTitledBorder(label);
		title.setTitlePosition(TitledBorder.TOP);
		

		textFieldBox.setBorder(title);
		
		textField.setPreferredSize(new Dimension(width, 20));
		
		textFieldBox.add(text);
		textFieldBox.add(textField);
		
		
		return textFieldBox;
	}
	
	/* show():
	 * Displays everything in the GUI
	 * 
	 */
	public void show() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Game GUI");
		frame.setSize(FRAME_X, FRAME_Y);
		
		gameGUI = new ControlGUI();
		frame.add(gameGUI, BorderLayout.CENTER);
		frame.setVisible(true);
		
	}
	
	public static void main(String[] args) {
		ControlGUI gui = new ControlGUI();
		gui.show();
	}
	
}
