package hotkeys.gui;

import java.util.ArrayList;
import java.util.Optional;

import actionnode.FoldNode;
import actionnode.base.ActionNode;
import control.Control;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import setup.Constant;

/**
 * HotkeysButton you can drag and drop action from current action to make a hot key.
 */
public class RFIButton extends Button{

	/** The history list. */
	private ArrayList<NodePointer> historyList;
	
	/** The action. */
	private ActionNode action;


	/**
	 * Instantiates a new hot keys button.
	 *
	 * @param action the action
	 * @param historyList the history list
	 */
	public RFIButton(ActionNode action, ArrayList<ActionNode> historyList, String buttonInfo) {
		super();
		this.setAction(action);
		ArrayList<NodePointer> historyPointer  = new ArrayList<NodePointer>();
		for (int i = 0; i < historyList.size(); i++) {
			historyPointer .add(new NodePointer(historyList.get(i)));
		}
		this.setHistoryList(historyPointer);	
		this.setClick();
		
		// GUI Styling
		this.setPrefSize(Constant.HOTKEYS_BUTTON_WIDTH, Constant.HOTKEYS_BUTTON_HEIGHT);
		this.setStyle("-fx-background-color: #303f9f; -fx-background-radius: 5 5 5 5; -fx-font-weight: bold; -fx-font-size: 12px; -fx-text-fill: white;");
		this.setOnMouseExited(e -> this.setStyle("-fx-background-color: #303f9f; -fx-background-radius: 5 5 5 5; -fx-font-weight: bold; -fx-font-size: 12px; -fx-text-fill: white;"));
		this.setOnMouseEntered(e -> this.setStyle("-fx-background-color: #49549e; -fx-background-radius: 5 5 5 5; -fx-font-weight: bold; -fx-font-size: 12px; -fx-text-fill: white;"));		
		this.setText(buttonInfo);
		
		
	}
	
	/**
	 * Sets the click.
	 */
	private void setClick() {
		this.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (Control.isDeleteModeOn()) {
					((Pane)RFIButton.this.getParent()).getChildren().remove(RFIButton.this);
				}else {
					if (RFIButton.this.action.getNextActions().size() == 0) {
						System.out.println("end game");
						RFIButton.this.action.showAvaPos();
					} else {						
						Control.setHistoryList(RFIButton.this.historyList);
						Control.setCurrActionNode(RFIButton.this.action);
						Control.getHistoryList().remove(Control.getHistoryList().size() - 1);
						Control.setUpPane();
					}
				}
			}
			
		});
	}
	
	/**
	 * Sets the history list.
	 *
	 * @param historyList the new history list
	 */
	public void setHistoryList(ArrayList<NodePointer> historyList) {
		this.historyList = historyList;
	}

	/**
	 * Sets the action.
	 *
	 * @param action the new action
	 */
	public void setAction(ActionNode action) {
		this.action = action;
	}
	
	/**
	 * Gets the history list.
	 *
	 * @return the history list
	 */
	public ArrayList<NodePointer> getHistoryList() {
		return historyList;
	}

	/**
	 * Gets the action.
	 *
	 * @return the action
	 */
	public ActionNode getAction() {
		return action;
	}

	/**
	 * Pop up text input.
	 *
	 * @return the string
	 */
	private String popUpTextInput() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setHeaderText(null);
		dialog.setTitle("Hot Key name input");
		dialog.setContentText("Enter your Hot Key name:");
		String name = "";
		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()){
			name = result.get();
		}

		// The Java 8 way to get the response value (with lambda expression).
		return name;
	}
	
}
