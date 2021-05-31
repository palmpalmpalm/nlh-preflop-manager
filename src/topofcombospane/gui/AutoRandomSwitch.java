package topofcombospane.gui;

import control.Control;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import setup.Constant;

/**
 * The Class WeightSwitch is a switch for weight mode.
 */
public class AutoRandomSwitch extends Button{
	
	/**
	 * Instantiates a new weight switch.
	 */
	public AutoRandomSwitch() {
		super();
		if (Control.isAutoRandomMode()) {
			this.setText("Random: ON");
			
			// GUI Styling
			this.setStyle("-fx-background-color: #673ab7; -fx-background-radius: 5 5 5 5; -fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: white;");
			this.setOnMouseExited(e -> this.setStyle("-fx-background-color: #673ab7; -fx-background-radius: 5 5 5 5; -fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: white;"));
			this.setOnMouseEntered(e -> this.setStyle("-fx-background-color: #7149ba; -fx-background-radius: 5 5 5 5; -fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: white;"));		
			
		}else {
			this.setText("Random: OFF");
			
			// GUI Styling
			this.setStyle("-fx-background-color: #442f6b; -fx-background-radius: 5 5 5 5; -fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: white;");
			this.setOnMouseExited(e -> this.setStyle("-fx-background-color: #442f6b; -fx-background-radius: 5 5 5 5; -fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: white;"));
			this.setOnMouseEntered(e -> this.setStyle("-fx-background-color: #5f4394; -fx-background-radius: 5 5 5 5; -fx-font-weight: bold; -fx-font-size: 14px; -fx-text-fill: white;"));		
		}
		this.setMaxSize(Constant.WEIGHT_WIDTH , Constant.WEIGHT_HEIGHT);
		this.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (Control.isAutoRandomMode()) {
					Control.turnOffAutoRandoMode();
				
				}else {		
					Control.turnOnAutoRandomMode();
				}
			}
		});
	}
}
