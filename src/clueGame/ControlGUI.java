package clueGame;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Menu;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
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
	
	private final int FRAME_X = 1000;
	private final int FRAME_Y = 1000;
	
	private static Board board;
	
	private Player currentPlayer;
	
	public ControlGUI() {
		setUp();
		
		currentPlayer = board.getCurrentPlayer();
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		
		
		JPanel cardPanel = createMyCardsPanel();
		JPanel controlPanel = createControlPanel();
		JMenuBar menuBar = createMenuBar();
		
		c.fill = GridBagConstraints.BOTH;
		c.insets = new Insets(1, 1, 1, 1);
		
		// Setting up the menu bar
		c.gridheight = 1;
		c.gridwidth = 10;
		c.gridx = 0;
		c.gridy = 0;
		add(menuBar, c);
		
		// Setting up the game board display
		c.weightx = 0.9;
		c.weighty = 0.9;
		c.gridx = 0;
		c.gridy=1;
		c.gridwidth = 6;
		c.gridheight = 6;
		add(board, c);
		
		// Setting up the the card panel
		c.weightx = 0.1;
		c.weighty = 0;
		c.gridx = 7;
		c.gridy = 1;
		c.gridwidth = 3;
		c.gridheight = 6;
		add(cardPanel, c);
		
		// Setting up the control panel (bottons and stuff on bottom)
		c.weightx = 0;
		c.weighty = 0.1;
		c.gridx = 0;
		c.gridy = 7;
		c.gridheight = 3;
		c.gridwidth = 10;
		add(controlPanel, c);
		
		
		
	}
	
	
	// Creates the bottom control panel on the Control GUI
	public JPanel createControlPanel() {
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JPanel topButtons = createTopButtonsPanel();
		JPanel bottomButtons = createBottomButtonsPanel();
		
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.BOTH;
		c.weightx = 1;
		
		
		c.gridx = 0; // Buttons start in top left corner
		c.gridy = 0;
		c.gridheight = 2; // Take up 2 thirds of grid space
		c.gridwidth = 9; // Entire length of grid
		c.weighty = 0.9; // Buttons take up most of the remaining y space
		controlPanel.add(topButtons, c);
		
		c.weighty = 0.1; // Bottom portion does not get much of remaining y space
		c.gridy = 2; // Starts 2 down, 0 over
		c.gridheight = 1; // Only 1 high
		controlPanel.add(bottomButtons, c);
		
		return controlPanel;
	}
	
	/* createTopButtonsPanel():
	 * Creates the top 3 frames which display whose turn it is, and give buttons for next player
	 * and make accusation.
	 * 
	 */
	
	public JPanel createTopButtonsPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		
		
		JPanel whoseTurn = createTextField("Whose turn?", currentPlayer.getPlayerName());
		JButton nextPlayer = new JButton("Next player");
		JButton makeAccusation = new JButton("Make an accusation");
		
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		c.fill = GridBagConstraints.BOTH;
		c.gridwidth = 3;
		
		
		c.weightx = 1;
		c.weighty = 1;
		
		c.gridx = 0;
		c.gridy = 0;
		c.gridheight = 1;
		panel.add(whoseTurn, c);
		
		c.gridx = 3;
		c.gridheight = 2;
		panel.add(nextPlayer, c);
		
		c.gridx = 6;
		panel.add(makeAccusation, c);
		
		return panel;
	}
	
	/* createButtonPanel():
	 * Creates a button with the textField specified as an argument, then returns that button as a JPanel
	 * 
	 */
	public JPanel createButtonPanel(String textField) {
		JButton button = new JButton(textField);
		JPanel buttonPanel = new JPanel();
		//button.setPreferredSize(new Dimension(FRAME_X/10 - 5, (int)(FRAME_Y/10) - 5));
		buttonPanel.add(button);
		return buttonPanel;
	}
	
	
	/* createTextField():
	 * Creates a text field used in the top buttons, with the label specified by the argument 'prompt'
	 * 
	 */
	public JPanel createTextField(String prompt, String contents) {
		JPanel panel = new JPanel();
		
		panel.setLayout(new GridLayout(3, 0));
		
		JLabel text = new JLabel(prompt, SwingConstants.CENTER);
		JTextField textBox = new JTextField();
		textBox.setEditable(false);
		textBox.setText(contents);
		
		
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
		
		JPanel die = createTextFieldBox("Die", "Roll", 40, true, Integer.toString(currentPlayer.getDieRoll()));
		JPanel guess = createTextFieldBox("Guess", "Guess", 350, false, "");
		JPanel guessResult = createTextFieldBox("Guess Result", "Response", 120, true, "");
		
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
	public JPanel createTextFieldBox(String label, String prompt, int width, boolean useCols, String contents) {
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
		
		textField.setText(contents);
		
		textFieldBox.add(text);
		textFieldBox.add(textField);
		
		
		return textFieldBox;
	}
	
	
	// Creates the cards panel
	public JPanel createMyCardsPanel() {
		JPanel cardsPanel = new JPanel();
		cardsPanel.setLayout(new BoxLayout(cardsPanel, BoxLayout.Y_AXIS));
		
		ArrayList<Card> playerCards = currentPlayer.getHand();
		
		ArrayList<Card> peopleCards = new ArrayList<>();
		ArrayList<Card> roomCards = new ArrayList<>();
		ArrayList<Card> weaponCards = new ArrayList<>();
		
		for (Card card : playerCards) {
			switch(card.getType()) {
			case PERSON:
				peopleCards.add(card);
				break;
			case ROOM:
				roomCards.add(card);
				break;
			case WEAPON:
				weaponCards.add(card);
				break;
			}
		}
		
		JTextArea people = createCardBox("People", peopleCards);
		JTextArea rooms = createCardBox("Rooms", roomCards);
		JTextArea weapons = createCardBox("Weapons", weaponCards);
		
		cardsPanel.add(people);
		cardsPanel.add(rooms);
		cardsPanel.add(weapons);
		
		TitledBorder title = BorderFactory.createTitledBorder("My Cards");
		title.setTitlePosition(TitledBorder.TOP);
		cardsPanel.setBorder(title);
		
		
		return cardsPanel;
	}
	
	private JTextArea createCardBox(String label, ArrayList<Card> cards) {
		JTextArea cardBox = new JTextArea();
		
		TitledBorder title = BorderFactory.createTitledBorder(label);
		title.setTitlePosition(TitledBorder.TOP);
		cardBox.setBorder(title);
		
		cardBox.setEditable(false);
		
		for (int i = 0; i < cards.size(); i++) {
			cardBox.append( cards.get(i).getName() + '\n');
		}
		
		
		return cardBox;
	}
	
	
	// Creates the menu bar with file
	public JMenuBar createMenuBar() {
		JMenuBar menuBar = new JMenuBar();

		JMenu file = new JMenu("File");
		
		// Adds the menu items to the file dropdown
		JMenuItem exit = new JMenuItem("Exit");
		JMenuItem showNotes = new JMenuItem("Show Notes");
		
		file.add(exit);
		file.add(showNotes);
		
		
		
		menuBar.add(file);
		
		return menuBar;
	}
	
	// Sets up the game board
	public static void setUp() {
		// Board is singleton, get the only instance
		board = Board.getInstance();
		// set the file names to use my config files
		board.setConfigFiles("Board_Layout.csv", "ClueRooms.txt", "players.txt", "cards.txt");		
		// Initialize will load BOTH config files 
		board.initialize();
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
		frame.add(this, BorderLayout.CENTER);
		
		String message = "You are " + board.getHumanPlayerName() + ", press Next Player to begin play";
		JOptionPane.showMessageDialog(frame, message, "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
		
		frame.setVisible(true);
		
	}
	
	public static void main(String[] args) {
		
		ControlGUI gui = new ControlGUI();
		gui.show();
		
		CustomDialog cDialog = new CustomDialog();
		cDialog.show();
	}
	
}
