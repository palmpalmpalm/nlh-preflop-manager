package combos.gui;

import java.util.ArrayList;
import java.util.Random;

import actionnode.CheckCallNode;
import actionnode.RaiseNode;
import actionnode.base.ActionNode;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import setup.Constant;
import setup.Setting;


/**
 * The Class CombosPane.
 */
public class CombosPane extends StackPane{
	
	private boolean isAutoRandom = true;
	/**
	 * Instantiates a new combos pane.
	 *
	 * @param actionList the action list
	 * @param weightMode the weight mode
	 */
	public CombosPane(ArrayList<ActionNode> actionList, boolean weightMode, boolean isAutoRandom){
		super();
		/*for (ActionNode action: actionList) {
			if (action instanceof RaiseNode) {
				RaiseNode castAction = (RaiseNode) action;
				castAction.getCurrentCombos().show();
			} else if (action instanceof CheckCallNode) {
				CheckCallNode castAction = (CheckCallNode) action;
				castAction.getCurrentCombos().show();
			}
		}*/
		CombosCover cover = new CombosCover();
		GridPane combosGrid = new GridPane();
		combosGrid = new GridPane();		
		this.setAutoRandom(isAutoRandom);
		
		if (weightMode == true) {
			this.useWeightCombos(combosGrid, actionList);
		}else {
			this.useCurrentCombos(combosGrid, actionList);
		}
		
		//GUI Styling
		combosGrid.setAlignment(Pos.CENTER);
		combosGrid.setMaxSize(Constant.COMBOS_WIDTH, Constant.COMBOS_HEIGHT);
		this.setBackground(new Background(new BackgroundFill(Constant.FOLD_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
		this.setMinSize(Constant.COMBOS_WIDTH, Constant.COMBOS_HEIGHT);
		this.getChildren().addAll(combosGrid, cover);
	}

	
	/**
	 * Use current combos.
	 *
	 * @param combosGrid the combos grid
	 * @param actionList the action list
	 */
	private void useCurrentCombos(GridPane combosGrid, ArrayList<ActionNode> actionList){
		Random r = new Random();
		int randomNumber = r.nextInt(100) + 1;
		for (int i = 0; i < Setting.getCard().length(); i++) {
			for (int j = 0; j < Setting.getCard().length(); j++) {
				
				FlowPane comboCell = new FlowPane();
				comboCell.setMinSize(Constant.GRID_SIZE, Constant.GRID_SIZE);
				
				double actionFreq = 0;
				int actionColor = 0;
				for (ActionNode action : actionList) {
					if (this.isAutoRandom == true) {
						if (action instanceof RaiseNode) {
							RaiseNode castAction = (RaiseNode) action;
							double currActionFreq = castAction.getCurrentCombos().getCurrentCombosByPos(i, j);
							if (randomNumber > actionFreq && randomNumber <= actionFreq + currActionFreq) {
								comboCell.getChildren()
										.add(new ActionCell(100, Constant.RAISE_COLOR_PALETTE[actionColor]));
							}
							actionFreq += currActionFreq;
							actionColor++;
						} else if (action instanceof CheckCallNode) {
							CheckCallNode castAction = (CheckCallNode) action;
							double currActionFreq = castAction.getCurrentCombos().getCurrentCombosByPos(i, j);
							if (randomNumber > actionFreq && randomNumber <= actionFreq + currActionFreq) {
								comboCell.getChildren().add(new ActionCell(100, Constant.CALL_COLOR));
							}
							actionFreq += currActionFreq;
						}
					} else {
						if (action instanceof RaiseNode) {
							RaiseNode castAction = (RaiseNode) action;
							comboCell.getChildren()
									.add(new ActionCell(castAction.getCurrentCombos().getCurrentCombosByPos(i, j),
											Constant.RAISE_COLOR_PALETTE[actionColor]));
							actionColor++;
						} else if (action instanceof CheckCallNode) {
							CheckCallNode castAction = (CheckCallNode) action;
							comboCell.getChildren().add(new ActionCell(
									castAction.getCurrentCombos().getCurrentCombosByPos(i, j), Constant.CALL_COLOR));
						}

					}					
				}
				combosGrid.add(comboCell, j, i);							
			}
		}
	}
	

	/**
	 * Use weight combos.
	 *
	 * @param combosGrid the combos grid
	 * @param actionList the action list
	 */
	private void useWeightCombos(GridPane combosGrid, ArrayList<ActionNode> actionList) {
		
		Random r = new Random();
		int randomNumber = r.nextInt(100) + 1;

		/*System.out.println(actionList.size());
		for (ActionNode action: actionList){
			action.getEachPosWeight().getPositionWeight(action.getPosition()).show();
		}*/
		for (int i = 0; i < Setting.getCard().length(); i++) {
			for (int j = 0; j < Setting.getCard().length(); j++) {
				
				FlowPane comboCell = new FlowPane();
				comboCell.setMinSize(Constant.GRID_SIZE, Constant.GRID_SIZE);

				double actionFreq = 0;
				int actionColor = 0;
				for (ActionNode action : actionList) {

					if (this.isAutoRandom == true) {
						if (action instanceof RaiseNode) {
							RaiseNode castAction = (RaiseNode) action;
							double currActionFreq = castAction.getCurrentCombos().getCurrentCombosByPos(i, j);
							if (randomNumber > actionFreq && randomNumber <= actionFreq + currActionFreq) {
								comboCell.getChildren()
										.add(new ActionCell(100, Constant.RAISE_COLOR_PALETTE[actionColor]));
							}
							actionFreq += currActionFreq;
							actionColor++;
						} else if (action instanceof CheckCallNode) {
							CheckCallNode castAction = (CheckCallNode) action;
							double currActionFreq = castAction.getCurrentCombos().getCurrentCombosByPos(i, j);
							if (randomNumber > actionFreq && randomNumber <= actionFreq + currActionFreq) {
								comboCell.getChildren().add(new ActionCell(100, Constant.CALL_COLOR));
							}
							actionFreq += currActionFreq;
						}
					}else {
						if (action instanceof RaiseNode) {
							RaiseNode castAction = (RaiseNode) action;
							comboCell.getChildren().add(new ActionCell(castAction.getWeightCombos(castAction.getPosition()).getWeightComboByPos(i, j),
									Constant.RAISE_COLOR_PALETTE[actionColor]));
							actionColor++;
						} else if (action instanceof CheckCallNode) {
							CheckCallNode castAction = (CheckCallNode) action;
							comboCell.getChildren().add(
									new ActionCell(castAction.getWeightCombos(castAction.getPosition()).getWeightComboByPos(i, j),
											Constant.CALL_COLOR));
						}
					}					
				}
				combosGrid.add(comboCell, j, i);
			}
		}
	}

	public void setAutoRandom(boolean isAutoRandom) {
		this.isAutoRandom = isAutoRandom;
	}
	
}
