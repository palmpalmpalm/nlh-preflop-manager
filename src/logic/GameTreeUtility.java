package logic;

import java.util.LinkedList;
import java.util.Queue;

import actionnode.CheckCallNode;
import actionnode.FoldNode;
import actionnode.RaiseNode;
import actionnode.base.ActionNode;
import container.AvaliblePosition;
import container.CurrentCombos;
import container.EachPositionWeight;
import container.EffectiveStack;
import setup.Setting;

// TODO: Auto-generated Javadoc
/**
 * The Class GameTreeUtility uses to add action and generate fold action to the game tree.
 */
public class GameTreeUtility {
	
	/** The que. */
	private static Queue<ActionNode> que = new LinkedList<ActionNode>();

	/**
	 * Adds the que.
	 *
	 * @param actionNode the action node
	 */
	public static void addQue(ActionNode actionNode) {
		GameTreeUtility.que.add(actionNode);
	}

	/**
	 * Gen fold action.
	 */
	public static void genFoldAction() {
		int count = 0;
		while (!GameTreeUtility.que.isEmpty()) {
			ActionNode currAction = GameTreeUtility.que.peek();
			GameTreeUtility.que.remove();
			count++;

			if (currAction.getTotalPlayerLeft() >= 1) {
				GameTreeUtility.addFold(currAction);
			}

		}
		System.out.println("+Added " + String.valueOf(count) + " node to GameTree+");
	}

	/**
	 * Adds the fold.
	 *
	 * @param currAction the curr action
	 */
	public static void addFold(ActionNode currAction) {
		int round = currAction.getRound();
		String nextPos = currAction.getNextAvaPos(currAction.getPosition());
		double nextPosStack = currAction.getStack(nextPos);
		double nextPosPutInPot = Setting.getStartingStack() - nextPosStack;

		if (currAction.getToCall() > nextPosPutInPot && nextPosStack >= 0.0 || (round == 1 && nextPos == "BB")) {
			if (currAction.isLastPos(currAction.getPosition())) {
				round++;
			}

			EffectiveStack foldEffStack = new EffectiveStack(currAction.getEffStack());
			AvaliblePosition foldAvaPos = new AvaliblePosition(currAction.getAvaPos());
			EachPositionWeight foldEachPosWeight = new EachPositionWeight(currAction.getEachPosWeight());

			foldAvaPos.foldPos(nextPos);
			ActionNode foldNode = new FoldNode(nextPos, currAction.getPot(), currAction.getToCall(), foldEffStack,
					foldAvaPos, foldEachPosWeight, round);

			currAction.addNextAction(foldNode);
			GameTreeUtility.addQue(foldNode);
		}
	}

	/**
	 * Adds the check call.
	 *
	 * @param currentCombos the current combos
	 * @param currAction the curr action
	 */
	public static void addCheckCall(CurrentCombos currentCombos, ActionNode currAction) {
		int round = currAction.getRound();
		String nextPos = currAction.getNextAvaPos(currAction.getPosition());
		double nextPosStack = currAction.getStack(nextPos);
		double nextPosPutInPot = Setting.getStartingStack() - nextPosStack;

		if (currAction.getToCall() > nextPosPutInPot && nextPosStack >= 0.0 || nextPos.equals("BB") && round == 1) {
			if (currAction.isLastPos(currAction.getPosition())) {
				round++;
			}

			double realToCall = Math.min(currAction.getToCall() - nextPosPutInPot, nextPosStack);
			EffectiveStack checkCallEffStack = new EffectiveStack(currAction.getEffStack());
			AvaliblePosition checkCallAvaPos = new AvaliblePosition(currAction.getAvaPos());
			EachPositionWeight checkEachPosWeight = new EachPositionWeight(currAction.getEachPosWeight());

			checkCallEffStack.setStack(nextPos, nextPosPutInPot - realToCall);
			checkEachPosWeight.updatePositionWeight(nextPos, currentCombos);
			ActionNode checkCallNode = new CheckCallNode(new CurrentCombos(currentCombos), nextPos,
					currAction.getPot() + realToCall, currAction.getToCall(), checkCallEffStack, checkCallAvaPos,
					checkEachPosWeight, round);

			currAction.addNextAction(checkCallNode);
			GameTreeUtility.addQue(checkCallNode);
		} else {
			System.out.println("invalid check/call exception");
		}
	}
	
	/**
	 * Adds the raise.
	 *
	 * @param currentCombos the current combos
	 * @param raiseSize the raise size
	 * @param currAction the curr action
	 */
	public static void addRaise(CurrentCombos currentCombos, double raiseSize, ActionNode currAction) {
		int round = currAction.getRound();
		String nextPos = currAction.getNextAvaPos(currAction.getPosition());
		double nextPosStack = currAction.getStack(nextPos);
		double nextPosPutInPot = Setting.getStartingStack() - nextPosStack;

		if (currAction.getToCall() > nextPosPutInPot && nextPosStack >= 0.0 || nextPos.equals("BB") && round == 1) {
			if (currAction.isLastPos(currAction.getPosition())) {
				round++;
			}

			double realToCall = Math.min(currAction.getToCall() - nextPosPutInPot, nextPosStack);
			double toRaise = raiseSize;
			double realToRaise = Math.min(nextPosStack, toRaise - nextPosPutInPot);

			if (realToRaise > realToCall) {
				EffectiveStack raiseEffStack = new EffectiveStack(currAction.getEffStack());
				AvaliblePosition raiseAvaPos = new AvaliblePosition(currAction.getAvaPos());
				EachPositionWeight raiseEachPosWeight = new EachPositionWeight(currAction.getEachPosWeight());

				raiseEffStack.setStack(nextPos, nextPosStack - realToRaise);
				raiseEachPosWeight.updatePositionWeight(nextPos, currentCombos);

				ActionNode raiseNode = new RaiseNode(new CurrentCombos(currentCombos), nextPos,
						currAction.getPot() + realToRaise, realToRaise + nextPosPutInPot, raiseEffStack, raiseAvaPos,
						raiseEachPosWeight, round);

				currAction.addNextAction(raiseNode);
				GameTreeUtility.addQue(raiseNode);
			} else {
				System.out.println("invalid raise size exception");
			}

		} else {
			System.out.println("invalid raise exception");
		}
	}

}
