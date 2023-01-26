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

public class MainMenu {
	private Scene scene;
	private Stage stage;
	private InstructionStage instruction;
	private AboutStage about;
	private ResultStage result;
	private MainMenu menu;

	public MainMenu(Stage stage) {
		this.menu = this; // for use inside convenience method
		this.stage = stage;
        this.instruction = new InstructionStage(this); // instantiate menu stages
        this.result = new ResultStage(this);
        this.about = new AboutStage(this.stage, this);

		stage.setTitle("Feeding Frenzy"); // set title, icon, make unresizable

		Image icon = new Image("images/fish.png");
		stage.getIcons().add(icon);

		stage.setResizable(false);

		// declares the background img
		Image bg = new Image("images/mainbg.gif", GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT, false, false);
		ImageView viewbg = new ImageView();
		viewbg.setImage(bg);

		StackPane root = new StackPane(); // root

		VBox vbox = new VBox(); // vbox for buttons
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(15);

        // sets the button (play) background
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
        this.setMouseHandler(b1, 1);

        // sets the button (instructions) background
        Image imgins = new Image("images/finalinsb.png");
        ImageView viewins = new ImageView(imgins);
        // sets the button (instructions) size
        viewins.setFitHeight(40);
        viewins.setPreserveRatio(true);
        // creates button (instructions)
        Button b2 = new Button();
        // position of the button (instructions)
        b2.setTranslateX(-320);
        b2.setTranslateY(-299);
        b2.setBackground(null);
        b2.setGraphic(viewins);
        this.setMouseHandler(b2, 2);

        // sets the button (results) background
        Image imgres = new Image("images/finalresultsb.png");
        ImageView viewres = new ImageView(imgres);
        // sets the button (results) size
        viewres.setFitHeight(40);
        viewres.setPreserveRatio(true);
        // creates button (results)
        Button b3 = new Button();
        // position of the button (results)
        b3.setTranslateX(-372);
        b3.setTranslateY(-363);
        b3.setBackground(null);
        b3.setGraphic(viewres);
        this.setMouseHandler(b3, 3);

        // sets the button (about) background
        Image imgab = new Image("images/finalaboutb.png");
        ImageView viewab = new ImageView(imgab);
        // sets the button (about) size
        viewab.setFitHeight(40);
        viewab.setPreserveRatio(true);
        // creates button (about)
        Button b4 = new Button();
        // position of the button (about)
        b4.setTranslateX(372);
        b4.setTranslateY(-421);
        b4.setBackground(null);
        b4.setGraphic(viewab);
        this.setMouseHandler(b4, 4);

        // sets the button (exit) background
        Image imgex = new Image("images/finalexitb.png");
        ImageView viewex = new ImageView(imgex);
        // sets the button (exit) size
        viewex.setFitHeight(40);
        viewex.setPreserveRatio(true);
        // creates button (exit)
        Button b5 = new Button();
        // position of the button (exit)
        b5.setTranslateX(320);
        b5.setTranslateY(-484);
        b5.setBackground(null);
        b5.setGraphic(viewex);
        this.setMouseHandler(b5, 5);

		vbox.getChildren().addAll(b1,b2,b3,b4,b5);
		root.getChildren().addAll(viewbg, vbox);
		this.scene = new Scene(root, GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
	}

	private void setMouseHandler(Button b, int num) { // set event handler
		b.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				switch(num) { // based on num, branch to a different scene
				case 1: // new game
					GameStage theGameStage = new GameStage(menu); // create game stage, set scene to it, start its stopwatch
                	stage.setScene(theGameStage.getScene());
                	theGameStage.getStopwatch().start();
                	break;
				case 2:
					stage.setScene(instruction.getScene());
					break;
				case 3:
					stage.setScene(result.getScene());
					break;
				case 4:
					stage.setScene(about.getScene());
					break;
				case 5:
					System.exit(0);
				}
			}
		});
	}

	// setter
	void setStage() {
		this.stage.setScene(this.scene);
	}

	// getter
	public Scene getScene() {
		return this.scene;
	}

	// getter
	Stage getStage() {
		return this.stage;
	}

	// getter
	ResultStage getResultStage() {
		return this.result;
	}

}
