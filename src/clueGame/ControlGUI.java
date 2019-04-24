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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
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
import javax.swing.SwingUtilities;
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
	public static JFrame frame;
	
	
	JMenuBar menuBar;
	
	JTextField whoseTurnTextBox;
	
	static JTextField dieRollTextBox;
	static JTextField guessTextBox;
	static JTextField responseTextBox;
	
	static JTextArea peopleTextBox;
	static JTextArea roomsTextBox;
	static JTextArea weaponsTextBox;
	
	private boolean gameRunning;
	
	private boolean hasChanged;
	
	private Mouse mouse;
	
	private final int FRAME_X = 800;
	private final int FRAME_Y = 800;
	
	private ButtonListener nextPlayerButton;
	private ButtonListener makeAccusationButton;
	private static ButtonListener cancelButton;
	private static ButtonListener submitButton;
	
	private static final JDialog frame1 = new JDialog(frame, "Make a Guess", true);
	
	private static Board board;
	
	private CustomDialog cDialog;
	
	private Player currentPlayer;
	
	public enum ErrorType{
		DOUBLE_MOVE,
		NO_TARGET_SELECTED,
		INVALID_TARGET
	}

	
	public ControlGUI() {
		gameRunning = false;
		
		frame = new JFrame();
		
		hasChanged = true;
		
		nextPlayerButton = new ButtonListener();
		makeAccusationButton = new ButtonListener();
		cancelButton = new ButtonListener();
		submitButton = new ButtonListener();
		
		cDialog = new CustomDialog();
		
		
		setUp();
		
		mouse = new Mouse(board.getCellDim());
		frame.addMouseListener(mouse);
		
		currentPlayer = board.getCurrentPlayer();
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		
		
		JPanel cardPanel = createMyCardsPanel();
		JPanel controlPanel = createControlPanel();
		menuBar = createMenuBar();
		
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
		
		
		JPanel whoseTurn = new JPanel();
		
		
		whoseTurn.setLayout(new GridLayout(3, 0));
		
		JLabel text = new JLabel("Whose turn?", SwingConstants.CENTER);
		whoseTurnTextBox = new JTextField();
		whoseTurnTextBox.setEditable(false);
		if (currentPlayer != null && gameRunning) {
			whoseTurnTextBox.setText(currentPlayer.getPlayerName());
		}else {
			whoseTurnTextBox.setText("");
		}
		
		
		
		whoseTurn.add(text);
		whoseTurn.add(whoseTurnTextBox);
		
		
		JButton nextPlayer = new JButton("Next player");
		JButton makeAccusation = new JButton("Make an accusation");
		
		nextPlayer.addActionListener(nextPlayerButton);
		makeAccusation.addActionListener(makeAccusationButton);
		
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
	
	
	/* createBottomButtonsPanel():
	 * Creates the fields that appear at the bottom of the GUI including the dice roll, the guess, and the response.
	 * 
	 */
	public JPanel createBottomButtonsPanel() {
		JPanel bottomButtons = new JPanel();
		bottomButtons.setLayout(new FlowLayout());
		
		
		
		
		
		
		dieRollTextBox = new JTextField();
		guessTextBox = new JTextField();
		responseTextBox = new JTextField();
		
		
		
		
		JPanel die = createTextFieldBox("Die", "Roll", 40, true, dieRollTextBox);
		JPanel guess = createTextFieldBox("Guess", "Guess", 350, false, guessTextBox);
		JPanel guessResult = createTextFieldBox("Guess Result", "Response", 120, true, responseTextBox);
		
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
	public JPanel createTextFieldBox(String label, String prompt, int width, boolean useCols, JTextField textField) {
		JPanel textFieldBox = new JPanel();
		
		if (!useCols)
			textFieldBox.setLayout(new GridLayout(2, 0));
		else
			textFieldBox.setLayout(new GridLayout(0, 2));
		JLabel text = new JLabel(prompt);
		
		textField.setEditable(false);
		
		TitledBorder title = BorderFactory.createTitledBorder(label);
		title.setTitlePosition(TitledBorder.TOP);
		

		textFieldBox.setBorder(title);
		
		textField.setPreferredSize(new Dimension(width, 20));
		
		textFieldBox.add(text);
		textFieldBox.add(textField);
		
		
		return textFieldBox;
	}
	
	
	// Creates the cards panel
	public JPanel createMyCardsPanel() {
		JPanel cardsPanel = new JPanel();
		cardsPanel.setLayout(new BoxLayout(cardsPanel, BoxLayout.Y_AXIS));

		
		peopleTextBox = createCardBox("People");
		roomsTextBox = createCardBox("Rooms");
		weaponsTextBox = createCardBox("Weapons");
		
		cardsPanel.add(peopleTextBox);
		cardsPanel.add(roomsTextBox);
		cardsPanel.add(weaponsTextBox);
		
		TitledBorder title = BorderFactory.createTitledBorder("My Cards");
		title.setTitlePosition(TitledBorder.TOP);
		cardsPanel.setBorder(title);
		
		
		return cardsPanel;
	}
	
	private JTextArea createCardBox(String label) {
		JTextArea cardBox = new JTextArea();
		
		TitledBorder title = BorderFactory.createTitledBorder(label);
		title.setTitlePosition(TitledBorder.TOP);
		cardBox.setBorder(title);
		
		cardBox.setEditable(false);
		
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
		
		cDialog.show();
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Game GUI");
		frame.setSize(FRAME_X, FRAME_Y);
		
		while(true) {
			if (hasChanged) {
				
				frame.add(this, BorderLayout.CENTER);
				
				frame.setVisible(true);
				
				mouse.setColOffset(board.getX());
				mouse.setRowOffset(board.getY());

				hasChanged = false;
				
			}
			
			
			// REMOVE ME WHEN THERE IS MORE CODE TO SLOW IT DOWN, LISTENERS DON'T WORK WHEN IT IS LOOPING TOO FAST
			/////////////////////////////////////////////////////////////////////////////
			try {
				TimeUnit.MILLISECONDS.sleep(5);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			////////////////////////////////////////////////////////////////////////////
			
			actions();
		}
		
		
	}
	
	
	public static void handleErrors(ErrorType e) {
		
		String message;
		
		switch(e) {
		case DOUBLE_MOVE:
			message = "You may not move more than once!";
			break;
		case NO_TARGET_SELECTED:
			message = "Please select a target.";
			break;
		case INVALID_TARGET:
			message = "Invalid target selection!";
			break;
		default:
			message = "Invalid error code";
			break;
		}
		
		JOptionPane.showMessageDialog(frame, message);
		
	}
	
	private void updateInfo() {
		
		
		
		currentPlayer = board.getCurrentPlayer();
		whoseTurnTextBox.setText(currentPlayer.getPlayerName());
		
		currentPlayer.rollDie();
		dieRollTextBox.setText(Integer.toString(currentPlayer.getDieRoll()));
		
		
		
		
		
		
		
	}
	
	public void actions() {
		if (nextPlayerButton.beenPressed()) {
			guessTextBox.setText("");
			responseTextBox.setText("");
			
			if (board.gameRunning) {
				if (board.nextPlayerPressed()) {
					updateInfo();
				}
				
			}else {
				board.gameRunning = true;
				
				currentPlayer = board.getCurrentPlayer();
				
				ArrayList<Card> playerDeck = currentPlayer.getHand();
				
				ArrayList<Card> personCards = new ArrayList<>();
				ArrayList<Card> roomCards = new ArrayList<>();
				ArrayList<Card> weaponCards = new ArrayList<>();
				
				for (Card card : playerDeck) {
					switch(card.getType()) {
					case PERSON:
						personCards.add(card);
						break;
					case ROOM:
						roomCards.add(card);
						break;
					case WEAPON:
						weaponCards.add(card);
						break;
					}
				}
				
				
				String text = "";
				
				for (int i = 0; i < personCards.size(); i++) {
					text = text + personCards.get(i).getName() + '\n';

				}
				peopleTextBox.setText(text);
				
				text = "";
				for (int i = 0; i < roomCards.size(); i++) {
					text = text + roomCards.get(i).getName() + '\n';

				}
				roomsTextBox.setText(text);
				
				text = "";
				for (int i = 0; i < weaponCards.size(); i++) {
					text = text + weaponCards.get(i).getName() + '\n';

				}
				weaponsTextBox.setText(text);
				
				
				updateInfo();
				
			}
			SwingUtilities.updateComponentTreeUI(frame);
		}
		if (makeAccusationButton.beenPressed()) {
			System.out.println("Making accusation");
		}
		if (mouse.hasClicked()) {
			if (currentPlayer instanceof HumanPlayer) board.movingTime(mouse.getClickRow(), mouse.getClickCol());
			SwingUtilities.updateComponentTreeUI(frame);
			
		}
		if (cancelButton.beenPressed()) {
			frame1.dispose();
		}
		if (submitButton.beenPressed()) {
			//do submit stuff
			Board.getInstance().handleSuggestion(Board.getInstance().getSolution(), Board.getInstance().getCurrentPlayer());
			frame1.dispose();
		}
		
	}
	
	public void splash() {
		String message = "You are " + board.getHumanPlayerName() + ", press Next Player to begin play";
		JOptionPane.showMessageDialog(frame, message, "Welcome to Clue", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void setGuess(Solution guess) {
		String guessText = guess.getPersonCard().getName() + " ";
		guessText += guess.getRoomCard().getName() + " ";
		guessText += guess.getWeaponCard().getName();
		
		this.guessTextBox.setText(guessText);
	}
	
	public void setRespose(Card card) {
		responseTextBox.setText(card.getName());
	}
	
	public static void displayModal() {
		JPanel newPanel = new JPanel();
		frame1.getContentPane().add(newPanel);
		frame1.pack();
		frame1.setVisible(true);
		
		newPanel.setLayout(new GridLayout(4,2));
		newPanel.add(new JLabel("Your room"));
		newPanel.add(new JLabel("Person"));
		newPanel.add(new JLabel("Weapon"));
		JButton mySubmitButton = new JButton("Submit");
		mySubmitButton.addActionListener(submitButton);
		newPanel.add(new JButton("Submit"));
		newPanel.add(new JLabel((Board.getInstance().getPeople().get(Board.getInstance().getWhichPersonWeOn()).getTarget().getLabel())));
		String[] faf1 = {"Miss Scarlet", "Mr. Green", "Mrs. Peacock", "Colonel Mustard", "Mrs. White", "Professor Plum"};
		newPanel.add(new JComboBox(faf1));
		String[] faf2 = { "Candlestick", "Lead Pipe", "Rope", "Knife", "Revolver", "Wrench" };
		newPanel.add(new JComboBox(faf2));
		JButton myCancelButton = new JButton("Cancel");
		myCancelButton.addActionListener(cancelButton);
		newPanel.add(myCancelButton);
		//now create the stuff in the grid places
		
//		JPanel cardsPanel = new JPanel();
//		cardsPanel.setLayout(new BoxLayout(cardsPanel, BoxLayout.Y_AXIS));
//
//		
//		peopleTextBox = createCardBox("People");
//		roomsTextBox = createCardBox("Rooms");
//		weaponsTextBox = createCardBox("Weapons");
//		
//		cardsPanel.add(peopleTextBox);
//		cardsPanel.add(roomsTextBox);
//		cardsPanel.add(weaponsTextBox);
//		
//		TitledBorder title = BorderFactory.createTitledBorder("My Cards");
//		title.setTitlePosition(TitledBorder.TOP);
//		cardsPanel.setBorder(title);
//		
//		
//		return cardsPanel;
	}
	public static void main(String[] args) {
		
		ControlGUI gui = new ControlGUI();
		gui.splash();
		gui.show();
		
		
		
		
	}
	
}
