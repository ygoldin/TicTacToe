package bots;

import java.util.Scanner;

import setup.TicTacToeBoard;

public class PlayAgainstBot {
	private static final Scanner INPUT = new Scanner(System.in);

	public static void main(String[] args) {
		do {
			playOneGame();
		} while(playAgain());
	}
	
	private static void playOneGame() {
		System.out.println("Welcome to Tic-Tac-Toe!");
		int playerGoingFirst;
		do {
			System.out.print("Do you want to go first (1) or second (2)? ");
			playerGoingFirst = INPUT.nextInt();
			INPUT.nextLine();
		} while(playerGoingFirst != 1 && playerGoingFirst != 2);
		
		TicTacToeBoard gameBoard = new TicTacToeBoard(playerGoingFirst);
		System.out.println(gameBoard);
		boolean playersTurn = playerGoingFirst == 1;
		while(!gameBoard.isGameOver()) {
			playOneMove(gameBoard, playersTurn);
			playersTurn = !playersTurn;
		}
		
		int winner = gameBoard.winner();
		if(winner == 0) {
			System.out.println("It's a draw!");
		} else if(winner == playerGoingFirst) {
			System.out.println("You won! Congrats!");
		} else {
			System.out.println("The bot won, good luck next time");
		}
	}
	
	private static void playOneMove(TicTacToeBoard gameBoard, boolean playersTurn) {
		int row, col;
		if(playersTurn) {
			do {
				System.out.println("Please enter valid dimension sizes (0-" + (TicTacToeBoard.SIZE-1) +
						") for an empty spot");
				do {
					System.out.print("Which row? ");
					row = INPUT.nextInt();
					INPUT.nextLine();
				} while(invalidDimension(row));
				do {
					System.out.print("Which col? ");
					col = INPUT.nextInt();
					INPUT.nextLine();
				} while(invalidDimension(col));
			} while(!gameBoard.isEmptySpot(row, col));
		} else {
			System.out.println("\n Bot moved:");
			GridPosition best = Minimax.bestMove(gameBoard);
			row = best.row;
			col = best.col;
		}
		gameBoard.makeMove(row, col);
		System.out.println(gameBoard);
	}
	
	private static boolean invalidDimension(int dim) {
		return dim < 0 || dim >= TicTacToeBoard.SIZE;
	}
	
	private static boolean playAgain() {
		System.out.print("Do you want to play again? ");
		String answer = INPUT.nextLine();
		return answer.startsWith("y") || answer.startsWith("Y");
	}

}
