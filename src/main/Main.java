package main;

import java.util.Timer;

import actionnode.BlindNode;
import actionnode.base.ActionNode;
import container.EachPositionWeight;
import control.Control;
import control.RNG;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import logic.GameTreeUtility;
import setup.Setting;

import javax.swing.plaf.synth.SynthTextAreaUI;

/**
 * The Class Main is main class for initialize the application
 *
 */
public class Main extends Application{

	/**
	 * Start the Application.
	 *
	 * @param stage the stage
	 * @throws Exception the exception
	 */
	@Override
	public void start(Stage stage) throws Exception {

		// blind is what players in BB and SB have to put in pot at the start of the game
		ActionNode blind = new BlindNode();
		GameTreeUtility.addQue(blind);
		GameTreeUtility.genFoldAction();	
		
		// set up pane
		BorderPane appPane = new BorderPane();
		
		// add blind to the root
		Control.setRoot(blind);
		Control.setAppPane(appPane);
		Control.setUpPane();
		Control.addRFIkeys();

		// init RNG
		Timer timer = new Timer();
		timer.schedule(new RNG(), 0, 5000);
		
		// set scene
		Scene scene = new Scene(appPane);
		stage.setTitle("NLH Preflop Manager");
		stage.setScene(scene);
		stage.setResizable(false);
		stage.show();
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}
	
}
