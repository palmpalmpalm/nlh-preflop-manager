package control;

import java.util.Random;
import java.util.TimerTask;


// TODO: Auto-generated Javadoc
/**
 * The Class RNG is randoming number every 5 seconds.
 */
public class RNG extends TimerTask{

	/**
	 * Run.
	 */
	@Override
	public void run() {
		Random r = new Random();
		int randomInt = r.nextInt(100) + 1;
		Control.setRNG(randomInt);
	}

}
