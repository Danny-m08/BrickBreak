import javafx.scene.shape.Rectangle;

import java.awt.*;

public class Brick {
    // This class represents the whole group of brick objects

	// I didn't like the default Orange and Blue colors lol
	private Color[] colors = { Color.red, new Color(255,140,0), Color.yellow, Color.green, new Color(30,144,255), Color.magenta };
	public int grid[][];
	public int brickWidth = 142;
	public int brickHeight = 40;
	public int rows;
	public int cols = 8;
	private int x;
	private int y;
	private int index;

	Brick(int r){
		rows = r;
		grid = new int[rows][cols];

		// init grid
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				grid[i][j] = 1;
			}
		}

	}

	public void draw(Graphics g) {
		x = 11;
		y = 50;
		index = -1;
		// Draw Bricks
		for(int i = 0; i < rows; ++i){
			g.setColor(nextColor());
			for(int j = 0; j < cols; ++j){
				if (grid[i][j] > 0) {
					g.fillRect(x, y, brickWidth, brickHeight);
				}
				x += brickWidth + 5;
			}
			x = 11;
			y += brickHeight + 5;
		}
	}

	private Color nextColor() {
		index++;
		index = index % colors.length;
		return colors[index];
	}
}
