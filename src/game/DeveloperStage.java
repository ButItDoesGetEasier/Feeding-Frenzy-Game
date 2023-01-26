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

public class DeveloperStage {
	private Scene scene;

	DeveloperStage(AboutStage about) {
		Canvas canvas = new Canvas(GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		GraphicsContext gc = canvas.getGraphicsContext2D();

		// sets the background
		Image bg = new Image("images/developers.png", GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT, false, false); // declare the background img
		gc.drawImage(bg, 0, 0);

		StackPane root1 = new StackPane(); // root

		VBox vbox = new VBox(); // vbox for buttons
	    vbox.setAlignment(Pos.CENTER);
	    vbox.setSpacing(15);

	    // sets the button (back to main menu) background
        Image imgmenu = new Image("images/mainmenu.png");
        ImageView viewmenu = new ImageView(imgmenu);
        // sets the button (exit) size
        viewmenu.setFitHeight(40);
        viewmenu.setPreserveRatio(true);
        // creates button (exit)
        Button b1 = new Button();
        // position of the button (exit)
        b1.setTranslateX(320);
        b1.setTranslateY(-318);
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
        b2.setTranslateY(-382);
        b2.setBackground(null);
        b2.setGraphic(viewex);

        // set event handlers
	    b1.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
            	about.setStage();
            }
        });

	    b2.setOnMouseClicked(new EventHandler<MouseEvent>() {
	           @Override
	            public void handle(MouseEvent e) {
	        	   System.exit(0);
	            }
	    });

		vbox.getChildren().addAll(b1, b2);
		root1.getChildren().addAll(canvas, vbox);
		this.scene = new Scene(root1, GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
	}

	// getter
	Scene getScene() {
		return this.scene;
	}
}


