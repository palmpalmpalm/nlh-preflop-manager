package nextaction.gui;

import java.util.ArrayList;

import actionnode.RaiseNode;
import actionnode.CheckCallNode;
import actionnode.base.ActionNode;
import control.Control;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import setup.Constant;

// TODO: Auto-generated Javadoc
/**
 * The Class NextAction is a pane for next action of the current position.
 */
public class NextActionPane extends ScrollPane {

	/**
	 * Instantiates a new next action pane.
	 *
	 * @param actionList the action list
	 */
	public NextActionPane(ArrayList<ActionNode> actionList) {
		super();
		VBox buttonPane = new VBox();
		VBox nextActionBox = new VBox();
		Text nextAction = new Text("Next Action");
		
		nextActionBox.getChildren().add(nextAction);			
		buttonPane.getChildren().add(nextActionBox);
		
		int actionColor = 0;
		for (int i = 0; i < actionList.size(); i++) {
			if (actionList.get(i) instanceof RaiseNode) {	
				HBox buttonBox = new HBox();
				Rectangle colorRect = new Rectangle();
				ActionButton actionButton = new ActionButton(actionList.get(i), i);
				buttonBox.getChildren().addAll(colorRect, actionButton);				
				buttonPane.getChildren().add(buttonBox);
				
				//GUI Styling
				colorRect.setWidth(Constant.RECT_SIZE);
				colorRect.setHeight(Constant.RECT_SIZE);
				colorRect.setFill(Constant.RAISE_COLOR_PALETTE[actionColor]);
				colorRect.setStyle("-fx-stroke: black; -fx-stroke-width: 1;");
				buttonBox.setAlignment(Pos.CENTER);
				buttonBox.setSpacing(Constant.SPACE);				
				actionColor++;
			}else if (actionList.get(i) instanceof CheckCallNode){
				HBox buttonBox = new HBox();
				Rectangle colorRect = new Rectangle();
				ActionButton actionButton = new ActionButton(actionList.get(i), i);
				buttonBox.getChildren().addAll(colorRect, actionButton);				
				buttonPane.getChildren().add(buttonBox);
				
				//GUI Styling
				colorRect.setWidth(Constant.RECT_SIZE);
				colorRect.setHeight(Constant.RECT_SIZE);
				colorRect.setFill(Constant.CALL_COLOR);
				colorRect.setStyle("-fx-stroke: black; -fx-stroke-width: 1;");
				
				buttonBox.setAlignment(Pos.CENTER);
				buttonBox.setSpacing(Constant.SPACE);
				
			}else {
				HBox buttonBox = new HBox();
				Rectangle colorRect = new Rectangle();
				ActionButton actionButton = new ActionButton(actionList.get(i), i);
				buttonBox.getChildren().addAll(colorRect, actionButton);				
				buttonPane.getChildren().add(buttonBox);
				
				// GUI Styling
				colorRect.setWidth(Constant.RECT_SIZE);
				colorRect.setHeight(Constant.RECT_SIZE);
				colorRect.setFill(Constant.FOLD_COLOR);
				colorRect.setStyle("-fx-stroke: black; -fx-stroke-width: 1;");
				
				buttonBox.setAlignment(Pos.CENTER);
				buttonBox.setSpacing(Constant.SPACE);
				
			}			
		}
		
		if (Control.getCurrAction().getPot() > Control.getRoot().getPot()  && Control.getCurrAction().getAvaPos().getTotalPlyerLeft() > 2) {
			VBox withFoldToPane = new VBox();
			withFoldToPane.getChildren().addAll(buttonPane, new FoldToNextAvaPane(Control.getCurrAction()));
			withFoldToPane.setSpacing(Constant.SPACE);
			withFoldToPane.setAlignment(Pos.CENTER);
			this.setContent(withFoldToPane);
		}else {
			this.setContent(buttonPane);
		}		
		
		// GUI Styling
		this.setStyle("-fx-background: #212121; -fx-padding: 0;");
		this.setFitToWidth(true);
		this.setPrefSize(Constant.ACTION_BUTTON_PANE_WIDTH, Constant.ACTION_BUTTON_PANE_HEIGHT);
		this.getStylesheets().add("scrollbar.css");
		nextAction.setStyle("-fx-font-size: 13px; -fx-fill: white; -fx-font-weight: bold;");
		nextActionBox.setAlignment(Pos.CENTER);
		nextActionBox.setMinHeight(Constant.BOX_HEIGHT);
		nextActionBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.07); -fx-background-radius: 5 5 5 5;");
		nextActionBox.setPadding(new Insets(5, 5, 5, 5));
		buttonPane.setSpacing(Constant.SPACE);
		buttonPane.setAlignment(Pos.TOP_CENTER);
		
	}
}
