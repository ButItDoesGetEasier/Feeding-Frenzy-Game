package game;

public class Enemy extends Fish {
	private boolean moving; // signifies that the enemy is moving in a direction
	private GameStage gs;

	Enemy (int xPos, int yPos, GameStage gs){
		super(xPos, yPos);
		this.gs = gs;
		this.imgStr = "images/enemy"+this.imgNum+".png"; // have separate image for when immune and not, imgNum is determined in Fish superclass
		this.imgStrImmune = "images/enemy"+this.imgNum+"immune.png";
		this.loadImage(this.imgStr,Fish.INIT_SIZE);
	}

	void move() {
		if(!this.moving) { // check first if not already moving
			EnemyTimer t = new EnemyTimer(this); // set a timer for movement then start
			t.start();
			this.moving = true;
		}
	}

	void changeXY () { // actual change in x and y position happens here
		// check if x and y position already reached the bounds of the x and y position of the background; if so, go to the opposite direction
		if(this.x <= this.gs.getBgX() || this.x + this.size >= this.gs.getBgX() + GameStage.MAP_SIZE) this.dx *= -1;
		if(this.y <= this.gs.getBgY() || this.y + this.size >= this.gs.getBgY() + GameStage.MAP_SIZE) this.dy *= -1;

		this.x += (this.dx*(this.speed+1)); // increment x and y in accordance to speed
		this.y += (this.dy*(this.speed+1));
	}

	void flipMoving() { // negate moving attribute
		this.moving = !this.moving;
	}
}