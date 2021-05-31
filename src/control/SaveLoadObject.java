package control;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import actionnode.base.ActionNode;

// TODO: Auto-generated Javadoc
/**
 * The Class SaveLoadObject.
 */
public class SaveLoadObject implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The root. */
	private ActionNode root;
	
	/** The starting stack. */
	private double startingStack;
	
	/** The position. */
	private ArrayList<String> position;
	
	/** The card. */
	private String card;
	
	
	/**
	 * Instantiates a new save load object.
	 *
	 * @param root the root
	 * @param startingStack the starting stack
	 * @param position the position
	 * @param card the card
	 */
	public SaveLoadObject(ActionNode root, double startingStack, ArrayList<String> position, String card) {
		this.setCard(card);
		this.setPosition(position);
		this.setRoot(root);
		this.setStartingStack(startingStack);
	}


	/**
	 * Gets the root.
	 *
	 * @return the root
	 */
	public ActionNode getRoot() {
		return root;
	}


	/**
	 * Sets the root.
	 *
	 * @param root the new root
	 */
	public void setRoot(ActionNode root) {
		this.root = root;
	}


	/**
	 * Gets the starting stack.
	 *
	 * @return the starting stack
	 */
	public double getStartingStack() {
		return startingStack;
	}


	/**
	 * Sets the starting stack.
	 *
	 * @param startingStack the new starting stack
	 */
	public void setStartingStack(double startingStack) {
		this.startingStack = startingStack;
	}


	/**
	 * Gets the position.
	 *
	 * @return the position
	 */
	public ArrayList<String> getPosition() {
		return position;
	}


	/**
	 * Sets the position.
	 *
	 * @param position the new position
	 */
	public void setPosition(ArrayList<String> position) {
		this.position = position;
	}


	/**
	 * Gets the card.
	 *
	 * @return the card
	 */
	public String getCard() {
		return card;
	}


	/**
	 * Sets the card.
	 *
	 * @param card the new card
	 */
	public void setCard(String card) {
		this.card = card;
	}
	
	
	
	
}
