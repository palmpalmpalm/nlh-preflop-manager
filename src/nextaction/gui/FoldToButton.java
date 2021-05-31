package nextaction.gui;

import control.Control;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import setup.Constant;

/**
 * The Class FoldToButton.
 */
public class FoldToButton extends Button{
	
	/** The pos. */
	String pos;
	
	/**
	 * Instantiates a new fold to button.
	 *
	 * @param pos the pos
	 */
	public FoldToButton(String pos) {
		super();
		this.setPos(pos);
		this.setText("Fold to " + String.valueOf(this.pos));
		
		this.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				Control.foldTo(pos);				
			}
		});
		
		// GUI Styling
				this.setPrefSize(Constant.ACTION_BUTTON_WIDTH, Constant.ACTION_BUTTON_HEIGHT);		
				this.setStyle(
						"-fx-background-color: rgba(255, 255, 255, 0.2); -fx-background-radius: 15 15 15 15; -fx-font-size: 12px; -fx-text-fill: white; -fx-font-weight: bold;");
				this.setOnMouseExited(e -> FoldToButton.this.setStyle(
						"-fx-background-color: rgba(255, 255, 255, 0.2); -fx-background-radius: 15 15 15 15;  -fx-font-size: 12px; -fx-text-fill: white; -fx-font-weight: bold;"));
				this.setOnMouseEntered(e -> FoldToButton.this.setStyle(
						"-fx-background-color: rgba(255, 255, 255, 0.08); -fx-background-radius: 15 15 15 15;  -fx-font-size: 12px; -fx-text-fill: white; -fx-font-weight: bold;"));

		
	}
	
	/**
	 * Sets the pos.
	 *
	 * @param pos the new pos
	 */
	public void setPos(String pos) {
		this.pos = pos;
	}
	
	
}
