package setup;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Palm-Laptop
 * Setting uses for set the game setting
 */
public class Setting {
	private static double startingStack = 100.0;
	private static ArrayList<String> position = new ArrayList<String>(Arrays.asList("LJ", "HJ", "CO", "BU", "SB", "BB"));
	private static String card = "AKQJT98765432";
	
	private static final String cardNLH = "AKQJT98765432";
	private static final String cardSixPlus = "AKQJT9876";
	
	private static final ArrayList<String> numberOfPlayerList = new ArrayList<String>(Arrays.asList("2", "6"));
	
	private static final ArrayList<String> position6Player = new ArrayList<String>(Arrays.asList("LJ", "HJ", "CO", "BU", "SB", "BB")); 
	private static final ArrayList<String> position2Player = new ArrayList<String>(Arrays.asList("SB", "BB"));
	
	// getter for starting stack
	public static double getStartingStack() {
		return Setting.startingStack;
	}
	
	// setter for staring stack
	public static void setStartingStack(double startingStack) {
		Setting.startingStack = startingStack;
	}
	
	// setter for position by number
	public static void setPositionByNumber(int numberOfPlayer) {
		switch (numberOfPlayer) {
		case 2:
			Setting.position = Setting.position2Player;
			break;
		case 6:
			Setting.position = Setting.position6Player;
			break;
		default:
			Setting.position = Setting.position6Player;
		}
	}
	
	// getter for position
	public static ArrayList<String> getPosition() {
		return Setting.position;
	}
	
	// setter for position
	public static void setPosition(ArrayList<String> position) {
		Setting.position = position;
	}
	
	// getter for card
	public static String getCard() {
		return Setting.card;
	}
	
	// setter for card
	public static void setCard(String card) {
		Setting.card = card;
	}
	
	//getter for numberOfPlayerList
	public static ArrayList<String> getNumberofplayerlist() {
		return Setting.numberOfPlayerList;
	}
	
	
}
