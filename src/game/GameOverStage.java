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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class GameOverStage {
	private Scene scene;
	private Canvas canvas;

	GameOverStage(MainMenu menu, GameStage gs) {
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		GraphicsContext gc = canvas.getGraphicsContext2D();

		Image bg = new Image("images/gameover.png", GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT, false, false); // declare the background img
		gc.drawImage(bg, 0, 0);

		this.fixScene(menu, gs); // set scene elements
	}

	// getter
	Scene getScene() {
		return this.scene;
	}

	void fixScene (MainMenu menu, GameStage gs) {
		menu.getResultStage().setStats(gs); // set new stats in the result stage

		StackPane root = new StackPane(); // root

		VBox vbox = new VBox(); // vbox for stats and buttons
	    vbox.setAlignment(Pos.CENTER);
	    vbox.setSpacing(15);

	    StackPane stats = new StackPane(); // stackpane for text, background of text
	    Rectangle rect = new Rectangle(GameStage.WINDOW_WIDTH/4, GameStage.WINDOW_HEIGHT/5);
	    rect.setFill(Color.AQUAMARINE); // set color, size, and opacity
	    rect.setOpacity(0.50);

	    // text would contain food and enemies eaten, size, and time alive of gs' player
	    Text txt = new Text(10, 50, "GAME SUMMARY\nFOOD EATEN: " + gs.getPlayer().getFoodsEaten() + "\nENEMIES EATEN: "
	    + gs.getPlayer().getEnemiesEaten() + "\nCURRENT SIZE: " + gs.getPlayer().getSize() + "\nTIME ALIVE: " + gs.getStopwatch().getTime());
	    txt.setFont(Font.font("arial",FontWeight.SEMI_BOLD, 16));
	    txt.setTextAlignment(TextAlignment.CENTER);
	    txt.setLineSpacing(1);
	    stats.getChildren().addAll(rect,txt);

	    // button for play again
	    Image img = new Image("images/finalplayb.png");
        ImageView view = new ImageView(img);
        // sets the button (play) size
        view.setFitHeight(100);
        view.setPreserveRatio(true);
        // creates button (play)
        Button b1 = new Button();
        // position of the button (play)
        b1.setTranslateX(0);
        b1.setTranslateY(20);
        b1.setBackground(null);
        b1.setGraphic(view);

	    // button for back to main menu
        Image imgmenu = new Image("images/mainmenu.png");
        ImageView viewmenu = new ImageView(imgmenu);
        // sets the button (exit) size
        viewmenu.setFitHeight(40);
        viewmenu.setPreserveRatio(true);
        // creates button (exit)
        Button b2 = new Button();
        // position of the button (exit)
        b2.setTranslateX(320);
        b2.setTranslateY(-395);
        b2.setBackground(null);
        b2.setGraphic(viewmenu);

        // set event handlers
	    b1.setOnMouseClicked(new EventHandler<MouseEvent>() {
	    	@Override
	    	public void handle(MouseEvent e) {
	    		GameStage theGameStage = new GameStage(menu);
            	menu.getStage().setScene(theGameStage.getScene());
            	theGameStage.getStopwatch().start();
	    	}
	    });

	    b2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            	menu.setStage();
            }
        });

		vbox.getChildren().addAll(stats, b1, b2);
		root.getChildren().addAll(this.canvas, vbox);
		this.scene = new Scene(root, GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
	}
}