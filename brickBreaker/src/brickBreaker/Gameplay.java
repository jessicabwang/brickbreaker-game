package brickBreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;

import javax.swing.JPanel;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
	private boolean play = false;
	private int score = 0;

	private int totalBricks = 21;

	// creating timer
	private Timer timer;
	private int delay = 8;

	// starting position of slider
	private int playerX = 310;

	private int ballposX = 120;
	private int ballposY = 350;
	private int ballXdir = -1;
	private int ballYdir = -2;

	private MapGenerator map;

	public Gameplay() {
		// creating brick wall obj with 3 rows and 7 columns
		map = new MapGenerator(3, 7);

		// make items pop up when Gameplay obj is created
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);
		timer.start();
	}

	public void paint(Graphics g) {
		// background
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);

		// draw map
		map.draw((Graphics2D)g);

		// borders
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);

		// scores
		g.setColor(Color.white);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString(""+score,  590,  30);

		// paddle
		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 8);

		// ball
		g.setColor(Color.yellow);
		g.fillOval(ballposX, ballposY, 20, 20);

		// winning game: no more bricks
		if (totalBricks <= 0) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.red);
			// You Won message
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("You Won!",  260,  300);

			// Restart message
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press Enter to Restart",  230,  350);

		}

		// Game Over: check if ball misses paddle
		if (ballposY > 570) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.red);

			// Game Over message
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Game Over, Score: ",  190,  300);

			// Restart message
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press Enter to Restart",  230,  350);
		}

		g.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// start timer
		timer.start();

		// move ball
		if (play) {
			// check if ball has touched paddle
			if (new Rectangle(ballposX, ballposY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
				ballYdir = -ballYdir;
			}

			// check if ball hits brick
			A: for (int i = 0; i < map.map.length; i++) { // first map is map generator, second map is array
				for(int j = 0; j < map.map[0].length; j++) {
					if (map.map[i][j] > 0) {
						// check position of ball and brick
						int brickX = j * map.brickWidth + 80;
						int brickY = i * map.brickHeight + 50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;

						// creating rectangle around brick
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);
						Rectangle brickRect = rect;

						// checking if ball intersects brick
						if (ballRect.intersects(brickRect)) {
							// change brick value to 0
							map.setBrickValue(0, i, j);
							// reset total number of bricks 
							totalBricks--;
							// increase score
							score += 5;

							if (ballposX + 19 <= brickRect.x || ballposX + 1 >= brickRect.x + brickRect.width) {
								// move ball to opposite direction after hitting brick
								ballXdir = -ballXdir;
							}
							else {
								ballYdir = -ballYdir;
							}
							// add break point to remove compiler from loop
							break A;
						}
					}
				}
			}

			// check if ball bounces off the walls, meaning it changes direction (becomes negative)
			ballposX += ballXdir;
			ballposY += ballYdir;
			// check left
			if(ballposX < 0) {
				ballXdir = -ballXdir;
			}
			// check top
			if(ballposY < 0) {
				ballYdir = -ballYdir;
			}
			// check right
			if(ballposX > 670) {
				ballXdir = -ballXdir;
			}
		}

		// call paint() method againt to redraw components
		repaint();

	}

	@Override
	public void keyTyped(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyPressed(KeyEvent e) {
		// move right and left with arrow keys
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (playerX >= 600) { // keep within border of paddle
				playerX = 600;
			}
			else {
				moveRight();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (playerX < 10) { // keep within border
				playerX = 10;
			}
			else {
				moveLeft();
			}
		}

		// if user presses ENTER to restart
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			if (!play) {
				play = true;
				ballposX = 120;
				ballposY = 350;
				ballXdir = -1;
				ballYdir = -2;
				playerX = 310;
				score = 0;
				totalBricks = 21;
				map = new MapGenerator(3, 7);

				repaint();
			}
		}
	}

	public void moveRight() {
		play = true;
		playerX += 20;
	}

	public void moveLeft() {
		play = true;
		playerX -= 20;
	}


}
