package nextaction.gui;

import actionnode.CheckCallNode;
import actionnode.FoldNode;
import actionnode.RaiseNode;
import actionnode.base.ActionNode;
import alert.gui.AlertPopUp;
import control.Control;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import setup.Constant;

/**
 * The Class ActionButton is a button for current position to choose next action.
 */
public class ActionButton extends Button {
	
	/** The action. */
	private ActionNode action;

	/**
	 * Instantiates a new action button.
	 *
	 * @param action the action
	 * @param buttonNumber the button number
	 */
	public ActionButton(ActionNode action, int buttonNumber) {
		super();
		this.action = action;
		String buttonInfo;
		int actionNumber = buttonNumber;
		if (action instanceof RaiseNode) {
			buttonInfo = action.getPosition() + " Raise to " + String.valueOf(action.getToCall());
		} else if (action instanceof CheckCallNode) {
			buttonInfo = action.getPosition() + " Check/Call " + String.valueOf(action.getToCall());
		} else { // FoldNode
			buttonInfo = action.getPosition() + " Fold ";
		}
		
		
		this.setText(buttonInfo);
		this.setClick(actionNumber);
		this.setDragToHotKeys();
		
		// GUI Styling
		this.setPrefSize(Constant.ACTION_BUTTON_WIDTH, Constant.ACTION_BUTTON_HEIGHT);		
		this.setStyle(
				"-fx-background-color: rgba(255, 255, 255, 0.3); -fx-background-radius: 15 15 15 15; -fx-font-size: 12px; -fx-text-fill: white; -fx-font-weight: bold;");
		this.setOnMouseExited(e -> ActionButton.this.setStyle(
				"-fx-background-color: rgba(255, 255, 255, 0.3); -fx-background-radius: 15 15 15 15;  -fx-font-size: 12px; -fx-text-fill: white; -fx-font-weight: bold;"));
		this.setOnMouseEntered(e -> ActionButton.this.setStyle(
				"-fx-background-color: rgba(255, 255, 255, 0.15); -fx-background-radius: 15 15 15 15;  -fx-font-size: 12px; -fx-text-fill: white; -fx-font-weight: bold;"));

	}

	/**
	 * Sets the click.
	 *
	 * @param actionNumber the new click
	 */
	private void setClick(int actionNumber) {
		this.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (Control.isDeleteModeOn()) {
					if (!(ActionButton.this.action instanceof FoldNode)) {
						Control.deleteAction(actionNumber);
					}
				} else {
					if (ActionButton.this.action.getNextActions().size() == 0) {
						System.out.println("end game");
						AlertPopUp alert = new AlertPopUp("End Game", "End of Preflop Action");
						alert.showAndWait();
						ActionButton.this.action.showAvaPos();
					} else {
						if (ActionButton.this.action instanceof FoldNode) { // Free Memory
							ActionButton.this.action.getEachPosWeight()
									.setPosStackToNull(ActionButton.this.action.getPosition());
						}
						Control.setCurrActionNode(ActionButton.this.action);
						Control.setUpPane();
					}
				}
			}
		});

	}

	/**
	 * Sets the drag to hot keys.
	 */
	private void setDragToHotKeys() {
		this.setOnDragDetected(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				Control.setDragAction(ActionButton.this);
				event.consume();
			}

		});
	}

	/**
	 * Gets the action.
	 *
	 * @return the action
	 */
	public ActionNode getAction() {
		return this.action;
	}
}
