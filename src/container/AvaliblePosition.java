package container;

import java.io.Serializable;
import java.util.ArrayList;

import setup.Setting;


// TODO: Auto-generated Javadoc
/**
 * The Class AvaliblePosition is class which contains all avalible position at current action line.
 */
public class AvaliblePosition implements Serializable{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The avalible position. */
	public ArrayList<String> avaliblePosition;


	/**
	 * Instantiates a new avalible position.
	 */
	public AvaliblePosition() {
		this.setAvaliblePosition(new ArrayList<String>());

		for (String pos : Setting.getPosition()) {
			this.avaliblePosition.add(pos);
		}
	}
	

	/**
	 * Instantiates a new avalible position.
	 *
	 * @param ap the ap
	 */
	public AvaliblePosition(AvaliblePosition ap) {
		this.setAvaliblePosition(new ArrayList<String>(ap.avaliblePosition));

	}

	/**
	 * Checks if is avalible.
	 *
	 * @param pos the pos
	 * @return true, if is avalible
	 */
	public boolean isAvalible(String pos) {
		int indexOfPos = Setting.getPosition().indexOf(pos);
		if (!this.avaliblePosition.get(indexOfPos).equals("fold")) {
			return true;
		} else {
			return false;
		}
	}


	/**
	 * Fold pos.
	 *
	 * @param pos the pos
	 */
	public void foldPos(String pos) {
		int indexPos = Setting.getPosition().indexOf(pos);
		this.avaliblePosition.set(indexPos, "fold");

	}

	/**
	 * Gets the next ava pos.
	 *
	 * @param pos the pos
	 * @return the next ava pos
	 */
	public String getNextAvaPos(String pos) {
	
		int nextPos = (Setting.getPosition().indexOf(pos) + 1) % Setting.getPosition().size();
		
		while (true) {
			nextPos %= Setting.getPosition().size();
			if (!this.avaliblePosition.get(nextPos).equals("fold")) {
				return this.avaliblePosition.get(nextPos);
			} else {
				nextPos++;
			}
		}

	}
	
	/**
	 * Gets the all next pos.
	 *
	 * @param pos the pos
	 * @return the all next pos
	 */
	public ArrayList<String> getAllNextPos(String pos){
		ArrayList<String> nextAllAvaPosList = new ArrayList<String>();
		String currPos = this.getNextAvaPos(pos);		
		int nPlayerLeft = this.getTotalPlyerLeft();
		
		for (int i = 0; i < nPlayerLeft; i++) {
			nextAllAvaPosList.add(currPos);
			currPos = this.getNextAvaPos(currPos);
		}
	
		return nextAllAvaPosList;
	}

	/**
	 * Checks if is last pos.
	 *
	 * @param pos the pos
	 * @return true, if is last pos
	 */
	public boolean isLastPos(String pos) {
		int indexPos = Setting.getPosition().indexOf(pos);
		for (int i = indexPos + 1; i < Setting.getPosition().size(); i++) {
			if (!this.avaliblePosition.get(i).equals("fold")) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Gets the total plyer left.
	 *
	 * @return the total plyer left
	 */
	public int getTotalPlyerLeft() {
		int count = 0;
		for (int i = 0; i < Setting.getPosition().size(); i++) {
			if (!this.avaliblePosition.get(i).equals("fold")) {
				count++;
			}
		}
		return count;
	}


	/**
	 * Show.
	 */
	public void show() {
		for (String pos : this.avaliblePosition) {
			if (!pos.equals("fold")) {
				System.out.print(pos + " ");
			}
		}
		System.out.print("\n");
	}
	
	/**
	 * Sets the avalible position.
	 *
	 * @param avaliblePosition the new avalible position
	 */
	public void setAvaliblePosition(ArrayList<String> avaliblePosition) {
		this.avaliblePosition = avaliblePosition;
	}
}