package editaction.gui;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import setup.Constant;

/**
 * The Class EditActionPane uses to delete/add action to the game tree.
 */
public class EditActionPane extends VBox{
	
	/**
	 * Instantiates a new edits the action pane.
	 */
	public EditActionPane() {
		Text editActionText = new Text("Edit Action");		
		Text orText = new Text("or");
			
		this.getChildren().add(editActionText);
		this.getChildren().add(new DeleteActionButton());
		this.getChildren().add(orText);
		this.getChildren().add(new AddActionPane());
		
		// GUI Styling
		editActionText.setStyle("-fx-font-size: 14px; -fx-fill: white;");
		orText.setStyle("-fx-font-size: 14px; -fx-fill: white;");
		this.setSpacing(Constant.SPACE);
		this.setPrefSize(Constant.EDIT_ACTION_PANE_WIDTH, Constant.EDIT_ACTION_PANE_HEIGHT);
		this.setAlignment(Pos.CENTER);
		this.setStyle("-fx-background-radius: 10 10 10 10; -fx-background-color: #303030;");
	}
}
