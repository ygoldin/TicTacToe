import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Minimax {
	private static final ForkJoinPool POOL = new ForkJoinPool();
	private static final int CUTOFF = 1;
	
	public static Integer[] bestMove(Board board) {
		BestMove result = POOL.invoke(new SearchTask(board, -1, -1));
		return null;
	}
	@SuppressWarnings("serial")
	private class SearchTask extends RecursiveTask<BestMove> {
		Board board;
		int row, col;
		
		public SearchTask(Board board, int row, int col) {
			this.board = board;
			this.row = row;
			this.col = col;
		}

		@Override
		protected BestMove compute() {
			if(board.isFull()) {
				return new BestMove(null, 0);
			}
			List<GridPosition> possibleMoves = new ArrayList<GridPosition>();
			for(int r = 0; r < Board.SIZE; r++) {
				for(int c = 0; c < Board.SIZE; c++) {
					if(board.isEmptySpot(r, c)) {
						possibleMoves.add(new GridPosition(r, c));
					}
				}
			}
			List<SearchTask> tasks = new ArrayList<SearchTask>();
			Board boardCopy = board.copy();
			boardCopy.makeMove(0, 0);
			SearchTask first = new SearchTask(boardCopy, 0, 0);
			for(int r = 0; r < Board.SIZE; r++) {
				for(int c = 0; c < Board.SIZE; c++) {
					if(board.isEmptySpot(r, c)) {
						boardCopy = board.copy();
						boardCopy.makeMove(r, c);
						SearchTask cur = new SearchTask(boardCopy, -1, -1);
						cur.fork();
						tasks.add(cur);
					}
				}
			}
			
			return null;
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
	
	private static class GridPosition {
		int row, col;
		
		public GridPosition(int row, int col) {
			this.row = row;
			this.col = col;
		}
	}

}
