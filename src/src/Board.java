// Brick Break Java
// Amber Mickler & Danny Marquez

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

public class Board extends JPanel implements KeyListener, ActionListener {

	private boolean playing;
	private int score;		//keep track of score

	private int paddleX;
	private int paddleY;
	private int paddleW;
	private int paddleH;
	private int paddleDir;

	private int ballX;
	private int ballY;
	private int ballC;
	private int ballR;
	private int ballXdir;
	private int ballYdir;

	private Timer timer;
	private int delay = 6;
	private int wins = 0;		//keep track of wins

	private Brick bricks;
	private int winCondition;
	private int x;
	private int y;

	private JLabel pressSpace;
	private JLabel gameOver;
	private JLabel scoreText;
	private JLabel pause;


	public int [] getBoardStatus(){		//returns array of game data
		int brickCount = bricks.rows * bricks.cols;
		int [] data = new int[brickCount + 3];
		int dit = 1;
		data[0] = brickCount;
		for(int i = 0; i < bricks.grid.length; ++i)
			for(int it = 0; it < bricks.grid[i].length; ++it, ++dit)
				data[dit] = bricks.grid[i][it];
			
		data[dit] = score;
		data[++dit] = wins;
		return data;

		}

	public void setScoreText(int score){
		scoreText.setText(" " + score );
	}

	public Board() {
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer = new Timer(delay, this);

		// Janky way of getting the text into the middle of the screen lol
		pressSpace = new JLabel("                  Press Space to Play");
		pressSpace.setFont(new Font("Serif", Font.BOLD, 72));
		pressSpace.setForeground(Color.WHITE);

		gameOver = new JLabel("                    GAME OVER");
		gameOver.setFont(new Font("Serif", Font.BOLD, 72));
		gameOver.setForeground(Color.RED);

		scoreText = new JLabel(" 0");
		scoreText.setFont(new Font("Serif", Font.BOLD, 32));
		scoreText.setForeground(Color.WHITE);

		pause = new JLabel("Press Space to Pause");
		pause.setFont(new Font("Serif", Font.BOLD, 32));
		pause.setForeground(Color.WHITE);
		pause.setVisible(false);
		


		BoxLayout box = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(box);

		add(scoreText);
		add(pause);
		add(Box.createVerticalGlue());
		add(pressSpace);
		add(gameOver);

		add(Box.createVerticalGlue());

		init();
	}

	public void setGameData(int size, int [] board, int score, int wins){
		this.wins = wins;
		init();
		int iter = 0;
		int completedBlocks = 0;
		for(int i = 0; i < bricks.rows; ++i)
			for(int it = 0; it < 8; ++it, ++iter){
				bricks.grid[i][it] = board[iter];
				if(board[iter] == 0)
					completedBlocks ++;
				}
		this.score = score;
		repaint();
		scoreText.setText(" " + score);
		winCondition = winCondition - completedBlocks;
	//	System.out.println("win condition " + winCondition);
	}



	public void init() {
		playing = false;
		score = 0;
		pause.setVisible(false);
		paddleX = 500;
		paddleY = (int)Math.floor(737*.9);
		paddleW = 200;
		paddleH = 20;
		paddleDir = 0;

		ballX = (int) Math.floor(1194/2);
		ballY = (int) Math.floor(737 * .7);
		ballR = 40;
		ballC = ballX + (int)(0.5 * ballR);
		ballXdir = -1;
		ballYdir = -2;

		gameOver.setVisible(false);
		pressSpace.setVisible(true);
		repaint();
		timer.start();

		if (wins > 5) { wins = 5; } 	// No more than 7 rows, I'm not a sadist

		bricks = new Brick((2 + wins));
		winCondition = (2 + wins) * 8;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();

		if (playing) {
			ballX += ballXdir;
			ballY += ballYdir;
			ballC = ballX + (int) .5 * ballR;

			// Ball hits top or sides of screen
			if (ballX <= 0 || ballX >= 1154) {
				ballXdir = -ballXdir;
			}
			if (ballY <= 0) {
				ballYdir = -ballYdir;
			}
			if (ballY >= 737) {
				// YOU LOSE
				gameOver.setVisible(true);
				pressSpace.setVisible(true);
				pause.setVisible(false);
				playing = false;
				timer.stop();
				wins = 0;
				File file = new File("lastGame.txt");
				if(file.exists()){
					try{file.delete();}
					catch(Exception ex){	System.out.println("Couldn't delete file"); }
					}

			}

			// If no more bricks left
			if (winCondition == 0) {
				wins++;
				score+=100;
				init();
			}

			// Start Collisions Detection
			Rectangle ball = new Rectangle(ballX, ballY, ballR, ballR);
			Rectangle paddle = new Rectangle(paddleX, paddleY, paddleW, paddleH);

			// Ball Hits Paddle
			if (ball.intersects(paddle)) {
				// Ball hits outermost 5th of paddle
				if (ballC <= paddleX + (paddleW * .2) || ballC >= paddleX + paddleW - (paddleW * .2)) {
					if (ballXdir < 0) { ballXdir = -3; }
					else { ballXdir = 3; }
									}
				// Ball hits inner 5th of paddle
				else if (ballC <= paddleX + (paddleW * .4) || ballC >= paddleX + paddleW - (paddleW * .4)) {
					if (ballXdir < 0) {ballXdir = -2; }
					else { ballXdir = 2; }
				}
				// Ball hits middle 5th of paddle, do nothing
				else {
					if (ballXdir < 0) { ballXdir = -1; }
					else { ballXdir = 1; }
				}

				// If the paddle is moving in an opposite direction to the ball, change the ball's direction
				if ((ballXdir > 0 && paddleDir < 0) || (ballXdir < 0 && paddleDir > 0)) {
					ballXdir = -ballXdir;
				}

				ballYdir = -ballYdir;
			}

			// Ball Hits Brick
			x = 11;
			y = 50;
			B: for (int i = 0; i < bricks.rows; i++) {
				for (int j = 0; j < bricks.cols; j++) {
					// If brick is visible
					if (bricks.grid[i][j] > 0) {
						// If they DO collide
						Rectangle brick = new Rectangle(x, y, bricks.brickWidth, bricks.brickHeight);
						if (ball.intersects(brick)) {
							bricks.grid[i][j] = 0;
							score += 10;
							winCondition--;
							// If ball hit the side of the brick
							if (ballX + (ballR - 1) <= x || ballX + 1 >= x + bricks.brickWidth) {
								ballXdir = -ballXdir;
							} else {
								// Ball hit top/bottom of brick
								ballYdir = -ballYdir;
							}
							break B;
						}
					}
					x += bricks.brickWidth + 5;
				}
				x = 11;
				y += bricks.brickHeight + 5;
			}
		}

		scoreText.setText(" " + score);
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (paddleX >= 983) {
				paddleX = 983;		//keep it in place
			} else {
				paddleX+=50;		//move to the right
			}
			paddleDir = 1;
		}

		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (paddleX <= 11) {
				paddleX = 11;
			} else {
				paddleX-=50;	//move to the left
			}
			paddleDir = -1;		//indicate left movement
		}
		//pause and play using space button
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			if(playing == true){
				playing = false;
				pressSpace.setVisible(true);
				pause.setVisible(false);
			}
			else{

				if(gameOver.isVisible())
					init();

				else
					pause.setVisible(true);
				
				playing = true;
				pressSpace.setVisible(false);
			}

			
		}

		//if (e.getKeyCode() == /* PAUSE KEY */) {}
			// if playing, timer stop, playing false
			// if !playing, timer start, playing true
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {
		paddleDir = 0;
	}


	public void paintComponent(Graphics g){
		super.paintComponent(g);
		super.setBackground(Color.black);

		// Draw Bricks
		bricks.draw(g);

		// Draw Ball
		g.setColor(Color.WHITE);
		g.fillOval(ballX, ballY, ballR, ballR);

		// Draw Paddle
		g.fillRect(paddleX, paddleY, paddleW, paddleH);

	}



}