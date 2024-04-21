// Game.java
package Models;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.SQLException;
import DAO.GameDataDAO;
import View.GameUI;

public class Game {
    private Guesser guesser;
    private SecretKeeper secretKeeper;
    private GameData gameData;
    private GameDataDAO gameDataDAO;
    private GameUI gameUI;
    private int attemptsLeft;
    public static final int MAX_ATTEMPTS = 5;

    // class constructor
    public Game (Guesser guesser, SecretKeeper secretKeeper, GameData gameData, GameDataDAO gameDataDAO) {
        this.guesser = guesser;
        this.secretKeeper = secretKeeper;
        this.gameData = gameData; 
        this.gameDataDAO = gameDataDAO;      
        this.gameUI = new GameUI();
        this.attemptsLeft = MAX_ATTEMPTS;
    }

    public static int getMaxAttempts() {
        return MAX_ATTEMPTS;
    }

    public void startGame() {
        // Use gameUI for UI interactions
        gameUI.displayMessage("Will you find the secret code?\nGood luck!");

        while (secretKeeper.hasAttemptsLeft()) {
            gameUI.displayMessage("---"); 
            gameUI.displayMessage("Round " + (MAX_ATTEMPTS - secretKeeper.getAttemptsLeft()));  
            gameUI.displayMessage("Rounds left: " + secretKeeper.getAttemptsLeft());     
            
            // String guess = gameUI.getInput("Enter guess: ");
            String guess = guesser.makeGuess();
            gameUI.displayMessage("YourGuess: " + guess);
            
            if (secretKeeper.isValidGuess(guess)) {
                secretKeeper.evaluateGuess(guess);
                String feedback = secretKeeper.provideFeedback(guess);
                gameUI.displayMessage(feedback);

                if (guess.equals(secretKeeper.getSecretCode())) {
                    gameUI.displayMessage("Congrats! You did it!");
                    gameData.setSolved(true);
                    break;
                }
            } else {
                gameUI.displayMessage("Wrong Input!");
            }
        } 

        // Womp womp womp . . . you lose
        if (!gameData.isSolved()) {
            gameUI.displayMessage("Sorry, too many tries. The code was: " + secretKeeper.getSecretCode());
            gameData.setSolved(false);
        }
        finalizeGameData();
    }

    private void finalizeGameData() {
        gameData.setPlayerName(guesser.getPlayerName());
        gameData.setGuesses(guesser.getGuesses());
        gameData.setRoundsToSolve(MAX_ATTEMPTS - secretKeeper.getAttemptsLeft());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = formatter.format(new Date());
        gameData.setFormattedDate(formattedDate);
        gameData.setSecretCode(secretKeeper.getSecretCode());
        saveGameDataToDatabase();
        gameUI.close();        
    }
    // Save data to database
    public void saveGameDataToDatabase() {
        try {
            gameDataDAO.saveGameData(gameData);
            gameUI.displayMessage("Game data saved");
        } catch (SQLException e) {
            gameUI.displayMessage("Error occured saving game data: " + e.getMessage());
        }
    }
}
