import java.util.Scanner;

public class Game {
    private Guesser guesser;
    private SecretKeeper secretKeeper;
    private String secretCode;
    private int maxAttempts;
    private int attemptsLeft;
    private Scanner scanner;


    // class constructor
    public Game (String[] args) {
        this.scanner = new Scanner(System.in);
        this.secretKeeper = new SecretKeeper(this, args);
        this.guesser = new Guesser(scanner);
        this.maxAttempts = secretKeeper.maxAttempts;
        this.attemptsLeft = secretKeeper.attemptsLeft;
        this.secretCode = secretKeeper.secretCode;
    }

    public void startGame() {
        System.out.println("G23 secret: " + secretCode);
        System.out.println("G23 Mxatt: " + maxAttempts);
        System.out.println("G23 attLef: " + attemptsLeft);
        if (maxAttempts == attemptsLeft) {
            System.out.println("Will you find the secret code?");
            System.out.println("---");
            System.out.println("Round " + (maxAttempts - attemptsLeft));
        }

        do {
            String guess = guesser.makeGuess();
            // System.out.println("GameLine28");
            if (isValidGuess(guess)) {
                // System.out.println("GameLine30");
                // guesser.makeGuess(guess);
                String feedback = secretKeeper.provideFeedback(guess);
                System.out.println(feedback);
                attemptsLeft--;

                if (guess.equals(secretCode)) {
                    System.out.println("Congrats! You did it!");
                    break;
                }

                if (attemptsLeft > 0) {
                    System.out.println("Round " + (maxAttempts - attemptsLeft));
                }
            
            } else {
                System.out.println("Wrong Input!");
            }
        } while (attemptsLeft > 0);

        // Womp womp womp . . . you lose
        if (attemptsLeft == 0) {
            System.out.println("Sorry, too many tries. The code was: " + secretCode);
        }
        
        scanner.close();
    }

    public boolean isValidGuess(String guess) {
        try {
            int guessNum = Integer.valueOf(guess);
            // boolean grt0 = guessNum >= 0;
            // boolean less8888 = guessNum <= 8888;
            // boolean len4 = guess.length()==4;

            // System.out.println("Guesslen4: " + len4); 
            // System.out.println("Grt0: " + grt0);
            // System.out.println("Less8888: " + less8888);
            // boolean allValid = guessNum >= 0 && guessNum <= 8888 && guess.length() == 4;
            // System.out.println("allValid: " + allValid);
            return guessNum >= 0 && guessNum <= 8888 && guess.length() == 4;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
