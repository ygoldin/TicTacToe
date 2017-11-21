public class Board {
	public static final int SIZE = 3;
	private char[][] grid;
	private static final char[] PLAYERS = {'X', 'O'};
	private int turn;
	private int movesMade;
	private int winner;
	
	//creates a new empty tic-tac-toe board with the given player starting first
	//1 means player1 starts first, 2 means player2 starts first
	//throws IllegalArgumentException if an invalid player number is passed in
	public Board(int whoStarts) {
		if(whoStarts != 1 && whoStarts != 2) {
			throw new IllegalArgumentException("invalid starting player");
		}
		grid = new char[SIZE][SIZE];
		turn = whoStarts-1;
	}
	
	private Board(int whoStarts, char[][] grid) {
		this.grid = grid;
		turn = whoStarts;
	}
	
	// returns whether a spot in the tic-tac-toe board is empty
	// throws IllegalArgumentException if the given spot is outside of the grid
	public boolean isEmptySpot(int row, int col) {
		if(row < 1 || row > SIZE || col < 1 || col > SIZE) {
			throw new IllegalArgumentException("invalid location");
		}
		char cur = grid[row-1][col-1];
		return cur != PLAYERS[0] && cur != PLAYERS[1];
	}
	
	// marks the given spot for the current player
	// returns true if the move resulted in a win
	// returns false if not
	// throws IllegalArgumentException if the given spot is outside of the grid or is not empty
	// throws IllegalStateException if the game is over
	public void makeMove(int row, int col) {
		if(!isEmptySpot(row, col)) {
			throw new IllegalArgumentException("not empty");
		}
		if(isGameOver()) {
			throw new IllegalStateException("game over");
		}
		grid[row-1][col-1] = PLAYERS[turn];
		boolean won = (movesMade > 4) && causedWin(row-1,col-1, PLAYERS[turn]);
		if(won) {
			winner = turn+1;
		}
		turn = (turn+1)%2;
		movesMade++;
	}
	
	// returns true if the given move caused the given player to win
	private boolean causedWin(int row, int col, char player) {
		if(row == 0) {
			if(col == 0) {
				return (grid[0][1] == player && grid[0][2] == player) ||
						(grid[1][0] == player && grid[2][0] == player) ||
						(grid[1][1] == player && grid[2][2] == player);
			} else if(col == 1) {
				return (grid[0][0] == player && grid[0][2] == player) ||
						(grid[1][1] == player && grid[2][1] == player);
			} else {
				return (grid[0][0] == player && grid[0][1] == player) ||
						(grid[1][2] == player && grid[2][2] == player) ||
						(grid[1][1] == player && grid[2][0] == player);
			}
		} else if(row == 1) {
			if(col == 0) {
				return (grid[0][0] == player && grid[2][0] == player) ||
						(grid[1][1] == player && grid[1][2] == player);
			} else if(col == 1) {
				return (grid[0][0] == player && grid[2][2] == player) ||
						(grid[2][0] == player && grid[0][2] == player) ||
						(grid[0][1] == player && grid[2][1] == player) ||
						(grid[1][0] == player && grid[1][2] == player);
			} else {
				return (grid[1][0] == player && grid[1][1] == player) ||
						(grid[0][2] == player && grid[2][2] == player);
			}
		} else {
			if(col == 0) {
				return (grid[0][0] == player && grid[1][0] == player) ||
						(grid[2][1] == player && grid[2][2] == player) ||
						(grid[1][1] == player && grid[0][2] == player);
			} else if(col == 1) {
				return (grid[2][0] == player && grid[2][2] == player) ||
						(grid[0][1] == player && grid[1][1] == player);
			} else {
				return (grid[2][0] == player && grid[2][1] == player) ||
						(grid[0][2] == player && grid[1][2] == player) ||
						(grid[1][1] == player && grid[0][0] == player);
			}
		}
	}
	
	public Board copy() {
		char[][] copyGrid = new char[SIZE][SIZE];
		for(int r = 0; r < SIZE; r++) {
			for(int c = 0; c < SIZE; c++) {
				copyGrid[r][c] = grid[r][c];
			}
		}
		return new Board(turn, copyGrid);
	}
	
	// returns 1 if it's p1s turn, 2 if p2s
	public int curPlayerTurn() {
		return turn + 1;
	}
	
	// returns true if the board is filled up
	public boolean isFull() {
		return movesMade == SIZE*SIZE;
	}
	
	public boolean isGameOver() {
		return movesMade == SIZE*SIZE || winner != 0;
	}
	
	//returns 1 if player1 won, 2 if player2 won
	//throws IllegalStateException if the game is not over
	public int winner() {
		if(!isGameOver()) {
			throw new IllegalStateException("game not over");
		}
		return winner;
	}
}
