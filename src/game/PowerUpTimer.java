package game;

import javafx.animation.PauseTransition;
import javafx.event.EventHandler;
import javafx.util.Duration;
import javafx.event.ActionEvent;

public class PowerUpTimer extends Thread {
	private PowerUp powerup;
	private boolean spawnNow; // determines if a powerup should appead or not
	private Fish affectedFish;

	public final int APPEAR = 10; // 10 secs before appearing, 5 before disappearing upon uncollection
	public final int DISAPPEAR = 5;

	PowerUpTimer(PowerUp powerup) {
		this.powerup = powerup; // set constructor values
		this.spawnNow = false;
		this.affectedFish = null;
	}

	public void run() {
		while(true){ // run continuously
			this.powerup.setConsumed(false); // at the start of every iteration, powerup is unconsumed
			try {
				Thread.sleep(APPEAR * 1000); // sleep 10 seconds
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			this.spawnNow = true; // after 10 seconds, powerup should spawn

			for(int sec = 0; sec < DISAPPEAR; sec++){ // loop to 5 (break upon being collected)
				try {
					if(this.powerup.isConsumed()){ // // if consumed,
						if (this.powerup.getType() == PowerUp.IMMUNITY) { // speed powerups are permanent while immunity only persist for 5

							PauseTransition transition = new PauseTransition(Duration.seconds(DISAPPEAR)); // induce 5 second delay
							transition.play();

							transition.setOnFinished(new EventHandler<ActionEvent>() { // after 5 seconds, powerup will lose effect
								public void handle(ActionEvent arg0) {
									powerup.stopEffect(affectedFish);
								}
							});

						}
						break; // break (count to 10 again before spawning)
					}
					Thread.sleep(1000); // wait 1 sec before counting up
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			this.spawnNow = false; // after above procedure, powerup should not appear anymore
			this.powerup.relocate(); // change powerup location
		}
	}

	// getter
	boolean getSpawnNow() {
		return this.spawnNow;
	}

	// setter
	void setAffectedFish(Fish fish) {
		this.affectedFish = fish;
	}
}
