import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javafx.scene.*;

public class Game extends JFrame{



    // This class is the main Game loop
    JMenuBar menuBar;
    Brick [] bricks;
    Board board;

	public Game(){
		super("Brick Break");
		super.setSize(800,650);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		board = new Board();

		menuBar = new JMenuBar();


		bricks = new Brick[10];
		//for(int i = 0; i < bricks.length; ++i)
		//	bricks[i] = new Brick();
//


		JMenu newGame = new JMenu("New Game");
		JMenuItem startGame = new JMenuItem("Start New Game");
//		JFXpanel fxPanel = new JFXpanel();
//		initializeFX(fxPanel());

		startGame.setMnemonic('s');



		startGame.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				JInternalFrame gameFrame = new JInternalFrame("Welcome");
				gameFrame.setVisible(true);
				System.out.println("Starting new game!");
			}
		});


		newGame.add(startGame);
		menuBar.add(newGame);
		super.add(board);
	//	super.add(bricks[i]);
		
		super.setJMenuBar(menuBar);
	}

//	private void initializeFX(JFXPanel p){
//		Scene scene = new createScene();
//		p.setScene(p);
//	}



    public static void main(String[] args) {
        System.out.println("Hello World!");
        Game brickBreak = new Game();
        brickBreak.setVisible(true);
    }
}
