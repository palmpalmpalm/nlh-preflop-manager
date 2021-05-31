package actionnode;

import actionnode.base.ActionNode;

import container.AvaliblePosition;
import container.EachPositionWeight;
import container.EffectiveStack;


/**
 * The Class BlindNode represents SB and BB placing blinds.
 */
public class BlindNode extends ActionNode{
	

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new blind node.
	 */
	public BlindNode() {
		super("BB", 1.5, 1.0, new EffectiveStack(), new AvaliblePosition(), new EachPositionWeight(), 0);
		this.setStack("SB", 99.5);
		this.setStack("BB", 99.0);
	}
	
	/**
	 * To string.
	 *
	 * @return the string
	 */
	public String toString() {
		return "BB SB Place Blind";
	}

}
