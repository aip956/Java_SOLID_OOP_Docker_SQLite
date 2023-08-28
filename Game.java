import java.util.Scanner;

public class Game {
    private Guesser guesser;
    private SecretKeeper secretKeeper;
    private String secretCode;
    private int maxAttempts;
    private int attemptsLeft;
    private Scanner scanner;

    // class constructor
    public Game (Guesser guesser, SecretKeeper secretKeeper, String[] args) {
        this.secretKeeper = new SecretKeeper(this, args);
        this.guesser = new Guesser();
        this.maxAttempts = secretKeeper.maxAttempts;
        this.attemptsLeft = secretKeeper.attemptsLeft;
        this.scanner = new Scanner(System.in);
    }

    public void startGame() {
     

        // play round

        if (maxAttempts == attemptsLeft()) {
            System.out.println("Round " + (maxAttempts - attemptsLeft()));
        }

        do {
            String guess = guesser.makeGuess();
            if (isValidGuess(guess)) {
                guesser.makeGuess(guess);
                String feedback = secretKeeper.provideFeedback(secretCode);
                System.out.println(feedback);
                attemptsLeft--;

                if (guesser.determineWinStatus(secretCode)) {
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
                return guessNum >= 0 && guessNum <= 8888 && guess.length() == 4;
            } catch (NumberFormatException e) {
                return false;
            }
    }

    

}
