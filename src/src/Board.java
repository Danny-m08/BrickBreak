import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Board extends JPanel implements KeyListener, ActionListener {

	// I didn't like the default Orange and Blue colors lol
	//private Color[] colors = { Color.red, new Color(255,140,0), Color.yellow, Color.green, new Color(30,144,255), Color.magenta };
	//private int index = -1;
	private boolean playing;
	private int numBricks;
	private int score;

	private int paddleX;
	private int paddleY;
	private int paddleW;
	private int paddleH;

	private int ballX;
	private int ballY;
	private int ballR;
	private int ballXdir;
	private int ballYdir;

	private Timer timer;
	private int delay = 6;
	private int wins = 0;

	private Brick bricks;
	private int x;
	private int y;

	private JLabel pressSpace;
	private JLabel gameOver;

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

		BoxLayout box = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(box);

		add(Box.createVerticalGlue());
		add(pressSpace);
		add(gameOver);
		add(Box.createVerticalGlue());

		init();
	}

	public void init() {
		//index = -1;
		playing = false;
		numBricks = 32;
		score = 0;

		paddleX = 500;
		paddleY = (int)Math.floor(737*.9);
		paddleW = 200;
		paddleH = 20;

		ballX = (int) Math.floor(1194/2);
		ballY = (int) Math.floor(737 * .7);
		ballR = 40;
		ballXdir = -1;
		ballYdir = -2;

		gameOver.setVisible(false);
		pressSpace.setVisible(true);
		repaint();
		timer.start();

		bricks = new Brick(2 + wins);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();

		if (playing) {
			ballX += ballXdir;
			ballY += ballYdir;

			// Ball hits top or sides of screen
			// Reverse direction
			if (ballX <= 0 || ballX >= 1154) {
				ballXdir = -ballXdir;
			}
			if (ballY <= 0) {
				ballYdir = -ballYdir;
			}
			if (ballY >= 737) {
				// YOU LOSE
				gameOver.setVisible(true);
				playing = false;
				timer.stop();
				wins = 0;
			}

			// Ball Hits Paddle
			if (new Rectangle(ballX, ballY, ballR, ballR).intersects(new Rectangle(paddleX, paddleY, paddleW, paddleH))) {
				ballYdir = -ballYdir;
			}

			// Ball Hits Brick
			x = 11;
			y = 50;
			for (int i = 0; i < bricks.rows; i++) {
				for (int j = 0; j < bricks.cols; j++) {
					// If brick is visible
					if (bricks.grid[i][j] > 0) {
						// If they DO collide
						if (new Rectangle(ballX, ballY, ballR, ballR).intersects(new Rectangle(x, y, bricks.brickWidth, bricks.brickHeight))) {
							bricks.grid[i][j] = 0;
							score += 10;
							// TODO ball changes direction
						}
					}
					x += bricks.brickWidth + 5;
				}
				x = 11;
				y += bricks.brickHeight + 5;
			}

		}

		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (paddleX >= 983) {
				paddleX = 983;
			} else {
				paddleX+=20;
				//repaint();
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (paddleX <= 11) {
				paddleX = 11;
			} else {
				paddleX-=20;
				//repaint();
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			playing = true;
			pressSpace.setVisible(false);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {}

	@Override
	public void keyReleased(KeyEvent e) {}


	public void paintComponent(Graphics g){
		super.paintComponent(g);
		super.setBackground(Color.black);
		//int brickWidth = 142;
		//int brickHeight = 40;

		/*int x = 11;
		int y = 50;
		index = -1;

		// Draw Bricks
		for(int i = 0; i < 7; ++i){
			g.setColor(nextColor());
			for(int it = 0; it < 8; ++it){
				g.fillRect(x,y,brickWidth,brickHeight);
				x += brickWidth + 5;
			}
			x = 11;
			y += brickHeight + 5;
		}*/
		// Draw Bricks
		bricks.draw(g);

		// Draw Ball
		g.setColor(Color.WHITE);
		g.fillOval(ballX, ballY, ballR, ballR);

		// Draw Paddle
		g.fillRect(paddleX, paddleY, paddleW, paddleH);

	}



/*	private Color nextColor() {
		index++;
		index = index % colors.length;
		return colors[index];
	}*/
}