package brickBreaker;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class MapGenerator {
	public int map[][];
	public int brickWidth;
	public int brickHeight;
	
	// creating rows and columns of bricks
	public MapGenerator(int row, int col) {
		map = new int[row][col];
		for (int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[0].length; j++) {
				// adding one because it detects whether brick has been touched by ball
				map[i][j] = 1;
			}
		}
		
		brickWidth = 540 / col;
		brickHeight = 150 / row;
	}
	
	// function to draw bricks
	public void draw(Graphics2D g) {
		for (int i = 0; i < map.length; i++) {
			for(int j = 0; j < map[0].length; j++) {
				if (map[i][j] > 0) {
					// if greater than 0, create vertical grid in that position
					g.setColor(Color.white);
					g.fillRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
					
					g.setStroke(new BasicStroke(3));
					g.setColor(Color.black);
					g.drawRect(j * brickWidth + 80, i * brickHeight + 50, brickWidth, brickHeight);
				}
			}
		}
	}
	
	// giving each brick a value to allow intersection between ball and brick
	public void setBrickValue(int value, int row, int col) {
		map[row][col] = value;
	}
}
