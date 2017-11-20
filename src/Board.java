public class Board {
	public static final int SIZE = 3;
	private char[][] grid;
	private static final char[] PLAYERS = {'X', 'O'};
	private int turn;
	private int movesMade;
	
	//creates a new empty tic-tac-toe board with the given player starting first
	//1 means player1 starts first, 2 means player2 starts first
	//throws IllegalArgumentException if an invalid player number is passed in
	public Board(int whoStarts) {
		if(whoStarts != 1 && whoStarts != 2) {
			throw new IllegalArgumentException("invalid starting player");
		}
		grid = new char[SIZE][SIZE];
		turn = whoStarts;
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
	public boolean makeMove(int row, int col) {
		if(!isEmptySpot(row, col)) {
			throw new IllegalArgumentException("not empty");
		}
		grid[row-1][col-1] = PLAYERS[turn];
		boolean won = (movesMade > 4) && causedWin(row-1, col-1, PLAYERS[turn]);
		turn = (turn+1)%2;
		movesMade++;
		return won;
	}
	
	// returns true if the given move caused the given player to win
	private boolean causedWin(int r, int c, char player) {
		if(r == 0) {
			if(c == 0) {
				return (grid[0][1] == player && grid[0][2] == player) ||
						(grid[1][0] == player && grid[2][0] == player) ||
						(grid[1][1] == player && grid[2][2] == player);
			} else if(c == 1) {
				return (grid[0][0] == player && grid[0][2] == player) ||
						(grid[1][1] == player && grid[2][1] == player);
			} else {
				return (grid[0][0] == player && grid[0][1] == player) ||
						(grid[1][2] == player && grid[2][2] == player) ||
						(grid[1][1] == player && grid[2][0] == player);
			}
		} else if(r == 1) {
			if(c == 0) {
				return (grid[0][0] == player && grid[2][0] == player) ||
						(grid[1][1] == player && grid[1][2] == player);
			} else if(c == 1) {
				return (grid[0][0] == player && grid[2][2] == player) ||
						(grid[2][0] == player && grid[0][2] == player) ||
						(grid[0][1] == player && grid[2][1] == player) ||
						(grid[1][0] == player && grid[1][2] == player);
			} else {
				return (grid[1][0] == player && grid[1][1] == player) ||
						(grid[0][2] == player && grid[2][2] == player);
			}
		} else {
			if(c == 0) {
				return (grid[0][0] == player && grid[1][0] == player) ||
						(grid[2][1] == player && grid[2][2] == player) ||
						(grid[1][1] == player && grid[0][2] == player);
			} else if(c == 1) {
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
	
	// returns true if it's p1s turn, false if p2s
	public boolean p1sTurn() {
		return turn == 0;
	}
	
	// returns true if the board is filled up
	public boolean isFull() {
		return movesMade == SIZE*SIZE;
	}
}
