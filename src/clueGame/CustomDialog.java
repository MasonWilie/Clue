package clueGame;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.BoxLayout;

/**
 * ControlGUI Class:
 * Creates and displays the graphical user interface for the clue game.
 * 
 * @author Hunter Rich
 * @author Mason Wilie
 * 
 */

public class CustomDialog extends JPanel{

	private CustomDialog dialog;
	private JDialog theDialog;

	private final int FRAME_X = 800;
	private final int FRAME_Y = 800;


	public CustomDialog() {
		setLayout(new GridLayout(0, 2));
		JPanel leftSide = createLeftPanel();
		JPanel rightSide = createRightPanel();
		add(leftSide);
		add(rightSide);
	}

	//Creates a vertical stacking panel on the left side of the grid layout
	public JPanel createLeftPanel() {
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

		JPanel threeThingsInThePanel = createLeftSideBoxes();
		leftPanel.add(threeThingsInThePanel);	

		return leftPanel;
	}

	//Creates a vertical stacking panel on the right side of the grid layout
	public JPanel createRightPanel() {
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

		JPanel threeThingsInThePanel = createRightSideBoxes();
		rightPanel.add(threeThingsInThePanel);

		return rightPanel;
	}

	//Creates the three People, Room and Weapon boxes with JCheckBoxes in them
	public JPanel createLeftSideBoxes() {
		JPanel threeThingsInThePanel = new JPanel();
		threeThingsInThePanel.setLayout(new BoxLayout(threeThingsInThePanel, BoxLayout.Y_AXIS));


		JPanel peopleBody = createPeopleBody();
		JPanel roomsBody = createRoomsBody();
		JPanel weaponsBody = createWeaponsBody();


		threeThingsInThePanel.add(peopleBody);

		threeThingsInThePanel.add(roomsBody);

		threeThingsInThePanel.add(weaponsBody);

		return threeThingsInThePanel;
	}

	//Creates the three JComboBoxes containing the people, rooms and weapons respectively
	public JPanel createRightSideBoxes() {
		JPanel threeThingsInThePanel = new JPanel();
		threeThingsInThePanel.setLayout(new BoxLayout(threeThingsInThePanel, BoxLayout.Y_AXIS));


		//Make an array of people, rooms, and weapons
		final int howManyPeople = 6;
		final int howManyRooms = 8;
		final int howManyWeapons = 6;
		String[] peopleList = { "Miss Scarlet", "Mr. Green", "Mrs. Peacock", "Colonel Mustard", "Mrs. White", "Professor Plum" };
		String[] roomList = { "Library", "Study", "Dining Room", "Hall", "Ballroom", "Billiard Room", "Kitchen", "Lounge" };
		String[] weaponList = { "Candlestick", "Lead Pipe", "Rope", "Knife", "Revolver", "Wrench" };

		JPanel peopleGuessBody = createGuessBody(peopleList);
		JPanel roomGuessBody = createGuessBody(roomList);
		JPanel weaponGuessBody = createGuessBody(weaponList);

		TitledBorder title = BorderFactory.createTitledBorder("Weapons");
		title.setTitlePosition(TitledBorder.TOP);


		peopleGuessBody.setBorder(title);
		roomGuessBody.setBorder(title);
		weaponGuessBody.setBorder(title);


		threeThingsInThePanel.add(peopleGuessBody);

		threeThingsInThePanel.add(roomGuessBody);

		threeThingsInThePanel.add(weaponGuessBody);

		return threeThingsInThePanel;
	}

	//The JPanel body containing the JCheckBoxes for all the people for the user to check or uncheck
	public JPanel createPeopleBody() {
		JPanel peopleBody = new JPanel();
		peopleBody.setLayout(new GridLayout(0, 2));

		//Create the clicky boxes in the peopleBody
		JCheckBox missScarlet = new JCheckBox("Miss Scarlet");
		JCheckBox mrGreen = new JCheckBox("Mr. Green");
		JCheckBox mrsPeacock = new JCheckBox("Mrs. Peacock");
		JCheckBox colonelMustard = new JCheckBox("Colonel Mustard");
		JCheckBox mrsWhite = new JCheckBox("Mrs. White");
		JCheckBox profPlum = new JCheckBox("Professor Plum");

		//Add the clicky boxes to peopleBody
		peopleBody.add(missScarlet);
		peopleBody.add(mrGreen);
		peopleBody.add(mrsPeacock);
		peopleBody.add(colonelMustard);
		peopleBody.add(mrsWhite);
		peopleBody.add(profPlum);

		TitledBorder title = BorderFactory.createTitledBorder("People");
		title.setTitlePosition(TitledBorder.TOP);

		peopleBody.setBorder(title);

		return peopleBody;
	}

	//The JPanel body containing the JCheckBoxes for all the rooms for the user to check or uncheck
	public JPanel createRoomsBody() {
		JPanel roomsBody = new JPanel();
		roomsBody.setLayout(new GridLayout(0, 2));

		//Create the clicky boxes in the roomsBody
		JCheckBox library = new JCheckBox("Library");
		JCheckBox study = new JCheckBox("Study");
		JCheckBox diningRoom = new JCheckBox("Dining Room");
		JCheckBox hall = new JCheckBox("Hall");
		JCheckBox ballroom = new JCheckBox("Ballroom");
		JCheckBox billiardRoom = new JCheckBox("Billiard Room");
		JCheckBox kitchen = new JCheckBox("Kitchen");
		JCheckBox lounge = new JCheckBox("Lounge");

		//Add the clicky boxes to roomsBody
		roomsBody.add(library);
		roomsBody.add(study);
		roomsBody.add(diningRoom);
		roomsBody.add(hall);
		roomsBody.add(ballroom);
		roomsBody.add(billiardRoom);
		roomsBody.add(kitchen);
		roomsBody.add(lounge);

		TitledBorder title = BorderFactory.createTitledBorder("Rooms");
		title.setTitlePosition(TitledBorder.TOP);
		roomsBody.setBorder(title);

		return roomsBody;
	}

	//The JPanel body containing the JCheckBoxes for all the weapons for the user to check or uncheck
	public JPanel createWeaponsBody() {
		JPanel weaponsBody = new JPanel();
		weaponsBody.setLayout(new GridLayout(0, 2));

		//Create the clicky boxes in the weaponsBody
		JCheckBox candlestick = new JCheckBox("Candlestick");
		JCheckBox leadPipe = new JCheckBox("Lead Pipe");
		JCheckBox rope = new JCheckBox("Rope");
		JCheckBox knife = new JCheckBox("Knife");
		JCheckBox revolver = new JCheckBox("Revolver");
		JCheckBox wrench = new JCheckBox("Wrench");

		//Add the clicky boxes to weaponsBody
		weaponsBody.add(candlestick);
		weaponsBody.add(leadPipe);
		weaponsBody.add(rope);
		weaponsBody.add(knife);
		weaponsBody.add(revolver);
		weaponsBody.add(wrench);

		TitledBorder title = BorderFactory.createTitledBorder("Weapons");
		title.setTitlePosition(TitledBorder.TOP);
		weaponsBody.setBorder(title);

		return weaponsBody;
	}

	//The JPanel body containing the JComboBox for the user to make a selection. Is called three times with different arrays serving as the content of the JComboBox
	public JPanel createGuessBody(String[] faf) {
		JPanel guessBody = new JPanel();
		guessBody.setLayout(new GridLayout(0, 2));

		//Create the dumbo bombo boxes
		JComboBox ourCombo = new JComboBox(faf);

		guessBody.add(ourCombo);

		return guessBody;
	}

	//Creates the dialog and sets up the general features of the dialog and sets it visible
	public void show() {
		theDialog = new JDialog();
		theDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		theDialog.setTitle("Custom Dialog");
		theDialog.setSize(FRAME_X, FRAME_Y);

		dialog = new CustomDialog();
		theDialog.add(dialog, BorderLayout.CENTER);
		theDialog.setVisible(true);

	}

	public static void main(String[] args) {
		CustomDialog dialog1 = new CustomDialog();
		dialog1.show();
	}

}
