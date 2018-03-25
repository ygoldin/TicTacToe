package gameplay;

import java.awt.EventQueue;

public class PlayTicTacToe {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				TicTacToeFrame tictactoe = new TicTacToeFrame();
				tictactoe.pack();
				tictactoe.setVisible(true);
			}
		});
	}

}
