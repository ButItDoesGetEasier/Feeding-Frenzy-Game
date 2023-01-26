package game;

import java.util.Random;

public class PowerUp extends Item{
	private String type; // type of powerup, either speed or immunity
	private boolean consumed; // determines if the powerup has been consumed
	private PowerUpTimer pTime; // times when powerup should appear/disappear or lose effect

	public final static String SPEED = "speed";
	public final static String IMMUNITY = "immunity";
	public final String SPEED_IMG = "images/speed.png";
	public final String IMMUN_IMG = "images/immunity.png";

	PowerUp(int x, int y, String type) {
		super(x, y);
		this.type = type; // set constructor values
		this.consumed = false;
		this.pTime = new PowerUpTimer(this);

		if (type == PowerUp.SPEED) { // load image based on type
			loadImage(SPEED_IMG, Food.FOOD_SIZE*2);
		} else if (type == PowerUp.IMMUNITY) {
			loadImage(IMMUN_IMG, Food.FOOD_SIZE*2);
		}
	}

	void affect(Fish fish) { // called upon being acquired by a fish
		if (this.type == SPEED) { // if type is speed, double fish' speed
			fish.doubleSpeed();
		} else { // immunity
			fish.setImmune(true); // set fish to immune, change its image to its immune form
			fish.loadImage(fish.getImgStrImmune(), fish.getSize());
		}
	}

	void stopEffect(Fish fish) {
		if (this.type == IMMUNITY) {
			fish.setImmune(false); // set fish to not immune, change its image back to its normal form
			fish.loadImage(fish.getImgStr(), fish.getSize());
		}
	}

	void timer() { // starts power up timer
		this.pTime.start();
	}

	// getters
	PowerUpTimer getPtimer() {
		return this.pTime;
	}

	boolean isConsumed() {
		return this.consumed;
	}

	String getType () {
		return this.type;
	}

	// setters
	void setConsumed(boolean consumed) {
		this.consumed = consumed;
	}

	void relocate() { // set powerup to a new location in the window
		Random r = new Random();

		int x = r.nextInt(GameStage.WINDOW_WIDTH);
		int y = r.nextInt(GameStage.WINDOW_HEIGHT);

		this.x = x;
		this.y = y;
	}
}
