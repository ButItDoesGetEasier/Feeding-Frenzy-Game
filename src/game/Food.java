package game;

import java.util.Random;

public class Food extends Item {
	private String imgStr;
	public final static int FOOD_SIZE = 20;

	Food(int x, int y) {
		super(x, y);

		Random r = new Random();
		this.imgStr = "images/food"+(r.nextInt(3)+1)+".png"; // choose from 3 different food images, load
		loadImage(this.imgStr, Food.FOOD_SIZE);
	}
}
