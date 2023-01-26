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
import javafx.stage.Stage;

public class ReferenceStage {
	private Stage stage;
	private Scene scene;

	ReferenceStage(AboutStage about) {
		Canvas canvas = new Canvas(GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		GraphicsContext gc = canvas.getGraphicsContext2D();

		Image bg = new Image("images/references.png", GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT, false, false); // declare the background img
		gc.drawImage(bg, 0, 0);

		StackPane root2 = new StackPane(); // root

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
        b1.setTranslateY(-320);
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
		root2.getChildren().addAll(canvas, vbox);
		this.scene = new Scene(root2, GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
	}

	// setter
	void setStage() {
		this.stage.setScene(this.scene);
	}

	// getter
	Scene getScene() {
		return this.scene;
	}

	// getter
	Stage getStage() {
		return this.stage;
	}

}
