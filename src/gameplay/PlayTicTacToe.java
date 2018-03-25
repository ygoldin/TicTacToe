package gameplay;

import java.awt.EventQueue;

/**
 * PlayTicTacToe can be launch a game of TicTacToe
 * @author Yael Goldin
 */
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
