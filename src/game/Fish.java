package game;

import java.util.Random;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class Fish extends Sprite {
	protected int size; // set attributes to protected for subclass access
	protected float speed;
	protected boolean alive, immune;
	protected GraphicsContext gc;
	protected String imgStr, imgStrImmune;
	protected int imgNum;
	protected int speedCount;

	public final static int INIT_SIZE = 40;

	Fish (int xPos, int yPos){
		super(xPos, yPos);
		this.size = Fish.INIT_SIZE;
		this.speed = (120/Fish.INIT_SIZE);
		this.speedCount = 1; // how many layers of speed boost has been gained
		this.alive = true; // signifies if alive, immune
		this.immune = false;

		Canvas canvas = new Canvas(GameStage.WINDOW_WIDTH, GameStage.WINDOW_WIDTH); // open canvas with same size as stage
		this.gc = canvas.getGraphicsContext2D();

		Random r = new Random();
		this.imgNum = r.nextInt(3)+1; // choose from 3 different fish images
	}

	// getters and setters
	boolean isAlive() {
		return this.alive;
	}

	boolean isImmune() {
		return this.immune;
	}

	int getSize() {
		return this.size;
	}

	float getSpeed() {
		return this.speed;
	}

	String getImgStr() {
		return this.imgStr;
	}

	String getImgStrImmune() {
		return this.imgStrImmune;
	}

	void setAlive(boolean alive) {
		this.alive = alive;
	}

	void setImmune(boolean immune) {
		this.immune = immune;
	}

	void incSize (int nSize) { // increase fish size by nSize
		this.size += nSize; // increment by nSize
		this.speed = (120f/size) * this.speedCount; // update speed
	}

	void doubleSpeed() { // multiply speed by 2
		this.speed = (2 * this.speed);
	}

	void eatsFish (Fish fish) { // called upon eating another fish
		fish.setAlive(false); // arg fish is set to not alive
		this.incSize(fish.getSize()); // increase size

		// load image again with increased size
		if(this.isImmune()) { // if currently immune, use immune string and normal if otherwise
			this.loadImage(this.getImgStrImmune(), this.getSize());
		} else {
			this.loadImage(this.getImgStr(), this.getSize());
		}

		// if fish is a player, increase enemies eaten and relocate it to center (since increase of size will move it southeast)
		if (this instanceof Player) {
			((Player) this).incEnemiesEaten();
			this.relocate(GameStage.WINDOW_WIDTH/2-(this.getSize()/2), GameStage.WINDOW_HEIGHT/2-(this.getSize()/2));
		}
	}

	void eatsFood (Food food) { // called upon eating food
		this.incSize(Food.FOOD_SIZE); // increase size by food size

		// load image again with increased size
		if(this.isImmune()) { // if currently immune, use immune string and normal if otherwise
			this.loadImage(this.getImgStrImmune(), this.getSize());
		} else {
			this.loadImage(this.getImgStr(), this.getSize());
		}

		Random r = new Random(); // find new x and y coordinates in the map, then relocate there
		int newX = (GameStage.MAP_SIZE/2) + (GameStage.WINDOW_WIDTH/2) - (Fish.INIT_SIZE/2) - (r.nextInt(GameStage.MAP_SIZE-(Fish.INIT_SIZE/2)));
		int newY = (GameStage.MAP_SIZE/2) + (GameStage.WINDOW_HEIGHT/2) - (Fish.INIT_SIZE/2) - (r.nextInt(GameStage.MAP_SIZE-(Fish.INIT_SIZE/2)));

		food.relocate(newX, newY);

		// if fish is a player, increase enemies eaten and relocate it to center (since increase of size will move it southeast)
		if (this instanceof Player) {
			((Player) this).incFoodsEaten();
			this.relocate(GameStage.WINDOW_WIDTH/2-(this.getSize()/2), GameStage.WINDOW_HEIGHT/2-(this.getSize()/2));
		}
	}

	void incSpeedCount () { // called when a speed power up is gained; increments count
		this.speedCount += 1;
	}

	void relocate(int x, int y) { // setter of x and y
		this.x = x;
		this.y = y;

	}
}
