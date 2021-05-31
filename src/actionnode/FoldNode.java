package actionnode;

import actionnode.base.ActionNode;
import container.AvaliblePosition;
import container.EachPositionWeight;
import container.EffectiveStack;

/**
 * The Class FoldNode represents the fold action.
 */
public class FoldNode extends ActionNode{
	

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new fold node.
	 *
	 * @param position the position
	 * @param pot the pot
	 * @param toCall the to call
	 * @param effStack the eff stack
	 * @param avaPos the ava pos
	 * @param eachPosWeight the each pos weight
	 * @param round the round
	 */
	public FoldNode(String position, double pot, double toCall, EffectiveStack effStack, AvaliblePosition avaPos,
			EachPositionWeight eachPosWeight, int round) {
		super(position, pot, toCall, effStack, avaPos, eachPosWeight, round);
	}
	
	/**
	 * To string.
	 *
	 * @return the string
	 */
	public String toString() {
		return this.getPosition() + " Fold";
	}
	
}
