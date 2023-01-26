package game;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Item{ // superclass of food and powerup
	protected int x;
	protected int y;
	private Image img;
	private double width;
	private double height;

	Item(int x, int y) {
		this.x = x; // xpos and ypos
		this.y = y;
	}

	void loadImage(String str, int size){
		try{
			Image img = new Image(str, size, size, false, false); // declare img with corresponding imgStr, size
			this.img = img; // set image
	        this.setSize(); // set width and height of images
		} catch(Exception e){}
	}

	void relocate(int x, int y) { // setter of x and y
		this.x = x;
		this.y = y;
	} // (food) upon being eaten, change location (to maintain 50 foods across map)

	// method to set the image to the image view node
	void render(GraphicsContext gc){
		gc.drawImage(this.img, this.x, this.y);
	}

	// method to set the object's width and height properties
	private void setSize(){
		this.width = this.img.getWidth();
		this.height = this.img.getHeight();
	}

	// method that will return the bounds of an image
	Rectangle2D getBounds(){
		return new Rectangle2D(this.x, this.y, this.width, this.height);
	}

	// getter
	int getX() {
		return this.x;
	}

	// getter
	int getY() {
		return this.y;
	}
}
