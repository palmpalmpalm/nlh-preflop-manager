package container;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import setup.Setting;

/**
 * @author Palm-Laptop
 *	EffectiveStack contains every position's stack
 */
public class EffectiveStack implements Serializable{
	// serialize ID
	private static final long serialVersionUID = 1L;
	
	// effectiveStack is mapping betweeen each position and stack to use as container for every postion
	private Map<String, Double> effectiveStack;

	// constructor
	public EffectiveStack() {
		this.effectiveStack = new HashMap<String, Double>();

		for (String pos : Setting.getPosition()) {
			this.effectiveStack.put(pos, 100.0);
		}
	}
	
	// constructor for copy
	public EffectiveStack(EffectiveStack es) {
		this.effectiveStack = new HashMap<String, Double>();
		this.effectiveStack.putAll(es.effectiveStack);
	}
	
	// getter for effectiveStack
	public Map<String, Double> getEffStack() {
		return effectiveStack;
	}

	// settter for effectiveStack
	public void setEffectiveStack(Map<String, Double> effStack) {
		this.effectiveStack = effStack;
	}

	// getter for stack
	public double getStack(String position) {
		return this.effectiveStack.get(position);
	}

	// setter for stack
	public void setStack(String position, double s) {
		this.effectiveStack.put(position, s);
	}
	
	// show
	public void show() {
		this.effectiveStack.forEach((pos,stackSize) -> System.out.println(pos + " : " + String.valueOf(stackSize)));
	}
}