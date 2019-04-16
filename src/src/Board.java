import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;

public class Board extends JPanel{

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		super.setBackground(new Color(153,0,0));
		int height = super.getHeight();
		int width = super.getWidth();
		int brickWidth = width / 8;
		int brickHeight = (height / 10) / 2;
		int radius  = (int) Math.floor(brickWidth * .4);

		int x = 0;
		int y = 0;

		g.setColor(Color.BLACK);
		g.fillOval((int) Math.floor(width/2),(int) Math.floor(height * .8), radius, radius);

		g.setColor(Color.BLUE);
		g.fillRect((int) Math.floor(width / 2) - (brickWidth/3),(int)Math.floor(height*.9),brickWidth,brickHeight/2);
		g.setColor(new Color(255,204,0));
		for(int i = 0; i < 3; ++i){
			for(int it = 0; it < 8; ++it){
				g.fillRect(x,y,brickWidth,brickHeight);
				x += brickWidth + 1;
			}
			x = 0;
			y = brickHeight +2;
		}
	}	
}