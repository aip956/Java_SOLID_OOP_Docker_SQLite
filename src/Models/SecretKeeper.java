// SecretKeeper.java
package Models;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;



public class SecretKeeper extends Player {
    public String secretCode;
    private List<String> guesses;
    public int maxAttempts;
    public int attemptsLeft;
    private static final String VALID_GUESS_PATTERN = "[0-7]{4}";


    public SecretKeeper() {
        super("Secret Keeper");
    
        this.secretCode = generateRandomSecret();
        this.guesses = new ArrayList<>();
        this.attemptsLeft = Game.getMaxAttempts();
    }

    private String generateRandomSecret() {
        String localSecret = "0000"; // if API fails
        try {
            URL url = new URL("https://www.random.org/integers/?num=4&min=0&max=7&col=1&base=10&format=plain&rnd=new");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
    
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
    
            secretCode = response.toString().trim();
            System.out.println("Secret from API: " + secretCode);
            return secretCode; // Return secret code generated
        } catch (IOException e) {
            System.out.println("Failed to get API secret; using local secret" + localSecret);
            return localSecret;
        }
    }
    public String getSecretCode() {
        return secretCode;
    }
    public boolean hasAttemptsLeft() {
        return attemptsLeft > 0;
    }

    // Method to check if guess is four nums 0 - 8
    public boolean isValidGuess(String guess) {
        // 4 digits 0 - 7
        return guess != null && guess.matches(VALID_GUESS_PATTERN);
    }

    public void evaluateGuess(String guess) {
        if (!isValidGuess(guess)) {
            throw new IllegalArgumentException("Invalid guess");
        }
        guesses.add(guess); // Add guess to list of guesses
        attemptsLeft--;

    }

    public int getAttemptsLeft() {
        return attemptsLeft;
    }
    
    public String provideFeedback(String guess) {

        if (!isValidGuess(guess)) {
            return "Invalid guess; enter 4 digits, 0 - 7.";
        }
        
        int wellPlaced = 0;
        int misPlaced = 0;
        Map<Character, Integer> secretCount = new HashMap<>();
        Map<Character, Integer> guessCount = new HashMap<>();

        // Count well placed; populate hash
        for (int i = 0; i < 4; i++) {
            char secretChar = secretCode.charAt(i);
            char guessChar = guess.charAt(i);

            if (secretChar == guessChar) {
                wellPlaced++;
            }
            else {
                secretCount.put(secretChar, secretCount.getOrDefault(secretChar, 0) + 1);
                guessCount.put(guessChar, guessCount.getOrDefault(guessChar, 0) + 1);
            }
        }

        // Count mis-placed
        for (char c : guessCount.keySet()) {
            if (secretCount.containsKey(c)) {
                misPlaced += Math.min(secretCount.get(c), guessCount.get(c));
            }
        }

        return String.format("Well placed pieces: %d\nMisplaced pieces: %d", wellPlaced, misPlaced);
    }

}

