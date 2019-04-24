import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Board extends JPanel implements KeyListener, ActionListener {

	private Color[] colors = { Color.red, Color.orange, Color.yellow, Color.green, Color.blue, Color.magenta };
	private int index = -1;
	private boolean playing = false;
	private int numBricks = 32;
	private int score = 0;

	private int paddleX = 500;
	private int paddleY = (int)Math.floor(737*.9);
	private int paddleW = 200;
	private int paddleH = 20;

	private int ballX = (int) Math.floor(1194/2);
	private int ballY = (int) Math.floor(737 * .7);
	private int ballR = 40;
	private int ballXdir = -1;
	private int ballYdir = -2;

	private JLabel pressSpace;


	public Board() {
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);

		pressSpace = new JLabel("Press Space to Play", SwingConstants.CENTER);
		pressSpace.setFont(new Font("Serif", Font.BOLD, 72));
		pressSpace.setForeground(Color.WHITE);
		pressSpace.setVisible(true);
		pressSpace.setVerticalTextPosition(JLabel.CENTER);
		add(pressSpace, BorderLayout.CENTER);

	}

	public void init() {

	}


	@Override
	public void actionPerformed(ActionEvent e) {

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (paddleX >= 983) {
				paddleX = 983;
			} else {
				paddleX++;
			}
		}

		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (paddleX <= 11) {
				paddleX = 11;
			} else {
				paddleX--;
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
		int brickWidth = 142;
		int brickHeight = 40;

		int x = 11;
		int y = 50;
		index = -1;

		// Draw Bricks
		for(int i = 0; i < 4; ++i){
			g.setColor(nextColor());
			for(int it = 0; it < 8; ++it){
				g.fillRect(x,y,brickWidth,brickHeight);
				x += brickWidth + 5;
			}
			x = 11;
			y += brickHeight + 5;
		}

		// Draw Ball
		g.setColor(Color.WHITE);
		g.fillOval(ballX, ballY, ballR, ballR);

		// Draw Paddle
		g.fillRect(paddleX, paddleY, paddleW, paddleH);

	}

	private Color nextColor() {
		index++;
		index = index % colors.length;
		return colors[index];
	}
}