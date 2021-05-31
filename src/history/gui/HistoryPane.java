package history.gui;

import java.util.ArrayList;

import actionnode.base.ActionNode;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import setup.Constant;


// TODO: Auto-generated Javadoc
/**
 * HistoryPane correct the what action you take and store it to list.
 * you can go back to that action
 */
public class HistoryPane extends VBox{
	

	/**
	 * Instantiates a new history pane.
	 *
	 * @param pastActionList the past action list
	 */
	public HistoryPane(ArrayList<ActionNode> pastActionList) {
		super();
		Text gameTreeText = new Text("Game Tree");
		Text potSizeText = new Text();
		VBox pastActionPane = new VBox();	
		ScrollPane pastActionScrollPane = new ScrollPane();		
		double potSize = pastActionList.get(pastActionList.size() - 1).getPot();
		potSizeText.setText("Pot: " + String.valueOf(potSize));		
		for (int i = 0;i< pastActionList.size(); i++) {
			pastActionPane.getChildren().add(new PastActionButton(pastActionList.get(i), i));
		}				
		pastActionScrollPane.setContent(pastActionPane);
		this.getChildren().addAll(gameTreeText, potSizeText, pastActionScrollPane);
		
		//GUI Styling
		this.setAlignment(Pos.CENTER);
		this.setSpacing(Constant.SPACE);
		this.setMinWidth(Constant.HISTORY_PANE_WIDTH);	
		this.setStyle(" -fx-background-radius: 10 10 10 10; -fx-background-color: rgba(255, 255, 255, 0.07); ");
		
		pastActionScrollPane.setFitToWidth(true);	
		pastActionScrollPane.setMaxWidth(Constant.HISTORY_PANE_WIDTH - 10);
		pastActionScrollPane.setMinHeight(Constant.HISTORY_PANE_HEIGHT- 5);
		pastActionScrollPane.setPadding(new Insets(7, 7, 7, 7));
		pastActionScrollPane.setStyle("-fx-background: #4d4d4d; -fx-padding: 0;");
		
		gameTreeText.setStyle("-fx-font-size: 14px; -fx-fill: white; -fx-font-weight: bold;");		
		potSizeText.setStyle("-fx-font-size: 12px; -fx-fill: white; -fx-font-weight: bold;");		
		pastActionPane.setSpacing(Constant.SPACE);
		pastActionPane.setAlignment(Pos.CENTER);
		pastActionPane.setPadding(new Insets(5,5,5,5));
		
	}
}
