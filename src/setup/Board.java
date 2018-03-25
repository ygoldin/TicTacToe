package setup;

public class Board {
	private char[][] grid;
	private int turn;
	private int movesMade;
	private int winner;
	
	public static final int SIZE = 3;
	public static final char[] PLAYERS = {'X', 'O'};
	
	/**
	 * creates a new empty tic-tac-toe board with the given player starting first
	 *  
	 * @param whoStarts The first player. 1 means player1 starts first, 2 means player2 starts first
	 * @throws IllegalArgumentException if an invalid player number is passed in
	 */
	public Board(int whoStarts) {
		this(whoStarts-1, new char[SIZE][SIZE]);
	}
	
	//creates a new empty tic-tac-toe board with the given player starting first
	//0 means player1 starts first, 1 means player2 starts first
	//throws IllegalArgumentException if an invalid player number is passed in
	//sets the board to have the given grid layout
	private Board(int whoStarts, char[][] grid) {
		if(whoStarts != 0 && whoStarts != 1) {
			throw new IllegalArgumentException("invalid starting player");
		}
		this.grid = grid;
		turn = whoStarts;
	}
	
	// returns whether a spot in the tic-tac-toe board is empty
	// throws IllegalArgumentException if the given spot is outside of the grid
	public boolean isEmptySpot(int row, int col) {
		if(row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
			throw new IllegalArgumentException("invalid location");
		}
		char cur = grid[row][col];
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
		grid[row][col] = PLAYERS[turn];
		boolean won = (movesMade > 4) && causedWin(row,col, PLAYERS[turn]);
		if(won) {
			winner = turn+1;
		}
		turn = (turn+1)%2;
		movesMade++;
	}
	
	// returns true if the given move caused the given player to win
	private boolean causedWin(int row, int col, char player) {
		return causedWinHorizontally(row, player) || causedWinVertically(col, player) ||
				causedWinDiagonally(player);
	}
	
	// returns true if the given player won horizontally
	private boolean causedWinHorizontally(int row, char player) {
		for(int c = 0; c < SIZE; c++) {
			if(grid[row][c] != player) {
				return false;
			}
		}
		return true;
	}
	
	// returns true if the given player won vertically
	private boolean causedWinVertically(int col, char player) {
		for(int r = 0; r < SIZE; r++) {
			if(grid[r][col] != player) {
				return false;
			}
		}
		return true;
	}
	
	// returns true if the given player won on a diagonal
	private boolean causedWinDiagonally(char player) {
		boolean result = true;
		for(int i = 0; i < SIZE; i++) {
			if(grid[i][i] != player) {
				result = false;
				break;
			}
		}
		if(!result) {
			for(int i = 0; i < SIZE; i++) {
				if(grid[SIZE - 1 - i][i] != player) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * returns a copy of the board
	 * 
	 * @return a copy of the board
	 */
	public Board copy() {
		char[][] copyGrid = new char[SIZE][SIZE];
		for(int r = 0; r < SIZE; r++) {
			for(int c = 0; c < SIZE; c++) {
				copyGrid[r][c] = grid[r][c];
			}
		}
		return new Board(turn, copyGrid);
	}
	
	/**
	 * returns whose turn it is
	 * 
	 * @return 1 if player 1s, 2 if player 2s
	 */
	public int curPlayerTurn() {
		return turn + 1;
	}
	
	/**
	 * returns if the board is full
	 * 
	 * @return true if the board is full, false otherwise
	 */
	public boolean isFull() {
		return movesMade == SIZE*SIZE;
	}
	
	/**
	 * checks if the game is over
	 * 
	 * @return true if the game is over (victory or draw), false otherwise
	 */
	public boolean isGameOver() {
		return isFull() || winner != 0;
	}
	
	/**
	 * returns the winner
	 * 
	 * @return 0 if the game ended in a draw, 1 if player1 won, 2 if player2 won
	 * @throws IllegalStateException if the game is not over
	 */
	public int winner() {
		if(!isGameOver()) {
			throw new IllegalStateException("game not over");
		}
		return winner;
	}
	
	@Override
	public String toString() {
		String blank = "   |   |   \n";
		String result = "";
		for(int r = 0; r < SIZE; r++) {
			result += blank;
			for(int c = 0; c < SIZE - 1; c++) {
				result += " " + grid[r][c] + " |";
			}
			result += " " + grid[r][SIZE-1] + "\n";
			if(r < SIZE-1) {
				result += "___|___|___\n";
			} else {
				result += blank;
			}
		}
		return result;
	}
}
