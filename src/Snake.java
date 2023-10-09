import java.awt.Point;
import java.util.ArrayList;
/**
 * 
 * @author zachgendreau
 * Instructions for snake object.
 * Used by snakeGUI in gameplay.
 */
public class Snake implements SnakeInterface {
	
	private final int DEFAULT_START_TAIL_LENGTH = 5;
	private int tailLength;
	private ArrayList<Point> path;
	private Direction currDirection;
	private Point previousTail;
	
	//constructors
	public Snake(int startingX, int startingY) {
		path =  new ArrayList<Point>();
		path.add(new Point(startingX, startingY));
		path.add(new Point(startingX, startingY - 1));
		path.add(new Point(startingX, startingY - 2));
		path.add(new Point(startingX, startingY - 3));
		path.add(new Point(startingX, startingY - 4));
		tailLength = DEFAULT_START_TAIL_LENGTH;
		currDirection = SnakeInterface.Direction.Right;
	}
	
	public Snake(Point startingPoint) {
		path.add(startingPoint);
		path.add(new Point(startingPoint.x, startingPoint.y - 1));
		path.add(new Point(startingPoint.x, startingPoint.y - 2));
		path.add(new Point(startingPoint.x, startingPoint.y - 3));
		path.add(new Point(startingPoint.x, startingPoint.y - 4));
		tailLength = DEFAULT_START_TAIL_LENGTH;
		currDirection = SnakeInterface.Direction.Right;
	}
	
	public Snake(Point startingPoint, int tailLength) {
		path.add(startingPoint);
		path.add(new Point(startingPoint.x, startingPoint.y - 1));
		path.add(new Point(startingPoint.x, startingPoint.y - 2));
		path.add(new Point(startingPoint.x, startingPoint.y - 3));
		path.add(new Point(startingPoint.x, startingPoint.y - 4));
		this.tailLength = tailLength;
		currDirection = SnakeInterface.Direction.Right;
	}

	@Override
	public Point getHead() {
		return path.get(0);
	}

	@Override
	public Point getPreviousEnd() {
		return previousTail;
	}

	@Override
	public Point[] getSnake() {
		Point[] pointList = new Point[tailLength];
		for(int i = 0; i < tailLength; i++) {
			pointList[i] = path.get(i);
		}
		return pointList;
	}

	@Override
	public int getNumLocationsVisited() {
		return path.size();
	}

	@Override
	public void changeDirection(SnakeInterface.Direction direction) {
		if(direction != currDirection) {
			if(direction == SnakeInterface.Direction.Up && currDirection != SnakeInterface.Direction.Down) {
				currDirection = SnakeInterface.Direction.Up;
			}
			if(direction == SnakeInterface.Direction.Down && currDirection != SnakeInterface.Direction.Up) {
				currDirection = SnakeInterface.Direction.Down;
			}
			if(direction == SnakeInterface.Direction.Left && currDirection != SnakeInterface.Direction.Right) {
				currDirection = SnakeInterface.Direction.Left;
			}
			if(direction == SnakeInterface.Direction.Right && currDirection != SnakeInterface.Direction.Left) {
				currDirection = SnakeInterface.Direction.Right;
			}
		}
	}

	@Override
	public Point move() {
		int currX = (int)getHead().getX();
		int currY = (int)getHead().getY();
		int newX = currX;
		int newY = currY;
		if(currDirection == SnakeInterface.Direction.Up) {
			newX = currX - 1;
			newY = currY;
		}
		if(currDirection == SnakeInterface.Direction.Down) {
			newX = currX + 1;
			newY = currY;
		}
		if(currDirection == SnakeInterface.Direction.Left) {
			newX = currX;
			newY = currY - 1;
		}
		if(currDirection == SnakeInterface.Direction.Right) {
			newX = currX;
			newY = currY + 1;
		}
		Point newPoint = new Point(newX, newY);
		path.add(0, newPoint);
		previousTail = path.get(tailLength);
		
		return newPoint;
		
	}

	@Override
	public Point move(SnakeInterface.Direction direction) {
		int currX = (int)getHead().getX();
		int currY = (int)getHead().getY();
		int newX = currX;
		int newY = currY;
		
		if(direction == SnakeInterface.Direction.Up && currDirection != SnakeInterface.Direction.Down) {
			newX = currX - 1;
			newY = currY;
		}
		if(direction == SnakeInterface.Direction.Down && currDirection != SnakeInterface.Direction.Up) {
			newX = currX + 1;
			newY = currY;
		}
		if(direction == SnakeInterface.Direction.Left && currDirection != SnakeInterface.Direction.Right) {
			newX = currX;
			newY = currY - 1;
		}
		if(direction == SnakeInterface.Direction.Right && currDirection != SnakeInterface.Direction.Left) {
			newX = currX;
			newY = currY + 1;
		}
		Point newPoint = new Point(newX, newY);
		path.add(0, newPoint);
		previousTail = path.get(tailLength);
		
		return newPoint;
	}

	@Override
	public boolean collisionOccurred() {
		boolean collision = false;
		for(int i = 0; i < getSnake().length; i++) {
			Point currPoint = getSnake()[i];
			for(int j = 0; j < getSnake().length; j++) {
				Point checkPoint = getSnake()[j];
				if((j != i) && currPoint.equals(checkPoint)) {
					collision = true;
				}
			}
		}
		return collision;
	}

	@Override
	public boolean isGameOver(int width, int height) {
		boolean gameOver = false;
	
		if(getHead().x <= width) {
			gameOver = false;
		}
		else {
			gameOver = true;
		}
		
	
		if(getHead().y <= height) {
			gameOver = false;
		}
		else {
			gameOver = true;
		}
		
		if(collisionOccurred()) {
			gameOver = true;
		}
		return gameOver;
	}

	@Override
	public void increaseLength() {
		tailLength++;
	}
	
	public String toString() {
		Point[] currSnake = getSnake();
		String output = "";
		for(int i = 0; i < currSnake.length; i++) {
			output += currSnake[i];
		}
		return output;
	}
}
