package container;

import java.io.Serializable;

import setup.Setting;

/**
 * @author Palm-Laptop
 * WeightCombos contains weight combos for single position
 * weightCombos for more clarify weight combos purposely use for containing weight of each position 
 */
public class WeightCombos implements Serializable{
	// serializable ID
	private static final long serialVersionUID = 1L;
	
	// container of weight combos (2d array of double)
	private Double[][] weightCombos;

	// constructor
	public WeightCombos() {
		//do nothing
	}	
	
	// constructor for copy with some implementation to reduce amount of ram used
	public WeightCombos(WeightCombos weightCombos) {
		if (!weightCombos.isNull()) {
			this.setWeightCombos(new Double[Setting.getCard().length()][Setting.getCard().length()]);
			for (int i = 0; i < Setting.getCard().length(); i++) {
				for (int j = 0; j < Setting.getCard().length(); j++) {
					this.weightCombos[i][j] = weightCombos.getWeightComboByPos(i, j);
				}
			}
		}
	}
	
	// constructor from given CurrentCombos
	public WeightCombos(CurrentCombos combos) {
		this.setWeightCombos(new Double[Setting.getCard().length()][Setting.getCard().length()]);
		for (int i = 0; i < Setting.getCard().length(); i++) {
			for (int j = 0; j < Setting.getCard().length(); j++) {
				this.weightCombos[i][j] = combos.getCurrentCombosByPos(i, j);
			}
		}		
	}
	
	// set weightCombos to null to reduce amount of ram used
	public void setToNull() {
		this.weightCombos = null;
	}
	
	// check is container is null or not
	public boolean isNull() {
		if (weightCombos == null) {
			return true;
		}else {
			return false;
		}
	}
	
	// update current combos to weight combos
	public void updateWeight(CurrentCombos currentCombos) {
		for (int i = 0; i < Setting.getCard().length(); i++) {
			for (int j = 0; j < Setting.getCard().length(); j++) {			
				this.weightCombos[i][j] = Math.floor(this.getWeightComboByPos(i, j) * currentCombos.getCurrentCombosByPos(i, j)/100.00);
			}
		}
	}
	
	// getter weight combos by position
	public double getWeightComboByPos(int i, int j) {
		if (this.weightCombos[i][j] == null) {
			return 0;
		} else {
			return this.weightCombos[i][j];
		}
	}
	
	// show container
	public void show() {
		System.out.print("  ");
		for (int i = 0; i < Setting.getCard().length(); i++) {
			System.out.print(" " + Setting.getCard().charAt(i) + "  ");
		}
		System.out.print("\n");

		for (int i = 0; i < Setting.getCard().length(); i++) {

			System.out.print(Setting.getCard().charAt(i) + " ");

			for (int j = 0; j < Setting.getCard().length(); j++) {
				String temp = String.valueOf(this.getWeightComboByPos(i, j));
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
	
	// setter for weight combos
	public void setWeightCombos(Double[][] weightCombos) {
		this.weightCombos = weightCombos;
	}
}
