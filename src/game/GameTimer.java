package game;

import java.util.ArrayList;
import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/*
 * The GameTimer is a subclass of the AnimationTimer class. It must override the handle method.
 */

public class GameTimer extends AnimationTimer{

	private GraphicsContext gc;
	private Scene theScene;
	private GameStage gs;
	private MainMenu menu;
	private Player player;

	private ArrayList<Fish> fishes; // will contain fishes regardless of type
	private ArrayList<Food> foods; // will contain foods

	private PowerUp speedPower;
	private PowerUp immunPower;

	public static final int MAX_NUM_ENEMIES = 10;
	public static final int MAX_NUM_FOOD = 50;

	GameTimer(GraphicsContext gc, Scene scene, GameStage gamestage, MainMenu menu, Player player){
		this.gs = gamestage; // set values
		this.menu = menu;
		this.gc = gc;
		this.theScene = scene;
		this.player = player;

		this.fishes = new ArrayList<Fish>(); // create arraylist of fishes
		this.spawnEnemies(); // spawn enemies (add them to list)
		fishes.add(player); // add player to list

		this.foods = new ArrayList<Food>(); // create arraylist of foods
		this.spawnFoods();   // spawn foods (add them to list)

		this.startPowerUp(); // start powerup count

		//call method to handle mouse click event
		this.handleKeyPressEvent();
	}

	@Override
	public void handle(long currentNanoTime) {
		this.gc.clearRect(0, 0, GameStage.MAP_SIZE,GameStage.MAP_SIZE);// clear bg
		this.gc.drawImage(this.gs.getBG(), this.gs.getBgX(), this.gs.getBgY()); // redraw based on bgX and bgY

		this.gs.clearStatBarGc(); // clear status bar

		relocateElements(); // relocate elements based on player movement

		this.moveEnemies(); // move the enemies

		// check collisions
		for(int i = fishes.size() - 1; i >= 0; i--) { // loop through fishes (start from end)
			if (fishes.get(i).isAlive()) { // only check if ith fish is alive
				for (int j = 0; j<GameTimer.MAX_NUM_FOOD; j++) { // loop through foods
					if (fishes.get(i).collidesWith(foods.get(j))) { // if ith fish collides with jth food
						fishes.get(i).eatsFood(foods.get(j)); // ith fish eats jth food
						break; // break to save framerate, other food in contact would still be eaten in the next handle calls
					}
				}

				for (int j = i-1; j >= 0; j--) { // loop through fishes except those already checked in the outer loop
					if (fishes.get(i).collidesWith(fishes.get(j))) { // check if the ith and jth fishes collide
						// check if the ith fish is bigger than the jth and that the jth is not immune
						if(fishes.get(i).getSize() > fishes.get(j).getSize() && !fishes.get(j).isImmune()) {
							fishes.get(i).eatsFish(fishes.get(j)); // the ith fish eats the jth
						} else if (fishes.get(j).getSize() > fishes.get(i).getSize() && !fishes.get(i).isImmune()){ // if it's the other way around,
							fishes.get(j).eatsFish(fishes.get(i)); // the jth fish eats the ith
						}
						break; // break to save framerate, other smaller fishes in contact would still be eaten in the next handle calls
					}
				}

				// checks if the ith fish collides wih the speed power up and that it is spawned and not yet consumed
				if (fishes.get(i).collidesWith(this.speedPower) && this.speedPower.getPtimer().getSpawnNow() && !this.speedPower.isConsumed()) {
					this.speedPower.affect(fishes.get(i)); // the speed power affects the ith fish
					this.speedPower.setConsumed(true); // the powerup is set to consumed
					this.fishes.get(i).incSpeedCount(); // increase number of speed layer of ith fish
				}

				// checks if the ith fish collides wih the immunity power up and that it is spawned and not yet consumed
				if (fishes.get(i).collidesWith(this.immunPower) && this.immunPower.getPtimer().getSpawnNow() && !this.immunPower.isConsumed()) {
					this.immunPower.affect(fishes.get(i)); // immunPower affects ith fish
					this.immunPower.setConsumed(true); // it is set to consumed
					this.immunPower.getPtimer().setAffectedFish(fishes.get(i)); // set the ith fish as immunpower's beneficiary
				}

			}

			if (!this.player.isAlive()) { // at the end of the collision checks, if the player is dead,
				this.stop(); // stop the timer
				this.gs.getGameOver().fixScene(menu, gs); // fix stats in gameover stage
				this.menu.getStage().setX(550); // fix window positions
				this.menu.getStage().setY(63);
				this.menu.getStage().setScene(this.gs.getGameOver().getScene()); // set the gameover scene as the scene
			}
		}

		renderFishes(); // render elements
		renderPowerUps();
		renderFoods();

		gs.printGameStatusBar(); // print status bar
	}

	// method that will render/draw the fishes to the canvas
	private void renderFishes() {
		ArrayList<Fish> fishesToRemove = new ArrayList<Fish>(); // remove dead fishes first; store them here
		for (Fish f : this.fishes){ // loop through fishes, if dead, add to fishes to remove
			if (f.isAlive()) {
				f.render(this.gc);
			} else {
				fishesToRemove.add(f);
			}
		}

		this.fishes.removeAll(fishesToRemove); // remove dead fishes

		if (fishes.size() == 1) { // only the player remains, win: gameover
			this.stop(); // stop timer
			this.gs.getGameOver().fixScene(menu, gs); // fix stats in gameover stage
			this.menu.getStage().setX(550); // fix window positions
			this.menu.getStage().setY(63);
			this.menu.getStage().setScene(this.gs.getGameOver().getScene()); // set the gameover scene as the scene
		}
	}

	// method that will render/draw the fishes to the canvas
	private void renderFoods() {
		for (Food f : this.foods){ // loop through fishes, render
			f.render(this.gc);
		}

	}

	private void renderPowerUps() {
		if (this.speedPower.getPtimer().getSpawnNow() && !this.speedPower.isConsumed()) { // if speedpower is spawned and unconsumed, render
			this.speedPower.render(this.gc);
		}

		if (this.immunPower.getPtimer().getSpawnNow() && !this.immunPower.isConsumed()) { // if immunPower is spawned and unconsumed, render
			this.immunPower.render(this.gc);
		}
	}

	private void spawnEnemies(){
		Random r = new Random();
		for(int i=0;i<GameTimer.MAX_NUM_ENEMIES;i++){ // loop 10 times (10 enemies)
			// generate valid location in the map randomly
			int x = (GameStage.MAP_SIZE/2) + (GameStage.WINDOW_WIDTH/2) - (Fish.INIT_SIZE) - (r.nextInt(GameStage.MAP_SIZE-Fish.INIT_SIZE));
			int y = (GameStage.MAP_SIZE/2) + (GameStage.WINDOW_HEIGHT/2) - (Fish.INIT_SIZE) - (r.nextInt(GameStage.MAP_SIZE-Fish.INIT_SIZE));

			this.fishes.add(new Enemy(x,y, this.gs)); // add new enemy to fishes
		}
	}

	private void spawnFoods(){
		Random r = new Random();
		for(int i=0;i<GameTimer.MAX_NUM_FOOD;i++){ // loop 50 times (50 enemies)
			// generate valid location in the map randomly
			int x = (GameStage.MAP_SIZE/2) + (GameStage.WINDOW_WIDTH/2) - (Fish.INIT_SIZE/2) - (r.nextInt(GameStage.MAP_SIZE-(Fish.INIT_SIZE/2)));
			int y = (GameStage.MAP_SIZE/2) + (GameStage.WINDOW_HEIGHT/2) - (Fish.INIT_SIZE/2) - (r.nextInt(GameStage.MAP_SIZE-(Fish.INIT_SIZE/2)));

			this.foods.add(new Food(x,y)); // add new Food to foods
		}

	}

	private void startPowerUp() { // start powerup timer
		Random r = new Random();

		// generate random locations in the window
		int x1 = r.nextInt(GameStage.WINDOW_WIDTH);
		int y1 = r.nextInt(GameStage.WINDOW_HEIGHT);

		int x2 = r.nextInt(GameStage.WINDOW_WIDTH);
		int y2 = r.nextInt(GameStage.WINDOW_HEIGHT);

		this.speedPower = new PowerUp(x1, y1, PowerUp.SPEED); // instantiate speed and immunity
		this.immunPower = new PowerUp(x2, y2, PowerUp.IMMUNITY);

		this.speedPower.timer(); // start timers
		this.immunPower.timer();
	}

	//method that will move the fishes
	private void moveEnemies(){
		// loop through the fishes arraylist
		for(Fish fish:fishes) {
			if (fish != this.player) { // if not a player, cast then move
				((Enemy) fish).move();
			}
		}
	}

	// method that will listen and handle the key press events
	private void handleKeyPressEvent() {
		this.theScene.setOnKeyPressed(new EventHandler<KeyEvent>(){
			public void handle(KeyEvent e){
                movePlayer(e.getCode());
			}
		});

		this.theScene.setOnKeyReleased(new EventHandler<KeyEvent>(){
		            public void handle(KeyEvent e){
		                stopPlayer(e.getCode());
		            }
		        });
    }

	// method that will change dx and dy depending on the key pressed
	private void movePlayer(KeyCode key) {
		if(key==KeyCode.W) this.player.setDY(1);

		if(key==KeyCode.A) this.player.setDX(1);

		if(key==KeyCode.S) this.player.setDY(-1);

		if(key==KeyCode.D) this.player.setDX(-1);
   	}

	private boolean checkBounds() {
		// check if the player has reached the bounds
		if ((this.gs.getBgY() >= (GameStage.WINDOW_HEIGHT/2-(this.player.getSize()/2)) && this.player.getDy() == 1) // check if the player has reached the top border and is pressing W
		|| (this.gs.getBgY() <= ((GameStage.MAP_SIZE-(GameStage.WINDOW_HEIGHT/2)-(this.player.getSize()/2))*-1) && this.player.getDy() == -1) // check if the player has reached the bottom border and is pressing S
		|| (this.gs.getBgX() <= ((GameStage.MAP_SIZE-(GameStage.WINDOW_WIDTH/2)-(this.player.getSize()/2))*-1) && this.player.getDx() == -1) // check if the player has reached the right border and is pressing D
		||	(this.gs.getBgX() >= (GameStage.WINDOW_HEIGHT/2-(this.player.getSize()/2)) && this.player.getDx() == 1)) // check if the player has reached the left border and is pressing A
		{
			return false; // if any of the following is true: return false
		}
		return true;
	}

	private void relocateElements() { // move elements based on player movement
		if(checkBounds()) { // check if the player is not the bounds first
			// set x and y of bg as its previous values + the dx(direction of player) * its speed
			this.gs.setBgX(this.gs.getBgX() + (this.player.getDx()*((int)this.player.getSpeed()+1)));
			this.gs.setBgY(this.gs.getBgY() + (this.player.getDy()*((int)this.player.getSpeed()+1)));

			// loop thru foods
			for (Food food:this.foods) {
				// set food as its previous + the dx(direction of player) * its speed
				food.relocate(food.getX()+(this.player.getDx()*((int)this.player.getSpeed()+1)), food.getY()+(this.player.getDy()*((int)this.player.getSpeed()+1)));
			}

			// set powerups as its previous + the dx(direction of player) * its speed
			this.immunPower.relocate(this.immunPower.getX()+(this.player.getDx()*((int)this.player.getSpeed()+1)), this.immunPower.getY()+(this.player.getDy()*((int)this.player.getSpeed()+1)));
			this.speedPower.relocate(this.speedPower.getX()+(this.player.getDx()*((int)this.player.getSpeed()+1)), this.speedPower.getY()+(this.player.getDy()*((int)this.player.getSpeed()+1)));

			// loop through enemies
			for (Fish fish:this.fishes) {
				if (fish != this.player){
					// set enemy as its previous + the dx(direction of player) * its speed
					fish.relocate(fish.getX()+(this.player.getDx()*((int)this.player.getSpeed()+1)), fish.getY()+(this.player.getDy()*((int)this.player.getSpeed()+1)));
				}
			}
		}
	}

	// method that will stop the player's movement; set the player's DX and DY to 0
	private void stopPlayer(KeyCode key){
		if (key==KeyCode.W || key==KeyCode.S) {
			this.player.setDY(0);
		} else {
			this.player.setDX(0);
		}
	}

	// getter
	Player getPlayer() {
		return this.player;
	}
}