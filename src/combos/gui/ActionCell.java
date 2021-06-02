package combos.gui;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import setup.Constant;

/**
 * The Class ActionCell.
 */
public class ActionCell extends Rectangle{

	/**
	 * Instantiates a new action cell.
	 *
	 * @param actionFrequency the action frequency
	 * @param color the color
	 */
	public ActionCell(double actionFrequency, Color color) {
		super();
		double af = Math.floor(actionFrequency/100 * (Constant.GRID_SIZE));
		af = Math.floor(af);
		
		//GUI Styling
		this.setWidth(af);
		this.setHeight(Constant.GRID_SIZE);
		this.setFill(color);
	}

}
