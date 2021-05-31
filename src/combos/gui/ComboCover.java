package combos.gui;

import javafx.geometry.Pos;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import setup.Constant;

// TODO: Auto-generated Javadoc
/**
 * The Class ComboCover.
 */
public class ComboCover extends FlowPane {

	/**
	 * Instantiates a new combo cover.
	 *
	 * @param comboName the combo name
	 */
	public ComboCover(String comboName) {
		super();
		Text textCell = new Text(comboName);
		textCell.setTextOrigin(textCell.getTextOrigin());
		textCell.setTextAlignment(TextAlignment.CENTER);
		
		//GUI Styling
		this.getChildren().add(textCell);
		this.setMinWidth(Constant.GRID_SIZE);
		this.setMinHeight(Constant.GRID_SIZE);
		this.setAlignment(Pos.TOP_CENTER);
	}
}
