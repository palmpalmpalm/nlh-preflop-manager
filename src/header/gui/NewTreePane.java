package header.gui;

import javafx.scene.control.TextField;
import alert.gui.AlertPopUp;
import control.Control;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import nextaction.gui.ActionButton;
import setup.Constant;
import setup.Setting;


/**
 * The Class NewTreePane uses to create new game tree with new setting
 * you can cutom game tree by 
 * -number of players
 * -stack size
 */
public class NewTreePane extends HBox{

	/**
	 * Instantiates a new new tree pane.
	 */
	public NewTreePane() {
		super();
		TextField startingStackField = new TextField("Stack Size");
		ComboBox numberOfPlayersBox = new ComboBox();
		Text numberOfPlayersText = new Text("Number of Players: ");
		Button newTreeButton = new Button("New Tree");

		for (int i = 0 ;i < Setting.getNumberofplayerlist().size(); i++) {
			numberOfPlayersBox.getItems().add(Setting.getNumberofplayerlist().get(i));
		}		
		
		this.getChildren().addAll(startingStackField, numberOfPlayersText, numberOfPlayersBox, newTreeButton);
		
		//GUI Styling
		numberOfPlayersBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.2);  -fx-prompt-text-fill: white; -fx-background-radius: 5 5 5 5");
		numberOfPlayersText.setStyle("-fx-font-size: 12px; -fx-fill: white; ");
		startingStackField.setStyle("-fx-background-color: rgba(255, 255, 255, 0.2);  -fx-text-fill: white; -fx-background-radius: 5 5 5 5");
		newTreeButton.setStyle(
				"-fx-background-color: rgba(255, 255, 255, 0.15); -fx-background-radius: 5 5 5 5; -fx-font-size: 12px; -fx-text-fill: white;");
		newTreeButton.setOnMouseExited(e -> newTreeButton.setStyle(
				"-fx-background-color: rgba(255, 255, 255, 0.15); -fx-background-radius: 5 5 5 5;  -fx-font-size: 12px; -fx-text-fill: white;"));
		newTreeButton.setOnMouseEntered(e -> newTreeButton.setStyle(
				"-fx-background-color: rgba(255, 255, 255, 0.3); -fx-background-radius: 5 5 5 5;  -fx-font-size: 12px; -fx-text-fill: white;"));
		startingStackField.setPrefSize(Constant.TEXTFIELD_WIDTH, Constant.TEXTFIELD_HEIGHT);
		startingStackField.setAlignment(Pos.CENTER);
		
		this.setSpacing(Constant.SPACE);
		this.setClearTextWhenClick(startingStackField);
		this.setAlignment(Pos.CENTER);
		
		// click new tree button will create new game tree
		newTreeButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					String startingStack = startingStackField.getText();
					String numberOfPlayers = (String) numberOfPlayersBox.getValue();
					Control.createNewPreflop(Double.valueOf(startingStack), Integer.parseInt(numberOfPlayers));
				}catch(Exception e){
					System.out.println("Invalid Input");
					AlertPopUp alert  = new AlertPopUp("Error", "Invalid Input");
					alert.showAndWait();
				}
				
			}			
		});
	}
	

	/**
	 * Sets the clear text when click.
	 *
	 * @param textField the new clear text when click
	 */
	public void setClearTextWhenClick(TextField textField) {
		textField.setOnMousePressed(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				textField.clear();
			}
		});		
		
	}

}
