import java.awt.Color;
import java.awt.Graphics;


import javax.swing.JButton;
/**
 * 
 * @author zachgendreau
 * Base component of snake board. Changes
 * colors to indicate if snake is present
 * on tile
 */
@SuppressWarnings("serial")
public class SnakeTile extends JButton{
		public enum on {yes, no};
		private on visited;
		@SuppressWarnings("unused")
		private SnakeSettings settings;
		private Color snakeColor;
		
		// construcor for single tile
		public SnakeTile(SnakeSettings settings) {
			this.settings = settings;
			snakeColor = settings.getColor();
			visited = on.no;
			this.setBackground(Color.gray);
			this.setOpaque(true); 
			this.setBorderPainted(false);
		}
		
		// indicates when a tile is currently occupied by the snake
		public void visit() {
			visited = on.yes;
		}
		/**
		 * Sets type of occupation status
		 * @param on
		 */
		public void setType(on on) {
			visited = on;
		}
		
		/**
		 * Paint component class for coloring of tile
		 */
		public void paintComponent(Graphics g) {
			if(visited == on.yes) {
				this.setBackground(snakeColor);
			}
			else if(visited == on.no) {
				this.setBackground(Color.red);
			}
			
		}

}
