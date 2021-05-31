package combos.gui;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import setup.Constant;
import setup.Setting;


// TODO: Auto-generated Javadoc
/**
 * The Class CombosCover.
 */
public class CombosCover extends GridPane{

	/**
	 * Instantiates a new combos cover.
	 */
	public CombosCover() {
		super();
		this.setMaxSize(Constant.COMBOS_WIDTH, Constant.COMBOS_HEIGHT);
		this.setAlignment(Pos.CENTER);
		this.setGridLinesVisible(true);

		for (int i = 0; i < Setting.getCard().length(); i++) {
			for (int j = 0; j < Setting.getCard().length(); j++) {
				String comboName = String.valueOf(Setting.getCard().charAt(Math.min(i, j))) 
						+ String.valueOf(Setting.getCard().charAt(Math.max(i, j)));
				if (i == j) { // POCKET COMBO
					this.add(new ComboCover(comboName), j, i);
				} else if (j < i) { // OFFSUIT COMBO
					this.add(new ComboCover(comboName + "o"), j, i);
				} else { // SUIT COMBO
					this.add(new ComboCover(comboName + "s"), j, i);
				}
			}
		}
	}
}
