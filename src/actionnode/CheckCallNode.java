package actionnode;

import actionnode.base.ActionNode;
import container.AvaliblePosition;
import container.CurrentCombos;
import container.EachPositionWeight;
import container.EffectiveStack;

/**
 * The Class CheckCallNode represents the check or call action.
 */
public class CheckCallNode extends ActionNode {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The current combos. */
	private CurrentCombos currentCombos;


	/**
	 * Instantiates a new check call node.
	 *
	 * @param currCombos the curr combos
	 * @param position the position
	 * @param pot the pot
	 * @param toCall the to call
	 * @param effStack the eff stack
	 * @param avaPos the ava pos
	 * @param eachPosWeight the each pos weight
	 * @param round the round
	 */
	public CheckCallNode(CurrentCombos currCombos, String position, double pot, double toCall, EffectiveStack effStack,
			AvaliblePosition avaPos, EachPositionWeight eachPosWeight, int round) {
		super(position, pot, toCall, effStack, avaPos, eachPosWeight, round);
		this.setCurrentCombos(new CurrentCombos(currCombos));
	}


	/**
	 * Gets the current combos.
	 *
	 * @return the current combos
	 */
	public CurrentCombos getCurrentCombos() {
		return this.currentCombos;
	}


	/**
	 * Sets the current combos.
	 *
	 * @param currentCombos the new current combos
	 */
	public void setCurrentCombos(CurrentCombos currentCombos) {
		this.currentCombos = currentCombos;
	}


	/**
	 * To string.
	 *
	 * @return the string
	 */
	public String toString() {
		return this.getPosition() + " Check/Call " + String.valueOf(super.getToCall()) + " BB";
	}

}
