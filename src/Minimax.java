import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Minimax {
	private static final ForkJoinPool POOL = new ForkJoinPool();
	
	public static GridPosition bestMove(Board board) {
		return POOL.invoke(new SearchTask(board)).position;
	}
	@SuppressWarnings("serial")
	private class SearchTask extends RecursiveTask<BestMove> {
		Board board;
		
		public SearchTask(Board board) {
			this.board = board;
		}

		@Override
		protected BestMove compute() {
			if(board.isGameOver()) { 
				if(!board.isFull()) {//game over when it becomes your turn -> you lost
					return new BestMove(null, -1);
				}
				return new BestMove(null, 0);
			}
			List<GridPosition> possibleMoves = new ArrayList<GridPosition>();
			for(int r = 0; r < Board.SIZE; r++) {
				for(int c = 0; c < Board.SIZE; c++) {
					GridPosition cur = new GridPosition(r, c);
					if(board.isEmptySpot(cur)) {
						possibleMoves.add(cur);
					}
				}
			}
			
			List<SearchTask> tasks = new ArrayList<SearchTask>();
			GridPosition firstMove = possibleMoves.get(0);
			Board boardCopy = board.copy();
			boardCopy.makeMove(firstMove);
			SearchTask first = new SearchTask(boardCopy);
			for(int i = 1; i < possibleMoves.size(); i++) {
				boardCopy = board.copy();
				boardCopy.makeMove(possibleMoves.get(i));
				SearchTask cur = new SearchTask(boardCopy);
				cur.fork();
				tasks.add(cur);
			}
			BestMove best = first.compute();
			GridPosition bestMove = firstMove;
			int bestValue = best.gameResult;
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
