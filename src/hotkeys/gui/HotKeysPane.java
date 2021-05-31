package hotkeys.gui;

import java.io.Serializable;
import java.util.ArrayList;

import actionnode.base.ActionNode;
import control.Control;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import setup.Constant;
import setup.Setting;

// TODO: Auto-generated Javadoc
/**
 * The Class HotKeysPane is a pane that contains hot keys.
 */
public class HotKeysPane extends VBox implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The keys pane. */
	private VBox keysPane;

	/**
	 * Instantiates a new hot keys pane.
	 */
	public HotKeysPane() {
		super();
		Text hotKeysText = new Text("Hot Keys");
		VBox hotKeysTextBox = new VBox();
		this.setKeysPane(new VBox());
		Text DragAndDropText = new Text("Drag and Drop");

		hotKeysTextBox.getChildren().add(hotKeysText);
		this.keysPane.getChildren().add(DragAndDropText);

		this.setOnDragOver();
		this.setOnDragDropped();

		ScrollPane scrollKeysPane = new ScrollPane();
		scrollKeysPane.setContent(keysPane);

		this.setSpacing(Constant.SPACE);
		this.setAlignment(Pos.CENTER);
		this.setPrefSize(Constant.HOTKEYS_WIDTH, Constant.HOTKEYS_HEIGHT);
		this.getChildren().addAll(hotKeysTextBox, scrollKeysPane);

		// Styling GUI
		scrollKeysPane.getStylesheets().add("scrollbar.css");
		
		hotKeysText.setStyle("-fx-font-size: 14px; -fx-fill: black; -fx-font-weight: bold;");

		hotKeysTextBox.setAlignment(Pos.CENTER);
		hotKeysTextBox.setMinHeight(Constant.BOX_HEIGHT);
		hotKeysTextBox.setStyle("-fx-background-color: #FFA000; -fx-background-radius: 5 5 5 5;");
		hotKeysTextBox.setPadding(new Insets(5, 5, 5, 5));

		DragAndDropText.setStyle("-fx-font-size: 12px; -fx-fill: lightgrey; -fx-font-weight: bold;");

		keysPane.setAlignment(Pos.CENTER);
		keysPane.setSpacing(Constant.SPACE);
		keysPane.setMinHeight(Constant.HOTKEYS_HEIGHT - 50);

		scrollKeysPane.setMinHeight(Constant.HOTKEYS_HEIGHT - 40);
		scrollKeysPane.setFitToWidth(true);
		scrollKeysPane.setStyle("-fx-background: #4d4d4d; -fx-padding: 0;");
	}

	/**
	 * Sets the on drag over.
	 *
	 * @param keysPane the new on drag over
	 */
	private void setOnDragOver() {
		this.keysPane.setOnDragOver(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				if (Control.isDbHasContentAndNotSamePane(HotKeysPane.this.keysPane)) {
					event.acceptTransferModes(TransferMode.COPY);
				}
				event.consume();
			}
		});
	}

	/**
	 * Sets the on drag dropped.
	 *
	 * @param keysPane the new on drag dropped
	 */
	private void setOnDragDropped() {
		this.keysPane.setOnDragDropped(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				if (Control.isDbHasContentAndNotSamePane(HotKeysPane.this.keysPane)) {
					keysPane.getChildren()
							.add(new HotKeysButton(Control.getDraggingButton().getAction(), Control.getHistoryList()));
					Control.setDraggingButtonToNull();
					event.setDropCompleted(true);
					Control.setUpPane();
				}
				event.consume();
			}
		});
	}
	
	public void addRFIButton(ActionNode action, ArrayList<ActionNode> historyList, String buttonInfo) {
		this.keysPane.getChildren().add(new RFIButton(action, historyList, buttonInfo));
	}

	/**
	 * Gets the hot keys buttons.
	 *
	 * @return the hot keys buttons
	 */
	public ArrayList<HotKeysButton> getHotKeysButtons() {
		ObservableList<Node> observlist = this.keysPane.getChildren();
		ArrayList<HotKeysButton> hotKeysButtonList = new ArrayList<HotKeysButton>();
		for (Node obs : observlist) {
			hotKeysButtonList.add((HotKeysButton) obs);
		}
		return hotKeysButtonList;

	}
	
	/**
	 * Sets the keys pane.
	 *
	 * @param keysPane the new keys pane
	 */
	public void setKeysPane(VBox keysPane) {
		this.keysPane = keysPane;
	}
	


}
