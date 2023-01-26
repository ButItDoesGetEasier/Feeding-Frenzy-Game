package game;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class ResultStage {
	private Scene scene;
	private int foodsEaten, enemiesEaten, currentSize;
	private String time;
	private Canvas canvas;
	private MainMenu menu;

	ResultStage(MainMenu menu) {
		this.menu = menu; // set menu and canvas values
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		GraphicsContext gc = canvas.getGraphicsContext2D();

		Image bg = new Image("images/results.png", GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT, false, false); // declare the background img
		gc.drawImage(bg, 0, 0);

		this.foodsEaten = 0; // initial stats to display
        this.enemiesEaten = 0;
        this.currentSize = Fish.INIT_SIZE;
        this.time = "00:00:00";

		fixScene(); // fix scene elements
	}

	void fixScene() {
		StackPane root = new StackPane(); // root

		VBox vbox = new VBox(); // vbox for stats text and button
	    vbox.setAlignment(Pos.CENTER);
	    vbox.setSpacing(15);

	    // sets the button (back to main menu) background
        Image imgmenu = new Image("images/mainmenu.png");
        ImageView viewmenu = new ImageView(imgmenu);
        // sets the button (back to main menu) size
        viewmenu.setFitHeight(40);
        viewmenu.setPreserveRatio(true);
        // creates button (back to main menu)
        Button b1 = new Button();
        // position of the button (back to main menu)
        b1.setTranslateX(320);
        b1.setTranslateY(-395);
        b1.setBackground(null);
        b1.setGraphic(viewmenu);

	    // sets the button (exit) background
        Image imgex = new Image("images/finalexitb.png");
        ImageView viewex = new ImageView(imgex);
        // sets the button (exit) size
        viewex.setFitHeight(40);
        viewex.setPreserveRatio(true);
        // creates button (exit)
        Button b2 = new Button();
        // position of the button (exit)
        b2.setTranslateX(370);
        b2.setTranslateY(-458);
        b2.setBackground(null);
        b2.setGraphic(viewex);

        // displayer text based on stored values
	    Text txt1 = new Text(10, 50, "FOOD EATEN: " + this.foodsEaten + "\nENEMIES EATEN: " + this.enemiesEaten
	    			+ "\nCURRENT SIZE: " + this.currentSize + "\nTIME ALIVE: " + this.time);
		txt1.setFont(Font.font("arial", FontWeight.THIN, 20));
		txt1.setTranslateX(-10);
		txt1.setTranslateY(-60);
		txt1.setLineSpacing(1);

		// set event handlers
	    b1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            	menu.getStage().setScene(menu.getScene());
            }
        });

	    b2.setOnMouseClicked(new EventHandler<MouseEvent>() {
		       @Override
		       public void handle(MouseEvent e) {
		    	   System.exit(0);
		       }
		 });

		vbox.getChildren().addAll(txt1, b1, b2);
		root.getChildren().addAll(canvas, vbox);
		this.scene = new Scene(root, GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
	}

	// getter
	Scene getScene() {
		return this.scene;
	}

	// set stats based on gs
	void setStats (GameStage gs) {
		this.foodsEaten = gs.getPlayer().getFoodsEaten();
		this.enemiesEaten = gs.getPlayer().getEnemiesEaten();
		this.currentSize = gs.getPlayer().getSize();
		this.time = gs.getStopwatch().getTime();
		this.fixScene(); // upon value update, refix scene with new stats
	}
 }
