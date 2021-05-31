package setup;

import javafx.geometry.Insets;
import javafx.scene.paint.Color;

// TODO: Auto-generated Javadoc
/**
 * The Class Constant.
 */
public final class Constant {

	/** The Constant SUIT_COMBOS. */
	public static final int SUIT_COMBOS = 4;
	
	/** The Constant OFFSUIT_COMBOS. */
	public static final int OFFSUIT_COMBOS = 12;
	
	/** The Constant POCKET_COMBOS. */
	public static final int POCKET_COMBOS = 6;

	/** The Constant GRID_SIZE. */
	public static final double GRID_SIZE = 35.0;

	/** The Constant RAISE_COLOR_PALETTE. */
	public static final Color[] RAISE_COLOR_PALETTE = { Color.ORANGERED, Color.DARKRED, Color.RED, Color.DEEPPINK,
			Color.FUCHSIA, Color.DARKVIOLET, Color.ROYALBLUE, Color.DEEPSKYBLUE, Color.LIGHTSKYBLUE, Color.CYAN,
			Color.LIGHTCYAN, Color.ALICEBLUE, Color.AQUA, Color.AZURE, Color.BLUEVIOLET, Color.BISQUE, Color.CORAL, Color.CORNFLOWERBLUE,
			Color.CHOCOLATE, Color.BEIGE, Color.DARKBLUE, Color.DARKKHAKI};
	
	/** The Constant CALL_COLOR. */
	public static final Color CALL_COLOR = Color.LAWNGREEN;
	
	/** The Constant FOLD_COLOR. */
	public static final Color FOLD_COLOR = Color.LIGHTGREY;
	
	/** The Constant COMBOS_COVER_INSETS. */
	public static final Insets COMBOS_COVER_INSETS = new Insets(1, 1, 1, 15);
	
	/** The Constant COMBOS_HEIGHT. */
	public static final int COMBOS_HEIGHT = (int) (Constant.GRID_SIZE * Setting.getCard().length());
	
	/** The Constant COMBOS_WIDTH. */
	public static final int COMBOS_WIDTH = (int) (Constant.GRID_SIZE * Setting.getCard().length());

	/** The Constant BUTTON_WIDTH. */
	public static final int BUTTON_WIDTH = 175;
	
	/** The Constant BUTTON_HEIGHT. */
	public static final int BUTTON_HEIGHT = 25;
	
	/** The Constant SPACE. */
	public static final int SPACE = 5;
	
	/** The Constant BIG_SPACE. */
	public static final int BIG_SPACE = 10;
	
	/** The Constant BOX_HEIGHT. */
	public static final int BOX_HEIGHT = 15;
	
	/** The Constant TEXTFIELD_WIDTH. */
	public static final int TEXTFIELD_WIDTH = 70;
	
	/** The Constant TEXTFIELD_HEIGHT. */
	public static final int TEXTFIELD_HEIGHT = 25;

	/** The Constant ADD_ACTION_TEXT_FIELD_WIDTH. */
	public static final int ADD_ACTION_TEXT_FIELD_WIDTH = 150;
	
	/** The Constant ADD_ACTION_TEXT_FIELD_HEIGHT. */
	public static final int ADD_ACTION_TEXT_FIELD_HEIGHT = 25;

	/** The Constant EDIT_ACTION_PANE_HEIGHT. */
	public static final int EDIT_ACTION_PANE_HEIGHT = 200;
	
	/** The Constant EDIT_ACTION_PANE_WIDTH. */
	public static final int EDIT_ACTION_PANE_WIDTH = 200;

	/** The Constant RECT_SIZE. */
	public static final int RECT_SIZE = 10;
	
	/** The Constant ACTION_BUTTON_WIDTH. */
	public static final int ACTION_BUTTON_WIDTH = 150;
	
	/** The Constant ACTION_BUTTON_HEIGHT. */
	public static final int ACTION_BUTTON_HEIGHT = 25;

	/** The Constant ACTION_BUTTON_PANE_WIDTH. */
	public static final int ACTION_BUTTON_PANE_WIDTH = 200;
	
	/** The Constant ACTION_BUTTON_PANE_HEIGHT. */
	public static final int ACTION_BUTTON_PANE_HEIGHT = (int) (Setting.getCard().length() * Constant.GRID_SIZE)
			- Constant.EDIT_ACTION_PANE_HEIGHT;

	/** The Constant SAVE_BUTTON_WIDTH. */
	public static final int SAVE_BUTTON_WIDTH = 60;
	
	/** The Constant LOAD_BUTTON_WIDTH. */
	public static final int LOAD_BUTTON_WIDTH = 60;

	/** The Constant HISTORY_PANE_HEIGHT. */
	public static final int HISTORY_PANE_HEIGHT = 400;
	
	/** The Constant HISTORY_PANE_WIDTH. */
	public static final int HISTORY_PANE_WIDTH = 140;

	/** The Constant HOTKEYS_HEIGHT. */
	public static final int HOTKEYS_HEIGHT = 420;
	
	/** The Constant HOTKEYS_WIDTH. */
	public static final int HOTKEYS_WIDTH = 100;
	
	/** The Constant HOTKEYS_BUTTON_WIDTH. */
	public static final int HOTKEYS_BUTTON_WIDTH = 80;
	
	/** The Constant HOTKEYS_BUTTON_HEIGHT. */
	public static final int HOTKEYS_BUTTON_HEIGHT = 20;

	/** The Constant WEIGHT_HEIGHT. */
	public static final int WEIGHT_HEIGHT = 15;
	
	/** The Constant WEIGHT_WIDTH. */
	public static final int WEIGHT_WIDTH = 130;

	/** The Constant HEADER_HEIGHT. */
	public static final int HEADER_HEIGHT = 60;
	
	/** The Constant COMBOS_WIDHT. */
	public static final int COMBOS_WIDHT = 20;

}
