package editaction.gui;

import control.Control;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import setup.Constant;

/**
 * The Class DeleteActionButton uses to delete action from current action node in game tree
 */
public class DeleteActionButton extends Button {

	/**
	 * Instantiates a new delete action button.
	 */
	public DeleteActionButton() {
		super();
		if (Control.isDeleteModeOn()) {
			this.setText("Delete Mode: ON"); 
			this.setStyle("-fx-background-color: #751919; -fx-background-radius: 10 10 10 10; -fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: white;");
			
		}else {
			this.setText("Delete Mode: OFF");
			this.setStyle("-fx-background-color: #ba3030; -fx-background-radius: 10 10 10 10; -fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: white;");
			this.setOnMouseExited(e -> this.setStyle("-fx-background-color: #ba3030; -fx-background-radius: 10 10 10 10; -fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: white;"));
			this.setOnMouseEntered(e -> this.setStyle("-fx-background-color: #ba5252; -fx-background-radius: 10 10 10 10; -fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: white;"));		
		}
		
		this.setMaxSize(Constant.BUTTON_WIDTH, Constant.BUTTON_HEIGHT);				
		
		this.setOnAction(new EventHandler<ActionEvent>() {

			// switch delete mode when click
			@Override
			public void handle(ActionEvent event) {
				if (Control.isDeleteModeOn()) {
					DeleteActionButton.this.setText("Delete Mode: OFF");
					Control.turnOffDeleteMode();
					
					DeleteActionButton.this.setStyle("-fx-background-color: #ba3030; -fx-background-radius: 10 10 10 10; -fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: white;");
					DeleteActionButton.this.setOnMouseExited(e -> DeleteActionButton.this.setStyle("-fx-background-color: #ba3030; -fx-background-radius: 10 10 10 10; -fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: white;"));
					DeleteActionButton.this.setOnMouseEntered(e -> DeleteActionButton.this.setStyle("-fx-background-color: #ba5252; -fx-background-radius: 10 10 10 10; -fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: white;"));		
				
				}else {
					setText("Delete Mode: ON");
					Control.turnOnDeleteMode();
					
					DeleteActionButton.this.setText("Delete Mode: ON"); 
					DeleteActionButton.this.setStyle("-fx-background-color: #751919; -fx-background-radius: 10 10 10 10; -fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: white;");
					DeleteActionButton.this.setOnMouseExited(null);
					DeleteActionButton.this.setOnMouseEntered(null);
				}
			}
		});
	}
}
