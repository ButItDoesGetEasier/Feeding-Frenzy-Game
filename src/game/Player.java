package game;

public class Player extends Fish {
	private int enemiesEaten;
	private int foodsEaten;

	Player (){
		super(GameStage.WINDOW_WIDTH/2-(Fish.INIT_SIZE/2), GameStage.WINDOW_HEIGHT/2-(Fish.INIT_SIZE/2)); // x and y is at the very center of window, subtract fish.initsize/2 since location is based on topleft corner
		this.imgStr = "images/player"+this.imgNum+".png"; // have separate image for when immune and not, imgNum is determined in Fish superclass
		this.imgStrImmune = "images/player"+this.imgNum+"immune.png";
		this.enemiesEaten = 0; // for stats
		this.foodsEaten = 0;
		this.loadImage(this.imgStr,Fish.INIT_SIZE); // load
	}

	// getter
	int getEnemiesEaten(){
		return this.enemiesEaten;
	}


	// getter
	int getFoodsEaten(){
		return this.foodsEaten;
	}

	void incEnemiesEaten(){
		this.enemiesEaten++;
	}

	void incFoodsEaten(){
		this.foodsEaten++;
	}
}
