package actionnode.base;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import container.AvaliblePosition;
import container.CurrentCombos;
import container.EachPositionWeight;
import container.EffectiveStack;
import container.WeightCombos;

/**
 * The Class ActionNode is the base class for all of action this node
 *         represent each node in the game tree
 */
public abstract class ActionNode implements Comparable<ActionNode>, Serializable {
	
	/** The Constant serialVersionUID. */
	// serializable ID
	private static final long serialVersionUID = 1L;
	
	/** The round. */
	private int round;
	
	/** The position. */
	private String position;
	
	/** The pot. */
	private double pot;
	
	/** The to call. */
	private double toCall;
	
	/** The each pos weight. */
	private EachPositionWeight eachPosWeight;
	
	/** The eff stack. */
	private EffectiveStack effStack;
	
	/** The ava pos. */
	private AvaliblePosition avaPos;
	
	/** The next actions. */
	private ArrayList<ActionNode> nextActions;

	/**
	 * Instantiates a new action node.
	 *
	 * @param position the position
	 * @param pot the pot
	 * @param toCall the to call
	 * @param effStack the eff stack
	 * @param avaPos the ava pos
	 * @param eachPosWeight the each pos weight
	 * @param round the round
	 */
	public ActionNode(String position, double pot, double toCall, EffectiveStack effStack, AvaliblePosition avaPos,
			EachPositionWeight eachPosWeight, int round) {
		this.setPosition(position);
		this.setPot(pot);
		this.setEachPosWeight(eachPosWeight);
		this.setEffStack(effStack);
		this.setAvaPos(avaPos);
		this.setNextActions(new ArrayList<ActionNode>());
		this.setRound(round);
		this.setToCall(toCall);
	}

	/**
	 * Gets the weight combos.
	 *
	 * @param pos the pos
	 * @return the weight combos
	 */
	public WeightCombos getWeightCombos(String pos) {
		return this.eachPosWeight.getPositionWeight(pos);
	}

	/**
	 * Update weight combos.
	 *
	 * @param pos the pos
	 * @param currentCombos the current combos
	 */
	public void updateWeightCombos(String pos, CurrentCombos currentCombos) {
		this.eachPosWeight.updatePositionWeight(pos, currentCombos);
	}

	/**
	 * Gets the each pos weight.
	 *
	 * @return the each pos weight
	 */
	public EachPositionWeight getEachPosWeight() {
		return eachPosWeight;
	}

	/**
	 * Sets the each pos weight.
	 *
	 * @param eachPosWeight the new each pos weight
	 */
	public void setEachPosWeight(EachPositionWeight eachPosWeight) {
		this.eachPosWeight = eachPosWeight;
	}

	/**
	 * Gets the total player left.
	 *
	 * @return the total player left
	 */
	public int getTotalPlayerLeft() {
		return this.avaPos.getTotalPlyerLeft();
	}

	/**
	 * Gets the next ava pos.
	 *
	 * @param pos the pos
	 * @return the next ava pos
	 */
	public String getNextAvaPos(String pos) {
		return this.avaPos.getNextAvaPos(pos);
	}

	/**
	 * Checks if is last pos.
	 *
	 * @param pos the pos
	 * @return true, if is last pos
	 */
	public boolean isLastPos(String pos) {
		return this.avaPos.isLastPos(pos);
	}

	/**
	 * Show ava pos.
	 */
	public void showAvaPos() {
		this.avaPos.show();
	}

	/**
	 * Gets the ava pos.
	 *
	 * @return the ava pos
	 */
	public AvaliblePosition getAvaPos() {
		return this.avaPos;
	}

	/**
	 * Sets the ava pos.
	 *
	 * @param avaPos the new ava pos
	 */
	public void setAvaPos(AvaliblePosition avaPos) {
		this.avaPos = avaPos;
	}

	/**
	 * Gets the stack.
	 *
	 * @param pos the pos
	 * @return the stack
	 */
	public double getStack(String pos) {
		return this.effStack.getStack(pos);
	}

	/**
	 * Sets the stack.
	 *
	 * @param pos the pos
	 * @param stack the stack
	 */
	public void setStack(String pos, double stack) {
		this.effStack.setStack(pos, stack);
	}

	/**
	 * Gets the eff stack.
	 *
	 * @return the eff stack
	 */
	public EffectiveStack getEffStack() {
		return this.effStack;
	}

	/**
	 * Sets the eff stack.
	 *
	 * @param effStack the new eff stack
	 */
	public void setEffStack(EffectiveStack effStack) {
		this.effStack = effStack;
	}

	/**
	 * Show eff stack.
	 */
	public void showEffStack() {
		this.effStack.show();
	}

	/**
	 * Adds the next action.
	 *
	 * @param actionNode the action node
	 */
	public void addNextAction(ActionNode actionNode) {
		this.nextActions.add(actionNode);
		Collections.sort(this.nextActions);
	}

	/**
	 * Delete action.
	 *
	 * @param index the index
	 */
	public void deleteAction(int index) {
		this.nextActions.remove(index);
	}

	/**
	 * Gets the next actions.
	 *
	 * @return the next actions
	 */
	public ArrayList<ActionNode> getNextActions() {
		return this.nextActions;
	}

	/**
	 * Sets the next actions.
	 *
	 * @param nextActions the new next actions
	 */
	public void setNextActions(ArrayList<ActionNode> nextActions) {
		this.nextActions = nextActions;
	}

	/**
	 * Gets the round.
	 *
	 * @return the round
	 */
	public int getRound() {
		return this.round;
	}

	/**
	 * Sets the round.
	 *
	 * @param round the new round
	 */
	public void setRound(int round) {
		this.round = round;
	}

	/**
	 * Gets the position.
	 *
	 * @return the position
	 */
	public String getPosition() {
		return this.position;
	}

	/**
	 * Sets the position.
	 *
	 * @param position the new position
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * Gets the pot.
	 *
	 * @return the pot
	 */
	public double getPot() {
		return this.pot;
	}

	/**
	 * Sets the pot.
	 *
	 * @param pot the new pot
	 */
	public void setPot(double pot) {
		this.pot = pot;
	}

	/**
	 * Gets the to call.
	 *
	 * @return the to call
	 */
	public double getToCall() {
		return this.toCall;
	}

	/**
	 * Sets the to call.
	 *
	 * @param toCall the new to call
	 */
	public void setToCall(double toCall) {
		this.toCall = toCall;
	}

	/**
	 * Compare to.
	 *
	 * @param that the that
	 * @return the int
	 */
	@Override
	public int compareTo(ActionNode that) {
		if (this.pot < that.pot) {
			return 1;
		} else if (this.pot > that.pot) {
			return -1;
		}
		return 0;
	}

	/**
	 * To string.
	 *
	 * @return the string
	 */
	@Override
	public abstract String toString();
}
