package container;

import java.util.List;


import setup.Setting;

import java.io.Serializable;
import java.util.Arrays;

// TODO: Auto-generated Javadoc
/**
 * CurrentCombos contains current combos for single position
 * current combos is a combos at the current node for more clarify its doesn't count weight from previous action
 */
public class CurrentCombos implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The current combos. */
	private double[][] currentCombos;

	/**
	 * Instantiates a new current combos.
	 *
	 * @param combosText the combos text
	 */
	public CurrentCombos(String combosText) {
		this.setCurrentCombos(new double[Setting.getCard().length()][Setting.getCard().length()]);
		this.setRangeCurrent(combosText);
	}
	
	/**
	 * Instantiates a new current combos.
	 *
	 * @param currCombos the curr combos
	 */
	public CurrentCombos(CurrentCombos currCombos) {	
		this.setCurrentCombos(new double[Setting.getCard().length()][Setting.getCard().length()]);
		for (int i = 0; i < Setting.getCard().length(); i++) {
			for (int j = 0; j < Setting.getCard().length(); j++) {
				this.currentCombos[i][j] = currCombos.getCurrentCombosByPos(i, j);
			}
		}
	}

	/**
	 * Sets the range current.
	 *
	 * @param combosText the new range current
	 */
	public void setRangeCurrent(String combosText) {
		List<String> textOrder = Arrays.asList(combosText.split("\\s*,\\s*"));

		double weight = 100;

		for (String order : textOrder) {
			if (order.charAt(0) == '[') {
				int parenIndex = order.indexOf(']');
				int percentIndex = order.indexOf('%');
				if (percentIndex == -1) {
					weight = Double.valueOf(order.substring(1, parenIndex));
				} else {
					weight = Double.valueOf(order.substring(1, percentIndex));
				}
			}
			int startIndex = order.indexOf(']');
			if (startIndex == -1) {
				startIndex = 0;
			} else {
				startIndex += 1; // shift 1
			}
			int stopIndex = order.indexOf('[', startIndex);
			if (stopIndex == -1) {
				stopIndex = order.length();
			}
			if (order.contains("/") && (order.charAt(0) != '[')) {
				stopIndex = order.indexOf('[');
				startIndex = 0;
			}
			this.addCombos(order.substring(startIndex, stopIndex), weight);
			if (order.charAt(order.length() - 1) == ']') {
				weight = 100.0;
			}
		}

	}
	

	/**
	 * Adds the combos.
	 *
	 * @param textOrder the text order
	 * @param weight the weight
	 */
	public void addCombos(String textOrder, double weight) {
		if (textOrder.contains("-")) {
			String[] combos = textOrder.split("-");
			int i, j, ie, je;
			i = this.getComboPosition(combos[0])[0];
			j = this.getComboPosition(combos[0])[1];
			ie = this.getComboPosition(combos[1])[0];
			je = this.getComboPosition(combos[1])[1];

			while (i != ie || j != je) {
				this.addCombo(i, j, weight);
				if (i == j) {
					i++;
					j++;
				} else if (j < i) {
					i++;
				} else {
					j++;
				}
			}
			this.addCombo(ie, je, weight);

		} else {
			int i, j;
			i = this.getComboPosition(textOrder)[0];
			j = this.getComboPosition(textOrder)[1];
			this.addCombo(i, j, weight);
		}
	}
	
	/**
	 * Adds the combo.
	 *
	 * @param i the i
	 * @param j the j
	 * @param weight the weight
	 */
	// add combo to container
	public void addCombo(int i, int j, double weight) {
		this.currentCombos[i][j] = Math.floor(weight);
	}
	
	/**
	 * Gets the combo position.
	 *
	 * @param combo the combo
	 * @return the combo position
	 */
	// get position of combo by given combo name
	public int[] getComboPosition(String combo) {
		int typeHand = combo.length();

		char firstCard = combo.charAt(0);
		char secondCard = combo.charAt(1);
		int indexFirstCard = Setting.getCard().indexOf(firstCard);
		int indexSecondCard = Setting.getCard().indexOf(secondCard);

		if (typeHand == 3 && combo.charAt(2) == 'o') {
			return new int[] { indexSecondCard, indexFirstCard };
		}

		return new int[] { indexFirstCard, indexSecondCard };
	}
	
	/**
	 * Show.
	 */
	public void show() {
		System.out.print("  ");
		for (int i = 0; i < Setting.getCard().length(); i++) {
			System.out.print(" " + Setting.getCard().charAt(i) + "  ");
		}
		System.out.print("\n");

		for (int i = 0; i < Setting.getCard().length(); i++) {

			System.out.print(Setting.getCard().charAt(i) + " ");

			for (int j = 0; j < Setting.getCard().length(); j++) {
				String temp = "0";
				temp = String.valueOf(this.currentCombos[i][j]);
				System.out.print(temp + "|");

			}
			System.out.print("\n");

			System.out.print("  ");
			for (int j = 0; j < Setting.getCard().length(); j++) {
				System.out.print("---+");
			}
			System.out.print("\n");
		}
	}

	/**
	 * Gets the current combo.
	 *
	 * @param ComboName the combo name
	 * @return the current combo
	 */
	public double getCurrentCombo(String ComboName) {
		int i = this.getComboPosition(ComboName)[0];
		int j = this.getComboPosition(ComboName)[1];

		if (this.currentCombos == null) {
			return 0;
		} else {
			return this.currentCombos[i][j];
		}
	}

	/**
	 * Gets the current combos by pos.
	 *
	 * @param i the i
	 * @param j the j
	 * @return the current combos by pos
	 */
	public double getCurrentCombosByPos(int i, int j) {
		if (this.currentCombos == null) {
			return 0;
		} else {
			return this.currentCombos[i][j];
		}
	}
	
	/**
	 * Sets the current combos.
	 *
	 * @param currentCombos the new current combos
	 */
	public void setCurrentCombos(double[][] currentCombos) {
		this.currentCombos = currentCombos;
	}

}
