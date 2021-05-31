package topofcombospane.gui;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import setup.Constant;
import javafx.geometry.Pos;

/**
 * The Class TopOfCombosPane is a pane on the top of combos pane this pane contains weight mode button.
 */
public class TopOfCombosPane extends BorderPane{
	
	/**
	 * Instantiates a new top of combos pane.
	 */
	public TopOfCombosPane() {
		super();
		Text combosName = new Text("Combos");
		VBox textBox = new VBox();
		HBox switchBox = new HBox();
		WeightSwitch weightSwitch = new WeightSwitch();
		AutoRandomSwitch autoRandomSwitch = new AutoRandomSwitch();
		switchBox.getChildren().addAll(weightSwitch, autoRandomSwitch);
		
		textBox.setAlignment(Pos.CENTER);
		textBox.getChildren().add(combosName);		
		
		this.setLeft(textBox);
		this.setRight(switchBox);
		
		// GUI Styling
		switchBox.setSpacing(Constant.SPACE);
		combosName.setStyle("-fx-font-size: 12px; -fx-fill: white; -fx-font-weight: bold;");
	}
}
