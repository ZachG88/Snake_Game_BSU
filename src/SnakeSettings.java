import java.awt.Color;

/**
 * 
 * @author zachgendreau
 * Settings data for a snake object
 * Set by constructor, managed by GUI.
 * Displayed in SnakeSettingsPanel
 *
 */
public class SnakeSettings {
	public enum Speed {fast, medium, slow}
	public enum SnakeColor {Black, Yellow, Blue}
	private final int MINIMUM_WIDTH = 16;
	private final int MINIMUM_HEIGHT = 8;
	private final int DEFAULT_WIDTH = 40;
	private final int DEFAULT_HEIGHT = 20;
	private int width;
	private int height;
	private Speed snakeSpeed;
	private SnakeColor color;
	
	// constructor
	public SnakeSettings() {
		height = DEFAULT_WIDTH;
		width = DEFAULT_HEIGHT;
		snakeSpeed = Speed.fast;
		color = SnakeColor.Black;
	}
	
	/**
	 * returns width
	 * @return
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * sets width
	 * @param width
	 * @return
	 */
	public boolean setWidth(int width) {
		if(width > MINIMUM_WIDTH) {
			this.width = width;
			return true;
		}
		else {
			return false;
		}
	}
	
	/**returns height
	 * 
	 * @return height
	 */
	public int getHeight() {
		return height;
	}
	
	/**ets height
	 * 
	 * @param height
	 * @return
	 */
	public boolean setHeight(int height) {
		if(height > MINIMUM_HEIGHT) {
			this.height = height;
			return true;
		}
		else {
			return false;
		}
	}
	
	/** 
	 * returns speed of snake
	 * @return snakeSpeed
	 */
	public Speed getSpeed() {
		return snakeSpeed;
	}
	
	/**
	 * sets speed of snake
	 * @param speed
	 */
	public void setSpeed(Speed speed) {
		snakeSpeed = speed;
	}

	/**
	 * sets snake color
	 * @param sColor
	 */
	public void setColor(SnakeColor sColor) {
		color = sColor;
	}
	
	// gets snake color
	public Color getColor() {
		Color output = Color.BLACK;
		if(color.equals(SnakeColor.Black)) {
			output = Color.BLACK;
		}
		if(color.equals(SnakeColor.Yellow)) {
			output = Color.yellow;
		}
		if(color.equals(SnakeColor.Blue)) {
			output = Color.blue;
		}
		return output;
	}
	
	
}

