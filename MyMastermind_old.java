import java.util.Scanner;


public class MyMastermind_old {
    static String secret = "";
    static int maxAttempts = 5;

    public static void main (String[] args) {
        // Determine if there are Command Line arguments
        if (args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                // Search for input secret
                if (args[i].equals("-c") && i < args.length) {
                    secret = args[i+1];
                    i++;
                }
                // Search for max attempts
                else if (args[i].equals("-t") && i < args.length) {
                    try {
                        maxAttempts = Integer.parseInt(args[i+1]);
                        i++;
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid value for -t");
                        return;
                    }
                }
            }
        } 
        // Declare GameState var
        GameState_old gameState;

        // Indicate which GameState method depending on if CLI args
        if (!secret.isEmpty()) {
            gameState = new GameState_old(secret, maxAttempts);
        } else {
            gameState = new GameState_old(maxAttempts);
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("Will you find the secret code?");
        System.out.println("---");

        // Print the first Round 0
        if (maxAttempts == gameState.getAttemptsLeft()) {
            System.out.println("Round " + (maxAttempts - gameState.getAttemptsLeft()));
        }

        // Check guess validity and play while attempts > 0
        do {
            String guess = scanner.nextLine();
            if (gameState.isValidGuess(guess)) {
                gameState.playRound(guess);
            } else {
                System.out.println("Wrong Input!");
            }
        } while (gameState.getAttemptsLeft() > 0);

        // Womp womp womp . . . you lose
        System.out.println("Sorry, too many tries. The code was: " + gameState.getSecret());
        
        scanner.close();
    }
}

