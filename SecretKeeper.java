import java.util.Random;

public class SecretKeeper extends Player {
    private Game game;
    public String secretCode;
    public int maxAttempts = 5;
    public int attemptsLeft = maxAttempts;

    // SecretKeeper will determine if there is a secret and/or max attempts
    // in the CLI args. If not, it will create a secret and/or default
    // the max attempts
    public SecretKeeper(Game game, String[] args) {
        this.game = game;
        if (hasSecretInArgs(args)) {
            secretCode = extractSecretFromArgs(args);
        } else {
            secretCode = generateRandomSecret();
        }
        if (hasTriesInArgs(args)) {
            maxAttempts = extractMaxAttemptsFromArgs(args);
            attemptsLeft = maxAttempts;
        }
        // System.out.println("SK18 secret: " + secretCode);
        // System.out.println("SK19 attempts: " + maxAttempts);
    }

    // boolean to indicate if the args contains a secret
    private boolean hasSecretInArgs(String[] args) {
        for (int i = 0; i < args.length; i++) {
                // Search for input secret
            if (args[i].equals("-c") && i < args.length) {
                return true;
            }
        }
        return false;
    }
    // boolean to indicate if the args contains max attempts
    private boolean hasTriesInArgs(String[] args) {
        for (int i = 0; i < args.length; i++) {
                // Search for input secret
            if (args[i].equals("-t") && i < args.length) {
                return true;
            }
        }
        return false;
    }
    // extract the secret from args
    public String extractSecretFromArgs(String[] args) {
        for (int i = 0; i < args.length; i++) {
            // Search for input secret
            if (args[i].equals("-c") && i < args.length) {
                secretCode = args[i+1];
                i++;
            }
        }
        return secretCode;
    }
    // generate a random secret if one was not included in args
    private String generateRandomSecret() {
        Random num = new Random();
        StringBuilder secretBuilder = new StringBuilder();
        while (secretBuilder.length() < 4) {
            int int_random = num.nextInt(9);
            if (secretBuilder.indexOf(String.valueOf(int_random)) == -1) {
                secretBuilder.append(String.valueOf(int_random));
            }        
        }
        secretCode = secretBuilder.toString();
        // System.out.println(secret);
        return secretCode;
    }
    // extract max attempts from args
    private int extractMaxAttemptsFromArgs(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-t") && i < args.length) {
                try {
                    maxAttempts = Integer.parseInt(args[i+1]);
                    i++;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid value for -t");
                    return maxAttempts;
                }
            }
        }
        return maxAttempts;
    }
    // Output the well-placed and mis-placed pieces, and/or win
    public String provideFeedback(String guess) {
        if (game.isValidGuess(guess)) {
            // System.out.println("SecretKeep75");
            int wellPlaced = 0;
            int misPlaced = 0;

            for (int i = 0; i < 4; i++) {
                char secretChar = secretCode.charAt(i);
                char guessChar = guess.charAt(i);

                if (secretChar == guessChar) {
                    wellPlaced++;
                }
                else if (secretCode.indexOf(guessChar) != -1) {
                    misPlaced++;
                }
            }

            // If all pieces are well-placed, winner
            if (wellPlaced == 4) {
                System.out.println("Congrats! You did it!");
                System.exit(0);
            }
            
            String feedback = "Well placed pieces: " + wellPlaced + "\n";
            feedback += "Misplaced pieces: " + misPlaced + "\n";
            feedback += "---\n";
            attemptsLeft--;

            // Print when Round > 0
            // if (attemptsLeft > 0) {
            //     System.out.println("SKRound " + (maxAttempts - attemptsLeft));
            // }
            return feedback;
        }
        return "Invalid guess";
    }
    
}
    


