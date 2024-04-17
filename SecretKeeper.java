
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;



public class SecretKeeper extends Player {
    private Game game;

    public String secretCode;
    private List<String> guesses;
    public int maxAttempts = 5;
    public int attemptsLeft = maxAttempts;
    private GameData gameData;
    private GameData.GameDataDAO gameDataDAO;
  

    /* 
    Remove CLI for secret and num tries
    Have var for num tries max

    */ 
    public SecretKeeper(Game game, String playerName) {
        this.game = game;
        setPlayerName(playerName);
        this.secretCode = generateRandomSecret();
        this.guesses = new ArrayList<>();
    }

    private String generateRandomSecret() {
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
            // secretGenerated = true;
        } catch (IOException e) {
            e.printStackTrace(); // Handle error
        }
        return secretCode; // Return secret code generated
    }

    public String provideFeedback(String guess) {
        if (game.isValidGuess(guess)) {
            guesses.add(guess); // Add guess to list of guesses
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
            // If all pieces are well-placed, winner
            if (wellPlaced == 4) {
                System.out.println("Congrats! You did it! I'm SK.java");
                // System.exit(0);
            }
            
            String feedback = "Well placed pieces: " + wellPlaced + "\n";
            feedback += "Misplaced pieces: " + misPlaced + "\n";
            feedback += "---\n";
            attemptsLeft--;
            return feedback;
        }
        return "Invalid guess";
    }


    // Save data to database
    public void saveGameDataToDatabase() {
        if (gameData == null) {
            gameData = new GameData();
        }
        
        // String playerName = getPlayerName();
        gameData.setPlayerName(getPlayerName());
        gameData.setRoundsToSolve(maxAttempts - attemptsLeft);
        gameData.setSolved(attemptsLeft > 0);
        gameData.setTimestamp(new Timestamp(System.currentTimeMillis()));
        gameData.setSecretCode(secretCode);
        gameData.setGuesses(guesses);

        try {
            gameDataDAO.saveGameData(gameData);
        } catch (SQLException e) {
            e.printStackTrace(); // Handle db error
        }
    }
}
