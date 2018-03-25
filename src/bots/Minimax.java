package bots;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import setup.TicTacToeBoard;

public class Minimax {
	private static final ForkJoinPool POOL = new ForkJoinPool();
	
	// returns the best move for the current player
	public static GridPosition bestMove(TicTacToeBoard board) {
		GridPosition result = POOL.invoke(new SearchTask(board)).position;
		if(result == null) {
			throw new IllegalStateException("game is already over");
		}
		return result;
	}
	@SuppressWarnings("serial")
	private static class SearchTask extends RecursiveTask<BestMove> {
		TicTacToeBoard board;
		
		public SearchTask(TicTacToeBoard board) {
			this.board = board;
		}

		@Override
		protected BestMove compute() {
			if(board.isGameOver()) { 
				if(!board.isFull()) {//game over when it becomes your turn -> you lost
					return new BestMove(null, -1);
				}
				return new BestMove(null, 0); //board is full -> draw
			}
			List<GridPosition> possibleMoves = new ArrayList<GridPosition>();
			for(int r = 0; r < TicTacToeBoard.SIZE; r++) {
				for(int c = 0; c < TicTacToeBoard.SIZE; c++) {
					GridPosition cur = new GridPosition(r, c);
					if(board.isEmptySpot(cur.row, cur.col)) {
						possibleMoves.add(cur);
					}
				}
			}
			if(possibleMoves.isEmpty()) {
				return new BestMove(null, 0);
			}
			List<SearchTask> tasks = new ArrayList<SearchTask>();
			GridPosition firstMove = possibleMoves.get(0);
			TicTacToeBoard boardCopy = board.copy();
			boardCopy.makeMove(firstMove.row, firstMove.col);
			SearchTask first = new SearchTask(boardCopy);
			for(int i = 1; i < possibleMoves.size(); i++) {
				boardCopy = board.copy();
				GridPosition cur = possibleMoves.get(i);
				boardCopy.makeMove(cur.row, cur.col);
				SearchTask curTask = new SearchTask(boardCopy);
				curTask.fork();
				tasks.add(curTask);
			}
			BestMove best = first.compute();
			GridPosition bestMove = firstMove;
			int bestValue = -best.gameResult;
			for(int i = 0; i < tasks.size(); i++) {
				int curResult = -tasks.get(i).join().gameResult;
				if(curResult > bestValue) {
					bestMove = possibleMoves.get(i+1);
					bestValue = curResult;
				}
			}
			return new BestMove(bestMove, bestValue);
		} 
	}
	
	private static class BestMove {
		GridPosition position;
		int gameResult;
		
		public BestMove(GridPosition position, int gameResult) {
			this.position = position;
			this.gameResult = gameResult;
		}
	}

}
