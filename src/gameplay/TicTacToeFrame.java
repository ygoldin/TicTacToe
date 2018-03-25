package gameplay;

import java.awt.Dimension;

import javax.swing.*;
import setup.TicTacToeBoard;

@SuppressWarnings("serial")
public class TicTacToeFrame extends JFrame {
	private TicTacToeBoard gameboard;
	
	public TicTacToeFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(1024, 768));
		setTitle("Tic-Tac-Toe");
		gameboard = new TicTacToeBoard(0);
	}
}
