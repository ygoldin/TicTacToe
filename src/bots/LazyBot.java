package bots;

import setup.TicTacToeBoard;
import setup.GridPosition;

public class LazyBot {
	public static GridPosition bestMove(TicTacToeBoard board) {
		for(int r = 0; r < TicTacToeBoard.SIZE; r++) {
			for(int c = 0; c < TicTacToeBoard.SIZE; c++) {
				if(board.isEmptySpot(r, c)) {
					return new GridPosition(r,c);
				}
			}
		}
		throw new IllegalStateException("game is already over");
	}
}
