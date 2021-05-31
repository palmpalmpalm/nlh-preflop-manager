package control;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import actionnode.BlindNode;
import actionnode.base.ActionNode;
import alert.gui.AlertPopUp;
import combos.gui.CombosPane;
import container.CurrentCombos;
import editaction.gui.EditActionPane;
import header.gui.NewTreePane;
import header.gui.SaveLoadPane;
import history.gui.HistoryPane;
import hotkeys.gui.HotKeysPane;
import hotkeys.gui.NodePointer;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import logic.GameTreeUtility;
import nextaction.gui.ActionButton;
import nextaction.gui.NextActionPane;
import setup.Constant;
import setup.Setting;
import topofcombospane.gui.TopOfCombosPane;


// TODO: Auto-generated Javadoc
/**
 * The Class Control is controller of the application. 
 */
public class Control {
	
	/** The root. */
	public static ActionNode root;
	
	/** The delete mode. */
	private static boolean deleteMode = false;
	
	/** The weight mode. */
	private static boolean weightMode = false;
	
	/** The auto random mode. */
	private static boolean autoRandomMode = false;
	
	/** The curr action. */
	private static ActionNode currAction;
	
	/** The hot keys pane. */
	private static HotKeysPane hotKeysPane = new HotKeysPane();
	
	/** The app pane. */
	private static BorderPane appPane;
	
	/** The history list. */
	private static ArrayList<ActionNode> historyList = new ArrayList<ActionNode>();
	
	/** The drag board. */
	private static Dragboard db;
	
	/** The dragging button. */
	private static ActionButton draggingButton;
	
	/** The rng. */
	private static Text RNG = new Text("RNG: 0");

	/**
	 * Sets the root.
	 *
	 * @param actionNode the new root
	 */
	public static void setRoot(ActionNode actionNode) {
		Control.root = actionNode;
		Control.historyList = new ArrayList<ActionNode>();
		Control.setCurrActionNode(Control.root);
	}

	/**
	 * Sets the app pane.
	 *
	 * @param appPane the new app pane
	 */
	public static void setAppPane(BorderPane appPane) {
		Control.appPane = appPane;

	}

	/**
	 * Sets the curr action node.
	 *
	 * @param action the new curr action node
	 */
	public static void setCurrActionNode(ActionNode action) {
		Control.currAction = action;
		Control.historyList.add(Control.currAction);
	}

	/**
	 * Sets the up pane.
	 */
	public static void setUpPane() {
		Control.appPane.getChildren().clear();
		Control.setMainPane();
		Control.setHistoryPane();
		Control.setHotKeysPane();
		Control.setHeader();

		Control.appPane.setStyle("-fx-background-color: #212121;");
	}


	/**
	 * Sets the rng.
	 *
	 * @param number the new rng
	 */
	public static void setRNG(int number) {
		Control.RNG.setText("RNG: " + String.valueOf(number));
	}


	/**
	 * Sets the header.
	 */
	public static void setHeader() {
		BorderPane header = new BorderPane();
		VBox headerBox = new VBox();
		SaveLoadPane saveLoadPane = new SaveLoadPane();
		NewTreePane newTreePane = new NewTreePane();
		VBox appNameBox = new VBox();
		Text appName = new Text("NLH PREFLOP MANAGER");

		saveLoadPane.setAlignment(Pos.CENTER_RIGHT);
		newTreePane.setAlignment(Pos.CENTER_RIGHT);
		headerBox.getChildren().addAll(newTreePane, saveLoadPane);

		appNameBox.getChildren().add(appName);

		header.setLeft(appNameBox);
		header.setRight(headerBox);

		Control.appPane.setTop(header);

		//GUI Styling
		Control.appPane.setMargin(header, new Insets(0, 0, 5, 0));
		appName.setStyle("-fx-font-size: 37; -fx-fill: white; -fx-font-weight: bold;");
		appNameBox.setAlignment(Pos.CENTER);
		headerBox.setSpacing(Constant.SPACE);
		header.setPadding(new Insets(10, 10, 10, 10));
		header.setPrefHeight(Constant.HEADER_HEIGHT);
		header.setStyle("-fx-background-color: rgba(255, 255, 255, 0.07);");

	}


	/**
	 * Sets the hot keys pane.
	 */
	public static void setHotKeysPane() {
		Control.appPane.setLeft(Control.hotKeysPane);
		Control.appPane.setMargin(Control.hotKeysPane, new Insets(10, 10, 10, 10));
	}


	/**
	 * Sets the history pane.
	 */
	public static void setHistoryPane() {
		HistoryPane historyPane = new HistoryPane(Control.historyList);
		Control.appPane.setRight(historyPane);

		
		Control.appPane.setMargin(historyPane, new Insets(10, 10, 10, 10));
	}

	/**
	 * Sets the main pane.
	 */
	public static void setMainPane() {
		NextActionPane nextActionPane = new NextActionPane(currAction.getNextActions());
		EditActionPane editActionPane = new EditActionPane();
		VBox actionPane = new VBox();
		VBox combosPane = new VBox();
		VBox RNGBox = new VBox();
		HBox mainPane = new HBox();
		TopOfCombosPane topOfCombosPane = new TopOfCombosPane();
		CombosPane combos = new CombosPane(currAction.getNextActions(), Control.weightMode, Control.autoRandomMode);

		actionPane.getChildren().addAll(nextActionPane, editActionPane);
		RNGBox.getChildren().add(Control.RNG);
		//topOfCombosPane.setRight(RNGBox);
		combosPane.getChildren().addAll(topOfCombosPane, combos);
		mainPane.getChildren().addAll(actionPane, combosPane);
		Control.appPane.setCenter(mainPane);

		// GUI Styling
		actionPane.setAlignment(Pos.CENTER);
		actionPane.setSpacing(Constant.BIG_SPACE);
		RNGBox.setAlignment(Pos.CENTER);
		combosPane.setAlignment(Pos.CENTER);
		combosPane.setSpacing(5);
		mainPane.setAlignment(Pos.CENTER);
		mainPane.setSpacing(Constant.BIG_SPACE);
		Control.appPane.setMargin(mainPane, new Insets(0, 0, 5, 0));
		Control.RNG.setStyle("-fx-font-size: 12px; -fx-fill: white; -fx-font-weight: bold;");
	}

	/**
	 * Sets the weight mode.
	 *
	 * @param weightMode the new weight mode
	 */
	public static void setWeightMode(boolean weightMode) {
		Control.weightMode = weightMode;
	}

	/**
	 * Turn on weight mode.
	 */
	public static void turnOnWeightMode() {
		Control.setWeightMode(true);
		System.out.println("Weight Mode: ON");
		Control.setUpPane();
	}

	/**
	 * Turn off weight mode.
	 */
	public static void turnOffWeightMode() {
		Control.setWeightMode(false);
		System.out.println("Weight Mode: OFF");
		Control.setUpPane();
	}
	
	/**
	 * Turn on auto random mode.
	 */
	public static void turnOnAutoRandomMode() {
		Control.setAutoRandomMode(true);
		System.out.println("Auto Random: ON");
		Control.setUpPane();
	}
	
	/**
	 * Turn off auto rando mode.
	 */
	public static void turnOffAutoRandoMode() {
		Control.setAutoRandomMode(false);
		System.out.println("Auto Random: OFF");
		Control.setUpPane();
	}
	
	/**
	 * Turn on delete mode.
	 */
	public static void turnOnDeleteMode() {
		Control.deleteMode = true;
		System.out.println("Delete Mode: ON");
	}

	/**
	 * Turn off delete mode.
	 */
	public static void turnOffDeleteMode() {
		Control.deleteMode = false;
		System.out.println("Delete Mode: OFF");
	}


	/**
	 * Checks if is weight mode.
	 *
	 * @return true, if is weight mode
	 */
	public static boolean isWeightMode() {
		return Control.weightMode;
	}


	/**
	 * Delete action.
	 *
	 * @param actionNumber the action number
	 */
	public static void deleteAction(int actionNumber) {
		Control.currAction.deleteAction(actionNumber);
		Control.setUpPane();
	}


	/**
	 * Checks if is delete mode on.
	 *
	 * @return true, if is delete mode on
	 */
	public static boolean isDeleteModeOn() {
		return Control.deleteMode;
	}


	/**
	 * Adds the action.
	 *
	 * @param textRange the text range
	 * @param actionSize the action size
	 */
	public static void addAction(String textRange, String actionSize) {
		try {
			if (actionSize.equals("X")) {
				GameTreeUtility.addCheckCall(new CurrentCombos(textRange), Control.currAction);
				GameTreeUtility.genFoldAction();
			} else {
				GameTreeUtility.addRaise(new CurrentCombos(textRange), Double.valueOf(actionSize), Control.currAction);
				GameTreeUtility.genFoldAction();
			}
			Control.setUpPane();
		} catch (Exception exception) {
			System.out.println("Invalid Input");
			AlertPopUp alert = new AlertPopUp("Error", "Invalid Input");
			alert.showAndWait();
		}
	}


	/**
	 * Go back action.
	 *
	 * @param actionNode the action node
	 * @param actionNumber the action number
	 */
	public static void goBackAction(ActionNode actionNode, int actionNumber) {
		int listSize = Control.historyList.size();
		for (int i = listSize - 1; i >= actionNumber; i--) {
			Control.historyList.remove(i);
		}
		Control.setCurrActionNode(actionNode);
		Control.setUpPane();
	}


	/**
	 * Sets the drag action.
	 *
	 * @param actionButton the new drag action
	 */
	public static void setDragAction(ActionButton actionButton) {
		Control.db = actionButton.startDragAndDrop(TransferMode.COPY);

		ClipboardContent content = new ClipboardContent();
		content.putString(String.valueOf(actionButton.getAction()));
		Control.db.setContent(content);
		Control.draggingButton = actionButton;
	}


	/**
	 * Checks if is db has content and not same pane.
	 *
	 * @param pane the pane
	 * @return true, if is db has content and not same pane
	 */
	public static boolean isDbHasContentAndNotSamePane(VBox pane) {
		if (Control.db != null && Control.db.hasString() && Control.draggingButton != null
				&& Control.draggingButton.getParent() != pane) {
			return true;
		} else {
			return false;
		}
	}


	/**
	 * Gets the dragging button.
	 *
	 * @return the dragging button
	 */
	public static ActionButton getDraggingButton() {
		return Control.draggingButton;
	}


	/**
	 * Sets the dragging button to null.
	 */
	public static void setDraggingButtonToNull() {
		Control.draggingButton = null;
	}


	/**
	 * Gets the hot keys pane.
	 *
	 * @return the hot keys pane
	 */
	public static HotKeysPane getHotKeysPane() {
		return hotKeysPane;
	}


	/**
	 * Gets the history list.
	 *
	 * @return the history list
	 */
	public static ArrayList<ActionNode> getHistoryList() {
		return Control.historyList;
	}


	/**
	 * Sets the history list.
	 *
	 * @param pastHistoryList the new history list
	 */
	public static void setHistoryList(ArrayList<NodePointer> pastHistoryList) {
		Control.historyList = new ArrayList<ActionNode>();
		for (NodePointer nodePointer : pastHistoryList) {
			Control.historyList.add(nodePointer.getNode());
		}
	}

	/**
	 * Load.
	 *
	 * @param fileName the file name
	 */	
	public static void load(String fileName) {
		File f = new File(fileName + ".txt");
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
			SaveLoadObject loadedPreflop = (SaveLoadObject) ois.readObject();
			ois.close();
			Control.setRoot(loadedPreflop.getRoot());
			Setting.setCard(loadedPreflop.getCard());
			Setting.setPosition(loadedPreflop.getPosition());
			Setting.setStartingStack(loadedPreflop.getStartingStack());
			Control.setHotKeysPane(new HotKeysPane());
			Control.setUpPane();
			Control.addRFIkeys();
			System.out.println("loaded file");
			AlertPopUp alert = new AlertPopUp("Success", "Loaded tree");
			alert.showAndWait();
		} catch (Exception e) {
		
			System.out.println("load file error");
			AlertPopUp alert = new AlertPopUp("Error", "Load file failed");
			alert.showAndWait();
		}
	}

	/**
	 * Save.
	 *
	 * @param fileName the file name
	 */
	public static void save(String fileName) {
		File f = new File(fileName + ".txt");
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
			SaveLoadObject savePreflop = new SaveLoadObject(Control.root, Setting.getStartingStack(), Setting.getPosition(), Setting.getCard());
			oos.writeObject(savePreflop);
			oos.flush();
			oos.close();
			System.out.println("save file done");
			AlertPopUp alert = new AlertPopUp("Success", "Saved tree");
			alert.showAndWait();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("save file error");
			AlertPopUp alert = new AlertPopUp("Error", "Save file failed");
			alert.showAndWait();
		}
	}

	/**
	 * Creates the new preflop.
	 *
	 * @param startingStack the starting stack
	 * @param numberOfPlayers the number of players
	 */
	public static void createNewPreflop(double startingStack, int numberOfPlayers) {
		Setting.setStartingStack(startingStack);
		Setting.setPositionByNumber(numberOfPlayers);
		ActionNode newTree = new BlindNode();
		GameTreeUtility.addQue(newTree);
		GameTreeUtility.genFoldAction();
		Control.setHotKeysPane(new HotKeysPane());
		Control.setRoot(newTree);
		Control.setUpPane();
		Control.addRFIkeys();

	}

	/**
	 * Sets the hot keys pane.
	 *
	 * @param hotKeysPane the new hot keys pane
	 */
	public static void setHotKeysPane(HotKeysPane hotKeysPane) {
		Control.hotKeysPane = hotKeysPane;
	}
	
	/**
	 * Gets the root.
	 *
	 * @return the root
	 */
	public static ActionNode getRoot() {
		return root;
	}

	/**
	 * Gets the curr action.
	 *
	 * @return the curr action
	 */
	public static ActionNode getCurrAction() {
		return currAction;
	}

	/**
	 * Sets the curr action.
	 *
	 * @param currAction the new curr action
	 */
	public static void setCurrAction(ActionNode currAction) {
		Control.currAction = currAction;
	}

	
	/**
	 * Fold to input position.
	 *
	 * @param pos the pos
	 */
	public static void foldTo(String pos) {
		while (Control.currAction.getPosition() != pos) {
			 
			int foldActionNumber = Control.currAction.getNextActions().size() - 1;
			if (Control.currAction.getNextActions().get(foldActionNumber).getPosition() == pos) {
				break;
			}		
			Control.setCurrActionNode(Control.currAction.getNextActions().get(foldActionNumber));
		}
		Control.setUpPane();
	}
	
	/**
	 * Checks if is auto random mode.
	 *
	 * @return true, if is auto random mode
	 */
	public static boolean isAutoRandomMode() {
		return autoRandomMode;
	}

	/**
	 * Sets the auto random mode.
	 *
	 * @param autoRandomMode the new auto random mode
	 */
	public static void setAutoRandomMode(boolean autoRandomMode) {
		Control.autoRandomMode = autoRandomMode;
	}
	
	
	/**
	 * Adds the RFI keys.
	 */
	public static void addRFIkeys() {
		for (int i = 0 ; i < Setting.getPosition().size() - 1; i++) {
			
			int foldActionNumber = Control.currAction.getNextActions().size() - 1;	
			Control.hotKeysPane.addRFIButton(Control.currAction, Control.historyList, Setting.getPosition().get(i) + " RFI");
			Control.setCurrActionNode(Control.currAction.getNextActions().get(foldActionNumber));
		}
		Control.setRoot(Control.root);
		Control.setUpPane();
	}

}
