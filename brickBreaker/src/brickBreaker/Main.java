package brickBreaker;

import javax.swing.JFrame;

public class Main {

	public static void main (String[] args) {
		// creating the frame for game
		JFrame obj = new JFrame();
		// creating game object
		Gameplay gamePlay = new Gameplay();
		obj.setBounds(10, 10, 700, 600);
		obj.setTitle("Breakout Ball");
		obj.setResizable(false);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// adding game panel to frame
		obj.add(gamePlay);
		obj.setVisible(true);
	}
}
