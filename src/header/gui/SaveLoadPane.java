package header.gui;

import control.Control;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import setup.Constant;

/**
 * The Class SaveLoadPane.
 */
public class SaveLoadPane extends HBox {


	/**
	 * Instantiates a new save load pane.
	 */
	public SaveLoadPane() {
		super();
		TextField fileNameField = new TextField("filename");		
		Button loadButton = new Button("Load");
		Button saveButton = new Button("Save");
		
		this.getChildren().addAll(fileNameField, loadButton, saveButton);
		
		// GUI Styling
		this.setSpacing(Constant.SPACE);		
		this.setClearTextWhenClick(fileNameField);
		fileNameField.setAlignment(Pos.CENTER);
		fileNameField.setPrefSize(Constant.TEXTFIELD_WIDTH, Constant.TEXTFIELD_HEIGHT);
		loadButton.setPrefSize(Constant.LOAD_BUTTON_WIDTH, Constant.BUTTON_HEIGHT);
		saveButton.setPrefSize(Constant.SAVE_BUTTON_WIDTH, Constant.BUTTON_HEIGHT);				
		fileNameField.setStyle("-fx-background-color: rgba(255, 255, 255, 0.2);  -fx-text-fill: white; -fx-background-radius: 5 5 5 5");		
		loadButton.setStyle(
				"-fx-background-color: rgba(255, 255, 255, 0.15); -fx-background-radius: 5 5 5 5; -fx-font-size: 12px; -fx-text-fill: white;");
		loadButton.setOnMouseExited(e -> loadButton.setStyle(
				"-fx-background-color: rgba(255, 255, 255, 0.15); -fx-background-radius: 5 5 5 5;  -fx-font-size: 12px; -fx-text-fill: white;"));
		loadButton.setOnMouseEntered(e -> loadButton.setStyle(
				"-fx-background-color: rgba(255, 255, 255, 0.3); -fx-background-radius: 5 5 5 5;  -fx-font-size: 12px; -fx-text-fill: white;"));		
		saveButton.setStyle(
				"-fx-background-color: rgba(255, 255, 255, 0.15); -fx-background-radius: 5 5 5 5; -fx-font-size: 12px; -fx-text-fill: white;");
		saveButton.setOnMouseExited(e -> saveButton.setStyle(
				"-fx-background-color: rgba(255, 255, 255, 0.15); -fx-background-radius: 5 5 5 5;  -fx-font-size: 12px; -fx-text-fill: white;"));
		saveButton.setOnMouseEntered(e -> saveButton.setStyle(
				"-fx-background-color: rgba(255, 255, 255, 0.3); -fx-background-radius: 5 5 5 5;  -fx-font-size: 12px; -fx-text-fill: white;"));
		

		loadButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String fileName = fileNameField.getText();
				Control.load(fileName);
			}
		});


		saveButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String fileName = fileNameField.getText();
				Control.save(fileName);
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
