package gameplay;

import java.util.Scanner;
import setup.Board;
import setup.Minimax;

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
		Board gameBoard = new Board(playerGoingFirst);
		while(!gameBoard.isGameOver()) {
			System.out.println(gameBoard);
			break;
		}
	}
	
	private static boolean playAgain() {
		System.out.print("Do you want to play again? ");
		String answer = INPUT.nextLine();
		return answer.startsWith("y") || answer.startsWith("Y");
	}

}
