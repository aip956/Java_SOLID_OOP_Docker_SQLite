import java.util.Random;

public class GameState_old {
    String secret;
    Integer maxAttempts;
    Integer attemptsLeft;

    // Use this constructor method if no secret was entered
    public GameState_old(int maxAttempts) {
        generateSecret();
        this.maxAttempts = maxAttempts;
        attemptsLeft = maxAttempts;
    }
    
    // Use this constructor method if a secret or attempts was entered
    public GameState_old(String secret, int maxAttempts) {
        this.secret = secret;
        this.maxAttempts = maxAttempts;
        attemptsLeft = maxAttempts;
    }

    // Setter function for MyMastermind class to access attemptsLeft
    public int getAttemptsLeft() {
        return attemptsLeft;
    }

    // Generate a random secret if no secret was entered
    private String generateSecret() {
        Random num = new Random();
        StringBuilder secretBuilder = new StringBuilder();
        while (secretBuilder.length() < 4) {
            int int_random = num.nextInt(9);
            if (secretBuilder.indexOf(String.valueOf(int_random)) == -1) {
                secretBuilder.append(String.valueOf(int_random));
            }        
        }
        secret = secretBuilder.toString();
        // System.out.println(secret);
        return secret;
    }

    // Setter for MyMastermind to print secret
    public String getSecret() {
        return secret;
    }

    // Count well-placed and mis-placed pieces
    public void playRound(String guess) {
        if(isValidGuess(guess)) {
            int wellPlaced = 0;
            int misPlaced = 0;

            for (int i = 0; i < 4; i++) {
                char secretChar = secret.charAt(i);
                char guessChar = guess.charAt(i);

                if (secretChar == guessChar) {
                    wellPlaced++;
                }
                else if (secret.indexOf(guessChar) != -1) {
                    misPlaced++;
                }
            }

            // If all pieces are well-placed, winner
            if (wellPlaced == 4) {
                System.out.println("Congrats! You did it!");
                System.exit(0);
            }
            
            System.out.println("Well placed pieces: " + wellPlaced);
            System.out.println("Misplaced pieces: " + misPlaced);
            System.out.println("---");
            attemptsLeft--;
            // Print when Round > 0
            if (attemptsLeft > 0) {
                System.out.println("Round " + (maxAttempts - attemptsLeft));
            }
        }
    }
    
    // Check the input is 4 digits
    public boolean isValidGuess(String guess) {
        try {
            int guessNum = Integer.valueOf(guess);
            return guessNum >= 0 && guessNum <= 8888 && guess.length() == 4;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
