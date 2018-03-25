package gameplay;

import java.awt.*;
import javax.swing.*;
import setup.TicTacToeBoard;

@SuppressWarnings("serial")
public class TicTacToeFrame extends JFrame {
	private TicTacToeBoard gameboard;
	private GridSpot[][] gridButtons;
	
	public TicTacToeFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setMinimumSize(new Dimension(1024, 768));
		setTitle("Tic-Tac-Toe");
		gameboard = new TicTacToeBoard(1);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(TicTacToeBoard.SIZE, TicTacToeBoard.SIZE));
		gridButtons = new GridSpot[TicTacToeBoard.SIZE][TicTacToeBoard.SIZE];
		for(int r = 0; r < TicTacToeBoard.SIZE; r++) {
			for(int c = 0; c < TicTacToeBoard.SIZE; c++) {
				gridButtons[r][c] = new GridSpot(r, c);
				buttonPanel.add(gridButtons[r][c]);
			}
		}
		add(buttonPanel);
	}
	
	private class GridSpot extends JButton {
		private static final int FONT_SIZE = 40;
		private static final String FONT_NAME = "Arial";
		
		public GridSpot(int row, int col) {
			setFont(new Font(FONT_NAME, Font.PLAIN, FONT_SIZE));
			addActionListener(e -> {
				if(!gameboard.isGameOver() && gameboard.isEmptySpot(row, col)) {
					int curPlayer = gameboard.curPlayerTurn();
					gameboard.makeMove(row, col);
					setText("" + TicTacToeBoard.PLAYERS[curPlayer - 1]);
				}
			});
		}
	}
}
