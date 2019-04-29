// Brick Break Java
// Amber Mickler & Danny Marquez

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javafx.scene.*;
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;

public class Game extends JFrame{

    // This class is the main Game loop
    JMenuBar menuBar;
    Board board;
 //	boolean newGame = true;

	public Game(){


		super("Brick Break");
		super.setSize(1200,800);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setResizable(false);
		
		File lastGameFile = new File("lastGame.txt");		
		board = new Board();
		menuBar = new JMenuBar();

		JMenu newGame = new JMenu("New Game");
		JMenuItem startGame = new JMenuItem("Start New Game");
		JMenuItem continueGame = new JMenuItem("Last saved game");		
		JMenu quitGame = new JMenu("Quit Game");
		JMenuItem quit = new JMenuItem("Quit");
		JMenuItem saveAndQuit = new JMenuItem("Save and Quit");


		continueGame.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					File file = new File("lastGame.txt");
					if(file.exists()){
						try{
							Scanner fileScanner = new Scanner(file);
							int boardSize = fileScanner.nextInt();
							int [] newBoard = new int[boardSize];
							
							for(int i = 0; i < boardSize; ++i)
								newBoard[i] = fileScanner.nextInt();
							
							int score = fileScanner.nextInt();
							int wins = fileScanner.nextInt();
							
							board.setGameData(boardSize,newBoard,score,wins);
							board.setScoreText(score);

							continueGame.setVisible(false);
						//	newGame = false;
						}
						catch(Exception ex){
							ex.printStackTrace();
							}
						}
					}
			});

		


		startGame.setMnemonic('s');
		startGame.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				int [] temp = new int[16];
				for(int i = 0; i < 16; ++i)
					temp[i] = 1;
				board.setGameData(16, temp , 0 , 0);
		//		newGame = true;
			}
		});

		saveAndQuit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				File file = new File("lastGame.txt");
				if(file.exists())
					file.delete();
					
				
				try{	
					int [] data = board.getBoardStatus();
					int size = data[0];
					file.createNewFile();
					PrintWriter fileWriter = new PrintWriter(file);
					fileWriter.println(data[0]);
					int i = 1;
					for(; i <= size; ++i)
						fileWriter.printf("%d ", data[i]);
					fileWriter.println();
					fileWriter.println(data[i]);
					fileWriter.println(data[++i]);
					fileWriter.close();
					}
				catch(Exception e){
					System.out.println(e);

					}
				System.exit(0);
			}
		});

		quit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent event){
				System.exit(0);
			}
		});
	
			
		if(!lastGameFile.exists())
			continueGame.setVisible(false);
	
		quitGame.add(saveAndQuit);
		quitGame.add(quit);
		newGame.add(continueGame);
		newGame.add(startGame);
		menuBar.add(newGame);
		menuBar.add(quitGame);

		super.add(board);
	//	super.add(bricks[i]);
		
		super.setJMenuBar(menuBar);
	}




    public static void main(String[] args) {
        //System.out.println("Hello World!");
        Game brickBreak = new Game();
        brickBreak.setVisible(true);
    }
}
