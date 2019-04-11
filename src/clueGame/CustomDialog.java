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
	private JFrame frame;
	
	private final int FRAME_X = 800;
	private final int FRAME_Y = 800;
	
	
	public CustomDialog() {
		setLayout(new GridLayout(0, 2));
		JPanel leftSide = createLeftPanel();
		JPanel rightSide = createRightPanel();
		add(leftSide);
		add(rightSide);
	}
	
	public JPanel createLeftPanel() {
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		
		JPanel threeThingsInThePanel = createLeftSideBoxes();
		leftPanel.add(threeThingsInThePanel);	
		
		return leftPanel;
	}
	
	public JPanel createRightPanel() {
		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		
		JPanel threeThingsInThePanel = createRightSideBoxes();
		rightPanel.add(threeThingsInThePanel);
		
		return rightPanel;
	}

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
	
	public JPanel createRightSideBoxes() {
		JPanel threeThingsInThePanel = new JPanel();
		threeThingsInThePanel.setLayout(new BoxLayout(threeThingsInThePanel, BoxLayout.Y_AXIS));
		
		
		//make an array of people, rooms, and weapons
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
	
	public JPanel createPeopleBody() {
		JPanel peopleBody = new JPanel();
		peopleBody.setLayout(new GridLayout(0, 2));
		
		//create the clicky boxes in the peopleBody
		JCheckBox missScarlet = new JCheckBox("Miss Scarlet");
		JCheckBox mrGreen = new JCheckBox("Mr. Green");
		JCheckBox mrsPeacock = new JCheckBox("Mrs. Peacock");
		JCheckBox colonelMustard = new JCheckBox("Colonel Mustard");
		JCheckBox mrsWhite = new JCheckBox("Mrs. White");
		JCheckBox profPlum = new JCheckBox("Professor Plum");
		
		//add the clicky boxes to peopleBody
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
	
	public JPanel createRoomsBody() {
		JPanel roomsBody = new JPanel();
		roomsBody.setLayout(new GridLayout(0, 2));
		
		//create the clicky boxes in the peopleBody
		JCheckBox library = new JCheckBox("Library");
		JCheckBox study = new JCheckBox("Study");
		JCheckBox diningRoom = new JCheckBox("Dining Room");
		JCheckBox hall = new JCheckBox("Hall");
		JCheckBox ballroom = new JCheckBox("Ballroom");
		JCheckBox billiardRoom = new JCheckBox("Billiard Room");
		JCheckBox kitchen = new JCheckBox("Kitchen");
		JCheckBox lounge = new JCheckBox("Lounge");
		
		//add the clicky boxes to peopleBody
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
	
	public JPanel createWeaponsBody() {
		JPanel weaponsBody = new JPanel();
		weaponsBody.setLayout(new GridLayout(0, 2));
		
		//create the clicky boxes in the peopleBody
		JCheckBox candlestick = new JCheckBox("Candlestick");
		JCheckBox leadPipe = new JCheckBox("Lead Pipe");
		JCheckBox rope = new JCheckBox("Rope");
		JCheckBox knife = new JCheckBox("Knife");
		JCheckBox revolver = new JCheckBox("Revolver");
		JCheckBox wrench = new JCheckBox("Wrench");
		
		//add the clicky boxes to peopleBody
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
	
	public JPanel createGuessBody(String[] faf) {
		JPanel guessBody = new JPanel();
		guessBody.setLayout(new GridLayout(0, 2));
		
		//create the dumbo bombo boxes
		JComboBox ourCombo = new JComboBox(faf);
		
		//add them to the guessBody
		guessBody.add(ourCombo);
		
		return guessBody;
	}
	
	public void show() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Custom Dialog");
		frame.setSize(FRAME_X, FRAME_Y);
		
		dialog = new CustomDialog();
		frame.add(dialog, BorderLayout.CENTER);
		frame.setVisible(true);
		
	}
	
	public static void main(String[] args) {
		CustomDialog dialog1 = new CustomDialog();
		dialog1.show();
	}
	
}
