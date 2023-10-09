import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;




/**
 * 
 * @author zachgendreau
 * Graphical user interface for 
 * snake game. Manages board, reset,
 * and points panels
 */
@SuppressWarnings("serial")
public class SnakeGUI extends JComponent implements KeyListener {
	private final int DEFAULT_WINDOW_WIDTH = 800;
	private final int DEFAULT_WIDOW_HEIGHT = 500;
	private Timer timer;
	private JButton resetButton;
	private SnakeSettings settings;
	private Snake snake;
	private SnakeTile[][] board;
	private SnakeSettingsPanel setPanel;
	private JPanel boardPanel = new JPanel();
	private long points;
	private int level;
	private int width;
	private int height;
	private int highScore;
	private JPanel resetPanel;
	private JPanel pointsPanel;
	private JLabel pointsLabel;
	private JLabel pointsNum;
	private JLabel levelLabel;
	private JLabel levelNum;
	private JLabel highLabel;
	private JLabel highNum;
	
	// Main method
	public static void main(String[] args) {
		JFrame frame = new JFrame("Snake Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SnakeGUI newGame = new SnakeGUI();
		frame.getContentPane().add(newGame);
		frame.pack();
		frame.setVisible(true);

	}

	/**
	 * Sets items that should not change over the course
	 * of multiple games. Calls setupGUI() to create initiol board
	 */
	public SnakeGUI() {
		highScore = 0;
		highNum = new JLabel("0");
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(DEFAULT_WINDOW_WIDTH, DEFAULT_WIDOW_HEIGHT));
		settings = new SnakeSettings();
		highScore = 0;
		highNum = new JLabel("0");
		setupGUI();
	}
	
	
	/**
	 * Method to setup components of GUI. Called initially 
	 * in constructor and during reset method
	 */
	private void setupGUI() {
		this.removeAll();
		this.addKeyListener(this);
		this.setFocusable(true);
        this.requestFocusInWindow();
        boardPanel.removeAll();
        width = settings.getWidth();
		height = settings.getHeight();
		board = new SnakeTile[width][height];
		boardPanel.setLayout(new GridLayout(width, height, 1, 1));
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				SnakeTile newTile = new SnakeTile(settings);
				newTile.setOpaque(true); 
				newTile.setBorderPainted(false);
				newTile.setPreferredSize(new Dimension(10, 10));
				boardPanel.add(newTile);
				board[i][j] = newTile;
			}
		}
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				SnakeTile currTile = board[i][j];
				currTile.setType(SnakeTile.on.no);
				currTile.repaint();
			}
		}
		snake = new Snake(7, 7);
		setPanel = new SnakeSettingsPanel(settings);
		resetButton = new JButton("Reset");
		resetButton.addActionListener(new ResetButtonListener());
		resetPanel = new JPanel();
		resetPanel.add(resetButton);
		resetPanel.setPreferredSize(new Dimension(100, 30));
		
	
		setupPointPanel();
		this.add(resetPanel, BorderLayout.SOUTH);
		this.add(boardPanel, BorderLayout.CENTER);
		this.add(pointsPanel, BorderLayout.NORTH);
	
		
	
		startAnimation();
	}
	
	
	/**
	 * Method to reset game. Called from settings panel
	 */
	public void resetGame() {
		timer.stop();
		if(Integer.parseInt(pointsNum.getText()) > highScore) {
			highScore = Integer.parseInt(pointsNum.getText());
			highNum.setText("" + highScore);
		}
		int dialogButton = JOptionPane.showInternalConfirmDialog(null, setPanel, "Play the Game? Choose your Options", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
		if(dialogButton == JOptionPane.YES_OPTION) {
			setupGUI();
		}
		else {
			System.exit(0);
		}
	}
	
	/**
	 * Helper method to setup points panel at top of GUI
	 */
	public void setupPointPanel() {
		points = 0;
		level = 0;
		pointsPanel = new JPanel();
		pointsLabel = new JLabel("Points");
		pointsNum = new JLabel("0");
		levelLabel = new JLabel("Level");
		levelNum = new JLabel("0");
		highLabel = new JLabel("High Score");
		
		pointsPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(10, 10, 10, 10);
        
        constraints.gridx = 0;
        constraints.gridy = 0;     
        pointsPanel.add(pointsLabel, constraints);
       
        constraints.gridy = 1;
        pointsPanel.add(pointsNum, constraints);
       
        constraints.gridx = 5;
        constraints.gridy = 0;     
        pointsPanel.add(highLabel, constraints);
         
        constraints.gridy = 1;
        pointsPanel.add(highNum, constraints);
		
         
        constraints.gridx = 10;
        constraints.gridy = 0;     
        pointsPanel.add(levelLabel, constraints);
         
        constraints.gridy = 1;
        pointsPanel.add(levelNum, constraints);
		
	}
	
	// ActionListener for when reset button is pressed
	public class ResetButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			resetGame();
		}
	}
	
	// updates level every 10 points and ioncreases length of snake by 1 tile
	public void updateLevel() {
		if(points % 10 == 0) {
			level++;
			levelNum.setText("" + level);
			snake.increaseLength();
		}
	}
	
	// update points every tile reached(timer intermission)
	public void updatePoints() {
		points++;
		pointsNum.setText("" + points);
	}
	
	// starts animation of snake, including timer
	public void startAnimation() {
		
		ActionListener taskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				advance();
				updateSnake();
			}
		};
		int speed = getIntegerSpeed(settings.getSpeed());
		timer = new Timer(speed, taskPerformer);
		timer.start();
	}
	
	// advances the snake every timer intermission
	public void advance() {
		snake.move();
	}
	
	// updates snake data evry timer intermission and repaints snake
	public void updateSnake() {
		Point[] currSnake = snake.getSnake();
		try {
			for(int i = 0; i < currSnake.length; i++) {
				int snakeX = (int)currSnake[i].getX();
				int snakeY = (int)currSnake[i].getY();
				SnakeTile currTile = board[snakeX][snakeY];
				currTile.visit();
				currTile.repaint();
			}
			int prevX = snake.getPreviousEnd().x;
			int prevY = snake.getPreviousEnd().y;
			SnakeTile prevEnd = board[prevX][prevY];
			prevEnd.setType(SnakeTile.on.no);
			prevEnd.repaint();
		}
		catch(ArrayIndexOutOfBoundsException aoob) {
			resetGame();
		}
	
		
		if(snake.isGameOver(width, height)) {
			resetGame();
		}
		
		updatePoints();
		updateLevel();
	
	}


	/**
	 * @Override
	 * takes keystrokes and changes direction of snake
	 */
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		switch(key) {
			case KeyEvent.VK_LEFT :
				snake.changeDirection(SnakeInterface.Direction.Left);
				break;
			case KeyEvent.VK_RIGHT :
				snake.changeDirection(SnakeInterface.Direction.Right);
				break;
			case KeyEvent.VK_UP :
				snake.changeDirection(SnakeInterface.Direction.Up);
				break;
			case KeyEvent.VK_DOWN :
				snake.changeDirection(SnakeInterface.Direction.Down);
				break;
        }
		
	}
	
	// converts enum speed to integer speed used by timer
	public int getIntegerSpeed(SnakeSettings.Speed speed) {
		int outputSpeed = 0;
		if(speed == SnakeSettings.Speed.slow) {
			outputSpeed = 125;
		}
		else if(speed == SnakeSettings.Speed.medium) {
			outputSpeed = 100;
		}
		else if(speed == SnakeSettings.Speed.fast) {
			outputSpeed = 50;
		}
		return outputSpeed;
	}
	
	
	// unused but necessary
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	

}
