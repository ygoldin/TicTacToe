package bots;

import setup.Board;
import setup.GridPosition;

public class LazyBot {
	public static GridPosition bestMove(Board board) {
		for(int r = 0; r < Board.SIZE; r++) {
			for(int c = 0; c < Board.SIZE; c++) {
				if(board.isEmptySpot(r, c)) {
					return new GridPosition(r,c);
				}
			}
		}
		throw new IllegalStateException("game is already over");
	}
}
