package game;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GameStage {
	public static final int WINDOW_HEIGHT = 800;
	public static final int WINDOW_WIDTH = 800;
	public static final int MAP_SIZE = 2400;
	private Scene scene;
	private Group root;
	private Canvas canvas;
	private GraphicsContext gc;
	private GameTimer gametimer;
	private Image bg;
	private Player player;
	private Canvas statusBarCanvas;
	private GraphicsContext statbarGc;
	private StopwatchTimer stopwatch;
	private GameOverStage gameover;
	private int bgX, bgY;

	//the class constructor
	GameStage(MainMenu menu) {
		this.root = new Group();

		this.canvas = new Canvas(GameStage.MAP_SIZE,GameStage.MAP_SIZE); // canvas of background
		this.gc = canvas.getGraphicsContext2D();

		this.statusBarCanvas = new Canvas(GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT); // canvas of status bar
		this.statbarGc = statusBarCanvas.getGraphicsContext2D();

		this.bg = new Image("images/bg.png", GameStage.MAP_SIZE, GameStage.MAP_SIZE, false, false); // declare the background img
		this.bgX = (GameStage.MAP_SIZE/3) * -1; // so that initial window presents the center of the map: divide map by 3, negate
		this.bgY = (GameStage.MAP_SIZE/3) * -1;

		this.player = new Player(); // declare player

		this.root.getChildren().addAll(canvas, statusBarCanvas); // add canvases to the root

		// assign scene
		this.scene = new Scene(root, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);

		//instantiate an animation timer and stopwatch (for time alive)
		this.gametimer = new GameTimer(this.gc, this.scene, this, menu, player); //
		this.stopwatch = new StopwatchTimer();

		this.gameover = new GameOverStage(menu, this);
		this.gametimer.start(); // start timer
	}

	// getter
	Scene getScene() {
		return this.scene;
	}

	// getter
	Player getPlayer() {
		return this.player;
	}

	// getter
	Image getBG() {
		return this.bg;
	}

	// getter
	int getBgX() {
		return this.bgX;
	}

	// getter
	int getBgY() {
		return this.bgY;
	}

	// setter
	void setBgX(int bgX) {
		this.bgX = bgX;
	}

	// setter
	void setBgY(int bgY) {
		this.bgY = bgY;
	}

	void printGameStatusBar() {
		// background for the game status bar
		Image statBarImg = new Image("images/tempStatBar.png", GameStage.WINDOW_WIDTH/3, GameStage.WINDOW_HEIGHT/7, false, false);
		statbarGc.drawImage(statBarImg, GameStage.WINDOW_WIDTH/2-(GameStage.WINDOW_WIDTH/3)/2, 0);
		// sets font for the game status bar
		statbarGc.setFont(Font.font("arial", FontWeight.LIGHT, 15));
		// print stats from player attributes and stopwatch
		statbarGc.fillText("GAME STATUS BAR\nFOOD EATEN: "+ this.player.getFoodsEaten() +"\nENEMIES EATEN: " + this.player.getEnemiesEaten() + "\nCURRENT SIZE: " + this.player.getSize() +
							"\nTIME ALIVE: "+this.stopwatch.getTime(), GameStage.WINDOW_WIDTH/2-70, 20);
	}

	void clearStatBarGc () {
		this.statbarGc.clearRect(0, 0, GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
	}

	// getter
	StopwatchTimer getStopwatch() {
		return this.stopwatch;
	}

	// getter
	GameOverStage getGameOver() {
		return this.gameover;
	}
}

