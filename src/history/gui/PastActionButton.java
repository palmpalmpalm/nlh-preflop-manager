package history.gui;

import actionnode.CheckCallNode;
import actionnode.FoldNode;
import actionnode.RaiseNode;
import actionnode.base.ActionNode;
import control.Control;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

// TODO: Auto-generated Javadoc
/**
 * The Class PasActionButton is the button of action that use in history pane
 * you can click to go back to the previous action
 */
public class PastActionButton extends Button{
	

	/**
	 * Instantiates a new past action button.
	 *
	 * @param action the action
	 * @param buttonNumber the button number
	 */
	public PastActionButton(ActionNode action, int buttonNumber) {
		super();
		String buttonInfo;
		int actionNumber = buttonNumber;
		if (action instanceof RaiseNode) {
			buttonInfo = "➥" + action.getPosition() + " Raise " + String.valueOf(action.getToCall() + "BB");
		} else if (action instanceof CheckCallNode) {
			buttonInfo = "➥" + action.getPosition() + " Check/Call " + String.valueOf(action.getToCall() + "BB");
		} else if (action instanceof FoldNode){ 
			buttonInfo = "➥" + action.getPosition() + " Fold ";
		} else { 
			buttonInfo = "➥" + "Blinds";
		}
		this.setText(buttonInfo);
		this.setClick(action, actionNumber);
		
		//GUI Styling
		this.setStyle("-fx-font-weight: bold; -fx-font-size: 12px; -fx-background-color: rgba(255, 255, 255, 0.5); -fx-background-radius: 5 5 5 5;");
		this.setOnMouseExited(e -> PastActionButton.this.setStyle("-fx-background-color: rgba(255, 255, 255, 0.5); -fx-background-radius: 5 5 5 5; -fx-font-weight: bold; -fx-font-size: 12px;"));
		this.setOnMouseEntered(e -> PastActionButton.this.setStyle("-fx-background-color: rgba(255, 255, 255, 0.25); -fx-background-radius: 5 5 5 5; -fx-font-weight: bold; -fx-font-size: 12px;"));		
	}
	

	/**
	 * Sets the click.
	 *
	 * @param action the action
	 * @param actionNumber the action number
	 */
	private void setClick(ActionNode action, int actionNumber) {
		this.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {		
				Control.goBackAction(action, actionNumber);				
			}			
		});
	}
}
