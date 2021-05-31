package nextaction.gui;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import setup.Constant;

import java.util.ArrayList;

import actionnode.base.ActionNode;

// TODO: Auto-generated Javadoc
/**
 * The Class FoldToNextAvaPane.
 */
public class FoldToNextAvaPane extends VBox{
	
	/**
	 * Instantiates a new fold to next avalible pane.
	 *
	 * @param currAction the current action
	 */
	public FoldToNextAvaPane(ActionNode currAction) {
		super();
		ArrayList<String> allAvaPosList = new ArrayList<String>();
		allAvaPosList = currAction.getAvaPos().getAllNextPos(currAction.getPosition());
		allAvaPosList.remove(allAvaPosList.size() - 1);
		allAvaPosList.remove(0);
		allAvaPosList.remove(0);
		for (String pos: allAvaPosList) {
			this.getChildren().add(new FoldToButton(pos));
		}
		
		//GUI Styling
		this.setSpacing(Constant.SPACE);
		this.setAlignment(Pos.CENTER);
	}
}
