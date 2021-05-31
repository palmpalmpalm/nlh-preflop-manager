package alert.gui;

import javafx.scene.control.Alert;

// TODO: Auto-generated Javadoc
/**
 * The Class AlertPopUp.
 */
public class AlertPopUp extends Alert{


	/**
	 * Instantiates a new alert pop up.
	 *
	 * @param alertHeader the alert header
	 * @param alertText the alert text
	 */
	public AlertPopUp(String alertHeader, String alertText) {
		super(AlertType.INFORMATION);
		this.setTitle(alertHeader);
		this.setHeaderText(null);
		this.setContentText(alertText);
	}

}
