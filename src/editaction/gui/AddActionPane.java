package editaction.gui;

import control.Control;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import setup.Constant;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;


/**
 * The Class AddActionPane uses for adding new action to current node in game tree
 */
public class AddActionPane extends VBox{
	

	/**
	 * Instantiates a new adds the action pane.
	 */
	public AddActionPane() {
		TextField textCombosField = new TextField("Text Combos");
		TextField actionSizeField = new TextField("Raise to (X for X/Call)");
		Button addActionButton = new Button("Add Action");
		
		addActionButton.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				String textRangeInput = textCombosField.getText();
				String actionSizeInput = actionSizeField.getText();
				
				Control.addAction(textRangeInput, actionSizeInput);				
			}
		});		
		
		//GUI Styling
		this.getChildren().addAll(textCombosField, actionSizeField, addActionButton);
		
		textCombosField.setMaxSize(Constant.ADD_ACTION_TEXT_FIELD_WIDTH, Constant.ADD_ACTION_TEXT_FIELD_HEIGHT);
		textCombosField.setStyle("-fx-background-color: rgba(255, 255, 255, 0.2);  -fx-text-fill: white;");
		textCombosField.setAlignment(Pos.CENTER);
		
		actionSizeField.setMaxSize(Constant.ADD_ACTION_TEXT_FIELD_WIDTH, Constant.ADD_ACTION_TEXT_FIELD_HEIGHT);		
		actionSizeField.setStyle("-fx-background-color: rgba(255, 255, 255, 0.2);  -fx-text-fill: white;");
		actionSizeField.setAlignment(Pos.CENTER);
		
		addActionButton.setMaxSize(Constant.BUTTON_WIDTH, Constant.BUTTON_HEIGHT);
		addActionButton.setStyle("-fx-background-color: #FFA000; -fx-background-radius: 10 10 10 10; -fx-font-weight: bold; -fx-font-size: 14px;");
		addActionButton.setOnMouseExited(e -> addActionButton.setStyle("-fx-background-color: #FFA000; -fx-background-radius: 10 10 10 10; -fx-font-weight: bold; -fx-font-size: 14px;"));
		addActionButton.setOnMouseEntered(e -> addActionButton.setStyle("-fx-background-color: #FFC107; -fx-background-radius: 10 10 10 10; -fx-font-weight: bold; -fx-font-size: 14px;"));		
		
		this.setClearTextWhenClick(textCombosField);
		this.setClearTextWhenClick(actionSizeField);
		this.setSpacing(Constant.SPACE);
		this.setAlignment(Pos.CENTER);		
		
	}	
	

	/**
	 * Sets the clear text when click.
	 *
	 * @param textField the new clear text when click
	 */
	private void setClearTextWhenClick(TextField textField) {
		
		textField.setOnMousePressed(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				textField.clear();
			}
		});		
	
	}
}
