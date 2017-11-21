package bots;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

import setup.GridPosition;
import setup.Board;

public class Minimax {
	private static final ForkJoinPool POOL = new ForkJoinPool();
	
	// returns the best move for the current player
	public static GridPosition bestMove(Board board) {
		GridPosition result = POOL.invoke(new SearchTask(board, true)).position;
		if(result == null) {
			throw new IllegalStateException("game is already over");
		}
		return result;
	}
	@SuppressWarnings("serial")
	private static class SearchTask extends RecursiveTask<BestMove> {
		Board board;
		boolean botMoves;
		
		public SearchTask(Board board, boolean botMoves) {
			this.board = board;
			this.botMoves = botMoves;
		}

		@Override
		protected BestMove compute() {
			if(board.isGameOver()) { 
				if(!board.isFull()) {//game over when it becomes your turn -> you lost
					return new BestMove(null, -1, 0, botMoves);
				}
				return new BestMove(null, 0, 0, botMoves); //board is full -> draw
			}
			List<GridPosition> possibleMoves = new ArrayList<GridPosition>();
			for(int r = 0; r < Board.SIZE; r++) {
				for(int c = 0; c < Board.SIZE; c++) {
					GridPosition cur = new GridPosition(r, c);
					if(board.isEmptySpot(cur.row, cur.col)) {
						possibleMoves.add(cur);
					}
				}
			}
			if(possibleMoves.isEmpty()) {
				return new BestMove(null, 0, 0, botMoves);
			}
			List<SearchTask> tasks = new ArrayList<SearchTask>();
			GridPosition firstMove = possibleMoves.get(0);
			Board boardCopy = board.copy();
			boardCopy.makeMove(firstMove.row, firstMove.col);
			SearchTask first = new SearchTask(boardCopy, !botMoves);
			for(int i = 1; i < possibleMoves.size(); i++) {
				boardCopy = board.copy();
				GridPosition cur = possibleMoves.get(i);
				boardCopy.makeMove(cur.row, cur.col);
				SearchTask curTask = new SearchTask(boardCopy, !botMoves);
				curTask.fork();
				tasks.add(curTask);
			}
			BestMove best = first.compute();
			GridPosition bestMove = firstMove;
			best.negate();
			for(int i = 0; i < tasks.size(); i++) {
				BestMove cur = tasks.get(i).join();
				cur.negate();
				if(cur.compareTo(best) > 0) {
					bestMove = possibleMoves.get(i+1);
					best = cur;
				}
			}
			return new BestMove(bestMove, best.gameResult, best.movesUntilResult + 1, botMoves);
		} 
	}
	
	private static class BestMove {
		GridPosition position;
		int gameResult;
		int movesUntilResult;
		int multiplier;
		
		public BestMove(GridPosition position, int gameResult, int movesUntilResult, boolean tryingToWin) {
			this.position = position;
			this.gameResult = gameResult;
			this.movesUntilResult = movesUntilResult;
			if(tryingToWin) {
				multiplier = 1;
			} else {
				multiplier = -1;
			}
		}
		
		public void negate() {
			gameResult = -gameResult;
		}
		
		public int compareTo(BestMove other) {
			if(gameResult != other.gameResult) {
				return (gameResult - other.gameResult)*multiplier;
			}
			if(gameResult != -1) {
				return -(movesUntilResult - other.movesUntilResult)*multiplier;
			}
			return (movesUntilResult - other.movesUntilResult)*multiplier;
		}
	}

}
