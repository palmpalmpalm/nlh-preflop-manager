package hotkeys.gui;

import actionnode.base.ActionNode;

// TODO: Auto-generated Javadoc
/**
 * The ClassNodePointer uses to make a pointer to action node with a purpose of make a copy of history listใ
 */
public class NodePointer {
	
	/** The node pointer. */
	ActionNode nodePointer;
	
	/**
	 * Instantiates a new node pointer.
	 *
	 * @param actionNode the action node
	 */
	public NodePointer(ActionNode actionNode) {
		this.setNodePointer(actionNode);
	}

	/**
	 * Sets the node pointer.
	 *
	 * @param nodePointer the new node pointer
	 */
	public void setNodePointer(ActionNode nodePointer) {
		this.nodePointer = nodePointer;
	}

	/**
	 * Gets the node.
	 *
	 * @return the node
	 */
	public ActionNode getNode() {
		return this.nodePointer;
	}
	
}
