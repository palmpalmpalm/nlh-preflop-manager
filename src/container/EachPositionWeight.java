package container;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import setup.Setting;


/**
 * The Class EachPositionWeight contains weight combos for every postion
 */
public class EachPositionWeight implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The each position weight. */
	private Map<String, WeightCombos> eachPositionWeight;
	
	/**
	 * Instantiates a new each position weight.
	 */
	public EachPositionWeight() {
		this.eachPositionWeight = new HashMap<String, WeightCombos>();

		for (String pos : Setting.getPosition()) {
			this.eachPositionWeight.put(pos, new WeightCombos());
		}
	}
	
	/**
	 * Instantiates a new each position weight.
	 *
	 * @param eachPosWeight the each pos weight
	 */
	public EachPositionWeight(EachPositionWeight eachPosWeight) { 
		this.eachPositionWeight = new HashMap<String, WeightCombos>();
		
		for (String pos : Setting.getPosition()) {
			this.eachPositionWeight.put(pos, new WeightCombos(eachPosWeight.getPositionWeight(pos)));		
		}
	}
	
	/**
	 * Gets the position weight.
	 *
	 * @param position the position
	 * @return the position weight
	 */
	public WeightCombos getPositionWeight(String position) {
		return this.eachPositionWeight.get(position);
		
	}
	
	/**
	 * Update position weight.
	 *
	 * @param pos the pos
	 * @param currentCombos the current combos
	 */
	public void updatePositionWeight(String pos, CurrentCombos currentCombos) {
		if (this.eachPositionWeight.get(pos).isNull()) {
			this.eachPositionWeight.put(pos, new WeightCombos(currentCombos));
		}else {
			this.eachPositionWeight.get(pos).updateWeight(currentCombos);
		}		
	}
	
	/**
	 * Sets the pos stack to null.
	 *
	 * @param pos the new pos stack to null
	 */
	public void setPosStackToNull(String pos) {
		this.eachPositionWeight.get(pos).setToNull();
	}
	
}
