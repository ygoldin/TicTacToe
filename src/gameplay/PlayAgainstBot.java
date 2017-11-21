package gameplay;

import java.util.Scanner;

public class PlayAgainstBot {
	private static final Scanner INPUT = new Scanner(System.in);

	public static void main(String[] args) {
		do {
			
		} while(playAgain());
	}
	
	private static boolean playAgain() {
		System.out.print("Do you want to play again? ");
		String answer = INPUT.nextLine();
		return answer.startsWith("y") || answer.startsWith("Y");
	}

}
