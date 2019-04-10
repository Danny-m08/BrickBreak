import javax.swing.*;
import java.awt.event.*;

public class Game extends JFrame{

    // This class is the main Game loop
    JMenuBar menuBar;

	public Game(){
		super("Brick Break");
		super.setSize(800,650);

		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menuBar = new JMenuBar();
		JMenu newGame = new JMenu("New Game");
		JMenuItem startGame = new JMenuItem("Start New Game");


		startGame.setMnemonic('s');

		newGame.add(startGame);
		menuBar.add(newGame);

		startGame.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				JInternalFrame gameFrame = new JInternalFrame("Welcome");
				gameFrame.setVisible(true);
				System.out.println("Starting new game!");
			}
		});

		super.setJMenuBar(menuBar);
	}


    public static void main(String[] args) {
        System.out.println("Hello World!");
        Game brickBreak = new Game();
        brickBreak.setVisible(true);
    }
}
