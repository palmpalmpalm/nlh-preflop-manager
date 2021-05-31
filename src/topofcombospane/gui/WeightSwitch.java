package topofcombospane.gui;

import control.Control;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import setup.Constant;

/**
 * The Class WeightSwitch is a switch for weight mode.
 */
public class WeightSwitch extends Button{
	
	/**
	 * Instantiates a new weight switch.
	 */
	public WeightSwitch() {
		super();
		if (Control.isWeightMode()) {
			this.setText("Weight Mode");
			
			// GUI Styling
			this.setStyle("-fx-background-color: #27ae60; -fx-background-radius: 5 5 5 5; -fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: white;");
			this.setOnMouseExited(e -> this.setStyle("-fx-background-color: #27ae60; -fx-background-radius: 5 5 5 5; -fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: white;"));
			this.setOnMouseEntered(e -> this.setStyle("-fx-background-color: #2ecc71; -fx-background-radius: 5 5 5 5; -fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: white;"));		
		
			
		}else {
			this.setText("Absolute Mode");
			
			// GUI Styling
			this.setStyle("-fx-background-color: #16a085; -fx-background-radius: 5 5 5 5; -fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: white;");
			this.setOnMouseExited(e -> this.setStyle("-fx-background-color: #16a085; -fx-background-radius: 5 5 5 5; -fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: white;"));
			this.setOnMouseEntered(e -> this.setStyle("-fx-background-color: #1abc9c; -fx-background-radius: 5 5 5 5; -fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: white;"));		
		
		}
		this.setMaxSize(Constant.WEIGHT_WIDTH , Constant.WEIGHT_HEIGHT);
		this.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (Control.isWeightMode()) {
					WeightSwitch.this.setText("Weight Mode");
					Control.turnOffWeightMode();
				
				}else {
					WeightSwitch.this.setText("Absolute Mode");
					Control.turnOnWeightMode();					
				
				}
			}
		});
	}
}
