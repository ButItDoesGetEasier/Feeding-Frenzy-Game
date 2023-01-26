package game;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AboutStage {
	private Scene scene;
	private Stage stage;
	private DeveloperStage devs;
	private ReferenceStage ref;
	private MainMenu menu;

	AboutStage(Stage stage, MainMenu menu) {
		this.stage = stage;

		Image bg = new Image("images/about1.png", GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT, false, false); // declare the background img
		ImageView viewbg = new ImageView();
		viewbg.setImage(bg);

		StackPane root = new StackPane(); // root

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
        b1.setTranslateY(-230);
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
        b2.setTranslateY(-293);
        b2.setBackground(null);
        b2.setGraphic(viewex);

        // sets the button (view developers) background
        Image imgdev = new Image("images/devs.png");
        ImageView viewdev = new ImageView(imgdev);
        // sets the button (exit) size
        viewdev.setFitHeight(80);
        viewdev.setPreserveRatio(true);
        // creates button (exit)
        Button b3 = new Button();
        // position of the button (exit)
        b3.setTranslateX(-210);
        b3.setTranslateY(70);
        b3.setBackground(null);
        b3.setGraphic(viewdev);

        // sets the button (view references) background
        Image imgref = new Image("images/ref.png");
        ImageView viewref = new ImageView(imgref);
        // sets the button (exit) size
        viewref.setFitHeight(80);
        viewref.setPreserveRatio(true);
        // creates button (exit)
        Button b4 = new Button();
        // position of the button (exit)
        b4.setTranslateX(220);
        b4.setTranslateY(-30);
        b4.setBackground(null);
        b4.setGraphic(viewref);

        this.devs = new DeveloperStage(this);
        this.ref = new ReferenceStage(this);
        this.menu = menu;

        // set event handlers
	    b1.setOnMouseClicked(new EventHandler<MouseEvent>() {
           @Override
            public void handle(MouseEvent e) {
        	   menu.setStage();
            }
        });

	    b2.setOnMouseClicked(new EventHandler<MouseEvent>() {
	       @Override
	       public void handle(MouseEvent e) {
	    	   System.exit(0);
	       }
	    });

	    b3.setOnMouseClicked(new EventHandler<MouseEvent>() {
	       @Override
	       public void handle(MouseEvent e) {
	    	   stage.setScene(devs.getScene());
	       }
	    });

	    b4.setOnMouseClicked(new EventHandler<MouseEvent>() {
		   @Override
		   public void handle(MouseEvent e) {
			   stage.setScene(ref.getScene());
		   }
		});

		vbox.getChildren().addAll(b1, b2, b3, b4);
		root.getChildren().addAll(viewbg, vbox);
		this.scene = new Scene(root, GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
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
